package pigeon.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import pigeon.service.DButil.DBcontroller;

/**
 * Servlet implementation class GetStar
 */
@WebServlet("/GetStar")
public class GetStar extends HttpServlet {
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStar() {
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

        //响应post请求
		String EM = request.getParameter("EM"); //用于接收前段输入的EM值，此处参数须和input控件的name值一致  
        String PW= request.getParameter("PW");//用于接收前段输入的PW值，此处参数须和input控件的name值一致  


        boolean type=false;//用于判断账号和密码是否与数据库中查询结果一致  
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter out = response.getWriter();
        try {  
        	int userid = 0;
            Connection con=(Connection) DBcontroller.getConnection();  
            Statement stmt=(Statement) con.createStatement();//句柄
            String loginSql="SELECT userid FROM user WHERE email="+ "'" + EM+ "'"+ " AND passwd="+ "'" +PW+ "'";//单引号坑死我了
	        ResultSet loginRs=stmt.executeQuery(loginSql);  
	        while(loginRs.next()) {  
                type=true;
                userid = loginRs.getInt(1);
            } 
	        if(type){
	        	//
	        			
	        	String RssListSql ="select userid,email,nickname from user "
	        					+ "where userid in "
	        						+ "(SELECT friendid FROM star "
	        						+ "WHERE userid='" + userid+ "')"
	        						+ "order by userid desc ";
	        	
	        	ResultSet RssListRs=stmt.executeQuery(RssListSql);
	        	JSONArray mJsonArray = new JSONArray();
	        	while (RssListRs.next()) {
		            JSONObject obj=new JSONObject();
		            obj.put("userid",RssListRs.getString(1));
		            obj.put("email",RssListRs.getString(2));
		            obj.put("nickname",RssListRs.getString(3));
		            mJsonArray.put(obj);
				}
	            out.print(mJsonArray);
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
