package com.board.test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.board.test.entity.BoardEntity;
import com.board.test.entity.UserInfoEntity;
import com.board.test.entity.jpaRepository.BoardEntityJpaRepository;
import com.board.test.entity.jpaRepository.UserInfoEntityJpaRepository;

import lombok.extern.slf4j.Slf4j;






@Slf4j
//@RestController
@Controller
public class BoardController {

//	@Autowired(required=true)
//	private HttpServletRequest request;
	
	@Autowired BoardEntityJpaRepository boardEntityJpaRepository;
	@Autowired UserInfoEntityJpaRepository userInfoEntityJpaRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	
	//메인페이지 이동
	@RequestMapping(value={"/Main"}, method=RequestMethod.GET)
    public ModelAndView getMain(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Main.html");
        return modelAndView;
    }
	
	//회원가입페이지 이동
	@RequestMapping(value={"/SignUp"}, method=RequestMethod.GET)
	public ModelAndView getSignUp(HttpServletRequest req, HttpServletResponse resp) {
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

		@RequestMapping("/BoardList")

		public String BoardList(HttpServletRequest req, HttpServletResponse resp, Model model) {

			List<BoardEntity> boardList =  boardEntityJpaRepository.findAll();


			model.addAttribute("boardList", boardList);
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%" + boardList.size());
			
			return  "BoardList";

			

		}

		
		//게시판 글쓰기
		@RequestMapping("/BoardWrite")
		public String BoardWrite(HttpServletRequest req, HttpServletResponse resp, Model model) {
			
			List<BoardEntity> boardList =  boardEntityJpaRepository.findAll();
			
			System.out.println("sesseion id  :  " + req.getSession().getAttribute("sessionId"));
			
			UserInfoEntity userInfo = userInfoEntityJpaRepository.findByUserId(String.valueOf(req.getSession().getAttribute("sessionId")));
			
			
			model.addAttribute("boardList", boardList);
			model.addAttribute("userInfo", userInfo);
			
			System.out.println("userInfo " + userInfo);
			System.out.println("boardList " + boardList.size());
			
			return  "BoardWrite";
			
			
			
		}
		
		
		
		//글저장
		@RequestMapping("/saveWrite")
		@ResponseBody 
		public Map<String, Object> saveWrite(HttpServletRequest req, HttpServletResponse resp,
				                                    BoardEntity boardInfo, UserInfoEntity  userInfo) {
			
			System.out.println("saveWrite start");
			
			Map<String, Object> mv = new HashMap<String, Object>(); 
			
			try {
				
				LocalDateTime date = LocalDateTime.now();
				
				boardInfo.setTitle(String.valueOf(boardInfo.getTitle()));
				
				boardInfo.setContents(String.valueOf(boardInfo.getContents()));
				
				boardInfo.setWriteId(String.valueOf(req.getSession().getAttribute("sessionId")));
				
				boardInfo.setWriteDate(date);
				boardInfo.setUseYn("Y");
				
				
				BoardEntity boardInfoData = boardEntityJpaRepository.save(boardInfo);
				
				System.out.println("@@@@@@@@  : " + boardInfo.toString());
				
	//			mv.addObject("result", "Y");
				mv.put("result", "Y");
				mv.put("no", boardInfoData.getNo());
				
			
			}catch (Exception e) {
				mv.put("result", "N");
			}
			
			return mv;
			
			
		}
		
		
		

		@RequestMapping(value={"/userList"}, method=RequestMethod.GET)
		public String userList(HttpServletRequest req, HttpServletResponse resp, Model model) {
			
			
			
			
			List<BoardEntity> boardListData =  boardEntityJpaRepository.findAll();
			
			List<UserInfoEntity> userInfoList =  userInfoEntityJpaRepository.findAll();
			
			System.out.println(userInfoList.toString());
			
			
			model.addAttribute("name", "가나다라");
			model.addAttribute("userInfoList", userInfoList);
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%" + userInfoList.size());
			
			return  "UserList";
			
			
			
		}
		
		
		//유저정보페이지
		@RequestMapping("/userInfo")
//		@RequestMapping(value={"/userInfo"}, method=RequestMethod.GET)
//		public String userInfo(HttpServletRequest req, HttpServletResponse resp, Model model, Map map, @RequestParam String userId) {
		public String userInfo(HttpServletRequest req, HttpServletResponse resp, Model model, Map map, @RequestParam Map<String, String> param) {
			
			System.out.println("model  :  " + model.toString());
			System.out.println("map  :  " + map.toString());
			
			List<BoardEntity> boardListData =  boardEntityJpaRepository.findAll();
			
			List<UserInfoEntity> userInfoList =  userInfoEntityJpaRepository.findAll();
			
//			Optional<UserInfoEntity> userInfo = Optional.empty(); 
//			Optional<UserInfoEntity> userInfo = Optional.ofNullable(userInfoEntityJpaRepository.findByUserId(userId));
			UserInfoEntity userInfo = null ;
//			userInfo = userInfoEntityJpaRepository.findByUserId(userId);
			System.out.println("all   " + param.toString());
			System.out.println("aaaa    userId  : " + param.get("userId").toString());
			userInfo = userInfoEntityJpaRepository.findByUserId(param.get("userId").toString());
			
//			System.out.println(userInfoList.toString());
			System.out.println("userInfo");
			System.out.println(userInfo.toString());
			
			
			model.addAttribute("userInfoList", userInfoList);
			model.addAttribute("userInfo", userInfo);
			
			
			return  "SignUp";
			
		}
		

		@RequestMapping("/add")
		public UserInfoEntity add(HttpServletRequest req, HttpServletResponse resp,UserInfoEntity userInfo) {
			

			LocalDateTime date = LocalDateTime.now();

			userInfo.setUserId("JSH");

			userInfo.setUserName("정석헌");

			userInfo.setPwd("1234");

			userInfo.setWriteId(userInfo.getUserId());

			userInfo.setWriteDate(date);

			userInfo.setUseYn("Y");

			UserInfoEntity userInfoData = userInfoEntityJpaRepository.save(userInfo);

			return userInfoData;


		}
		
		
		@RequestMapping("/addUser11")
		public String addUser11(Model model) {
			
			System.out.println("addUser start");
			System.out.println(model.toString());
			
			ModelAndView mv = new ModelAndView();;
			
			
			UserInfoEntity userInfo = new UserInfoEntity();
			LocalDateTime date = LocalDateTime.now();
			
			userInfo.setUserId("JSH");

			userInfo.setUserName("정석헌");

			userInfo.setPwd("1234");

			userInfo.setWriteId(userInfo.getUserId());

			userInfo.setWriteDate(date);

			userInfo.setUseYn("Y");

			
			UserInfoEntity userInfoData = userInfoEntityJpaRepository.save(userInfo);
			
			System.out.println("@@@@@@@@  : " + userInfoData.toString());
			

//			return "Board";
			return "forward:/list";
			
			
		}

		@RequestMapping("/addUser")
		@ResponseBody 
		public Map<String, Object> addUser(HttpServletRequest req, HttpServletResponse resp,
				                                    UserInfoEntity  userInfo) {
			
			System.out.println("addUser start");
			System.out.println(userInfo.toString());
			
//			ModelAndView mv = new ModelAndView();
			
			Map<String, Object> mv = new HashMap<String, Object>(); 
			
			try {
				
				LocalDateTime date = LocalDateTime.now();
				
				userInfo.setUserId(String.valueOf(userInfo.getUserId()));
				
				userInfo.setUserName(String.valueOf(userInfo.getUserName()));
				
				userInfo.setPwd(String.valueOf(userInfo.getPwd()));
				
				userInfo.setWriteId(String.valueOf(userInfo.getUserId()));
				
				userInfo.setWriteDate(date);
				
				userInfo.setUseYn("Y");
				
				UserInfoEntity userInfoData = userInfoEntityJpaRepository.save(userInfo);
				
				System.out.println("@@@@@@@@  : " + userInfoData.toString());
				
	//			mv.addObject("result", "Y");
				mv.put("result", "Y");
				mv.put("no", userInfoData.getNo());
				mv.put("userId", userInfoData.getUserId());
				
				req.getSession().setAttribute("sessionId", String.valueOf(userInfo.getUserId()));
				
			}catch (Exception e) {
				mv.put("result", "N");
				req.getSession().setAttribute("sessionId", "");
			}
			
			return mv;
			
			
		}
		
		
	

}
