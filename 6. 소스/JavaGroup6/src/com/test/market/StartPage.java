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
import com.test.market.admin.Notice;
import com.test.market.board.Board;
import com.test.market.board.ReportRecord;
import com.test.market.clientcenter.ClientCenter;
import com.test.market.event.Event;
import com.test.market.event.EventPlayer;
import com.test.market.member.FindUserId;
import com.test.market.member.FindUserPw;
import com.test.market.member.SignIn;
import com.test.market.member.SignUp;
import com.test.market.member.User;
import com.test.market.mypage.ShoppingBasket;

/***
 * 프로그램 시작을 위한 스타트페이지 (회원가입,로그인,아이디찾기,비밀번호찾기,프로그램종료)
 * @author 6조
 * 
 */
public class StartPage {

	private final static String USERDATA;
	private final static String BOARDDATA;
	private final static String BOARDREPORTDATA;
	private final static String SHOPPINGBASKETDATA;
	private final static String CLIENTCENTERDATA;
	private final static String ADVERDATA;
	private final static String NOTICEDATA;
	private final static String EVENTDATA;
	private final static String EVENTPLAYLIST;
	private static ArrayList<EventPlayer> playerList;
	private static ArrayList<Notice> noticeList;
	private static ArrayList<Adver> advertisementList;
	private static ArrayList<User> userList;
	private static ArrayList<Board> boardList;
	private static ArrayList<ReportRecord> reportList;
	private static ArrayList<ShoppingBasket> shoppingBasketList;
	private static ArrayList<ClientCenter> clientCenterList;
	private static ArrayList<Event> eventList;
	private static Scanner scan;
	private static User myInfo;
	private static MainPage mainPage;
	private static Random rnd;

	static {
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
		scan = new Scanner(System.in);
		userList = new ArrayList<User>();
		myInfo = new User();
		boardList = new ArrayList<Board>();
		shoppingBasketList = new ArrayList<ShoppingBasket>();
		reportList = new ArrayList<ReportRecord>();
		clientCenterList = new ArrayList<ClientCenter>();
		noticeList = new ArrayList<Notice>();
		mainPage = new MainPage();
		rnd = new Random();
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 이벤트참가자정보를 저장하기 위한 메소드
	 * @param playerList 이벤트참가자정보
	 */
	public static void setPlayerList(ArrayList<EventPlayer> playerList) {
		StartPage.playerList = playerList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 이벤트목록정보를 저장하기 위한 메소드
	 * @param eventList 이벤트목록정보
	 */
	public static void setEventList(ArrayList<Event> eventList) {
		StartPage.eventList = eventList;
	}

	
	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 공지사항목록정보를 저장하기 위한 메소드
	 * @param noticeList 공지사항목록정보
	 */
	public static void setNoticeList(ArrayList<Notice> noticeList) {
		StartPage.noticeList = noticeList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 이벤트목록정보를 저장하기 위한 메소드
	 * @param advertisementList 이벤트목록정보
	 */
	public static void setAdvertisementList(ArrayList<Adver> advertisementList) {
		StartPage.advertisementList = advertisementList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 고객센터목록정보를 저장하기 위한 메소드
	 * @param clientCenterList 고객센터목록정보
	 */
	public static void setClientCenterList(ArrayList<ClientCenter> clientCenterList) {
		StartPage.clientCenterList = clientCenterList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 내정보를 저장하기 위한 메소드
	 * @param myInfo 내정보
	 */
	public static void setMyInfo(User myInfo) {
		StartPage.myInfo = myInfo;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 장바구니정보를 저장하기 위한 메소드
	 * @param shoppingBasketList 장바구니정보
	 */
	public static void setShoppingBasketList(ArrayList<ShoppingBasket> shoppingBasketList) {
		StartPage.shoppingBasketList = shoppingBasketList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 게시글신고정보 저장하기 위한 메소드
	 * @param reportList 게시글신고정보
	 */
	public void setReportList(ArrayList<ReportRecord> reportList) {
		this.reportList = reportList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 유저들 정보를 저장하기 위한 메소드
	 * @param userList 유저들 정보
	 */
	public static void setUserList(ArrayList<User> userList) {
		StartPage.userList = userList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 게시글정보를 저장하기 위한 메소드
	 * @param boardList 게시글정보
	 */
	public static void setBoardList(ArrayList<Board> boardList) {
		StartPage.boardList = boardList;
	}

	/***
	 * 프로그램을 처음 시작하는 main 메소드
	 * @param args 메인
	 */
	public static void main(String[] args) {

		boolean loop = true;

		// 유저들 정보 더미데이터 가져오기
		loadUser();
		loadBoard();
		loadReport();
		loadShoppingBasket();
		loadClientCenterBoard();
		loadAdvertisement();
		loadNoticeList();
		loadEventList();
		loadPlayUser();

		while (loop) {

			StartPage.loadWelcome();
			System.out.println("\n\n\n");

			String num = selectNum();

			if (num.equals("1")) {

				SignUp signUp = new SignUp();
				signUp.setUserList(userList);
				signUp.exec();

			} else if (num.equals("2")) {
				SignIn signIn = new SignIn();
				signIn.setUserList(userList);
				int getNum = signIn.exec();

				if (getNum == 1) {
					// main 가기
					mainPage.setMyInfo(myInfo);
					mainPage.setUserList(userList);
					mainPage.setBoardList(boardList);
					mainPage.setReportList(reportList);
					mainPage.setShoppingBasketList(shoppingBasketList);
					mainPage.setClientCenterList(clientCenterList);
					mainPage.setAdvertisementList(advertisementList);
					mainPage.setNoticeList(noticeList);
					mainPage.setEventList(eventList);
					mainPage.setPlayerList(playerList);
					mainPage.playMainPage();

				} else if (getNum == 2) {
					// 아이디 찾기 가기
					FindUserId findId = new FindUserId(userList);
					findId.exec();
					pause();

				} else if (getNum == 2) {
					// 비밀번호 찾기 가기
					FindUserPw findPw = new FindUserPw(userList);
					findPw.exec();
					pause();
				}

			} else if (num.equals("3")) {
				// 아이디찾기
				FindUserId findId = new FindUserId(userList);
				findId.exec();
				pause();

			} else if (num.equals("4")) {

				// 비밀번호찾기
				FindUserPw findPw = new FindUserPw(userList);
				findPw.exec();
				pause();

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
				loop = false;
			}

		}

	}

	/***
	 * 시작페이지 출력목록을 보여주는 메소드
	 * @return 항목의 번호
	 */
	public static String selectNum() {

		int num = rnd.nextInt(advertisementList.size());
		System.out.println("==================================");
		System.out.println("이동하고자 하는 항목의 번호를 입력하세요.");
		System.out.println("==================================");
		System.out.println("1. 회원가입\n");
		System.out.println("2. 로그인\n");
		System.out.println("3. 아이디 찾기\n");
		System.out.println("4. 비밀번호 찾기\n");
		System.out.println("5. 프로그램 종료\n");
		System.out.println("==================================");
		System.out.println("               [광고]");
		System.out.printf("%s", advertisementList.get(num).getContent());
		System.out.println("==================================");
		System.out.print("입력: ");

		String sel = scan.nextLine();

		return sel;
	}

	/***
	 * 출력을 잠시 멈추는 메소드
	 */
	public static void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();

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
	 * 데이터파일에 있는 게시글신고 목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadReport() {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(BOARDREPORTDATA));
			String line = "";
			while ((line = reader.readLine()) != null) {
				ReportRecord report = new ReportRecord();
				String[] temp = line.split("/");
				report.setSeq(temp[0]);
				report.setId(temp[1]);
				reportList.add(report);
			}

			reader.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***
	 * 데이터파일에 있는 장바구니 목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadShoppingBasket() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(SHOPPINGBASKETDATA));
			String line = "";
			while ((line = reader.readLine()) != null) {

				ShoppingBasket basket = new ShoppingBasket();

				String[] temp = line.split("/");
				basket.setSeq(temp[0]);
				basket.setWriterNickname(temp[1]);
				basket.setMyId(temp[2]);

				shoppingBasketList.add(basket);
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***
	 * 데이터파일에 있는 유저들 정보 목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadUser() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(USERDATA));

			String line = "";

			while ((line = reader.readLine()) != null) {

				String[] temp = line.split("/");
				User user = new User();

				user.setName(temp[0]);
				user.setId(temp[1]);
				user.setPw(temp[2]);
				user.setNickname(temp[3]);
				user.setGender(temp[4]);
				user.setTel(temp[5]);
				user.setAccount(temp[6]);
				user.setInsurance(temp[7]);
				user.setMoney(Integer.parseInt(temp[8]));
				user.setCount(Integer.parseInt(temp[9]));

				userList.add(user);
			}
			reader.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***
	 * 데이터파일에 있는 메인게시글 목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadBoard() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(BOARDDATA));

			String line = "";

			while ((line = reader.readLine()) != null) {

				String[] temp = line.split("/");
				Board board = new Board();

				board.setSeq(temp[0]);
				board.setTitle(temp[1]);
				board.setNickname(temp[2]);
				board.setPrice(Integer.parseInt(temp[3]));
				board.setArea(temp[4]);
				board.setUse_date(Integer.parseInt(temp[5]));
				Calendar c = Calendar.getInstance();
				String[] uploadSplit = temp[6].split("-");
				c.set(Integer.parseInt(uploadSplit[0]), Integer.parseInt(uploadSplit[1]) - 1,
						Integer.parseInt(uploadSplit[2]));
				board.setCalendar(c);
				board.setTrade_status(temp[7]);
				board.setCategory(temp[8]);
				board.setItem(temp[9]);
				board.setReport(Integer.parseInt(temp[10]));
				board.setHit(Integer.parseInt(temp[11]));

				String tempContent = "";
				while (!(line = reader.readLine()).equals("==================")) {
					tempContent += line + "\r\n";
				}
				board.setContent(tempContent);

				boardList.add(board);
			}
			reader.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***
	 * 데이터파일에 있는 고객센터게시글 목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadClientCenterBoard() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(CLIENTCENTERDATA));

			String line = "";

			while ((line = reader.readLine()) != null) {

				String[] temp = line.split("/");
				ClientCenter board = new ClientCenter();

				board.setSeq(temp[0]);
				board.setTitle(temp[1]);
				board.setNickname(temp[2]);
				board.setMyId(temp[3]);
				board.setHit(Integer.parseInt(temp[4]));
				board.setMyInsurance(temp[5]);
				Calendar c = Calendar.getInstance();
				String[] timeTemp = temp[6].split("-");
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				board.setWriteDay(c);
				board.setType(temp[7]);

				String tempContent = "";
				while (!(line = reader.readLine()).equals("==================")) {
					tempContent += line + "\r\n";
				}
				board.setContent(tempContent);

				clientCenterList.add(board);
			}
			reader.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***
	 * 프로젝트 로고
	 */
	public static void loadWelcome() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader("folder\\Welcome.txt"));

			String line = "";

			while ((line = reader.readLine()) != null) {

				System.out.println(line);
			}
		} catch (Exception e) {
		}

	}

	/***
	 * 데이터파일에 있는 광고목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadAdvertisement() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(ADVERDATA));
			String line = "";

			while ((line = reader.readLine()) != null) {
				Adver adver = new Adver();

				String[] temp = line.split("/");
				adver.setSeq(temp[0]);
				Calendar c = Calendar.getInstance();
				String[] timeTemp = temp[1].split("-");
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				adver.setWriteDay(c);

				String tempContent = ""; // ***

				while (!(line = reader.readLine()).equals("==========")) {

					tempContent += line + "\r\n";
				}
				adver.setContent(tempContent);
				advertisementList.add(adver);
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}// loadAdvertisement

	
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
	 * 공지사항 목록을 메모장에 저장하는 메소드
	 */
	public static void saveNotice() {

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
	 * 데이터파일에 있는 공지사항 목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadNoticeList() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(NOTICEDATA));

			String line = "";
			while ((line = reader.readLine()) != null) {

				String[] temp = line.split("/");
				Notice notice = new Notice();
				notice.setSeq(temp[0]);
				notice.setTitle(temp[1]);
				notice.setHit(Integer.parseInt(temp[2]));
				Calendar c = Calendar.getInstance();
				String[] timeTemp = temp[3].split("-");
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				notice.setWriteDay(c);
				String tempContent = ""; // ***

				while (!(line = reader.readLine()).equals("===============")) {

					tempContent += line + "\r\n";
				}
				notice.setContent(tempContent);

				noticeList.add(notice);
			}

		} catch (Exception e) {

		}

	}

	
	/***
	 * 데이터파일에 있는 이벤트목록 정보를 ArrayList에 저장하는 메소드
	 */
	private static void loadEventList() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(EVENTDATA));

			String line = "";

			while ((line = reader.readLine()) != null) {

				// Memo 객체 1개 생성
				Event event = new Event();

				String[] temp = line.split("/");

				// 게시글 내용 읽기
				String tempContent = "";

				while (!(line = reader.readLine()).equals("====================")) {
					tempContent += line + "\r\n";
				}

				event.setContent(tempContent);//

				event.setSeq(temp[0]);// 순번
				Calendar c = Calendar.getInstance();
				String[] timeTemp = temp[1].split("-");
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				event.setRegdate(c); // 등록일자
				event.setHit(Integer.parseInt(temp[2]));// 조회수
				event.setTitle(temp[3]); // 제목

				eventList.add(event);

			} // while

			reader.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	

	/***
	 * 데이터파일에 있는 이벤트 참가자 목록을 ArrayList에 저장하는 메소드
	 */
	public static void loadPlayUser() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(EVENTPLAYLIST));

			String line = "";

			while ((line = reader.readLine()) != null) {

				EventPlayer player = new EventPlayer();

				String[] temp = line.split("/");
				player.setSeq(temp[0]);
				player.setId(temp[1]);

				String[] timeTemp = temp[2].split("-");
				Calendar c = Calendar.getInstance();
				c.set(Integer.parseInt(timeTemp[0]), Integer.parseInt(timeTemp[1]) - 1, Integer.parseInt(timeTemp[2]));
				player.setPlayDay(c);

				playerList.add(player);

			}

			reader.close();

		} catch (Exception e) {

		}

	}

}
