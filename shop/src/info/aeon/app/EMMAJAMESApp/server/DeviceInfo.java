package info.aeon.app.EMMAJAMESApp.server;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceInfo {

	private Context context;

	public DeviceInfo(Context context) {
		super();
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public String getDeviceId(){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	     String uuid = tm.getDeviceId();
	     return uuid;
	}
	
}
