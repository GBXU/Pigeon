package pigeon.app.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pigeon.app.bean.NewsBean;
import pigeon.app.netutil.R;

public class NewsListviewAdapter  extends ArrayAdapter<NewsBean>{
	private int resourceId;
	public NewsListviewAdapter(Context context, int textViewResourceId,List<NewsBean> newslist) {
		super(context,textViewResourceId,newslist);
		resourceId = textViewResourceId;
	}

	public void addNewsList(List<NewsBean> newslist){
		this.addAll(newslist);
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		NewsBean news = getItem(position);// 获取当前项的news实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView title = (TextView)view.findViewById(R.id.news_title);
		TextView date = (TextView)view.findViewById(R.id.news_date);
		//ImageView pic = (ImageView)view.findViewById(R.id.news_pic);
		title.setText(news.getNewsTitle());
		date.setText(news.getNewsDate());
		//details.setText(news.getNewsPic()());
		return view;
	}
}
