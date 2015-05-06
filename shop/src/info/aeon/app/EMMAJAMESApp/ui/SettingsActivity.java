package info.aeon.app.EMMAJAMESApp.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.adapter.WallpaperAdapter;
import info.aeon.app.EMMAJAMESApp.bean.Paper;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.JSONParser;

public class SettingsActivity extends BaseActivity {

	private ListView wallpaper_listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_settings);
		setPageTitleAndShow(getResources().getString(R.string.wallpaper_setting));
		findViewById();
		initView();
	}
	@Override
	protected void findViewById() {
		super.findViewById();
		wallpaper_listview = (ListView)findViewById(R.id.wallpaper_listview);
	}

	@Override
	protected void initView() {
		super.initView();
		threadGetWebData();
		//List<Paper> papers = new JsonParser().getPapersFromMap(RE.JSON_URL_PAPER);
		//wallpaper_listview.setAdapter(new WallpaperAdapter(this, papers	,wallpaper_listview));
	}
	
	public void threadGetWebData(){
		showLoadingDialog(true);
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair(GV.REQUEST_BRAND_KEY, GV.currentBrandSidValue));
				List<Paper> papers = new JSONParser().getPapersFromMap(GV.JSON_URL_PAPER,paramList);
				if(papers.size() > 0){
					msg.obj = papers;
					mHandler.sendMessage(msg);
				}else { //没有壁纸
					//DisplayToast(SettingsActivity.this.getResources().getString(R.string.img_download_failed));
					showLoadingDialog(false);
				}
			}
		}.start();
	}
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			wallpaper_listview.setAdapter(new WallpaperAdapter(SettingsActivity.this, (List<Paper>)msg.obj,wallpaper_listview));
			showLoadingDialog(false);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		refreshHeader();
	}
}
