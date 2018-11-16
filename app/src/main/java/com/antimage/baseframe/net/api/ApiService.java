package com.antimage.baseframe.net.api;


import com.antimage.baseframe.model.Response;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by xuyuming on 2018/10/16.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("")
    Observable<Response> login();

    Observable<Response> register();

}
