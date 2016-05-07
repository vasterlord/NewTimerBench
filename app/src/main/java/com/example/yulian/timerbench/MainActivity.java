package com.example.yulian.timerbench;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageButton;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.app.Fragment;
import android.app.FragmentHostCallback;
import com.example.yulian.timerbench.R;
import com.example.yulian.timerbench.Stopwatch;
import com.example.yulian.timerbench.SportTimer;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton imgBtnSportTimer, imgBtnStopwatch, imgBtnReminder;
    SportTimer sportTimer;
    Stopwatch stopwatch;
    FragmentManager fTrans;
    private Camera mCamera;
    private Parameters mParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(" Sport&Fighting Timer");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sportTimer = new SportTimer();
        fTrans = getSupportFragmentManager();
        setTitle("Sport&Fighting Timer");
        fTrans.beginTransaction()
                .replace(R.id.fragreplace, sportTimer).commit();
        fTrans.addOnBackStackChangedListener(null);
        imgBtnSportTimer = (ImageButton) findViewById(R.id.imgBtnSportTimer);
        imgBtnStopwatch = (ImageButton) findViewById(R.id.imgBtnStopwatch);
        imgBtnReminder = (ImageButton) findViewById(R.id.imgBtnReminder);

    }
    public void onClick(View view) {
        fTrans = getSupportFragmentManager();
        sportTimer = new SportTimer();
        stopwatch = new Stopwatch();
        switch (view.getId()){
            case R.id.imgBtnSportTimer:
                setTitle("Sport&Fighting Timer");
                fTrans.beginTransaction()
                        .replace(R.id.fragreplace, sportTimer).commit();
                fTrans.addOnBackStackChangedListener(null);
                break;
            case R.id.imgBtnStopwatch :
                setTitle("StopWatch");
                fTrans.beginTransaction()
                        .replace(R.id.fragreplace, stopwatch).commit();
                fTrans.addOnBackStackChangedListener(null);
                break;
            case R.id.imgBtnReminder:
                setTitle("Alarm and Reminder");
                fTrans.addOnBackStackChangedListener(null);
                Context context = getApplicationContext();
                Intent notificationIntent = new Intent(context, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager nm = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Resources res = context.getResources();
                Notification.Builder builder = new Notification.Builder(context);
                Uri ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                long[] vibrate = new long[] { 1000, 1000, 1000, 1000 };
                builder.setContentIntent(contentIntent)
                        .setSmallIcon(R.drawable.timer)
                        .setWhen(System.currentTimeMillis())
                        .setSound(ringURI)
                        .setVibrate(vibrate)
                        .setContentTitle(" Notification ")
                        .setContentText(" Timer was finished! Good job ;)");
                Notification notification = builder.build();
                notification.defaults = Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE;
                notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS;
                notification.flags = notification.flags | Notification.FLAG_INSISTENT;
                Notification n = builder.getNotification();
                nm.notify(1, n);
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {

        }
        else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, Developers.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
