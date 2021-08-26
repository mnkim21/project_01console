package com.test.market.member;

import java.util.ArrayList;
import java.util.Scanner;

import com.test.market.StartPage;

/***
 * 회원의 비밀번호를 찾기위한 메소드 
 * @author 6조
 */
public class FindUserPw {

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
	public FindUserPw(ArrayList<User> list) {
		this.userList = list;
	}

	/***
	 * 비밀번호 찾기 이름,아이디,휴대폰번호 작성 메소드
	 */
	public void exec() {
		StartPage.loadWelcome();
		System.out.println("\n\n\n");

		System.out.println("==================================");
		System.out.println("            비밀번호 찾기");
		System.out.println("   (아래의 항목을 순서대로 입력하시오");
		System.out.println("==================================");
		System.out.print("1. 이름:");
		String name = scan.nextLine();
		System.out.print("2. 아이디:");
		String id = scan.nextLine();
		System.out.print("3. 휴대폰번호(숫자만):");
		String tel = scan.nextLine();

		findPw(name, id, tel);

	}


	/***
	 * 유저들 리스트중 입력한 이름,아이디, 전화번호가 일치하는지 찾는 메소드
	 * @param name 이름
	 * @param id 아이디
	 * @param tel 전화번호
	 */
	public void findPw(String name, String id, String tel) {

		int check = 0;
		for (User list : userList) {

			if (list.getName().equals(name) && list.getTel().equals(tel) && list.getId().equals(id)) {
				System.out.printf("회원님의 비밀번호는 %s 입니다\n", list.getPw());
				check = 1;
				break;
			}
		}
		if (check == 0) {
			System.out.println("올바른 정보를 입력해주세요.");
			System.out.println("==================================");
			System.out.println("         P.비밀번호 찾기 C.취소");
			System.out.println("==================================");
			System.out.print("입력: ");
			String faultSel = scan.nextLine();
			if (faultSel.equals("P")) {
				exec();

			}

		}

	}

}
