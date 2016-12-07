package pigeon.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import jdk.nashorn.internal.ir.Flags;
import pigeon.service.DButil.DBcontroller;

/**
 * Servlet implementation class SetRss
 */
@WebServlet("/SetRss")
public class SetRss extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetRss() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��Ӧpost����
		request.setCharacterEncoding("UTF-8");
		String EM = request.getParameter("EM"); //
        String PW= request.getParameter("PW");//
        String TITLE= request.getParameter("TITLE");//
        String RSS= request.getParameter("RSS");//
        
        
        boolean type=false;//�����ж��˺ź������Ƿ������ݿ��в�ѯ���һ��  
    	boolean checkType=false;
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter out = response.getWriter();
        try {  
        	int userid = 0;
            Connection con=(Connection) DBcontroller.getConnection();  
	        String loginSql="SELECT userid FROM user WHERE email=? AND passwd=?";//�����ſ�������
            PreparedStatement loginPreStmt = con.prepareStatement(loginSql);//���
            loginPreStmt.setString(1, EM);  
            loginPreStmt.setString(2, PW);  
	        ResultSet loginRs=loginPreStmt.executeQuery();  
	        if(loginRs.next()) {  
	        	//��¼�ɹ�
                userid = loginRs.getInt(1);
	        	//�����ַ
	        	String checkSql="select websiteid from website where websiteurl=?";
	        	PreparedStatement checkPreStmt = con.prepareStatement(checkSql);//���
	        	checkPreStmt.setString(1, RSS);  
	        	ResultSet checkRs=checkPreStmt.executeQuery(); 
	        	if(checkRs.next()){
	        		//��վ�Ѿ�����
		        	out.print("��ַ�Ѵ���");
	        		//����ע
	        		String checkSql1="select * from rss where userid=? and websiteid=?";
		        	PreparedStatement checkPreStmt1 = con.prepareStatement(checkSql1);//���
		        	checkPreStmt1.setInt(1, userid);  
		        	checkPreStmt1.setInt(2, checkRs.getInt(1));
		        	ResultSet checkRs1=checkPreStmt1.executeQuery(); 
		        	if(checkRs1.next()) {
						//�ѹ�ע
			        	out.print("�ѹ�ע");
					}else {
						//δ��ע
						String insertSql="insert into rss(userid,websiteid) values(?,?)";
			        	PreparedStatement insertPreStmt = con.prepareStatement(insertSql);//���
			        	insertPreStmt.setInt(1, userid);  
			        	insertPreStmt.setInt(2, checkRs.getInt(1));
			        	int insertRs=insertPreStmt.executeUpdate(); 
			        	out.print("�ɹ���ע"+insertRs+"����վ");
					}
	        		
	        	}else{
	        		//��ַ������
					String insertSql="insert into website(websiteurl,websitename) values(?,?)";
		        	PreparedStatement insertPreStmt = con.prepareStatement(insertSql);//���
		        	insertPreStmt.setString(1, RSS);  
		        	insertPreStmt.setString(2, TITLE);
		        	int insertRs=insertPreStmt.executeUpdate(); 
		        	out.print("�ɹ����"+insertRs+"����վ");
		        	
		        	String checkSql1="select websiteid from website where websiteurl=?";
		        	PreparedStatement checkPreStmt1 = con.prepareStatement(checkSql1);//���
		        	checkPreStmt1.setString(1, RSS);  
		        	ResultSet checkRs1=checkPreStmt1.executeQuery(); 
		        	
		        	if (checkRs1.next()) {
						String insertSql1="insert into rss(userid,websiteid) values(?,?)";
			        	PreparedStatement insertPreStmt1 = con.prepareStatement(insertSql1);//���
			        	insertPreStmt1.setInt(1, userid);  
			        	insertPreStmt1.setInt(2, checkRs1.getInt(1));
			        	int insertRs1=insertPreStmt1.executeUpdate(); 
			        	out.print("�ɹ���ע"+insertRs1+"����վ");
					}
	        	}
	        }
        }  
        catch(Exception ex) {  
            ex.printStackTrace();  
        }  
        finally {  
            DBcontroller.Close();  
            out.flush();
            out.close();
        }
	}

}
