package com.git.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ���� ���� �ڷḦ �������� ��� Ŭ����
 * 
 * @author taehee.kwon
 * @since 2020.01.01
 */
public class ReadUtil {

	/**
	 * CSV ������ ���κ��� �ʿ� ��� ����Ʈ�� �����Ѵ�
	 * 
	 * @param filePath
	 * @return
	 */
	public List<HashMap<String, String>> readCSV(String filePath) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>> ();
		
		// ������ ������� �����ؼ� �о�´�
		List<String> lineList = readFile(filePath, 0);
		
		String[] cols = null;
		
		for(int i=0; i<lineList.size(); i++) {
			String line = lineList.get(i);
			
			// ù��° ���� ���(=�÷�)
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
	 * ���� ������ List�� ��´�
	 * �� ���������� skipLine���� ���� ��ŵ�Ѵ� ��
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
