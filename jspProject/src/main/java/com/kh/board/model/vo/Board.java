package com.kh.board.model.vo;

public class Board {
	
	private int boardNo;
	private int boardType;
	private String category; // 작성기능시 카테고리번호 | 조회기능시 카테고리명 
	private String boardTitle;
	private String boardContent;
	private String boardWriter; // 작성할때 회원번호 | 조회시 ?
	private int count; 
	private String create_date; // 작성시 작성일 | 조회시 ?
	private String status; 
	
	public Board() {}

	public Board(int boardNo, int boardType, String category, String boardTitle, String boardContent,
			String boardWriter, int count, String create_date, String status) {
		super();
		this.boardNo = boardNo;
		this.boardType = boardType;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.count = count;
		this.create_date = create_date;
		this.status = status;
	}
	
	

	public Board(int boardNo, String category, String boardTitle, String boardContent, String boardWriter, String create_date) {
		super();
		this.boardNo = boardNo;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.create_date = create_date;
	}

	public Board(int boardNo, String category, String boardTitle, String boardWriter, int count, String create_date) {
		super();
		this.boardNo = boardNo;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardWriter = boardWriter;
		this.count = count;
		this.create_date = create_date;
	}
	
	

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardType=" + boardType + ", category=" + category + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardWriter=" + boardWriter + ", count=" + count
				+ ", create_date=" + create_date + ", status=" + status + "]";
	}
	
	
	
	
	
}
