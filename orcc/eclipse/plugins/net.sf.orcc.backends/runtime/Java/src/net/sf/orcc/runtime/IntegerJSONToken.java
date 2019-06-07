package net.sf.orcc.runtime;

public class IntegerJSONToken{
	public Integer value;
	public long timeStamp;
	public String pod;
	public String type = "Integer";
	
	public IntegerJSONToken(String p, long ts, Integer v){
		this.pod = p;
		this.timeStamp = ts;
		this.value = v;
	}	
	
	public IntegerJSONToken(){
		this.pod = null;
		this.timeStamp = 0L;
		this.value = null;
	}
	
	public String toString() {
		return "Value is: " + value + " time stamp is: " + timeStamp + " pod is: " + pod + " valueType is: Integer";
	}
}	
