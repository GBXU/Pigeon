package pigeon.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();
	public static List<FragmentActivity> fragmentActivities = new ArrayList<FragmentActivity>();

	public static void addFragmentActivity(FragmentActivity fragmentActivity) {
		fragmentActivities.add(fragmentActivity);
	}
	public static void removeFragmentActivity(FragmentActivity fragmentActivity) {
		fragmentActivities.remove(fragmentActivity);
	}
	public static void finishAllFragmentActivity() {
		for(FragmentActivity fragmentActivity:fragmentActivities){
			if (!fragmentActivity.isFinishing()) {
				fragmentActivity.finish();
			}
		}
	}
	public static void addActivity(Activity activity) {
		activities.add(activity);
	}
	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}
	public static void finishAll() {
		for(Activity activity:activities){
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}
