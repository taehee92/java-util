package com.git.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 파일 등의 자료를 생성하는 기능 클래스
 * 
 * @author taehee.kwon
 * @since 2020.01.01
 */
public class WriteUtil {
	final String NEWLINE = System.getProperty("line.separator");
	
	/**
	 * 리스트를 파일로 저장한다
	 * 
	 * @param result
	 * @param filePath
	 * @return
	 */
	public int makeFile(List<String> result, String filePath) {
		int cnt = 0;
		
		StringBuffer sb = new StringBuffer ();
		
		for(int i=0; i<result.size(); i++) {
			sb.append(result.get(i));
			sb.append(NEWLINE);
			
			cnt++;
		}
		
		if(sb.toString().length() > 0) {
			BufferedWriter bw = null;
			
			try {
				bw = new BufferedWriter(
						new OutputStreamWriter(
							new FileOutputStream(filePath, false), StandardCharsets.UTF_8));
				
	            bw.write(sb.toString());
	            bw.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
}
