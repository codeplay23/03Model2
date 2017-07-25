package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}

	@Override
	public void addProduct(Product product) throws Exception {
		productDAO.insertProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	@Override
	public HashMap<String, Object> getProductList(Search searchVO) throws Exception {
		return productDAO.getProductList(searchVO);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDAO.updateProduct(product);
	}

}
