package com.example.task_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.DriverPropertyInfo;


public class MainActivity extends AppCompatActivity implements LocationListener,SensorEventListener {
    SwitchCompat sw_check;
    TextView tv_speed,tv_time,tv_longitude,tv_latitude;
    private TextView xTextView,yTextView,zTextView;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAccelerometerSensorAvailable, itIsNotFirstTime=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw_check = findViewById(R.id.sw_check);

        tv_speed = findViewById(R.id.tv_speed);
        tv_time = findViewById(R.id.tv_time);
        tv_longitude = findViewById(R.id.tv_longitude);
        tv_latitude = findViewById(R.id.tv_latitude);

        xTextView=findViewById(R.id.xTextView);
        yTextView=findViewById(R.id.yTextView);
        zTextView=findViewById(R.id.zTextView);

        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null)
        {
            accelerometerSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable=true;
        }
        else
        {
            xTextView.setText("Accelerometer sensor is not available");
            isAccelerometerSensorAvailable=false;
        }




//check for gps permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);

        } else {
            //start the program if the permission is granted
            doStuff();
        }
        this.updateSpeed(null);
        sw_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.this.updateSpeed(null);
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location!=null){
            CLocation myLocation=new CLocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
            
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Toast.makeText(this,"Waiting for GPS connection!",Toast.LENGTH_SHORT).show();
        }

    }
    private void updateSpeed(CLocation location)
    {
        float nCurrentSpeed=0;
        float nCurrentTime=0;
        float nCurrentLongitude=0;
        float nCurrentLatitude=0;
        if(location!=null)
        {
            location.setbUseMetricUnits(this.useMetricUnits());
            nCurrentSpeed=location.getSpeed();
            nCurrentTime=location.getTime();
            nCurrentLongitude= (float) location.getLongitude();
            nCurrentLatitude= (float) location.getLatitude();


        }
        Formatter fmt=new Formatter(new StringBuffer());
        Formatter fmt1=new Formatter(new StringBuffer());
        Formatter fmt2=new Formatter(new StringBuffer());
        Formatter fmt3=new Formatter(new StringBuffer());
        fmt.format(Locale.US, "%5.1f",nCurrentSpeed);
        fmt1.format(Locale.US, "%5.1f",nCurrentTime);
        fmt2.format(Locale.US, "%5.1f",nCurrentLongitude);
        fmt3.format(Locale.US, "%5.1f",nCurrentLatitude);

        String strCurrentSpeed=fmt.toString();
        String strCurrentTime=fmt1.toString();
        String strCurrentLongitude=fmt2.toString();
        String strCurrentLatitude=fmt3.toString();
        strCurrentSpeed=strCurrentSpeed.replace(" ","0");
        strCurrentTime=strCurrentTime.replace(" ","0");
        strCurrentLongitude=strCurrentLongitude.replace(" ","0");
        strCurrentLatitude=strCurrentLatitude.replace(" ","0");
        if(this.useMetricUnits())
        {
            tv_speed.setText(strCurrentSpeed + "km/h");
            tv_time.setText(strCurrentTime + "h");
            tv_longitude.setText(strCurrentLongitude + "deg");
            tv_latitude.setText(strCurrentLatitude + "deg");

        }
        else
        {
            tv_speed.setText(strCurrentSpeed + "km/h");
            tv_time.setText(strCurrentSpeed + "h");
            tv_longitude.setText(strCurrentSpeed + "deg");
            tv_latitude.setText(strCurrentSpeed + "deg");
        }
    }

    private boolean useMetricUnits()
    {
        return  sw_check.isChecked();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1000)
        {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                doStuff();

            }
            else
            {
                finish();

            }
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        xTextView.setText(sensorEvent.values[0]+"m/s/s");
        yTextView.setText(sensorEvent.values[1]+"m/s/s");
        zTextView.setText(sensorEvent.values[2]+"m/s/s");



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isAccelerometerSensorAvailable)
            sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isAccelerometerSensorAvailable)
            sensorManager.unregisterListener(this);
    }




//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(isAccelerometerSensorAvailable)
//            sensorManager.registerListener((SensorEventListener) this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
//
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(isAccelerometerSensorAvailable)
//            sensorManager.unregisterListener(this);
//    }
}

