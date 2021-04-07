package kr.or.ddit.designpattern.pooling;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class ReaderUtilTestView {
   public static void main(String[] args) throws IOException {
      // 풀링 작업 전 factory 데이터 넘김
      PooledObjectFactory<StringBuffer> factory = new StringBufferFactory();
      // 풀링 작업을 위한 객체 생성
      GenericObjectPool<StringBuffer> pool = new GenericObjectPool<StringBuffer>(factory);
      // 최대 3개의 pool 생성
      pool.setMaxTotal(3);
      // 최대 기다리는 시간
      pool.setMaxWaitMillis(2000);
      // pool 되고 있는 객체가 몇개인지 확인하기 위해 존재
//      pool.getNumActive();
      
      
      InputStream is = ReaderUtilTestView.class.getResourceAsStream("/kr/or/ddit/io/another day.txt");
//      InputStreamReader reader = new InputStreamReader(is);
      
      // pool 데이터 전달
      String result = new ReaderUtil(pool).readFromInputStreamToStream(is);
      
//      new ReaderUtil(당구).readFromInputStreamToStream(is);
//      new ReaderUtil(당구).readFromInputStreamToStream(is);
//      new ReaderUtil(당구).readFromInputStreamToStream(is);
      
      System.out.printf("active count : %d\n", pool.getNumActive());
      System.out.println(result);
   }
}