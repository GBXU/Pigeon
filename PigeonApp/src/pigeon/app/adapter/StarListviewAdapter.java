package pigeon.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pigeon.app.bean.UserBean;
import pigeon.app.netutil.R;

public class StarListviewAdapter extends ArrayAdapter<UserBean>{
	private int resourceId;
	public StarListviewAdapter(Context context, int textViewResourceId,List<UserBean> list) {
		super(context,textViewResourceId,list);
		resourceId = textViewResourceId;
	}
	public void loadStarList(List<UserBean> list){
		this.clear();
		this.addAll(list);
	}
	public void addStarList(List<UserBean> list){
		this.addAll(list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserBean user = getItem(position);// 获取当前项的news实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		
		TextView nickname = (TextView)view.findViewById(R.id.star_nickname);
		TextView email = (TextView)view.findViewById(R.id.star_email);
		TextView userid = (TextView)view.findViewById(R.id.star_userid);
		
		nickname.setText(user.getNickname());
		email.setText(user.getEmail());
		userid.setText("编号: "+String.valueOf(user.getUserId()));
		return view;
	}
}
