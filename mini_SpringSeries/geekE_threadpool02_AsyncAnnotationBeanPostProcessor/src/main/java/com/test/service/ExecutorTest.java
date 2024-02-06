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
                ex->System.out.println("sucess "+ex));//回调函数onSuccess和onFailure都传递给了Spirng框架，由Spring框架去监听返回结果是否已经创建完成，并主动推送给应用程序（我们）
    }


}
