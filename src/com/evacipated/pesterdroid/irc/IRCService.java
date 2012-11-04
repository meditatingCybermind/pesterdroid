package com.evacipated.pesterdroid.irc;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.evacipated.pesterdroid.R;
import com.evacipated.pesterdroid.MainActivity;

public class IRCService extends Service {
	private IRCConnection connection;
	private boolean foreground = false;
	
	public static final String ACTION_FOREGROUND = "com.evacipated.pesterchum.mobile.service.foreground";
	public static final String ACTION_BACKGROUND = "com.evacipated.pesterchum.mobile.service.background";
	public static final String ACTION_ACK_NEW_MENTIONS = "com.evacipated.pesterchum.mobile.service.ack_new_mentions";
	public static final String EXTRA_ACK_SERVERID = "com.evacipated.pesterchum.mobile.service.ack_serverid";
    public static final String EXTRA_ACK_CONVTITLE = "com.evacipated.pesterchum.mobile.service.ack_convtitle";
    public static final String ACTION_CONNECT = "com.evacipated.pesterchum.mobile.service.connect";
	
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
		} else if (ACTION_CONNECT.equals(intent.getAction())) {
			try {
				connect();
			} catch (NickAlreadyInUseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IrcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public void connect() throws NickAlreadyInUseException, IOException, IrcException {
		Log.w("connect()", "starting connection");
		connection = new IRCConnection();
		connection.setVerbose(true);
		connection.setNickname("mobileTest");
		connection.setRealName("pcm1");
		connection.connect("irc.mindfang.org");
	}
	
	public void disconnect() {
		if (connection != null) {
			connection.disconnect();
		}
	}
	
	@Override
	public void onDestroy() {
		disconnect();
		if (foreground) {
			stopForeground(true);
		}
	}
}
