package com.board.test;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloWorldController {

	
	@Autowired BoardDao boardDao;
	@Autowired UserInfoDao userInfoDao;

	@RequestMapping("/hello")

	public String hello() {

		return "hello, world!!";

	}
	
	
	@RequestMapping("/add")
	public UserInfo add(UserInfo userInfo) {
		
		LocalDateTime date = LocalDateTime.now();
		
		
		userInfo.setUSER_ID("JSH");
		userInfo.setUSER_NAME("정석헌");
		userInfo.setPWD("1234");
		userInfo.setWRITE_ID(userInfo.getUSER_ID());
		userInfo.setWRITE_DATE(date);
		userInfo.setUSE_YN("Y");
		
		UserInfo userInfoData = userInfoDao.save(userInfo);
		
		return userInfoData;
		
	}
	
	
	@RequestMapping("/list")
	public UserInfo list(Model model) {
		
		List<UserInfo> userInfoList = userInfoDao.findAll();
		
		return (UserInfo) userInfoList;
		
	}
	
	
	

}
