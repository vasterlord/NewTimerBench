package com.example.yulian.timerbench;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.concurrent.TimeUnit;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button btnStart, btnStop,btnSetTime;
    TextView textViewTime;
    public Typeface tf;
    FrameLayout frameTime;
    LinearLayout pincers;
    String tm;
    long timeToEnd = 30000;
    private static int sHour;
    private static int sMinute;
    private static int sSecond;
    private static long time;
    private static long timePause;
    public static void setTimePause(long timePause) {
        MainActivity.timePause = timePause;
    }

    CounterClass timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        pincers = (LinearLayout) findViewById(R.id.pincers);
        frameTime = (FrameLayout) findViewById(R.id.frameTime);
        textViewTime = (TextView)findViewById(R.id.textViewTime);
       // tf = Typeface.createFromAsset(getAssets(), "ds-digital.ttf");
        //textViewTime.setTypeface(tf);
        final NumberPicker npHours = (NumberPicker) findViewById(R.id.npHours);
        final NumberPicker npMinutes = (NumberPicker) findViewById(R.id.npMinute);
        final NumberPicker npSeconds = (NumberPicker) findViewById(R.id.npSecond);

        npHours.setMaxValue(23);
        npHours.setMinValue(0);
        npHours.setWrapSelectorWheel(false);
        npMinutes.setMaxValue(59);
        npMinutes.setMinValue(0);
        npMinutes.setWrapSelectorWheel(false);
        npSeconds.setMaxValue(59);
        npSeconds.setMinValue(0);
        npSeconds.setWrapSelectorWheel(false);
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().toString().equals("Start")) {
                    MainActivity.sHour = npHours.getValue();
                    MainActivity.sMinute = npMinutes.getValue();
                    MainActivity.sSecond = npSeconds.getValue();
                    MainActivity.time = (1000 * (60 * (60 * MainActivity.sHour)) + 1000 * (60 * MainActivity.sMinute) + 1000 * (MainActivity.sSecond) + 500);
                    timer = new CounterClass(MainActivity.time, 1000);
                    timer.start();
                    btnStart.setText("Pause");
                    btnStop.setText("Cancel");
                    pincers.setVisibility(View.INVISIBLE);
                   frameTime.setVisibility(View.VISIBLE);
                } else if (btnStart.getText().toString().equals("Resume")) {
                    MainActivity.time = MainActivity.timePause;
                    timer = new CounterClass(MainActivity.time, 1000);
                    timer.start();
                    btnStart.setText("Pause");
                    btnStop.setText("Cancel");
                    pincers.setVisibility(View.INVISIBLE);
                    frameTime.setVisibility(View.VISIBLE);
                } else {
                    timer.cancel();
                    btnStart.setText("Resume");
                    btnStop.setText("Cancel");
                }
            }

        });
        btnStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStop.getText().toString().equals("Cancel")) {
                    timer.onFinish();
                    timer.cancel();
                  //  frameTime.setVisibility(View.INVISIBLE);
                    btnStart.setText("Start");
                    btnStop.setText("Reset");
                } else {
                    btnStart.setText("Start");
                    npHours.setValue(0);
                    npMinutes.setValue(0);
                    npSeconds.setValue(0);
                    textViewTime.setText("00:00:00");
                }
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            pincers.setVisibility(View.VISIBLE);
            textViewTime.setText("End now");
            btnStart.setText("Start");
        }
        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textViewTime.setText(hms);
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
