package com.rzrk.action.service.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.action.service.UserService;
import com.rzrk.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private SessionFactory sessionFactory;
	
	public void test() {
	
		System.out.println("测试成功");
		
	}

	@Override
	public void save(User user) {
		
		Session session  = sessionFactory.openSession();
		session.save(user);
	}

}
