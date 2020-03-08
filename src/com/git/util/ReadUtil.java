package com.git.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ���� ���� �ڷḦ �������� ��� Ŭ����
 * 
 * @author taehee.kwon
 * @since 2020.01.01
 */
public class ReadUtil {
	
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
