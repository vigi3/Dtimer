package com.proj.dtimer;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PictureManager {

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
    private static PictureManager pictureInstance = null;

    static {
        pictureInstance = new PictureManager();
    }

    private PictureManager() {

    }


}
