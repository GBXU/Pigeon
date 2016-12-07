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
 * Servlet implementation class GetPublish
 */
@WebServlet("/GetPublish")
public class GetPublish extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPublish() {
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
		String EM = request.getParameter("EM"); //���ڽ���ǰ�������EMֵ���˴��������input�ؼ���nameֵһ��  
        String PW= request.getParameter("PW");//���ڽ���ǰ�������PWֵ���˴��������input�ؼ���nameֵһ��  
        int PAGE= Integer.valueOf(request.getParameter("PAGE")).intValue();
        
        boolean type=false;//�����ж��˺ź������Ƿ������ݿ��в�ѯ���һ��  
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter out = response.getWriter();
        try {  
        	int userid = 0;
            Connection con=(Connection) DBcontroller.getConnection();  
            Statement stmt=(Statement) con.createStatement();//���
	        String loginSql="SELECT userid FROM dbpigeon.user WHERE email="+ "'" + EM+ "'"+ " AND passwd="+ "'" +PW+ "'";//�����ſ�������
	        ResultSet loginRs=stmt.executeQuery(loginSql);  
	        while(loginRs.next()) {  
                type=true;
                userid = loginRs.getInt(1);
            } 
	        if(type){
	        	String publishListSql =
	        			"select publishid,nickname,message,time,pic from user,publish "
	        			+"where (publish.userid in (SELECT friendid FROM star WHERE star.userid='" + userid + "'))"
			        	+"and user.userid = publish.userid "
			        	+ "order by publishid desc "
						+ " limit "
						+ PAGE
						+ ","
						+ (PAGE+1)*10+";";
	        	ResultSet publishListRs=stmt.executeQuery(publishListSql);
	        	JSONArray mJsonArray = new JSONArray();
	        	while (publishListRs.next()) {
		            JSONObject obj=new JSONObject();
		            obj.put("publishid",publishListRs.getString(1));
		            obj.put("nickname",publishListRs.getString(2));
		            obj.put("message",publishListRs.getString(3));
		            obj.put("time",publishListRs.getString(4));
		            obj.put("pic",publishListRs.getString(5));
		            
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
