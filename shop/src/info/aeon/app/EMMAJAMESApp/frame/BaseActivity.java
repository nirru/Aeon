package info.aeon.app.EMMAJAMESApp.frame;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.Header;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.server.AppDialog;
import info.aeon.app.EMMAJAMESApp.server.PushHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public abstract class BaseActivity extends Activity {
	
	public static final String TAG = BaseActivity.class.getSimpleName();
	private View pageHeader;
	private ImageView brandImg;
	private AppDialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page);
	}
	
	public void setPageTitleAndShow(String titleName){
		((TextView)findViewById(R.id.page_title_text)).setText(titleName);
		findViewById(R.id.page_title).setVisibility(View.VISIBLE);
	}
	
	public void hideHeader(){
		findViewById(R.id.page_header).setVisibility(View.GONE);
	}
	
	public void showHeader(){
		findViewById(R.id.page_header).setVisibility(View.VISIBLE);
	}
	
	public void setPageContentView(int layoutResId) {
		LinearLayout pageContentLayout = (LinearLayout) findViewById(R.id.page_content);
		View pageContent = View.inflate(this, layoutResId, null);
		LinearLayout.LayoutParams pageContentParas = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		pageContentLayout.addView(pageContent, pageContentParas);
	}
	
	


	/**
	 * 绑定控件id
	 */
	
	protected void findViewById(){
		pageHeader = findViewById(R.id.page_header);
		brandImg = (ImageView)findViewById(R.id.brand_img);
	}

	/**
	 * 初始化控件
	 */
	protected void initView(){
		pageHeader.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("chuchuyajun", "page header clicked!");
				showActivityInTab(GV.TAB_HOME);
			}
		});
	}

	protected void refreshHeader() {
		if(GV.choosedBrandResId != -1)
			brandImg.setImageResource(GV.choosedBrandResId);
	}
	
	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
		overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	protected void openActivityPostString(Class<?> pClass, String stringValue) {
		Intent intent = new Intent(this, pClass);
		if (!stringValue.equalsIgnoreCase("")) {
			intent.putExtra(GV.INTENT_KEY_NAME_STRING, stringValue);
		}
		startActivity(intent);
	}
	
	protected void showActivityInTab(String activityTag) {
		FrameActivity.showTabActivity(activityTag);
	}
	
	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	public ProgressDialog showProgressDialog(String alertString) {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		Drawable drawable=getResources().getDrawable(R.drawable.a_loading_animation);
		progressDialog.setIndeterminateDrawable(drawable);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setMessage(alertString);
		progressDialog.show();
		return progressDialog;
	}
	
	/*public void _toggleLoadingDialog(){
		if(loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
			return;
		}
		loadingDialog  = new AppDialog(this, R.layout.loading_dialog, false,1.0f,0.4f);
		Drawable drawable=getResources().getDrawable(R.drawable.a_loading_animation);
		((ProgressBar)loadingDialog.findViewById(R.id.loading_img)).setIndeterminateDrawable(drawable);
		loadingDialog.setCancelable(false);
		loadingDialog.show();
	}
	*/
	
	//因为采用了缓存，所以刷新速度特别快，但是客户非要说没有刷新，非要做个样子。所以需要让加载动画有一个最短显示时间
	long showTime = 0;
	static final long AT_LEAST_LAST_TIME = 300;
	public void showLoadingDialog(Boolean show){
		if(show){
			if(loadingDialog == null){
				loadingDialog  = new AppDialog(this, R.layout.loading_dialog, false,1.0f,0.4f);
				Drawable drawable=getResources().getDrawable(R.drawable.a_loading_animation);
				((ProgressBar)loadingDialog.findViewById(R.id.loading_img)).setIndeterminateDrawable(drawable);
				loadingDialog.setCancelable(false);
				loadingDialog.show();
				showTime = System.currentTimeMillis();
			} else {
				loadingDialog.show();
			}
		} else {
			if(System.currentTimeMillis() - showTime < AT_LEAST_LAST_TIME){
				delayDismiss();
			} else {
				if(loadingDialog != null){
					loadingDialog.dismiss();
					loadingDialog = null;
				}
			}
		}
	}
	
	private void delayDismiss(){
		Timer timer = new Timer(); 
		TimerTask task = new TimerTask(){   
		    public void run(){
		    	if(loadingDialog != null){
					loadingDialog.dismiss();
					loadingDialog = null;
				}
		    }   
		};   
		timer.schedule(task, AT_LEAST_LAST_TIME);
	}
	
	
	//提示
	protected void DisPlay(String content){
		Toast.makeText(this, content, 1).show();
	}
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	public void DisplayToast(int strResId) {
		Toast.makeText(this, this.getResources().getString(strResId), Toast.LENGTH_SHORT).show();
	}
	
	public void defaultBrowserOpenUrl(Context context, String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		context.startActivity(intent);
	}
	
	public abstract void threadGetWebData();

}
