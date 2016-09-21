package framework;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLHandleMapping {
	private Map<String, CtrlAndMethod> mappings;
	private List<String> ctrlNameList;
	
	public URLHandleMapping() {}
	public URLHandleMapping(String scanPackage) throws Exception {
		mappings = new HashMap<>();
		ctrlNameList = new ArrayList<>();
		
		// scanPackage : kr.co.mlec
		getCtrlNameList(scanPackage);
		
		addMap();
	}

	private void addMap() throws Exception {
		for (String ctrlName : ctrlNameList) {
			Class<?> clz = Class.forName(ctrlName.trim());
			Object target = clz.newInstance();
			
			// clz 안에 존재하는 모든 메서드를 추출한다.
			Method[] mArr = clz.getDeclaredMethods();
			// 반복을 진행하면서 메서드의 URI 정보를 추출하고
			// URI 에 해당하는 객체와 실행 메서드 정보를 맵에 저장한다.
			for (Method method : mArr) {
				RequestMapping rm = method.getAnnotation(RequestMapping.class);
				if (rm == null) continue;
				
				mappings.put(
					rm.value(), new CtrlAndMethod(target, method)
				);
			}
		}
	}
	/**
	 * scanPackage 밑의 모든 클래스 중에서 @Controller 어노테이션이
	 * 설정된 컨트롤 클래스를 찾아서 ctrlNameList에 추가한다.
	 * @param scanPackage
	 */
	private void getCtrlNameList(String scanPackage) throws Exception {
		// kr.co.mlec
		URL url = this.getClass()
					  .getClassLoader()
					  .getResource(scanPackage.replace(".", "\\"));
		// file:/C:/../LecBoard/WEB-INF/classes/kr/co/mlec/
		System.out.println("url.toString() : " + url.toString());
		File f = new File(url.toURI());
		// C:\..\LecBoard\WEB-INF\classes\kr\co\mlec
		System.out.println("f.getPath() : " + f.getPath());
		// C:\..\LecBoard\WEB-INF\classes\
		String packagePath = f.getPath()
				              .replace(scanPackage.replace(".", "\\"), "");
		System.out.println("packagePath : " + packagePath);
		
		File[] fileList = f.listFiles();
		
		/*
		file ::: C:\..\LecBoard\WEB-INF\classes\kr\co\mlec\board
		file ::: C:\..\LecBoard\WEB-INF\classes\kr\co\mlec\filter
		file ::: C:\..\LecBoard\WEB-INF\classes\kr\co\mlec\login
		file ::: C:\..\LecBoard\WEB-INF\classes\kr\co\mlec\member
		file ::: C:\..\LecBoard\WEB-INF\classes\kr\co\mlec/util
		 */
		for (File file : fileList) {
			System.out.println("file ::: " + file.getPath());
			getClassNameByPackage(file, packagePath);
		}
	}
	
	/**
	 * file 이 만약 디렉토리라면 디렉토리 하위의 모든 파일들을 찾는다.
	 * file 이 파일 이라면 파일의 이름을 추출해서 클래스 패키지 형태로 변환
	 * @param file
	 * @param packagePath
	 */
	private void getClassNameByPackage(File file, String packagePath) 
			throws Exception {
		if (file.isDirectory()) {
			File[] fileList = file.listFiles();
			for (File f : fileList) {
				getClassNameByPackage(f, packagePath);
			}
		}
		else if (file.isFile()) {
			//  C:\....\WEB-INF\classes\kr\co\mlec\board\Board.class
			//  kr.co.mlec.board.Board
			String fPath = file.getPath();
			System.out.println("file 인 경우 : " + fPath);
			String className = fPath.substring(0, fPath.lastIndexOf("."))   
					                .replace(packagePath, "")
					                .replace("\\", ".");
			
			//  C:\....\WEB-INF\classes\kr\co\mlec\board\Board
			//  kr\co\mlec/util\PrintUtil
			//  kr.co.mlec.util.PrintUtil
			System.out.println("className1 : " + className);
			
			// 어노테이션이 선언된 경우만 리스트에 추가
			boolean isExist = isControllerAnno(className);
			if (isExist == true) {
				ctrlNameList.add(className);
			}
		}
	}
	
	/**
	 * 매개변수에 넘어온 클래스에 @Controller 어노테이션이 선언되어 있는지
	 * 판단
	 * @param className : 패키지를 포함한 클래스명
	 * @return
	 */
	private boolean isControllerAnno(String className) throws Exception {
		Class<?> clz = Class.forName(className);
		Controller cAnno = clz.getAnnotation(Controller.class);
		return cAnno != null;
	}
	
	public CtrlAndMethod getCtrlAndMethod(String requestUri) {
		return mappings.get(requestUri);
	}
}









