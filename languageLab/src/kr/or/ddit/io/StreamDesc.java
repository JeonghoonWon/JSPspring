package kr.or.ddit.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

/**
 * 
 * 스트림 : 연속성을 가진 일련의 데이터의 흐름.
 * 
 * 스트림의 종류
 * 1. 전송 데이터 크기
 * 		1) byte stream(1byte) : XXXInputStream / XXXOutputStream
 * 			FileInputStream / FileOutputStream  1차 stream / 
 * 			SocketInputStream / SocketOutputStream
 * 			ByteInputStream/ByteOutputStream (매체 : Inmemory)
 * 		
 * 		2) character stream(1 char)	: XXXReader / XXXWriter
 * 			FileReader / FileWriter
 * 			StringReader/StringWriter 
 *
 * 2. stream 생성 방법
 * 		1) 1차 stream(단일형 스트림) : 매체를 대상으로 직접 생성(개방) 하는 스트림
 * 		2) 2차 stream(연결형 스트림) : 1차 스트림을 대상으로 보조형으로 사용하는 스트림
 * 			더 많은 데이터를 한번 더 필터링 해서 사용하고 싶기 때문에 2차 stream 사용
 * 			- buffered stream : ButteredReader
 * 			- filtered stream : DataInputStream
 *			- object stream : ObjectInputStream	(only Serializable 객체를 대상으로 직렬화/역직렬화)
 *		
 *		직렬화(Serialization)	: 격체를  전송하거나 기록하기 위한 형태의 바이트 배열의 형태로 변환하는 작업
 *		역직렬화(DeSerialization) : 기록되어있거나 전송된 바이트 배열로부터 원래 긱체의 상태를 복원하는 작업
 *
 *		** 매체로부터 데이터를 읽어들이는 단계
 *		1. 매체를 어플리케이션내에서 핸들링할 수 있는 객체로 생성
 *		   new File(file system path), new ServerSocket(port)
 *		2. 읽어들이기 위한 스트림 생성
 *		   new FileInputStream(file), socket.getInputStream()
 *		   
 *			1차 2차를 연결하기 위해 필요한 것:
 *		  	 new InputStreamReader
 *		  
 *			 new BufferdReader() 
 * 		3. stream end 까지 읽기 반복(EOF, -1, null)
 * 		
 * 		4. 자원 release : close
 *
 *
 */
public class StreamDesc {
	
	public static void main(String[] args) throws IOException{
		String path = "https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
		File folder = new File("d:/contents");
		File writeFile = new File(folder,"googlelogo_color_272x92dp.png");
		
		
		URL url = new URL(path);
		System.out.println(url);
		URLConnection conn = url.openConnection();	// 소켓 통신을 위한 통신 언어를 만든 것
		
		try(
		InputStream is = conn.getInputStream();
		FileOutputStream fos = new FileOutputStream(writeFile);
			
		){
			byte[] buffer = new byte[1024];	// try 안에는 close 하는것만 들어가야 함
			int cnt = -1;
			while((cnt = is.read(buffer))!= -1) {
				fos.write(buffer, 0, cnt);
				
			}
		}
				
				
		//		d:/contents/오래된 노래_utf8.txt
//		File folder = new File("d:/contents");
//		File readFile = new File(folder,"오래된 노래_utf8.txt");
//		String temp = null;
//		FileReader reader = new FileReader(readFile); //1차 stream 
//		BufferedReader br = new BufferedReader(reader);
//		while((temp = br.readLine()) != null) {
//			System.out.println(temp);
//		}
		
		
//		File folder = new File("d:/contents");
//		File readFile = new File(folder,"오래된 노래_utf8.txt"); // 출력을 하면 글자가 깨진다.
//		String temp = null;
////		try( Closable 객체 생성){}catch{}finally{}
//		
//		try(
//		FileInputStream fis = new FileInputStream(readFile); // 파일을 바이트 단위로 쪼개야함.
//		InputStreamReader reader = new InputStreamReader(fis, "utf-8"); 
//		BufferedReader br = new BufferedReader(reader);
//		){
//			while((temp = br.readLine()) != null) {
//				System.out.println(temp);
//			}
//		}catch (IOException e) {
//			System.err.println(e.getMessage());
//			// try catch를 사용하지 않으면 br, reader, fis 순으로 close 해줘야한다.
//			// 그걸 걱정 안하고 close 하기 위해 사용.
//			// 1.7 버전 이후 부터 사용 가능
//		}
//	String qualifiedName = "/kr/or/ddit/io/another day.txt"; // 앞에 슬러시 없으면 상대경로
////		ClassLoader loader = Thread.currentThread().getContextClassLoader();
////		URL url = loader.getResource(qualifiedName);
////		URL url = StreamDesc.class.getResource(qualifiedName);
//		// http : 프로토콜 , 스키마 라고 함.
//		
//		// ClassLoader로 찾으려면 상대 경로
//		// class 로 찾으려면 절대 경로
//		String temp = null;
//		System.out.println(url);
//		// 1차 stream
//
//		try(	
//				InputStream in = StreamDesc.class.getResourceAsStream(qualifiedName);
//			
//				InputStreamReader reader = new InputStreamReader(in); 
//				BufferedReader br = new BufferedReader(reader);
//				){
//					while((temp = br.readLine()) != null) {
//						System.out.println(temp);
//					}
//				}catch (IOException e) {
//					System.err.println(e.getMessage());
//					// try catch를 사용하지 않으면 br, reader, fis 순으로 close 해줘야한다.
//					// 그걸 걱정 안하고 close 하기 위해 사용.
//					// 1.7 버전 이후 부터 사용 가능
//				}
		
	}
	
	private static void readFileSystemResource() {
		
		
		StreamDesc de = new StreamDesc();
		
		File folder = new File("d:/contents");
		File readFile = new File(folder,"오래된 노래_utf8.txt"); 
		// 출력을 하면 글자가 깨진다.
		String temp = null;
//		try( Closable 객체 생성){}catch{}finally{}
		
		try(
		FileInputStream fis = new FileInputStream(readFile); // 파일을 바이트 단위로 쪼개야함.
		InputStreamReader reader = new InputStreamReader(fis, "utf-8"); 
		BufferedReader br = new BufferedReader(reader);
		){
			while((temp = br.readLine()) != null) {
				System.out.println(temp);
			}
		}catch (IOException e) {
			System.err.println(e.getMessage());
			// try catch를 사용하지 않으면 br, reader, fis 순으로 close 해줘야한다.
			// 그걸 걱정 안하고 close 하기 위해 사용.
			// 1.7 버전 이후 부터 사용 가능
		}
	}

}
