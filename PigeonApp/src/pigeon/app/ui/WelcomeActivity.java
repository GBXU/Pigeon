package pigeon.app.ui;

import android.app.Activity;

//baseActivity֪����ʲô�  Bundle��������  ������÷�ʽ  ��ʱ�˳�  ����� 


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.R;

public class WelcomeActivity extends BaseActivity {
	private AppConfig appConfig = new AppConfig();
	private static final int SHOW_TIME_MIN = 3000;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_welcome);
	
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				long startTime = System.currentTimeMillis();
				Boolean isLogined = appConfig.isLogin(WelcomeActivity.this);//������ݿ�
				HttpUtil.sendGet(appConfig.SetRefresh,"UTF-8");
				long loadingTime = System.currentTimeMillis() - startTime;
				if (loadingTime < SHOW_TIME_MIN) {
					try {
						Thread.sleep(SHOW_TIME_MIN - loadingTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				return isLogined;
			}
			protected void onPostExecute(Boolean isLogined) {
				if(isLogined){
					//�е�¼ ����
					//��¼ҳ��
					Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);// ���������ֱ��ʾ����Ķ���,�˳��Ķ���
				}else {
					Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);// ���������ֱ��ʾ����Ķ���,�˳��Ķ���
				}
			}
		}.execute(new Void[] {});
	}


}
//http://www.jb51.net/article/36190.htm
