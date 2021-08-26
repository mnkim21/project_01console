package com.test.market.member;

//이름/아이디/패스워드/닉네임/성별/핸드폰번호/계좌/보험여부/잔액/판매횟수

/***
 * 유저들 정보를 저장하기 위한 클래스
 * @author 6조
 *
 */
public class User {

	private String name;
	private String id;
	private String pw;
	private String nickname;
	private String gender;
	private String tel;
	private String account;
	private String insurance;
	private int money;
	private int count;

	/***
	 * 회원의 이름 Getter
	 * @return 이름
	 */
	public String getName() {
		return name;
	}

	/***
	 * 회원의 이름 Setter
	 * @param name 이름
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	/***
	 * 회원의 아이디 Getter
	 * @return 아이디
	 */
	public String getId() {
		return id;
	}

	/***
	 * 회원의 아이디 Setter
	 * @param id 아이디
	 */ 
	public void setId(String id) {
		this.id = id;
	}

	/***
	 * 회원의 비밀번호 Getter
	 * @return 비밀번호
	 */
	public String getPw() {
		return pw;
	}

	/***
	 * 회원의 비밀번호 Setter
	 * @param pw 비밀번호
	 */
	public void setPw(String pw) {
		this.pw = pw;
	}

	/***
	 * 회원의 닉네임 Getter
	 * @return 닉네임
	 */
	public String getNickname() {
		return nickname;
	}

	/***
	 * 회원의 닉네임 Setter
	 * @param nickname 닉네임
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
	/***
	 * 회원의 성별 Getter
	 * @return 성별
	 */
	public String getGender() {
		return gender;
	}

	
	/***
	 * 회원의 성별 Setter
	 * @param gender 성별
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/***
	 * 회원의 전화번호 Getter
	 * @return 전화번호
	 */
	public String getTel() {
		return tel;
	}

	/***
	 * 회원의 전화번호 Setter
	 * @param tel 전화번호
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/***
	 * 회원의 계좌번호 Getter
	 * @return 계좌번호
	 */
	public String getAccount() {
		return account;
	}
	
	/***
	 * 회원의 계좌번호 Setter
	 * @param account 계좌번호
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/***
	 * 회원의 보험험가입 유무 Getter
	 * @return 보험유무
	 */
	public String getInsurance() {
		return insurance;
	}

	/***
	 * 회원의 보험험가입 유무 Setter
	 * @param insurance 보험유무
	 */
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	
	/***
	 * 회원의 잔고 Getter
	 * @return 잔고
	 */
	public int getMoney() {
		return money;
	}

	
	/***
	 * 회원의 잔고 Setter
	 * @param money 잔고
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	
	/***
	 * 회원의 게시글 수 Getter
	 * @return 게시글 수
	 */
	public int getCount() {
		return count;
	}

	/***
	 * 회원의 게시글 수 Setter
	 * @param count 게시글 수
	 */
	public void setCount(int count) {
		this.count = count;
	}

}