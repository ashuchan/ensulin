package com.ensulin.models.dto;

public class OutputObject {

	public static String TYPE_CREATE= "CREATE";
	public static String TYPE_UPDATE= "UPDATE";
	public static String TYPE_DELETE= "DELETE";
	public static String TYPE_SEARCH= "SEARCH";
	public static String STATUS_OK="OK";
	public static String STATUS_FAIL="FAIL";
	public String type;
	public Object message;
	public String status;
	
	public OutputObject(String a, Object b, String c){
		this.type = a;
		this.message = b;
		this.status = c;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
