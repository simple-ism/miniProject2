package framework;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	public static Object getParamToVO(
			Class<?> clz, HttpServletRequest request) throws Exception {
		// 메서드 실행을 위한 target object
		Object obj = clz.newInstance();
		
		// clz 클래스 정보에 있는 모든 메서드를 가져온다.
		Method[] mArr = clz.getDeclaredMethods();
		for (Method m : mArr) {
			String mName = m.getName();
			if (!mName.startsWith("set")) continue;
//			System.out.println("mName : " + mName);
			
			// setNo       --> no
			// setTitle    --> title
			// setWriter   --> writer
			// setContent  --> content
			// setRegDate  --> regDate
			
			// set 제거하기
			// No, Title, Writer, Content, RegDate
			mName = mName.substring("set".length());
			// no, title, writer, content, regDate
			mName = Character.toLowerCase(mName.charAt(0)) + 
					mName.substring(1);
			
			// set 메서드에 해당하는 파라미터 가져오기
			String pValue = request.getParameter(mName);
			if (pValue == null) continue;
			
			// set 메서드에 설정할 값이 파라미터에 존재함
			
			// 메서드를 실행하기
			// 메서드 객체 : m, 인스턴스 객체 : obj
			// public void setNo(int no);
			// board.setNo(Integer.parseInt("10"));
			// board.setTitle("aaa");
			
			String pName = m.getParameterTypes()[0].getName();
//			System.out.println("변경된 mName : " + mName);
//			System.out.println("파라미터 타입 이름 : " + pName);
			switch (pName) {
			case "int":
				m.invoke(obj, Integer.parseInt(pValue));
				break;
			default:
				m.invoke(obj, pValue);
				break;
			}
		}
		return obj;
	}
}










