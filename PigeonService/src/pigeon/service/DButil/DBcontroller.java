package pigeon.service.DButil;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBcontroller {
    private static String url="jdbc:mysql://127.0.0.1:3306/dbpigeon?useUnicode=true&characterEncoding=utf-8&useSSL=false"; 
    private static String driverClass="com.mysql.jdbc.Driver";  
    private static String username="�˺�";  
    private static String password="����";  
    private static Connection conn;  
    //�������ݿ�ǰ��װ��JDBC����  
    static{  
        try{  
        	//ʵ��java.sql.Driver �ӿڵ�ʵ����  �˴�ʹ��mysql�ṩ��������
            Class.forName(driverClass);  
        }  
        catch(ClassNotFoundException e){  
            e.printStackTrace();  
        }  
    }
    //�������ݿ�����  
    public static void main(String[] args){  
        Connection conn=DBcontroller.getConnection();  
        if(conn == null){  
            System.out.println("���ݿ�����ʧ�ܣ�");  
        }  
    } 
    //��ȡ���ݿ�����  
    public static Connection getConnection(){  
        try{
        	//jdbc:mysql://127.0.0.1:3306/dbNews?useUnicode=true&characterEncoding=utf-8&useSSL=false
        	//jdbc:mysql �����ݿ�ͨ������Э��
        	//127.0.0.1:3306 ���ݿ�iP�Ͷ˿ں�
        	//dbNews ������ʹ�õ��ض����ݿ�
            conn = (Connection)DriverManager.getConnection(url,username,password); 
        }  
        catch(SQLException e){
            e.printStackTrace();  
        }  
        return conn;  
    }  
    //�ر����ݿ�����  
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