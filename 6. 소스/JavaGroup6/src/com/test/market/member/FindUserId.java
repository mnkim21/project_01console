package com.test.market.member;

import java.util.ArrayList;
import java.util.Scanner;

import com.test.market.StartPage;

/***
 * 회원의 아이디 찾기를 위한 클래스
 * @author 6조
 *
 */
public class FindUserId {

	private static ArrayList<User> userList;
	private static Scanner scan;

	static {
		scan = new Scanner(System.in);
		userList = new ArrayList<User>();
	}

	/***
	 * 매개변수로 유저들 정보를 받는 생성자
	 * @param list 유저들 정보
	 */
	public FindUserId(ArrayList<User> list) {

		this.userList = list;

	}

	/***
	 * 아이디 찾기 이름,휴대폰번호 작성 메소드
	 */
	public void exec() {
		StartPage.loadWelcome();
		System.out.println("\n\n\n");

		System.out.println("==================================");
		System.out.println("             아이디 찾기");
		System.out.println("   (아래의 항목을 순서대로 입력하시오");
		System.out.println("==================================");
		System.out.print("1. 이름:");
		String name = scan.nextLine();
		System.out.print("2. 휴대폰번호(숫자만):");
		String tel = scan.nextLine();
		findId(name, tel);
	}

	/***
	 * 유저들 리스트중 입력한 이름, 전화번호가 일치하는지 찾는 메소드
	 * @param name 이름
	 * @param tel 전화번호
	 */
	public void findId(String name, String tel) {

		int check = 0;
		for (User list : userList) {

			if (list.getName().equals(name) && list.getTel().equals(tel)) {
				System.out.printf("회원님의 아이디는 %s 입니다\n", list.getId());
				check = 1;
				break;
			}
		}
		if (check == 0) {
			System.out.println("해당 아이디는 존재하지 않습니다.");
			System.out.println("==================================");
			System.out.println("         I.아이디 찾기 C.취소");
			System.out.println("==================================");
			System.out.print("입력: ");
			String faultSel = scan.nextLine();
			if (faultSel.equals("I")) {
				exec();
			}
		}
	}
}
