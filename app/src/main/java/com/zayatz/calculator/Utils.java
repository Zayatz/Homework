package com.zayatz.calculator;

/**
 * Created by Zayatz on 24.02.2016.
 */
import android.app.Activity;
import android.content.Intent;

public class Utils
{
    private static int sTheme;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case Constants.THEME_OFF:
                activity.setTheme(R.style.switchOffStyle);
                Intent intent = new Intent();
                intent.putExtra(Constants.GET_EXTRA, false);
                activity.setIntent(intent);
                break;
            case Constants.THEME_ON:
                activity.setTheme(R.style.switchOnStyle);
                intent = new Intent();
                intent.putExtra(Constants.GET_EXTRA, true);
                activity.setIntent(intent);
                break;
        }
    }
}