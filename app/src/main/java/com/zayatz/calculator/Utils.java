package com.zayatz.calculator;

/**
 * Created by Zayatz on 24.02.2016.
 */
import android.app.Activity;
import android.content.Intent;

public class Utils
{
    private static int sTheme;

    /* задає тему отриманому актівіті та перезапускає його

     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /* задає тему згідно з вибором, передає через інтент стан switchBtn */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case Constants.THEME_OFF:
                activity.setTheme(R.style.switchOffStyle);
                Intent intent = new Intent().putExtra(Constants.GET_EXTRA, false);
                activity.setIntent(intent);
                break;
            case Constants.THEME_ON:
                activity.setTheme(R.style.switchOnStyle);
                intent = new Intent().putExtra(Constants.GET_EXTRA, true);
                activity.setIntent(intent);
                break;
        }
    }
}