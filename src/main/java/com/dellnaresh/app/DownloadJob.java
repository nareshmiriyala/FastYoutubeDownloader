package com.dellnaresh.app;

import com.dellnaresh.workerpool.WorkerThread;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by nareshm on 14/06/2015.
 */
public class DownloadJob extends WorkerThread {

    public static final String MP4 = ".mp4";
    private String youtubeUrl;
    private String path;
    /**
     * Notify States
     */
    public enum States {
        QUEUED, DOWNLOADING, RETRYING, ERROR, STOP, DONE;
    }
    private DownloadJob.States currentState=States.QUEUED;

    public DownloadJob(String s) {
        super(s);
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public States getCurrentState() {
        return currentState;
    }

    @Override
    public void processCommand() throws IOException {
        currentState=States.DOWNLOADING;
        YoutubeVideo youtubeVideo = new YoutubeVideo(youtubeUrl);
        String videoTitle = youtubeVideo.getTitle();
        File file = new File(path + videoTitle + MP4);
        URL downloadURL = youtubeVideo.findDefaultURL();
        downloadVideo(downloadURL, file);

    }

    private void downloadVideo(URL downloadURL, File file) throws IOException {
        FileUtils.copyURLToFile(downloadURL, file);
        currentState=States.DONE;
    }
}
