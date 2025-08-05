package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.NoticeDao;
import com.kh.board.model.vo.Notice;
import com.kh.common.JDBCTemplate;

import static com.kh.common.JDBCTemplate.*;

public class NoticeService {

	public ArrayList<Notice> selectNoticeList() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Notice> list =new NoticeDao().selectNoticeList(conn);
		
		close(conn);
		return list;
	}

	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int result = new NoticeDao().insertNotice(conn, n);
		
		if(result>0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		return result;
	} 
	
	
	
	

}
