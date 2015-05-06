package info.aeon.app.EMMAJAMESApp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.adapter.CollectionAdapter;
import info.aeon.app.EMMAJAMESApp.adapter.WallpaperAdapter;
import info.aeon.app.EMMAJAMESApp.bean.Album;
import info.aeon.app.EMMAJAMESApp.bean.Paper;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.JSONParser;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;

public class CollectionActivity extends BaseActivity  {

	private ListView specialListView;
	private List<Album> collections;
	private PreferenceHelper pHelper;

	private static final String COLLECTION_NAME_KEY_STRING = "NAME",
			COLLECTION_THUMB_KEY_STRING = "THUMB";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_special);
		setPageTitleAndShow(getResources().getString(R.string.special_goods_show));
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		specialListView = (ListView)findViewById(R.id.special_listview);
	}

	
	@Override
	protected void initView() {
		super.initView();
		pHelper=new PreferenceHelper(this);
		threadGetWebData();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshHeader();
		pHelper.setPushBrandStateByName(GV.PUSH_SPECIAL, false);
	}

	
	public void threadGetWebData(){
		showLoadingDialog(true);
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair(GV.REQUEST_BRAND_KEY, GV.currentBrandSidValue));
				collections = new JSONParser().getAlbumsFromMap(GV.JSON_URL_ALBUM,paramList);
				if(collections.size() > 0){
					msg.obj = collections;
					mHandler.sendMessage(msg);
				}else { //没有特辑
					showLoadingDialog(false);
				}
			}
		}.start();
	}
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			specialListView.setAdapter(new CollectionAdapter(CollectionActivity.this, (List<Album>)msg.obj,specialListView));
			showLoadingDialog(false);
		}
	};

}
