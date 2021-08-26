package com.test.market.admin;

import java.util.Calendar;

/**
 * 광고를 생성하기위한 클래스
 * @author 6조
 * 
 */
public class Adver {

	private String content;
	private String seq;
	private Calendar writeDay;
	
	/***
	 * 광고 번호 Getter
	 * @return 광고번호
	 */
	public String getSeq() {
		return seq; 
	}

	/***
	 * 광고번호 Setter
	 * @param seq  광고번호
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/***
	 * 작성일 Getter
	 * @return 작성일
	 */
	public Calendar getWriteDay() {
		return writeDay;
	}

	
	/**
	 * 작성일 Setter
	 * @param writeDay  작성일
	 */
	public void setWriteDay(Calendar writeDay) {
		this.writeDay = writeDay;
	}

	/***
	 * 광고내용 Getter
	 * @return 광고내용
	 */
	public String getContent() {
		return content;
	}

	/***
	 * 광고내용 Setter
	 * @param content  광고내용
	 */
	public void setContent(String content) {
		this.content = content;
	}

}//Adver
