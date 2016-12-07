package pigeon.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import pigeon.app.adapter.StarListviewAdapter;
import pigeon.app.bean.UserBean;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.JsonParseUtil;
import pigeon.app.netutil.R;

public class StarListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,OnItemLongClickListener{
	private AppConfig appConfig = new AppConfig();
	private List<UserBean> mUserBeans = new ArrayList<UserBean>();
	private StarListviewAdapter mAdapter;
	private ListView mListView;
	private SwipeRefreshLayout mSwipeLayout;  
	private int mFriendId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_starlist);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			StarListActivity.this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		new MyAsyncTaskGetStar().execute();
		
		mAdapter = new StarListviewAdapter(StarListActivity.this,R.layout.item_star, mUserBeans);
		mListView = (ListView)findViewById(R.id.layout_starlist_listview);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemLongClickListener(this);

		mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.layout_starlist_swipe);  
		mSwipeLayout.setOnRefreshListener(this); 
	}
	//输入参数、后台进度、输出结果
	public class MyAsyncTaskGetStar extends AsyncTask<String, String, List<UserBean>>{
		//在onPreExecute后执行，接收参数返回数据 
		
		protected List<UserBean> doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			
			String mJsonData = HttpUtil.sendPost(appConfig.GetStar,mParams);
			List<UserBean> list = JsonParseUtil.GetStarData(mJsonData);
			return list;
		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(List<UserBean> list) {
			if(list.size()>0){
				mAdapter.loadStarList(list);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	@Override
	public boolean onItemLongClick(AdapterView<?>parent,View view,int position,long id) {
		// TODO Auto-generated method stub
		mFriendId = mAdapter.getItem(position).getUserId();
    	mListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v, 
	    		ContextMenuInfo menuInfo) {
	            menu.add(0,0,0,"取消关注");
            }
    	});
    	return false;
	}
    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case 0:
        	new MyAsyncTaskDeleteStar().execute();
        	new MyAsyncTaskGetStar().execute();
            break;
        default:
            break;
        }
        return super.onContextItemSelected(item);
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
		// TODO Auto-generated method stub
		new MyAsyncTaskGetStar().execute();
        mSwipeLayout.setRefreshing(false);  
	}
	// 异步删除
	public class MyAsyncTaskDeleteStar extends AsyncTask<String, String, String>{
		//在onPreExecute后执行，接收参数返回数据 
		protected String doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			mParams.put("FRIENDID",String.valueOf(mFriendId));		
			String  result= HttpUtil.sendPost(appConfig.DeleteStar,mParams);
			return result;
		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(String result) {
			Toast.makeText(StarListActivity.this, result, Toast.LENGTH_SHORT).show();
		}
	}
}
