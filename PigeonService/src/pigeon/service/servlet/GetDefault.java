package pigeon.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
 * Servlet implementation class GetDefault
 */
@WebServlet("/GetDefault")
public class GetDefault extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDefault() {
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
	        	String FriendSql = "select friendid from star "
								 + "WHERE userid='" + userid+ "'";
	        	Statement friendstmt=(Statement) con.createStatement();//句柄
	        	ResultSet FriendRs=friendstmt.executeQuery(FriendSql);
    			Map<Integer, Integer> rss=new HashMap<Integer, Integer>();
    			int friendid;
	        	while(FriendRs.next()) {
	        		friendid = FriendRs.getInt(1);
	        		if(friendid == userid){
	        			continue;
	        		}
	        		String RssListSql = "SELECT websiteid FROM rss "
    						+ "WHERE userid='" + friendid+ "' "
    						+ "order by websiteid desc ";
		        	ResultSet RssListRs=stmt.executeQuery(RssListSql);
	        		while(RssListRs.next()) {
	        			int websiteid = RssListRs.getInt(1);
	        			if(rss.containsKey(Integer.valueOf(websiteid))){
	        				rss.put(websiteid, rss.get(websiteid)+1);
	        			}else {
	        				rss.put(websiteid, 1);
						}
					}
	        	}
	        	Set<Integer> websiteids = rss.keySet();
	        	for(Integer webid:websiteids){
	        		if(rss.get(webid)<1){
	        			websiteids.remove(webid);
	        		}
	        	}
	        	String rssset = "";
	        	for(Integer webid:websiteids){
	        		rssset =rssset+ webid+",";
	        	}
	        	if (rssset.length()>1) {
		        	rssset = rssset.substring(0, rssset.length()-1);
				}
	        	String DefaultListSql ="select websiteid,websiteurl,websitename from website "
	        					+ "where websiteid in "
	        						+ "("
	        						+ rssset
	        						+ ")";
	        	Statement defaultstmt=(Statement) con.createStatement();//句柄
	        	ResultSet DefaultListRs=defaultstmt.executeQuery(DefaultListSql);
	        	JSONArray mJsonArray = new JSONArray();
	        	while (DefaultListRs.next()) {
		            JSONObject obj=new JSONObject();
		            obj.put("websiteid",DefaultListRs.getString(1));
		            obj.put("websiteurl",DefaultListRs.getString(2));
		            obj.put("websitename",DefaultListRs.getString(3));
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
