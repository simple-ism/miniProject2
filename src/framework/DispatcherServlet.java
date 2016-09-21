/*
 *  url 패턴 : 
 *  /board/list.do  ->  BoardListController 
 *  /board/write.do  ->  BoardWriteController
 *  
 *  command 패턴 : 
 *  /board.do?type=list -> BoardListController
 *  /board.do?type=write -> BoardWriteController
 * 
 * 
 *  사용자의 요청을 받고 
 *  요청에 해당하는 작업 컨트롤러 클래스를 호출하고
 *  작업클래스에서 실행한 결과를
 *  적절한 사용자 화면 페이지를 호출하여 사용할 수 있게 한다.
 *  
 *  추후..
 *  자동 파라미터 처리
 *  사용자 요청 작업 클래스를 검색하는 부분을 효율적으로 변경
 */
package framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
	
	private URLHandleMapping mappings;
	@Override
	public void init(ServletConfig config) throws ServletException {
		String scanPackage = config.getInitParameter("scan-package");
		try {
			mappings = new URLHandleMapping(scanPackage);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 현재 프로젝트 경로 찾기
		String contextPath = request.getContextPath();
		String requestUri  = request.getRequestURI();
		requestUri = requestUri.substring(contextPath.length());
		
		String view = "";
		CtrlAndMethod cam = mappings.getCtrlAndMethod(requestUri);
		if (cam == null) {
			throw new ServletException("요청하신 URL이 존재하지 않습니다.");
		}
		try {
			Object target = cam.getTarget();
			Method method = cam.getMethod();
			
			PreParameterProcess ppp = new PreParameterProcess();
			// 파라미터에 입력될 값들을 담은 배열
			Object[] param = ppp.process(method, request);
			
			// 반환타입 정보 얻기
			Class<?> rType = method.getReturnType();
			String rName = rType.getSimpleName();
			ModelAndView mav = null;
			switch (rName) {
			case "ModelAndView":
				mav = (ModelAndView)method.invoke(target, param);
				view = mav.getView();
				break;
			case "String":
				view = (String)method.invoke(target, param);
				break;
			case "void":
				method.invoke(target, param);
				view = requestUri.replace(".do", ".jsp");
				break;
			}
			
			if (view.startsWith("redirect:")) {
				response.sendRedirect(view.substring("redirect:".length()));
			} 
			else if (view.startsWith("ajax:")) {
				response.setContentType("text/json; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(view.substring("ajax:".length()));
				out.close();
			}
			else {
				// mav 객체의 model 정보를 화면에 사용할 수 있도록
				// 공유 하는 작업 필요함
				if (mav != null) {
					Map<String, Object> model = mav.getModel();
					Set<String> keys = model.keySet();
					for (String key : keys) {
						request.setAttribute(key, model.get(key));
					}
				}
				RequestDispatcher rd = request.getRequestDispatcher(view);
				rd.forward(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}












