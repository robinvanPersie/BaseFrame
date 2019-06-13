package com.antimage.independentmodule.test;

import com.antimage.basemodule.exception.ApiExceptionHelper;
import com.antimage.basemodule.presenter.ActivityPresenter;
import com.antimage.independentmodule.core.ApiService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by xuyuming on 2019/6/13.
 */

public class TestPresenter extends ActivityPresenter<TestView> {

    private final ApiService mApiService;

    @Inject
    public TestPresenter(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public void onAttachView(TestView testView) {
        super.onAttachView(testView);
        mApiService.getWeather("上海")
                .compose(bindLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mV.success(response.getData());
                }, ApiExceptionHelper.normal()::process);
    }
}
