package com.demo.materialdesignnavdrawer.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * This utility class is for device related stuff.
 *
 * @author Sotti https://plus.google.com/+PabloCostaTirado/about
 */
public class UtilsDevice
{
    /**
     * Returns the screen width in pixels
     *
     * @param context is the context to get the resources
     *
     * @return the screen width in pixels
     */
    public static int getScreenWidth(Context context)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return metrics.widthPixels;
    }
}
