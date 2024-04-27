package com.test.service;

import com.minis.beans.factory.annotation.Autowired;

public class Action2 implements IAction {
	@Autowired
	Action1 action1;
	@Override
	public void doAction() {
		System.out.println("really do action2");
		
	}

	@Override
	public void doSomething() {
		System.out.println("really do something action2");
	}

}
