package info.aeon.app.EMMAJAMESApp.adapter;

import java.util.List;
import java.util.Map;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.interfaces.GridViewItemTouchHelper;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeGridMenuAdapter1 extends BaseAdapter {
	private LayoutInflater myInflater;
	private List<Map<String, Object>> data;
	private Context context;
	private List<Boolean> iconstatus;
	private PreferenceHelper pHelper;
	
	public HomeGridMenuAdapter1(Context context, List<Map<String, Object>> data,List<Boolean> iconstatus){
		myInflater = LayoutInflater.from(context);
		this.data = data;
		this.context=context;
		this.iconstatus=iconstatus;
		pHelper=new PreferenceHelper(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = myInflater.inflate(R.layout.home_gridmenu_item, null);
			holder.home_gridmenu_icon = (ImageView) convertView.findViewById(R.id.home_gridmenu_icon);
			holder.home_gridmenu_iv = (ImageView) convertView.findViewById(R.id.home_gridmenu_iv);
			holder.home_gridmenu_text = (TextView) convertView.findViewById(R.id.home_gridmenu_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.home_gridmenu_icon.setImageResource((Integer)data.get(position).get("ICON"));
		holder.home_gridmenu_text.setText(data.get(position).get("TEXT").toString());
		String brand=pHelper.getPushBrand();
		int brandId=-1;
		if(GV.brandPics.get(brand)!=null&&!GV.brandPics.get(brand).equals("")){
			brandId=GV.brandPics.get(brand);
		}
		if(GV.choosedBrandResId==brandId){
			if(iconstatus.get(position)){
				holder.home_gridmenu_iv.setVisibility(View.VISIBLE);
			}else{
				holder.home_gridmenu_iv.setVisibility(View.GONE);
			}
		}else{
			holder.home_gridmenu_iv.setVisibility(View.GONE);
		}

//		switch (position) {
//		case 0:
//			if(getData("myshop")){
//				holder.home_gridmenu_iv.setVisibility(View.VISIBLE);
//			}else{
//				holder.home_gridmenu_iv.setVisibility(View.GONE);
//			}
//			break;
//		case 2:
//			if(getData("special")){
//				holder.home_gridmenu_iv.setVisibility(View.VISIBLE);
//			}else{
//				holder.home_gridmenu_iv.setVisibility(View.GONE);
//			}
//			break;
//		case 3:
//			if(getData("update")){
//				holder.home_gridmenu_iv.setVisibility(View.VISIBLE);
//			}else{
//				holder.home_gridmenu_iv.setVisibility(View.GONE);
//			}
//			break;
//		case 4:
//			if(getData("newproduct")){
//				holder.home_gridmenu_iv.setVisibility(View.VISIBLE);
//			}else{
//				holder.home_gridmenu_iv.setVisibility(View.GONE);
//			}
//			break;
//
//		default:
//			break;
//		}
		return convertView;
	}
	
	private class ViewHolder {

		public ImageView home_gridmenu_icon,home_gridmenu_iv;
		public TextView home_gridmenu_text;
	}
	
//	private boolean getData(String field){
//		boolean temp=context.getSharedPreferences("shop", Context.MODE_PRIVATE).getBoolean(field, false);
//		return temp;
//	}
}
