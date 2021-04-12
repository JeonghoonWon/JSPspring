package kr.or.ddit.security;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/** 
 * encoding(부호화/decoding) : 저장이나 전송을 위해 
 * 			매체가 인지할 수 있는 방식으로 데이터의 표현방식을 바꾸는 작업.
 * 			UrlEncoding(Percent encoding), Base64
 * encrypting(암호화) : 허가되지 않은(key) 사용자의 데이터 접근을 막기위해 
 * 			데이터를 변환하는 작업  
 *
 * 단방향 암호화 (해시 함수) : 복호화 불가능한 암호화  해시값 : 일정한 길이의 문자열로 출력되는것 
 * 			: 입력 데이터의 길이가 달라도, 출력 데이터의 길이는 동일한 구조.
 * 		MessageDigest
 * 		SHA-512 (512:bit = 64 byte) 1bit = 8 byte
 * 		단방향에선 복호화가 불가능하다.
 * 양방향 암호화 : 키를 소유한 경우 복호화가 가능한 암호화
 * 		Ciph
 * (단방향 암호화는 주로 비밀번호를 암호화 할때 사용한다.)
 * (양방향 암호화는 전송 데이터를 다룰때 사용한다.)
 * 		대칭키 암호화 : 하나의 동일키(비밀키)를 통해 암복호화 수행
 * 			AES-128, AES-256
 * 			- 숫자의 의미 : key 의 길이를 정하는 것 . 	AES-128 : 16byte 길이의 key / 	 AES-256 : 32byte 길이의 key	
 * 			- 암호화 할때 키가 사용되었다면 key도 함께 전송 되어야 한다. 
 * 			- 동일한 key가 양쪽에 있어야 암복호화가 가능하기때문에
 * 		비대칭키 암호화 : 한쌍의 키(개인키/공개키)를 통해 암복호화 수행 / 암호화에 안전 하지만 암호화의 볼륨이 큰 경우 사용하기 어렵다.
 * 		
 * 
 * 		아래 내용들은 찾아보면 다 나온다.
 * 		더 중요한건 코드에서의 분류, 진행되는 방법
 */
public class EncyptDesc {
		public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
			String plain = "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ";
			
			Cipher cipher =  Cipher.getInstance("RSA");
			// 수학적 연산을 하기 때문에 초기화 백터나 padding 필요 없다
			
			// 한쌍의 키를 만들어 내기위한 KeyPairGenerator
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			
			keyPairGen.initialize(2048); //256byte
			KeyPair keyPair = keyPairGen.generateKeyPair();
			
			PrivateKey privateKey =  keyPair.getPrivate(); // 개인키
			PublicKey publicKey =  keyPair.getPublic(); // 공개키
			
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] input = plain.getBytes();
			byte[] encrypted = cipher.doFinal(input);
			String encoded = Base64.encodeBase64String(encrypted);
			System.out.println(encoded);
			
			byte[] decoded = Base64.decodeBase64(encoded);
			cipher.init(Cipher.DECRYPT_MODE,publicKey);
			byte[] decrtpted = cipher.doFinal(decoded);
			
			System.out.println(new String(decrtpted));
			
			
			
			
			
		}
		
		public static void aesEncryptTest(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// cbc 때문에 16byte 자리 초기 백터가 필요하다.
			byte[] iv = new byte[128/8];
			// 난수 생성으로 초기화 백터를 생성한다.
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			
			
			System.out.println(Arrays.toString(iv));
//			[-15, 121, 30, 69, 99, 52, 59, -57, -6, 54, -34, -9, 0, 127, 108, -116]

//			 비밀키
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);  // key 길이를 제한 해준다. 128bit
			
			SecretKey key = keyGen.generateKey();
			
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			// 문자열로 되어있기 때문에 타입 안정성이 없다.
			
			// AES는 하나의 키를 사용하는데 한쌍의 키가 사용될 경우 exception. 
			// 길이가 넘어가도 exception
			byte[] input = plain.getBytes();
			byte[] encrypted = cipher.doFinal(input);
			String encoded = Base64.encodeBase64String(encrypted);
			System.out.println(encoded);
			
			// 다시 복원
			byte[] decoded = Base64.decodeBase64(encoded);
			
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			byte[] decrypted = cipher.doFinal(decoded);
			System.out.println(new String(decrypted));
			
			// 16byte 자리 초기백터 가 필요
		}
		
		public static String encryptSha512(String plain) throws NoSuchAlgorithmException {
//			단방향 암호화  신규 회원 가입할때 써먹을 수 있다. 
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] input = plain.getBytes();
			byte[] encrypted = md.digest(input);
			System.out.println(encrypted.length*8);
			String encoded = Base64.encodeBase64String(encrypted);
			System.out.println(encoded);
//			String savedPass = "z6nxzDHNpYSV/uY9soAA6B+hjyAqDjzm50UmVebuxcrtLedsIkv83rrNmi/34a7NVQBDcs7Qi6U6AjGRa/G4Cg==";
//			System.out.println(savedPass.equals(encoded)?"인증성공":"인증실패");
			return encoded;
		}
		
		
		public static void encodeTest(String plain) throws IOException {
//			1. Url encoding
			String encoded  = URLEncoder.encode(plain,"UTF-8");
			System.out.println(encoded); // 누군가가 이해하도록 데이터를 바꾸는게 핵심
			String decoded = URLDecoder.decode(encoded,"UTF-8");
			System.out.println(decoded); //
			
			
//			2. Base 64 영문 소문자 대문자 갯수 + 0~9 숫자 갯수 + +/
			
			
			byte[] binary = plain.getBytes(); // 가지고 있는걸 byte 단위로 쪼갠다. 
			encoded = Base64.encodeBase64String(binary); // 쪼갠걸 적용시켜둔다.
			System.out.println(encoded);
			byte[] decodeBinary = Base64.decodeBase64(encoded);
			System.out.println(new String(decodeBinary));
			// 인코딩과 디코딩에서 보호가 포함되어있는가?
//			  - 지금까진 없었다. 암호화가 필요함.
		}
}
