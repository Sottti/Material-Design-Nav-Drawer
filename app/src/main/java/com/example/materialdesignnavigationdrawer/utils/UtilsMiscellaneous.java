package com.example.materialdesignnavigationdrawer.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;

public class UtilsMiscellaneous {

  public static int getThemeAttributeDimensionSize(@NonNull final Context context, final int attr) {
    TypedArray typedArray = null;

    try {
      typedArray = context.getTheme().obtainStyledAttributes(new int[]{attr});
      return typedArray.getDimensionPixelSize(0, 0);
    } finally {
      if (typedArray != null) {
        typedArray.recycle();
      }
    }
  }

}
