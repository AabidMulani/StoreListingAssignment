package com.coupondunia.assignment.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.widget.Toast;

import com.coupondunia.assignment.R;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public class Utils {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showThisMsg(Context activity, String title, String message, DialogInterface.OnClickListener
            onOkClickListener, DialogInterface.OnClickListener onCancelClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null) {
            builder.setTitle(title);
            builder.setIcon(R.drawable.app_icon);
        } else {
            builder.setTitle(null);
        }
        builder.setMessage(message);
        builder.setPositiveButton(activity.getString(android.R.string.ok), onOkClickListener);
        if (onCancelClickListener != null) {
            builder.setNegativeButton(activity.getString(android.R.string.cancel), onCancelClickListener);
        }
        builder.setCancelable(false);
        AppCompatDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public static boolean checkInternetConnection(Context mContext) {
        boolean retVal = false;
        try {
            ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            retVal = conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr
                    .getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }


    public static Typeface getThisFont(Context context, int textStyleIndex) {
        final int FONT_BOLD = 1;
        final int FONT_LIGHT = 2;
        final int FONT_MEDIUM = 3;
        final int FONT_THIN = 4;

        Typeface typeface;
        switch (textStyleIndex) {
            case FONT_BOLD:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
                break;
            case FONT_LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
                break;
            case FONT_MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
                break;
            case FONT_THIN:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
                break;
            default:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
                break;
        }
        return typeface;
    }

}
