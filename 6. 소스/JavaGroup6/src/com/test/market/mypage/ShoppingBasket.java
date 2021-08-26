package com.test.market.mypage;

/***
 * 장바구니 정보를 저장하기 위한 클래스
 * @author 6조
 * 
 */
public class ShoppingBasket {
	
	private String seq;
	private String writerNickname;
	private String myId;
	
	
	/***
	 * 장바구니에 담을 게시글의 번호 Getter
	 * @return 번호
	 */
	public String getSeq() {
		return seq;
	}
	
	/***
	 * 장바구니에 담을 게시글의 번호 Setter
	 * @param seq 번호
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	/***
	 * 장바구니에 담을 게시글의 게시자 닉네임 Getter
	 * @return 닉네임
	 */
	public String getWriterNickname() {
		return writerNickname;
	}
	
	/***
	 * 장바구니에 담을 게시글의 게시자 닉네임 Setter
	 * @param writerNickname 닉네임
	 */
	public void setWriterNickname(String writerNickname) {
		this.writerNickname = writerNickname;
	}
	
	/***
	 * 장바구니에 게시글을 담은 사람의 아이디 Getter
	 * @return 아이디
	 */
	public String getMyId() {
		return myId;
	}
	
	/***
	 * 장바구니에 게시글을 담은 사람의 아이디 Setter
	 * @param myId 아이디
	 */
	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	

}
