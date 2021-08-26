package com.test.market.board;

import java.util.Calendar;

/***
 * 게시글 신고 기록을 저장하기 위한 클래스
 * @author 6조
 * 
 *
 */
public class ReportRecord {
	
	private String seq;
	private String id;
	
	/***
	 * 신고한 게시글의 번호 Getter
	 * @return 번호
	 */
	public String getSeq() {
		return seq;
	}
	
	/***
	 * 신고한 게시글의 번호 Setter
	 * @param seq 게시글번호
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	/***
	 * 신고한사람의 아이디 Getter
	 * @return 아이디
	 */
	public String getId() {
		return id;
	}
	
	/***
	 * 신고한사람의 아이디 Getter
	 * @param id 아이디
	 */
	public void setId(String id) {
		this.id = id;
	}


}
