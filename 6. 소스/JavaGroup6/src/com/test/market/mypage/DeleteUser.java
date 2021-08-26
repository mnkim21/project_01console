package com.test.market.mypage;

import java.util.Calendar;

/***
 * 회원탈퇴 정보를 저장하기 위한 클래스
 * @author 6조
 *
 */
public class DeleteUser {
	
	private String id;
	private String tel;
	private String account;
	private Calendar deleteDate;
	
	
	/***
	 * 탈퇴자 아이디 Getter
	 * @return 아이디
	 */
	public String getId() {
		return id;
	}
	
	/***
	 * 탈퇴자 아이디 Setter
	 * @param id 아이디
	 */
	public void setId(String id) {
		this.id = id;
	}
	

	/***
	 * 탈퇴자 전화번호 Getter
	 * @return 전화번호
	 */
	public String getTel() {
		return tel;
	}
	
	/***
	 * 탈퇴자 전화번호 Setter
	 * @param tel 전화번호
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	/***
	 * 탈퇴자 계좌번호 Getter
	 * @return 계좌번호
	 */
	public String getAccount() {
		return account;
	}
	
	/***
	 * 탈퇴자 계좌번호 Setter
	 * @param account 계좌번호
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	/***
	 * 탈퇴일 Getter
	 * @return 탈퇴일
	 */
	public Calendar getDeleteDate() {
		return deleteDate;
	}
	
	/***
	 * 탈퇴일 Setter
	 * @param deleteDate 탈퇴일
	 */
	public void setDeleteDate(Calendar deleteDate) {
		this.deleteDate = deleteDate;
	}
	
	
	
	

}
