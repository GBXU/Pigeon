package pigeon.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import pigeon.service.DButil.DBcontroller;

/**
 * Servlet implementation class SetPublish
 */
@WebServlet("/SetPublish")
public class SetPublish extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPublish() {
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
        String MESSAGE= request.getParameter("MESSAGE");//
        
        
        boolean type=false;//�����ж��˺ź������Ƿ������ݿ��в�ѯ���һ��  
    	
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter out = response.getWriter();
        try {  
        	int userid = 0;
            Connection con=(Connection) DBcontroller.getConnection();  
            String loginSql="SELECT userid FROM user WHERE email="+ "'" + EM+ "'"+ " AND passwd="+ "'" +PW+ "';";//�����ſ�������
            Statement loginPreStmt=(Statement) con.createStatement();//���
	        ResultSet loginRs=loginPreStmt.executeQuery(loginSql); 

	        if(loginRs.next()) {  
                type=true;
                userid = loginRs.getInt(1);
        		Timestamp d = new Timestamp(System.currentTimeMillis()); 
	        	String insertSql="insert into publish(userid,message,time) values(?,?,?)";
	        	PreparedStatement insertPreStmt = con.prepareStatement(insertSql);//���
	        	insertPreStmt.setInt(1, userid);  
	        	insertPreStmt.setString(2, MESSAGE); 
	        	insertPreStmt.setTimestamp(3, d); 
	        	int Rs=insertPreStmt.executeUpdate();
	        	out.print("�ɹ�����"+Rs+"����Ϣ");
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
