package com.antimage.baseframe.net.download;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by xuyuming on 2018/10/23.
 */

public class ResponseBodyWrapper extends ResponseBody {

    private ResponseBody body;
    private IDownloadCallback listener;
    private BufferedSource bufferedSource;

    public ResponseBodyWrapper(ResponseBody body, IDownloadCallback listener) {
        this.body = body;
        this.listener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() {
        return body.contentLength();
    }


    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(body.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {

            long totalBytesRead = 0l;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);

                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (listener != null) {
                    listener.onProgress(totalBytesRead, contentLength());
                }
                return bytesRead;
            }
        };
    }
}
