package com.icia.memberboard.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.icia.memberboard.api.KakaoJoinApi;
import com.icia.memberboard.api.KakaoLoginApi;
import com.icia.memberboard.api.NaverJoinApi;
import com.icia.memberboard.api.NaverLoginApi;
import com.icia.memberboard.dto.MemberDTO;
import com.icia.memberboard.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	private ModelAndView mav;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private KakaoJoinApi kakaoJoinApi;
	
	@Autowired
	private KakaoLoginApi kakaoLoginApi;
	
	@Autowired
	private NaverJoinApi naverJoinApi;
	
	@Autowired
	private NaverLoginApi naverLoginApi;
	
	//홈화면 이동
	@RequestMapping(value="/")
	public String home() {
		return "home";
	}
	//회원가입페이지 이동
	@RequestMapping(value="/memberjoinform")
	public String join() {
		return "join";
	}
	//아이디 중복확인
	@RequestMapping(value="/idchk")
	public @ResponseBody String memberIdchk(@RequestParam("mid") String mid) {
		System.out.println("전달값 : "+mid);
		String resultMsg = memberService.idChk(mid);
		return resultMsg;
	}
	//회원가입
	@RequestMapping(value="/boardjoin")
	public ModelAndView join(@ModelAttribute MemberDTO member) throws IllegalStateException, IOException {
		mav = memberService.memberJoin(member);
		return mav;
	}
	//회원가입페이지 이동
	@RequestMapping(value="/memberloginform")
	public String login() {
		return "login";
	}
	//로그인
	@RequestMapping(value="/memberlogin")
	public ModelAndView login(@ModelAttribute MemberDTO member) {
		mav = memberService.memberLogin(member);
		return mav;
	}
	//카카오 로그인 1
	@RequestMapping(value="/kakaojoin")
	public ModelAndView kakaoJoin(HttpSession session) {
		String kakaoUrl = kakaoJoinApi.getAuthorizationUrl(session);
		mav = new ModelAndView();
		mav.addObject("kakaoUrl", kakaoUrl);
		mav.setViewName("KakaoLogin");
		return mav;
	}
	//카카오 서버 인증 통과 후 처리2
	@RequestMapping(value="/kakaojoinok")
	public ModelAndView kakaoJoinOk(@RequestParam("code") String code, HttpSession session) {
		JsonNode token = kakaoJoinApi.getAccessToken(code);
		JsonNode profile = kakaoJoinApi.getKakaoUserInfo(token.path("access_token"));
		String kakaoId = profile.get("id").asText();
		mav = new ModelAndView();
		mav.addObject("kakaoId", kakaoId);
		mav.setViewName("join");
		return mav;
	}
	//카카오 로그인 3
	@RequestMapping(value="kakaologin")
	public ModelAndView kakaoLogin(HttpSession session) {
		String kakaoUrl = kakaoLoginApi.getAuthorizationUrl(session);
		mav = new ModelAndView();
		mav.addObject("kakaoUrl",kakaoUrl);	
		mav.setViewName("KakaoLogin");
		return mav;
	}
	//카카오 로그인 4
	@RequestMapping(value="/kakaologinok")
	public ModelAndView kakaoLoginOk(@RequestParam("code")String code) {
		JsonNode token = kakaoLoginApi.getAccessToken(code);
		JsonNode profile = kakaoLoginApi.getKakaoUserInfo(token.path("access_token"));
		
		mav = memberService.kakaoLogin(profile);
		return mav;
	}
	//네이버 회원가입 1
	@RequestMapping(value="/naverjoin")
	public ModelAndView naverJoin(HttpSession session) {
		String naverUrl = naverJoinApi.getAuthorizationUrl(session);
		mav = new ModelAndView();
		mav.addObject("naverUrl",naverUrl);	
		mav.setViewName("NaverLogin");
		return mav;
	}
	//네이버 회원가입 2
	@RequestMapping(value="/naverjoinok")
	public ModelAndView naverJoinOk(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) throws IOException, ParseException {
		mav = new ModelAndView();
		OAuth2AccessToken oauthToken = naverJoinApi.getAccessToken(session, code, state);
		String profile = naverJoinApi.getUserProfile(oauthToken);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(profile);
		JSONObject naverUser = (JSONObject)obj;
		JSONObject userInfo = (JSONObject)naverUser.get("response");
		String naverId = (String) userInfo.get("id");
		mav.addObject("naverId",naverId);	
		mav.setViewName("join");
		return mav;
	}
	//네이버 로그인 1
	@RequestMapping(value="/naverlogin")
	public ModelAndView naverLogin(HttpSession session) {			String naverUrl = naverLoginApi.getAuthorizationUrl(session);
		mav = new ModelAndView();
		mav.addObject("naverUrl",naverUrl);	
		mav.setViewName("NaverLogin");			
		return mav;
	}
	//네이버 로그인 2
	@RequestMapping(value="/naverloginok")
	public ModelAndView naverLoginOk(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) throws IOException, ParseException {
		mav = new ModelAndView();
		OAuth2AccessToken oauthToken = naverLoginApi.getAccessToken(session, code, state);
		String profile = naverLoginApi.getUserProfile(oauthToken);
		mav = memberService.naverLogin(profile);
		return mav;
		}	
	//정보수정1
	@RequestMapping(value="/mypage")
	public ModelAndView memberUpdate() {
		mav = memberService.memberMypage();
		return mav;
	}
	//정보수정2
	@RequestMapping(value="/memberupdate")
	public ModelAndView memberUpdate(@ModelAttribute MemberDTO member) throws IllegalStateException, IOException {
		mav = memberService.memberUpdate(member);
		return mav;
	}
	
	//로그아웃
	@RequestMapping(value="/memberlogout")
	public String memberLogout() {
		session.invalidate();
		return "login";
	}
	//목록 조회
	@RequestMapping(value="/memberlist")
	public ModelAndView memberList() {
		mav = memberService.memberList();
		return mav;
	}
	//회원 조회
	@RequestMapping(value="/memberview")
	public ModelAndView memberView(@RequestParam("mid") String mid) {
		mav = memberService.memberView(mid);
		return mav;
	}
	
	//회원 삭제
	@RequestMapping(value="/memberdelete")
	public ModelAndView memberDelete(@RequestParam("mid") String mid) {
		mav = memberService.memberDelete(mid);
		return mav;
	}
	
}
