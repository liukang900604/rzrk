package com.rzrk.action;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.action.service.UserService;
import com.rzrk.entity.User;




@ParentPackage("basePackage")
@Action(value="userAction")//使用convention-plugin插件提供的@Action注解将一个普通java类标注为一个可以处理用户请求的Action，Action的名字为struts2Test
@Namespace("/")//使用convention-plugin插件提供的@Namespace注解为这个Action指定一个命名空间
public class UserAction {

@Resource
private UserService userService;

public void  testAction(){
	System.out.println("进入action");
	 User user = new User();
     //user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
     user.setUserName("测试名称");
     user.setSex("男");
     userService.save(user);
}
}
