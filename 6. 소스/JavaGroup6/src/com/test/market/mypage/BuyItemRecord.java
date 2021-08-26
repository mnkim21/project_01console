package com.test.market.mypage;

import java.util.Calendar;

/***
 * 구매기록을 담기위한 클래스
 * @author 6조
 * 
 *
 */
public class BuyItemRecord {

	private String buyItem;
	private int price;
	private Calendar boughtDay;
	private String area;
	private String myId;
	
	/***
	 * 구매지역 Getter
	 * @return 구매지역
	 */
	public String getArea() {
		return area;
	}
	
	/***
	 * 구매지역 Setter
	 * @param area 구매지역
	 * 
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	/***
	 * 구매자의 아이디 Getter
	 * @return 아이디
	 */
	public String getMyId() {
		return myId;
	}
	
	/***
	 * 구매자의 아이디 Setter
	 * @param myId  아이디
	 * 
	 */
	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	/***
	 * 구매하는 물품명 Getter
	 * @return 물품명
	 */
	public String getBuyItem() {
		return buyItem;
	}
	
	/***
	 * 구매하는 물품명 Setter
	 * @param buyItem  물품명
	 * 
	 */
	public void setBuyItem(String buyItem) {
		this.buyItem = buyItem;
	}
	
	/***
	 * 물품가격 Getter
	 * @return 구매가격 
	 */
	public int getPrice() {
		return price;
	}
	
	/***
	 * 구매가격 Getter
	 * @param price 구매가격
	 *  
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/***
	 * 구매일자 Getter
	 * @return  구매일자
	 */
	public Calendar getBoughtDay() {
		return boughtDay;
	}
	
	/***
	 * 
	 * @param boughtDay 구매일자
	 * 구매일자 Setter 
	 */
	public void setBoughtDay(Calendar boughtDay) {
		this.boughtDay = boughtDay;
	}
	
}
