package info.aeon.app.EMMAJAMESApp.frame;

import java.util.HashMap;
import java.util.Map;
import info.aeon.app.EMMAJAMESApp.R;
import android.graphics.drawable.Drawable;
import android.webkit.WebView;

public class GV {
	public static final String TAG = "chuchuyajun";
	//public static Typeface jpTypeface;
	
	public static final String BRAND_1 = "ESPRITMUR",
							   BRAND_2 = "bo.dre",
							   BRAND_3 = "emmajames";
	public static Map<String, Integer> brandPics;
	
	public static Map<String, String> pushStyle;
	
	public static Map<String, String> requestSidValues;
	public static Map<String,String> brandCartUrls;
	public static Drawable homeBgDrawable = null;
	
	public static int choosedBrandResId = -1;
	public static String currentBrandSidValue;
	public static String currentBrandCartUrl;
	public static WebView webView;
	public static boolean isinit=false;
	
	
	public final static String CACHE_PIC_DIR = "/.aeon/cache"; // 图片将缓存到这个文件夹下
	
	public static final String REQUEST_SID_KEY = "sid";
	public static final String REQUEST_BRAND_KEY = "brand";
	public static final String REQUEST_BRAND_VALUE_1 = "1",
			                   REQUEST_BRAND_VALUE_2 = "2",
					           REQUEST_BRAND_VALUE_3 = "3";
	public static final String REQUEST_AREA_KEY = "area";
	
	public static final String PUSH_MYSHOP="myshop";
	public static final String PUSH_COUPON="coupon";
	public static final String PUSH_SPECIAL="special";
	public static final String PUSH_NEWGOODS="newgoods";
	
	public static final String UDID="udid";
	public static final String DEVICE="device";
	public static final String DEVICEID="deviceid";
	public static final String DEVICE_TYPE="1";
	
	//WebActivity每次进入时会打开这个链接
	public static String webpageCurrentUrl = "https://prottapp.com/p/e09465#/";
	
    public static final String SHOP_HOMEPAGE_URL = "http://aeontown.project-design.biz/shop/";
    public static final String NOTICE_COMFIRM_URL = "http://www.waon.net/service/net-station/login-guide/";
    public static final String HOME_BTN_NEWS_URL = "http://aeontown.project-design.biz/news/";
    
    public static final String HOME_BTN_BRAND1_CART = "https://shops.aeonsquare.net/shop/c/c080107/?linkid=po11_H18";
    public static final String HOME_BTN_BRAND2_CART = "https://shops.aeonsquare.net/shop/c/c080107/?linkid=po11_H17";
    public static final String HOME_BTN_BRAND3_CART = "https://shops.aeonsquare.net/shop/c/c080107/?linkid=po11_H16";
	
	//public static final String SHOP_BASE_URL = "http://chuchuyajun.xicp.net/shop/";
	public static final String SHOP_BASE_URL = "http://aeonapp.sakura.ne.jp/aeonshop/";
	public static final String JSON_URL_ALBUM = SHOP_BASE_URL + "api/album.php";
	public static final String JSON_URL_COUPON = SHOP_BASE_URL + "api/coupon.php";
	public static final String JSON_URL_GOODS = SHOP_BASE_URL + "api/goods.php";
	public static final String JSON_URL_NOTICE = SHOP_BASE_URL + "api/notice.php";
	public static final String JSON_URL_PAPER = SHOP_BASE_URL + "api/paper.php";
	public static final String JSON_URL_SHOP = SHOP_BASE_URL + "api/shop.php";
	public static final String JSON_URL_TOP = SHOP_BASE_URL + "api/top.php";
	public static final String JSON_URL_ADDSHOP = SHOP_BASE_URL + "api/addShop.php";
	public static final String JSON_URL_START = SHOP_BASE_URL + "api/start.php";
	
	//因为Json返回的只是图片的名称，所以需要知道网站中存放这些图片的文件夹在什么位置
	public static final String IMG_URL_BASE_ALBUM = SHOP_BASE_URL + "albumimg/";
	public static final String IMG_URL_BASE_COUPON = SHOP_BASE_URL + "couponimg/";
	public static final String IMG_URL_BASE_PAPER = SHOP_BASE_URL + "paperimg/";
	public static final String IMG_URL_BASE_SHOP = SHOP_BASE_URL + "shopimg/";
	public static final String IMG_URL_BASE_TOP = SHOP_BASE_URL + "paperimg/";
	
	
	//maybe unused
	public static final String INTENT_KEY_NAME_STRING = "SHOP";
	
	public static final String TAB_BRAND = "TAB_BRAND_ACTIVITY";
	public static final String TAB_HOME = "TAB_HOME_ACTIVITY";
	
	public static final String TAB_WEB = "TAB_WEB_ACTIVITY";
	public static final String TAB_LOGIN = "TAB_LOGIN_ACTIVITY";
	public static final String TAB_NOTICE = "TAB_NOTICE_ACTIVITY";
	public static final String TAB_COUPON = "TAB_COUPON_ACTIVITY";
	public static final String TAB_NEWGOODS = "TAB_NEWGOODSS_ACTIVITY";
	public static final String TAB_COLLECTION = "TAB_COLLECTION_ACTIVITY";
	public static final String TAB_SETTINGS = "TAB_SETTINGS_ACTIVITY";
	
//	public static final String PUSH_MESSAGE="message";
//	public static final String PUSH_MYSHOP="myshop";
//	public static final String PUSH_COUPON="coupon";
//	public static final String PUSH_SPECIAL="special";
//	public static final String PUSH_NEWGOODS="newgoods";
//	public static final String PUSH_BRAND="brand";
//	public static final String PUSH_STATUS="status";
	
	public static void init() {
		brandPics = new HashMap<String, Integer>();
		brandPics.put(BRAND_1, R.drawable.brand1);
		brandPics.put(BRAND_2, R.drawable.brand2);
		brandPics.put(BRAND_3, R.drawable.brand3);
		
		requestSidValues = new HashMap<String, String>();
		requestSidValues.put(BRAND_1, REQUEST_BRAND_VALUE_1);
		requestSidValues.put(BRAND_2, REQUEST_BRAND_VALUE_2);
		requestSidValues.put(BRAND_3, REQUEST_BRAND_VALUE_3);
		
		brandCartUrls = new HashMap<String, String>();
		brandCartUrls.put(BRAND_1,HOME_BTN_BRAND1_CART);
		brandCartUrls.put(BRAND_2,HOME_BTN_BRAND2_CART);
		brandCartUrls.put(BRAND_3,HOME_BTN_BRAND3_CART);
		isinit=true;
	}

}
