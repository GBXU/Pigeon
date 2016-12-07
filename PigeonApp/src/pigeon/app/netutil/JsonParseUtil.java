package pigeon.app.netutil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;
import pigeon.app.bean.NewsBean;
import pigeon.app.bean.RssBean;
import pigeon.app.bean.UserBean;
import pigeon.app.bean.WorldBean;

public class JsonParseUtil {
	public static List<RssBean> GetRssData(String jsonData){
		List<RssBean> mRssList = new ArrayList<RssBean>();

		try {
			JSONArray mJsonArray = new JSONArray(jsonData);
			JSONObject mJsonObject = new JSONObject(); 
			for (int i = 0; i < mJsonArray.length(); i++) { 

				RssBean bean = new RssBean();
				mJsonObject = mJsonArray.getJSONObject(i);
				bean.setWebsiteId(mJsonObject.getInt("websiteid"));
		        bean.setWebsiteName(mJsonObject.getString("websitename"));
		        bean.setWebsiteUrl(mJsonObject.getString("websiteurl"));
		        mRssList.add(bean);	
		    } 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return mRssList;
	}
	public static List<NewsBean> GetNewsData(String jsonData){
		List<NewsBean> mNewsList = new ArrayList<NewsBean>();

		try {
			JSONArray mJsonArray = new JSONArray(jsonData);
			JSONObject mJsonObject = new JSONObject(); 
			for (int i = 0; i < mJsonArray.length(); i++) { 

				NewsBean bean = new NewsBean();
				mJsonObject = mJsonArray.getJSONObject(i);
				bean.setNewsTitle(mJsonObject.getString("newstitle"));
				bean.setNewsId(Integer.valueOf(mJsonObject.getString("newsid")));
		        bean.setNewsUrl(mJsonObject.getString("newsurl"));
		        bean.setNewsDate(mJsonObject.getString("newsdate"));
		        mNewsList.add(bean);	

		    } 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return mNewsList;
	}
	public static List<WorldBean> GetWorldData(String jsonData){
		List<WorldBean> mWorldList = new ArrayList<WorldBean>();

		try {
			JSONArray mJsonArray = new JSONArray(jsonData);
			JSONObject mJsonObject = new JSONObject(); 
			for (int i = 0; i < mJsonArray.length(); i++) { 

				WorldBean bean = new WorldBean();
				mJsonObject = mJsonArray.getJSONObject(i);
		        bean.setPublishid(Integer.valueOf(mJsonObject.getString("publishid")));
		        bean.setNickname(mJsonObject.getString("nickname"));
		        bean.setMessage(mJsonObject.getString("message"));
		        bean.setTime(mJsonObject.getString("time"));
		        //bean.setMessage(mJsonObject.getString("pic")); 
		        //数据库空，服务器上pic的键值对不会生成，得到的html的String也没有pic标签
		        mWorldList.add(bean);	

		    } 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return mWorldList;
	}
	
	public static List<UserBean> GetStarData(String jsonData){
		List<UserBean> list = new ArrayList<UserBean>();

		try {
			JSONArray mJsonArray = new JSONArray(jsonData);
			JSONObject mJsonObject = new JSONObject(); 
			for (int i = 0; i < mJsonArray.length(); i++) { 
				UserBean bean = new UserBean();
				mJsonObject = mJsonArray.getJSONObject(i);
		        bean.setNickname(mJsonObject.getString("nickname"));
		        bean.setEmail(mJsonObject.getString("email"));
		        bean.setUserId(Integer.valueOf(mJsonObject.getString("userid")));
		        list.add(bean);	

		    } 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}
	public static List<UserBean> GetUserData(String jsonData){
		List<UserBean> list = new ArrayList<UserBean>();

		try {
			JSONArray mJsonArray = new JSONArray(jsonData);
			JSONObject mJsonObject = new JSONObject(); 
			for (int i = 0; i < mJsonArray.length(); i++) { 
				UserBean bean = new UserBean();
				mJsonObject = mJsonArray.getJSONObject(i);
		        bean.setNickname(mJsonObject.getString("nickname"));
		        bean.setEmail(mJsonObject.getString("email"));
		        bean.setPasswd(mJsonObject.getString("passwd"));
		        bean.setUserId(Integer.valueOf(mJsonObject.getString("userid")));
		        list.add(bean);	
		    } 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}
}
