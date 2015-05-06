package info.aeon.app.EMMAJAMESApp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.bean.Notice;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.JSONParser;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;

public class NoticeActivity extends BaseActivity {

	private ListView noticeListView;
	private View comfirm_btn,login_btn;
	private TextView shop_notice_title;
	private PreferenceHelper preferenceHelper;
	private Resources resources;
	
	private static final String NOTICE_DATE_KEY_STRING = "DATE",
								NOTICE_CONTENT_KEY_STRING = "CONTENT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_notice);
		setPageTitleAndShow(getResources().getString(R.string.my_shop_login));
		preferenceHelper = new PreferenceHelper(this);
		resources = this.getResources();
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		shop_notice_title = (TextView)findViewById(R.id.shop_notice_title);
		noticeListView = (ListView)findViewById(R.id.shop_notice_list);
		
		comfirm_btn =  findViewById(R.id.comfirm_btn);
		login_btn =  findViewById(R.id.login_btn);
	}

	@Override
	protected void initView() {
		super.initView();
		comfirm_btn.setOnClickListener(btnClickListener);
		login_btn.setOnClickListener(btnClickListener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshHeader();
		preferenceHelper.setPushBrandStateByName(GV.PUSH_MYSHOP, false);
		threadGetWebData();
	}

	private OnClickListener btnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.comfirm_btn:
//				一時的にポイント確認ボタンを削除
//				ボタンUIはWhiteにすることで存在しています
//				元に戻す場合はactivity_notice.xmlからボタンColorを黒に戻し位置調整をする
//				defaultBrowserOpenUrl(NoticeActivity.this,GV.NOTICE_COMFIRM_URL);
				break;
			case R.id.login_btn:
				showActivityInTab(GV.TAB_LOGIN);
				break;
					
			}
		}
	};
	
	private OnItemClickListener noticeItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
		}
	};
	
	public void threadGetWebData(){
		final String sids = preferenceHelper.getPreferShopSidString(GV.currentBrandSidValue,"");
		Log.d(GV.TAG,"通知SIDS"+sids);
		if(sids.equals("")) return;
		showLoadingDialog(true);
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair(GV.REQUEST_BRAND_KEY, GV.currentBrandSidValue));
				paramList.add(new BasicNameValuePair(GV.REQUEST_SID_KEY, sids));
				List<Notice> notices = new JSONParser().getNoticesFromMap(GV.JSON_URL_NOTICE,paramList);
				if(notices.size() > 0){
					msg.obj = notices;
					mHandler.sendMessage(msg);
				} else {
					showLoadingDialog(false);
				}
			}
		}.start();
	}
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) { 
			shop_notice_title.setText(resources.getText(R.string.myShopNotice));
			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			List<Notice> notices = (List<Notice>) msg.obj;
			for(int i=0;i<notices.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Notice notice = notices.get(i);
				map.put(NOTICE_DATE_KEY_STRING,"["+notice.getAddtime()+"]");
				map.put(NOTICE_CONTENT_KEY_STRING,notice.getContent());
				data.add(map);
			}
			noticeListView.setAdapter(new SimpleAdapter(NoticeActivity.this, data, R.layout.notice_item, 
					new String[]{NOTICE_DATE_KEY_STRING,NOTICE_CONTENT_KEY_STRING},
					new int[]{R.id.notice_date,R.id.notice_content}));
			noticeListView.setOnItemClickListener(noticeItemClickListener);
			showLoadingDialog(false);
		}
	};


}
