package com.dragon3.infrastructure.util;

import java.util.UUID;

public class IdGenerator {
	
	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
