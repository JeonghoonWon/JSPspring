package kr.or.ddit.designpattern.pooling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.pool2.ObjectPool;

public class ReaderUtil {
	private ObjectPool<StringBuffer> pool;

	public ReaderUtil(ObjectPool<StringBuffer> pool) {
		super();
		this.pool = pool;
	}

	public String readFromInputStreamToStream(InputStream is) throws IOException {
		InputStreamReader reader = new InputStreamReader(is);
		return readFromReaderToString(reader);
	}

	public String readFromInputStreamToStream(InputStream is, String charset) throws IOException {
		InputStreamReader reader = new InputStreamReader(is, charset);
		return readFromReaderToString(reader);
	}

	public String readFromReaderToString(Reader reader) throws IOException {
		// 버퍼를 만들어서 한 줄 씩 읽는게 빠르다
		// pooling 작업 전
		StringBuffer result = null;
		try (BufferedReader br = new BufferedReader(reader);) {
			result = pool.borrowObject();
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				result.append(String.format("%s\n", tmp));
			}
			
			String str = result.toString();
			return str;
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			if (result != null) {
				try {
					pool.returnObject(result);
				} catch (Exception e) {
					throw new IOException(e);
				}
			}
		}
	}
}