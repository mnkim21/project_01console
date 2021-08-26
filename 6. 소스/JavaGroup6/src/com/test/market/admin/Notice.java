package com.test.market.admin;

import java.util.Calendar;

/***
 * 공지사항을 작성하기 위한 클래스
 * @author 6조
 * 
 */
public class Notice {

	
	private String seq;
	private String title;
	private Calendar writeDay;
	private String content;
	private int hit;
	
	
	/***
	 * 공지사항 게시글의 번호 Getter
	 * @return 번호
	 */
	public String getSeq() {
		return seq;
	}
	
	/***
	 * 공지사항 게시글의 번호 Setter
	 * @param seq 게시글 번호
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	/***
	 * 공지사항 게시글의 제목 Getter
	 * @return 제목
	 */
	public String getTitle() {
		return title;
	}
	
	/***
	 * 공지사항 게시글의 제목 Setter
	 * @param title 제목
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/***
	 * 공지사항의 작성일 Getter
	 * @return 작성일
	 */
	public Calendar getWriteDay() {
		return writeDay;
	}
	
	/***
	 * 공지사항의 작성일 Setter
	 * @param writeDay 작성일
	 */
	public void setWriteDay(Calendar writeDay) {
		this.writeDay = writeDay;
	}
	
	/***
	 * 공지사항의 내용 Getter
	 * @return 내용
	 */
	public String getContent() {
		return content;
	}
	
	/***
	 * 공지사항의 내용 Setter
	 * @param content 내용
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/***
	 * 공지사항의 조회수 Getter
	 * @return 조회수
	 */
	public int getHit() {
		return hit;
	}
	
	/***
	 * 공지사항의 조회수 Setter
	 * @param hit 조회수
	 */
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	
	
	
}
