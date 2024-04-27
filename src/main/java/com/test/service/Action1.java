package com.test.service;

import com.minis.beans.factory.annotation.Autowired;

public class Action1 implements IAction {
	@Autowired
	Action2 action2;

	@Override
	public void doAction() {
		System.out.println("really do action1");
		
	}

	@Override
	public void doSomething() {
		System.out.println("really do something action1");
	}

}
