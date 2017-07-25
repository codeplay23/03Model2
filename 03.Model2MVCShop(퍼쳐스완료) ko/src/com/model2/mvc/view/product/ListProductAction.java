package com.model2.mvc.view.product;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	public ListProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Search search = new Search();

		int currentPage = 1;
		
		if(request.getParameter("currentPage") != null && !(request.getParameter("currentPage")).equals("")){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("이번에 눌린 커런트 페이지"+currentPage);
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		System.out.println("리프액 서치컨디션"+request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		System.out.println("리프액 서치키워드"+request.getParameter("searchKeyword"));
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageUnit(pageUnit);
		
		ProductService service = new ProductServiceImpl();
		
		
		HashMap<String, Object> map = service.getProductList(search);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(),
						pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
	
		request.setAttribute("list",map.get("list"));
		System.out.println("리스트프로덕트액션"+map.get("list"));
		request.setAttribute("search",search);
		request.setAttribute("resultPage", resultPage);
		
		return "forward:/product/listProduct.jsp";
	}

}
