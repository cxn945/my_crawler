package cxn.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Mysql持久化工具
 * @author UPG-cxn 2015年11月17日 
 * @version V1.0
 */
public class MysqlConnInstance {

	private static Connection conn = null;
	private static String url = "jdbc:mysql://localhost:3306/cxn_spider?useUnicode=true&characterEncoding=UTF8";
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			
			System.out.println("成功加载MySQL驱动程序");
			
			conn = DriverManager.getConnection(url, "root", "root");
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			System.err.println("找不到MySQL Driver！"+e.getMessage());
		} catch (SQLException e) {
			System.err.println("建立链接失败！"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static boolean insert(String sql,Object[] params) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(params!=null && params.length>0){
			for(int i=0;i<params.length;i++){
				pstmt.setString(i+1,params[i]==null?"NULL":params[i].toString());
			}
		}
		int result = pstmt.executeUpdate();
		conn.commit();
		if(result!=-1){
			return true;
		}
		return false;
	}
	
	public static void close() throws SQLException{
		if(conn!=null){
			conn.close();
		}
	}
	
}