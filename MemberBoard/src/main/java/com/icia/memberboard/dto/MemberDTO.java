package com.icia.memberboard.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	private String mid;
	private String mpassword;
	private String mname;
	private String mphone;
	private String mbirth;
	private String memail;
	private String maddress;
	
	private MultipartFile mfile;
	private String mfilename;
	
	private String kakaoId;
	private String naverId;
}
