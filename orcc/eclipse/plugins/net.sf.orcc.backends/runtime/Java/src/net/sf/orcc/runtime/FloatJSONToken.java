package net.sf.orcc.runtime;

public class FloatJSONToken{
	public Float value;
	public long timeStamp;
	public String pod;
	public String type = "Float";
	
	public FloatJSONToken(String p, long ts, Float v){
		this.pod = p;
		this.timeStamp = ts;
		this.value = v;
	}	
	
	public FloatJSONToken(){
		this.pod = null;
		this.timeStamp = 0L;
		this.value = 0.0F;
	}
	
	public String toString() {
		return "Value is: " + value + " time stamp is: " + timeStamp + " pod is: " + pod + " valueType is: Float";
	}
}	