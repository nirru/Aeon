package info.aeon.app.EMMAJAMESApp.adapter;

import java.util.ArrayList;
import java.util.List;
import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.bean.Shop;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.AppDialog;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ShopsExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> areaNames;
	private List<List<Shop>> allAreaShops;
	private int checkedSums = 0;
	private final static  int MAX_CHECK_SHOPS = 3;
	private PreferenceHelper prefHelper;
	private List<String> preferShopSids;
	private AppDialog paletteDialog;

	public ShopsExpandableListAdapter(Context context, List<String> areaNames,List<List<Shop>> allShoos) {
		this.context = context;
		this.areaNames = areaNames;
		this.allAreaShops = allShoos;
		prefHelper = new PreferenceHelper(context);
		preferShopSids = prefHelper.getPreferShopSidList(GV.currentBrandSidValue,null);
		checkedSums = preferShopSids == null ? 0 : preferShopSids.size();
		if(preferShopSids == null) preferShopSids = new ArrayList<String>();
		paletteDialog = new AppDialog(context, R.layout.login_alert_dialog, false,1.0f,0.4f);
		paletteDialog.findViewById(R.id.dialog_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paletteDialog.dismiss();
			}
		});
		
	}

	public List<String> getPreferdShopSids(){
		return preferShopSids;
	}
	
	public void setPreferShops(){
			prefHelper.setPreferShops(GV.currentBrandSidValue,preferShopSids);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return allAreaShops.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return allAreaShops.get(groupPosition).get(childPosition).hashCode();
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		final String currentShopSid = ((Shop) getChild(groupPosition,childPosition)).getSid();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.shop_group_child_item, null);
		final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.shop_checkbox);
		checkBox.setText(allAreaShops.get(groupPosition).get(childPosition).getShopname());
		
		if(preferShopSids.contains(currentShopSid)) {  //此店铺是用户上次选中过的店铺
			Log.d(GV.TAG, "此店铺是用户收藏的店铺，店铺SID为："+currentShopSid);
			checkBox.setChecked(true);
		} else checkBox.setChecked(false);
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					checkBox.toggle();
			}
		});
		
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					checkedSums++;
					if(!preferShopSids.contains(currentShopSid)) preferShopSids.add(currentShopSid);
				} else {
					checkedSums--;
					if(preferShopSids.contains(currentShopSid)) preferShopSids.remove(currentShopSid);
				}
				
				if(checkedSums > MAX_CHECK_SHOPS) {
					checkedSums = MAX_CHECK_SHOPS;
					paletteDialog.show();
					//Toast.makeText(context, context.getResources().getString(R.string.max_allow_message), Toast.LENGTH_SHORT).show();
					preferShopSids.remove(currentShopSid);
					buttonView.setChecked(false);
				}
				Log.d(GV.TAG,"已经选择的SID="+preferShopSids.toString());
			}
		});
		
		return convertView;

	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return allAreaShops.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return areaNames.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return areaNames.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return areaNames.get(groupPosition).hashCode();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.shops_group_title_item, null);
		TextView title = (TextView) v.findViewById(R.id.selected_area);
		title.setText("["+areaNames.get(groupPosition)+"]");
		return v;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
