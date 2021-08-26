package com.test.market;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

import com.test.market.admin.Adver;
import com.test.market.admin.AdverList;
import com.test.market.admin.MemberManagement;
import com.test.market.admin.Notice;
import com.test.market.admin.NoticeList;
import com.test.market.board.Board;
import com.test.market.board.BoardList;
import com.test.market.board.ReportRecord;
import com.test.market.board.WriteBoard;
import com.test.market.clientcenter.ClientCenter;
import com.test.market.clientcenter.ClientCenterList;
import com.test.market.event.Event;
import com.test.market.event.EventList;
import com.test.market.event.EventPlayer;
import com.test.market.member.User;
import com.test.market.mypage.MyPage;
import com.test.market.mypage.ShoppingBasket;

/***
 * 물품검색,게시글작성,게시글목록보기,마이페이지,고객센터,이벤트관리,광고관리,회원정보관리,공지사항 등을 실행할 수 있는 클래스이다
 * @author 6조
 *
 */
public class MainPage {

	private static Scanner scan;
	private final static String BOARDDATA;
	private final static String USERDATA;
	private final static String BOARDREPORTDATA;
	private final static String SHOPPINGBASKETDATA;
	private final static String CLIENTCENTERDATA;
	private final static String ADVERDATA;
	private final static String NOTICEDATA;
	private final static String EVENTDATA;
	private final static String EVENTPLAYLIST;
	private static ArrayList<EventPlayer> playerList;
	private static ArrayList<Event> eventList;
	private static ArrayList<Notice> noticeList;
	private static ArrayList<Adver> advertisementList;
	private static ArrayList<Board> boardList;
	private static ArrayList<User> userList;
	private static ArrayList<ReportRecord> reportList;
	private static ArrayList<ShoppingBasket> shoppingBasketList;
	private static ArrayList<ClientCenter> clientCenterList;
	
	private static User myInfo;
	private static StartPage startPage;
	private static Random rnd;

	static {
		scan = new Scanner(System.in);
		BOARDDATA = "folder\\board.txt";
		USERDATA = "folder\\user.txt";
		BOARDREPORTDATA = "folder\\boardreport.txt";
		SHOPPINGBASKETDATA = "folder\\shoppingbasket.txt";
		CLIENTCENTERDATA = "folder\\clientcenterboard.txt";
		ADVERDATA = "folder\\adv.txt";
		NOTICEDATA = "folder\\notice.txt";
		EVENTDATA = "folder\\event.txt";
		EVENTPLAYLIST = "folder\\eventplaylist.txt";
		playerList = new ArrayList<EventPlayer>();
		eventList = new ArrayList<Event>();
		advertisementList = new ArrayList<Adver>();
		startPage = new StartPage();
		shoppingBasketList = new ArrayList<ShoppingBasket>();
		reportList = new ArrayList<ReportRecord>();
		userList = new ArrayList<User>();
		boardList = new ArrayList<Board>();
		clientCenterList = new ArrayList<ClientCenter>();
		noticeList = new ArrayList<Notice>();
		myInfo = new User();
		rnd = new Random();

	}


	/***
	 * 메인 페이지를 실행하는 메소드이다
	 */
	public void playMainPage() {

		if (!(this.myInfo.getId().equals("admin"))) {
			boolean loop = true;

			while (loop) {
				startPage.loadWelcome();
				System.out.println("\n\n\n");

				String mainSelNum = mainListSelectNum();
				if (mainSelNum.equals("1")) {

					// 물품검색
					BoardList list = new BoardList();
					list.setMyInfo(myInfo);
					list.setUserList(userList);
					list.setBoardList(boardList);
					list.setReportList(reportList);
					list.setShoppingBasketList(shoppingBasketList);
					System.out.print("검색어\n");
					if (list.showBoardList(searchItem()) == 1) {
						WriteBoard writeBoard = new WriteBoard(boardList, this.myInfo, userList);
						writeBoard.exec();
					}

				} else if (mainSelNum.equals("2")) {
					// 게시글 작성
					WriteBoard writeBoard = new WriteBoard(boardList, this.myInfo, userList);
					writeBoard.exec();
				} else if (mainSelNum.equals("3")) {
					// 게시글 목록보기
					BoardList list = new BoardList();
					list.setMyInfo(myInfo);
					list.setUserList(userList);
					list.setBoardList(boardList);
					list.setReportList(reportList);
					list.setShoppingBasketList(shoppingBasketList);

					if (list.showBoardList("") == 1) {
						WriteBoard writeBoard = new WriteBoard(boardList, this.myInfo, userList);
						writeBoard.exec();
					}

				} else if (mainSelNum.equals("4")) {
					// 마이페이지
					MyPage myPage = new MyPage();
					myPage.setBoardList(boardList);
					myPage.setUserList(userList);
					myPage.setMyInfo(myInfo);
					myPage.setReportList(reportList);
					myPage.setShoppingBasketList(shoppingBasketList);
					myPage.setClientCenterList(clientCenterList);

					// 여기서 값 받아서 회원탈퇴하면 루프 종료해야함 안그러면 회원탈퇴 해도 이 페이지에 남아있기 때문에!! *********
					if (myPage.showMyPage() == 1) {
						startPage.setBoardList(boardList);
						startPage.setUserList(userList);
						startPage.setMyInfo(myInfo);
						startPage.setReportList(reportList);
						startPage.setShoppingBasketList(shoppingBasketList);
						startPage.setAdvertisementList(advertisementList);
						startPage.setNoticeList(noticeList);
						startPage.setEventList(eventList);
						startPage.setPlayerList(playerList);
						loop = false;
					}
				} else if (mainSelNum.equals("5")) {
					// 고객센터
					ClientCenterList clientsenter = new ClientCenterList(userList, clientCenterList, myInfo);
					clientsenter.showBoardList();

				} else if (mainSelNum.equals("6")) {
					// 공지사항관리 NOTICE
					NoticeList notice = new NoticeList(noticeList,myInfo);
					notice.showNoticeList();

				} else if (mainSelNum.equals("7")) {
					// 이벤트
					EventList event = new EventList(userList, eventList, myInfo,playerList);
					event.showEventList();
					
				} else if (mainSelNum.equals("0")) {
					// 뒤로가기 실행됨
					startPage.setBoardList(boardList);
					startPage.setUserList(userList);
					startPage.setReportList(reportList);
					startPage.setShoppingBasketList(shoppingBasketList);
					startPage.setClientCenterList(clientCenterList);
					startPage.setAdvertisementList(advertisementList);
					startPage.setNoticeList(noticeList);
					startPage.setEventList(eventList);
					startPage.setPlayerList(playerList);
					loop = false;
				} else {
					System.out.println("프로그램을 종료합니다");
					saveBoard();
					saveUser();
					saveBoardReport();
					saveShoppingBasket();
					saveClientCenterBoard();
					saveAdvertisement();
					saveNotice();
					saveEventList();
					savePlayerList();
					System.exit(0);
				}

			}
		} else {// 관리자 페이지 목록
			boolean loop = true;

			while (loop) {
				startPage.loadWelcome();
				System.out.println("\n\n\n");
				String mainSelNum = adminMainListSelectNum();

				if (mainSelNum.equals("1")) {

					// 물품검색
					BoardList list = new BoardList();
					list.setMyInfo(myInfo);
					list.setUserList(userList);
					list.setBoardList(boardList);
					list.setReportList(reportList);
					list.setShoppingBasketList(shoppingBasketList);
					System.out.print("검색어\n");
					WriteBoard writeBoard = new WriteBoard(boardList, this.myInfo, userList);
					writeBoard.exec();

				} else if (mainSelNum.equals("2")) {
					// 게시글 작성
					WriteBoard writeBoard = new WriteBoard(boardList, this.myInfo, userList);
					writeBoard.exec();
				} else if (mainSelNum.equals("3")) {
					// 게시글 목록보기
					BoardList list = new BoardList();
					list.setMyInfo(myInfo);
					list.setUserList(userList);
					list.setBoardList(boardList);
					list.setReportList(reportList);
					list.setShoppingBasketList(shoppingBasketList);
					if (list.showBoardList("") == 1) {
						WriteBoard writeBoard = new WriteBoard(boardList, this.myInfo, userList);
						writeBoard.exec();
					}

				} else if (mainSelNum.equals("4")) {
					// 마이페이지
					MyPage myPage = new MyPage();
					myPage.setBoardList(boardList);
					myPage.setUserList(userList);
					myPage.setMyInfo(myInfo);
					myPage.setReportList(reportList);
					myPage.setShoppingBasketList(shoppingBasketList);
					myPage.setClientCenterList(clientCenterList);

					// 여기서 값 받아서 회원탈퇴하면 루프 종료해야함 안그러면 회원탈퇴 해도 이 페이지에 남아있기 때문에!! *********
					if (myPage.showMyPage() == 1) {
						startPage.setBoardList(boardList);
						startPage.setUserList(userList);
						startPage.setMyInfo(myInfo);
						startPage.setReportList(reportList);
						startPage.setShoppingBasketList(shoppingBasketList);
						startPage.setAdvertisementList(advertisementList);
						startPage.setNoticeList(noticeList);
						startPage.setEventList(eventList);
						startPage.setPlayerList(playerList);
						loop = false;
					}
				} else if (mainSelNum.equals("5")) {
					// 고객센터
					ClientCenterList clientsenter = new ClientCenterList(userList, clientCenterList, myInfo);
					clientsenter.showBoardList();

				} else if (mainSelNum.equals("6")) {
					// 이벤트
					EventList event = new EventList(userList, eventList, myInfo,playerList);
					event.showEventList();
					
				} else if (mainSelNum.equals("7")) {
					// 광고관리
					AdverList adver = new AdverList(advertisementList);
					adver.showAdverList();
				} else if (mainSelNum.equals("8")) {
					// 회원관리
					MemberManagement memberManagement = new MemberManagement(userList);
					memberManagement.showMemberList();
				} else if (mainSelNum.equals("9")) {
					// 공지사항관리 NOTICE
					NoticeList notice = new NoticeList(noticeList,myInfo);
					notice.showNoticeList();

				} else if (mainSelNum.equals("0")) {
					// 뒤로가기 실행됨
					startPage.setBoardList(boardList);
					startPage.setUserList(userList);
					startPage.setReportList(reportList);
					startPage.setShoppingBasketList(shoppingBasketList);
					startPage.setClientCenterList(clientCenterList);
					startPage.setAdvertisementList(advertisementList);
					startPage.setNoticeList(noticeList);
					startPage.setEventList(eventList);
					startPage.setPlayerList(playerList);
					loop = false;
				} else {
					System.out.println("프로그램을 종료합니다");
					saveBoard();
					saveUser();
					saveBoardReport();
					saveShoppingBasket();
					saveClientCenterBoard();
					saveAdvertisement();
					saveNotice();
					saveEventList();
					savePlayerList();
					System.exit(0);
				}
			}

		}

	}

	/***
	 * 일반사용자용 메인 페이지 보여주는 메소드
	 * @return 항목 번호 리턴
	 */
	public String mainListSelectNum() {

		int num = rnd.nextInt(advertisementList.size());
		System.out.println("==================================");
		System.out.println("  이동하고자 하는 항목의 번호를 입력하세요.");
		System.out.println("==================================");
		System.out.println("1. 물품검색\n");
		System.out.println("2. 게시글 작성\n");
		System.out.println("3. 게시글 목록보기\n");
		System.out.println("4. 마이페이지\n");
		System.out.println("5. 고객센터\n");
		System.out.println("6. 공지사항\n");
		System.out.println("7. 이벤트");
		System.out.println("0. 로그아웃\n");
		System.out.println("프로그램 종료를 원하시면 close를 입력해 주세요");
		System.out.println("==================================");
		System.out.println("               [광고]");
		System.out.printf("%s", advertisementList.get(num).getContent());
		System.out.println("==================================");
		System.out.print("입력: ");
		String mainSelNum = scan.nextLine();

		return mainSelNum;
	}

	/***
	 * 관리자용 메인페이지를 보여주는 메소드
	 * @return 항목 번호 리턴
	 */
	public String adminMainListSelectNum() {

		int num = rnd.nextInt(advertisementList.size());
		System.out.println("==================================");
		System.out.println("  이동하고자 하는 항목의 번호를 입력하세요.");
		System.out.println("==================================");
		System.out.println("1. 물품검색   \t6. 이벤트관리\n");
		System.out.println("2. 게시글 작성\t7. 광고관리\n");
		System.out.println("3. 게시글 목록보기\t8. 회원정보관리\n");
		System.out.println("4. 마이페이지  \t9. 공지사항관리\n");
		System.out.println("5. 고객센터    \t0. 뒤로가기\n");
		System.out.println("프로그램 종료를 원하시면 close를 입력해 주세요");
		System.out.println("==================================");
		System.out.println("               [광고]");
		System.out.printf("%s", advertisementList.get(num).getContent());
		System.out.println("==================================");
		System.out.print("입력: ");
		String mainSelNum = scan.nextLine();

		return mainSelNum;
	}
	
	/***
	 * 물품검색을 위한 목록을 보여주는 메소드
	 * @return 검색어 리턴
	 */
	public String searchItem() {

		System.out.println("================================================================");
		System.out.println("                           물품검색");
		System.out.println("================================================================");
		System.out.println("■카테고리 목록");
		System.out.println("*의류  *도서  *잡화  *디지털  *유아용품  *생활용품");
		System.out.println("■지역목록");
		System.out.println("*서울시  *경기도  *강원도  *충청도  *경상도  *전라도  *제주도");
		System.out.println("================================================================");
		System.out.print("입력: ");
		return scan.nextLine();

	}

	/***
	 * 메인 게시글정보를 메모장에 저장하는 메소드
	 */
	public static void saveBoard() {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(BOARDDATA));
			for (Board board : boardList) {
				writer.write(String.format("%s/%s/%s/%d/%s/%d/%tF/%s/%s/%s/%d/%d\n%s==================\n",
						board.getSeq(), board.getTitle(), board.getNickname(), board.getPrice(), board.getArea(),
						board.getUse_date(), board.getCalendar(), board.getTrade_status(), board.getCategory(),
						board.getItem(), board.getReport(), board.getHit(), board.getContent()));
			}

			writer.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
/***
 * 유저들 정보를 메모장에 저장하는 메소드
 */
	public static void saveUser() {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(USERDATA));

			for (User user : userList) {

				writer.write(String.format("%s/%s/%s/%s/%s/%s/%s/%s/%d/%d\n", user.getName(), user.getId(),
						user.getPw(), user.getNickname(), user.getGender(), user.getTel(), user.getAccount(),
						user.getInsurance(), user.getMoney(), user.getCount()));
			}
			writer.close();
		} catch (Exception e) {
		}

	}

	/***
	 * 게시글신고기록을 메모장에 저장하는 메소드
	 */
	public static void saveBoardReport() {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(BOARDREPORTDATA));

			for (ReportRecord report : reportList) {
				writer.write(String.format("%s/%s\n", report.getSeq(), report.getId()));
			}

			writer.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/***
	 * 장바구니목록을 메모장에 저장하는 메소드
	 */
	public static void saveShoppingBasket() {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(SHOPPINGBASKETDATA));

			for (ShoppingBasket basket : shoppingBasketList) {
				writer.write(
						String.format("%s/%s/%s\n", basket.getSeq(), basket.getWriterNickname(), basket.getMyId()));
			}

			writer.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/***
	 * 고객센터게시글 메모장에 저장하는 메소드
	 */
	public static void saveClientCenterBoard() {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTCENTERDATA));
			for (ClientCenter board : clientCenterList) {
				writer.write(String.format("%s/%s/%s/%s/%d/%s/%tF/%s\n%s==================\n", board.getSeq(),
						board.getTitle(), board.getNickname(), board.getMyId(), board.getHit(), board.getMyInsurance(),
						board.getWriteDay(), board.getType(), board.getContent()));
			}
			writer.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***
	 * 광고목록을 메모장에 저장하는 메소드
	 */
	public static void saveAdvertisement() {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(ADVERDATA));
			for (Adver adver : advertisementList) {
				writer.write(String.format("%s/%tF\n%s==========\n", adver.getSeq(), adver.getWriteDay(),
						adver.getContent()));
			} // for
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}// saveAdvertisement
	
	
	/***
	 * 이벤트 목록을 메모장에 저장하는 메소드
	 */
	private static void saveEventList() {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTDATA));

			for (Event event : eventList) {

				writer.write(String.format("%s/%tF/%d/%s\n%s====================\n", event.getSeq(), event.getRegdate(),
						event.getHit(),event.getTitle(), event.getContent()));
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/***
	 * 공지사항 목록을 메모장에 저장하는 메소드
	 */
	public void saveNotice() {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(NOTICEDATA));

			for (Notice notice : noticeList) {
				writer.write(String.format("%s/%s/%d/%tF\n%s===============\n", notice.getSeq(), notice.getTitle(),
						notice.getHit(), notice.getWriteDay(), notice.getContent()));
			}
			writer.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/***
	 * 이벤트 참가기록을 메모장에 저장하는 메소드
	 */
	public static void savePlayerList() {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTPLAYLIST));

			for(EventPlayer e :playerList) {
				writer.write(String.format("%s/%s/%tF\n", e.getSeq(), e.getId(), e.getPlayDay()));
			}
			

			writer.close();

		} catch (Exception e) {

		}

	}

	/***
	 *  다른 클래스로부터 해당 메소드가 있는 클래스에 게시글 정보를 저장하는 메소드
	 * @param boardList 게시글 정보
	 */
	public void setBoardList(ArrayList<Board> boardList) {
		this.boardList = boardList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 유저들 정보를 저장하는 메소드
	 * @param userList 유저들 정보
	 */
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
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
	public void setReportList(ArrayList<ReportRecord> reportList) {
		this.reportList = reportList;
	}

/***
 * 다른 클래스로부터 해당 메소드가 있는 클래스에 장바구니 정보를 저장하는 메소드
 * @param shoppingBasketList 장바구니정보
 */
	public void setShoppingBasketList(ArrayList<ShoppingBasket> shoppingBasketList) {
		this.shoppingBasketList = shoppingBasketList;
	}

	/***
	 * 출력을 잠시 멈추는 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();

	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 고객센터 게시글을 저장하는 메소드
	 * @param clientCenterList 고객센터 게시글정보
	 */
	public void setClientCenterList(ArrayList<ClientCenter> clientCenterList) {
		this.clientCenterList = clientCenterList;
	}

/***
 * 다른 클래스로부터 해당 메소드가 있는 클래스에 광고정보을 저장하는 메소드
 * @param advertisementList 광고정보
 */
	public void setAdvertisementList(ArrayList<Adver> advertisementList) {
		this.advertisementList = advertisementList;
	}

/***
 * 다른 클래스로부터 해당 메소드가 있는 클래스에 공지정보를 저장하는 메소드
 * @param noticeList 공지정보
 */
	public static void setNoticeList(ArrayList<Notice> noticeList) {
		MainPage.noticeList = noticeList;
	}

/***
 * 다른 클래스로부터 해당 메소드가 있는 클래스에 이벤트정보를 저장하는 메소드
 * @param eventList 이벤트정보
 */
	public static void setEventList(ArrayList<Event> eventList) {
		MainPage.eventList = eventList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 이벤트 참여 정보를 저장하는 메소드
	 * @param playerList 이벤트 참여 정보
	 */
	public static void setPlayerList(ArrayList<EventPlayer> playerList) {
		MainPage.playerList = playerList;
	}

	
}
