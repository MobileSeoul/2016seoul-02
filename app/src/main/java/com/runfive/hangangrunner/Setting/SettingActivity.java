package com.runfive.hangangrunner.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;

import com.runfive.hangangrunner.R;


public class SettingActivity extends PreferenceActivity {



    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_option);
        addPreferencesFromResource(R.xml.setting_main);
    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        //Toast.makeText(SettingActivity.this, "preference:"+preference.getKey(), Toast.LENGTH_SHORT).show();
        if(preference.getKey().equals("pref_display"))
        {
            CheckBoxPreference pref = (CheckBoxPreference) preference;
            if(pref.isChecked())
            {
                saveDisplayOnPreferences();
            }
            else
            {
                saveDisplayOffPreferences();
            }
        }
        else if(preference.getKey().equals("pref_voice"))
        {
            CheckBoxPreference pref = (CheckBoxPreference) preference;
            if(pref.isChecked())
            {
                saveVoiceOnPreferences();
            }
            else
            {
                saveVoiceOffPreferences();
            }
        }
        else if(preference.getKey().equals("pref_alarm"))
        {
            CheckBoxPreference pref = (CheckBoxPreference) preference;
            if(pref.isChecked())
            {
                saveAlarmOnPreferences();
            }
            else
            {
                saveAlarmOffPreferences();
            }
        }
        else if(preference.getKey().equals("pref_faq"))
        {
            createBackStack(new Intent(this, FaqActivity.class));
        }
        else
        {
            createBackStack(new Intent(this, AccessTermActivity.class));
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }



    @SuppressWarnings("StatementWithEmptyBody")



    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {

            startActivity(intent);
            finish();
        }
    }


    private void saveDisplayOffPreferences()
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("display", false);
        editor.commit();
    }

    private void saveDisplayOnPreferences()
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("display", true);
        editor.commit();
    }

    private void saveAlarmOffPreferences()
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("alarm", false);
        editor.commit();
    }

    private void saveAlarmOnPreferences()
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("alarm", true);
        editor.commit();
    }

    private void saveVoiceOffPreferences()
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("voice", false);
        editor.commit();
    }

    private void saveVoiceOnPreferences()
    {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("voice", true);
        editor.commit();
    }
}