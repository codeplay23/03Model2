package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	public GetProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String prodNo=request.getParameter("prodNo");
		String menu = request.getParameter("menu");
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					cookie = new Cookie("history", cookies[i].getValue()+","+prodNo);
				}else{
					cookie = new Cookie("history", prodNo);
				}
			}
		}
		
		cookie.setMaxAge(60*5);
		response.addCookie(cookie);
					
		
		ProductService service=new ProductServiceImpl();
		Product product= service.getProduct(Integer.parseInt(prodNo));
		request.setAttribute("product", product);
		
		if(menu!=null){
			if(menu.equals("manage")){
				return "forward:/updateProductView.do";		
			}
		}
		return "forward:/product/getProduct.jsp";
	}

}
