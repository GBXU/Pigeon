package pigeon.app.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import pigeon.app.bean.UserBean;
import pigeon.app.config.AppConfig;
import pigeon.app.dbutil.DBManager;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.JsonParseUtil;
import pigeon.app.netutil.R;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private AppConfig appConfig = new AppConfig();
	private EditText editTextEmail;
	private EditText editTextPasswd;
	private Button buttonLogin;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		editTextEmail = (EditText)findViewById(R.id.layout_login_email);
		editTextPasswd = (EditText)findViewById(R.id.layout_login_passwd);
		buttonLogin = (Button)findViewById(R.id.layout_login_button);
		buttonLogin.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_login_button:
			String email = editTextEmail.getText().toString();
			String passwd = editTextPasswd.getText().toString();
			new MyAsyncTaskLogin().execute(email,passwd);
			break;
		default:
			break;
		}
	}
	//�����������̨���ȡ�������
	public class MyAsyncTaskLogin extends AsyncTask<String, String, List<UserBean>>{
		//��onPreExecute��ִ�У����ղ����������� 
		
		protected List<UserBean> doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",str[0]);
			mParams.put("PW",str[1]);
			
			String mJsonData = HttpUtil.sendPost(appConfig.Login,mParams);
			List<UserBean> mUserList = JsonParseUtil.GetUserData(mJsonData);
			return mUserList;
		}
		@Override //��̨����������ִ�У�֮ǰ�ļ������ǲ������ɽ���UI����
		protected void onPostExecute(List<UserBean> mUserList) {
			if(mUserList.size()>0){
				appConfig.setCurrent(mUserList.get(0));
				DBManager mDbManager = new DBManager(LoginActivity.this);
				mDbManager.insertUser(mUserList);
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);// ���������ֱ��ʾ����Ķ���,�˳��Ķ���

			}else {
				Toast.makeText(LoginActivity.this, "��¼ʧ��", Toast.LENGTH_LONG).show();
			}
		}
	}
}
