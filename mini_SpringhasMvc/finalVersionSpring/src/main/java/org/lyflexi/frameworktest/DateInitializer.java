package org.lyflexi.frameworktest;

import org.lyflexi.framework.web.bind.WebDataBinder;
import org.lyflexi.framework.web.bind.support.WebBindingInitializer;

import java.util.Date;

public class DateInitializer implements WebBindingInitializer{
	@Override
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class,"yyyy-MM-dd", false));
	}
}
