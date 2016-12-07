package pigeon.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pigeon.app.netutil.R;

public class SettingFragment extends Fragment implements OnClickListener{
	private Activity activity;
	private TextView textView_usualsetting;
	private TextView textView_defaultRss;
	private TextView textView_starFriends;
	private TextView textView_localNews;
	private TextView textView_feedback;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		this.activity = getActivity();
		super.onActivityCreated(savedInstanceState);
		textView_usualsetting = (TextView)activity.findViewById(R.id.textView_usualSetting);
		textView_usualsetting.setOnClickListener(this);
		
		textView_defaultRss = (TextView)activity.findViewById(R.id.textView_defaultRss);
		textView_defaultRss.setOnClickListener(this);

		textView_starFriends = (TextView)activity.findViewById(R.id.textView_starFriends);
		textView_starFriends.setOnClickListener(this);
		
		textView_localNews = (TextView)activity.findViewById(R.id.textView_localNews);
		textView_localNews.setOnClickListener(this);
		
		textView_feedback = (TextView)activity.findViewById(R.id.textView_feedback);
		textView_feedback.setOnClickListener(this);
	}
	public void onClick(View v){
		Intent intent;
		switch (v.getId()) {
		case R.id.textView_usualSetting:
			intent = new Intent(activity,ProfileActivity.class);
			startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.textView_defaultRss:
			intent = new Intent(activity,DefaultRssListActivity.class);
			startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.textView_starFriends:
			intent = new Intent(activity,StarListActivity.class);
			startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.textView_localNews:
			intent = new Intent(activity,LocalNewsActivity.class);
			startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.textView_feedback:
			intent = new Intent(activity,FeedbackActivity.class);
			startActivity(intent);
			activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;

		default:
			break;
		}
	}
}
