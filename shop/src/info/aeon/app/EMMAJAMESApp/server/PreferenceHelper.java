package info.aeon.app.EMMAJAMESApp.server;

import java.util.ArrayList;
import java.util.List;

import info.aeon.app.EMMAJAMESApp.frame.GV;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class PreferenceHelper {
	private SharedPreferences preferences;
	private static final String PREFERENCE_NAME = "shop";
	private static final String BRAND1_PREFER_SHOP_SIDS = "brand1_prefer_shops_sids";
	private static final String BRAND2_PREFER_SHOP_SIDS = "brand2_prefer_shops_sids";
	private static final String BRAND3_PREFER_SHOP_SIDS= "brand3_prefer_shops_sids";
	
	private static final String BRAND1_SET_WALLPAPER = "brand1_wallpaper";
	private static final String BRAND2_SET_WALLPAPER = "brand2_wallpaper";
	private static final String BRAND3_SET_WALLPAPER= "brand3_wallpaper";
	
	private static final String PUSH_MESSAGE="message";
	private static final String PUSH_MYSHOP="myshop";
	private static final String PUSH_COUPON="coupon";
	private static final String PUSH_SPECIAL="special";
	private static final String PUSH_NEWGOODS="newgoods";

	private static final String PUSH_BRAND="pushbrand";


	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_REG_ID = "registration_id";
	

	private String PUSH_REQUEST_VALUE1="MY店舗からのお知らせが更新されました";
	private String PUSH_REQUEST_VALUE2="特集が更新されました";
	private String PUSH_REQUEST_VALUE3="クーポンが更新されました";
	private String PUSH_REQUEST_VALUE4="新着商品が更新されました";
	
	
	
	public PreferenceHelper(Context context){
		preferences = context.getSharedPreferences(PREFERENCE_NAME ,Context.MODE_PRIVATE);
	}
	
	public void setPreferShops(String requestBrandValue,List<String> shopSidList){
		Editor prefEditor = preferences.edit();
		String sidsString = "";
		if(shopSidList.size() > 0) {
			int lastIndex = shopSidList.size() - 1;
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<lastIndex;i++){
				sb.append(shopSidList.get(i)+",");
			}
			sb.append(shopSidList.get(lastIndex));
			sidsString = sb.toString();
		}
		
		if(requestBrandValue == GV.REQUEST_BRAND_VALUE_1) prefEditor.putString(BRAND1_PREFER_SHOP_SIDS, sidsString);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_2) prefEditor.putString(BRAND2_PREFER_SHOP_SIDS, sidsString);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_3) prefEditor.putString(BRAND3_PREFER_SHOP_SIDS, sidsString);
		prefEditor.commit();
	}
	
	public List<String> getPreferShopSidList(String requestBrandValue,String defaultSid){
		List<String> shopSids = new ArrayList<String>();
		String sidString = getPreferShopSidString(requestBrandValue,defaultSid);
		if(sidString ==  null || sidString.equals("")) return null;
		String[] tmpArray = sidString.split(",");
		for(int i=0;i<tmpArray.length;i++) shopSids.add(tmpArray[i]);
		return shopSids;
	}
	
	public String getPreferShopSidString(String requestBrandValue,String defaultSid){
		if(requestBrandValue == GV.REQUEST_BRAND_VALUE_1)	return preferences.getString(BRAND1_PREFER_SHOP_SIDS,defaultSid);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_2)	return preferences.getString(BRAND2_PREFER_SHOP_SIDS,defaultSid);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_3)	return preferences.getString(BRAND3_PREFER_SHOP_SIDS,defaultSid);
		else return null;
	}
	
	public String getBrandWallpaper(String requestBrandValue,String defaultUrl){
		if(requestBrandValue == GV.REQUEST_BRAND_VALUE_1)	return preferences.getString(BRAND1_SET_WALLPAPER,defaultUrl);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_2)	return preferences.getString(BRAND2_SET_WALLPAPER,defaultUrl);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_3)	return preferences.getString(BRAND3_SET_WALLPAPER,defaultUrl);
		else return null;
	}
	
	public void setBrandWallpaper(String requestBrandValue,String paperUrl){
		Editor prefEditor = preferences.edit();
		if(requestBrandValue == GV.REQUEST_BRAND_VALUE_1)	prefEditor.putString(BRAND1_SET_WALLPAPER,paperUrl);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_2)	prefEditor.putString(BRAND2_SET_WALLPAPER,paperUrl);
		else if(requestBrandValue == GV.REQUEST_BRAND_VALUE_3)	prefEditor.putString(BRAND3_SET_WALLPAPER,paperUrl);
		prefEditor.commit();
	}
	
	public boolean getPushBrandState(String requestBrandValue){
		if(requestBrandValue.equals(PUSH_MYSHOP))	return preferences.getBoolean(PUSH_MYSHOP,false);
		else if(requestBrandValue.equals(PUSH_COUPON))	return preferences.getBoolean(PUSH_COUPON,false);
		else if(requestBrandValue.equals(PUSH_SPECIAL))	return preferences.getBoolean(PUSH_SPECIAL,false);
		else if(requestBrandValue.equals(PUSH_NEWGOODS))	return preferences.getBoolean(PUSH_NEWGOODS,false);
		else return false;
	}
	
	public void setPushBrandState(String requestBrandValue,boolean state){
		Editor prefEditor = preferences.edit();
		if(requestBrandValue.equals(PUSH_REQUEST_VALUE1))	prefEditor.putBoolean(PUSH_MYSHOP,state);
		else if(requestBrandValue.equals(PUSH_REQUEST_VALUE2))	prefEditor.putBoolean(PUSH_SPECIAL,state);
		else if(requestBrandValue.equals(PUSH_REQUEST_VALUE3))	prefEditor.putBoolean(PUSH_COUPON,state);
		else if(requestBrandValue.equals(PUSH_REQUEST_VALUE4))	prefEditor.putBoolean(PUSH_NEWGOODS,state);
		prefEditor.commit();
	}
	
	public void setPushBrandStateByName(String requestBrandValue,boolean state){
		Editor prefEditor = preferences.edit();
		if(requestBrandValue.equals(PUSH_MYSHOP))	prefEditor.putBoolean(PUSH_MYSHOP,state);
		else if(requestBrandValue.equals(PUSH_SPECIAL))	prefEditor.putBoolean(PUSH_SPECIAL,state);
		else if(requestBrandValue.equals(PUSH_COUPON))	prefEditor.putBoolean(PUSH_COUPON,state);
		else if(requestBrandValue.equals(PUSH_NEWGOODS))	prefEditor.putBoolean(PUSH_NEWGOODS,state);
		prefEditor.commit();
	}
	
	public void setRegidAndAppVersion(String regid,int appver){
		Editor prefEditor = preferences.edit(); 
		prefEditor.putString(PROPERTY_REG_ID,regid);
		prefEditor.putInt(PROPERTY_APP_VERSION,appver);
		prefEditor.commit();
	}
	
	public String getPROPERTY_REG_ID(){
		return preferences.getString(PROPERTY_REG_ID,"");
	}
	
	public void initPushData(){
		Editor prefEditor = preferences.edit();
		prefEditor.putBoolean(PUSH_MYSHOP,false);
		prefEditor.putBoolean(PUSH_SPECIAL,false);
		prefEditor.putBoolean(PUSH_COUPON,false);
		prefEditor.putBoolean(PUSH_NEWGOODS,false);
		prefEditor.putString(PUSH_MESSAGE,"");
		prefEditor.commit();
	}
	
	public void setPushBrand(String brand){
		Editor prefEditor = preferences.edit();
		prefEditor.putString(PUSH_BRAND, brand);
		prefEditor.commit();
	}
	
	public void setPushMessage(String message){
		Editor prefEditor = preferences.edit(); 
		prefEditor.putString(PUSH_MESSAGE,message);
		prefEditor.commit();
	}
	
	public String getPushMessage(){
		return preferences.getString(PUSH_MESSAGE,"");
	}
	public int getAppVersion(){
		return preferences.getInt(PROPERTY_APP_VERSION,Integer.MIN_VALUE);
	}
	
	public String getPushBrand(){
		return preferences.getString(PUSH_BRAND,"");
	}
	
	public List<Boolean> getPushBrandStateList(){
		String[] pushDataList=new String[]{PUSH_MYSHOP,"","",PUSH_SPECIAL,PUSH_NEWGOODS,PUSH_COUPON,"","",""};
		List<Boolean> statelist = new ArrayList<Boolean>();
		for(int i=0;i<pushDataList.length;i++){
			if(!pushDataList.equals("")){
				statelist.add(getPushBrandState(pushDataList[i]));
			}else{
				statelist.add(false);
			}
				
		}
		return statelist;
	}
	
	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

}
