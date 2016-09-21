package product.controller;

import com.google.gson.Gson;

import framework.Controller;
import framework.RequestMapping;
import product.service.ProductService;
import product.service.ProductServiceImple;

@Controller
public class ProductController {

	private ProductService service;
	
	public ProductController(){
		this.service = new ProductServiceImple();
	}
	
	@RequestMapping("/jsp/TopP.do")
	public String TopP (int page, String num) throws Exception{
		switch(num){
		case "1":
			return "ajax:"+ new Gson().toJson(service.selectLowPrice(page));
			
		case "2":
			return "ajax:"+ new Gson().toJson(service.selectHighPrice(page));
			
		case "3":
			return "ajax:"+ new Gson().toJson(service.selectPName(page));
			
		case "4":
			return "ajax:"+ new Gson().toJson(service.selectPRegDate(page));
			
		default :
			return "ajax:"+ new Gson().toJson(service.selectPRegDate(page));
			
		}
	}
	
}
