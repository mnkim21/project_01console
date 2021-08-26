package com.test.market.mypage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.market.MainPage;
import com.test.market.StartPage;
import com.test.market.board.Board;
import com.test.market.board.BoardList;
import com.test.market.board.ReportRecord;
import com.test.market.clientcenter.ClientCenter;
import com.test.market.member.SignIn;
import com.test.market.member.SignUp;
import com.test.market.member.User;

/***
 * 회원정보수정,장바구니,구매내역,충전/출금하기,충전/출금내역,회원탈퇴를 하기위한 클래스
 * @author 6조
 *
 */
public class MyPage {

	private static ArrayList<User> userList;
	private static ArrayList<Board> boardList;
	private static ArrayList<ReportRecord> reportList;
	private static ArrayList<ShoppingBasket> shoppingBasketList;
	private static ArrayList<Board> basketList;
	private static User myInfo;
	private static Scanner scan;
	private static MainPage mainPage;
	private final static String DELETEUSER;
	private static BoardList boardListPage;
	private static ArrayList<BuyItemRecord> boughtItemList;
	private static ArrayList<ChargeMoney> chargeList;
	private static ArrayList<Withdraw> withdrawList;
	private static ArrayList<ClientCenter> clientCenterList;
	private final static String BUYITEMDATA;
	private final static String CHARGEDATA;
	private final static String WITHDRAWDATA;

	static {
		scan = new Scanner(System.in);
		mainPage = new MainPage();
		DELETEUSER = "folder\\deleteuser.txt";
		BUYITEMDATA = "folder\\buyrecord.txt";
		reportList = new ArrayList<ReportRecord>();
		shoppingBasketList = new ArrayList<ShoppingBasket>();
		basketList = new ArrayList<Board>();
		boardListPage = new BoardList();
		boughtItemList = new ArrayList<BuyItemRecord>();
		CHARGEDATA = "folder\\chargerecord.txt";
		chargeList = new ArrayList<ChargeMoney>();
		WITHDRAWDATA = "folder\\withdrawrecord.txt";
		withdrawList = new ArrayList<Withdraw>();
		clientCenterList = new ArrayList<ClientCenter>();
		myInfo = new User();
	}

	/***
	 * 마이페이지의 목록을 보여주는 메소드 (회원정보수정,장바구니,구매내역,충전/출금하기,충전/출금내역,회원탈퇴)
	 * @return 탈퇴시 1 리턴 그 외 0
	 */
	public int showMyPage() {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			System.out.println("=====================================================================");
			System.out.printf("[닉네임:%s] [이름:%s] [전화번호:%s] [성별:%s] [보험가입:%s] [잔액:%d]\n", myInfo.getNickname(),
					myInfo.getName(), myInfo.getTel(), myInfo.getGender(), myInfo.getInsurance(), myInfo.getMoney());
			System.out.println("=====================================================================");
			System.out.println();
			System.out.println("1. 회원정보수정\n");
			System.out.println("2. 장바구니\n");
			System.out.println("3. 구매내역\n");
			System.out.println("4. 충천/출금하기\n");
			System.out.println("5. 충전/출금내역\n");
			System.out.println("6. 회원탈퇴\n");
			System.out.println("=====================================================================");
			System.out.println("                             0. 뒤로가기");
			System.out.println("=====================================================================");
			System.out.print("입력: ");
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				modify();
			} else if (sel.equals("2")) {
				showShoppingBasketList();

			} else if (sel.equals("3")) {
				loadBoughtRecord();
				showBoughtList();

			} else if (sel.equals("4")) {

				deposit_Withdraw();

			} else if (sel.equals("5")) {
				loadChargeRecord();
				loadWithdrawRecord();
				showDepoist_WithdrawRecord();

			} else if (sel.equals("6")) {
				deleteUser();
				mainPage.setMyInfo(myInfo);
				mainPage.setShoppingBasketList(shoppingBasketList);
				mainPage.setReportList(reportList);
				mainPage.setBoardList(boardList);
				mainPage.setUserList(userList);
				mainPage.setClientCenterList(clientCenterList);
				loop = false;
				return 1;

			} else {
				mainPage.setMyInfo(myInfo);
				mainPage.setShoppingBasketList(shoppingBasketList);
				mainPage.setReportList(reportList);
				mainPage.setBoardList(boardList);
				mainPage.setUserList(userList);
				mainPage.setClientCenterList(clientCenterList);
				loop = false;
			}
		}
		return 0;

	}

	/***
	 * 충전/출금내역을 선택하는 메소드
	 */
	public void showDepoist_WithdrawRecord() {

		boolean loop = true;

		while (loop) {

			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			
			System.out.println("=========================================");
			System.out.println("                충전/출금내역 확인");
			System.out.println("=========================================");
			System.out.println();
			System.out.println("1. 충전내역\n");
			System.out.println("2. 출금내역\n");
			System.out.println("0. 뒤로가기\n");
			System.out.println("=========================================");
			System.out.println("              번호를 입력해주세요");
			System.out.println("=========================================");
			System.out.print("번호 입력: ");
			String num = scan.nextLine();

			if (num.equals("1")) {
				showDepositRecord();
			} else if (num.equals("2")) {
				showWithdrawRecord();
			} else {
				loop = false;
			}

		}

	}

	/***
	 * 충전내역 출력하는 메소드
	 */
	public void showDepositRecord() {

		chargeList.sort(new Comparator<ChargeMoney>() {

			@Override
			public int compare(ChargeMoney o1, ChargeMoney o2) {
				// TODO Auto-generated method stub
				return o2.getChargeDay().compareTo(o1.getChargeDay());
			}
		});

		int end = chargeList.size();
		int last = 0;

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				System.out.println("==========================================================");
				System.out.println("                      [충전내역 보기]");
				System.out.println("==========================================================");
				System.out.println("[충전액]\t\t[잔액]\t\t\t[충전날짜]");
				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= chargeList.size()) {
						break;
					} else {
						if(chargeList.get(j).getMyId().equals(myInfo.getId())) {
							System.out.printf("%,d원\t\t%,d원\t\t%tF\n", chargeList.get(j).getMoney(),chargeList.get(j).getAfterMoney(),chargeList.get(j).getChargeDay());
						}
					}
				}
				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1, end / 10 + last==0?1:end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("             P.이전 페이지 N.다음 페이지  C. 뒤로가기");
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

				} else {
					// 뒤로가기
					loop = false;
					break;
				}
			}

		}

	}
	
	/***
	 * 출금내역 출력하는 메소드
	 */
	public void showWithdrawRecord() {

		withdrawList.sort(new Comparator<Withdraw>() {

			@Override
			public int compare(Withdraw o1, Withdraw o2) {
				// TODO Auto-generated method stub
				return o2.getWithdrawDay().compareTo(o1.getWithdrawDay());
			}
		});

		int end = withdrawList.size();
		int last = 0;

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				System.out.println("==========================================================");
				System.out.println("                      [출금내역 보기]");
				System.out.println("==========================================================");
				System.out.println("[출금액]\t\t[잔액]\t\t\t[출금날짜]");
				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= withdrawList.size()) {
						break;
					} else {
						if(withdrawList.get(j).getMyId().equals(myInfo.getId())) {
							System.out.printf("%,d원\t\t%,d원\t\t%tF\n", withdrawList.get(j).getMoney(),withdrawList.get(j).getAfterMoney(),withdrawList.get(j).getWithdrawDay());
						}
					}
				}
				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1, end / 10 + last==0?1:end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("             P.이전 페이지 N.다음 페이지  C. 뒤로가기");
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

				} else {
					// 뒤로가기
					loop = false;
					break;
				}
			}

		}
	}

	/***
	 * 충전/출금하기를 선택하는 메소드
	 */
	public void deposit_Withdraw() {

		boolean loop = true;

		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			System.out.println("=========================================");
			System.out.println("                충전/출금하기");
			System.out.println("=========================================");
			System.out.println();
			System.out.println("1. 충전하기\n");
			System.out.println("2. 출금하기\n");
			System.out.println("0. 뒤로가기\n");
			System.out.println("=========================================");
			System.out.println("              번호를 입력해주세요");
			System.out.println("=========================================");
			System.out.print("번호 입력: ");
			String num = scan.nextLine();

			if (num.equals("1")) {

				deposit();

			} else if (num.equals("2")) {
				withdraw();

			} else {
				loop = false;
			}

		}

	}

	/***
	 * 충전하기 메소드
	 */
	public void deposit() {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			System.out.println("=============================================");
			System.out.println("       [충전하기] 쉽게 충전하고 빠르게 사용하자!");
			System.out.println("        1,000원 이상부터 충전이 가능합니다.");
			System.out.println("     충전을 취소하고 싶을 시에 exit를 입력해주세요.");
			System.out.println("=============================================");
			System.out.printf("                                 (잔액:%,d원)\n",myInfo.getMoney());
			System.out.println();
			System.out.print("비밀번호 입력하세요: ");
			String pw = scan.nextLine();
			if (pw.equals("exit")) {
				System.out.println("충전이 취소됩니다.");
				break;
			}
			if (!myInfo.getPw().equals(pw)) {
				System.out.println("비밀번호가 올바르지 않습니다");
				pause();
				continue;
			}
			System.out.print("계좌번호를 입력하세요: ");
			String account = scan.nextLine();
			if (account.equals("exit")) {
				System.out.println("충전이 취소됩니다.");
				break;
			}
			if (!myInfo.getAccount().equals(account)) {
				System.out.println("계좌번호가 올바르지 않습니다.");
				pause();
				continue;
			}

			System.out.println("충전금액 입력란에 0을 입력시 충전이 취소됩니다.");
			System.out.print("충전금액(원) 입력하세요(숫자만): ");

			String strMoney = scan.nextLine();

			boolean moneyLoop = true;
			while (moneyLoop) {
				if (validInt(strMoney) > 0) {
					System.out.println("올바르지 않은 단어가 포함되어 있습니다.");
					System.out.print("충전금액(원) 입력하세요(숫자만): ");
					strMoney = scan.nextLine();
				} else {
					moneyLoop = false;
				}
			}
			if (strMoney.equals("0")) {
				System.out.println("충전이 취소됩니다.");
				break;
			}
			int money = Integer.parseInt(strMoney);

			if (money <= 1000) {
				System.out.println("1,000원 이상부터 충전이 가능합니다.");
				continue;
			}

			System.out.println("=============================================");
			System.out.printf("                [%,d원을 충전할까요?]\n", money);
			System.out.println("             S. 충전하기  C. 충전취소");
			System.out.println("=============================================");
			System.out.print("입력: ");
			String sel = scan.nextLine();
			if (sel.equals("S") || sel.equals("s")) {
				myInfo.setMoney(myInfo.getMoney() + money);
				setMyInfoMoney(myInfo);
				mainPage.setUserList(userList);
				mainPage.setMyInfo(myInfo);
				System.out.printf("%,d원이 충전되었습니다 현재 잔액은 %,d원 입니다\n", money, myInfo.getMoney());
				saveChargeRecord(money);
				loop = false;
			} else {
				System.out.println("충전이 취소됩니다.");
				loop = false;
			}
		}

		pause();

	}

	/***
	 * 출금하기 메소드
	 */
	public void withdraw() {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			System.out.println("=============================================");
			System.out.println("       [출금하기] 쉽게 출금하고 빠르게 출금하자!");
			System.out.println("     출금을 취소하고 싶을 시에 exit를 입력해주세요.");
			System.out.println("=============================================");
			System.out.printf("                                 (잔액:%,d원)\n",myInfo.getMoney());
			System.out.println();
			System.out.print("비밀번호 입력하세요: ");
			String pw = scan.nextLine();
			if (pw.equals("exit")) {
				System.out.println("출금이 취소됩니다.");
				break;
			}
			if (!myInfo.getPw().equals(pw)) {
				System.out.println("비밀번호가 올바르지 않습니다");
				pause();
				continue;
			}
			System.out.print("계좌번호를 입력하세요: ");
			String account = scan.nextLine();
			if (account.equals("exit")) {
				System.out.println("출금이 취소됩니다.");
				break;
			}
			if (!myInfo.getAccount().equals(account)) {
				System.out.println("계좌번호가 올바르지 않습니다.");
				pause();
				continue;
			}

			System.out.println("출금금액 입력란에 0을 입력시 충전이 취소됩니다.");
			System.out.print("출금금액(원) 입력하세요(숫자만): ");
			
			String strMoney = scan.nextLine();

			boolean moneyLoop = true;
			while (moneyLoop) {
				if (validInt(strMoney) > 0) {
					System.out.println("올바르지 않은 단어가 포함되어 있습니다.");
					System.out.print("출금금액(원) 입력하세요(숫자만): ");
					strMoney = scan.nextLine();
				} else {
					moneyLoop = false;
				}
			}
			if (strMoney.equals("0")) {
				System.out.println("출금이 취소됩니다.");
				break;
			}
			int money = Integer.parseInt(strMoney);
			
			if(myInfo.getMoney()-money<0) {
				
				System.out.printf("출금 가능 금액은 %,d원 입니다\n",myInfo.getMoney());
				System.out.println("출금이 취소됩니다.");
				
				break;
				
			}

			System.out.println("=============================================");
			System.out.printf("                [%,d원을 출금할까요?]\n", money);
			System.out.println("             S. 출금하기  C. 출금취소");
			System.out.println("=============================================");
			System.out.print("입력: ");
			String sel = scan.nextLine();
			if (sel.equals("S") || sel.equals("s")) {
				myInfo.setMoney(myInfo.getMoney() - money);
				setMyInfoMoney(myInfo);
				mainPage.setUserList(userList);
				mainPage.setMyInfo(myInfo);
				System.out.printf("%,d원이 출금되었습니다 현재 잔액은 %,d원 입니다\n", money, myInfo.getMoney());
				saveWithdrawRecord(money);
				loop = false;
			} else {
				System.out.println("출금이 취소됩니다.");
				loop = false;
			}
		}

		pause();

	}

	/***
	 * 회원의 충전/출금후 유저리스트의 내정보를 갱신하는 메소드
	 * @param myInfo 내정보
	 */
	public void setMyInfoMoney(User myInfo) {

		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId().equals(myInfo.getId())) {
				userList.set(i, myInfo);
				break;
			}
		}

	}

	/***
	 * 구매기록을 보여주는 메소드
	 */
	public void showBoughtList() {

		// 구매날짜 내림차순
		boughtItemList.sort(new Comparator<BuyItemRecord>() {

			@Override
			public int compare(BuyItemRecord o1, BuyItemRecord o2) {

				return o2.getBoughtDay().compareTo(o1.getBoughtDay());
			}
		});

		int end = boughtItemList.size();
		int last = 0;

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				System.out.println("==========================================================");
				System.out.println("                      [구매내역 보기]");
				System.out.println("==========================================================");
				System.out.println("[물품명]\t[가격]\t[구매지역]\t\t[구매날짜]");
				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= end) {
						break;
					} else {
						if (boughtItemList.get(j).getMyId().equals(myInfo.getId())) {
							System.out.printf("%s\t%d\t%s\t\t%tF\n", boughtItemList.get(j).getBuyItem(),
									boughtItemList.get(j).getPrice(), boughtItemList.get(j).getArea(),
									boughtItemList.get(j).getBoughtDay());
						}
					}
				}
				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1, end / 10 + last==0?1:end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("             P.이전 페이지 N.다음 페이지  C. 뒤로가기");
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

				} else {
					// 뒤로가기
					loop = false;
					break;
				}
			}

		}
	}

	/***
	 * 장바구니 목록을 보여주는 메소드
	 * @return 게시글작성은 1 그외 0
	 */
	public int showShoppingBasketList() {

		for (int i = 0; i < boardList.size(); i++) {
			for (int j = 0; j < shoppingBasketList.size(); j++) {

				if (boardList.get(i).getSeq().equals(shoppingBasketList.get(j).getSeq())
						&& shoppingBasketList.get(j).getMyId().equals(myInfo.getId())) {
					basketList.add(boardList.get(i));
				}
			}
		}

		if (basketList.size() == 0) {
			System.out.println("장바구니에 담긴 게시글이 없습니다.");
			pause();
			return 0;
		}

		basketList.sort(new Comparator<Board>() {

			@Override
			public int compare(Board o1, Board o2) {
				return o2.getCalendar().compareTo(o1.getCalendar());
			}
		});
		int end = basketList.size();
		int last = 0;

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				System.out.println("==========================================================");
				System.out.println("                      [게시글 목록보기]");
				System.out.println("==========================================================");
				System.out.println("[번호]\t[제목]\t\t[상태]\t[가격]\t[사용기간]\t[희망거래지역]\t[닉네임]\t");
				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= end) {
						break;
					} else {
						System.out.printf("%s\t%s\t%s\t%s\t%s개월\t%s\t\t%s\t\n", basketList.get(j).getSeq(),
								basketList.get(j).getTitle(),basketList.get(j).getTrade_status(),basketList.get(j).getPrice(),
								basketList.get(j).getUse_date(), basketList.get(j).getArea(),
								basketList.get(j).getNickname());
					}
				}
				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1, end / 10 + last==0?1:end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("           P.이전 페이지 N.다음 페이지  R. 장바구니 삭제 ");
				System.out.println("           D.게시글 보기  S.페이지 검색   C. 뒤로가기 ");
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
				} else if (sel.equals("R") || sel.equals("r")) {
					int check = 0;
					System.out.print("번호 입력: ");
					String num = scan.nextLine();
					for (int j = 0; j < shoppingBasketList.size(); j++) {
						if (shoppingBasketList.get(i).getSeq().equals(num)
								&& shoppingBasketList.get(i).getMyId().equals(myInfo.getId())) {
							System.out.println("해당 게시글이 장바구니에서 삭제됩니다.");
							shoppingBasketList.remove(i);
							mainPage.setShoppingBasketList(shoppingBasketList);
							check = 1;
							break;
						}
					}
					if (check == 0) {
						System.out.println("알맞는 게시글이 없습니다.");
					}
					pause();

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
					hitBoard(boardNum);

					// 게시글 보기
					boardListPage.setMyInfo(myInfo);
					boardListPage.setBoardList(boardList);
					boardListPage.setUserList(userList);
					boardListPage.setReportList(reportList);
					boardListPage.setShoppingBasketList(shoppingBasketList);
					boardListPage.showBoardDetail(boardNum);
					boardList = boardListPage.getBoardList();
					userList = boardListPage.getUserList();
					reportList = boardListPage.getReportList();
					myInfo = boardListPage.getMyInfo();
					shoppingBasketList = boardListPage.getShoppingBasketList();

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

		}
		return 0;// 삭제하고 다르거로 바꿔야함
	}

	/***
	 * 장바구니에 있던 게시글을 읽었을 때 조회수를 증가시키는 메소드
	 * @param board 게시글 번호
	 */
	public void hitBoard(String board) {

		for (int i = 0; i < boardList.size(); i++) {
			if (boardList.get(i).getSeq().equals(board)) {
				boardList.get(i).setHit(boardList.get(i).getHit() + 1);
				mainPage.setBoardList(boardList);
			}
		}
	}

	/***
	 * 회원탈퇴하는 메소드
	 */
	public void deleteUser() {

		System.out.println("비밀번호와 휴대폰인증을 해주세요");

		System.out.print("비밀번호: ");
		String checkPw = scan.nextLine();
		System.out.print("휴대폰번호: ");
		String checkTel = scan.nextLine();

		if (myInfo.getPw().equals(checkPw) && myInfo.getTel().equals(checkTel)) {

			System.out.println("남은 잔액과 회원님께서 작성하신 게시글은 자동 삭제됩니다.");
			System.out.print("회원탈퇴를 하시겠습니까?(O,X):");
			String choice = scan.nextLine();

			if (choice.equals("O") || choice.equals("o") || choice.equals("0")) {

				for (int i = 0; i < boardList.size(); i++) {
					if (boardList.get(i).getNickname().equals(myInfo.getNickname())) {
						boardList.remove(i);
					}
				}
				
				for(int i=0;i<clientCenterList.size();i++) {
					if(clientCenterList.get(i).getMyId().equals(myInfo.getId())) {
						clientCenterList.remove(i);
					}
				}
				
				// 탈퇴한 시간 저장하는 메모장 만들기
				deleteUserRecord();

				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getId().equals(myInfo.getId())) {
						userList.remove(i);
						this.myInfo = new User();
						break;
					}
				}
				
				

				System.out.println("회원이 탈퇴되었습니다.");
				System.out.println("메인페이지로 이동합니다.");
			} else {
				System.out.println("회원탈퇴를 취소합니다.");

			}
		} else {
			System.out.println("정보가 일치하지 않습니다.");
		}
		pause();
	}

	/***
	 * 회원정보를 수정할 수 있는 메소드
	 */
	public void modify() {

		String pwleng = "";
		for (int i = 0; i < myInfo.getPw().length(); i++) {
			pwleng += "*";
		}
		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			
			System.out.println("=====================================");
			System.out.println("            회원정보수정");
			System.out.println("=====================================");
			System.out.printf("1.닉네임	: %s\n", myInfo.getNickname());
			System.out.printf("2.비밀번호	: %s\n", pwleng);
			System.out.printf("3.전화번호	: %s\n", myInfo.getTel());
			System.out.printf("4.보험가입	: %s\n", myInfo.getInsurance());
			System.out.printf("5.계좌번호	: %s\n", myInfo.getAccount());
			System.out.println("=====================================");
			System.out.println("            번호를 입력해주세요.");
			System.out.println("               0.뒤로가기");
			System.out.println("=====================================");
			System.out.print("입력: ");
			String sel = scan.nextLine();
			if (sel.equals("1")) {
				System.out.println("닉네임을 수정합니다.");
				//4. 닉네임 : 한글을 이용하여 4글자 입력가능
				System.out.print("닉네임: ");
				String nickname = scan.nextLine();
				boolean nicknameLoop = true;
				while (nicknameLoop) {
					if (checkNickname(nickname) > 0) {
						System.out.println("닉네임을 다시 입력하세요");
						System.out.print("닉네임: ");
						nickname = scan.nextLine();
					} else {
						nicknameLoop = false;
					}
				}
				

				for (int i = 0; i < shoppingBasketList.size(); i++) {
					if (shoppingBasketList.get(i).getWriterNickname().equals(myInfo.getNickname())) {
						shoppingBasketList.get(i).setWriterNickname(nickname);
					}
				}
				mainPage.setShoppingBasketList(shoppingBasketList);

				for (int i = 0; i < boardList.size(); i++) {
					if (boardList.get(i).getNickname().equals(myInfo.getNickname())) {
						boardList.get(i).setNickname(nickname);
					}
				}
				
				for(int i=0;i<clientCenterList.size();i++) {
					if(clientCenterList.get(i).getNickname().equals(myInfo.getNickname())) {
						clientCenterList.get(i).setNickname(nickname);
					}
				}

				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getNickname().equals(myInfo.getNickname())) {
						userList.get(i).setNickname(nickname);
						myInfo.setNickname(nickname);
						mainPage.setMyInfo(myInfo);
						break;
					}
				}

			} else if (sel.equals("2")) {
				//3. 비밀번호 : 영어 대소문자, 숫자, 특수문자(!,@,#,$)를 조합하여 입력가능
				System.out.println("비밀번호를 수정합니다.");
				System.out.print("비밀번호: ");
				String pw = scan.nextLine();
				boolean pwLoop = true;
				while (pwLoop) {
					if (checkPw(pw) > 0) {
						System.out.println("영문자, 숫자, 특수문자(!,@,#,$)만 입력가능합니다.");
						System.out.print("비밀번호: ");
						pw = scan.nextLine();
					} else {
						pwLoop = false;
					}
				}

				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getId().equals(myInfo.getId())) {
						userList.get(i).setPw(pw);
						myInfo.setPw(pw);
						mainPage.setMyInfo(myInfo);
						break;
					}
				}

			} else if (sel.equals("3")) {

				//6. 휴대폰번호 : 숫자만 입력가능/ 반드시 010으로 시작해야되며 이후에는 숫자8자리 입력가능
				System.out.println("휴대폰번호를 수정합니다.");
				System.out.print("휴대폰번호(숫자만): ");
				String tel = scan.nextLine();
				boolean telLoop = true;
				while (telLoop) {
					if (checkTel(tel) > 0) {
						System.out.println("휴대폰번호를 다시 입력하세요");
						System.out.print("휴대폰번호(숫자만): ");
						tel = scan.nextLine();
					} else {
						telLoop = false;
					}
				}	


				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getTel().equals(myInfo.getTel())) {
						userList.get(i).setTel(tel);
						myInfo.setTel(tel);
						mainPage.setMyInfo(myInfo);
						break;
					}
				}

			} else if (sel.equals("4")) {
				System.out.println("보험가입을 수정합니다.");
				System.out.print("보험가입(Y/N): ");
				String change_insurance = scan.nextLine();
				while (checkInsurance(change_insurance) == 1) {
					System.out.print("보험가입(Y/N): ");
					change_insurance = scan.nextLine();
				}
				
				for(int i=0;i<clientCenterList.size();i++) {
					if(clientCenterList.get(i).getMyId().equals(myInfo.getId())) {
						clientCenterList.get(i).setMyInsurance(change_insurance);
					}
				}

				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getId().equals(myInfo.getId())) {
						userList.get(i).setInsurance(change_insurance);
						myInfo.setInsurance(change_insurance);
						mainPage.setMyInfo(myInfo);
						break;
					}
				}

			} else if (sel.equals("5")) {

				//7. 계좌번호 : 숫자를 이용하여 12자리 입력가능
				System.out.println("계좌번호를 수정합니다.");
				System.out.print("계좌번호(12자리/숫자만): ");
				String account = scan.nextLine();
				boolean accountLoop = true;
				while (accountLoop) {
					if (checkAccount(account) > 0) {
						System.out.println("계좌번호를 다시 입력하세요");
						System.out.print("계좌번호(12자리/숫자만): ");
						account = scan.nextLine();
					} else {
						accountLoop = false;
					}
				}

				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getAccount().equals(myInfo.getAccount())) {
						userList.get(i).setAccount(account);
						myInfo.setAccount(account);
						mainPage.setMyInfo(myInfo);
						break;
					}
				}

			} else {
				System.out.println("정보수정을 종료합니다.");
				loop = false;
			}
		}

		mainPage.setUserList(userList);
		mainPage.setBoardList(boardList);
		pause();

	}

	/***
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

	/***\
	 * 
	 * @param nickname 닉네임 유효성검사
	 * @return 유효성 검사 실패시 1이상 리턴
	 */
	public int validNickname(String nickname) {

		int count = 0;
		for (int i = 0; i < nickname.length(); i++) {

			char c = nickname.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '가' && c <= '힣')) {
				count++;
			}
		}

		return count;
	}

	/***
	 * 
	 * @param money 전화번호 휴효성검사
	 * @return 유효성 검사 실패시 정수 리턴
	 */
	public int validInt(String money) {

		int count = 0;
		for (int i = 0; i < money.length(); i++) {

			char c = money.charAt(i);
			if (!(c >= '0' && c <= '9')) {
				count++;
			}
		}

		return count;
	}

	
	/***
	 * 
	 * @param account 계좌번호 유효성 및 중복 검사를 위한 매개변수
	 * 계좌번호 유효성 및 중복 검사 메소드
	 * 숫자를 이용하여 12자리 입력가능
	 * @return 유효성검사 실패시 1 리턴
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
	 * @param insurance 보험가입 유효성검사
	 * @return 잘못 입력시 1 리턴
	 */
	public int checkInsurance(String insurance) {

		if (insurance.equals("Y") || insurance.equals("N")) {
			return 0;
		} else {
			System.out.println("잘못 입력 하셨습니다.");
			return 1;
		}

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
	 * 출력을 멈추는 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();

	}

	
	/***
	 * 
	 * @param money 충전금액 받아오는 매개변수
	 * 충전금액을 저장하는 메소드이다
	 */
	public void saveChargeRecord(int money) {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(CHARGEDATA, true));

			writer.write(String.format("%s/%d/%tF/%d\n", myInfo.getId(), money, Calendar.getInstance(),myInfo.getMoney()));

			writer.close();

		} catch (Exception e) {

		}

	}

	/***
	 * 
	 * @param money 얼마를 출금했는가를 저장하기위한 매개변수
	 * 얼마를 출금했는가를 저장하기위한 메소드
	 */
	public void saveWithdrawRecord(int money) {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(WITHDRAWDATA, true));

			writer.write(String.format("%s/%d/%tF/%d\n", myInfo.getId(), money, Calendar.getInstance(),myInfo.getMoney()));

			writer.close();

		} catch (Exception e) {

		}

	}

	/***
	 * 회원탈퇴시 정보를 저장하는 메소드
	 */
	public void deleteUserRecord() {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(DELETEUSER, true));

			String temp = String.format("%s/%s/%s/%tF\n", myInfo.getId(), myInfo.getTel(), myInfo.getAccount(),
					Calendar.getInstance());

			writer.write(temp);
			writer.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/***
	 * 출금내역을 ArrayList에 저장하는 메소드
	 */
	public void loadWithdrawRecord() {
		withdrawList = new ArrayList<Withdraw>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(WITHDRAWDATA));

			String line = "";

			while ((line = reader.readLine()) != null) {

				Withdraw withdraw = new Withdraw();

				String[] temp = line.split("/");
				withdraw.setMyId(temp[0]);
				withdraw.setMoney(Integer.parseInt(temp[1]));

				Calendar c = Calendar.getInstance();
				String[] timeTemp = temp[2].split("-");
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				withdraw.setWithdrawDay(c);
				withdraw.setAfterMoney(Integer.parseInt(temp[3]));
				
				withdrawList.add(withdraw);
			}

			reader.close();

		} catch (Exception e) {

		}

	}

	/***
	 * 충전내역을 ArrayList에 저장하는 메소드
	 */
	public void loadChargeRecord() {
		chargeList = new ArrayList<ChargeMoney>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(CHARGEDATA));

			String line = "";

			while ((line = reader.readLine()) != null) {

				ChargeMoney charge = new ChargeMoney();

				String[] temp = line.split("/");
				charge.setMyId(temp[0]);
				charge.setMoney(Integer.parseInt(temp[1]));

				Calendar c = Calendar.getInstance();
				String[] timeTemp = temp[2].split("-");
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				charge.setChargeDay(c);
				charge.setAfterMoney(Integer.parseInt(temp[3]));
				
				chargeList.add(charge);
			}

			reader.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/***
	 * 구매기록을 ArrayList에 저장하는 메소드
	 */
	public void loadBoughtRecord() {

		boughtItemList = new ArrayList<BuyItemRecord>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(BUYITEMDATA));

			String line = "";

			while ((line = reader.readLine()) != null) {

				BuyItemRecord item = new BuyItemRecord();
				String[] temp = line.split("/");
				item.setBuyItem(temp[0]);
				item.setPrice(Integer.parseInt(temp[1]));
				item.setMyId(temp[2]);
				item.setArea(temp[3]);
				Calendar c = Calendar.getInstance();
				String[] timeTemp = temp[4].split("-");
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				item.setBoughtDay(c);
				
				boughtItemList.add(item);
			}

			reader.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 유저들 정보를 저장하는 메소드
	 * @param userList 유저 정보
	 */
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	
	/***
	 *  다른 클래스로부터 해당 메소드가 있는 클래스에 게시글 정보를 저장하는 메소드
	 * @param boardList 게시글 정보
	 */
	public void setBoardList(ArrayList<Board> boardList) {
		MyPage.boardList = boardList;
	}

	
	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 내 정보를 저장하는 메소드
	 * @param myInfo 내정보
	 */
	public void setMyInfo(User myInfo) {
		this.myInfo = myInfo;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 게시글신고 기록을 저장하는 메소드
	 * @param reportList 게시글신고 정보
	 */
	public static void setReportList(ArrayList<ReportRecord> reportList) {
		MyPage.reportList = reportList;
	}

	
	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 장바구니 정보를 저장하는 메소드
	 * @param shoppingBasketList 장바구니정보
	 */
	public static void setShoppingBasketList(ArrayList<ShoppingBasket> shoppingBasketList) {
		MyPage.shoppingBasketList = shoppingBasketList;
	}

	
	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 고객센터 게시글을 저장하는 메소드
	 * @param clientCenterList 고객센터 게시글정보
	 */
	public static void setClientCenterList(ArrayList<ClientCenter> clientCenterList) {
		MyPage.clientCenterList = clientCenterList;
	}

	
	/***
	 * 다른 클래스에 유저들 정보를 주기위한 메소드
	 * @return 유저들 정보
	 */
	public static ArrayList<User> getUserList() {
		return userList;
	}

	
	/***
	 * 다른 클래스에 내정보를 주기위한 메소드
	 * @return 내정보
	 */
	public static User getMyInfo() {
		return myInfo;
	}

}
