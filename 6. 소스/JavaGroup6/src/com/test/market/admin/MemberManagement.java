package com.test.market.admin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import com.test.market.MainPage;
import com.test.market.StartPage;
import com.test.market.member.User;

/***
 * 관리자의 회원관리를 위한 클래스 
 * @author 6조
 *
 */
public class MemberManagement {

	private static Scanner scan;
	private static ArrayList<User> userList;
	private static ArrayList<User> searchMemberList;
	private final static String USERDATA;
	private final static String DELETEUSER;
	private static MainPage mainPage;

	static {
		DELETEUSER = "folder\\deleteuser.txt";
		scan = new Scanner(System.in);
		searchMemberList = new ArrayList<User>();
		USERDATA = "folder\\user.txt";
		mainPage = new MainPage();
	}
	
	/***
	 * 매개변수로 유저들 정보를 넘겨받는 생성자
	 * @param userList 유저들 정보
	 */
	public MemberManagement(ArrayList<User> userList) {
		this.userList=userList;
	}

	/***
	 * 회원정보 리스트를 보여주는 메소드
	 */
	public void showMemberList() {

		boolean loop = true;

		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			int end = userList.size();
			int last = 0;

			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				
				System.out.println("==========================================================");
				System.out.println("                      [회원 정보 관리]");
				System.out.println("==========================================================");
				System.out.println("[이름]\t[아이디]\t\t[닉네임]\t[전화번호]\t\t[계좌번호]\t\t[보험가입여부]");

				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= userList.size()) {
						break;
					} else {
						System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n",userList.get(j).getName(),
								userList.get(j).getId(), userList.get(j).getNickname(), userList.get(j).getTel(),
								userList.get(j).getAccount(), userList.get(j).getInsurance());
						
					}
				}

				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1, end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("                P.이전 페이지  N.다음 페이지");
				System.out.println("              S.회원검색  D.회원삭제  0.뒤로가기 ");
				System.out.println("==========================================================");
				System.out.print("입력: ");
				String sel = scan.nextLine();

				if (sel.equals("P") || sel.equals("p")) {
					// 이전 페이지
					if (i != 0) {
						i--;
					} else {
						System.out.println("이전페이지가 없습니다");
						pause();
					}
				} else if (sel.equals("N") || sel.equals("n")) {
					// 다음페이지
					if (i + 1 >= (end / 10) + last) {
						System.out.println("다음페이지가 없습니다");
						pause();
					} else {
						i++;
					}
				} else if (sel.equals("S") || sel.equals("s")) {
					// 회원검색
					System.out.print("검색할 회원 정보 입력 : ");
					String memberInfo = scan.nextLine();
					searchMember(memberInfo);
				} else if (sel.equals("D") || sel.equals("d")) {
					// 회원 삭제
					System.out.print("삭제할 회원의 정보 입력(아이디/닉네임/전화번호/계좌번호만 입력가능) : ");
					String deleteInfo = scan.nextLine();
					deleteUserRecord(deleteInfo);
					deleteMember(deleteInfo);
					
					if(userList.size()%10==0&& i!=0) {
						if(i==userList.size()/10+last) {
							i--;
						}
						last=0;
						pause();
					}
				} else {
					// 메인페이지가기
					loop = false;
					break;
				}
			} // for(int i)
		} // while(loop)
		mainPage.setUserList(userList);
	}// showMemberList

	
	/***
	 * 회원을 삭제하는 메소드
	 * @param deleteInfo 삭제할 회원 정보
	 */
	public void deleteMember(String deleteInfo) {

		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId().equals(deleteInfo) || userList.get(i).getNickname().equals(deleteInfo)
					|| userList.get(i).getTel().equals(deleteInfo) || userList.get(i).getAccount().equals(deleteInfo)) {
				userList.remove(i);
				break;
			}
		}
		pause();
	}

	/***
	 * 회원삭제 기록을 하는 메소드
	 * @param deleteInfo 삭제할 회원 정보
	 */
	public void deleteUserRecord(String deleteInfo) {

		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId().equals(deleteInfo) || userList.get(i).getNickname().equals(deleteInfo)
					|| userList.get(i).getTel().equals(deleteInfo) || userList.get(i).getAccount().equals(deleteInfo)) {

				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(DELETEUSER, true));

					String temp = String.format("%s/%s/%s/%tF\n", userList.get(i).getId(),userList.get(i).getTel(), userList.get(i).getAccount(),
							Calendar.getInstance());

					writer.write(temp);
					writer.close();

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

	}

	/***
	 * 회원을 검색하는 메소드
	 * @param memberInfo 회원 정보
	 */
	public void searchMember(String memberInfo) {

		// 동명이인 있을 수 있으므로 페이징 만들기
		StartPage.loadWelcome();
		System.out.println("\n\n\n");

		if (!memberInfo.equals("")) {
			String[] searchMember = memberInfo.trim().split(" ");
			for (int i = 0; i < userList.size(); i++) {
				for (int j = 0; j < searchMember.length; j++) {
					if (userList.get(i).getName().equals(searchMember[j])
							|| userList.get(i).getId().equals(searchMember[j])
							|| userList.get(i).getNickname().equals(searchMember[j])
							|| userList.get(i).getTel().equals(searchMember[j])
							|| userList.get(i).getAccount().equals(searchMember[j])) {
						searchMemberList.add(userList.get(i));
					}
				}
			}
			if (searchMemberList.size() == 0) {
				System.out.println("일치하는 회원이 없습니다.");
				pause();
				return;
			} else {
				for (int i = 0; i < searchMemberList.size(); i++) {
					System.out.println("==========================================================");
					System.out.println("                      [회원 정보 관리]");
					System.out.println("==========================================================");
					System.out.println("[이름]\t[아이디]\t[닉네임]\t[전화번호]\t\t[계좌번호]\t\t[보험가입여부]");

					System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n",searchMemberList.get(i).getName(),
							searchMemberList.get(i).getId(), searchMemberList.get(i).getNickname(),
							searchMemberList.get(i).getTel(), searchMemberList.get(i).getAccount(),
							searchMemberList.get(i).getInsurance());

					System.out.println("==========================================================");
					System.out.println("                       0. 뒤로가기   ");
					System.out.println("==========================================================");
					System.out.print("입력: ");
					String sel2 = scan.nextLine();

					if (sel2.equals("0") || sel2.equals("o") || sel2.equals("O") || sel2.equals("ㅇ")) {
						searchMemberList = new ArrayList<User>();
						break;
					} 
				}
			}
		} // if(!memberInfo.equals("")
		return;
	}

	/***
	 * 출력을 잠시 멈추는 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();
	} // pause

	/***
	 * 다른 클래스로 부터 유저들 정보를 받는 메소드
	 * @param userList 유저들 정보
	 */
	public void setUserList(ArrayList<User> userList) {
		MemberManagement.userList = userList;
	}

}// MemberManagement
