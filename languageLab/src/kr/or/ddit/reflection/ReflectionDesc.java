package kr.or.ddit.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import kr.or.ddit.reflect.ReflectionTest;

public class ReflectionDesc {

		@SuppressWarnings("unchecked")
		public static void main(String[] args) {
			Object obj = ReflectionTest.getObject();
			System.out.println(obj);
			
			Map<Object, Object> map = new HashMap<>();
			dePopulate(obj, map);
		}
		
		public static void dePopulate(Object bean, Map<Object,Object>map) {
			Class clz = bean.getClass();
			Field[] fields = clz.getDeclaredFields();
			for(Field tmp : fields) {
				String varName = tmp.getName();
				try {
					PropertyDescriptor pd = new PropertyDescriptor(varName, clz); //(java bean 규약에 한해서) 한 쌍에 대한 정보를 가지고 있음 (setter/getter)
					Class varType = pd.getPropertyType();
					Method getter = pd.getReadMethod();
					Object value = getter.invoke(bean);
					
					Method setter = pd.getWriteMethod();
					
					setter.invoke(bean, "");
					
					System.out.printf("%s, %s= %s;\n",varType.getSimpleName(),varName,value);
				} catch (IntrospectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
			}
			
		}
		
		private static void reflect2(Object obj) {
			
			Class clz = obj.getClass();
			Field[] fields = clz.getDeclaredFields();
			for(Field tmp : fields) {
				String varName = tmp.getName();
				try {
					PropertyDescriptor pd = new PropertyDescriptor(varName, clz); //(java bean 규약에 한해서) 한 쌍에 대한 정보를 가지고 있음 (setter/getter)
					Class varType = pd.getPropertyType();
					Method getter = pd.getReadMethod();
					Object value = getter.invoke(obj);
					System.out.printf("%s, %s= %s;\n",varType.getSimpleName(),varName,value);
				} catch (IntrospectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
			}
		}
		
		private static void reflect1(Object obj) {
			
			Class clz = obj.getClass();
			System.out.println(clz);
			Field[] fields = clz.getDeclaredFields(); // 전연변수를 받아온다.(배열로)
			for(Field tmp : fields) {	// 전역변수를 for문 돌린다.
				String varName = tmp.getName();
				Class varType = tmp.getType();
				try {
//					tmp.setAccessible(true); // 외부에서 접근 가능해진다.
//					Object value = tmp.get(obj);
					String readMethodName = "get"+varName.substring(0,1).toUpperCase()
												 +varName.substring(1);
					Method readMethod = clz.getDeclaredMethod(readMethodName);
					
					Object value = readMethod.invoke(obj);
					System.out.printf("%s, %s= %s;\n",varType.getSimpleName(),varName, value);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// 실행하면 아무것도 가져오지못한다. public 만 가져올 수 있음 .대부분 class 만들때 캡슐화를 하기 때문에 가져오기 어렵다.
				// clz.getDeclaredFields(); 를 해줘야 가져올 수 있다. System.out.printf("%s %s;\n",varType.getName(),varName);	출력하면 퀄리파이드네임까지 가져온다.
				// System.out.printf("%s %s;\n",varType.getSimpleName(),varName);	getSimpleName() 으로 변경해주면 ex)String mem_hp; 형태로 출력
			}
		}
	
}
