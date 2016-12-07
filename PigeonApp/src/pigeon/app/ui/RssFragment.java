package pigeon.app.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;  
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import pigeon.app.adapter.RssListviewAdapter;
import pigeon.app.bean.RssBean;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.JsonParseUtil;
import pigeon.app.netutil.R;

public class RssFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
													OnScrollListener,
													OnItemClickListener,
													OnItemLongClickListener{
	private AppConfig appConfig = new AppConfig();
	private Activity mActivity;
	private boolean isLoading =true;
	private ListView mListView;
	private SwipeRefreshLayout mSwipeLayout;  
	private RssListviewAdapter mAdapter;
	private int mWebsiteId;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_rss, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		this.mActivity = getActivity();
		mListView = (ListView)mActivity.findViewById(R.id.fragment_rss_listview);
		mAdapter = new RssListviewAdapter(mActivity, null);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		
		mSwipeLayout = (SwipeRefreshLayout)mActivity.findViewById(R.id.fragment_rss_swipe);  
		mSwipeLayout.setOnRefreshListener(this);  
		new MyAsyncTaskGetRss().execute();
		super.onActivityCreated(savedInstanceState);
	}
	// 异步获取列表类
	public class MyAsyncTaskGetRss extends AsyncTask<String, String, List<RssBean>>{
		//在onPreExecute后执行，接收参数返回数据 
		protected List<RssBean> doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);			
			String mJsonData = HttpUtil.sendPost(appConfig.GetRss,mParams);
			List<RssBean> mRssList = JsonParseUtil.GetRssData(mJsonData);
			return mRssList;
		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(List<RssBean> mRssList) {
			if(mRssList.size()>0){
				isLoading = false;
				mAdapter.loadRssList(mRssList);
				mAdapter.notifyDataSetChanged();
			}
			super.onPostExecute(mRssList);
		}
	}
	@Override
	   public void setUserVisibleHint(boolean isVisibleToUser) {  
	       super.setUserVisibleHint(isVisibleToUser);  
	       if (isVisibleToUser) {  
	           //相当于Fragment的onResume  
	    	   //new MyAsyncTaskGetRss().execute();
	       } else {  
	           //相当于Fragment的onPause  
	       }  
	   }  
	@Override
	public void onItemClick(AdapterView<?>parent,View view,int position,long id) {
		// TODO Auto-generated method stub
//		if(view.getId()!=R.id.foot_view){
//		Intent intent = new Intent(activity, NewsDetailsActivity.class);
//		intent.putExtra("url", adapter.getNewss().get(position).getUrl());
//		startActivity(intent);
//	}
//		Toast.makeText(activity, "你点击了item", Toast.LENGTH_SHORT).show();
		
		RssBean mRssBean = (RssBean)mAdapter.getItem(position);
		Intent intent = new Intent(mActivity,NewsListActivity.class);//Intent.ACTION_VIEW
		intent.putExtra("title", mRssBean.getWebsiteName());
		intent.putExtra("websiteid", Integer.toString(mRssBean.getWebsiteId()));
		startActivity(intent);
		mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onItemLongClick(AdapterView<?>parent,View view,int position,long id) {
		// TODO Auto-generated method stub
		RssBean mRssBean = (RssBean)mAdapter.getItem(position);
		mWebsiteId = mRssBean.getWebsiteId();
    	mListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
                    public void onCreateContextMenu(ContextMenu menu, View v, 
                    		ContextMenuInfo menuInfo) {
                            menu.add(0,0,0,"取消关注");
                            //group ID参数 , item ID参数,order ID参数,title参数
                    }
    	});
		return false;
	}
    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case 0:
        	new MyAsyncTaskDeleteRss().execute();
        	new MyAsyncTaskGetRss().execute();
            break;
        default:
            break;
        }
        return super.onContextItemSelected(item);
    }
	@Override
	public void onRefresh() {
		if (!isLoading) {
			isLoading = true;
			new MyAsyncTaskGetRss().execute();		
		}
        mSwipeLayout.setRefreshing(false);  	
	}
	// 异步删除
	public class MyAsyncTaskDeleteRss extends AsyncTask<String, String, String>{
		//在onPreExecute后执行，接收参数返回数据 
		protected String doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			mParams.put("RSSID",String.valueOf(mWebsiteId));		
			String  result= HttpUtil.sendPost(appConfig.DeleteRss,mParams);
			return result;
		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(String result) {
			Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
		}
	}
}