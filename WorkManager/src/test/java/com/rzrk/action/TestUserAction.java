package com.rzrk.action;

import java.util.UUID;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rzrk.action.service.UserService;
import com.rzrk.entity.User;

public class TestUserAction {

	@Test
    public void test(){
        //通过spring.xml配置文件创建Spring的应用程序上下文环境
	    ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-hibernate.xml"});
        //从Spring的IOC容器中获取bean对象
        UserService userService =  (UserService) ac.getBean("userServiceImpl");
        SessionFactory sessionFactory =  (SessionFactory) ac.getBean("sessionFactory");
        //执行测试方法
       // userService.test();
        User user = new User();
        //user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setUserName("测试名称1");
        user.setSex("男");
        userService.save(user);
    }
}
