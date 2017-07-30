package maheshwari.developer.android.nirbhaya.HelperClass;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.widget.Toast;
import maheshwari.developer.android.nirbhaya.Activity.AlertActivity;
import maheshwari.developer.android.nirbhaya.R;

public class MyService extends Service implements SensorEventListener
{
	SensorManager sm;
	Sensor s;
	static int y_accelleration;
	static int x_accelleration;
	static int z_accelleration;

	int tempx1, tempx2, tempy1, tempy2, tempz1, tempz2, differ1, differ2,differ3;
	int count = 1;

	public IBinder onBind(Intent arg0) 
	{
		return null;
	}

	public void onCreate()
	{
		super.onCreate();
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void onAccuracyChanged(Sensor arg0, int arg1)
	{
		
	}

	@SuppressLint({ "ShowToast", "Wakelock" })
	public void onSensorChanged(SensorEvent event)
	{
		double x = event.values[0];
		double y = event.values[1];
		double z = event.values[2];
		System.out.println("change");
		System.out.println("x value  " + x);
		System.out.println("y value  " + y);
		System.out.println("z value  " + z);
		Intent dialogIntent = new Intent(this, AlertActivity.class);
		Log.v("y:", "" + event.values[1]);
		y_accelleration = (int) event.values[1] * 10;
		x_accelleration = (int) event.values[0] * 10;
		z_accelleration = (int) event.values[2] * 10;

		if (count == 1)
		{
			tempx1 = (int) event.values[0] * 10;
			tempy1 = (int) event.values[1] * 10;
			tempz1 = (int) event.values[2] * 10;
			tempx2 = (int) event.values[0] * 10;
			tempy2 = (int) event.values[1] * 10;
			tempz2 = (int) event.values[2] * 10;
			count++;
		} 
		else
		{
			tempx1 = tempx2;
			tempy1 = tempy2;
			tempz1 = tempz2;
			tempx2 = (int) event.values[0] * 10;
			tempy2 = (int) event.values[1] * 10;
			tempz2 = (int) event.values[2] * 10;
			differ1 = tempx2 - tempx1;
			differ2 = tempy2 - tempy1;
			differ3 = tempz2 - tempz1;
			
			if ((differ1 >= 100) && (differ1 <= 100))
			{
				unLock();
				dialogIntent.setAction(Intent.ACTION_MAIN);
				dialogIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplication().startActivity(dialogIntent);
			}
			
			if ((differ1 >= (-100)) && (differ1 <= (-100)))
			{
				unLock();
				dialogIntent.setAction(Intent.ACTION_MAIN);
				dialogIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplication().startActivity(dialogIntent);
			}
		}
	}

	@Override
	public void onDestroy()
	{
		Toast.makeText(getApplicationContext(), "Service stopped",Toast.LENGTH_SHORT).show();
		sm.unregisterListener(this);
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId)
	{
        Intent notificationIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new Notification.Builder(this)
                .setTicker("New notification")
                .setContentTitle("Nirbhaya : Be Fearless!")
                .setContentText("Service Started!")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent).getNotification();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
		super.onStart(intent, startId);
	}

	@SuppressLint("Wakelock")
	@SuppressWarnings("deprecation")
	
	public void unLock()
	{
		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		final KeyguardManager.KeyguardLock kl = km
				.newKeyguardLock("MyKeyguardLock");
		kl.disableKeyguard();
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
		wakeLock.acquire();
	}
}
