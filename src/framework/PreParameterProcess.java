package framework;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;

public class PreParameterProcess {
	/**
	 * 
	 * @param method  : URI에 해당하는 기능 처리 메서드 객체
	 * @param request : 파라미터 정보를 담고있는 객체
	 * @return 
	 * @throws Exception 
	 */
	public Object[] process(Method method, HttpServletRequest request) 
			throws Exception {
		// 메서드에서 파라미터 타입 정보를 추출한다.
		Parameter[] pArr = method.getParameters();
		// 파라미터 타입에 해당하는 값들을 담을 배열
		Object[] rArr = new Object[pArr.length];
		
		// 파라미터 배열에 담긴 정보를 추출하기 위해서 반복을 실행함
		int index = 0;
		for (Parameter param : pArr) {
			Class<?> tClz = param.getType();
			// 파라미터 타입 이름
			String tName = tClz.getSimpleName();
			// 파라미터 이름
			String name = param.getName();
			
			// 만약, 파라미터 정보에 RequestParam 어노테이션이 존재하면
			// 어노테이션의 value 속성값을 name에 설정한다.
			RequestParam rp = param.getAnnotation(RequestParam.class);
			if (rp != null) {
				name = rp.value();
			}
			
			switch (tName) {
			case "int":
				rArr[index++] = 
					Integer.parseInt(request.getParameter(name));
				break;
			case "HttpServletRequest":
				rArr[index++] = request;
				break;
			case "String":
				rArr[index++] = request.getParameter(name);
				break;
			case "HttpSession":
				rArr[index++] = request.getSession();
				break;
			default:
				rArr[index++] = WebUtil.getParamToVO(tClz, request);
			}
		}
		return rArr;
	}
}





