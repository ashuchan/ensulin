package com.nandu.Utility;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


public class JsonMapper extends ObjectMapper {
	
	public JsonMapper(){
		super();
		this.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}
}
