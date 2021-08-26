package com.test.market.mypage;

import java.util.Calendar;

/***
 * 출금하기위한 정보를 저장하기 위한 클래스
 * @author 6조
 * 
 */
public class Withdraw {

	private String myId;
	private int money;
	private Calendar withdrawDay;
	private int afterMoney;
	
	
	/***
	 * 출금 후 잔액 Getter
	 * @return 잔액
	 */ 
	public int getAfterMoney() {
		return afterMoney;
	}
	
	/***
	 * 촐금 후 잔액 Setter
	 * @param afterMoney 출금 후 잔액
	 */
	public void setAfterMoney(int afterMoney) {
		this.afterMoney = afterMoney;
	}
	
	/***
	 * 출금하는 사람의 아이디 Getter
	 * @return 아이디
	 */
	public String getMyId() {
		return myId;
	}
	
	/***
	 * 출금하는 사람의 아이디 Setter
	 * @param myId 아이디
	 */
	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	/***
	 * 출금 금액 Getter
	 * @return 금액
	 */
	public int getMoney() {
		return money;
	}
	
	/***
	 * 출금 금액 Setter
	 * @param money 출금금액
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	

	/***
	 * 출금 날짜 Getter
	 * @return 날짜
	 */
	public Calendar getWithdrawDay() {
		return withdrawDay;
	}
	
	/***
	 * 출금날짜 Setter
	 * @param withdrawDay 출금날짜
	 */
	public void setWithdrawDay(Calendar withdrawDay) {
		this.withdrawDay = withdrawDay;
	}
	
	
}
