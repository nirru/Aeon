package info.aeon.app.EMMAJAMESApp.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.SimpleAdapter;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.adapter.HomeGridMenuAdapter;
import info.aeon.app.EMMAJAMESApp.adapter.HomeGridMenuAdapter1;
import info.aeon.app.EMMAJAMESApp.bean.Coupon;
import info.aeon.app.EMMAJAMESApp.bean.Top;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.interfaces.GridViewItemTouchHelper;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader;
import info.aeon.app.EMMAJAMESApp.server.JSONParser;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader.ImageCallback;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HomeActivity extends BaseActivity {

	private GridView homeGrimenu;
	private List<Integer> menuItemIcons;
	private ImageView homeBg;
	private static final String MENU_ICON_KEY_STRING = "ICON";
	private static final String MENU_TEXT_KEY_STRING = "TEXT";
	private PreferenceHelper ph;
//	private List<Map<String, Object>> data;
	private List<Boolean> iconstatus;
	private HomeGridMenuAdapter1 homeAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_home_2);
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		homeGrimenu = (GridView)findViewById(R.id.home_gridmenu);
		homeBg = (ImageView)findViewById(R.id.home_bg);
	}

	@Override
	protected void initView() {
		super.initView();
		homeBg.setImageResource(R.drawable.wallpaper_default);
		ph = new PreferenceHelper(this);
		
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		iconstatus=new ArrayList<Boolean>();
		menuItemIcons = new ArrayList<Integer>();
		menuItemIcons.add(R.drawable.home_gridmenu_login);
		menuItemIcons.add(R.drawable.home_gridmenu_cart);
		menuItemIcons.add(R.drawable.home_gridmenu_news);
		menuItemIcons.add(R.drawable.home_gridmenu_special);
		menuItemIcons.add(R.drawable.home_gridmenu_newgoods);
		menuItemIcons.add(R.drawable.home_gridmenu_sale);
		menuItemIcons.add(R.drawable.home_gridmenu_shop);
		menuItemIcons.add(R.drawable.home_gridmenu_settings);
		
		List<String> menuItemTexts = new ArrayList<String>();
		Resources res = getResources();
		menuItemTexts.add(res.getString(R.string.my_shop_login));
		menuItemTexts.add(res.getString(R.string.online_cart));
		menuItemTexts.add(res.getString(R.string.shop_news));
		menuItemTexts.add(res.getString(R.string.special_goods_show));
		menuItemTexts.add(res.getString(R.string.new_goods));
		menuItemTexts.add(res.getString(R.string.sale_off));
		menuItemTexts.add(res.getString(R.string.shop_view));
		menuItemTexts.add(res.getString(R.string.wallpaper_setting));
		
		for(int i=0;i<menuItemIcons.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(MENU_ICON_KEY_STRING,menuItemIcons.get(i));
			map.put(MENU_TEXT_KEY_STRING,menuItemTexts.get(i));
			data.add(map);
			iconstatus.add(false);
		}
		
//		homeGrimenu.setAdapter(new SimpleAdapter(this, data, R.layout.home_gridmenu_item, 
//				new String[]{MENU_ICON_KEY_STRING,MENU_TEXT_KEY_STRING}, new int[]{R.id.home_gridmenu_icon,R.id.home_gridmenu_text}));
		homeAdapter=new HomeGridMenuAdapter1(this, data,iconstatus);
		homeGrimenu.setAdapter(homeAdapter);
		homeGrimenu.setOnItemClickListener(homeMenuBtnClickListener);
	}
	
	private OnItemClickListener homeMenuBtnClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.d("chuchuyajun","主页面菜单项被点击了："+position);
			switch(menuItemIcons.get(position)){
			case R.drawable.home_gridmenu_login:
				showActivityInTab(GV.TAB_NOTICE);
				break;
			case R.drawable.home_gridmenu_cart:
				defaultBrowserOpenUrl(HomeActivity.this,GV.currentBrandCartUrl);
				break;
			case R.drawable.home_gridmenu_sale:
				showActivityInTab(GV.TAB_COUPON);
				break;
			case R.drawable.home_gridmenu_special:
				showActivityInTab(GV.TAB_COLLECTION);
				break;
			case R.drawable.home_gridmenu_newgoods:
				showActivityInTab(GV.TAB_NEWGOODS);
				break;
			case R.drawable.home_gridmenu_news:
				GV.webpageCurrentUrl = GV.HOME_BTN_NEWS_URL;
				showActivityInTab(GV.TAB_WEB);
				break;
			case R.drawable.home_gridmenu_shop:
				GV.webpageCurrentUrl = GV.SHOP_HOMEPAGE_URL;
				showActivityInTab(GV.TAB_WEB);
				break;
			case R.drawable.home_gridmenu_settings:
				showActivityInTab(GV.TAB_SETTINGS);
				break;
			}
		}
	};


	@Override
	public void threadGetWebData(){
		showLoadingDialog(true);
		if(GV.homeBgDrawable != null) {
			showLoadingDialog(false);
			return;
		}
		String paperUrl = ph.getBrandWallpaper(GV.currentBrandSidValue, null);
		if(paperUrl != null){
			Message msg = new Message();
			msg.obj = paperUrl;
			mHandler.sendMessage(msg);
			return;
		}
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair(GV.REQUEST_BRAND_KEY, GV.currentBrandSidValue));
				List<Top> tops = new JSONParser().getTopsFromMap(GV.JSON_URL_TOP,paramList);
				if(tops.size() > 0){
					int random = new Random().nextInt(tops.size());
					String imgUrl = GV.IMG_URL_BASE_TOP+tops.get(random).getImg();
					msg.obj = imgUrl;
					//ph.setBrandWallpaper(GV.currentBrandSidValue, imgUrl);
					mHandler.sendMessage(msg);
				}/*else { //没有随机图片
					toggleLoadingDialog();
				}*/
			}
		}.start();
	}
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String imageUrl = (String)msg.obj;
			Drawable cachedImage = new AsyncImageLoader(HomeActivity.this).loadDrawable(imageUrl, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	            	if(imageDrawable != null){
	            		homeBg.setImageDrawable(imageDrawable);
	            		GV.homeBgDrawable = imageDrawable;
		            	resizeWallpaper(imageDrawable);
	            	}
	            	showLoadingDialog(false);
	            }
	        });
			if(cachedImage != null) {//如果已经缓存了
				homeBg.setImageDrawable(cachedImage); 
				GV.homeBgDrawable = cachedImage;
				resizeWallpaper(cachedImage);
				showLoadingDialog(false);
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		refreshHeader();
		if(GV.homeBgDrawable != null) { //从设置壁纸页面过来的
			homeBg.setImageDrawable(GV.homeBgDrawable);
			resizeWallpaper(GV.homeBgDrawable);
		} else {//从品牌选择页面过来的
			threadGetWebData();
		}
		setIconData();
		homeAdapter.notifyDataSetChanged();
	}
	
	private void resizeWallpaper(Drawable drawable){
		Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
		 int width = bitmap.getWidth();
     	float scale = (float)homeBg.getWidth()/width;
     	homeBg.setMinimumHeight((int)(bitmap.getHeight()*scale+0.5));
     	Log.d(GV.TAG, "scale:"+scale);
	}
	//===========================================================================
	// adjust bitmap height   unused!
	//===========================================================================
	private void setHomeWallpaper(Bitmap bitmap){
		int width = this.getResources().getDisplayMetrics().widthPixels;
		float scaleX = (float)width / bitmap.getWidth();
		int height = (int) (bitmap.getHeight()* scaleX + 0.5f);
		homeBg.setScaleType(ScaleType.FIT_START);
		homeBg.setImageBitmap(zoomBitmap(bitmap,width,height));
	}
	
	private Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		Bitmap newbmp = null;
		if (bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidht = ((float) w / width);
			float scaleHeight = ((float) h / height);
			matrix.postScale(scaleWidht, scaleHeight);
			newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
		}
		return newbmp;
	}
	
	
	private void setIconData(){
		List<Boolean> list=new ArrayList<Boolean>();
		list=ph.getPushBrandStateList();
		for(int i=0;i<iconstatus.size();i++){
				if(list.get(i)){
					iconstatus.set(i, true);
				}else{
					iconstatus.set(i, false);
				}
		}
		
	}
	
	
}
