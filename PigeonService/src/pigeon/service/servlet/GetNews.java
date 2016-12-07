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
 * Servlet implementation class GetNews
 */
@WebServlet("/GetNews")
public class GetNews extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNews() {
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
        String WEBSITEID= request.getParameter("WEBSITEID");
        int PAGE= Integer.valueOf(request.getParameter("PAGE")).intValue();
        
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
	        			
	        	String NewsListSql ="select newsid,newstitle,newsurl,newsdate from news where websiteid ='"
	        						+ WEBSITEID+ "' "
	        						+ "order by newsid desc "
	        						+ "limit "
	        						+ PAGE
	        						+ ", "
	        						+ (PAGE+1)*10;
	        	
	        	ResultSet NewsListRs=stmt.executeQuery(NewsListSql);
	        	JSONArray mJsonArray = new JSONArray();
	        	while (NewsListRs.next()) {
		            JSONObject obj=new JSONObject();
		            obj.put("newsid",NewsListRs.getString(1));
		            obj.put("newstitle",NewsListRs.getString(2));
		            obj.put("newsurl",NewsListRs.getString(3));
		            obj.put("newsdate",NewsListRs.getString(4));
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
