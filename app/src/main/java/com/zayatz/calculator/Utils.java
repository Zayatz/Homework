package com.zayatz.calculator;

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
                break;
            case Constants.THEME_ON:
                activity.setTheme(R.style.switchOnStyle);
                break;
        }
    }
}