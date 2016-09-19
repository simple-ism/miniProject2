package product.service;

public class ProductServiceImple implements ProductService {

	@Override
	public void selectProductService() throws Exception {
		dao.selectAll();
		
	}
	
}
