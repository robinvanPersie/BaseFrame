package com.antimage.basemodule.core;

/**
 * Created by xuyuming on 2019/6/12.
 */

public interface AppManager {

    /**
     * 是否首次启动应用
     */
    boolean isFirstLaunch();

    /**
     * 重启
     * @param killDelay  关闭延迟  ms
     * @param startDelay 启动延迟  ms
     */
    void restart(long killDelay, long startDelay);


    /**
     * 退出应用
     * @param delay 退出延迟  ms
     */
    void exit(long delay);
}
