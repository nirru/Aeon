package info.aeon.app.EMMAJAMESApp.push;


import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.frame.FrameActivity;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	public Intent notificationIntent;
	public PreferenceHelper pHelper;
	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification(extras);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification(extras);
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i = 0; i < 5; i++) {
					// Log.i("wsd", "Working... " + (i + 1) + "/5 @ "
					// + SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
				// Log.i("wsd",
				// "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				sendNotification(extras);
				// Log.i("wsd", "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	@SuppressLint("InlinedApi")
	private void sendNotification(Bundle msg) {
		pHelper=new PreferenceHelper(this);
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationIntent = new Intent(GcmIntentService.this,
				FrameActivity.class);
		notificationIntent.putExtras(msg);
		notificationIntent.putExtra("statue", true);
		notificationIntent.removeExtra("message");
		notificationIntent.putExtra("message", msg);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(
				GcmIntentService.this, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setTicker(msg.getString("message"))
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.drawable.ic_launcher)
				.setAutoCancel(true)
				.setContentTitle("AEON")
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(msg
								.getString("message")))
				.setContentText(msg.getString("message"))
				.setVibrate(new long[] { 0, 1000 });
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		String[] temp=msg.getString("message").split(":");
		if(!pHelper.getPushBrand().equals(temp[0])){
			pHelper.initPushData();
		}
		pHelper.setPushBrand(temp[0]);
		pHelper.setPushMessage(msg.getString("message"));
		pHelper.setPushBrandState(temp[1], true);

	}
	

}