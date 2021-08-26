package com.test.market.clientcenter;

import java.util.Calendar;

/***
 * 고객센터 게시글 정보를 담기위한 클래스
 * @author 6조
 * 
 */
public class ClientCenter {
	
	private String seq;
	private String title;
	private String myId;
	private String content;
	private String myInsurance;
	private String type;
	private Calendar writeDay;
	private String nickname;
	private int hit;
	
	/***
	 * 조회수 Getter
	 * @return 조회수
	 */
	public int getHit() {
		return hit;
	}
	
	/***
	 * 조회수 Setter
	 * @param hit 조회수
	 */
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	
	/***
	 * 닉네임 Getter
	 * @return 닉네임
	 */
	public String getNickname() {
		return nickname;
	}
	
	/***
	 * 닉네임 Setter
	 * @param nickname 닉네임
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/***
	 * 게시글번호 Getter
	 * @return 게시글번호
	 */
	public String getSeq() {
		return seq;
	}
	
	/***
	 * 게시글번호 Setter
	 * @param seq 게시글번호
	 */
	public void setSeq(String seq) {
		this.seq = seq;
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
	 * 게시자 아이디 Getter
	 * @return 아이디
	 */
	public String getMyId() {
		return myId;
	}
	
	/***
	 * 게시자 아이디 Setter
	 * @param myId 아이디
	 */
	public void setMyId(String myId) {
		this.myId = myId;
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
	 * 게시자 보험가입 유무 Getter
	 * @return 보험가입
	 */
	public String getMyInsurance() {
		return myInsurance;
	}
	
	/***
	 * 게시자 보험가입 유무 Setter
	 * @param myInsurance 보험가입
	 */
	public void setMyInsurance(String myInsurance) {
		this.myInsurance = myInsurance;
	}
	
	/***
	 * 게시글 유형(비방,욕설,사기,기타) Getter
	 * @return 유형
	 */
	public String getType() {
		return type;
	}
	
	/***
	 * 게시글 유형(비방,욕설,사기,기타) Setter
	 * @param type 유형
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/***
	 * 게시글 작성일 Getter
	 * @return 작성일
	 */
	public Calendar getWriteDay() {
		return writeDay;
	}
	
	/***
	 * 게시글 작성일 Setter
	 * @param writeDay 작성일
	 */
	public void setWriteDay(Calendar writeDay) {
		this.writeDay = writeDay;
	}
	
}
