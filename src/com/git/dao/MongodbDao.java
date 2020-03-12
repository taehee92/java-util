package com.git.dao;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Java & 몽고DB 연동 클래스  
 * 
 * @author taehee.kwon
 * @since 2020.03.11
 */
public class MongodbDao {
	
	/**
	 * 몽고DB Collection에서 Document 가져오는 메서드
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
			// 몽고DB 연동
			mongoClient = getConnection(mongoDBIp, mongoDBPort);
			
			// DB -> Collection(=RDB의 테이블) 지정
			DB database = mongoClient.getDB(mongoDBNm);
			DBCollection collection = database.getCollection(collectionNm);
			
			// 필요 쿼리 작성 -> 실행
			BasicDBObject query = new BasicDBObject ();
			
			/** 
			 * 1) IN 쿼리 
			 * "str" 이라는 변수명의 값이 list에 포함되는 document만 조회
			 * 
			 * -> Java code
			 	List<String> list = new ArrayList<String> ();
				query = new BasicDBObject("str", new BasicDBObject("$in", list));
			 * 
			 * -> MongoDB query
				 db.getCollection('컬렉션명').find({
				    "str": { $in: [
				           "list 값", ...
				        ]}
				    });
			 */

			
			/**
			 * 2) 다중 비교 쿼리 (and, or) 
			 * "num" 이라는 변수명의 값과 특정 값을 비교해서 만족하는 document만 조회
			 * 미만(lt), 이하(lte), 이상(gte), 초과(gt), 동일(eq)
			 * 
			 * -> Java code		
			 	List<BasicDBObject> subQueryList = new ArrayList<BasicDBObject> ();
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$lt", 미만 값)));
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$lte", 이하 값)));
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$gte", 이상 값)));
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$gt", 초과 값)));
				subQueryList.add(new BasicDBObject("num", new BasicDBObject("$eq", 동일 값)));
				query = new BasicDBObject("$and", subQueryList);
			 * 
			 * -> MongoDB query
				 db.getCollection('컬렉션명').find({"$and":[
						    {"num":{"$lt":미만 값}},
						    {"num":{"$lte":이하 값}},
						    {"num":{"$gte":이상 값}},
						    {"num":{"$gt":초과 값}},
						    {"num":{"$eq":동일 값}}
					    ]
					}); 
			 */
			
			// 쿼리 실행
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
	 * 몽고DB Collection에서 Document 가져오는 메서드
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
			// 몽고DB 연동
			mongoClient = getConnection(mongoDBIp, mongoDBPort);
			
			// DB -> Collection(=RDB의 테이블) 지정
			DB database = mongoClient.getDB(mongoDBNm);
			DBCollection collection = database.getCollection(collectionNm);
			
			// 실행
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
	 * MongoDB의 커넥션 획득
	 * 
	 * ※데이터 방향에 따른 mongoPort
	 * 27017 : 서버에서 데이터 보낼 때
	 * 27027 : 서버로 데이터 받을 때
	 * 
	 * @return
	 * @throws UnknownHostException 
	 */
	public MongoClient getConnection (String mongoDBIp, String mongoDBPort) throws UnknownHostException {
		return new MongoClient(mongoDBIp, Integer.valueOf(mongoDBPort));
	}
}
