package product;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import util.MyAppSqlConfig;

public class ProductDAO {
	public static SqlSession sqlMapping;
	public ProductDAO () {
		sqlMapping = MyAppSqlConfig.getSqlSessionInstance();
	}
	
	public List<ProductVO> selectLowPrice(int page){
		return sqlMapping.selectList("product.ProductDAO.selectLowPrice", page);
	}

	public List<ProductVO> selectHighPrice(int page) {
		
		return sqlMapping.selectList("product.ProductDAO.selectHighPrice", page);
	}

	public List<ProductVO> selectPName(int page) {
		
		return sqlMapping.selectList("product.ProductDAO.selectPName", page);
	}

	public List<ProductVO> selectPRegDate(int page) {
		
		return sqlMapping.selectList("product.ProductDAO.selectPRegDate", page);
	}
}
