package com.git.dao;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Java & ����DB ���� Ŭ����  
 * 
 * @author taehee.kwon
 * @since 2020.03.11
 */
public class MongodbDao {
	
	/**
	 * ����DB Collection���� Document �������� �޼���
	 * 
	 * @param mongoDBIp
	 * @param mongoDBPort
	 * @param mongoDBNm
	 * @param collectionNm
	 * @param applyIdxList
	 */
	public void selectFind(String mongoDBIp, String mongoDBPort, String mongoDBNm, String collectionNm) {
		MongoClient mongoClient = null;
		
		try {
			// ����DB ����
			mongoClient = getConnection(mongoDBIp, mongoDBPort);
			
			// DB -> Collection(=RDB�� ���̺�) ����
			DB database = mongoClient.getDB(mongoDBNm);
			DBCollection collection = database.getCollection(collectionNm);
			
			// �ʿ� ���� �ۼ� -> ����
			BasicDBObject query = new BasicDBObject ();
			
			/** 
			 * 1) IN ���� 
			 * "str" �̶�� �������� ���� list�� ���ԵǴ� document�� ��ȸ
			 * 
			 * -> Java code
			 	List<String> list = new ArrayList<String> ();
				query = new BasicDBObject("str", new BasicDBObject("$in", list));
			 * 
			 * -> MongoDB query
				 db.getCollection('�÷��Ǹ�').find({
				    "str": { $in: [
				           "list ��", ...
				        ]}
				    });
			 */

			
			/**
			 * 2) ���� �� ���� (and, or) 
			 * "num" �̶�� �������� ���� Ư�� ���� ���ؼ� �����ϴ� document�� ��ȸ
			 * �̸�(lt), ����(lte), �̻�(gte), �ʰ�(gt), ����(eq)
			 * 
			 * -> Java code		
			 	List<BasicDBObject> subQueryList = new ArrayList<BasicDBObject> ();
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$lt", �̸� ��)));
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$lte", ���� ��)));
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$gte", �̻� ��)));
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$gt", �ʰ� ��)));
				query = new BasicDBObject("$and", subQueryList);
			 * 
			 * -> MongoDB query
				 db.getCollection('�÷��Ǹ�').find({"$and":[
						    {"num":{"$lt":�̸� ��}},
						    {"num":{"$lte":���� ��}},
						    {"num":{"$gte":�̻� ��}},
						    {"num":{"$gt":�ʰ� ��}}
					    ]
					}); 
			 */
			
			// ���� ����
			DBCursor item = collection.find(query);
			
			while(item.hasNext()) {
				System.out.println(item.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}
	
	
	/**
	 * ����DB Collection���� Document �������� �޼���
	 * 
	 * @param mongoDBIp
	 * @param mongoDBPort
	 * @param mongoDBNm
	 * @param collectionNm
	 * @param applyIdxList
	 */
	public void select(String mongoDBIp, String mongoDBPort, String mongoDBNm, String collectionNm) {
		MongoClient mongoClient = null;
		
		try {
			// ����DB ����
			mongoClient = getConnection(mongoDBIp, mongoDBPort);
			
			// DB -> Collection(=RDB�� ���̺�) ����
			DB database = mongoClient.getDB(mongoDBNm);
			DBCollection collection = database.getCollection(collectionNm);
			
			// ����
			DBCursor item = collection.find();
			
			while(item.hasNext()) {
				System.out.println(item.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(mongoClient != null) {
				mongoClient.close();
			}
		}
	}
	
	
	/**
	 * MongoDB�� Ŀ�ؼ� ȹ��
	 * 
	 * �ص����� ���⿡ ���� mongoPort
	 * 27017 : �������� ������ ���� ��
	 * 27027 : ������ ������ ���� ��
	 * 
	 * @return
	 * @throws UnknownHostException 
	 */
	public MongoClient getConnection (String mongoDBIp, String mongoDBPort) throws UnknownHostException {
		return new MongoClient(mongoDBIp, Integer.valueOf(mongoDBPort));
	}
}
