package com.test.market.board;

import java.util.Calendar;

//번호/제목/닉네임/가격/지역/사용일/업로드날짜/거래상태/내용
/***
 * 메인 게시글 작성을 위한 클래스
 * @author 6조
 *
 */
public class Board{
	
	private String seq;
	private String title;
	private String nickname;
	private String item;
	private int price;
	private String area;
	private int use_date;
	private Calendar calendar;
	private String trade_status;
	private String content;
	private String category;
	private int report;
	private int hit;
	
	
	/***
	 * 게시글 신고 수 Getter
	 * @return 신고 수
	 */
	public int getReport() {
		return report;
	}
	
	/***
	 * 게시글 신고 수 Setter
	 * @param report 신고 수
	 */
	public void setReport(int report) {
		this.report = report;
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
	
	/***
	 * 게시글 물품명 Getter
	 * @return 물품명
	 */
	public String getItem() {
		return item;
	}
	
	/***
	 * 게시글 물품명 Setter
	 * @param item 물품명
	 */
	public void setItem(String item) {
		this.item = item;
	}
	
	/***
	 * 게시글 카테고리 Getter
	 * @return 카테고리
	 */
	public String getCategory() {
		return category;
	}
	
	/***
	 * 게시글 카테고리 Setter
	 * @param category 카테고리
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/***
	 * 게시글 번호 Setter
	 * @param seq 게시글 번호
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	/***
	 * 게시글 번호 Getter
	 * @return 번호
	 */
	public String getSeq() {
		return seq;
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
	 * @param title 게시글 제목
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/***
	 * 게시글 작성자 닉네임 Getter
	 * @return 닉네임
	 */
	public String getNickname() {
		return nickname;
	}
	
	/***
	 * 게시글 작성자 닉네임 Setter
	 * @param nickname 작성자 닉네임
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/***
	 * 게시글 물품 가격 Getter
	 * @return 가격
	 */
	public int getPrice() {
		return price;
	}
	
	/***
	 * 게시글 물품 가격 Setter
	 * @param price 가격
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/***
	 * 게시글 거래지역 Getter
	 * @return 거래지역
	 */
	public String getArea() {
		return area;
	}
	
	/***
	 * 게시글 거래지역 Setter
	 * @param area 거래지역
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	/***
	 * 게시글 사용(개월) Getter
	 * @return 사용(개월)
	 */
	public int getUse_date() {
		return use_date;
	}
	
	/***
	 * 게시글 사용(개월) Setter
	 * @param use_date 사용(개월)
	 */
	public void setUse_date(int use_date) {
		this.use_date = use_date;
	}
	
	/***
	 * 게시글 작성일 Getter
	 * @return 작성일
	 */
	public Calendar getCalendar() {
		return calendar;
	}
	
	/***
	 * 게시글 작성일 Setter
	 * @param calendar 작성일
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	/***
	 * 게시글 거래상태 Getter
	 * @return 거래상태
	 */
	public String getTrade_status() {
		return trade_status;
	}
	
	/***
	 * 게시글 거래상태 Setter
	 * @param trade_status 거래상태
	 */
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
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
	
	
	
}