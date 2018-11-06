package com.board.test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.test.entity.BoardEntity;
import com.board.test.entity.UserInfoEntity;
import com.board.test.entity.jpaRepository.BoardEntityJpaRepository;
import com.board.test.entity.jpaRepository.UserInfoEntityJpaRepository;

import lombok.extern.slf4j.Slf4j;






@Slf4j
@RestController
//@Controller
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
//        modelAndView.setViewName("Main.html");
        modelAndView.setViewName("Main");
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
//		modelAndView.setViewName("SignUp.html");
		modelAndView.setViewName("SignUp");
		return modelAndView;
	}
	
	
	//로그인페이지 이동
	@RequestMapping(value={"/LoginForm"}, method=RequestMethod.GET)
	public ModelAndView getLoginForm(HttpServletRequest req, HttpServletResponse resp) {

		ModelAndView modelAndView = new ModelAndView();

		//세션정보가 있다면
		if(req.getSession().getAttribute("sessionId") != null || !String.valueOf(req.getSession().getAttribute("sessionId")).equals("")) {
			
//			modelAndView.setViewName("LoginForm.html");
			modelAndView.setViewName("LoginForm");
		}else {
			
			List<BoardEntity> boardList =  boardEntityJpaRepository.findAll();
			
			
			modelAndView.addObject("boardList", boardList);
//			modelAndView.setViewName("BoardList.html");
			modelAndView.setViewName("BoardList");
			
			
		}
		
		
		
		return modelAndView;
	}
	
	


//	@PostMapping(value="/SignIn")
	@RequestMapping("/SignIn")
//	@RequestMapping(value={"/SignIn"}, method=RequestMethod.POST)
//	@ResponseBody 
	public Map<String, Object> SignIn(HttpServletRequest req, HttpServletResponse resp, UserInfoEntity  userInfoParam) {
		
		System.out.println("/SignIn  start");
		System.out.println("/SignIn  userInfoParam : " + userInfoParam.toString());
		
		Map<String, Object> mv = new HashMap<String, Object>();
		
		
		UserInfoEntity userInfo = userInfoEntityJpaRepository.findByUserIdAndPwd(String.valueOf(userInfoParam.getUserId()), String.valueOf(userInfoParam.getPwd()));
		
		
		
		
		if(ObjectUtils.isEmpty(userInfo)){
			System.out.println("userInfo null");
			mv.put("result", "N");
		}else {
			System.out.println("userInfo not null");
			
			mv.put("result", "Y");
			mv.put("userId", userInfo.getUserId());
			mv.put("userName", userInfo.getUserName());
			
			req.getSession().setAttribute("sessionId", String.valueOf(userInfo.getUserUuid()));
		}
		
		
		return  mv;
		
		
		
	}
		

		//게시판 정보 조회
		@RequestMapping("/BoardList")
		public ModelAndView BoardList(HttpServletRequest req, HttpServletResponse resp, Model model) {

			
			ModelAndView mv = new ModelAndView();
					
			List<BoardEntity> boardList =  boardEntityJpaRepository.findAll();


			mv.addObject("boardList", boardList);
			mv.setViewName("BoardList");
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%" + boardList.size());
			
			return  mv;

			

		}

		
		//게시판 글쓰기
		@RequestMapping("/BoardWrite")
		public ModelAndView BoardWrite(HttpServletRequest req, HttpServletResponse resp, Model model) {
			
			
			ModelAndView mv = new ModelAndView();
			
			
			List<BoardEntity> boardList =  boardEntityJpaRepository.findAll();
			
			System.out.println("sesseion id  :  " + req.getSession().getAttribute("sessionId"));
			
//			UserInfoEntity userInfo = userInfoEntityJpaRepository.findByUserId(String.valueOf(req.getSession().getAttribute("sessionId")));
			UserInfoEntity userInfo = userInfoEntityJpaRepository.findByUserUuid(String.valueOf(req.getSession().getAttribute("sessionId")));
			
			
			mv.addObject("boardList", boardList);
			mv.addObject("userInfo", userInfo);
			
			mv.addObject("detailView", "N");		//작성페이지
			
			mv.setViewName("BoardWrite");
			
			
			System.out.println("userInfo " + userInfo);
			System.out.println("boardList " + boardList.size());
			
			return  mv;
			
			
			
		}
		
		
		
		//글저장
		@RequestMapping("/saveWrite")
//		@ResponseBody 
		public Map<String, Object> saveWrite(HttpServletRequest req, HttpServletResponse resp,
				                                    BoardEntity boardInfo, UserInfoEntity  userInfo) {
			
			System.out.println("saveWrite start");
			
			Map<String, Object> mv = new HashMap<String, Object>(); 
			
			
			try {
				
				LocalDateTime date = LocalDateTime.now();
				
				boardInfo.setTitle(String.valueOf(boardInfo.getTitle()));
				
				boardInfo.setContents(String.valueOf(boardInfo.getContents()));
				
				userInfo.setUserUuid(String.valueOf(req.getSession().getAttribute("sessionId")));
				boardInfo.setUserInfo(userInfo);
				
				boardInfo.setWriteDate(date);
				boardInfo.setUseYn("Y");
				
				
				BoardEntity boardInfoData = boardEntityJpaRepository.save(boardInfo);
				
				System.out.println("@@@@@@@@  : " + boardInfo.toString());
				
	//			mv.addObject("result", "Y");
				mv.put("result", "Y");
				mv.put("boardUuid", boardInfoData.getBoardUuid());
				
			
			}catch (Exception e) {
				e.printStackTrace();
				mv.put("result", "N");
			}
			
			return mv;
			
			
		}
		
		//게시판 상세보기
//		@RequestMapping("/BoardDetail")
		@RequestMapping(value={"/BoardDetail/{boardUuid}"}, method=RequestMethod.GET)
		public ModelAndView BoardDetail(HttpServletRequest req, HttpServletResponse resp, Model model, @PathVariable String boardUuid) {
			
			System.out.println("AAAAA 111111");
			
			ModelAndView mv = new ModelAndView();
			
			System.out.println(boardUuid);
			
			BoardEntity boardInfo =  boardEntityJpaRepository.findByBoardUuid(boardUuid);
			
			System.out.println("sesseion id  :  " + req.getSession().getAttribute("sessionId"));
			
			UserInfoEntity userInfo = userInfoEntityJpaRepository.findByUserId(String.valueOf(req.getSession().getAttribute("sessionId")));
			
			
			mv.addObject("boardInfo", boardInfo);
			mv.addObject("userInfo", userInfo);
			mv.addObject("detailView", "Y");		//상세페이지
			
			mv.setViewName("BoardWrite");
			
			System.out.println("userInfo " + userInfo);
			
			return mv;
			
		}
		
		
		
		//글수정
		@RequestMapping("/saveUpdate")
//		@ResponseBody 
		public Map<String, Object> saveUpdate(HttpServletRequest req, HttpServletResponse resp,
				BoardEntity boardParam, UserInfoEntity  userInfoParam, Map<String, Object> paramMap) {
			
			System.out.println("saveUpdate start");

			
			
			
			Map<String, Object> mv = new HashMap<String, Object>(); 
			
			BoardEntity boardInfo = new BoardEntity();
			UserInfoEntity userInfo = new UserInfoEntity();
			
			
			try {
				
				LocalDateTime date = LocalDateTime.now();
				
				boardInfo.setBoardUuid(boardParam.getBoardUuid());
				
				boardInfo.setTitle(String.valueOf(boardParam.getTitle()));
				
				boardInfo.setContents(String.valueOf(boardParam.getContents()));
				
				userInfo.setUserUuid(String.valueOf(req.getSession().getAttribute("sessionId")));
				boardInfo.setUserInfo(userInfo);
				
				boardInfo.setUpdateDate(date);
				boardInfo.setUseYn("Y");
				
				
				BoardEntity boardInfoData = boardEntityJpaRepository.save(boardInfo);
				
				System.out.println("@@@@@@@@  : " + boardInfo.toString());
				
				//			mv.addObject("result", "Y");
				mv.put("result", "Y");
				mv.put("boardUuid", boardInfoData.getBoardUuid());
				
				
			}catch (Exception e) {
				e.printStackTrace();
				mv.put("result", "N");
			}
			
			return mv;
			
			
		}
		
		
		

		@RequestMapping(value={"/userList"}, method=RequestMethod.GET)
		public ModelAndView userList(HttpServletRequest req, HttpServletResponse resp, Model model) {
			
			ModelAndView mv = new ModelAndView();
			
			
			List<BoardEntity> boardListData =  boardEntityJpaRepository.findAll();
			
			List<UserInfoEntity> userInfoList =  userInfoEntityJpaRepository.findAll();
			
			System.out.println(userInfoList.toString());
			
			
			mv.addObject("name", "가나다라");
			mv.addObject("userInfoList", userInfoList);
			
			mv.setViewName("UserList");
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%" + userInfoList.size());
			
			return  mv;
			
			
			
		}
		
		
		//유저정보페이지
		@RequestMapping("/userInfo")
//		@RequestMapping(value={"/userInfo"}, method=RequestMethod.GET)
//		public String userInfo(HttpServletRequest req, HttpServletResponse resp, Model model, Map map, @RequestParam String userId) {
		public ModelAndView userInfo(HttpServletRequest req, HttpServletResponse resp, Model model, Map map, @RequestParam Map<String, String> param) {
			
			
			ModelAndView mv = new ModelAndView();
			
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
			
			
			mv.addObject("userInfoList", userInfoList);
			mv.addObject("userInfo", userInfo);
			
			mv.setViewName("SignUp");
			
			return  mv;
			
		}
		
		
		

		@RequestMapping("/addUser")
//		@ResponseBody 
		public Map<String, Object> addUser(HttpServletRequest req, HttpServletResponse resp,
				                                    UserInfoEntity  userInfo) {
			Map<String, Object> mv = new HashMap<String, Object>(); 
			
			System.out.println("addUser start");
			System.out.println(userInfo.toString());
			
			
			UUID uuid = UUID.randomUUID();		//uuid 생성
			
			
			try {
				
				UserInfoEntity inserData = new UserInfoEntity();
				
				LocalDateTime date = LocalDateTime.now();
				
//				inserData.setUuid(String.valueOf(uuid));
				
				inserData.setUserId(String.valueOf(userInfo.getUserId()));
				
				inserData.setUserName(String.valueOf(userInfo.getUserName()));
				
				inserData.setPwd(String.valueOf(userInfo.getPwd()));
				
				inserData.setWriteId(String.valueOf(userInfo.getUserId()));
				
				inserData.setWriteDate(date);
				
				inserData.setUseYn("Y");
				
				System.out.println("inserData  : " + inserData.toString());
				
				UserInfoEntity userInfoData = userInfoEntityJpaRepository.save(inserData);
				
				System.out.println("@@@@@@@@  : " + userInfoData.toString());
				
	//			mv.addObject("result", "Y");
				mv.put("result", "Y");
				mv.put("userUuid", inserData.getUserUuid());
				mv.put("userId", inserData.getUserId());
				
				req.getSession().setAttribute("sessionId", String.valueOf(inserData.getUserUuid()));
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				
				mv.put("result", "N");
				req.getSession().setAttribute("sessionId", "");
			}
			
			return mv;
			
			
		}
		
		
	

}
