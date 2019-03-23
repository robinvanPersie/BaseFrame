package com.antimage.baseframe.presenter;

import com.antimage.baseframe.core.InjectConfig;
import com.antimage.baseframe.net.api.ApiService;
import com.antimage.baseframe.presenter.base.ActivityPresenter;
import com.antimage.baseframe.ui.view.MaterialView;

import javax.inject.Inject;

/**
 * Created by xuyuming on 2019/3/23.
 */

public class MaterialPresenter extends ActivityPresenter<MaterialView> {

    private ApiService mApiService;

    @Inject
    public MaterialPresenter() {
        mApiService = InjectConfig.get().apiService();
    }

}
