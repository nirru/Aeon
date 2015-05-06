package info.aeon.app.EMMAJAMESApp.ui;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.FrameActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;
import info.aeon.app.EMMAJAMESApp.server.PushHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class BrandActivity extends BaseActivity {

	private View brand_btns, brand1, brand2, brand3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_brand);
		setPageTitleAndShow(getResources().getString(R.string.brand_choose_title));
		findViewById();
		hideHeader();
		initView();
	}

	protected void findViewById() {
		brand_btns = findViewById(R.id.brand_btns);
		brand1 = findViewById(R.id.brand_1);
		brand2 = findViewById(R.id.brand_2);
		brand3 = findViewById(R.id.brand_3);
	}

	@Override
	protected void initView() {
		brand1.setOnClickListener(btnClickListener);
		brand2.setOnClickListener(btnClickListener);
		brand3.setOnClickListener(btnClickListener);
	}
	
	private void EnterHomePage(String brandName){
		GV.choosedBrandResId = GV.brandPics.get(brandName);
		GV.currentBrandSidValue = GV.requestSidValues.get(brandName);
		GV.currentBrandCartUrl = GV.brandCartUrls.get(brandName);
		GV.homeBgDrawable = null;
		
		((FrameActivity)getParent()).showFooter(true);
		((FrameActivity)getParent()).setFooterBg();
		
		FrameActivity.showTabActivity(GV.TAB_HOME);
		overridePendingTransition(0,0);
	}
	
	private OnClickListener btnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.brand_1:
				EnterHomePage(GV.BRAND_1);
				break;

			case R.id.brand_2:
				EnterHomePage(GV.BRAND_2);
				break;

			case R.id.brand_3:
				EnterHomePage(GV.BRAND_3);
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	public void threadGetWebData() {}

	@Override
	protected void onResume() {
		super.onResume();
		((FrameActivity)getParent()).showFooter(false);
	}
	
}
