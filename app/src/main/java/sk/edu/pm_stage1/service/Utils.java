package sk.edu.pm_stage1.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
  private static final String TAG = Utils.class.getName();
  public static final String SHORT_FORMAT = "MM/dd/yyyy";
  public static final String MILITARY_SHORT_FORMAT = "yyyy-MM-dd";
  public static final String EMPTY_STRING = "";

  public static boolean isNetworkAvailable(Context appContext) {
    final ConnectivityManager connectivityManager =
        (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetworkInfo != null) {
      return activeNetworkInfo.isConnected();
    }
    return false;
  }

  public static Date convertStringToDate(final String dateStr, final String format) {
    if (TextUtils.isEmpty(dateStr)) {
      return null;
    }
    try {
      final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      return dateFormat.parse(dateStr);
    } catch (ParseException e) {
      Log.e(TAG, e.getMessage());
      return null;
    }
  }

  public static String convertDateToString(final Date date, final String format) {
    if (date == null) {
      return EMPTY_STRING;
    }
    final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(date);
  }
}
