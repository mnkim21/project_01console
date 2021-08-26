package com.test.market.clientcenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Scanner;

import com.test.market.MainPage;
import com.test.market.StartPage;
import com.test.market.board.Board;
import com.test.market.member.User;

/***
 * 고객센터 글 리스트/작성/삭제를 위한 클래스
 * @author 6조
 * 
 */
public class ClientCenterList {
	private static Scanner scan;
	private static ArrayList<User> userList;
	private static ArrayList<ClientCenter> clientCenterList;
	private static User myInfo;
	private static MainPage mainPage;

	static {
		userList = new ArrayList<User>();
		clientCenterList = new ArrayList<ClientCenter>();
		scan = new Scanner(System.in);
		myInfo = new User();
		mainPage = new MainPage();
	}

	/***
	 * 매개변수로 유저들 정보,고객센터 게시글, 내정보를 받는 생성자
	 * @param userList 유저들 정보
	 * @param clientCenterList 고객센터 게시글정보
	 * @param myInfo 내정보
	 */
	public ClientCenterList(ArrayList<User> userList, ArrayList<ClientCenter> clientCenterList, User myInfo) {

		this.userList=userList;
		this.clientCenterList=clientCenterList;
		this.myInfo=myInfo;
	}

	
	/***
	 * 고객센터 게시글을 쓰기위한 메소드
	 */
	public void writeBoard() {
		
		StartPage.loadWelcome();

		clientCenterList.sort(new Comparator<ClientCenter>() {
			@Override
			public int compare(ClientCenter o1, ClientCenter o2) {

				return Integer.parseInt(o2.getSeq()) - Integer.parseInt(o1.getSeq());
			}
		});

		System.out.println("==============================================");
		System.out.println("                  게시글 작성");
		System.out.println("==============================================");
		System.out.print("제목: ");
		String title = scan.nextLine();
		// 데이터 구분자인 슬래시가 들어가면 안됨
		while(title.contains("/")||title.length()>=20) {
			System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
			System.out.print("제목: ");
			title = scan.nextLine();
		}

		// 여기를 유형으로 바꾸자
		System.out.println("아래 유형중 한가지를 선택해 주세요.");
		System.out.println("유형: 비방, 욕설, 사기, 기타");
		System.out.print("유형: ");
		String type = scan.nextLine();
		while (checkType(type) > 0) {
			System.out.println("목록에 없는 유형 입니다.");
			System.out.print("유형: ");
			type = scan.nextLine();
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

		System.out.println("==============================================");
		System.out.println("              A.작성 완료 C.작성 취소");
		System.out.println("==============================================");
		System.out.print("번호 입력: ");
		String sel = scan.nextLine();

		if (sel.equals("A")||sel.equals("a")) {

			ClientCenter board = new ClientCenter();
			if (clientCenterList.size() == 0) {
				board.setSeq(String.valueOf(1));
			} else {
				board.setSeq(String.valueOf(Integer.parseInt(clientCenterList.get(0).getSeq()) + 1));
			}

			board.setTitle(title);
			board.setNickname(myInfo.getNickname());
			Calendar c = Calendar.getInstance();
			board.setMyInsurance(myInfo.getInsurance());
			board.setMyId(myInfo.getId());
			board.setWriteDay(c);
			board.setType(type);
			board.setContent(content);
			board.setHit(0);
			this.clientCenterList.add(board);
			System.out.println("작성이 완료되었습니다.");
			pause();

		} else {
			System.out.println("작성이 취소되었습니다.");
			pause();
		}

	}

	/***
	 * 고객센터 게시글을 보여주기 위한 메소드
	 */
	public void showBoardList() {
		
		int end = clientCenterList.size();
		clientCenterList.sort(new Comparator<ClientCenter>() {

			@Override
			public int compare(ClientCenter o1, ClientCenter o2) {
				return o2.getMyInsurance().compareTo(o1.getMyInsurance());
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
				if(clientCenterList.size()%10==0&& i!=0) {
					if(i==clientCenterList.size()/10+last) {
						i--;
					}
					last=0;
					pause();
				}
				System.out.println("==========================================================");
				System.out.println("                      [문의글 목록보기]");
				System.out.println("                                      고객센터:010-XXXX-XXXX");
				System.out.println("==========================================================");
				System.out.println("[번호]\t[제목]\t\t[유형]\t[작성자]\t[보험]\t[작성일]");
				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= clientCenterList.size()) {
						break;
					} else {
						System.out.printf("%s\t%s\t%s\t%s\t%s\t%tF\n", clientCenterList.get(j).getSeq(),
								clientCenterList.get(j).getTitle(), clientCenterList.get(j).getType(),
								clientCenterList.get(j).getNickname(),clientCenterList.get(j).getMyInsurance(),clientCenterList.get(j).getWriteDay());
					}
				}
				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1, end / 10 + last==0?1:end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("           P.이전 페이지 N.다음 페이지 W.게시글 작성  ");
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
					writeBoard();
					if(clientCenterList.size()%10 ==1) {
						last=1;
					}
				} else {
					// 뒤로가기
					mainPage.setMyInfo(myInfo);
					mainPage.setUserList(userList);
					mainPage.setClientCenterList(clientCenterList);
					loop = false;
					break;
					
				}
			}
		}
	}

	/***
	 * 고객센터 게시글의 조회수를 증가하기 위한 메소드
	 * @param num 게시글 번호
	 */
	public void hitBoard(String num) {

		for (int i = 0; i < clientCenterList.size(); i++) {
			if (clientCenterList.get(i).getSeq().equals(num)) {
				clientCenterList.get(i).setHit(clientCenterList.get(i).getHit() + 1);
			}
		}
	}

	/***
	 * 고객센터 게시글의 상세보기를 위한 메소드
	 * @param boardNum 게시글의 번호
	 */
	public void showBoardDetail(String boardNum) {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			ClientCenter board = new ClientCenter();
			for (ClientCenter getlist : clientCenterList) {

				if (getlist.getSeq().equals(boardNum)) {

					System.out.println("==========================================================");
					System.out.printf("               %s(%s)  %s        \n", getlist.getSeq(), getlist.getType(),
							getlist.getTitle());
					System.out.printf("                               (업로드날짜:%tF/조회수:%,d)\n", getlist.getWriteDay(),
							getlist.getHit());
					System.out.println("==========================================================");
					System.out.printf("- 닉네임 	:%s\n", getlist.getNickname());
					System.out.printf("- 내용		:\n%s", getlist.getContent());
					System.out.println("==========================================================");
					System.out.println("            E.게시글 수정  D.게시글 삭제 C.뒤로가기");
					System.out.println("==========================================================");
					System.out.print("입력: ");
					String num = scan.nextLine();

					if (num.equals("E")||num.equals("e")) {
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
						if (getlist.getMyId().equals(myInfo.getId()) || myInfo.getNickname().equals("admin")) {
							System.out.print("게시글을 삭제 하시겠습니까?(O,X):");
							String choice = scan.nextLine();
							if (choice.equals("O") || choice.equals("o") || choice.equals("0")) {
								deleteBoard(getlist.getSeq());

								System.out.println("게시글이 삭제되었습니다");
								break;
							} else {
								System.out.println("게시글 삭제를 취소합니다");
								pause();
							}
						} else {
							System.out.println("게시글을 삭제할 권한이 없습니다");
							pause();
						}
					}
				} else {
					// 뒤로가기
					loop = false;
				}
			}
		}

		pause();

	}

	/***
	 * 고객센터 게시글의 삭제를 위한 메소드
	 * @param num 게시글의 번호
	 */
	public void deleteBoard(String num) {
		int i = 0;
		for (i = 0; i < clientCenterList.size(); i++) {
			if (clientCenterList.get(i).getSeq().equals(num)) {
				clientCenterList.remove(i);
				break;
			}
		}
	}

	/***
	 * 고객센터 유형의 유효성검사
	 * @param type 유형
	 * @return 유효성 검사 실패시 1이상 리턴
	 */
	public int checkType(String type) {

		int count = 0;
		String[] list = { "비방", "욕설", "사기", "기타" };
		if (!(type.equals(list[0]) || type.equals(list[1]) || type.equals(list[2]) || type.equals(list[3]))) {
			count++;
		}
		return count;
	}
	
/***
 * 고객센터를 수정하기 위한 메소드	
 * @param boardNum 게시글의 번호
 */
	public void modifyBoard(String boardNum) {

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			for (int i = 0; i < clientCenterList.size(); i++) {
				if (clientCenterList.get(i).getSeq().equals(boardNum)) {
					System.out.println("===========================================");
					System.out.println("            고객센터-게시글 수정");
					System.out.println("===========================================");
					System.out.printf("- 닉네임 		:%s\n", clientCenterList.get(i).getNickname());
					System.out.printf("-1. 제목 		: %s\n", clientCenterList.get(i).getTitle());
					System.out.printf("-2. 유형		: %s\n", clientCenterList.get(i).getType());
					System.out.printf("-3. 내용		:\n%s", clientCenterList.get(i).getContent());
					System.out.println("===========================================");
					System.out.println("                 0.뒤로가기");
					System.out.println("===========================================");
					System.out.print("번호입력: ");
					String num = scan.nextLine();

					if (num.equals("1")) {
						System.out.print("제목: ");
						String title = scan.nextLine();
						// 데이터 구분자인 슬래시가 들어가면 안됨
						while(title.contains("/")||title.length()>=15) {
							System.out.println("올바르지 않은 단어가 포함되어 있거나 길이가 너무깁니다.");
							System.out.print("제목: ");
							title = scan.nextLine();
						}
						clientCenterList.get(i).setTitle(title);
						System.out.println("제목이 변경되었습니다.");

					} else if (num.equals("2")) {
						
						System.out.println("유형: 비방, 욕설, 사기, 기타");
						System.out.print("유형: ");
						String type = scan.nextLine();
						while (checkType(type) > 0) {
							System.out.println("목록에 없는 유형 입니다.");
							System.out.print("유형: ");
							type = scan.nextLine();
						}
						clientCenterList.get(i).setType(type);
						System.out.println("유형이 변경되었습니다.");
						

					} else if (num.equals("3")) {
						System.out.print("내용(종료:exit): ");

						String content = "";

						while (true) {
							String temp = scan.nextLine();
							if (temp.equals("exit")) {
								break;
							}
							content += temp + "\r\n";
						}
						clientCenterList.get(i).setContent(content);
						System.out.println("내용이 변경되었습니다.");
						
						

					} else {
						
						System.out.println("게시글 수정을 종료합니다.");
						loop=false;
						break;

					}
				}
			}
		}
		
	}
	

	/***
	 * 출력을 멈추는 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 유저들 정보를 받아오는 메소드
	 * @param userList 유저들 정보리스트
	 */
	public static void setUserList(ArrayList<User> userList) {
		ClientCenterList.userList = userList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 고객센터게시글정보를 받아오는 메소드
	 * @param clientCenterList 고객센터게시글정보리스트
	 */
	public static void setClientCenterList(ArrayList<ClientCenter> clientCenterList) {
		ClientCenterList.clientCenterList = clientCenterList;
	}

	/***
	 * 다른 클래스로부터 해당 메소드가 있는 클래스에 내 정보를 받아오는 메소드
	 * @param myInfo 내정보
	 */
	public static void setMyInfo(User myInfo) {
		ClientCenterList.myInfo = myInfo;
	}

}
