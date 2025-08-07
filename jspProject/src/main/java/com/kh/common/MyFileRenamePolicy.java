package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy{
	// The type MyFileRenamePolicy must implement the inherited abstract method FileRenamePolicy.rename(File)
	@Override
	public File rename(File originFile) {
		
		// 원본파일명 ("aaa.jpg")
		String originName = originFile.getName();
		
		// => 수정 파일명 ("20250807141596321.jpg")
		// 				파일업로드시간(년월일시분초) + 5자리 랜덤값(10000~99999) + 원본파일확장자
		
		// 1. 파일업로드시간(년월일시분초 형태) (String currentTime)
		
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "2025080702255"
		
		// 2. 5자리 랜덤값 (int ranNum)
		int ranNum = (int)(Math.random()* 90000 + 10000); // 23123
		
		// 3. 원본파일 확장자 (String ext)	  마지막.의 인덱스 aa.bb.jpg
		String ext = originName.substring(originName.lastIndexOf(".")); // ".jpg"
		
		String changeName = currentTime + ranNum + ext;
		
	
		return new File(originFile.getParent(), changeName);
		// 원본파일을 changeName으로 바꿔서 저장하겠다.
	}

}
