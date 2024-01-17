package com.test.service;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Author: ly
 * @Date: 2024/1/16 20:06
 */
public class ExecutorTest implements ThreadPoolService{
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    public void sayHello() {
        taskExecutor.submitListenable(()->{
            try {
                Thread.sleep(2000);
                System.out.println("Thread " + Thread.currentThread().getName());
                return 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;
        }).addCallback(data->{System.out.println("sucess "+data);},
                ex->System.out.println("sucess "+ex));
    }


}
