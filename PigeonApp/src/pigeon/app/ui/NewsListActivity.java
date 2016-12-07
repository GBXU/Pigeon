package pigeon.app.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import pigeon.app.adapter.NewsListviewAdapter;
import pigeon.app.bean.NewsBean;
import pigeon.app.config.AppConfig;
import pigeon.app.netutil.HttpUtil;
import pigeon.app.netutil.JsonParseUtil;
import pigeon.app.netutil.R;

public class NewsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,OnScrollListener,OnItemClickListener{
	//OnTouchListener
	private AppConfig appConfig = new AppConfig();
	private int PAGE = 0;
	private String WEBSITEID;
	private boolean isLoading =true;
	private List<NewsBean> mNewsList = new ArrayList<NewsBean>();
	private NewsListviewAdapter mAdapter;
	private SwipeRefreshLayout mSwipeLayout;  
	//private LinearLayout mLinearLayout;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_newslist);
		
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			NewsListActivity.this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		setTitle(title);
		WEBSITEID = intent.getStringExtra("websiteid");
		new MyAsyncTaskGetNews().execute(WEBSITEID);

		mAdapter = new NewsListviewAdapter(NewsListActivity.this,R.layout.item_news, mNewsList);
		ListView mListView = (ListView)findViewById(R.id.layout_newslist_listview);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		
		mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.layout_newslist_swipe);  
		mSwipeLayout.setOnRefreshListener(this);  
		
//		mLinearLayout = (LinearLayout)findViewById(R.id.layout_newslist);
//		mLinearLayout.setOnTouchListener(this);
	}
	//输入参数、后台进度、输出结果
	public class MyAsyncTaskGetNews extends AsyncTask<String, String, List<NewsBean>>{
		//在onPreExecute后执行，接收参数返回数据 
		
		protected List<NewsBean> doInBackground(String... str) {
			Map<String, String> mParams=new HashMap<String, String>();
			mParams.put("EM",appConfig.email);
			mParams.put("PW",appConfig.passwd);
			mParams.put("WEBSITEID",str[0]);
			mParams.put("PAGE",Integer.toString(PAGE));
			
			String mJsonData = HttpUtil.sendPost(appConfig.GetNews,mParams);
			List<NewsBean> mNewsList = JsonParseUtil.GetNewsData(mJsonData);
			Collections.sort(mNewsList,new Comparator<NewsBean>() {
				//按日期升序，之后插入会相反，变成降序，近日期在前
				public int compare(NewsBean b1,NewsBean b2) {
					if(b1.getNewsId()>b2.getNewsId()){
						return -1;
					}else if(b1.getNewsId()== b2.getNewsId()){
						return 0;
					}else {
						return 1;
					}
				}
			});
			return mNewsList;
		}
		@Override //后台操作结束后执行，之前的计算结果是参数，可进行UI操作
		protected void onPostExecute(List<NewsBean> mNewsList) {
			isLoading = false;
			if(mNewsList.size()>0){
				mAdapter.addNewsList(mNewsList);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	public void onItemClick(AdapterView<?>parent,View view,int position,long id) {
		NewsBean mNewsBean = mNewsList.get(position);
		Intent intent = new Intent(NewsListActivity.this,WebpageActivity.class);//Intent.ACTION_VIEW
		intent.putExtra("url", mNewsBean.getNewsUrl());
		intent.putExtra("title", mNewsBean.getNewsTitle());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if(totalItemCount<=firstVisibleItem+visibleItemCount+1&&!isLoading){
			isLoading = true;
			PAGE++;
			new MyAsyncTaskGetNews().execute(WEBSITEID);
		}

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
		// TODO Auto-generated method stub
		if (!isLoading) {
			PAGE = 0;
			isLoading = true;
			new MyAsyncTaskGetNews().execute(WEBSITEID);		
		}
        mSwipeLayout.setRefreshing(false);  	
	}
/*
	@Override
	public boolean onTouch(View v, MotionEvent event) {
	    final int XSPEED_MIN = 200;  	    //手指向右滑动时的最小速度  
	    final int XDISTANCE_MIN = 150;  	    //手指向右滑动时的最小距离  
	    float xDown = 0;  	    //记录手指按下时的横坐标。  
	    float xMove = 0;  	    //记录手指移动时的横坐标。  
	    VelocityTracker mVelocityTracker = null;  	    //用于计算手指滑动的速度。  
	    //创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。 
        if (mVelocityTracker == null) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            xDown = event.getRawX();  
            break;  
        case MotionEvent.ACTION_MOVE:  
            xMove = event.getRawX();  
            //活动的距离  
            int distanceX = (int) (xMove - xDown);  
            //获取顺时速度  
            mVelocityTracker.computeCurrentVelocity(1000);  
            int velocity = (int) mVelocityTracker.getXVelocity();  
            int xSpeed = Math.abs(velocity);  
            //当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity  
            if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {  
                finish();
            }  
            break;  
        case MotionEvent.ACTION_UP:  
        	//回收VelocityTracker对象。 
            mVelocityTracker.recycle();  
            mVelocityTracker = null;   
            break;  
        default:  
            break;  
        }  
		return true;
	}
*/
}
