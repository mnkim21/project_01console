package com.test.market.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Scanner;

import com.test.market.MainPage;
import com.test.market.StartPage;
import com.test.market.board.Board;
import com.test.market.member.User;

/***
 * 공지사항의 목록을 보여주기 위한 클래스
 * @author 6조
 *
 */
public class NoticeList {

	private static Scanner scan;
	private static ArrayList<Notice> noticeList;
	private static User myInfo;
	private static MainPage mainPage;
	private final static String NOTICEDATA;

	static {
		NOTICEDATA = "folder\\notice.txt";
		scan = new Scanner(System.in);
		noticeList = new ArrayList<Notice>();
		myInfo = new User();
		mainPage = new MainPage();
	}

	/***
	 * 매개변수로 공지사항정보, 내정보를 받아오는 생성자
	 * @param noticeList 공지사항정보
	 * @param myInfo 내정보
	 */
	public NoticeList(ArrayList<Notice> noticeList,User myInfo) {

		this.noticeList=noticeList;
		this.myInfo = myInfo;
	}
	
	
	/***
	 * 공지사항목록을 보여주는 메소드
	 */
	public void showNoticeList() {

		int end = noticeList.size();
		int last = 0;

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				System.out.println("=====================================================================");
				System.out.println("                                 [ 공지사항 ]");
				System.out.println("=====================================================================");
				System.out.println("[번호]\t[작성자]\t[제목]\t\t\t[게시일]\n");
				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= noticeList.size()) {
						break;
					} else {
						System.out.printf("%s\t%s\t%s\t\t%tF\n", noticeList.get(j).getSeq(), "admin",
								noticeList.get(j).getTitle().length()>10?noticeList.get(j).getTitle().substring(0,10)+"..":noticeList.get(j).getTitle(), noticeList.get(j).getWriteDay());
					}
				}

				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1,
						end / 10 + last == 0 ? 1 : noticeList.size() / 10 + last);
				System.out.println("==========================================================");
				System.out.println("==========================================================");
				System.out.println("          P.이전 페이지 N.다음 페이지 I.공지사항 보기  ");
				System.out.println("             A.추가하기  D.삭제하기 C. 뒤로가기");
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
					if (i + 1 >= end / 10 + last) {
						System.out.println("다음페이지가 없습니다");
						pause();

					} else {
						i++;
					}

				} else if (sel.equals("A") || sel.equals("a")) {
					// 추가
					if (myInfo.getId().equals("admin")) {
						writeNotice();
						if (noticeList.size() % 10 == 1) {
							last = 1;
						}
					} else {
						System.out.println("권한이 없습니다");
						pause();
					}

				} else if (sel.equals("D") || sel.equals("d")) {

					if (myInfo.getId().equals("admin")) {
						System.out.print("번호를 입력해주세요: ");
						String strNum = scan.nextLine();
						while (checkNum(strNum) > 0) {
							System.out.println("번호를 잘못 입력하셨습니다");
							strNum = scan.nextLine();
						}
						for (int j = 0; j < noticeList.size(); j++) {
							if (noticeList.get(j).getSeq().equals(strNum)) {
								noticeList.remove(j);
								System.out.println("해당 공지가 삭제되었습니다.");
								if (noticeList.size() % 10 == 0 && i != 0) {
									if(i==noticeList.size()/10+last) {
										i--;
									}
									last = 0;
								}
								pause();
								break;
							}
						}
					} else {
						System.out.println("권한이 없습니다.");
						pause();
					}
				} else if (sel.equals("I") || sel.equals("i")) {

					System.out.print("공지사항 번호입력: ");
					String boardNum = scan.nextLine();
					// 조회수 올리기
					hitBoard(boardNum);//
					// 게시글 보기
					showBoardDetail(boardNum);

				} else {
					// 뒤로가기
					mainPage.setNoticeList(noticeList);
					loop = false;
					break;
				}
			}
		}
	}

	/***
	 * 공지사항의 상세정보를 보여주는 메소드
	 * @param boardNum 게시글번호
	 */
	public void showBoardDetail(String boardNum) {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			Notice board = new Notice();
			for (Notice getlist : noticeList) {

				if (getlist.getSeq().equals(boardNum)) {

					System.out.println("==========================================================");
					System.out.printf("                    %s        \n", getlist.getTitle());
					System.out.printf("                               (업로드날짜:%tF/조회수:%,d)\n", getlist.getWriteDay(),
							getlist.getHit());
					System.out.println("==========================================================");
					System.out.printf("- 작성자 		:%s\n", "admin");
					System.out.printf("- 내용:\n%s", getlist.getContent());
					System.out.println("==========================================================");
					System.out.println("                          0.뒤로가기");
					System.out.println("==========================================================");
					System.out.print("입력: ");
					String sel = scan.nextLine();

					if (sel.equals("0")) {
						loop = false;
						break;
					} else {
						loop = false;
						break;
					}
				}
			}
		}

		pause();
	}

	/***
	 * 공지사항의 조회수를 증가시키기 위한 메소드
	 * @param board 게시글의 번호
	 */
	public void hitBoard(String board) {

		for (int i = 0; i < noticeList.size(); i++) {
			if (noticeList.get(i).getSeq().equals(board)) {
				noticeList.get(i).setHit(noticeList.get(i).getHit() + 1);
			}
		}
	}

	/***
	 * 공지사항의 작성을 위한 메소드
	 */
	public void writeNotice() {

		noticeList.sort(new Comparator<Notice>() {

			@Override
			public int compare(Notice o1, Notice o2) {

				return Integer.parseInt(o2.getSeq()) - Integer.parseInt(o1.getSeq());
			}
		});
		StartPage.loadWelcome();
		System.out.println("\n\n\n");

		System.out.println("===========================================");
		System.out.println("                 [공지추가]");
		System.out.println("===========================================");
		System.out.println();

		System.out.print("제목: ");
		String title = scan.nextLine();
		// 데이터 구분자인 슬래시가 들어가면 안됨
		while (title.contains("/") || title.length() >= 20) {
			System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
			System.out.print("제목: ");
			title = scan.nextLine();
		}

		System.out.print("내용(종료:exit): ");
		String content = "";

		while (true) {
			String temp = scan.nextLine();
			if (temp.equals("exit")) {
				break;
			}
			content += temp + "\r\n";
		}

		System.out.println("===========================================");
		System.out.println("           S.작성완료    C.작성취소");
		System.out.println("===========================================");
		System.out.print("입력: ");
		String sel = scan.nextLine();

		if (sel.equals("S") || sel.equals("s")) {
			Notice notice = new Notice();

			if (noticeList.size() == 0) {
				notice.setSeq("1");
			} else {
				notice.setSeq(String.valueOf(Integer.parseInt(noticeList.get(0).getSeq()) + 1));

			}
			notice.setTitle(title);
			notice.setWriteDay(Calendar.getInstance());
			notice.setHit(0);
			notice.setContent(content);
			noticeList.add(notice);

			System.out.println("작성이 완료되었습니다.");
		} else {
			System.out.println("작성이 취소되었습니다.");
		}
		pause();
	}

	/***
	 * 게시글을 보기위한 번호를 입력할때 숫자인지 확인하는 메소드
	 * @param num 게시글번호
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkNum(String num) {
		int count = 0;
		for (int i = 0; i < num.length(); i++) {
			char c = num.charAt(i);
			if (!(c >= '0' && c <= '9')) {
				count++;
			}
		}
		return count;
	}

	/***
	 * 출력을 잠시 멈추는 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();

	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 공지사항정보를 저장하는 메소드
	 * @param noticeList 공지사항 정보
	 */
	public static void setNoticeList(ArrayList<Notice> noticeList) {
		NoticeList.noticeList = noticeList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 내정보를 저장하는 메소드
	 * @param myInfo 내정보
	 */
	public static void setMyInfo(User myInfo) {
		NoticeList.myInfo = myInfo;
	}

}
