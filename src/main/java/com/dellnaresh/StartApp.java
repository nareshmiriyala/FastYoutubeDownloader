package com.dellnaresh;

import com.dellnaresh.app.DownloadJob;
import com.dellnaresh.workerpool.WorkerPool;

/**
 * Created by nareshm on 14/06/2015.
 */
public class StartApp {
    public static void main(String[] args) {
        WorkerPool instance = WorkerPool.getInstance();
        DownloadJob job=new DownloadJob("Download job1");
        job.setPath("C:\\\\Naresh Data\\\\Development Software\\\\Videos\\\\Android\\");
        job.setYoutubeUrl("https://www.youtube.com/watch?v=Meq-1HQdhbQ");
        instance.deployJob(job);
        if(job.getCurrentState()!=null && job.getCurrentState().equals(DownloadJob.States.DONE)){
            try {
                instance.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
