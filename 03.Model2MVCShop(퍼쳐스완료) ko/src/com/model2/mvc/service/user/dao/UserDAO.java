package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;


public class UserDAO {
	
	public UserDAO(){
	}

	public void insertUser(User userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO users VALUES (?,?,?,'user',?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserId());
		stmt.setString(2, userVO.getUserName());
		stmt.setString(3, userVO.getPassword());
		stmt.setString(4, userVO.getSsn());
		stmt.setString(5, userVO.getPhone());
		stmt.setString(6, userVO.getAddr());
		stmt.setString(7, userVO.getEmail());
		stmt.executeUpdate();
		
		con.close();
	}

	public User findUser(String userId) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM users WHERE user_id = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userId);

		ResultSet rs = stmt.executeQuery();
		User user = null;
		while (rs.next()) {
			user = new User();
			user.setUserId(rs.getString("USER_ID"));
			user.setUserName(rs.getString("USER_NAME"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setRole(rs.getString("ROLE"));
			user.setSsn(rs.getString("SSN"));
			user.setPhone(rs.getString("CELL_PHONE"));
			user.setAddr(rs.getString("ADDR"));
			user.setEmail(rs.getString("EMAIL"));
			user.setRegDate(rs.getDate("REG_DATE"));
			System.out.println(user+"<===유저정보 of UserDAO");
		}
		
		
		con.close();

		return user;
	}

	public HashMap<String,Object> getUserList(Search search) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM users ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " WHERE user_id LIKE'%" + search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " WHERE user_name='" + search.getSearchKeyword()
						+ "'";
			}
		}
		sql += " ORDER BY user_id";

		int totalCount = getTotalCount(sql);
		
		sql = makeCurrentPage(sql, search);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println("로우의 수:" + totalCount);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(totalCount));

		ArrayList<User> list = new ArrayList<User>();

		while(rs.next()){
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			user.setSsn(rs.getString("ssn"));
			user.setPhone(rs.getString("cell_phone"));
			user.setAddr(rs.getString("addr"));
			user.setEmail(rs.getString("email"));
			user.setRegDate(rs.getDate("reg_date"));

			list.add(user);
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}

	public void updateUser(User userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE users SET user_name=?,cell_phone=?,addr=?,email=? WHERE user_id=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserName());
		stmt.setString(2, userVO.getPhone());
		stmt.setString(3, userVO.getAddr());
		stmt.setString(4, userVO.getEmail());
		stmt.setString(5, userVO.getUserId());
		stmt.executeUpdate();
		
		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception{
		Connection con = DBUtil.getConnection();
		
		sql = "SELECT count(*) FROM ("+sql+") countTable";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		int totalCount = 0;
		
		if(rs.next()){
			totalCount = rs.getInt(1);
		}
		
		con.close();
		pstmt.close();
		rs.close();
		return totalCount;
		
	}
	
	private String makeCurrentPage(String sql, Search search) {
		sql = "SELECT * " 
				+"FROM(SELECT ROWNUM rn, inner_table.* "
					+"FROM ("+sql+ ") inner_table "
					+"WHERE ROWNUM <="+search.getCurrentPage()*search.getPageUnit()+") "
					+"WHERE rn BETWEEN "
					+((search.getCurrentPage()-1)*(search.getPageUnit())+1)
					+" AND "+(search.getCurrentPage()*search.getPageUnit()); 
		System.out.println(search.getCurrentPage());
		System.out.println(search.getPageUnit());
		return sql;
		
	}
}