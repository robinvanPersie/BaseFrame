package com.antimage.baseframe.net.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by xuyuming on 2018/10/23.
 */

public interface DownloadService {

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url, @Header("Range") String range);
}
