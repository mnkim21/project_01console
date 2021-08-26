package com.test.market.member;

import java.util.ArrayList;
import java.util.Scanner;

import com.test.market.StartPage;

/***
 * 로그인을 하기위한 클래스
 * @author 6조
 *
 */
public class SignIn {

	private static ArrayList<User> userList;
	private static Scanner scan;
	private static User myUser;
	private static StartPage startPage;

	static {
		scan = new Scanner(System.in);
		userList = new ArrayList<User>();
		myUser = new User();
		startPage = new StartPage();
	}


	/***
	 * 다른 클래스로 해당메소드가 존재하는 클래스에 내정보를 저장하는 메소드
	 * @param myUser 내정보
	 */
	public void setMyUser(User myUser) {
		this.myUser = myUser;
	}

	/***
	 * 다른 클래스로 해당메소드가 존재하는 클래스에 유저정보를 저장하는 메소드
	 * @param userList 유저들정보
	 */
	public static void setUserList(ArrayList<User> userList) {
		SignIn.userList = userList;
	}

	/***
	 * 로그인을 실행하는 메소드
	 * @return 아이디찾기2, 비밀번호찾기3, 다시로그인4, 그외 0
	 */
	public int exec() {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			System.out.println("=================================");
			System.out.println("              로그인");
			System.out.println("=================================");
			System.out.print("아이디: ");
			String id = scan.nextLine();
			System.out.print("비밀번호: ");
			String pw = scan.nextLine();

			int getNum = check(id, pw);

			if (getNum == 4) {
				continue;
			}
			return getNum;
		}
		return 0;

	}

	/***
	 * 아이디,비밀번호 입력을 통해 로그인 성공,실패를 판단하는 메소드
	 * @param id 아이디
	 * @param pw 패스워드
	 * @return 아이디찾기2, 비밀번호찾기3, 다시로그인4, 그외 0
	 */
	public int check(String id, String pw) {

		for (User list : userList) {

			if (list.getId().equals(id) && list.getPw().equals(pw)) {
				System.out.println(list.getNickname() + "님, 로그인 되었습니다");
				System.out.println("(엔터 입력 시, 메인목록으로 갑니다.)");
				startPage.setMyInfo(list);
				pause();
				return 1;
			}
		}
		System.out.println("아이디와 비밀번호가 일치하지 않습니다.");
		System.out.println("====================================================");
		System.out.println("             I.아이디 찾기 P.비밀번호 찾기");
		System.out.println("                R.다시하기    C.취소");
		System.out.println("====================================================");
		System.out.print("입력: ");
		String sel = scan.nextLine();

		if (sel.equals("I")||sel.equals("i")) {
			System.out.println("아이디 찾기로 이동합니다");
			return 2;
		} else if (sel.equals("P") || sel.equals("p")) {
			System.out.println("비밀번호 찾기로 이동합니다");
			return 3;
		} else if (sel.equals("R") || sel.equals("r")) {
			System.out.println("로그인을 다시 합니다 이동합니다");
			return 4;
		} else {
			System.out.println("취소되었습니다.");
			return 0;
		}
	}

	/***
	 * 출력을 잠시 멈추는 메소드
	 */
	public static void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();

	}

}
