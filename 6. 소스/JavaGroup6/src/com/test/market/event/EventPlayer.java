package com.test.market.event;

import java.util.Calendar;
/***
 * 이벤트 기록을 저장하는 클래스
 * @author 6조
 * 
 * 
 */
public class EventPlayer {
	
	private String id;
	private String seq;
	private Calendar playDay;
	
	/***
	 * 이벤트 참여자 아이디 Getter
	 * @return 아이디
	 */
	public String getId() {
		return id;
	}
	/***
	 * 이벤트 참여자 아이디 Setter
	 * @param id 아이디
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/***
	 * 참여한 이벤트의 게시글 번호 Getter
	 * @return 게시글번호
	 */
	public String getSeq() {
		return seq;
	}
	
/***
 * 참여한 이벤트의 게시글 번호 Setter
 * @param seq 게시글번호
 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	/***
	 * 이벤트 참여한 날짜 Getter
	 * @return 날짜
	 */
	public Calendar getPlayDay() {
		return playDay;
	}
	
	/***
	 * 이벤트 참여한 날짜 Setter
	 * @param playDay 날짜
	 */
	public void setPlayDay(Calendar playDay) {
		this.playDay = playDay;
	}
	
}
