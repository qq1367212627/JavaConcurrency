/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package indi.lewis;

public class FutureData implements Data{

    private RealData realData;
    private volatile boolean isReady = false;

    public synchronized String getRequest() {
        while(!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realData.getRequest();
    }

    public synchronized void setRealData(RealData realData) {
        if(isReady){
            return ;
        }
        this.realData = realData;
        isReady = true;
        notify();
        this.realData = realData;
    }
}
