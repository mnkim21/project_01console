package com.test.market.board;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import com.test.market.MainPage;
import com.test.market.StartPage;
import com.test.market.member.User;
import com.test.market.mypage.MyPage;
import com.test.market.mypage.ShoppingBasket;

/***
 * 판매 게시글을 작성하기 위한 클래스
 * 
 * @author 6조
 *
 */
public class BoardList {

	private static Scanner scan;
	private static ArrayList<Board> boardList;
	private static ArrayList<Board> searchBoardList;
	private static ArrayList<User> userList;
	private static ArrayList<ShoppingBasket> shoppingBasketList;
	private static ArrayList<ReportRecord> reportList;
	private static User myInfo;
	private static MainPage mainPage;
	private static Random rnd;
	private final static String DELETEBOARDDATA;
	private final static String BUYITEMDATA;

	static {
		scan = new Scanner(System.in);
		rnd = new Random();
		mainPage = new MainPage();
		shoppingBasketList = new ArrayList<ShoppingBasket>();
		reportList = new ArrayList<ReportRecord>();
		searchBoardList = new ArrayList<Board>();
		myInfo = new User();
		DELETEBOARDDATA = "folder\\deleteboard.txt";
		BUYITEMDATA = "folder\\buyrecord.txt";
	}

	/***
	 * 
	 * @param itemSearch 검색어를 받기 게시글 리스트를 보여주는 메소드
	 * @return 메소드를 끝내기 위한 리턴(뒤로가기/게시글 작성)
	 */
	public int showBoardList(String itemSearch) {

		if (!itemSearch.equals("")) {
			// 물품검색 했을 때! 연관 보드 리스트 가져오기
			String[] searchTemp = itemSearch.trim().split(" ");
			for (int i = 0; i < boardList.size(); i++) {
				for (int j = 0; j < searchTemp.length; j++) {
					if (boardList.get(i).getTitle().contains(searchTemp[j])
							|| boardList.get(i).getArea().contains(searchTemp[j])
							|| boardList.get(i).getCategory().contains(searchTemp[j])) {
						searchBoardList.add(boardList.get(i));

					}
				}
			}

			if (searchBoardList.size() == 0) {
				System.out.println("결과물이 없습니다.");
				pause();
				return 0;
			}

			int end = searchBoardList.size();
			searchBoardList.sort(new Comparator<Board>() {

				@Override
				public int compare(Board o1, Board o2) {
					return o2.getCalendar().compareTo(o1.getCalendar());
				}
			});

			int last = 0;

			boolean loop = true;
			while (loop) {
				StartPage.loadWelcome();
				System.out.println("\n\n\n");
				if (end % 10 > 0) {
					last = 1;
				}

				for (int i = 0; i <= end / 10 + last;) {
					searchBoardList.sort(new Comparator<Board>() {

						@Override
						public int compare(Board o1, Board o2) {
							return o2.getCalendar().compareTo(o1.getCalendar());
						}
					});
					if (searchBoardList.size() % 10 == 0 && i != 0) {
						if (i == searchBoardList.size() / 10 + last) {
							i--;
						}
						last = 0;
						pause();
					}
					System.out.println("=======================================================================");
					System.out.println("                           [게시글 목록보기]");
					System.out.println("=======================================================================");
					System.out.println("[번호]\t[제목]\t\t[상태]\t[가격]\t[사용기간]\t[희망거래지역]\t[닉네임]\t");
					for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
						if (j >= searchBoardList.size()) {
							break;
						} else {
							System.out.printf("%s\t%s\t%s\t%,d\t%s개월\t%s\t\t%s\t\n", searchBoardList.get(j).getSeq(),
									searchBoardList.get(j).getTitle(), searchBoardList.get(j).getTrade_status(),
									searchBoardList.get(j).getPrice(), searchBoardList.get(j).getUse_date(),
									searchBoardList.get(j).getArea(), searchBoardList.get(j).getNickname());
						}
					}
					System.out.println("=======================================================================");
					System.out.printf("                         %d/%d\n", i + 1,
							end / 10 + last == 0 ? 1 : end / 10 + last);
					System.out.println("=======================================================================");
					System.out.println("                  P.이전 페이지  N.다음 페이지  W.게시글 작성");
					System.out.println("                  D.게시글 보기  S.페이지 검색  C.뒤로가기");
					System.out.println("=======================================================================");
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
						if (!(i + 1 >= end / 10 + last)) {
							i++;
						} else {
							System.out.println("다음페이지가 없습니다");
							pause();
						}

					} else if (sel.equals("D") || sel.equals("d")) {

						System.out.print("게시글 번호입력: ");
						String boardNum = scan.nextLine();
						// 조회수 올리기
						hitBoard(boardNum);//

						// 게시글 보기
						showBoardDetail(boardNum);

					} else if (sel.equals("S") || sel.equals("s")) {
						// 페이지 검색
						System.out.print("페이지 입력: ");
						int search = scan.nextInt();
						scan.skip("\r\n");
						if (search < 1) {
							System.out.println("해당 페이지는 없습니다");
							pause();
						} else if (search > end / 10 + last) {
							System.out.println("해당 페이지는 없습니다");
							pause();
						} else {
							i = search - 1;
						}

					} else if (sel.equals("W") || sel.equals("w")) {
						// 게시글 작성
						mainPage.setUserList(userList);
						mainPage.setBoardList(boardList);
						mainPage.setMyInfo(myInfo);
						mainPage.setShoppingBasketList(shoppingBasketList);
						mainPage.setReportList(reportList);
						return 1;
					} else {
						// 뒤로가기
						mainPage.setUserList(userList);
						mainPage.setBoardList(boardList);
						mainPage.setMyInfo(myInfo);
						mainPage.setShoppingBasketList(shoppingBasketList);
						mainPage.setReportList(reportList);
						loop = false;
						return 0;
					}
				}

			}
		} else {
			int end = boardList.size();
			int last = 0;
			// 게시글 목록 눌렀을때 그냥 업로드 날짜 순으로 보여주기위해 정렬
			boardList.sort(new Comparator<Board>() {

				@Override
				public int compare(Board o1, Board o2) {
					return o2.getCalendar().compareTo(o1.getCalendar());
				}
			});
			boolean loop = true;
			while (loop) {
				StartPage.loadWelcome();
				System.out.println("\n\n\n");
				if (end % 10 > 0) {
					last = 1;
				}

				for (int i = 0; i <= end / 10 + last;) {
					boardList.sort(new Comparator<Board>() {

						@Override
						public int compare(Board o1, Board o2) {
							return o2.getCalendar().compareTo(o1.getCalendar());
						}
					});
					if (boardList.size() % 10 == 0 && i != 0) {
						if (i == boardList.size() / 10 + last) {
							i--;
						}
						last = 0;
						pause();
					}
					System.out.println("=======================================================================");
					System.out.println("                           [게시글 목록보기]");
					System.out.println("=======================================================================");
					System.out.println("[번호]\t[제목]\t\t[상태]\t[가격]\t[사용기간]\t[희망거래지역]\t[닉네임]\t");
					for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
						if (j >= boardList.size()) {
							break;
						} else {
							System.out.printf("%s\t%s\t%s\t%,d\t%s개월\t%s\t\t%s\t\n", boardList.get(j).getSeq(),
									boardList.get(j).getTitle(), boardList.get(j).getTrade_status(),
									boardList.get(j).getPrice(), boardList.get(j).getUse_date(),
									boardList.get(j).getArea(), boardList.get(j).getNickname());
						}
					}
					System.out.println("=======================================================================");
					System.out.printf("                         %d/%d\n", i + 1,
							end / 10 + last == 0 ? 1 : end / 10 + last);
					System.out.println("=======================================================================");
					System.out.println("                  P.이전 페이지  N.다음 페이지  W.게시글 작성");
					System.out.println("                  D.게시글 보기  S.페이지 검색  C.뒤로가기");
					System.out.println("=======================================================================");
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
						if (!(i > end / 10 + last)) {
							i++;
						} else {
							System.out.println("다음페이지가 없습니다");
							pause();
						}

					} else if (sel.equals("D") || sel.equals("d")) {

						System.out.print("게시글 번호입력: ");
						String boardNum = scan.nextLine();
						// 조회수 올리기
						hitBoard(boardNum);
						// 게시글 보기
						showBoardDetail(boardNum);

					} else if (sel.equals("S") || sel.equals("s")) {
						// 페이지 검색
						System.out.print("페이지 입력: ");
						int search = scan.nextInt();
						scan.skip("\r\n");
						if (search < 1) {
							System.out.println("해당 페이지는 없습니다");
							pause();
						} else if (search > end / 10 + last) {
							System.out.println("해당 페이지는 없습니다");
							pause();
						} else {
							i = search - 1;
						}

					} else if (sel.equals("W") || sel.equals("w")) {
						// 게시글 작성
						return 1;
					} else {
						// 뒤로가기
						loop = false;
						return 0;
					}
				}

//			pause();

			}
		}
		return 0;

	}

	/***
	 * 
	 * @param num 게시글 번호를 받는다. 조회수를 1증가 시키는 메소드
	 */
	public void hitBoard(String num) {

		for (int i = 0; i < boardList.size(); i++) {
			if (boardList.get(i).getSeq().equals(num)) {
				boardList.get(i).setHit(boardList.get(i).getHit() + 1);
				mainPage.setBoardList(boardList);
			}
		}
	}

	/***
	 * 
	 * @param boardNum 게시글 번호를 받는다. 게시글의 상세내용을 보기위한 메소드
	 */
	public void showBoardDetail(String boardNum) {

		boolean loop = true;

		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			Board board = new Board();
			for (Board getlist : boardList) {

				if (getlist.getSeq().equals(boardNum)) {

					System.out.println("=======================================================================");
					System.out.printf("          %s(%s) %s           (업로드날짜:%tF/조회수:%,d)\n"
									  , getlist.getSeq()
									  , getlist.getTrade_status()
									  , getlist.getTitle()
									  , getlist.getCalendar()
									  , getlist.getHit());
					System.out.println("=======================================================================");
					System.out.printf("- 닉네임 : %s\n", getlist.getNickname());
					System.out.printf("- 물품명 : %s\n", getlist.getItem());
					System.out.printf("- 가격 :%,d원\n", getlist.getPrice());
					System.out.printf("- 사용기간 : %d개월\n", getlist.getUse_date());
					System.out.printf("- 희망거래지역 :%s\n", getlist.getArea());
					System.out.printf("- 내용 :\n%s", getlist.getContent());
					System.out.println("=======================================================================");
					System.out.println("            E.게시글 수정  D.게시글 삭제  R.댓글 작성  P.게시글 신고"); // 작성자가 나라면
					System.out.println("                   B.구매하기  S.장바구니 담기  C.뒤로가기");
					System.out.println("=======================================================================");
					System.out.print("입력: ");
					String num = scan.nextLine();

					if (num.equals("E")) {
						// 게시글 수정
						if (getlist.getNickname().equals(myInfo.getNickname())
								|| myInfo.getNickname().equals("admin")) {
							modifyBoard(getlist.getSeq());
						} else {
							System.out.println("수정할 수 있는 권한이 없습니다.");
							pause();
						}

					} else if (num.equals("D") || num.equals("d")) {
						// 게시글 삭제
						if (getlist.getNickname().equals(myInfo.getNickname())
								|| myInfo.getNickname().equals("admin")) {
							System.out.print("게시글을 삭제 하시겠습니까?(O,X):");
							String choice = scan.nextLine();
							if (choice.equals("O") || choice.equals("o") || choice.equals("0")) {
								saveDeleteBoard(getlist.getSeq());
								deleteBoard(getlist.getSeq());
								// 삭제중인 게시글이 자신이 신고한 목록중 있을경우 데이터 절약을 위해 신고기록 삭제
								for (int i = 0; i < reportList.size(); i++) {
									if (reportList.get(i).getSeq().equals(getlist.getSeq())) {
										reportList.remove(i);
										mainPage.setReportList(reportList);
										if (boardList.size() % 10 == 0 && i != 0) {
											i--;
										}
										loop = false;
										break;
									}
								}

								System.out.println("게시글이 삭제되었습니다");
								loop = false;
								break;
							} else {
								System.out.println("게시글 삭제를 취소합니다");
								pause();
							}
						} else {
							System.out.println("게시글을 삭제할 권한이 없습니다");
							pause();
						}

					} else if (num.equals("B") || num.equals("b")) {
						System.out.println("구매를 진행합니다.");
						pause();
						if (getlist.getTrade_status().equals("판매완료")) {
							System.out.println("이미 판매가 완료된 상품입니다.");
						} else {
							buyItem(getlist);
						}
						pause();
					} else if (num.equals("P") || num.equals("p")) {
						// 신고하기 구현

						if (reportBoard(getlist.getSeq()) == 1) {

							loop = false;
							break;
						}

					} else if (num.equals("S") || num.equals("s")) {
						// 장바구니

						if (shoppingBasketList.size() > 0) {
							for (int i = 0; i < shoppingBasketList.size(); i++) {
								if (shoppingBasketList.get(i).getSeq().equals(getlist.getSeq())
										&& shoppingBasketList.get(i).getWriterNickname().equals(getlist.getNickname())
										&& shoppingBasketList.get(i).getMyId().equals(myInfo.getId())) {
									System.out.println("이미 담겨져있는 게시글입니다.");
									pause();
									break;
								} else {
									ShoppingBasket basket = new ShoppingBasket();
									basket.setSeq(getlist.getSeq());
									basket.setWriterNickname(getlist.getNickname());
									basket.setMyId(myInfo.getId());
									shoppingBasketList.add(basket);
									System.out.println("장바구니에 담겨졌습니다.");
									pause();
									break;
								}
							}
						} else {
							ShoppingBasket basket = new ShoppingBasket();
							basket.setSeq(getlist.getSeq());
							basket.setWriterNickname(getlist.getNickname());
							basket.setMyId(myInfo.getId());
							shoppingBasketList.add(basket);
							System.out.println("장바구니에 담겨졌습니다.");
							pause();
						}

						mainPage.setShoppingBasketList(shoppingBasketList);
					} else {
						// 뒤로가기
						loop = false;
					}
				}
			}
		}

		pause();
	}

	/***
	 * 
	 * @param boardNum 게시글 번호를 받는다. 게시글을 수정하기 위한 메소드. 자신/관리자만 수정가능
	 */
	public void modifyBoard(String boardNum) {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			for (int i = 0; i < boardList.size(); i++) {
				if (boardList.get(i).getSeq().equals(boardNum)) {
					System.out.println("===========================================");
					System.out.println("                게시글 수정");
					System.out.println("===========================================");
					System.out.printf("- 닉네임 			:%s\n", boardList.get(i).getNickname());
					System.out.printf("-1. 제목 			: %s\n", boardList.get(i).getTitle());
					System.out.printf("-2. 물품명      	:%s\n", boardList.get(i).getItem());
					System.out.printf("-3. 가격(원)    	:%,d원\n", boardList.get(i).getPrice());
					System.out.printf("-4. 사용기간(개월)	: %d개월\n", boardList.get(i).getUse_date());
					System.out.printf("-5. 희망거래지역 	:%s\n", boardList.get(i).getArea());
					System.out.printf("-6. 내용:\n%s", boardList.get(i).getContent());
					System.out.println("===========================================");
					System.out.println("                 0.뒤로가기");
					System.out.println("===========================================");
					System.out.print("번호입력: ");
					String num = scan.nextLine();

					if (num.equals("1")) {
						System.out.print("제목: ");
						String title = scan.nextLine();
						while (title.contains("/") || title.length() >= 15) {
							System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
							System.out.print("제목: ");
							title = scan.nextLine();
						}
						boardList.get(i).setTitle(title);
						System.out.println("제목이 변경되었습니다.");

					} else if (num.equals("2")) {

						System.out.print("물품명: ");
						String item = scan.nextLine();
						while (item.contains("/") || item.length() >= 15) {
							System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
							System.out.print("제목: ");
							item = scan.nextLine();
						}
						boardList.get(i).setItem(item);
						System.out.println("물품명이 변경되었습니다.");

					} else if (num.equals("3")) {
						System.out.print("가격: ");
						String strPrice = scan.nextLine();
						while (new WriteBoard().checkPrice(strPrice) > 0) {
							System.out.println("올바르지 않은 가격입니다.");
							System.out.print("가격: ");
							strPrice = scan.nextLine();
						}
						int price = Integer.parseInt(strPrice);
						boardList.get(i).setPrice(price);
						System.out.println("가격이 변경되었습니다.");

					} else if (num.equals("4")) {

						System.out.print("사용기간(단위:개월)숫자입력): ");
						String strUse_date = scan.nextLine();
						while (new WriteBoard().checkUse_Date(strUse_date) > 0) {
							System.out.println("올바르지 않은 가격입니다.");
							System.out.print("사용기간(단위:개월)숫자입력): ");
							strUse_date = scan.nextLine();
						}
						int use_date = Integer.parseInt(strUse_date);
						boardList.get(i).setUse_date(use_date);
						System.out.println("사용기간이 변경되었습니다.");

					} else if (num.equals("5")) {
						System.out.print("거래지역: ");
						String area = scan.nextLine();
						while (area.contains("/") || area.length() >= 15) {
							System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
							System.out.print("거래지역: ");
							area = scan.nextLine();
						}
						boardList.get(i).setArea(area);
						System.out.println("거래지역이 변경되었습니다.");

					} else if (num.equals("6")) {
						System.out.print("내용(종료:exit): ");
						String content = "";
						while (true) {
							String temp = scan.nextLine();
							if (temp.equals("exit")) {
								break;
							}
							content += temp + "\r\n";
						}
						boardList.get(i).setContent(content);
						System.out.println("내용이 변경되었습니다.");

					} else {

						System.out.println("게시글 수정을 종료합니다.");
						loop = false;
						break;

					}

				}
			}
		}

	}

	/***
	 * 
	 * @param num 게시글 번호를 받는 매개변수
	 * @return 메소드를 끝내기위한 리턴 게시글을 신고하기위한 메소드
	 */
	public int reportBoard(String num) {

		boolean dupCheck = false;
		System.out.print("게시글을 신고 하시겠습니까?(O,X):");
		String choice = scan.nextLine();
		if (choice.equals("O") || choice.equals("o") || choice.equals("0")) {
			for (int i = 0; i < boardList.size(); i++) {
				if (boardList.get(i).getSeq().equals(num)) {
					for (int j = 0; j < reportList.size(); j++) {
						if (reportList.get(j).getSeq().equals(boardList.get(i).getSeq())
								&& reportList.get(j).getId().equals(myInfo.getId())) {
							dupCheck = true;
							break;
						}
					}
					if (dupCheck) {
						System.out.println("이미 신고한 게시글 입니다");
						pause();
						break;
					}
					ReportRecord report = new ReportRecord();
					report.setSeq(boardList.get(i).getSeq());
					report.setId(myInfo.getId());
					reportList.add(report);
					mainPage.setReportList(reportList);
					boardList.get(i).setReport(boardList.get(i).getReport() + 1);

					if (boardList.get(i).getReport() >= 5) {
						System.out.println("일정량의 신고수가 누적되어 게시글을 삭제합니다.");
						saveDeleteBoard(boardList.get(i).getSeq());
						deleteBoard(num);
						mainPage.setBoardList(boardList);
						// 게시글이 삭제되면 내가 신고했던 기록도 없애기 // 데이터 낭비 줄이기 위해서
						for (int j = 0; j < reportList.size(); j++) {
							if (reportList.get(j).getSeq().equals(num)) {
								reportList.remove(j);
								mainPage.setReportList(reportList);
								break;
							}
						}
						return 1;
					} else {
						System.out.println("게시글 신고를 완료했습니다.");
						mainPage.setBoardList(boardList);
					}
				}
			}
		} else {
			System.out.println("게시글 신고를 취소합니다.");
		}
		pause();
		return 0;
	}

	/***
	 * 
	 * @param num 게시글 번호를 받는 매개변수 게시글을 삭제하는 메소드
	 * 
	 */
	public void deleteBoard(String num) {
		int i = 0;
		for (i = 0; i < boardList.size(); i++) {
			if (boardList.get(i).getSeq().equals(num)) {
				boardList.remove(i);
				mainPage.setBoardList(boardList);
				break;
			}
		}

		// 내 게시글을 삭제했으니 게시글카운트도 1줄이기
		for (int j = 0; j < userList.size(); j++) {
			if (userList.get(j).getNickname().equals(boardList.get(i).getNickname())) {
				userList.get(j).setCount(userList.get(j).getCount() - 1);
				myInfo.setCount(myInfo.getCount() - 1);
				mainPage.setUserList(userList);
				mainPage.setMyInfo(myInfo);
			}
		}
	}

	/***
	 * 
	 * @param board 게시글 정보를 받기위한 매개변수 해당 게시글의 물품을 사기위한 메소드
	 */
	public void buyItem(Board board) {

		StartPage.loadWelcome();
		System.out.println("\n\n\n");
		System.out.println("==========================================================");
		System.out.println("                           결제");
		System.out.println("==========================================================");
		System.out.printf("                                        %s(잔액 : %,d)\n", myInfo.getNickname(), myInfo.getMoney());
		System.out.printf("물품명 : %s\n", board.getItem());
		System.out.printf("가격 : %,d\n", board.getPrice());
		System.out.printf("판매자닉네임 : %s\n", board.getNickname());
		System.out.printf("거래지역 : %s\n", board.getArea());
		System.out.println("==========================================================");
		System.out.println("               1. 충전하기  2.결제하기  3. 취소하기");
		System.out.println("==========================================================");
		System.out.print("번호입력: ");
		String selNum = scan.nextLine();
		if (selNum.equals("1")) {
			// 충전페이지 이동!!
			MyPage myPage = new MyPage();
			myPage.setUserList(userList);
			myPage.setMyInfo(myInfo);
			myPage.deposit();
			this.userList = myPage.getUserList();
			this.myInfo = myPage.getMyInfo();
		} else if (selNum.equals("2")) {
			// 내 보유금액 - 물품 가격 해서 검사하기
			if (myInfo.getMoney() >= board.getPrice()) {
				System.out.print("결제를 진행 하시겠습니까?(O,X):");
				String choice = scan.nextLine();
				if (choice.equals("O") || choice.equals("o") || choice.equals("0")) {
					myInfo.setMoney(myInfo.getMoney() - board.getPrice());
					board.setTrade_status("판매완료");
					System.out.println("결제가 완료되었습니다");
					saveBoughtRecord(board);
					userInfo();
					mainPage.setUserList(userList);// 유저들 정보 업데이트
					mainPage.setBoardList(boardList);
					mainPage.setMyInfo(myInfo);
				} else {
					System.out.println("결제가 취소됩니다");
				}
			} else {
				System.out.println("결제금액이 부족합니다. 충전이 필요합니다");
			}
		}

	}

	// 결제후 깎인 값 정보 넘기기
	/***
	 * ArrayList에 담긴 내 정보를 갱신하기 위한 메소드
	 * 
	 */
	public void userInfo() {
		for (int i = 0; i < userList.size(); i++) {
			if (myInfo.getId().equals(userList.get(i).getId())) {
				userList.set(i, myInfo);
				break;
			}
		}
	}

	/***
	 * 출력을 멈추기 위한 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();
	}

	// 삭제된 게시물 저장하는 메소드!!
	/***
	 * 
	 * @param num 게시글의 번호를 받는 매개변수 게시글의 삭제를 통해 메모장에 저장하는 메소드
	 */
	public void saveDeleteBoard(String num) {
		for (int i = 0; i < boardList.size(); i++) {
			if (boardList.get(i).getSeq().equals(num)) {
				try {

					BufferedWriter writer = new BufferedWriter(new FileWriter(DELETEBOARDDATA, true));
					writer.write(String.format("%s/%s/%s/%d/%s/%d/%tF/%s/%s/%s/%d/%d\n%s==================\n",
							boardList.get(i).getSeq(), boardList.get(i).getTitle(), boardList.get(i).getNickname(),
							boardList.get(i).getPrice(), boardList.get(i).getArea(), boardList.get(i).getUse_date(),
							boardList.get(i).getCalendar(), boardList.get(i).getTrade_status(),
							boardList.get(i).getCategory(), boardList.get(i).getItem(), boardList.get(i).getReport(),
							boardList.get(i).getHit(), boardList.get(i).getContent()));
					writer.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	/***
	 * 
	 * @param board 구매기록을 저장하기 위해 필요한 게시글정보를 받아오는 매개변수 구매기록을 저장하기 위한 메소드
	 */
	public void saveBoughtRecord(Board board) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(BUYITEMDATA, true));

			writer.write(String.format("%s/%d/%s/%s/%tF\n", board.getItem(), board.getPrice(), myInfo.getId(),
					board.getArea(), Calendar.getInstance()));
			// 물품명/가격/내아이디/구매날짜
			writer.close();

		} catch (Exception e) {

		}

	}

	/***
	 * @param boardList 보드정보를 받는 매개변수 다른 클래스로부터 해당 메소드가 있는 클래스에 보드정보를 받는 메소드
	 */
	public static void setBoardList(ArrayList<Board> boardList) {
		BoardList.boardList = boardList;
	}

	/***
	 * 
	 * @param myInfo 내정보를 받는 매개변수 다른 클래스로부터 해당 메소드가 있는 클래스에 내정보를 받는 메소드
	 */
	public void setMyInfo(User myInfo) {
		this.myInfo = myInfo;
	}

	/***
	 * 
	 * @param userList 유저들 정보를 받는 매개변수 다른 클래스로부터 해당 메소드가 있는 클래스에 유저들 정보를 받는 메소드
	 */
	public static void setUserList(ArrayList<User> userList) {
		BoardList.userList = userList;
	}

	/***
	 * 
	 * @param shoppingBasketList 장바구니정보를 받는 매개변수 다른 클래스로부터 해당 메소드가 있는 클래스에 장바구니정보를
	 *                           받는 메소드
	 */
	public static void setShoppingBasketList(ArrayList<ShoppingBasket> shoppingBasketList) {
		BoardList.shoppingBasketList = shoppingBasketList;
	}

	/***
	 * 
	 * @param reportList 게시글신고정보를 받는 매개변수 다른 클래스로부터 해당 메소드가 있는 클래스에 게시글신고정보를 받는 메소드
	 */
	public static void setReportList(ArrayList<ReportRecord> reportList) {
		BoardList.reportList = reportList;
	}

	/***
	 * 다른클래스로 게시글정보를 보내기 위한 메소드
	 * 
	 * @return 게시글정보
	 */
	public static ArrayList<Board> getBoardList() {
		return boardList;
	}

	/***
	 * 다른클래스로 유저들 정보를 보내기 위한 메소드
	 * 
	 * @return 유저들 정보
	 */
	public static ArrayList<User> getUserList() {
		return userList;
	}

	/***
	 * 다른클래스로 장바구니정보를 보내기 위한 메소드
	 * 
	 * @return 장바구니정보
	 */
	public static ArrayList<ShoppingBasket> getShoppingBasketList() {
		return shoppingBasketList;
	}

	/***
	 * 다른클래스로 게시글신고정보를 보내기 위한 메소드
	 * 
	 * @return 게시글신고정보
	 */
	public static ArrayList<ReportRecord> getReportList() {
		return reportList;
	}

	/***
	 * 다른클래스로 내정보를 보내기 위한 메소드
	 * 
	 * @return 내정보
	 */
	public static User getMyInfo() {
		return myInfo;
	}

}
