package net.sf.orcc.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import net.sf.orcc.runtime.actors.IScheduler;

public class WebSocketFifo<T> extends Fifo<T> {
	boolean isInput;
	String podName;
	FifoWsClient wsClient;
	URI serverURI;
	Thread senderThread;
	IScheduler scheduler;
	String currentType;

	public WebSocketFifo( IScheduler scheduler, String podName, boolean isInput, URI uri, int size, String ty ) {
		super( size );
		serverURI = uri;
		this.podName = podName;
		this.isInput = isInput;
		this.scheduler = scheduler;
		this.currentType = ty;
	}
	
	public void connect(String t) {
		wsClient = new FifoWsClient( serverURI );
		this.currentType = t;
		wsClient.connect();
		if( !isInput ) {
			senderThread = new Thread( new SenderThread() );
			senderThread.start();
		}
	}

	class SenderThread implements Runnable {
		public void run() {
			System.out.println( "[WebSocketFifo "+this+"] SenderThread started, podName: "+podName+"; isInput: "+isInput+"; URI: "+serverURI );
			while( true ) {
				try {
					T elem = WebSocketFifo.this.readBlocking();
					wsClient.sendItem( podName, System.currentTimeMillis(), elem );
				} catch( InterruptedException ex ) {}
			}
		}
	}

	public class FifoWsClient extends WebSocketClient {
	
		public FifoWsClient(URI serverUri, Draft draft) {
			super(serverUri, draft);
		}

		public FifoWsClient(URI serverURI) {
			super(serverURI);
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
			System.out.println("new connection opened");
			// Subscribe to the pod name if the data is incoming (input pod on our side)
			if( isInput )
				switch (currentType){
					case "Integer" : 
						sendItem( podName, 0L, 0);
						break;
					case "String":
						sendItem( podName, 0L, "zero");
						break;
					case "Float":
						sendItem( podName, 0L, 0.0f);
						break;
				}
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
			System.out.println("closed with exit code " + code + " additional info: " + reason);
		}

		@Override
		public void onMessage(String message) {
			
			System.out.println("received message: " + message);
			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(message);
				
				switch (root.path("type").textValue()){
					case "Integer": IntegerJSONToken tokenI = mapper.readValue( message, IntegerJSONToken.class );
						System.out.println( "received tokenI: "+tokenI );
						WebSocketFifo.this.write( (T)( tokenI.value ) );
						break;
					case "String" : StringJSONToken tokenS = mapper.readValue( message, StringJSONToken.class );
						System.out.println( "received tokenS: "+tokenS );
						WebSocketFifo.this.write( (T)( tokenS.value ) );
						break;
					case "Float"  : FloatJSONToken tokenF = mapper.readValue( message, FloatJSONToken.class );
						System.out.println( "received tokenF: "+tokenF );
						WebSocketFifo.this.write( (T)( tokenF.value ) );
						break;
			}

				try { 
					scheduler.schedule();
				} catch( Exception ex ) {
					ex.printStackTrace();
				}
			} catch( IOException ex ) {
				ex.printStackTrace();
			}
		}

		@Override
		public void onMessage(ByteBuffer message) {
			System.out.println("received ByteBuffer");
		}

		@Override
		public void onError(Exception ex) {
			System.err.println("an error occurred:" + ex);
		}
		
		private <T> boolean sendItem( String podName, long timeStamp, T value ) {
			//System.out.println( "sendItem: "+podName+"; timeStamp: "+timeStamp+"; value: "+value );
			ObjectMapper mapper = new ObjectMapper();
			boolean success = false;
			String on_s = "";
			switch (currentType) {
			case "Integer": 
			try {
				IntegerJSONToken ijt = new IntegerJSONToken( podName, timeStamp , ((Integer)value).intValue() );
				on_s = mapper.writeValueAsString(ijt);
				send( on_s );
				success = true;
			} catch( JsonProcessingException ex ) {
				ex.printStackTrace();
			} catch( IOException ex ) {
				ex.printStackTrace();
			}
			break;
			case "String":
				try {
					StringJSONToken ijt = new StringJSONToken( podName, timeStamp , ((String)value).toString());
					on_s = mapper.writeValueAsString(ijt);
					send( on_s );
					success = true;
				} catch( JsonProcessingException ex ) {
					ex.printStackTrace();
				} catch( IOException ex ) {
					ex.printStackTrace();
				}				
			break;
			case "Float":
				try {
					FloatJSONToken ijt = new FloatJSONToken( podName, timeStamp , ((Float)value).floatValue() );
					on_s = mapper.writeValueAsString(ijt);
					send( on_s );
					success = true;
				} catch( JsonProcessingException ex ) {
					ex.printStackTrace();
				} catch( IOException ex ) {
					ex.printStackTrace();
				}				
			break;	
			} // switch
			return success;
		}
	}
}
