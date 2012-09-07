package com.distantsphere.pesterchum.mobile.irc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.distantsphere.pesterchum.mobile.MainActivity;
import com.distantsphere.pesterchum.mobile.R;

public class IRCService extends Service {
	private IRCConnection connection;
	private boolean foreground = false;
	
	public static final String ACTION_FOREGROUND = "com.distantsphere.pesterchum.mobile.service.foreground";
	public static final String ACTION_BACKGROUND = "com.distantsphere.pesterchum.mobile.service.background";
	public static final String ACTION_ACK_NEW_MENTIONS = "com.distantsphere.pesterchum.mobile.service.ack_new_mentions";
	public static final String EXTRA_ACK_SERVERID = "com.distantsphere.pesterchum.mobile.service.ack_serverid";
    public static final String EXTRA_ACK_CONVTITLE = "com.distantsphere.pesterchum.mobile.service.ack_convtitle";
	
    private static final int FOREGROUND_NOTIFICATION = 1;

	private NotificationManager notificationManager;
	private Notification notification;
	
	private final IRCBinder mBinder = new IRCBinder();
	
	public class IRCBinder extends Binder {
        public IRCService getService() {
            return IRCService.this;
        }
    }
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d("IRCService", "start");
		handleCommand(intent);
		
		return START_STICKY;
	}
	
	private void handleCommand(Intent intent) {
		Log.d("intent", intent.getAction());
		if (ACTION_FOREGROUND.equals(intent.getAction())) {
			if (foreground)
				return;
			
			foreground = true;
			
			notification = new Notification(R.drawable.ic_launcher, getText(R.string.notification_running), 0);
			
			Intent notifyIntent = new Intent(this, MainActivity.class);
            notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
            
            notification.setLatestEventInfo(this, getText(R.string.app_name), "pesterClient", contentIntent);
            
            startForeground(FOREGROUND_NOTIFICATION, notification);
		} else if (ACTION_BACKGROUND.equals(intent.getAction()) && !foreground) {
			stopForeground(true);
		} else if (ACTION_ACK_NEW_MENTIONS.equals(intent.getAction())) {
			//ackNewMentions(intent.getIntExtra(EXTRA_ACK_SERVERID, -1), intent.getStringExtra(EXTRA_ACK_CONVTITLE));
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

}
