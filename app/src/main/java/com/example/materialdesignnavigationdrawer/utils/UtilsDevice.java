package com.example.materialdesignnavigationdrawer.utils;

import android.content.Context;
import android.support.annotation.NonNull;

public class UtilsDevice {

  public static int getScreenWidthInPx(@NonNull final Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }
}
