package info.aeon.app.EMMAJAMESApp.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.adapter.HomeGridMenuAdapter;
import info.aeon.app.EMMAJAMESApp.adapter.WallpaperAdapter;
import info.aeon.app.EMMAJAMESApp.bean.Coupon;
import info.aeon.app.EMMAJAMESApp.bean.Paper;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.interfaces.GridViewItemTouchHelper;
import info.aeon.app.EMMAJAMESApp.server.AppDialog;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader;
import info.aeon.app.EMMAJAMESApp.server.JSONParser;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader.ImageCallback;

public class CouponActivity extends BaseActivity  {

	private ImageView couponImg;
	private PreferenceHelper pHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_coupon);
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		couponImg = (ImageView)findViewById(R.id.coupon_img);
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
		pHelper.setPushBrandStateByName(GV.PUSH_COUPON, false);
	}
	
	@Override
	public void threadGetWebData(){
		showLoadingDialog(true);
		new Thread() {
			public void run() {
				Message msg = new Message();
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair(GV.REQUEST_BRAND_KEY, GV.currentBrandSidValue));
				List<Coupon> coupons = new JSONParser().getCouponFromMap(GV.JSON_URL_COUPON,paramList);
				if(coupons.size() > 0){
					msg.obj = coupons;
					mHandler.sendMessage(msg);
				} else { //没有优惠活动
					showLoadingDialog(false);
				}
			}
		}.start();
	}
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			@SuppressWarnings("unchecked")
			Coupon coupon = ((List<Coupon>)msg.obj).get(0);//只显示一张优惠券图片
			String imageUrl = GV.IMG_URL_BASE_COUPON+coupon.getImg(); 
			Drawable cachedImage = new AsyncImageLoader(CouponActivity.this).loadDrawable(imageUrl, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	            	couponImg.setImageDrawable(imageDrawable);
	            	resizeCouponImg(imageDrawable);
	            	showLoadingDialog(false);
	            }
	        });
	        if(cachedImage != null) {//如果已经缓存了
	        	couponImg.setImageDrawable(cachedImage);
	        	resizeCouponImg(cachedImage);
	        	showLoadingDialog(false);
			}
		}
	};
	
	private void resizeCouponImg(Drawable drawable){
		Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
		 int width = bitmap.getWidth();
     	float scale = (float)couponImg.getWidth()/width;
     	couponImg.setMinimumHeight((int)(bitmap.getHeight()*scale+0.5f));
     	Log.d(GV.TAG, "scale:"+scale);
	}
}
