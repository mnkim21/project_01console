package com.test.market.event;

import java.util.Calendar;

/***
 * 이벤트를 작성하기 위한 클래스
 * @author 6조
 * 
 */
public class Event {

	private String seq;//번호
	private Calendar regdate; //등록일자
	private String title; //이벤트 제목	
	private String content; //이벤트 내용
	private int hit; //조회수
	
	
	/***
	 * 이벤트 게시글 번호 Getter
	 * @return 게시글번호
	 */
	public String getSeq() {
		return seq;
	}
		
	/***
	 * 이벤트 게시글 번호 setter
	 * @param seq 게시글번호
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
/***
 * 게시글 작성일 Getter
 * @return 작성일
 */
	public Calendar getRegdate() {
		return regdate;
	}

/***
 * 게시글 작성일 Setter
 * @param regdate 작성일
 */
	public void setRegdate(Calendar regdate) {
		this.regdate = regdate;
	}

/***
 * 게시글 제목 Getter
 * @return 제목
 */
	public String getTitle() {
		return title;
	}
	
	/***
	 * 게시글 제목 Setter
	 * @param title 제목
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/***
	 * 게시글 내용 Getter
	 * @return 내용
	 */
	public String getContent() {
		return content;
	}
	
	/***
	 * 게시글 내용 Setter
	 * @param content 내용
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/***
	 * 게시글 조회수 Getter
	 * @return 조회수
	 */
	public int getHit() {
		return hit;
	}
	
	/***
	 * 게시글 조회수 Setter
	 * @param hit 조회수
	 */
	public void setHit(int hit) {
		this.hit = hit;
	}
	
}//Event
