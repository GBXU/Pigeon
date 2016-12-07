package pigeon.app.ui;


import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.R;

public class AddRssActivity extends BaseActivity implements OnClickListener{
	private AppConfig appConfig = new AppConfig();
	private EditText editTextUrl;
	private EditText editTextTitle;
	private Button button_browser ;
	private Button button_apply ;
	private WebView webView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_addrss);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			AddRssActivity.this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		webView = (WebView)findViewById(R.id.layout_webpage_webview);
		editTextUrl = (EditText)findViewById(R.id.edittext_addrss);
		editTextTitle = (EditText)findViewById(R.id.edittext_addrsstitle);
		button_browser = (Button)findViewById(R.id.button_browser);
		button_browser.setOnClickListener(this);
		button_apply = (Button)findViewById(R.id.button_apply);
		button_apply.setOnClickListener(this);
		
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				editTextUrl.setText(webView.getUrl());
				return true;
			}
		});
		Intent intent = getIntent();
		int from = intent.getIntExtra("from", 0);
		if(from == 2){
			String title = intent.getStringExtra("title");
			editTextTitle.setText(title);
			setTitle(title);
			String url = intent.getStringExtra("url");
			editTextUrl.setText(url);
			webView.loadUrl(url);
		}
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.button_browser:
			String url = editTextUrl.getText().toString();
			webView.loadUrl(url);	
			break;
		case R.id.button_apply:
			String title = editTextTitle.getText().toString();
			String rssurl = editTextUrl.getText().toString();
			new MyAsyncTaskAddRss().execute(title,rssurl);
		default:
			break;
		}
	}
	
	//输入参数、后台进度、输出结果
	public class MyAsyncTaskAddRss extends AsyncTask<String, String, String>{
		//在onPreExecute后执行，接收参数返回数据 
		
		protected String doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			mParams.put("TITLE",str[0]);
			mParams.put("RSS",str[1]);
			
			String result = HttpUtil.sendPost(appConfig.SetRss,mParams);
			HttpUtil.sendGet(appConfig.SetRefresh,"UTF-8");
			return result;

		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(String result) {
			Toast.makeText(AddRssActivity.this, result, Toast.LENGTH_SHORT);
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
