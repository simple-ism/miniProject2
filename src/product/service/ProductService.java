package product.service;

import java.util.List;

import product.ProductVO;

public interface ProductService {
	public List<ProductVO> selectLowPrice(int page) throws Exception;
	public List<ProductVO> selectHighPrice(int page) throws Exception;
	public List<ProductVO> selectPName(int page) throws Exception;
	public List<ProductVO> selectPRegDate(int page) throws Exception;
}
