package info.aeon.app.EMMAJAMESApp.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import info.aeon.app.EMMAJAMESApp.bean.Album;
import info.aeon.app.EMMAJAMESApp.bean.Coupon;
import info.aeon.app.EMMAJAMESApp.bean.Goods;
import info.aeon.app.EMMAJAMESApp.bean.Notice;
import info.aeon.app.EMMAJAMESApp.bean.Paper;
import info.aeon.app.EMMAJAMESApp.bean.Shop;
import info.aeon.app.EMMAJAMESApp.bean.Top;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import android.util.Log;

public class JSONParser {

	private static final String USER_AGENT = "Mozilla/4.5";
	
	//keys of bean object in json data from web api 
	private static final String[] ALBUM_JSON_KEYS = {"albumname","img","width","height","url","addtime"};
	private static final String[] COUPON_JSON_KEYS = {"couponname","img","width","height","addtime"};
	private static final String[] GOODS_JSON_KEYSC = {"goodsname","url","addtime"};
	private static final String[] NOTICE_JSON_KEYS = {"content","addtime"};
	private static final String[] PAPER_JSON_KEYS = {"papername","img","width","height","addtime"};
	private static final String[] SHOP_JSON_KEYS = {"sid","shopname","area","addtime"};
	private static final String[] TOP_JSON_KEYS = {"img","width","height"};

//-----------------------------------------------------------------------------
//                  从JSON解析出来的HASHMAP中获取BEAN对象
//-----------------------------------------------------------------------------
	public List<Album> getAlbumsFromMap(String albumJsonUrl,List<NameValuePair> paramList){
		JsonDataFromWeb dataFromWeb = requestWebData(albumJsonUrl,paramList,ALBUM_JSON_KEYS);
		List<Album> albums = new ArrayList<Album>();
		List<Map<String, String>> mapList = dataFromWeb.reArray;
		String picName;
		for(int i=0; i<mapList.size(); i++){
			Album album = new Album();
			Map<String, String> map = mapList.get(i);
			album.setAlbumname(map.get("albumname")) ;
			picName = map.get("img");
			if(!isPicName(picName))continue;
			album.setImg(picName) ;
			album.setWidth(map.get("width")) ;
			album.setHeight(map.get("height")) ;
			album.setUrl(map.get("url")) ;
			album.setAsstime(map.get("addtime")) ;
			albums.add(album);
		}
		return albums;
	}
	
	public List<Coupon> getCouponFromMap(String couponJsonUrl,List<NameValuePair> paramList){
		JsonDataFromWeb dataFromWeb = requestWebData(couponJsonUrl,paramList,COUPON_JSON_KEYS);
		List<Coupon> coupons = new ArrayList<Coupon>();
		List<Map<String, String>> mapList = dataFromWeb.reArray;
		String picName;
		for(int i=0; i<mapList.size(); i++){
			Coupon coupon = new Coupon();
			Map<String, String> map = mapList.get(i);
			coupon.setCouponname(map.get("couponname")) ;
			picName = map.get("img");
			if(!isPicName(picName))continue;
			coupon.setImg(picName) ;
			coupon.setWidth(map.get("width")) ;
			coupon.setHeight(map.get("height")) ;
			coupon.setAddtime(map.get("addtime")) ;
			coupons.add(coupon);
		}
		return coupons;
	}
	
	public List<Goods> getGoodsFromMap(String goodsJsonUrl,List<NameValuePair> paramList){
		JsonDataFromWeb dataFromWeb = requestWebData(goodsJsonUrl,paramList,GOODS_JSON_KEYSC);
		List<Goods> goodsList = new ArrayList<Goods>();
		List<Map<String, String>> mapList = dataFromWeb.reArray;
		for(int i=0; i<mapList.size(); i++){
			Goods goods = new Goods();
			Map<String, String> map = mapList.get(i);
			goods.setGoodsname(map.get("goodsname")) ;
			goods.setUrl(map.get("url")) ;
			goods.setAddtime(map.get("addtime")) ;
			goodsList.add(goods);
		}
		return goodsList;
	}
	
	public List<Notice> getNoticesFromMap(String noticeJsonUrl,List<NameValuePair> paramList){
		JsonDataFromWeb dataFromWeb = requestWebData(noticeJsonUrl,paramList,NOTICE_JSON_KEYS);
		List<Notice> notices = new ArrayList<Notice>();
		List<Map<String, String>> mapList = dataFromWeb.reArray;
		for(int i=0; i<mapList.size(); i++){
			Notice notice = new Notice();
			Map<String, String> map = mapList.get(i);
			notice.setContent(map.get("content")) ;
			notice.setAddtime(map.get("addtime")) ;
			notices.add(notice);
		}
		return notices;
	}
	
	public List<Paper> getPapersFromMap(String paperJsonUrl,List<NameValuePair> paramList){
		JsonDataFromWeb dataFromWeb = requestWebData(paperJsonUrl,paramList,PAPER_JSON_KEYS);
		List<Paper> papers = new ArrayList<Paper>();
		List<Map<String, String>> mapList = dataFromWeb.reArray;
		String picName;
		for(int i=0; i<mapList.size(); i++){
			Paper paper = new Paper();
			Map<String, String> map = mapList.get(i);
			paper.setPapername(map.get("papername")) ;
			picName = map.get("img");
			if(!isPicName(picName))continue;
			paper.setImg(picName) ;
			paper.setWidth(map.get("width")) ;
			paper.setHeight(map.get("height")) ;
			paper.setAddtime(map.get("addtime")) ;
			papers.add(paper);
		}
		return papers;
	}
	
	
	public List<Shop> getShopsFromMap(String shopsJsonUrl,List<NameValuePair> paramList){
		JsonDataFromWeb dataFromWeb = requestWebData(shopsJsonUrl,paramList,SHOP_JSON_KEYS);
		List<Shop> shops = new ArrayList<Shop>();
		List<Map<String, String>> mapList = dataFromWeb.reArray;
		for(int i=0; i<mapList.size(); i++){
			Shop shop = new Shop();
			Map<String, String> map = mapList.get(i);
			shop.setSid(map.get("sid")) ;
			shop.setShopname(map.get("shopname"));
			shop.setArea(map.get("area"));
			shop.setAddtime(map.get("addtime")) ;
			shops.add(shop);
		}
		return shops;
	}
	
	public List<Top> getTopsFromMap(String topJsonUrl,List<NameValuePair> paramList){
		JsonDataFromWeb dataFromWeb = requestWebData(topJsonUrl,paramList,TOP_JSON_KEYS);
		List<Top> tops = new ArrayList<Top>();
		List<Map<String, String>> mapList = dataFromWeb.reArray;
		String picName;
		for(int i=0; i<mapList.size(); i++){
			Top top = new Top();
			Map<String, String> map = mapList.get(i);
			picName = map.get("img");
			if(!isPicName(picName))continue;
			top.setImg(picName) ;
			top.setWidth(map.get("width")) ;
			top.setHeight(map.get("height")) ;
			tops.add(top);
		}
		return tops;
	}
	
	
//-----------------------------------------------------------------------------
//                  与对象无关的JSON解析相关函数
//-----------------------------------------------------------------------------
	public Boolean isPicName(String str){
		if(str.equals("") || str == null) return false;
		if(str.endsWith(".jpg") || str.endsWith(".png") || str.endsWith(".gif") || str.endsWith("bmp")) return true;
		return false;
	}
	
	//POST方法
	public JsonDataFromWeb requestWebData(String jsonUrl,List<NameValuePair> paramList,String[] jsonObjKeys) {
		JsonDataFromWeb dataFromWeb = new JsonDataFromWeb();
		try {
			String jsonStr = requestJSONData(jsonUrl,paramList);
			Log.d(GV.TAG,jsonStr);
			JSONObject jsonObj = new JSONObject(jsonStr);
			
			dataFromWeb.statuID = jsonObj.getInt("statusID");
			dataFromWeb.msg = jsonObj.getString("msg");
			JSONArray jsonReArray = jsonObj.getJSONArray("reArray");

			for (int i = 0; i < jsonReArray.length(); i++) {
				dataFromWeb.reArray.add(parseWebDataObj((JSONObject) jsonReArray.get(i),jsonObjKeys));
			}
		} catch (Exception e) {
			Log.e(GV.TAG,"无法解析JSON:\n"+e.getMessage());
			e.printStackTrace();
		}
		return dataFromWeb;
	}
	
	//GET方法
		public JsonDataFromWeb getWebData(String jsonUrl,String[] jsonObjKeys) {
			JsonDataFromWeb dataFromWeb = new JsonDataFromWeb();
			try {
				String jsonStr = getRequest(jsonUrl);
				//Log.d(GV.TAG,jsonStr);
				JSONObject jsonObj = new JSONObject(jsonStr);
				
				dataFromWeb.statuID = jsonObj.getInt("statusID");
				dataFromWeb.msg = jsonObj.getString("msg");
				JSONArray jsonReArray = jsonObj.getJSONArray("reArray");

				for (int i = 0; i < jsonReArray.length(); i++) {
					dataFromWeb.reArray.add(parseWebDataObj((JSONObject) jsonReArray.get(i),jsonObjKeys));
				}
			} catch (Exception e) {
				Log.e(GV.TAG,"无法解析JSON:\n"+e.getMessage());
				e.printStackTrace();
			}
			return dataFromWeb;
		}


	private Map<String,String> parseWebDataObj(JSONObject jsonObject,String[] jsonObjKeys) throws JSONException{
		Map<String, String> resultMap = new HashMap<String, String>();
		for(int i=0; i<jsonObjKeys.length; i++){
			String keyString = jsonObjKeys[i];
			resultMap.put(keyString, jsonObject.getString(keyString));
		}
		return resultMap;
	}


	//--------------------------------------------------------------------
	//     从网络获取json字符串 方法一
	//--------------------------------------------------------------------
	/**
	 * 发送get请求返回信息。
	 * 
	 * @param url
	 * @return String
	 */
	protected String getRequest(String url) throws Exception {
		return getRequest(url, new DefaultHttpClient(new BasicHttpParams()));
	}

	/**
	 * 向api发送get请求，url需按照api要求写，返回取得的信息。
	 * 
	 * @param url
	 * @param client
	 * @return String
	 */
	protected String getRequest(String url, DefaultHttpClient client)
			throws Exception {
		String result = null;
		int statusCode = 0;
		HttpGet getMethod = new HttpGet(url);
		//Log.d(GV.TAG, "do the getRequest,url=" + url + "");
		try {
			getMethod.setHeader("User-Agent", USER_AGENT);
			// 添加用户密码验证信息
			// client.getCredentialsProvider().setCredentials(
			// new AuthScope(null, -1),
			// new UsernamePasswordCredentials(mUsername, mPassword));

			HttpResponse httpResponse = client.execute(getMethod);
			// statusCode == 200 正常
			statusCode = httpResponse.getStatusLine().getStatusCode();
			//Log.d(GV.TAG, "statuscode = " + statusCode);
			// 处理返回的httpResponse信息
			result = retrieveInputStream(httpResponse.getEntity());
		} catch (Exception e) {
			Log.e(GV.TAG, e.getMessage());
			throw new Exception(e);
		} finally {
			getMethod.abort();
		}
		return result;
	}

	/**
	 * 处理httpResponse信息,返回String
	 * 
	 * @param httpEntity
	 * @return String
	 */
	protected String retrieveInputStream(HttpEntity httpEntity) {
		Long l = httpEntity.getContentLength();
		int length = (int) httpEntity.getContentLength();
		// the number of bytes of the content, or a negative number if unknown.
		// If the content length is known but exceeds Long.MAX_VALUE, a negative
		// number is returned.
		// length==-1，下面这句报错，println needs a message
		if (length < 0)
			length = 10000;
		StringBuffer stringBuffer = new StringBuffer(length);
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(
					httpEntity.getContent(), HTTP.UTF_8);
			char buffer[] = new char[length];
			int count;
			while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
				stringBuffer.append(buffer, 0, count);
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(GV.TAG, e.getMessage());
		} catch (IllegalStateException e) {
			Log.e(GV.TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(GV.TAG, e.getMessage());
		}
		return stringBuffer.toString();
	}
	
	
	
	
	//--------------------------------------------------------------------
	//     从网络获取json字符串 方法二
	//--------------------------------------------------------------------
	HttpPost httpRequest=null; 
    HttpResponse httpResponse;
	public String requestJSONData(String url,List <NameValuePair> paramList)throws ClientProtocolException, IOException {
		String result = null;
		/*建立HttpPost连接*/ 
        httpRequest=new HttpPost(url); 
        /*Post运作传送变数必须用NameValuePair[]阵列储存*/ 
        try { 
            //发出HTTP request 
            httpRequest.setEntity(new UrlEncodedFormEntity(paramList,HTTP.UTF_8)); 
            //取得HTTP response 
            httpResponse=new DefaultHttpClient().execute(httpRequest); 
            //若状态码为200 
            if(httpResponse.getStatusLine().getStatusCode()==200){ 
                //取出回应字串 
                result=EntityUtils.toString(httpResponse.getEntity()); 
            }else{ 
            	Log.e(GV.TAG,"JSONParser POST ERROR");
            } 
        } catch (Exception e) { 
        	Log.e(GV.TAG,"JSONParser POST ERROR");
        } 
        Log.d(GV.TAG,result);
        return result;
	}
	public String requestJSONData1(String url,List <NameValuePair> paramList)throws ClientProtocolException, IOException {
		String result = null;
		/*建立HttpPost连接*/ 
		httpRequest=new HttpPost(url); 
		Log.i("wsd","ur;:"+url);
		/*Post运作传送变数必须用NameValuePair[]阵列储存*/ 
		try { 
			//发出HTTP request 
			httpRequest.setEntity(new UrlEncodedFormEntity(paramList,HTTP.UTF_8)); 
			//取得HTTP response 
			httpResponse=new DefaultHttpClient().execute(httpRequest); 
			//若状态码为200 
			if(httpResponse.getStatusLine().getStatusCode()==200){ 
				//取出回应字串 
				result=EntityUtils.toString(httpResponse.getEntity()); 
			}else{ 
				Log.e("wsd","JSONParser POST ERROR");
			} 
		} catch (Exception e) { 
			Log.e("wsd","JSONParser POST ERROR");
		} 
//        Log.d(GV.TAG,result);
		return result;
	}

//--------------------------------------------------------------------
	
	
	
	public class JsonDataFromWeb {
		public int statuID;
		public String msg;
		public List<Map<String,String>> reArray;
		public JsonDataFromWeb(){
			this.statuID = 0;
			this.msg = "null";
			this.reArray = new ArrayList<Map<String,String>>();
		}
	}


}