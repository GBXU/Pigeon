package pigeon.app.config;

import android.content.Context;
import android.util.Log;
import pigeon.app.bean.UserBean;
import pigeon.app.dbutil.DBManager;

public class AppConfig {
	public static int userId;
	public static String email;
	public static String passwd;
	public static String nickname;
	
	public static String GetRss;
	public static String SetRss;
	public static String DeleteRss;
	
	public static String GetDefault;
	
	public static String GetNews;
	
	public static String GetStar;
	public static String SetStar;
	public static String DeleteStar;
	
	public static String GetPublish;
	public static String SetPublish;
	
	public static String SetRefresh;
	public static String Login;
	
	private String RootUrl;
	public AppConfig() {
		
		RootUrl = "·þÎñÆ÷µØÖ·";
		
		GetRss = RootUrl+"GetRss";
		SetRss = RootUrl+"SetRss";
		DeleteRss = RootUrl+"DeleteRss";
		
		GetDefault = RootUrl+"GetDefault";
		
		GetNews = RootUrl+"GetNews";

		GetStar = RootUrl+"GetStar";
		SetStar = RootUrl+"SetStar";
		DeleteStar = RootUrl+"DeleteStar";

		GetPublish = RootUrl+"GetPublish";
		SetPublish = RootUrl+"SetPublish";
		
		SetRefresh = RootUrl+"Refresh";
		
		Login = RootUrl+"Login";
	}
	public void setCurrent(UserBean mUserBean) {
		this.email = mUserBean.getEmail();
		this.passwd = mUserBean.getPasswd();
		this.nickname = mUserBean.getNickname();
		this.userId = mUserBean.getUserId();
	}
	public void clearCurrent() {
		this.email = null;
		this.passwd = null;
		this.nickname = null;
		this.userId = 0;
	}
	public Boolean isLogin(Context context){
		DBManager mDbManager = new DBManager(context);
		UserBean mUserBean = mDbManager.currentUser();
		if (mUserBean == null) {
			return false;
		}
		userId = mUserBean.getUserId();
		email = mUserBean.getEmail();
		passwd = mUserBean.getPasswd();
		nickname =mUserBean.getNickname();
		return true;
	}
	public UserBean getUserBean() {
		UserBean item = new UserBean();
		item.setEmail(email);
		item.setNickname(nickname);
		item.setPasswd(passwd);
		item.setUserId(userId);
		return item;
	}
}
