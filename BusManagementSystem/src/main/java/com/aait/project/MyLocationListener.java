package com.aait.project;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        // Retrieve the updated GPS coordinates
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Process or store the coordinates as needed
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);

        // TODO: Add your custom logic here to handle the updated coordinates
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Handle status changes of the location provider
        switch (status) {
            case LocationProvider.AVAILABLE:
                // Location provider is available
                System.out.println("Location provider is available");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                // Location provider is out of service
                System.out.println("Location provider is out of service");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                // Location provider is temporarily unavailable
                System.out.println("Location provider is temporarily unavailable");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Handle when the location provider is enabled
        System.out.println("Location provider enabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Handle when the location provider is disabled
        System.out.println("Location provider disabled: " + provider);
    }
}
