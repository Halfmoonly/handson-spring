package com.test.service;

import com.minis.scheduling.annotation.Async;
import com.minis.scheduling.annotation.AsyncResult;
import com.minis.util.concurrent.FailureCallback;
import com.minis.util.concurrent.ListenableFuture;
import com.minis.util.concurrent.SuccessCallback;

/**
 * @Author: ly
 * @Date: 2024/1/16 20:41
 */
public class Action4 implements IAction{
    @Async
    public Boolean sayHello(SuccessCallback<? super Boolean> successCallback, FailureCallback failureCallback){
        System.out.println("Base Service says hello.Execute method asynchronously. "
                + Thread.currentThread().getName());
        ListenableFuture<Boolean> result = new AsyncResult<>(true);
        result.addCallback(successCallback,	failureCallback);

        return true;
    }

    @Async
    public ListenableFuture<Boolean> sayHello(){
        System.out.println("Base Service says hello.Execute method asynchronously. "
                + Thread.currentThread().getName());
        ListenableFuture<Boolean> result = new AsyncResult<>(true);

        return result;
    }
    @Override
    public void doAction() {

    }

    @Override
    public void doSomething() {

    }
}
