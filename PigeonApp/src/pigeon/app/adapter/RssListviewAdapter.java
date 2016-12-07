package pigeon.app.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import pigeon.app.bean.RssBean;
import pigeon.app.netutil.R;

public class RssListviewAdapter extends BaseAdapter{
	private List<RssBean> rsslist;
	private Context context;
	public RssListviewAdapter(Context context,List<RssBean> rsslist) {
		super();
		this.rsslist = rsslist;//�ǵ���this	
		if(rsslist == null){
			this.rsslist  = new ArrayList<RssBean>();//�ǵ���this���������
		}
		this.context = context;
	}

	public void loadRssList(List<RssBean> rsslist) {
		this.rsslist.clear();
		this.rsslist.addAll(rsslist);
	}
	public void addRssList(List<RssBean> rsslist){
		this.rsslist.addAll(rsslist);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		RssBean website = this.rsslist.get(position);// ��ȡ��ǰ���rssʵ��
		if(convertView == null){
			convertView = View.inflate(context,R.layout.item_rss, null);
		}
		TextView rss_title = (TextView)convertView.findViewById(R.id.rss_websiteName);
		TextView rss_latest = (TextView)convertView.findViewById(R.id.rss_latest);
		//���Ϊ�գ������ã���Ϊbean���ǿ�ָ��
		rss_title.setText(website.getWebsiteName());
		//rss_latest.setText(website.getLatest());
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.rsslist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.rsslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


}