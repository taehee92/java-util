package com.git.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * RestAPI
 * 
 * @author taehee.kwon
 * @since 2020.06.19
 */
public class RestAPI {
	
	/**
	 * GET 방식으로 데이터 가져오는 메서드
	 * 
	 * @param requestUrl
	 * @return
	 */
	public String get(String requestUrl) {
		BufferedReader in = null; 
		StringBuffer result = new StringBuffer ();
		
		try { 
			URL obj = new URL(requestUrl);
			HttpURLConnection con = (HttpURLConnection)obj.openConnection(); 
			con.setRequestMethod("GET"); 
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
			
			String line = ""; 
			while((line = in.readLine()) != null) { 
				result.append(line);
			} 
		} catch(Exception e) { 
			e.printStackTrace(); 
		} finally {
			if(in != null) {
				try { 
					in.close(); 
				} catch(Exception e) { 
					e.printStackTrace(); 
				}
			}
		}
		
		return result.toString();
	}
}
