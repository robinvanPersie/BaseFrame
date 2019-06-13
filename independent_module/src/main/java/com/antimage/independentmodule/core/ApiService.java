package com.antimage.independentmodule.core;

import com.antimage.basemodule.model.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xuyuming on 2019/6/13.
 */

public interface ApiService {

    @GET("open/api/weather/json.shtml?")
    Observable<Response<String>> getWeather(@Query("city") String city);
}
