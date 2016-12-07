package pigeon.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import pigeon.app.bean.UserBean;
import pigeon.app.config.AppConfig;
import pigeon.app.dbutil.DBHelper;
import pigeon.app.dbutil.DBManager;
import pigeon.app.netutil.R;

public class ProfileActivity extends BaseActivity{
	private AppConfig mAppConfig = new AppConfig();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profile);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			ProfileActivity.this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		Button button = (Button)findViewById(R.id.button_logout);
		button.setOnClickListener(new OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				Toast.makeText(ProfileActivity.this, "ÍË³ö³É¹¦", Toast.LENGTH_SHORT).show();
				List<UserBean> list = new ArrayList<UserBean>();
				list.add(mAppConfig.getUserBean());
				DBManager mDbManager = new DBManager(ProfileActivity.this);
				mDbManager.deleteUser(list);
				mAppConfig.clearCurrent();	

				ActivityCollector.finishAll();
				ActivityCollector.finishAllFragmentActivity();
				finish();
				
			}
		});
		EditText editTextNick= (EditText)findViewById(R.id.layout_profile_nick);
		editTextNick.setHint(mAppConfig.getUserBean().getNickname());
		EditText editTextEmail= (EditText)findViewById(R.id.layout_profile_email);
		editTextEmail.setHint(mAppConfig.getUserBean().getEmail());
		
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
