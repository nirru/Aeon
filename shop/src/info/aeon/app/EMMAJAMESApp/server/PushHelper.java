package info.aeon.app.EMMAJAMESApp.server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.Header;
import org.apache.http.util.EncodingUtils;

import info.aeon.app.EMMAJAMESApp.frame.GV;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class PushHelper {
	
	public static final String TAG = "push";

	private Activity activity;
	String SENDER_ID = "423093158039";
	public static final String EXTRA_MESSAGE = "message";
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	public AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	TextView mDisplay;
	public String regid;
	private PreferenceHelper pHelper;
	private DeviceInfo deviceinfo;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	public PushHelper(Activity activity) {
		this.activity=activity;
		pHelper=new PreferenceHelper(activity);
		deviceinfo=new DeviceInfo(activity);
	}
	
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public String getRegistrationId(Context context) {
		String registrationId = pHelper.getPROPERTY_REG_ID();
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = pHelper.getAppVersion();
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	public void registerInBackground() {
		new AsyncTask() {
			protected void onPostExecute(String msg) {
				mDisplay.append(msg + "\n");
			}

			@Override
			protected Object doInBackground(Object... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(activity);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;
					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(activity, regid);
					request(regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}
		}.execute(null, null, null);
	}

	private void sendRegistrationIdToBackend() {
		// Your implementation here.
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		pHelper.setRegidAndAppVersion(regId, appVersion);
	}	
	
	 /**
	 * @return Application's {@code SharedPreferences}.
	 */
	 public SharedPreferences getGCMPreferences(Context context) {
	 // This sample app persists the registration ID in shared preferences,
	 // but
	 // how you store the regID in your app is up to you.
		 return pHelper.getPreferences();
	 }
	 
		public void request(String rid){
			RequestParams p = new RequestParams();
			p.put("udid", rid);
			p.put("device", "1");
			p.put("deviceid", deviceinfo.getDeviceId());
//			String temp=deviceinfo.getDeviceId();
			asyncHttpClient.post(GV.JSON_URL_START, p,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode,Header[] headers, byte[] responseBody) {
							// TODO Auto-generated method stub
							String res = EncodingUtils.getString(
									responseBody, "UTF-8");
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							// TODO Auto-generated method stub
							super.onFailure(arg0, arg1, arg2, arg3);
						}

					});
		}

		
		public void checkGooglePlay(){
			if (checkPlayServices()) {
				gcm = GoogleCloudMessaging.getInstance(activity);
				regid = getRegistrationId(activity);
				if (regid.isEmpty()) registerInBackground();
				else request(regid);
			} else {
				Log.i(TAG, "No valid Google Play Services APK found.");
			}
		}
		
		
		/**
		 * Check the device to make sure it has the Google Play Services APK. If it
		 * doesn't, display a dialog that allows users to download the APK from the
		 * Google Play Store or enable it in the device's system settings.
		 */
		private boolean checkPlayServices() {
			int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
			if (resultCode != ConnectionResult.SUCCESS) {
				if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
					GooglePlayServicesUtil.getErrorDialog(resultCode, activity,PLAY_SERVICES_RESOLUTION_REQUEST).show();
				} else {
					Log.i(TAG, "This device is not supported.");
				}
				return false;
			}
			return true;
		}
	
}
