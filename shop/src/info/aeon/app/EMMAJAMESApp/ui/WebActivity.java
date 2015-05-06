package info.aeon.app.EMMAJAMESApp.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;
import android.widget.ImageView;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.adapter.HomeGridMenuAdapter;
import info.aeon.app.EMMAJAMESApp.frame.BaseActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.interfaces.GridViewItemTouchHelper;

public class WebActivity extends BaseActivity {

	private WebView webView;
	private Boolean isFirstPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPageContentView(R.layout.activity_web);
		hideHeader();
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		super.findViewById();
		webView = (WebView)findViewById(R.id.main_webview);
	}

	@Override
	protected void initView() {
		super.initView();
		isFirstPage = true;
		GV.webView = webView;
		//Intent intent= getIntent();
		//String url= intent.getStringExtra(RE.INTENT_KEY_NAME_STRING);
		//webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.getSettings().setJavaScriptEnabled(true);  
		//webView.loadUrl(url);
		//webView.loadUrl("file:///android_asset/");
		webView.setWebViewClient(webViewClient);
	}

	private WebViewClient webViewClient = new WebViewClient(){

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// 页面中链接，如果希望点击链接继续在当前browser中响应，
	        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
			// 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
			view.loadUrl(formatUrl(url));
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			//toggleLoadingDialog();
			showLoadingDialog(true);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			//toggleLoadingDialog();
			showLoadingDialog(false);
			//webview复用的问题，比如先从点击店铺一览的按钮进入了这个webview，然后回到homepage，之后
			//点击news，然后有跳转到这个webview，此时点击后退，会回到上次进入的店铺一览的网址中。为了
			//解决这个问题，我将这个activity设置成NO_HISTORY，这样每次进入都会重新启动这个Activity。
			//但是点击后退仍然会回到店铺一览，所以我的做法是在第一次打开了页面后清除历史记录，因为清除
			//历史记录只能清除当前页面之前的页面，且要在页面加载完成才能清除。所以定义了一个flag
			if(isFirstPage == true){
				webView.clearHistory();
				isFirstPage = false;
				Log.d(GV.TAG,"清除历史记录");
			}
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
			//toggleLoadingDialog();
		}
		
	};
	
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshHeader();
		threadGetWebData();
	}
	
	private String formatUrl(String url){
		if(url.startsWith("http://") || url.startsWith("https://")) return url;
		return "http://"+url;
	}

	@Override
	public void threadGetWebData() {
		webView.loadUrl(formatUrl(GV.webpageCurrentUrl));
	}

	@Override
	protected void onPause() {
		super.onPause();
		isFirstPage = true;
	}
}
