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
        Connection conn=DBController.getConnection();  
        if(conn == null){  
            System.out.println("���ݿ�����ʧ�ܣ�");  
        }  
    } 
    //��ȡ���ݿ�����  
    public static Connection getConnection(){  
        try{
        	//jdbc:mysql://127.0.0.1:3306/dbpigeon?useUnicode=true&characterEncoding=utf-8&useSSL=false
        	//jdbc:mysql �����ݿ�ͨ������Э��
        	//127.0.0.1:3306 ���ݿ�iP�Ͷ˿ں�
        	//dbpigeon ������ʹ�õ��ض����ݿ�
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
    public static List<String> getDBWebsite(Connection con) {
    	List<String> mUrlList = new ArrayList<String>();
        try {  
            Statement stmt=(Statement) con.createStatement();//���
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
	        Statement stmt=(Statement) con.createStatement();//���
	        String sql="SELECT websiteid FROM website where websiteurl='"+website+"'";
	        ResultSet rs=stmt.executeQuery(sql);  
	        if(rs.next()) {
	        	websiteid = rs.getInt(1);
	        }  
	    	
	        String selectSql="select * from news where newsurl= ? and websiteid= ? ";
			PreparedStatement selectPreStmt = con.prepareStatement(selectSql);

	    	String insertSql="insert into news(newsurl,websiteid,newsdate,newstitle) values(?,?,?,?)";
			PreparedStatement insertPreStmt=con.prepareStatement(insertSql); //��� 
			
			for(int i=0;i<mNewsPath.size();i++){
        		selectPreStmt.setString(1,mNewsPath.get(i));
        		selectPreStmt.setString(2,String.valueOf(websiteid));
        		ResultSet rs1=selectPreStmt.executeQuery();
        		if (rs1.next()) {
					System.out.println("existed");//�����Ѿ�����
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
		}//���
        
		return true;
	}
}
