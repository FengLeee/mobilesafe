package com.yiya.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamTostring {

	public static String getString(InputStream in) throws IOException {
		//ÄÚ´æÊä³öÁ÷
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		int len = 0;
		byte[] arr = new byte[1024];
		while( (len=in.read(arr))!=-1 ) {
			bao.write(arr, 0, len);
		}
		return bao.toString();
		
	}

}
