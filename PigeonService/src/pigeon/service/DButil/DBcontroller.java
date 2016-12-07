package pigeon.service.DButil;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBcontroller {
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
        Connection conn=DBcontroller.getConnection();  
        if(conn == null){  
            System.out.println("数据库连接失败！");  
        }  
    } 
    //获取数据库连接  
    public static Connection getConnection(){  
        try{
        	//jdbc:mysql://127.0.0.1:3306/dbNews?useUnicode=true&characterEncoding=utf-8&useSSL=false
        	//jdbc:mysql 与数据库通信所用协议
        	//127.0.0.1:3306 数据库iP和端口号
        	//dbNews 服务器使用的特定数据库
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
  }  