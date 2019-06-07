package net.sf.orcc.runtime;

public class StringJSONToken{
	public String value;
	public long timeStamp;
	public String pod;
	public String type = "String";
	
	public StringJSONToken(String p, long ts, String v){
		this.pod = p;
		this.timeStamp = ts;
		this.value = v;
	}	
	
	public StringJSONToken(){
		this.pod = null;
		this.timeStamp = 0L;
		this.value = null;
	}
	
	public String toString() {
		return "Value is: " + value + " time stamp is: " + timeStamp + " pod is: " + pod + " valueType is: String";
	}
}	
