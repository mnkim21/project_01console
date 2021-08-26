package com.test.market.board;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Scanner;

import com.test.market.MainPage;
import com.test.market.StartPage;
import com.test.market.member.User;

/***
 *	메인 게시글 작성을 위한 클래스 
 * @author 6조
 *
 */
public class WriteBoard {
	private static Scanner scan;
	private static ArrayList<Board> boardList;
	private static ArrayList<User> userList;
	private static User myInfo;
	private static MainPage mainPage;
	
	static {
		scan = new Scanner(System.in);
		mainPage = new MainPage();
		boardList = new ArrayList<Board>();
		userList = new ArrayList<User>();
	}
	
	/***
	 * 매개변수 없는 생성자
	 */
	public WriteBoard() {
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * 매개변수로 게시글정보,유저들 정보,내정보를 받아오는 생성자
	 * @param boardList 게시글정보 리스트
	 * @param myInfo 내정보
	 * @param userList 유저들 정보 
	 */
	public WriteBoard(ArrayList<Board> boardList,User myInfo,ArrayList<User> userList) {
		//내 로그인정보 담아오기
		this.boardList=boardList;
		this.myInfo=myInfo;
		this.userList=userList;
		
	
	}
	
	/***
	 * 메인 게시글 작성을 위한 메소드
	 */
	public void exec() {
		
		StartPage.loadWelcome();
		System.out.println("\n\n\n");
		
		//내림차순 정렬함
		boardList.sort(new Comparator<Board>() {
			@Override
			public int compare(Board o1, Board o2) {
				
				return Integer.parseInt(o2.getSeq())-Integer.parseInt(o1.getSeq());
			}
		});
		
		System.out.println("==============================================");
		System.out.println("                  게시글 작성");
		System.out.println("==============================================");
		System.out.print("제목: ");
		String title = scan.nextLine();
		//데이터 구분자인 슬래시가 들어가면 안됨
		while(title.contains("/")||title.length()>=15) {
			System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
			System.out.print("제목: ");
			title = scan.nextLine();
		}
		
		System.out.print("제품명: ");
		String item=scan.nextLine();
		while(item.contains("/")||item.length()>=15) {
			System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
			System.out.print("제품명: ");
			item = scan.nextLine();
		}
		
		System.out.print("가격: ");
		String strPrice = scan.nextLine();
		while(checkPrice(strPrice)>0) {
			System.out.println("올바르지 않은 가격입니다.");
			System.out.print("가격: ");
			strPrice = scan.nextLine();
		}
		int price = Integer.parseInt(strPrice);
		
		
		System.out.print("사용기간(개월): ");
		String strUse_date = scan.nextLine();
		while(checkUse_Date(strUse_date)>0) {
			System.out.println("올바르지 않은 가격입니다.");
			System.out.print("사용기간(개월): ");
			strUse_date = scan.nextLine();
		}
		int use_date = Integer.parseInt(strUse_date);
		
		System.out.println("*서울시  *경기도  *강원도  *충청도  *경상도  *전라도  *제주도");
		System.out.print("거래지역: ");
		String area = scan.nextLine();
		while(checkArea(area) > 0) {
			System.out.println("목록에 없는 지역입니다.");
			System.out.print("거래지역: ");
			area = scan.nextLine();
		}
		
//		System.out.print("거래방법(1.직거래/2.택배): ");
//		String how = scan.nextLine();
//		while(checkHow(how) > 0) {
//			System.out.println("다시 입력하세요.");
//			System.out.print("거래방법(1.직거래/2.택배): ");
//			how = scan.nextLine();
//		}
		
		
		System.out.println("*의류  *도서  *잡화  *디지털  *유아용품  *생활용품  *애견용품  *기타");
		System.out.print("카테고리: ");
		String category = scan.nextLine();
		while(checkCategory(category)>0) {
			System.out.println("목록에 없는 카테고리입니다");
			System.out.print("카테고리: ");
			category = scan.nextLine();
		}
		
		System.out.print("내용(종료:exit): ");
		
		String content="";
		
		while(true) {
			String temp=scan.nextLine();
			if(temp.equals("exit")) {
				break;
			}
			content += temp+"\r\n";
		}
		System.out.println("==============================================");
		System.out.println("             A.작성 완료  C.작성 취소");
		System.out.println("==============================================");
		System.out.print("번호 입력: ");
		String sel = scan.nextLine();
		
		if(sel.equals("A")) {
			
			Board board = new Board();
			if(boardList.size()==0) {
				board.setSeq(String.valueOf(1));
			}else {
				board.setSeq(String.valueOf(Integer.parseInt(boardList.get(0).getSeq())+1));
			}
			
			board.setTitle(title);
			board.setNickname(myInfo.getNickname());
			board.setPrice(price);
			board.setArea(area);
			board.setUse_date(use_date);
			Calendar c= Calendar.getInstance();
			board.setCalendar(c);
			board.setTrade_status("판매중");
			board.setCategory(category);
			board.setItem(item);
			board.setContent(content);
			board.setReport(0);
			board.setHit(0);
			boardList.add(board);
			mainPage.setBoardList(boardList);
			System.out.println("작성이 완료되었습니다.");
			//내 정보랑 유저리스트
			myInfo.setCount(myInfo.getCount()+1);
			mainPage.setMyInfo(myInfo);
			//userlist에 내용도 바꿔줘야하니까 메소드로 따로 만들어서 설정함.
			userInfo(myInfo);
			mainPage.setUserList(userList);
			pause();
			
		}else {
			System.out.println("되돌아 갑니다");
			pause();
		}
		
	}
	
	/***
	 * 게시글 작성 후 유저들 정보도 같이 갱신하는 메소드
	 * @param myInfo  내정보
	 */
	public void userInfo(User myInfo) {
		for (int i = 0; i < userList.size(); i++) {
			if (myInfo.getId().equals(userList.get(i).getId())) {
				userList.set(i, myInfo);
				break;
			}
		}
	}
	
//	//입력한 거래방법이 맞는지 확인하는 메소드
//		public int checkHow(String how) {
//			int count = 0;
//			
//			if(how.equals("1")) {
//				how = "직거래";
//			} else if(how.equals("2")) {
//				how = "택배";
//			} else {
//				count++;
//			}
//			return count;
//				
//		}
		
	/***
	 * 거래지역 유효성 검사
	 * @param area 거래지역
	 * @return 유효성검사 실패시 1리턴
	 */
		public int checkArea(String area) {
			int count = 0;
			String[] list = {"서울시", "경기도", "강원도", "충청도", "경상도", "전라도", "제주도"};
			
			if(!(area.equals(list[0])||area.equals(list[1])||area.equals(list[2])||area.equals(list[3])||area.equals(list[4])||area.equals(list[5])||area.equals(list[6]))) {
				count = 1;
			}
			return count;

		}
	
/***
 * 입력한 가격이 숫자가 맞는지 확인하는 메소드
 * @param strPrice 가격
 * @return 유효성검사 실패시 1이상 리턴
 */
	public int checkPrice(String strPrice) {
		int count=0;
		for(int i=0;i<strPrice.length();i++) {
			char c = strPrice.charAt(i);
			if(!(c>='0'&&c<='9')) {
				count++;
			}
		}
		return count;
	}
	
	
	//입력한 사용일이 숫자가 맞는지 확인하는 메소드
	/***
	 * 입력한 사용일이 숫자가 맞는지 확인하는 메소드
	 * @param strUse_Date 사용일
	 * @return 유효성검사 실패시 1이상 리턴
	 */
	public int checkUse_Date(String strUse_Date) {
		
		int count=0;
		for(int i=0;i<strUse_Date.length();i++) {
			char c = strUse_Date.charAt(i);
			if(!(c>='0'&&c<='9')) {
				count++;
			}
		}
		return count;
	}
	
	//입력한 카테고리가 알맞는지 확인하는 메소드
	/***
	 * 입력한 카테고리가 알맞는지 확인하는 메소드
	 * "의류","도서","잡화","디지털","유아용품","생활용품","애견용품","기타"
	 * @param categroy 카테고리
	 * @return 카테고리에없으면 1이상 리턴
	 */
	public int checkCategory(String categroy) {
		
		int count=0;
		String[] list ={"의류","도서","잡화","디지털","유아용품","생활용품","애견용품","기타"};
		if(!(categroy.equals(list[0])||categroy.equals(list[1])||categroy.equals(list[2])||categroy.equals(list[3])||categroy.equals(list[4])||categroy.equals(list[5])||categroy.equals(list[6])||categroy.equals(list[7]))) {
			count++;
		}
		return count;
	}
	
	/***
	 * 출력을 잠시 멈추는 메소드
	 */
	public static void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();

	}

	/***
	 *  다른 클래스로부터 해당 메소드가 있는 클래스에 게시글 정보를 저장하는 메소드
	 * @param boardList 게시글 정보
	 */
	public static void setBoardList(ArrayList<Board> boardList) {
		WriteBoard.boardList = boardList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 유저들 정보를 저장하는 메소드
	 * @param userList 유저들 정보
	 */
	public static void setUserList(ArrayList<User> userList) {
		WriteBoard.userList = userList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 내 정보를 저장하는 메소드
	 * @param myInfo 내정보
	 */
	public static void setMyInfo(User myInfo) {
		WriteBoard.myInfo = myInfo;
	}
	
	
}
