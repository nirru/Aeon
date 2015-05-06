package info.aeon.app.EMMAJAMESApp.adapter;

import java.util.List;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.bean.Shop;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;

public class ShopsAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Shop> shops;
	private int checkedSums = 0;
	
	public ShopsAdapter(Context context,List<Shop> shops) {
		this.mInflater = LayoutInflater.from(context);
		this.shops = shops;
	}

	@Override
	public int getCount() {
		return shops.size();
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
		ShopCheckHolder holder = null;
		//if (convertView == null) {

			holder = new ShopCheckHolder();

			convertView = mInflater.inflate(R.layout.shop_group_child_item,null);
			holder.shopCheckBox = (CheckBox)convertView.findViewById(R.id.shop_checkbox);
		//	convertView.setTag(holder);

		//} else {
		//	holder = (ShopCheckHolder) convertView.getTag();
		//}
		
		holder.shopCheckBox.setText(shops.get(position).getShopname());
		holder.shopCheckBox.setOnCheckedChangeListener(shopCheckChangeListener);
		
		return convertView;
	}

	private OnCheckedChangeListener shopCheckChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			checkedSums = isChecked?checkedSums+1:checkedSums-1;
			if(checkedSums > 3) buttonView.setChecked(false);
		}
	};
	
	public final class ShopCheckHolder {
		public CheckBox shopCheckBox;
	}

}