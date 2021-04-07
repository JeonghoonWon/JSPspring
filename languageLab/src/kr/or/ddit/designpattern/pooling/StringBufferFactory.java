package kr.or.ddit.designpattern.pooling;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class StringBufferFactory extends BasePooledObjectFactory<StringBuffer> {

	@Override
	public StringBuffer create() throws Exception {
		// TODO Auto-generated method stub
		return new StringBuffer();
	}

	@Override
	public PooledObject<StringBuffer> wrap(StringBuffer obj) {
		// TODO Auto-generated method stub
		return new DefaultPooledObject<StringBuffer>(obj);
	}
	
	@Override
	public void passivateObject(PooledObject<StringBuffer> p) throws Exception {
			StringBuffer returnObj = p.getObject();
			if(returnObj.length()>0)
			// 반납 받은 tmxmfla 버퍼에 값이 있을떄
			returnObj.delete(0, returnObj.length());
			returnObj.setLength(0);
	}
	
}
