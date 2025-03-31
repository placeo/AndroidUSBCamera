package com.jiangdg.ausbc.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.media.Image;
import androidx.core.content.ContextCompat;
import com.jiangdg.ausbc.R;
import com.jiangdg.usb.DeviceFilter;
import timber.log.Timber;
import java.util.List;

/** Camera tools
 *
 * @author Created by jiangdg on 2022/7/19
 */
public final class CameraUtils {

    // Private constructor to prevent instantiation
    private CameraUtils() {
    }

    /**
     * check is usb camera
     *
     * @param device see {@link UsbDevice}
     * @return true usb camera
     */
    public static boolean isUsbCamera(UsbDevice device) {
        return true;
        /*Thread.currentThread().getStackTrace();
        for (StackTraceElement it : Thread.currentThread().getStackTrace()) {
            Timber.d("ASD " + it);
        }
        Timber.d("ASD isUsbCamera " + device);
        switch (device.getDeviceClass()) {
            case UsbConstants.USB_CLASS_VIDEO:
                return true;
            case UsbConstants.USB_CLASS_MISC:
                boolean isVideo = false;
                for (int i = 0; i < device.getInterfaceCount(); i++) {
                    int cls = device.getInterface(i).getInterfaceClass();
                    if (cls == UsbConstants.USB_CLASS_VIDEO) {
                        isVideo = true;
                        break;
                    }
                }
                return isVideo;
            default:
                return false;
        }*/
    }

    /**
     * Is camera contains mic
     *
     * @param device usb device
     * @return true contains
     */
    public static boolean isCameraContainsMic(UsbDevice device) {
        if (device == null) {
            return false;
        }
        boolean hasMic = false;
        for (int i = 0; i < device.getInterfaceCount(); i++) {
            int cls = device.getInterface(i).getInterfaceClass();
            if (cls == UsbConstants.USB_CLASS_AUDIO) {
                hasMic = true;
                break;
            }
        }
        return hasMic;
    }

    /**
     * Filter needed usb device by according to filter regular
     *
     * @param context  context
     * @param usbDevice see {@link UsbDevice}
     * @return true find success
     */
    public static boolean isFilterDevice(Context context, UsbDevice usbDevice) {
        List<DeviceFilter> filters = DeviceFilter.getDeviceFilters(context, R.xml.default_device_filter);
        for (DeviceFilter devFilter : filters) {
            if (usbDevice != null && devFilter.mProductId == usbDevice.getProductId() && devFilter.mVendorId == usbDevice.getVendorId()) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasStoragePermission(Context ctx) {
        int locPermission = ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return locPermission == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasCameraPermission(Context ctx) {
        int locPermission = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA);
        return locPermission == PackageManager.PERMISSION_GRANTED;
    }
}

