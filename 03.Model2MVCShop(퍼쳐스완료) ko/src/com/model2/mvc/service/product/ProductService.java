package com.model2.mvc.service.product;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductService {
	
	public void addProduct(Product product) throws Exception;

	public Product getProduct(int prodNo) throws Exception;

	public HashMap<String,Object> getProductList(Search searchVO) throws Exception;

	public void updateProduct(Product product) throws Exception;
	
}