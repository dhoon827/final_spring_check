package com.icia.memberboard.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.memberboard.dto.MemberDTO;

@Repository
public class MemberDAO {
	@Autowired
	private SqlSessionTemplate sql;

	//중복확인
	public String idChk(String mid) {
		return sql.selectOne("Member.idChk", mid);
	}

	//회원가입
	public int memberJoin(MemberDTO member) {
		if(member.getKakaoId() != null)
			return sql.insert("Member.kakaoMemberJoin", member);
		else if(member.getNaverId() != null)
			return sql.insert("Member.naverMemberJoin", member);
		else
			return sql.insert("Member.memberJoin", member);
		}

	//로그인
	public String memberLogin(MemberDTO member) {
		return sql.selectOne("Member.memberLogin", member);
	}

	//정보수정1
	public MemberDTO memberMypage(String mid) {
		return sql.selectOne("Member.memberMypage", mid);
	}
	
	//정보수정2
	public int memberUpdate(MemberDTO member) {
		return sql.update("Member.memberUpdate", member);
	}

	//목록 조회
	public List<MemberDTO> memberList() {
		return sql.selectList("Member.memberList");
	}
	
	//상세 조회
	public MemberDTO memberView(String mid) {
		return sql.selectOne("Member.memberView", mid);
		
	}
	//회원 삭제
	public int memberDelete(String mid) {
		return sql.delete("Member.memberDelete", mid);
	}

	//카카오 로그인
	public String kakaoLogin(String kakaoId) {
		return sql.selectOne("Member.kakaoLogin", kakaoId);
	}

	//네이버 로그인
	public String naverLogin(String naverId) {
		return sql.selectOne("Member.naverLogin", naverId);
	}


}
