package com.test.market.mypage;

import java.util.Calendar;

/***
 * 충전내역을 기록하기 위한 클래스
 * @author 6조
 * 
 */
public class ChargeMoney {
	
	private String myId;
	private int money;
	private Calendar chargeDay;
	private int afterMoney;
	
	/***
	 * 충전 후 잔액 Getter
	 * @return 잔액
	 */
	public int getAfterMoney() {
		return afterMoney;
	}
	
	/***
	 * 충전 후 잔액 Setter
	 * @param afterMoney 잔액
	 */
	public void setAfterMoney(int afterMoney) {
		this.afterMoney = afterMoney;
	}
	
	/***
	 * 충전자의 아이디 Getter
	 * @return 아이디
	 */
	public String getMyId() {
		return myId;
	}
	
	/***
	 * 충전자의 아이디 Setter
	 * @param myId 아이디
	 */
	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	/***
	 * 충전금액 Getter
	 * @return 충전금액
	 */
	public int getMoney() {
		return money;
	}
	
	/***
	 * 충전금액 Setter
	 * @param money 충전금액
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	
	/***
	 * 충전날짜 Getter
	 * @return 충전날짜
	 */
	public Calendar getChargeDay() {
		return chargeDay;
	}
	
	
	/***
	 * 충전날짜 Setter
	 * @param chargeDay 충전날짜
	 */
	public void setChargeDay(Calendar chargeDay) {
		this.chargeDay = chargeDay;
	}
	
}
