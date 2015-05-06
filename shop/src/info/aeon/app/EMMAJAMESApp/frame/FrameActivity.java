package info.aeon.app.EMMAJAMESApp.frame;

import java.util.ArrayList;
import java.util.List;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;
import info.aeon.app.EMMAJAMESApp.server.PushHelper;
import info.aeon.app.EMMAJAMESApp.ui.BrandActivity;
import info.aeon.app.EMMAJAMESApp.ui.HomeActivity;
import info.aeon.app.EMMAJAMESApp.ui.LoginActivity;
import info.aeon.app.EMMAJAMESApp.ui.NoticeActivity;
import info.aeon.app.EMMAJAMESApp.ui.CouponActivity;
import info.aeon.app.EMMAJAMESApp.ui.SettingsActivity;
import info.aeon.app.EMMAJAMESApp.ui.CollectionActivity;
import info.aeon.app.EMMAJAMESApp.ui.NewgoodsActivity;
import info.aeon.app.EMMAJAMESApp.ui.WebActivity;
import info.aeon.app.EMMAJAMESApp.view.AnimationTabHost;



import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class FrameActivity extends TabActivity {
	
	public static final String TAG = FrameActivity.class.getSimpleName();

	public static List<String> activityStack;
	private static View footerMenuLayout;
	private ImageView btn_preview,btn_choosebrand,btn_home,btn_refresh,btn_backview;
	public static AnimationTabHost mTabHost;
	public static int currentActivityIndex = -1;
	private PreferenceHelper pHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame_footer_tab);
		appInit();
		findViewById();
		initView();
	}

	private void findViewById() {
		footerMenuLayout = findViewById(R.id.footer_menu_btns);
		btn_preview =  (ImageView)findViewById(R.id.tab_preview);
		btn_choosebrand =  (ImageView)findViewById(R.id.tab_choose_brand);
		btn_home =  (ImageView)findViewById(R.id.tab_home);
		btn_refresh =  (ImageView)findViewById(R.id.tab_refresh);
		btn_backview =  (ImageView)findViewById(R.id.tab_backview);
	}
	
	public void setFooterBg(){
		if(GV.currentBrandSidValue == GV.REQUEST_BRAND_VALUE_1) footerMenuLayout.setBackgroundResource(R.color.brand1_footer_bg);
		else if(GV.currentBrandSidValue == GV.REQUEST_BRAND_VALUE_2) footerMenuLayout.setBackgroundResource(R.color.brand2_footer_bg);
		else if(GV.currentBrandSidValue == GV.REQUEST_BRAND_VALUE_3) footerMenuLayout.setBackgroundResource(R.color.brand3_footer_bg);
	}
	
	private void appInit(){
		GV.init();
		if(!isNetworkConnected()){
			Toast.makeText(this,getResources().getString(R.string.network_not_connected),Toast.LENGTH_SHORT).show();
		}else{
			new PushHelper(this).checkGooglePlay();
		}
	}
	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	private void initView() {
		mTabHost = (AnimationTabHost) findViewById(android.R.id.tabhost);
		pHelper=new PreferenceHelper(this);
		activityStack = new ArrayList<String>();

		Intent intentBrand = new Intent(FrameActivity.this, BrandActivity.class);
		Intent intentHome = new Intent(FrameActivity.this, HomeActivity.class);
		
		Intent intentWeb = new Intent(FrameActivity.this, WebActivity.class);
		Intent intentNotice = new Intent(FrameActivity.this, NoticeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		Intent intentLogin = new Intent(FrameActivity.this, LoginActivity.class);
		Intent intentCoupon = new Intent(FrameActivity.this, CouponActivity.class);
		Intent intentNews = new Intent(FrameActivity.this, NewgoodsActivity.class);
		Intent intentCollection = new Intent(FrameActivity.this, CollectionActivity.class);
		Intent intentSettings = new Intent(FrameActivity.this, SettingsActivity.class);
		

		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_BRAND).setIndicator(GV.TAB_BRAND).setContent(intentBrand));
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_HOME).setIndicator(GV.TAB_HOME).setContent(intentHome));
		
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_WEB).setIndicator(GV.TAB_WEB).setContent(intentWeb));
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_NOTICE).setIndicator(GV.TAB_NOTICE).setContent(intentNotice));
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_LOGIN).setIndicator(GV.TAB_LOGIN).setContent(intentLogin));
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_COUPON).setIndicator(GV.TAB_COUPON).setContent(intentCoupon));
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_NEWGOODS).setIndicator(GV.TAB_NEWGOODS).setContent(intentNews));
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_COLLECTION).setIndicator(GV.TAB_COLLECTION).setContent(intentCollection));
		mTabHost.addTab(mTabHost.newTabSpec(GV.TAB_SETTINGS).setIndicator(GV.TAB_SETTINGS).setContent(intentSettings));
		
		//非要让品牌选择页面也能被下面前进后退按钮控制，品牌选择页面都没有前进和后退这两个按钮！而且进品牌选择页面可以点下面的按钮！
		//说他是模拟浏览器操作吧，但是现在非要搞这么一个怪异的行为！还是IOS那逼孩子太水，做出这么个怪东西！最扯淡的是安卓还必须和IOS保持一致！
		//不合理的地方也要做成一样！
		boolean temp=getIntent().getBooleanExtra("statue",false);
		getIntent().removeExtra("statue");
		if(temp){
			setPushView();
		}else{
			showTabActivity(GV.TAB_BRAND);
		}
		
		mTabHost.setOpenAnimation(false);
		
		btn_preview.setOnClickListener(footerBtnClickListener);
		btn_choosebrand.setOnClickListener(footerBtnClickListener);
		btn_home.setOnClickListener(footerBtnClickListener);
		btn_refresh.setOnClickListener(footerBtnClickListener);
		btn_backview.setOnClickListener(footerBtnClickListener);
		
	}
	
	private void setPushView(){
		activityStack.clear();
		currentActivityIndex = -1;
		String message=pHelper.getPushMessage();
		if (message != null && !message.equals("")) {
			String[] temp = message.split(":");
			setBrand(temp[0]);
			if (temp[1].equals(getResources().getString(R.string.push_message1))) {
				showTabActivity(GV.TAB_NOTICE);
			} else if (temp[1].equals(getResources().getString(R.string.push_message2))) {
				showTabActivity(GV.TAB_COUPON);
			} else if (temp[1].equals(getResources().getString(R.string.push_message3))) {
				showTabActivity(GV.TAB_COLLECTION);
			} else if (temp[1].equals(getResources().getString(R.string.push_message4))) {
				showTabActivity(GV.TAB_NEWGOODS);
			}
		}
	}
	
	private void goBackPage(){
		if(getCurrentTagName().equals(GV.TAB_WEB) && GV.webView != null){
			if(GV.webView.canGoBack()){
				GV.webView.goBack();
				return;
			}
		}
		
		if(activityStack.size() > 1 && currentActivityIndex > 0){
			currentActivityIndex--;
			mTabHost.setCurrentTabByTag(activityStack.get(currentActivityIndex));
		}
	}
	
	private void goForwardPage(){
		if(getCurrentTagName().equals(GV.TAB_WEB) && GV.webView != null){
			GV.webView.goForward();
			return;
		}
		if(activityStack.size() > 1 && currentActivityIndex < activityStack.size() -1){
			currentActivityIndex++;
			mTabHost.setCurrentTabByTag(activityStack.get(currentActivityIndex));
		}
	}
	
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        	goBackPage();
	            return false;
	        }
	        return super.onKeyDown(keyCode,event);
	    }
	 
	 
	public static void pushActivityStack(String tagName){
		//如果点击了“前页”按钮，再打开其他页面就会覆盖掉这个页面后面的记录
		if(currentActivityIndex < activityStack.size() -1){
			currentActivityIndex++;
			activityStack.set(currentActivityIndex, tagName);
		} else if(currentActivityIndex == activityStack.size() -1){ //队列尾
			//只要不是自己跳转到自己这个页面，貌似也没有这种情况。
			if(!activityStack.get(activityStack.size()-1).equals(tagName)){ 
				currentActivityIndex++;
				activityStack.add(tagName);
			}
		} else Log.e(GV.TAG, "队列出错！队列为"+activityStack.toString()+"但是，页面指针="+currentActivityIndex);		
		mTabHost.setCurrentTabByTag(tagName);
	}
	
	private static String getCurrentTagName(){
		if(currentActivityIndex > -1 && activityStack.size() > 0) 
			return activityStack.get(currentActivityIndex);
		else return null;
	}
	
	private static String getPreTagName(){
		if(currentActivityIndex > 0 && activityStack.size() > 1) 
			return activityStack.get(currentActivityIndex-1);
		else return null;
	}
	public static void showTabActivity(String tagName){
		int stackSize = activityStack.size();
		switch(stackSize){
		case 0://如果为空直接添加
			activityStack.add(tagName);
			mTabHost.setCurrentTabByTag(tagName);
			currentActivityIndex++;
			break;
		case 1: //只有一个元素
			if(!tagName.equals(activityStack.get(0))){
				pushActivityStack(tagName);
			}
			break;
		default: //两个以上
			if(tagName.equals(getCurrentTagName())){ 
				//如果和当前页面相同。此种情况不会发生
			} else if(tagName.equals(getPreTagName())){
				//如果和倒数第二个相同，也就是返回效果了。
				mTabHost.setCurrentTabByTag(tagName);
				currentActivityIndex--;
			} else {
				//如果都不等于，那就是打开新页面，需要标签名入栈。
				pushActivityStack(tagName);
			}
			break;
		}
		Log.d(GV.TAG,"当前页面队列为："+activityStack.toString()+"页面指针："+currentActivityIndex);
	}
	
	private OnClickListener footerBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tab_choose_brand:
				/*activityStack.clear();
				currentActivityIndex = -1;
				GV.homeBgDrawable = null;
				Intent intent = new Intent(FrameActivity.this,BrandActivity.class);
				startActivity(intent);*/
				showTabActivity(GV.TAB_BRAND);
				break;

			case R.id.tab_home:
				showTabActivity(GV.TAB_HOME);
				break;

			case R.id.tab_refresh:
				refreshCurrentTab();
				break;

			case R.id.tab_preview:
				goBackPage();
				break;
				
			case R.id.tab_backview:
				goForwardPage();
				break;

			default:
				break;
			}
			//Log.d(GV.TAG,"当前页面队列为："+activityStack.toString()+"页面指针："+currentActivityIndex);
		}
	};
	
	private void refreshCurrentTab() {
		@SuppressWarnings("deprecation")
		Activity currentActivity = getCurrentActivity();
		if (currentActivity instanceof BaseActivity) {
			((BaseActivity) currentActivity).threadGetWebData();

		}
	}
	
	private void setBrand(String brandName){
		GV.choosedBrandResId = GV.brandPics.get(brandName);
		GV.currentBrandSidValue = GV.requestSidValues.get(brandName);
		GV.currentBrandCartUrl = GV.brandCartUrls.get(brandName);
	}
	
	public void showFooter(Boolean show){
		footerMenuLayout.setVisibility(show? View.VISIBLE : View.GONE);
	}
	
}
