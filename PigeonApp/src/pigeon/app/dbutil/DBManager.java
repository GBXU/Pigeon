package pigeon.app.dbutil;

import java.util.List;

import android.content.Context;
import pigeon.app.bean.UserBean;

public class DBManager {
	private Context mContext;
	private static DBHelper mDbHelper;
	public DBManager(Context appContext){
		this.mContext = appContext;
		mDbHelper = new DBHelper(this.mContext);
	}
	public void insertUser(List<UserBean> list) {
		mDbHelper.deleteUser(list);
		mDbHelper.insertUser(list);
	}
	public void deleteUser(List<UserBean> list) {
		mDbHelper.deleteUser(list);
		}
	public UserBean currentUser() {
		UserBean current;
		current = mDbHelper.queryUser();
		return current;
	}
}
