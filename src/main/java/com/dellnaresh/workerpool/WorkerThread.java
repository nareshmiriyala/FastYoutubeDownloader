package com.dellnaresh.workerpool;

import java.io.IOException;

/**
 * Created by nareshm on 12/3/14.
 */
public abstract class WorkerThread extends AbstractJob {
    private String command;

    public WorkerThread(String s) {
        super(s);
        this.command = s;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
        try {
            processCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " End.");
    }

    public abstract void processCommand() throws IOException;

    @Override
    public String toString() {
        return this.command;
    }
}
