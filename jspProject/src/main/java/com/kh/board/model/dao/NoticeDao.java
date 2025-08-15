package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Notice;
import com.kh.common.JDBCTemplate;

import static com.kh.common.JDBCTemplate.*;

public class NoticeDao {
	
	private Properties prop = new Properties();// 비어져 있는.. 쿼리를 읽어주는 기본 생성자
	
	public NoticeDao() { // 읽어들이고자하는 xml 파일의 절대경로
		String filePath = NoticeDao.class.getResource("/db/sql/notice-mapper.xml").getPath(); //이 파일이 있는 절대경로 저장
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Notice> selectNoticeList(Connection conn) {
		// select (여러행) => ResultSet => ArrayList<Notice>
		
		
		ArrayList<Notice> list = new ArrayList<Notice>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectNoticeList");
		
		try {
			pstmt =conn.prepareStatement(sql); // 완성된 sql
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
//				list.add(new Notice()); 기본 초기값
				list.add(new Notice(rset.getInt("notice_no"),
									rset.getString("notice_title"),
									rset.getString("user_id"),
									rset.getInt("count"),
									rset.getDate("create_date")));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}  return list;
		
		
		
	}

	public int insertNotice(Connection conn, Notice n) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0 ;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter()));
		
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			close(pstmt);
			
		} return result;
	}

	public int increaseCount(Connection conn, int noticeNo) {
		// update문 => 처리된 행수 => 트랜젝션처리
		int result = 0;
		PreparedStatement pstmt = null;
		String sql =prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql
			pstmt.setInt(1, noticeNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		} return result;
		
		
	}

	public Notice selectNotice(Connection conn, int noticeNo) {
		// select 문 => resultSet 한행 => Notice 객체
		Notice n = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectNotice");
		
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				n = new Notice(rset.getInt("notice_no"),
						rset.getString("notice_title"),
						rset.getString("notice_content"),
						rset.getString("user_id"),
						rset.getDate("create_date"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		} return n;
		
		
		
	}

	public int updateNotice(Connection conn, Notice n) {
		// update => 처리된행수 => 트랜젝션
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, n.getNoticeNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}return result;
		
	}

	public int deleteNoticeForm(Connection conn, int noticeNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteNoticeForm");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}return result;
		
	
	}

}
