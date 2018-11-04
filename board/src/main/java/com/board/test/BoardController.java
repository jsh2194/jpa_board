package com.board.test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;




@Slf4j
//@RestController
@Controller

public class BoardController {

	
	@Autowired BoardDao boardDao;
	@Autowired UserInfoDao userInfoDao;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	
	//메인페이지 이동
	@RequestMapping(value={"/Main"}, method=RequestMethod.GET)
    public ModelAndView getMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Main.html");
        return modelAndView;
    }
	
	//회원가입페이지 이동
	@RequestMapping(value={"/SignUp"}, method=RequestMethod.GET)
	public ModelAndView getSignUp() {
		logger.debug("/SignUp debug");
		logger.info("/SignUp info");
		logger.trace("/SignUp trace");
		System.out.println("/SignUp trace start");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("SignUp.html");
		return modelAndView;
	}
	
	


	@RequestMapping("/hello")

	public String hello() {
		
//		Logger logger = LoggerFactory.getLogger("Hello page start");
		
		logger.trace("Hello world trace");
		logger.debug("Hello world debug");
		logger.info("Hello world info");
		logger.warn("Hello world warn");
		logger.error("Hello world error");
		
		log.debug("aaaaa");
		log.debug("aaaaa");
		log.debug("id {}", "AAA");
		log.info("bbbbbbbbbbb");
		log.info("ASDASDASD");
		log.trace("ASDASDASD");
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");

		return "hello, world!!";

	}
	
	
		//게시판 정보 조회

		@RequestMapping("/list")

		public String list(Model model) {

			List<BoardEntity> boardListData =  boardDao.findAll();

			List<UserInfoEntity> userInfoList =  userInfoDao.findAll();
			
			System.out.println(userInfoList.toString());
			
			

			model.addAttribute("name", "가나다라");
			model.addAttribute("userInfoList", userInfoList);
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%" + userInfoList.size());
			
			return  "Board";

			

		}

		

		@RequestMapping("/add")

		public UserInfoEntity add(UserInfoEntity userInfo) {
			

			LocalDateTime date = LocalDateTime.now();

			userInfo.setUSER_ID("JSH");

			userInfo.setUSER_NAME("정석헌");

			userInfo.setPWD("1234");

			userInfo.setWRITE_ID(userInfo.getUSER_ID());

			userInfo.setWRITE_DATE(date);

			userInfo.setUSE_YN("Y");

			UserInfoEntity userInfoData = userInfoDao.save(userInfo);

			return userInfoData;


		}
		
		
		@RequestMapping("/addUser11")
		public String addUser11(Model model) {
			
			System.out.println("addUser start");
			System.out.println(model.toString());
			
			ModelAndView mv = new ModelAndView();;
			
			
			UserInfoEntity userInfo = new UserInfoEntity();
			LocalDateTime date = LocalDateTime.now();
			
			userInfo.setUSER_ID("JSH");
			
			userInfo.setUSER_NAME("정석헌");
			
			userInfo.setPWD("1234");
			
			userInfo.setWRITE_ID(userInfo.getUSER_ID());
			
			userInfo.setWRITE_DATE(date);
			
			userInfo.setUSE_YN("Y");
			
			UserInfoEntity userInfoData = userInfoDao.save(userInfo);
			
			System.out.println("@@@@@@@@  : " + userInfoData.toString());
			

//			return "Board";
			return "forward:/list";
			
			
		}

		@RequestMapping("/addUser")
		@ResponseBody 
		public Map<String, Object> addUser(UserInfoEntity  userInfo) {
			
			System.out.println("addUser start");
			System.out.println(userInfo.toString());
			
//			ModelAndView mv = new ModelAndView();
			
			Map<String, Object> mv = new HashMap<String, Object>(); 
			
			try {
				
			
			
			LocalDateTime date = LocalDateTime.now();
			
			
			userInfo.setUSER_ID(String.valueOf(userInfo.getUSER_ID()));
			
			userInfo.setUSER_NAME(String.valueOf(userInfo.getUSER_NAME()));
			
			userInfo.setPWD(String.valueOf(userInfo.getPWD()));
			
			userInfo.setWRITE_ID(String.valueOf(userInfo.getUSER_ID()));
			
			userInfo.setWRITE_DATE(date);
			
			userInfo.setUSE_YN("Y");
			
			UserInfoEntity userInfoData = userInfoDao.save(userInfo);
			
			System.out.println("@@@@@@@@  : " + userInfoData.toString());
			
//			mv.addObject("result", "Y");
			mv.put("result", "Y");
			
			}catch (Exception e) {
				mv.put("result", "N");
			}
			
			return mv;
			
			
		}
		
		
	

}
