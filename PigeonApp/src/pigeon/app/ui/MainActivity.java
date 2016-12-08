package pigeon.app.ui;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import pigeon.app.netutil.R;

public class MainActivity extends BaseFragmentActivity implements OnClickListener, OnPageChangeListener {
	private ViewPager mViewPager;
	// װ��Fragment�ļ���
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	// ������
	private FragmentPagerAdapter mAdapter;
	// Button ����
	private List<ChangeColorIconWithText> mButtons = new ArrayList<ChangeColorIconWithText>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		
		ConnectivityManager cManager=(ConnectivityManager)getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if(info!=null){
			Toast.makeText(MainActivity.this,"����״̬����" , Toast.LENGTH_LONG).show();
		}
		
		initView();//��ʼ���ؼ�
		initDatas();//��ʼ������
		mViewPager.setAdapter(mAdapter);
		initEvent();//��ʼ���¼�

	}

	// ��ʼ���ؼ�
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_rss);
		ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_world);
		ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_setting);
		
		mButtons.add(one);
		mButtons.add(two);
		mButtons.add(three);
		
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		one.setIconAlpha(1.0f);
	}

	private void initDatas(){
		RssFragment mRssFragment = new RssFragment();
		WorldFragment mWorldFragment=new WorldFragment();
		SettingFragment mSettingFragment = new SettingFragment();
		mFragments.add(mRssFragment);
		mFragments.add(mWorldFragment);
		mFragments.add(mSettingFragment);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mFragments.size();
			}
			@Override
			public Fragment getItem(int position) {
				return mFragments.get(position);
			}
		};
	}

	
	private void initEvent() {
		mViewPager.setOnPageChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
	    // Associate searchable configuration with the SearchView
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//		R.layout.mytextview, cursor, new String[] { "tb_name" },
//		new int[] { R.id.textview });
//searchView.setSuggestionsAdapter(adapter);
//	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
//			@Override
//			public boolean onQueryTextSubmit(String arg0) {
//                if (searchView != null) {
//                    // �õ�����������
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm != null) {
//                        // �⽫�ü��������е�����¶������أ�����һ�������ڵ��������ť�����뷨����ԹԵ��Զ����صġ�
//                        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // ���뷨�������ʾ״̬����ô���������뷨
//                    }
//                    searchView.clearFocus(); // ����ȡ����
//                }
//                return true;
//			}
//			@Override
//			public boolean onQueryTextChange(String arg0) {
//				// �Զ���ȫ
//				return false;
//			}
//		});
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.action_search:
			return true;
		case R.id.action_add:
			Intent intent = new Intent(MainActivity.this,AddRssActivity.class);
			intent.putExtra("from", 1);
			startActivity(intent);
			return true;
		case R.id.action_publish:
			Intent intent1 = new Intent(MainActivity.this,AddPublishActivity.class);
			startActivity(intent1);
			return true;
		case R.id.action_exit:
			ActivityCollector.finishAll();
			ActivityCollector.finishAllFragmentActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private void setOverflowButtonAlways()
	{
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����menu��ʾicon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onClick(View v) {
		clickTab(v);
	}

	/**
	 * ���Tab��ť
	 * 
	 * @param v
	 */
	private void clickTab(View v) {
		resetOtherTabs();

		switch (v.getId()) {
		case R.id.id_indicator_rss:
			mButtons.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_world:
			mButtons.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_setting:
			mButtons.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		}
	}

	/**
	 * ����������TabIndicator����ɫ
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mButtons.size(); i++) {
			mButtons.get(i).setIconAlpha(0);
		}
	}
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset > 0) {
			ChangeColorIconWithText left = mButtons.get(position);
			ChangeColorIconWithText right = mButtons.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
	}

}