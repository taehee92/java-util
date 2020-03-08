package com.git.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 파일 등의 자료를 가져오는 기능 클래스
 * 
 * @author taehee.kwon
 * @since 2020.01.01
 */
public class ReadUtil {

	/**
	 * CSV 파일을 라인별로 맵에 담아 리스트에 세팅한다
	 * 
	 * @param filePath
	 * @return
	 */
	public List<HashMap<String, String>> readCSV(String filePath) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>> ();
		
		// 파일을 헤더까지 포함해서 읽어온다
		List<String> lineList = readFile(filePath, 0);
		
		String[] cols = null;
		
		for(int i=0; i<lineList.size(); i++) {
			String line = lineList.get(i);
			
			// 첫번째 줄은 헤더(=컬럼)
			if(i == 0 && line.trim().length() > 0) {
				cols = line.trim().split(",");
			}
			
			if(cols != null) {
				String[] values = null;
				if(line.endsWith(",")) {
					values = (line + " ").split(",");
				} else {
					values = line.split(",");
				}
				
				if(cols.length == values.length) {
					HashMap<String, String> map = new HashMap<String, String> ();
					for(int j=0; j<values.length; j++) {
						map.put(cols[j], values[j]);
					}
					
					result.add(map);
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * 파일 내용을 List에 담는다
	 * ※ 위에서부터 skipLine개의 줄은 스킵한다 ※
	 * 
	 * @param filePath
	 * @param skipLine
	 * @return
	 */
	public List<String> readFile(String filePath, int skipLine) {
		List<String> result = new ArrayList<String> ();
		
		File file = new File(filePath);
		if(file.exists()) {
			int skip = 0;
			
			BufferedReader br = null;
			
			try {
				br = new BufferedReader (new InputStreamReader(new FileInputStream(filePath), "UTF8"));
				String line = "";
				
				while((line = br.readLine()) != null) {
					skip ++;
					
					if(skip <= skipLine) {
						continue;
					}
					
					result.add(line);
				}
				
				br.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(br != null) {
					try { 
						br.close(); 
					} catch(Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		return result;
	}
}
