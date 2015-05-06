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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.adapter.CollectionAdapter;
import info.aeon.app.EMMAJAMESApp.adapter.HomeGridMenuAdapter;
import info.aeon.app.EMMAJAMESApp.adapter.NewsAdapter;
import info.aeon.app.EMMAJAMESApp.bean.Album;
import info.aeon.app.EMMAJAMESApp.bean.Goods;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.FrameActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.interfaces.GridViewItemTouchHelper;
import info.aeon.app.EMMAJAMESApp.server.JSONParser;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;

public class NewgoodsActivity extends BaseActivity {

	private ListView newsListView;
	private List<Goods> goodsList;
	private PreferenceHelper pHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_newgoods);
		setPageTitleAndShow(getResources().getString(R.string.new_goods));
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		newsListView = (ListView)findViewById(R.id.news_listview);
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
		pHelper.setPushBrandStateByName(GV.PUSH_NEWGOODS, false);
	}
	
	private OnItemClickListener newsItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			//DisplayToast("NEWS ITEM CLICKED!");
			GV.webpageCurrentUrl = goodsList.get(position).getUrl();
			FrameActivity.showTabActivity(GV.TAB_WEB);
		}
	};
	
	public void threadGetWebData(){
		showLoadingDialog(true);
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair(GV.REQUEST_BRAND_KEY, GV.currentBrandSidValue));
				goodsList = new JSONParser().getGoodsFromMap(GV.JSON_URL_GOODS,paramList);
				if(goodsList.size() > 0){
					msg.obj = goodsList;
					mHandler.sendMessage(msg);
				}else { //没有新着商品
					showLoadingDialog(false);
				}
			}
		}.start();
	}
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			newsListView.setAdapter(new NewsAdapter(NewgoodsActivity.this, (List<Goods>)msg.obj));
			newsListView.setOnItemClickListener(newsItemClickListener);
			showLoadingDialog(false);
		}
	};

}
