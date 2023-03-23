package com.sag.sagpro.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class VersionUtils {

    public static int VERSION_NAME = 0;
    public static int VERSION_CODE = 1;
    public static int VERSION_NAME_CODE = 2;

    public static String getPackageInfo(Context context, int type) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (type == VERSION_NAME)
                return pInfo.versionName;
            else if (type == VERSION_CODE)
                return "" + pInfo.versionCode;
            else
                return pInfo.versionName + "|" + pInfo.versionCode;
//            String versionName = pInfo.versionName;
//            int versionCode = pInfo.versionCode;
//            Log.d("Version", "Version name: " + versionName + ", Version code: " + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


}
