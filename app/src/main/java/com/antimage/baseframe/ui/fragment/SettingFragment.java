package com.antimage.baseframe.ui.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.antimage.baseframe.R;
import com.antimage.baseframe.core.AppConfig;
import com.antimage.baseframe.core.AppManager;
import com.antimage.baseframe.core.InjectConfig;
import com.antimage.baseframe.utils.android.DeviceUtils;
import com.antimage.baseframe.utils.android.SPUtils;
import com.antimage.baseframe.utils.android.ToastUtils;

/**
 * Created by xuyuming on 2018/11/15.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    AppConfig appConfig;
    AppManager appManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConfig = InjectConfig.get().appConfig();
        appManager = InjectConfig.get().appManager();

        addPreferencesFromResource(R.xml.setting_xml);

        Preference versionPreference = findPreference("key_version");
        versionPreference.setSummary("版本名： " + DeviceUtils.getVersionName() + "\n版本号：" + DeviceUtils.getVersionCode());

        ListPreference lp = (ListPreference) findPreference("key_list");
        lp.setOnPreferenceChangeListener(this);
        lp.setValueIndex(SPUtils.getEnvironment());
        lp.setSummary("当前环境:" + appConfig.getApiHost());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if ("key_list".equals(key)) {
            int value = Integer.parseInt(String.valueOf(newValue));
            SPUtils.setEnvironment(value);
            preference.setSummary("当前环境:" + appConfig.getApiHost());
            ToastUtils.toastShort("即将重启...");
            appManager.restartApp(4, 200);
            return true;
        }
        return false;
    }
}
