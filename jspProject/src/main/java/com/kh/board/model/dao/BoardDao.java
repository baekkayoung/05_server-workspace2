package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardDao {
	
	private Properties prop = new Properties();
	
	public BoardDao() { // 그냥 load로 하면 안됨 , 입력 스트림.
		// C:\05_server-workspace2\jspProject\WebContent\WEB-INF\classes.. 클래스찾아가서 
		String filePath = BoardDao.class.getResource("/db/sql/board-mapper.xml").getPath();
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
		e.printStackTrace();
		}
	}


	public int selectListCount(Connection conn) {
		// select문 => ResultSet(한개) => int
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt("count"); // 별칭
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close(rset);
			close(pstmt);
		}
		return listCount;
	}


	public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
		// select 문 => ResultSet(여러행) => ArrayList<Board>
		
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			/*
			 * currentPage : 1 => 시작값 : 1  | 끝값 : 10
			 * currentPage : 2 => 시작값 : 11 | 끝값 20
			 * currentPAge : 3 => 시작값 : 21 | 끝값 : 30
			 * 
			 * 시작값 : (currentPage - 1) * boardLimit + 1
			 * 끝값 : 시작값 + boardLimit - 1
			 * 
			 * */
			
			int startRow = (pi.getCurrentPage() - 1 ) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1 ;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Board(rset.getInt("board_no"),
									rset.getString("category_name"),
									rset.getString("board_title"),
									rset.getString("user_id"),
									rset.getInt("count"),
									rset.getString("create_date"))); // 기본생성자?
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		} return list;
		
	}


	public ArrayList<Category> selectCategoryList(Connection conn) {
		ArrayList<Category> list = new ArrayList<Category>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCategoryList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(new Category(rset.getInt("category_no"),
						rset.getString("category_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		} return list;
		
		
	}


	public int insertBoard(Connection conn, Board b) {
		int result = 0 ;
		PreparedStatement pstmt = null;
		String sql =prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
	}


	private String getCategory() {
		return null;
	}


	public int insertAttachment(Connection conn, Attachment at) {
		int result = 0 ;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close(pstmt);
		}
		return result;
	}
	
	public int increaseCount(Connection conn, int BoardNo) {
		// update문 => 처리된행수 => 트랜젝션 처리 
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, BoardNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		} return result;
		
	};
	
	public Board selectBoard(Connection conn, int BoardNo) {
		// select문 => ResultSet (한행) = > Board
		Board b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, BoardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = new Board(rset.getInt("board_no"),
						rset.getString("category_name"),
						rset.getString("board_title"),
						rset.getString("board_content"),
						rset.getString("user_id"),
						rset.getString("create_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			
		} return b;
		
	}
	
	
	
	
	
	
	
	public Attachment selectAttachment(Connection conn, int BoardNo) {
		
		Attachment at = null ;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, BoardNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				at = new Attachment(rset.getInt("file_no")
						,rset.getString("origin_name")
						,rset.getString("change_name")
						, rset.getString("file_path"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		
		} return at;
		
	}


	public int updateBoard(Connection conn, Board b) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, b.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}return result;
		
	}


	public int updateAttachment(Connection conn, Attachment at) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			pstmt.setInt(4, at.getFileNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			
		} return result;
	}


	public int insertNewAttachment(Connection conn, Attachment at) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNewAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, at.getRefBoardNo());
			pstmt.setString(2, at.getOriginName());
			pstmt.setString(3, at.getChangeName());
			pstmt.setString(4, at.getFilePath());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		} return result;
	}
	



	public int insertThBoard(Connection conn, Board b) {
		int result = 0 ;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertThBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
		
			
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardContent());
			pstmt.setInt(3, Integer.parseInt(b.getBoardWriter()));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		} return result;
		
	}



	public int insertAttachmentList(Connection conn, ArrayList<Attachment> list) {
		int result = 0 ;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachmentList");
		
		try {
			
			for(Attachment at : list ) {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, at.getOriginName());
				pstmt.setString(2, at.getChangeName());
				pstmt.setString(3, at.getFilePath());
				pstmt.setInt(4, at.getFileLevel());
				
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		} return result;
	}


	public ArrayList<Board> selectThumbnailList(Connection conn) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectThumbnailList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
		
			// 반복문 돌려가면서
			while(rset.next()) {
				Board b = new Board();
				b.setBoardNo(rset.getInt("board_no"));
				b.setBoardTitle(rset.getString("board_title"));
				b.setCount(rset.getInt("count"));
				b.setTitleImg(rset.getString("titleImage"));
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			
		} return list ;
		
		
	}


	public Board selectThumbnaildetailView(Connection conn) {
		
		Board b = new Board();
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectThumbnaildetailView");
		
		try {
			pstmt = conn.prepareStatement(sql);
		
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = new Board(rset.getString("board_title"),
						rset.getString("board_writer"),
						rset.getString("create_date"),
						rset.getString("board_content"),
						rset.getString("titleImage"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		} return b;
		
	}


	

}
