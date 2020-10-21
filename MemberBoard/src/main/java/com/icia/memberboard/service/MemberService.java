package com.icia.memberboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.memberboard.dao.MemberDAO;
import com.icia.memberboard.dto.MemberDTO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private HttpSession session;
	
	private ModelAndView mav; 
	
	//중복확인
	public String idChk(String mid) {
		String checkResult = memberDAO.idChk(mid);
		String resultMsg = null;
		if(checkResult == null)
			resultMsg = "OK";
		else
			resultMsg = "NO";
		return resultMsg;
	}

	//회원가입
	public ModelAndView memberJoin(MemberDTO member) throws IllegalStateException, IOException {
		mav = new ModelAndView();
		MultipartFile mfile = member.getMfile();
		String mfilename = mfile.getOriginalFilename();
		String savePath = "D:\\source\\Spring\\MemberBoard\\src\\main\\webapp\\resources\\uploadFile"+mfilename;
		if(!mfile.isEmpty()) {
			mfile.transferTo(new File(savePath));
		}
		member.setMfilename(mfilename);
		int joinResult = memberDAO.memberJoin(member);
		if(joinResult > 0)
			mav.setViewName("login");
		else
			mav.setViewName("BoardWriteFileFail");
		return mav;
	}
	//로그인
	public ModelAndView memberLogin(MemberDTO member) {
		mav = new ModelAndView();
		
		String loginId = memberDAO.memberLogin(member);
		if(loginId != null) {
			session.setAttribute("loginId", loginId);
			mav.setViewName("redirect:/boardlistpaging");
		}else {
			mav.setViewName("LoginFail");
		}
		return mav;
	}
	//정보수정1
	public ModelAndView memberMypage() {
		mav = new ModelAndView();
		String mid = (String)session.getAttribute("loginId");
		MemberDTO memberMypage = memberDAO.memberMypage(mid);
		mav.addObject("memberMypage", memberMypage);
		mav.setViewName("mypage");
		return mav;
	}

	//정보수정2
	public ModelAndView memberUpdate(MemberDTO member) throws IllegalStateException, IOException {
		mav = new ModelAndView();
		MultipartFile mfile = member.getMfile();
		String mfilename = mfile.getOriginalFilename();
		String savePath = "D:\\source\\Spring\\MemberBoard\\src\\main\\webapp\\resources\\uploadFile"+mfilename;
		if(!mfile.isEmpty()) {
			mfile.transferTo(new File(savePath));
		}
		member.setMfilename(mfilename);
		int updateResult = memberDAO.memberUpdate(member);
		mav = new ModelAndView();
		if(updateResult>0) {
			mav.setViewName("redirect:/boardlistpaging");
		}else {
			mav.setViewName("MemberUpdateFail");
		}
		return mav;
	}

	//목록 조회
	public ModelAndView memberList() {
		mav = new ModelAndView();
		List<MemberDTO> memberList = memberDAO.memberList();
		mav.addObject("memberList", memberList);
		mav.setViewName("memberlist");
		return mav;
	}
	
	//상세조회
		public ModelAndView memberView(String mid) {
			mav = new ModelAndView();	
			MemberDTO memberView = memberDAO.memberView(mid);
			mav.addObject("memberView", memberView);
			mav.setViewName("memberview");
			return mav;
		}

	//회원삭제
	public ModelAndView memberDelete(String mid) {
		mav = new ModelAndView();
		int deleteResult = memberDAO.memberDelete(mid);
		if(deleteResult>0) {
			mav.setViewName("redirect:/memberlist");
		}else {
			mav.setViewName("MemberDeleteFail");
		}
			return mav;
		}

	//카카오 로그인
	public ModelAndView kakaoLogin(JsonNode profile) {
		mav = new ModelAndView();
		String kakaoId = profile.get("id").asText();
		String loginId = memberDAO.kakaoLogin(kakaoId);
		session.setAttribute("loginId", loginId);
		mav.setViewName("redirect:/boardlistpaging");
		return mav;
	}

	//네이버 로그인
	public ModelAndView naverLogin(String profile) throws ParseException {
		mav = new ModelAndView();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(profile);
		JSONObject naverUser = (JSONObject)obj;
		JSONObject userInfo = (JSONObject)naverUser.get("response");
		String naverId = (String)userInfo.get("id");
		String loginId = memberDAO.naverLogin(naverId);
		session.setAttribute("loginId", loginId);
		mav.setViewName("redirect:/boardlistpaging");
		return mav;
	}


}
