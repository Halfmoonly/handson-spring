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
        //这和AIO的异步本质是一样的：AIO用户在进行读写操作时，主线程自己不去等待获取结果（一个线程），而是新起一个子线程（一个线程）向底层操作系统注册回调函数，最后操作系统执行回调函数并向用户程序送结果
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
                ex->System.out.println("sucess "+ex));//回调函数onSuccess和onFailure都传递给了Spirng框架，由Spring框架去监听返回结果是否已经创建完成，并主动推送给应用程序（我们）。这里的Spring框架就可以类比于操作系统底层

        System.out.println("sayHello's main finish....");
    }


}
