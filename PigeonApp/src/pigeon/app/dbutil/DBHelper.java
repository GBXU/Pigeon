package pigeon.app.dbutil;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import pigeon.app.bean.RssBean;
import pigeon.app.bean.UserBean;
import pigeon.app.config.AppConfig;

public class DBHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "dbpigeonapp";
	private static final int VERSION = 1;
	
	private Context mContext;
	private static final String CREATE_USER="create table user("
			+"userId int primary key,"
			+"email varchar(100),"
			+"passwd varchar(100),"
			+"nickname varchar(100)"
			+")";

	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}


	public void insertUser(List<UserBean> list) {
		
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < list.size(); i++){
			db.execSQL("insert into user(userId,email,passwd,nickname) values("
					+ "'"+list.get(i).getUserId() +"'"
					+ ","
					+ "'"+list.get(i).getEmail() +"'"
					+ ","
					+ "'"+list.get(i).getPasswd() +"'"
					+ ","
					+ "'"+list.get(i).getNickname() +"'"
					+ ")");
		}
	}
	public UserBean queryUser() {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select userId,email,passwd,nickname "
				+ "from user", null);
		UserBean item = null;
		if (cursor.moveToNext()) {
			item = new UserBean();
			item.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
			item.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			item.setPasswd(cursor.getString(cursor.getColumnIndex("passwd")));
			item.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
		}
		cursor.close();
		return item;		
	}
	public void deleteUser(List<UserBean> list) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < list.size(); i++){
			db.execSQL("delete from user where userId =" + "'" +list.get(i).getUserId()+ "'");
		}
	}
}
