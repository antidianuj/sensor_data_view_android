package com.example.task_1;

import android.location.Location;
public class CLocation extends Location{
    private boolean bUseMetricUnits=false;
    public CLocation(Location location)
    {
        this(location,true);

    }
    public CLocation(Location location,boolean bUseMetricUnits)
    {
        super(location);
        this.bUseMetricUnits=bUseMetricUnits;


    }
    public boolean getUseMetricUnits()
    {
        return this.bUseMetricUnits;
    }
    public void setbUseMetricUnits(boolean bUseMetricUnits)
    {
        this.bUseMetricUnits=bUseMetricUnits;
    }

    @Override
    public float distanceTo(Location dest) {
        float nDistance=super.distanceTo(dest);
        return nDistance;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy=super.getAccuracy();
        return nAccuracy;
    }

    @Override
    public double getAltitude() {
        double nAltitude= super.getAltitude();
        return nAltitude;
    }

    @Override
    public float getSpeed() {
        float nSpeed=super.getSpeed();
        return nSpeed;
    }

    @Override
    public long getTime() {
        float nTime=super.getTime();
        return (long) nTime;
    }
    @Override
    public double getLongitude() {
        float nLongitude= (float) super.getLongitude();
        return nLongitude;
    }
    @Override
    public double getLatitude() {
        float nLatitude= (float) super.getLatitude();
        return nLatitude;
    }

}
