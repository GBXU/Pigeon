package pigeon.crawler.dbutil;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import pigeon.crawler.bean.WebsiteBean;
public class DBController {
    private static String url="jdbc:mysql://127.0.0.1:3306/dbpigeon?useUnicode=true&characterEncoding=utf-8&useSSL=false"; 
    private static String driverClass="com.mysql.jdbc.Driver";  
    private static String username="账号";  
    private static String password="密码";  
    private static Connection conn;  
    //连接数据库前，装载JDBC驱动  
    static{  
        try{  
        	//实现java.sql.Driver 接口的实体类  此处使用mysql提供的驱动包
            Class.forName(driverClass);  
        }  
        catch(ClassNotFoundException e){  
            e.printStackTrace();  
        }  
    }
    //建立数据库连接  
    public static void main(String[] args){  
        Connection conn=DBController.getConnection();  
        if(conn == null){  
            System.out.println("数据库连接失败！");  
        }  
    } 
    //获取数据库连接  
    public static Connection getConnection(){  
        try{
        	//jdbc:mysql://127.0.0.1:3306/dbpigeon?useUnicode=true&characterEncoding=utf-8&useSSL=false
        	//jdbc:mysql 与数据库通信所用协议
        	//127.0.0.1:3306 数据库iP和端口号
        	//dbpigeon 服务器使用的特定数据库
            conn = (Connection)DriverManager.getConnection(url,username,password); 
        }  
        catch(SQLException e){
            e.printStackTrace();  
        }  
        return conn;  
    }  
    //关闭数据库连接  
    public static void Close(){  
        if(conn!=null){  
            try{  
                conn.close();  
            }  
            catch(SQLException e){  
                e.printStackTrace();  
            }  
        }  
    }
    public static List<String> getDBWebsite(Connection con) {
    	List<String> mUrlList = new ArrayList<String>();
        try {  
            Statement stmt=(Statement) con.createStatement();//句柄
	        String sql="SELECT websiteurl FROM website";
	        ResultSet rs=stmt.executeQuery(sql);  
	        while(rs.next()) {
	        	mUrlList.add(rs.getString(1));
            }  
        }  
        catch(Exception ex) {  
            ex.printStackTrace();  
        }
		return mUrlList;
	}
    public static Boolean setDBNews(Connection con,List<String> mNewsPath,List<String>mNewsTitle,String website) {
    	//Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
    	Date date=new java.util.Date();  
    	String newsDate=sdf.format(date); 
		try {
	    	int websiteid = 0;
	        Statement stmt=(Statement) con.createStatement();//句柄
	        String sql="SELECT websiteid FROM website where websiteurl='"+website+"'";
	        ResultSet rs=stmt.executeQuery(sql);  
	        if(rs.next()) {
	        	websiteid = rs.getInt(1);
	        }  
	    	
	        String selectSql="select * from news where newsurl= ? and websiteid= ? ";
			PreparedStatement selectPreStmt = con.prepareStatement(selectSql);

	    	String insertSql="insert into news(newsurl,websiteid,newsdate,newstitle) values(?,?,?,?)";
			PreparedStatement insertPreStmt=con.prepareStatement(insertSql); //句柄 
			
			for(int i=0;i<mNewsPath.size();i++){
        		selectPreStmt.setString(1,mNewsPath.get(i));
        		selectPreStmt.setString(2,String.valueOf(websiteid));
        		ResultSet rs1=selectPreStmt.executeQuery();
        		if (rs1.next()) {
					System.out.println("existed");//新闻已经存在
				}else {
					insertPreStmt.setString(1, mNewsPath.get(i));  
	        		insertPreStmt.setInt(2, websiteid);  
	        		insertPreStmt.setString(3, newsDate);  
	        		insertPreStmt.setString(4, mNewsTitle.get(i));
	        		insertPreStmt.executeUpdate();  
				}
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}//句柄
        
		return true;
	}
}
