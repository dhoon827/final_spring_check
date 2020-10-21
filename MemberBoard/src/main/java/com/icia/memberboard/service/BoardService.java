package com.icia.memberboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.memberboard.dao.BoardDAO;
import com.icia.memberboard.dao.CommentDAO;
import com.icia.memberboard.dto.BoardDTO;
import com.icia.memberboard.dto.CommentDTO;
import com.icia.memberboard.dto.PageDTO;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private CommentDAO commentDAO;
	
	private ModelAndView mav; 
	
	//한페이지에 3개씩 보이고 밑에목록은 5개 보임
	private static final int PAGE_LIMIT = 3;
	private static final int BLOCK_LIMIT = 5;

	//페이징
		public ModelAndView boardListPaging(int page) {
			mav = new ModelAndView();
			int listCount = boardDAO.listCount();
			int startRow = (page-1)*PAGE_LIMIT + 1;
			int endRow = page*PAGE_LIMIT;
		
			PageDTO paging =  new PageDTO();
			paging.setStartRow(startRow);
			paging.setEndRow(endRow);
			List<BoardDTO> boardList = boardDAO.boardListPaging(paging);
			
			int maxPage = (int)(Math.ceil((double)listCount/PAGE_LIMIT));
			int startPage = (((int)(Math.ceil((double)page/BLOCK_LIMIT)))-1) * BLOCK_LIMIT+1;

			int endPage = startPage + BLOCK_LIMIT -1;
			if(endPage>maxPage) {
				endPage = maxPage;
			}
			
			paging.setPage(page);
			paging.setStartPage(startPage);
			paging.setEndPage(endPage);
			paging.setMaxPage(maxPage);
			
			mav.addObject("paging", paging);
			mav.addObject("boardList", boardList);
			mav.setViewName("main");
			return mav;
		}

		//글쓰기
		public ModelAndView boardWrite(BoardDTO board) throws IllegalStateException, IOException {
			mav = new ModelAndView();
			MultipartFile bfile = board.getBfile();
			String bfilename = bfile.getOriginalFilename();
			String savePath = "D:\\source\\Spring\\MemberBoard\\src\\main\\webapp\\resources\\uploadFile"+bfilename;
			if(!bfile.isEmpty()) {
				bfile.transferTo(new File(savePath));
			}
			board.setBfilename(bfilename);
			int writeResult = boardDAO.boardWrite(board);
			if(writeResult > 0)
				mav.setViewName("redirect:/boardlistpaging");
			else
				mav.setViewName("boardv/BoardWriteFileFail");
			return mav;
		}
		//글 상세조회
		public ModelAndView boardView(int bnumber, int page) {
			mav = new ModelAndView();
			int boardHits = boardDAO.boardHits(bnumber);
			BoardDTO boardView =  boardDAO.boardView(bnumber);
			List<CommentDTO> commentList = commentDAO.commentView(bnumber);
			mav.addObject("commentList", commentList); mav.addObject("page", page);
			
			mav.addObject("boardView", boardView);
			mav.setViewName("view");
			return mav;
		}

		//글 정보 가져오기 (수정)
		public ModelAndView boardUpdate(int bnumber) {
			mav = new ModelAndView();
			BoardDTO boardUpdate =  boardDAO.boardUpdate(bnumber);
			mav.addObject("boardUpdate", boardUpdate);
			mav.setViewName("update");
			return mav;
		}

		//글 수정
		public ModelAndView boardUpdateProcess(BoardDTO board) {
			mav = new ModelAndView();
			int updateResult = boardDAO.boardUpdateProcess(board);
			if(updateResult > 0)
				mav.setViewName("redirect:/boardview?bnumber="+board.getBnumber());
			else
				mav.setViewName("boardv/BoardUpdateFail");
			return mav;
		}

		//삭제
		public ModelAndView boardDelete(int bnumber) {
			mav =  new ModelAndView();
			int deleteResult = boardDAO.boardDelete(bnumber);
			if(deleteResult>0)
				mav.setViewName("redirect:/boardlistpaging");
			else
				mav.setViewName("boardv/fail");
			return mav;
		}

		//검색
		 public ModelAndView boardSearch(String searchtype, String keyword) { mav =
		 new ModelAndView(); List<BoardDTO> searchList =
		 boardDAO.boardSearch(searchtype, keyword); mav.addObject("boardList",
		 searchList); mav.setViewName("main"); return mav; }

		//조회 순 정렬
		public ModelAndView hitsList() {
			mav = new ModelAndView();
			List<BoardDTO> boardList = boardDAO.boardList();
			mav.addObject("boardList", boardList);
			mav.setViewName("main");
			return mav;
		}
		 

}
