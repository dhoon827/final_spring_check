package com.icia.memberboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.memberboard.dao.CommentDAO;
import com.icia.memberboard.dto.CommentDTO;

@Service
public class CommentService {
	
	@Autowired
	private CommentDAO commentDAO;

	//댓글작성
	public List<CommentDTO> commentWrite(CommentDTO comment) {
		int writeResult = commentDAO.commentWrite(comment);
		List<CommentDTO> commentList = new ArrayList<CommentDTO>();
		if(writeResult > 0)
			commentList = commentDAO.commentList(comment.getCbnumber());
		else
			commentList = null;
		return commentList;
	}

}
