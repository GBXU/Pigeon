package pigeon.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import pigeon.app.bean.WorldBean;
import pigeon.app.netutil.R;

public class WorldListviewAdapter extends BaseAdapter{
	private List<WorldBean> worldlist;
	private Context context;
	public WorldListviewAdapter(Context context,List<WorldBean> worldlist) {
		super();
		this.worldlist = worldlist;//记得用this	
		if(worldlist == null){
			this.worldlist  = new ArrayList<WorldBean>();//记得用this，避免错误
		}
		this.context = context;
	}
	public void loadWorldList(List<WorldBean> worldlist){
		this.worldlist.clear();
		this.worldlist.addAll(worldlist);
	}
	
	public void addWorldList(List<WorldBean> worldlist){
		this.worldlist.addAll(worldlist);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		WorldBean worldMessage = this.worldlist.get(position);// 获取当前项的实例
		if(convertView == null){
			convertView = View.inflate(context,R.layout.item_world, null);
		}
		TextView name = (TextView)convertView.findViewById(R.id.world_name);
		TextView message = (TextView)convertView.findViewById(R.id.world_messgae);
		TextView time = (TextView)convertView.findViewById(R.id.world_time);
		//如果为空，不能用，因为bean中是空指针
		name.setText(worldMessage.getNickname());
		message.setText(worldMessage.getMessage());
		time.setText(worldMessage.getTime());
		//rss_latest.setText(website.getLatest());
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.worldlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.worldlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


}