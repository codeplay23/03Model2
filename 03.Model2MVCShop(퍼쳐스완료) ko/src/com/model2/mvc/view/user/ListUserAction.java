package com.model2.mvc.view.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class ListUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		Search search=new Search();
		
		int currentPage=1;
		if(request.getParameter("currentPage") != null && !request.getParameter("currentPage").equals("") ){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		System.out.println("searchCondition::"+request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		System.out.println("searchKeyword::"+request.getParameter("searchKeyword"));
		
		int pageUnit=Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		int pageSize=Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		search.setPageUnit(pageUnit);
		
		
		
		UserService service=new UserServiceImpl();
		HashMap<String,Object> map=service.getUserList(search);

		Page resultPage = new Page(currentPage, (Integer)map.get("count"), pageUnit, pageSize);
				
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		
		return "forward:/user/listUser.jsp";
	}
}