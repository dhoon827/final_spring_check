package com.icia.memberboard.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.memberboard.dto.BoardDTO;
import com.icia.memberboard.dto.PageDTO;

@Repository
public class BoardDAO {
	@Autowired
	private SqlSessionTemplate sql;
	
	
		//페이징
		public int listCount() {
			return sql.selectOne("Board.boardListCount");
		}
		public List<BoardDTO> boardListPaging(PageDTO paging) {
			return sql.selectList("Board.boardListPaging", paging);
		}
		
		//글쓰기
		public int boardWrite(BoardDTO board) {
			return sql.insert("Board.boardWrite", board);
		}
		//글 상세조회
		public BoardDTO boardView(int bnumber) {
			return sql.selectOne("Board.boardView", bnumber);
		}

		//조회수
		public int boardHits(int bnumber) {
			return sql.update("Board.boardHits", bnumber);
		}
		//수정 가져오기
		public BoardDTO boardUpdate(int bnumber) {
			return sql.selectOne("Board.boardUpdate", bnumber);
		}

		//글수정
		public int boardUpdateProcess(BoardDTO board) {
			return sql.update("Board.boardUpdateProcess", board);
		}

		//삭제
		public int boardDelete(int bnumber) {
			return sql.delete("Board.boardDelete", bnumber);
		}
		//검색
		public List<BoardDTO> boardSearch(String searchtype, String keyword) {
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("type", searchtype);
			searchMap.put("word", keyword);
			return sql.selectList("Board.boardSearch", searchMap);
		}
		//조회순정렬
		public List<BoardDTO> boardList() {
			return sql.selectList("Board.boardList");
		}


}
