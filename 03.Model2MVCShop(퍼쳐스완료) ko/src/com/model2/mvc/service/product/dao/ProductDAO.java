package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public Product findProduct(int prodNo) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql = "Select * from Product WHERE prod_no = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, prodNo);
		ResultSet rs = pstmt.executeQuery();
		Product product = null;
		while(rs.next()){
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
		}
      con.close();
		
		return product;
	}
	
	public HashMap<String, Object> getProductList(Search search) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT p.prod_no, p.prod_name, p.price, p.reg_date, t.tran_status_code from product p, transaction t "
					+ "WHERE p.prod_no = t.prod_no(+) ";
				
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("")) {
				sql += " AND PROD_NO='" + search.getSearchKeyword()
						+ "'";
			} else if (search.getSearchCondition().equals("0") && !search.getSearchCondition().equals("")) {
				sql += " AND PROD_NAME LIKE '%" + search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchCondition().equals("")){
				sql += " AND PRICE='" + search.getSearchKeyword()
						+ "'";
			}
		}
		sql += " order by PROD_NO ";
		
		int totalCount = this.getTotalCount(sql);
		
		sql = makeCurrentPageSql(sql, search);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Product> list = new ArrayList<Product>();
		

		for(int i = 0; i < search.getPageUnit(); i++){
			while(rs.next()){

			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("price"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(CommonUtil.null2str(rs.getString("tran_status_code")).trim());
			
			list.add(product);
			}
		}

		map.put("totalCount", new Integer(totalCount));
		
		System.out.println("listSize : "+list.size());
		map.put("list",list);
		System.out.println("mapSize : "+map.size());
		
		rs.close();
		pstmt.close();
		con.close();
		
		return map;
	}
	
	public void insertProduct(Product product) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO product VALUES(seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, product.getProdName());
		pstmt.setString(2, product.getProdDetail());
		pstmt.setString(3, CommonUtil.toStrDateStr(product.getManuDate()));
		pstmt.setInt(4, product.getPrice());
		pstmt.setString(5, product.getFileName());
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
	}
	
	public void updateProduct(Product product) throws Exception{
		
		Connection con = DBUtil.getConnection();

		String sql = "update product set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=?,REG_DATE=sysdate WHERE PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setInt(6, product.getProdNo());
		stmt.executeUpdate();
		
		con.close();				
	}
	
		private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
		
	private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageUnit()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageUnit()+1) +" AND "+search.getCurrentPage()*search.getPageUnit();
			
			System.out.println("ProductDAO :: make SQL :: "+ sql);	
			
			return sql;
	}

}
