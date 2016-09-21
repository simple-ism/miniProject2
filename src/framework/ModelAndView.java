package framework;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
	// 작업후 호출할 페이지 
	private String view;
	// 화면페이지에서 사용할 공유 데이터
	private Map<String, Object> model = new HashMap<>();

	public ModelAndView() {}
	public ModelAndView(String view) {
		this.view = view;
	}
	
	public void addAttribute(String key, Object value) {
		model.put(key, value);
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public Map<String, Object> getModel() {
		return model;
	}
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
}










