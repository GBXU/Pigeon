package pigeon.app.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.R;

public class AddPublishActivity extends BaseActivity{
	private AppConfig appConfig = new AppConfig();
	private EditText editText;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feedback);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			AddPublishActivity.this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		Button button = (Button)findViewById(R.id.button_feedback);
		editText = (EditText)findViewById(R.id.editText_feedback);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String message = editText.getText().toString();
				new MyAsyncTaskAddPublish().execute(message);
				
			}
		});
	}
	//�����������̨���ȡ�������
	public class MyAsyncTaskAddPublish extends AsyncTask<String, String, String>{
		//��onPreExecute��ִ�У����ղ����������� 
		protected String doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			mParams.put("MESSAGE",str[0]);
			
			String result = HttpUtil.sendPost(appConfig.SetPublish,mParams);
			return result;

		}
		@Override //��̨����������ִ�У�֮ǰ�ļ������ǲ������ɽ���UI����
		protected void onPostExecute(String result) {
			Toast.makeText(AddPublishActivity.this, result, Toast.LENGTH_SHORT);
			finish();
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
