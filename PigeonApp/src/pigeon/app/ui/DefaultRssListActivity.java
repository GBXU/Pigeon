package pigeon.app.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import pigeon.app.adapter.RssListviewAdapter;
import pigeon.app.bean.RssBean;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.JsonParseUtil;
import pigeon.app.netutil.R;

public class DefaultRssListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
																OnScrollListener,
																OnItemClickListener{
	private AppConfig appConfig = new AppConfig();
	private RssListviewAdapter mAdapter;
	private ListView mListView;
	private boolean isLoading =true;
	private SwipeRefreshLayout mSwipeLayout;  
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_rss);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			DefaultRssListActivity.this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		new MyAsyncTaskGetDefault().execute();
		
		mAdapter = new RssListviewAdapter(DefaultRssListActivity.this, null);
		
		mListView = (ListView)findViewById(R.id.fragment_rss_listview);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		
		mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.fragment_rss_swipe);  
		mSwipeLayout.setOnRefreshListener(this);
	}
	//输入参数、后台进度、输出结果
	public class MyAsyncTaskGetDefault extends AsyncTask<String, String, List<RssBean>>{
		//在onPreExecute后执行，接收参数返回数据 
		
		protected List<RssBean> doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			
			String mJsonData = HttpUtil.sendPost(appConfig.GetDefault,mParams);
			List<RssBean> mRssList = JsonParseUtil.GetRssData(mJsonData);
			return mRssList;
		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(List<RssBean> mRssList) {
			isLoading = false;
			if(mRssList.size()>0){
				mAdapter.loadRssList(mRssList);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	public void onItemClick(AdapterView<?>parent,View view,int position,long id) {
		RssBean mRssBean = (RssBean)mAdapter.getItem(position);
		Intent intent = new Intent(DefaultRssListActivity.this,AddRssActivity.class);//Intent.ACTION_VIEW
		intent.putExtra("from", 2);
		intent.putExtra("url", mRssBean.getWebsiteUrl());
		intent.putExtra("title", mRssBean.getWebsiteName());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
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
	@Override
	public void onRefresh() {
		if (!isLoading) {
			isLoading = true;
			new MyAsyncTaskGetDefault().execute();
		}
        mSwipeLayout.setRefreshing(false);  
	}
}
