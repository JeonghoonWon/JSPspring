package kr.or.ddit.annotation;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *	Annotation : 사람과 시스템에게 일정 정보를 전달하기 위한 방법
 *	종류
 *	1. Marker annotation
 *	2. SingleValue annotation
 *	3. MultiValue annotation
 *
 *	커스텀 어노테이션 방법
 *	1. @interface 키워드로 생성 : Annotation 의 구현체의 형태로 정의됨.
 *	2. 필수 정책
 *		1) 어노테이션 사용 위치 : @Target
 *		2) 어노테이션의 생존 범위 : @Retention (SOURCE, COMPILE, RUNTIME)
 *
 */


public class AnnotationDesc {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		String basePackage = "kr.or.ddit.designpattern.commandpattern";
		//basePackage.replace('.','/'); //classPath 이후의 경로가 나온것
		
		// 현재 동작하고 있는 
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		URL baseFolderURL = loader.getResource(basePackage.replace('.','/'));
		
		//System.out.println(baseFolderURL);
	
		File baseFolder = new File(baseFolderURL.getFile());
		String baseFolderAP = baseFolder.getAbsolutePath();
		//System.out.println(baseFolder.getAbsolutePath());
		File[] files = baseFolder.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("class");
			}
			
		});
		List<Class<?>> withGroup = new ArrayList<>(); // annotation 가지고 있는 그룹
		List<Class<?>> withoutGroup = new ArrayList<>(); // annotation 없는 그룹
		
		for(File classFile : files) {
			//System.out.println(classFile.getAbsolutePath());
			String clzAP = classFile.getAbsolutePath();
			int lastIdx = clzAP.lastIndexOf(".");
			String className = 
					clzAP.substring(baseFolderAP.length()+1,lastIdx);
			//System.out.println(basePackage +"."+ className);
			String qualified = basePackage +"."+ className;
			
			try {
				Class<?> clz = Class.forName(qualified);
				// 특정 타입의 annotation 이 있는지 봐야함.
				FirstAnnotation annotation = clz.getAnnotation(FirstAnnotation.class);
				if(annotation == null) {
					withoutGroup.add(clz);
				}else {
					withGroup.add(clz);	
				}
				
				System.out.println(clz);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}// for end
		
		System.out.println(withGroup);
		
		// 
		// annotation 이 가지고 있는 instance 를 value 로 넣는다. : Object
		// 
		Map<String,Object> instanceMap = new HashMap<>();
		for(Class<?> tmp : withGroup) {
		// 클래스를 힙 메모리에 넣어야 한다.	
			Object instance = tmp.newInstance();
			FirstAnnotation annotation = tmp.getAnnotation(FirstAnnotation.class);
			String key = annotation.value();
			
			instanceMap.put(key, instance);
		}
			//System.out.println(instanceMap);
			for(Entry<String,Object> entry : instanceMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				System.out.printf("%s : %s \n", key,value);
			
		}

	}
}
