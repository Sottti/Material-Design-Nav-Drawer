package com.sottocorp.materialdesignnavdrawer.utils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * This utility class is for device related stuff.
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
    public static int getScreenWidth(@NonNull final Context context)
    {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
