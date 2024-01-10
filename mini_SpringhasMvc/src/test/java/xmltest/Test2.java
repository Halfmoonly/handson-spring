package xmltest;


import org.lyflexi.framework.context.ClassPathXmlApplicationContext;
import org.lyflexi.frameworktest.service.AService;
import org.lyflexi.frameworktest.service.BaseBaseService;
import org.lyflexi.frameworktest.service.BaseService;

public class Test2 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
