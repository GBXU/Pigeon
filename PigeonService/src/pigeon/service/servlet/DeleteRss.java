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

import com.mysql.jdbc.Connection;

import pigeon.service.DButil.DBcontroller;

/**
 * Servlet implementation class DeleteRss
 */
@WebServlet("/DeleteRss")
public class DeleteRss extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRss() {
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
		//响应post请求
		request.setCharacterEncoding("UTF-8");
		String EM = request.getParameter("EM"); //
        String PW= request.getParameter("PW");//
        String RSSID= request.getParameter("RSSID");//
        
        
        boolean type=false;//用于判断账号和密码是否与数据库中查询结果一致  
    	boolean checkType=false;
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter out = response.getWriter();
        try {  
        	int userid = 0;
            Connection con=(Connection) DBcontroller.getConnection();  
	        String loginSql="SELECT userid FROM user WHERE email=? AND passwd=?";//单引号坑死我了
            PreparedStatement loginPreStmt = con.prepareStatement(loginSql);//句柄
            loginPreStmt.setString(1, EM);  
            loginPreStmt.setString(2, PW);  
	        ResultSet loginRs=loginPreStmt.executeQuery();  
	        if(loginRs.next()) {  
	        	//登录成功
                userid = loginRs.getInt(1);
				String deleteSql="delete from rss where userid=? and websiteid=?";
	        	PreparedStatement deletePreStmt = con.prepareStatement(deleteSql);//句柄
	        	deletePreStmt.setInt(1, userid);  
	        	deletePreStmt.setInt(2, Integer.valueOf(RSSID));
	        	int deleteRs=deletePreStmt.executeUpdate(); 
	        	out.print("成功取消关注"+deleteRs+"个网站");
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
