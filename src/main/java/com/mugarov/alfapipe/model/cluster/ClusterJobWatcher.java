/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.cluster;

import com.mugarov.alfapipe.model.ParameterPool;
import java.util.Timer;
import java.util.TimerTask;


/**
 *
 * @author mugarov
 */
public class ClusterJobWatcher {
    private final int waitingTime = ParameterPool.CLUSTER_WAITING_TIME;
    
    private Ticker tick;
    private Timer timer;
    
    
    /**
     * TODO:
     * 
     * 
     */
    public ClusterJobWatcher(){

        
    }
    
    public void run() throws InterruptedException{
        this.tick = new Ticker();
        this.timer = new Timer();
        this.tick.reset();
        this.timer.schedule(tick, waitingTime, waitingTime);
        while(!tick.isDone()){
            // do nothing
            
        }
        this.timer.cancel();
        System.out.println("Timer done, continue.");
        return;
    }
   
    
    public boolean isDone(){
        return this.tick.isDone();
    }
    
    /**
     * TODO: Cancel all Jobs with qdel "jobid"
     */
    public void cancelAll(){
        
    }

    private static class Ticker extends TimerTask{
        private boolean done;
        //for testing:
        private int numberOfChecks;

        public Ticker() {
            this.numberOfChecks = 10;
            this.done = false;
        }
        
        @Override
        public void run(){
            this.done = this.check();
        }
        
        private boolean check(){
            
            if(this.numberOfChecks > 0){
                this.numberOfChecks = this.numberOfChecks -1;
                System.out.println("Ticking another "+this.numberOfChecks+" Times");
                return false;
            }
            else{
                return true;
            }
            
        }
        
        public boolean isDone(){
            return this.done;
        }
        
        public void reset(){
            this.numberOfChecks = 10;
            this.done = false;
        }
    }
    
}
