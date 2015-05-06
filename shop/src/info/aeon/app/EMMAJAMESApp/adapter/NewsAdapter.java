package info.aeon.app.EMMAJAMESApp.adapter;

import java.util.ArrayList;
import java.util.List;
import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.bean.Goods;
import info.aeon.app.EMMAJAMESApp.frame.FrameActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	// 可以在外部实现点击事件绑定//private ListItemClickHelp callback;

	private List<Goods> goodsList;
	public List<Integer> newsItemBgColors; 
	
	public NewsAdapter(Context context,List<Goods> goodsList) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.goodsList = goodsList;

		Resources re = context.getResources();
		newsItemBgColors = new ArrayList<Integer>();
		newsItemBgColors.add(re.getColor(R.color.news_item_bg_1));
		newsItemBgColors.add(re.getColor(R.color.news_item_bg_2));
	}

	@Override
	public int getCount() {
		return goodsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		GoodsHolder holder = null;
		if (convertView == null) {

			holder = new GoodsHolder();

			convertView = mInflater.inflate(R.layout.news_item,	null);
			convertView.setBackgroundColor(newsItemBgColors.get(position%2));
			holder.goodsAddTime = (TextView) convertView.findViewById(R.id.news_item_date);
			holder.goodsName = (TextView) convertView.findViewById(R.id.news_item_content);
			//holder.goto_btn = (ImageView) convertView.findViewById(R.id.news_item_goto_btn);
			convertView.setTag(holder);

		} else {
			holder = (GoodsHolder) convertView.getTag();
		}

		holder.goodsAddTime.setText("["+goodsList.get(position).getAddtime()+"]");
		holder.goodsName.setText(goodsList.get(position).getGoodsname());

		// 可以在外部实现点击事件绑定//final View _convertView = convertView;
		/*holder.goto_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GV.webpageCurrentUrl = goodsList.get(position).getUrl();
				FrameActivity.showTabActivity(GV.TAB_WEB);
			}
		});*/
		return convertView;
	}

	public final class GoodsHolder {
		public TextView goodsAddTime;
		public TextView goodsName;
		//public ImageView goto_btn;
	}

}