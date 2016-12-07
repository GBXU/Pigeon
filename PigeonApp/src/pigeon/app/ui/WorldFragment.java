package pigeon.app.ui;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import pigeon.app.adapter.WorldListviewAdapter;
import pigeon.app.bean.WorldBean;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.JsonParseUtil;
import pigeon.app.netutil.R;

public class WorldFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,OnScrollListener, OnItemClickListener{
	private AppConfig appConfig = new AppConfig();
	private int PAGE = 0;
	private boolean isLoading =true;
	private Activity mActivity;
	private ListView mListView;
	private WorldListviewAdapter mAdapter;
	private SwipeRefreshLayout mSwipeLayout;  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_world, container, false);
		this.mActivity = getActivity();
		return view;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		mListView = (ListView)mActivity.findViewById(R.id.fragment_world_listview);
		mAdapter = new WorldListviewAdapter(mActivity, null);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		
		mSwipeLayout = (SwipeRefreshLayout)mActivity.findViewById(R.id.fragment_world_swipe);  
		mSwipeLayout.setOnRefreshListener(this); 
		new MyAsyncTaskGetPublish().execute();
		super.onActivityCreated(savedInstanceState);
	}
	// 异步获取列表类
	public class MyAsyncTaskGetPublish extends AsyncTask<String, String, List<WorldBean>>{
		//在onPreExecute后执行，接收参数返回数据 
		protected List<WorldBean> doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			mParams.put("PAGE",Integer.toString(PAGE));
			String mJsonData = HttpUtil.sendPost(appConfig.GetPublish,mParams);
			List<WorldBean> mWorldList = JsonParseUtil.GetWorldData(mJsonData);
			Collections.sort(mWorldList,new Comparator<WorldBean>() {
				//按日期升序，之后插入会相反，变成降序，近日期在前
				public int compare(WorldBean b1,WorldBean b2) {
					if(b1.getPublishid()> b2.getPublishid()){
						return -1;
					}else if(b1.getPublishid() == b2.getPublishid()){
						return 0;
					}else {
						return 1;
					}
				}
			});
			return mWorldList;
		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(List<WorldBean> mWorldList) {
			isLoading = false;
			if(mWorldList.size()>0){
				mAdapter.loadWorldList(mWorldList);
				mAdapter.notifyDataSetChanged();
			}
			super.onPostExecute(mWorldList);
		}
	}
	@Override
	   public void setUserVisibleHint(boolean isVisibleToUser) {  
	       super.setUserVisibleHint(isVisibleToUser);  
	       if (isVisibleToUser) {  
	           //相当于Fragment的onResume  
	    	   //PAGE = 0;
	    	   //new MyAsyncTaskGetPublish().execute();
	       } else {  
	           //相当于Fragment的onPause  
	       }  
	   } 
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//		if(totalItemCount<=firstVisibleItem+visibleItemCount+1&&!isLoading){
//			isLoading = true;
//			PAGE++;
//			new MyAsyncTaskGetPublish().execute();
//		}
		
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (!isLoading) {
			isLoading = true;
			new MyAsyncTaskGetPublish().execute();			
		}
        mSwipeLayout.setRefreshing(false);  
	}
}
