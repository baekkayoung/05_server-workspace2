package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardService {

	public int selectListCount() {
		
		Connection conn = getConnection();
		
		int listCount = new BoardDao().selectListCount(conn);
		
		close(conn);
		return listCount;
	}

	public ArrayList<Board> selectList(PageInfo pi) {
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		
		close(conn);
		return list;
	
	}

	public ArrayList<Category> selectCategoryList() {
		Connection conn = getConnection();
		ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
		
		close(conn);
		return list;
	}

	public int insertBoard(Board b, Attachment at) {
		
		Connection conn = getConnection();	
		int result1 = new BoardDao().insertBoard(conn, b);
		int result2 = 1; // 애를 0으로하면? 안됨
		if(at!=null) { // 없을때는 이거 자체를 안 함
			result2 = new BoardDao().insertAttachment(conn,at);
		}
		
		if (result1 > 0 && result2>0) {
			commit(conn);
		} else {
			rollback(conn);
		} return result1 * result2;
	}

}
