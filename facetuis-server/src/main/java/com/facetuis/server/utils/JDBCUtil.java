package com.facetuis.server.utils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtil {
	public static void main(String[] args) {
		getTableName() ;
	}
	/*
	 * 获取所有表名
	 */
	public static List<String> getTableName() {	
		List<String> list=new ArrayList<String>();
		Connection conn=DBUtil.getConn(); //获取连接对象
		try {
	        String sql = "select table_name from information_schema.tables where table_type='base table' and table_schema='facetuis'";
			//String sql = "select * from mz";
	        PreparedStatement  prst = (PreparedStatement) conn.prepareStatement(sql);
			// 4.Statement->ResultSet
			ResultSet rs = prst.executeQuery(sql); 
	        // 5.通过ResultSet获取数据
	        while (rs.next()) {
	        	System.out.println(
	        			"      <table tableName=\""+rs.getString("table_name")+"\"   enableCountByExample=\"false\" enableUpdateByExample=\"false\"  enableDeleteByExample=\"false\" enableSelectByExample=\"false\" selectByExampleQueryId=\"false\">        	\r\n" +
	        			"        	 <property name=\"useActualColumnNames\" value=\"true\"/><!-- 不用驼峰原则  生成的字段带下划线 -->\r\n" +
	        			"      </table>");
//	        	String str=rs.getString("mz_id");
//	        	if(str.length()==1) {
//	        		str="0"+str;
//	        	}
//	        	System.out.println(" m.put(\""+str+"\", \""+rs.getString("mzname").trim()+"\");");
	        	//list.add(rs.getString("table_name"));
	        }	
	        DBUtil.close(rs, prst, conn);  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;		      
	}
	/*
	 * 给表添加列
	 */
	public static void UpdateColumn() {
		try { 
			Connection conn=DBUtil.getConn(); //获取连接对象
			// comment 会变乱码 default '1' comment '用户类型(1：普通用户，2：会员)'
			// String sql = "alter table "+string+" add column is_use int(1) default 1";	
			// String sql = "alter table "+string+" drop column is_use";
			List<String> list=getTableName();//库中所有的表
			for (String string : list) {
				String sql = "alter table "+string+" add column is_use int(1) default 1";	            
				PreparedStatement prst=(PreparedStatement) conn.prepareStatement(sql);
				prst.executeUpdate();
	            DBUtil.close(prst);
			}   
			DBUtil.close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}          
	}
	/*
	 * 批量添加数据
	 */
	public static void inset() {
		Connection conn=DBUtil.getConn(); //获取连接对象
		try {	        
	        conn.setAutoCommit(false);  // 关闭事务自动提交  
	        PreparedStatement prst =(PreparedStatement) conn.prepareStatement("insert into test1(a,b) values (?,1)");  
	        for (int i = 0; i < 10000; i++){  
	            prst.setInt(1, i);             
	            prst.addBatch();  	// 把一个SQL命令加入命令列表  
	        }  	       
	        prst.executeBatch();   	// 执行批量更新  	        
	        conn.commit();  		// 语句执行完毕，提交本事务  
	        DBUtil.close(prst,conn);//关闭流
		} catch (SQLException e) {
			e.printStackTrace();
		}          
	}
}
/**
 * 获取连接用到
 * @author Administrator
 *
 */
class  DBUtil {
    private final static String URL="jdbc:mysql://42.51.224.88:3306/facetuis";  //连接格式jdbc:mysql://(ip地址):（端口）/(数据库名)
    private final static String NAME="root";        //mysql用户名
    private final static String PASS="FACETUIS.COM";    //对应的密码
    private static Connection conn=null;        	   //数据库连接对象  
    
    /**
     * 
     *@Title:DBUtil
     *@Description:
     */
    public DBUtil(){}
    
    /**
     * 
     * @Tiltle getConn
     * @return Connection
     * @Description:返回连接
     */
    public static Connection getConn(){
        //告诉jvm使用mysql
        try {
            //加载驱动，string为驱动名字
            Class.forName("com.mysql.jdbc.Driver");
            //连接数据库，得到Connection连接
            conn=(Connection) DriverManager.getConnection(URL,NAME,PASS);   //DriverManager,    初始化驱动
            //System.out.println("连接数据库：  "+conn);
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }    
    //关闭结果对象集
    public static void close(ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }    
    //关闭编译语句对象
    public static void close(PreparedStatement prst){
        if(prst!=null){
            try{
                prst.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }   
    //关闭结果对象集
    public static void close(Connection conn){
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }    
    //对于更新操作关闭资源
    public static void close(PreparedStatement prst,Connection conn){
        close(prst);
        close(conn);
    }
    
    //关闭所有
    public static void close(ResultSet rs,PreparedStatement prst,Connection conn){
        close(rs);
        close(prst);
        close(conn);
    }
}