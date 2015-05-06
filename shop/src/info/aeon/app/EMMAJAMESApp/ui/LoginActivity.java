package info.aeon.app.EMMAJAMESApp.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.adapter.ShopsExpandableListAdapter;
import info.aeon.app.EMMAJAMESApp.bean.Shop;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.DeviceInfo;
import info.aeon.app.EMMAJAMESApp.server.JSONParser;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;
import info.aeon.app.EMMAJAMESApp.server.PushHelper;

public class LoginActivity extends BaseActivity {

	private View btn_decide,btn_top,sub_title;
	private ScrollView mScrollView;
	private ExpandableListView allShopsExpanList;
	private GridView area_gridmenu;
	private List<String> areaNames;
	private PushHelper pushHelper;
	private PreferenceHelper preferenceHelper;
	private ShopsExpandableListAdapter shopsExpandableListAdapter;
	private static final String AREA_NAME_KEY_STRING = "AREA";
	private String regid="";
	private DeviceInfo deviceinfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_login);
		Log.e("hello", "ram");
		setPageTitleAndShow(getResources().getString(R.string.myshop_login));
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		sub_title = findViewById(R.id.sub_title);
		btn_top = findViewById(R.id.top_btn);
		mScrollView = (ScrollView)findViewById(R.id.mScrollView);
		btn_decide = findViewById(R.id.decide_btn);
		area_gridmenu = (GridView) findViewById(R.id.area_gridmenu);
		allShopsExpanList = (ExpandableListView) findViewById(R.id.area_shops_listview);
	}

	@Override
	protected void initView() {
		super.initView();
		pushHelper=new PushHelper(this);
		preferenceHelper=new PreferenceHelper(this);
		deviceinfo=new DeviceInfo(this);
		initAreaList();
		threadGetWebData();
		btn_top.setOnClickListener(clickListener);
	}


	private void initAreaList() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Resources resource = getResources();
		areaNames = new ArrayList<String>();
		areaNames.add(resource.getString(R.string.area_1));
		areaNames.add(resource.getString(R.string.area_2));
		areaNames.add(resource.getString(R.string.area_3));
		areaNames.add(resource.getString(R.string.area_4));
		areaNames.add(resource.getString(R.string.area_5));
		areaNames.add(resource.getString(R.string.area_6));
		areaNames.add(resource.getString(R.string.area_7));
		areaNames.add(resource.getString(R.string.area_8));
		
		
		for (int i = 0; i < areaNames.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(AREA_NAME_KEY_STRING, areaNames.get(i));
			data.add(map);
		}
		area_gridmenu.setAdapter(new SimpleAdapter(LoginActivity.this, data, R.layout.area_item, new String[] { AREA_NAME_KEY_STRING }, new int[] { R.id.area_name }));
		area_gridmenu.setOnItemClickListener(areaItemClickListener);
	}
	
	// =====================================================================================
	//             从网络获取数据    
	// =====================================================================================
	@Override
	public void threadGetWebData() {
		showLoadingDialog(true);
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair(GV.REQUEST_BRAND_KEY, GV.currentBrandSidValue));
				List<Shop> shops =  new JSONParser().getShopsFromMap(GV.JSON_URL_SHOP, paramList);
				if (shops.size() > 0) {
					msg.obj = categoryShops(shops);;
					mHandler.sendMessage(msg);
				} else {
					showLoadingDialog(false);
				}
			}
		}.start();
	}
	
	public void postAddStore() {
		final String sids = preferenceHelper.getPreferShopSidString(GV.currentBrandSidValue,"");
		if(sids.equals("")) return;
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				regid = pushHelper.getRegistrationId(LoginActivity.this);
				paramList.add(new BasicNameValuePair(GV.UDID, regid));
				paramList.add(new BasicNameValuePair(GV.DEVICE, GV.DEVICE_TYPE));
				paramList.add(new BasicNameValuePair(GV.DEVICEID, deviceinfo.getDeviceId()));
				paramList.add(new BasicNameValuePair(GV.REQUEST_SID_KEY, sids));
				try {
					new JSONParser().requestJSONData1(GV.JSON_URL_ADDSHOP, paramList);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private List<List<Shop>> categoryShops(List<Shop> shops){
		List<List<Shop>> allShops = new ArrayList<List<Shop>>();
		for (int i=0; i< areaNames.size(); i++)
			 allShops.add(new ArrayList<Shop>());
		int shopCounts = shops.size();
		for(int i=0; i<shopCounts; i++){
			Shop shop = shops.get(i);
			int index = areaNames.indexOf(shop.getArea());
			if(index != -1){
				allShops.get(index).add(shop);
			}
		}
		return allShops;
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@SuppressLint("NewApi")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			shopsExpandableListAdapter = new ShopsExpandableListAdapter(LoginActivity.this, areaNames,(List<List<Shop>>) msg.obj);
			area_gridmenu.setOnItemClickListener(areaItemClickListener);
			allShopsExpanList.setAdapter(shopsExpandableListAdapter);
			for (int i = 0; i < shopsExpandableListAdapter.getGroupCount(); i++) {
				allShopsExpanList.expandGroup(i, true);
			}
			allShopsExpanList.setOnGroupClickListener(allShopsGroupListren);
			btn_decide.setOnClickListener(clickListener);
			scrollToHead();
			showLoadingDialog(false);
		}
	};

	// =====================================================================================
	//             scrollview 滑动函数
	// =====================================================================================
	private void scrollToHead(){
		mScrollView.post(new Runnable() { 
	        public void run() { 
	        	mScrollView.fullScroll(ScrollView.FOCUS_UP); 
	        } 
	}); 
	}
	
	private void scrollToPosition(final int x, final int y){
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				mScrollView.scrollTo(x, y);// 改变滚动条的位置
			}
		};
		
		Handler handler = new Handler();
		handler.postDelayed(runnable, 200);
	}
	 
	
	// =====================================================================================
	// 监听事件
	// =====================================================================================
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.top_btn:
				scrollToHead();
				break;
			case R.id.decide_btn:
				postAddStore();
				shopsExpandableListAdapter.setPreferShops();
				showActivityInTab(GV.TAB_NOTICE);
				break;
			}
			
		}
	};
	
	
	private OnItemClickListener areaItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			/*if (allShopsExpanList.isGroupExpanded(position)) {
				allShopsExpanList.collapseGroup(position);
			} else { 
				allShopsExpanList.expandGroup(position);
			}*/
			scrollToPosition(0,getGroupPosition(position));
		}
	};
	
	private int getAboveHeight(){
		int height = 0;
		height += sub_title.getHeight();
		height += btn_decide.getHeight();
		height += area_gridmenu.getHeight();
		height += 70 * getResources().getDisplayMetrics().density;
		return height;
	}
	
	private int getGroupPosition(int groupPosition) {
		int totalHeight = getAboveHeight();
		View group0 = shopsExpandableListAdapter.getGroupView(0, true, null, allShopsExpanList);
		group0.measure(0, 0);
		int groupHeight = group0.getMeasuredHeight();

		View child0 = shopsExpandableListAdapter.getChildView(0, 0, false, null, null);
		child0.measure(0, 0);
		int childHeight = child0.getMeasuredHeight();

		for (int i = 0; i < groupPosition; i++) {
			totalHeight += groupHeight + childHeight * shopsExpandableListAdapter.getChildrenCount(i);
		}
		return totalHeight;
	}
	
	private OnGroupClickListener allShopsGroupListren = new ExpandableListView.OnGroupClickListener() {
		@SuppressLint("NewApi")
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			if (parent.isGroupExpanded(groupPosition)) {
				parent.collapseGroup(groupPosition);
			} else {
				// 第二个参数true表示展开时是否触发默认滚动动画
				parent.expandGroup(groupPosition, true);
			}
			return true;
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshHeader();
	}
}
