/*
 * Copyright 2017-2023 Jiangdg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jiangdg.ausbc.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.PowerManager;

import androidx.core.content.ContextCompat;

/** Common Utils
 *
 * @author Created by jiangdg on 2021/12/27
 */
public final class Utils {

    public static boolean debugCamera = true;

    private Utils() {
        // Private constructor to prevent instantiation
    }

    public static boolean isTargetSdkOverP(Context context) {
        int targetSdkVersion;
        try {
            ApplicationInfo aInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            targetSdkVersion = aInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return targetSdkVersion >= Build.VERSION_CODES.P;
    }

    public static Location getGpsLocation(Context context) {
        if (context != null) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            int locPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
            if (locPermission == PackageManager.PERMISSION_GRANTED) {
                return locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
        }
        return null;
    }

    public static PowerManager.WakeLock wakeLock(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "jj:camera");
        mWakeLock.setReferenceCounted(false);
        mWakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
        return mWakeLock;
    }

    public static void wakeUnLock(PowerManager.WakeLock wakeLock) {
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    public static String getGLESVersion(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getDeviceConfigurationInfo().getGlEsVersion();
    }
}

