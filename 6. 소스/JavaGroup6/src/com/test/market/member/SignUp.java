package com.test.market.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.market.StartPage;
import com.test.market.mypage.DeleteUser;

/***
 * 회원가입을 하기위한 클래스
 * @author 6조
 */
public class SignUp {
	private final static String DELETEUSER;
	private static Scanner scan;
	private static ArrayList<DeleteUser> deleteUserList;
	private static ArrayList<User> userList;
	private static StartPage startPage;

	static {
		scan = new Scanner(System.in);
		DELETEUSER="folder\\deleteuser.txt";
		deleteUserList = new ArrayList<DeleteUser>();
		userList = new ArrayList<User>();
		startPage = new StartPage();
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 유저들 정보를 저장하기 위한 메소드
	 * @param userList 유저들 정보
	 */
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}


	/***
	 *회원가입 실행 메소드 
	 */
	public void exec() {

		//최근 삭제 기록을 가져오는 메소드입니다.
		getCheckDeleteUser();
		
		System.out.println("=================================");
		System.out.println("            회원가입");
		System.out.println("   (아래의 항목을 순서대로 입력하시오)");
		System.out.println("=================================");
				
		System.out.print("1.이름: ");
		String name = scan.nextLine();
		boolean nameLoop = true;
		
		//1. 이름 : 한글, 영어 대소문자 입력가능
		while (nameLoop) {
			if (checkName(name) > 0) {
				System.out.println("올바르지 않은 단어가 포함되어 있습니다");
				System.out.print("1.이름: ");
				name = scan.nextLine();
			} else {
				nameLoop = false;
			}
		}
		
	    //2. 아이디 : 영어 대소문와 숫자를 조합하여 4~10자리만 입력가능/ 첫 글자는 숫자를 입력할 수 없음
		System.out.print("2.아이디: ");
		String id = scan.nextLine();
		boolean idLoop = true;
		while (idLoop) {
			if (checkId(id) > 0) {
				System.out.println("아이디를 다시 입력하세요.");
				System.out.print("2.아이디: ");
				id = scan.nextLine();
			} else {
				idLoop = false;
			}
		}

		//3. 비밀번호 : 영어 대소문자, 숫자, 특수문자(!,@,#,$)를 조합하여 입력가능
		System.out.print("3.비밀번호: ");
		String pw = scan.nextLine();
		boolean pwLoop = true;
		while (pwLoop) {
			if (checkPw(pw) > 0) {
				System.out.println("영문자, 숫자, 특수문자(!,@,#,$)만 입력가능합니다.");
				System.out.print("3.비밀번호: ");
				pw = scan.nextLine();
			} else {
				pwLoop = false;
			}
		}
		
		//4. 닉네임 : 한글을 이용하여 4글자 입력가능
		System.out.print("4.닉네임: ");
		String nickname = scan.nextLine();
		boolean nicknameLoop = true;
		while (nicknameLoop) {
			if (checkNickname(nickname) > 0) {
				System.out.println("닉네임을 다시 입력하세요");
				System.out.print("4.닉네임: ");
				nickname = scan.nextLine();
			} else {
				nicknameLoop = false;
			}
		}

		//5. 성별 : F,f,M,m만 입력가능 / 입력받은 값을 대문자로 변환하여 비교
		System.out.print("5.성별(F.여/M.남): ");
		String genderEnter = scan.nextLine();
		String gender = genderEnter.toUpperCase();
		
		while (checkGender(gender) == 1) {			
			System.out.print("5.성별(F.여/M.남): ");
			genderEnter = scan.nextLine();
			gender = genderEnter.toUpperCase();
		}
		
		//입력받은 영문자를 각각 "여","남"으로 변환하여 User.txt에 저장
		if(gender.equals("F")) {
			gender = "여";
		} else if(gender.equals("M")) {
			gender = "남";
		}

		//6. 휴대폰번호 : 숫자만 입력가능/ 반드시 010으로 시작해야되며 이후에는 숫자8자리 입력가능
		System.out.print("6.휴대폰번호(숫자만): ");
		String tel = scan.nextLine();
		boolean telLoop = true;
		while (telLoop) {
			if (checkTel(tel) > 0) {
				System.out.println("휴대폰번호를 다시 입력하세요");
				System.out.print("6.휴대폰번호(숫자만): ");
				tel = scan.nextLine();
			} else {
				telLoop = false;
			}
		}	
		
		//7. 계좌번호 : 숫자를 이용하여 12자리 입력가능
		System.out.print("7.계좌번호(12자리/숫자만): ");
		String account = scan.nextLine();
		boolean accountLoop = true;
		while (accountLoop) {
			if (checkAccount(account) > 0) {
				System.out.println("계좌번호를 다시 입력하세요");
				System.out.print("7.계좌번호(12자리/숫자만): ");
				account = scan.nextLine();
			} else {
				accountLoop = false;
			}
		}
		
		//8. 보험 : Y,y,N,n만 입력가능
		System.out.print("8.보험가입여부(Y/N): ");
		String insuranceEnter = scan.nextLine();
		String insurance = insuranceEnter.toUpperCase();
		
		while (checkInsurance(insurance) == 1) {
			System.out.print("8.보험가입여부(Y/N): ");
			insuranceEnter = scan.nextLine();
			insurance = insuranceEnter.toUpperCase();
		}
		
		System.out.println("=================================");
		System.out.println("\t[이름]\t[아이디]\t[비밀번호]\t\t[닉네임]\t[성별]\t\t[휴대폰번호]\t\t[계좌번호]\t[보험가입]");
		System.out.printf("입력정보: %s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"
							, name, id, pw, nickname, gender, tel, account, insurance);
		System.out.println("=================================");

		System.out.println("=================================");
		System.out.println("         S.가입진행   C.가입취소");
		System.out.println("=================================");

		System.out.print("입력: ");
		String choice = scan.nextLine();
		
		//최근 탈퇴기록을 확인합니다. 탈퇴후 10일후에 가입 가능합니다
		for(int i=0;i<deleteUserList.size();i++) {
			if(deleteUserList.get(i).getTel().equals(tel)
			||deleteUserList.get(i).getId().equals(id)
			||deleteUserList.get(i).getAccount().equals(account)) {
				if(((Calendar.getInstance().getTimeInMillis()-deleteUserList.get(i).getDeleteDate().getTimeInMillis())/1000/60/60/24)<10) {
					System.out.println("최근에 회원탈퇴한 계정입니다. 탈퇴후 10일후 재가입을 할 수 있습니다");
					pause();
					choice="C";
					break;
					
				}
				
			}
		}

		if (choice.toUpperCase().equals("S")) {
			User user = new User();
			user.setName(name);
			user.setId(id);
			user.setPw(pw);
			user.setNickname(nickname);
			user.setGender(gender);
			user.setTel(tel);
			user.setAccount(account);
			user.setInsurance(insurance);
			user.setMoney(0);
			user.setCount(0);
			userList.add(user);
			System.out.println("축하합니다. 가입이 완료되었습니다");
			pause();
			
			//회원가입한 정보가 들어있는 유저리스트를 스타트페이지에 보냄으로써 갱신이 된다.
			startPage.setUserList(userList);
			
			//가입한 내 정보를 넘겨주는 역할이다
			startPage.setMyInfo(user);
			
		} else {
			
			System.out.println("회원가입이 취소되었습니다.");
			pause();
			
		}

	}
	
	
	/***
	 * 
	 * @param name 이름 유효성 검사를 위한 매개변수
	 * 이름 유효성 검사를 위한메소드 한글, 영어 대소문자만 입력 가능
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkName(String name) {

		int count = 0;
		
		for (int i = 0; i < name.length(); i++) {

			char c = name.charAt(i);
			if (!(c >= '가' && c <= '힣' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
				count++;
			}
		}
		return count;
	}
	
	/***
	 * 
	 * @param id 아이디의 유효성 및 중복 검사를 위한 매개변수
	 * 아이디의 유효성 및 중복 검사 메소드
	 * 영어 대소문자, 숫자를 조합하여 4~10자 입력가능
	 * 단, 아이디의 시작은 영어 대소문자만 가능
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkId(String id) {
		
		int count = 0;
		
		String regex = "^[A-Za-z][A-Za-z0-9]{3,9}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(id);
		
		if(m.find()) {
			
			for (User list : userList) {
				
				if (list.getId().equals(id)) {
					System.out.println("이미 사용중인 아이디 입니다");
					count = 1;
					break;
				} else {
					count = 0;
				}
			}
		} else {
			System.out.println("영문자와 숫자를 이용하여 4~10자 입력가능합니다.");
			count = 1;
		}
		return count;
	}

	/***
	 * 
	 * @param pw 비밀번호 유효성 검사를 위한 매개변수
	 * 비밀번호 유효성 검사 메소드
	 * 숫자, 영어 대소문자, 특수문자(!,@,#,$)만 입력가능
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkPw(String pw) {

		int count = 0;
		
		for (int i = 0; i < pw.length(); i++) {
			char c = pw.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '!' || c == '@' || c == '#' || c == '$')) {
				count++;
			}
		}
		return count;
	}

	/***
	 * 
	 * @param nickname 닉네임 유효성 및 중복 검사를 위한 매개변수
	 * 닉네임 유효성 및 중복 검사 메소드
	 * - 한글을 이용하여 4자리 입력가능
	 * 
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkNickname(String nickname) {

		int count = 0;
		
		String regex = "^[가-힣]{4}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(nickname);
		
		if(m.find()) {

			for (User list : userList) {
				
				if (list.getNickname().equals(nickname)) {
					System.out.println("이미 사용중인 닉네임 입니다");
					count = 1;
					break;
				} else {
					count = 0;
				}
			}
		} else {
			System.out.println("한글 4글자만 입력가능합니다.");
			count = 1;
		}
		return count;
	}
	
	
	/***
	 * 
	 * @param gender 성별 유효성 검사를 위한 매개변수
	 * 성별 유효성 검사 메소드
	 * F,f,M,m만 입력가능
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkGender(String gender) {
		
		if (gender.equals("F") || gender.equals("M")) {
			return 0;
		} else {
			System.out.println("잘못 입력하셨습니다");
			return 1;
		}
	}

	/***
	 * @param tel 전화번호 유효성 및 중복 검사 매개변수
	 * 전화번호 유효성 및 중복 검사 메소드
	 * 숫자만 입력가능
	 * 반드시 010으로 시작해야 됨
	 * 010이후에는 숫자 8자리 입력가능
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkTel(String tel) {

		int count = 0;
		
		String regex = "^(010)[0-9]{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(tel);
		
		if(m.find()) {
			for (User list : userList) {
				
				if (list.getTel().equals(tel)) {
					System.out.println("이미 사용중인 전화번호 입니다");
					count = 1;
					break;
				} else {
					count = 0;
				}
			}
		} else {
			System.out.println("010부터 숫자를 입력하세요");
			count = 1;
		}
		return count;
	}
		
	/***
	 * 
	 * @param account 계좌번호 유효성 및 중복 검사를 위한 매개변수
	 * 계좌번호 유효성 및 중복 검사 메소드
	 * 숫자를 이용하여 12자리 입력가능
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkAccount(String account) {

		int count = 0;
		
		String regex = "^[0-9]{12}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(account);
		
		if(m.find()) {
			for (User list : userList) {
				
				if (list.getAccount().equals(account)) {
					System.out.println("이미 사용중인 계좌번호 입니다");
					count = 1;
					break;
				} else {
					count = 0;
				}
			}
		} else {
			System.out.println("숫자 12자리만 입력가능합니다");
			count = 1;
		}
		return count;
	}
	
	/***
	 * 
	 * @param insurance 보험 유효성 검사를 위한 매개변수
	 * 보험 유효성 검가 메소드
	 * Y,y,N,n만 입력가능
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkInsurance(String insurance) {
		
		if(insurance.equals("Y") || insurance.equals("N")) {
			return 0;
		} else {
			System.out.println("잘못 입력하셨습니다");
			return 1;
		}

	}
	
	/***
	 * 탈퇴기록을 가져오는 메소드
	 */
	public void getCheckDeleteUser() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(DELETEUSER));
			
			String line="";
			while((line=reader.readLine())!=null) {

				DeleteUser deleteUser = new DeleteUser();
				
				String temp[] = line.split("/");

				deleteUser.setId(temp[0]);
				deleteUser.setTel(temp[1]);
				deleteUser.setAccount(temp[2]);
				Calendar c = Calendar.getInstance();
				String[] time = temp[3].split("-");
				c.set(Integer.parseInt(time[0]),Integer.parseInt(time[1])-1,Integer.parseInt(time[2]));
				deleteUser.setDeleteDate(c);
				deleteUserList.add(deleteUser);
			}
			reader.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());

		
		}
	}
	
	/***
	 * 출력을 멈추는 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();
	}
}
