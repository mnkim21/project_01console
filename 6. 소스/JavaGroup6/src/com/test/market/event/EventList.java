package com.test.market.event;

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

/***
 * 이벤트 목록을 보여주기 위한 클래스
 * @author 6조
 * 
 */
public class EventList {

	private static Scanner scan;
	private static ArrayList<Event> eventList; // customer.txt의 대리자
	private static ArrayList<User> userList;
	private static ArrayList<EventPlayer> playerList;
	private static User myInfo;
	private static MainPage mainPage;

	static {

		scan = new Scanner(System.in);
		eventList = new ArrayList<Event>();
		mainPage = new MainPage();
		myInfo = new User();
		userList = new ArrayList<User>();
		playerList = new ArrayList<EventPlayer>();
	}

	/***
	 * 매개변수 유저들 정보,이벤트정보,내정보,이벤트참여자정보를 생성자를 통해 받는다
	 * @param userList 유저들 정보
	 * @param eventList 이벤트정보
	 * @param myInfo 내정보
	 * @param playerList 이벤트 참여자정보
	 */
	public EventList(ArrayList<User> userList, ArrayList<Event> eventList, User myInfo,
			ArrayList<EventPlayer> playerList) {

		this.userList = userList;
		this.eventList = eventList;
		this.myInfo = myInfo;
		this.playerList = playerList;

	}

	
	/***
	 * 이벤트 목록을 보여주기위한 메소드
	 */
	public void showEventList() {

		int end = eventList.size();
		int last = 0;

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				eventList.sort(new Comparator<Event>() {

					@Override
					public int compare(Event o1, Event o2) {

						return o2.getRegdate().compareTo(o1.getRegdate());
					}
				});

				System.out.println("==========================================================");
				System.out.println("                        [이벤트리스트]");
				System.out.println("                  ★ 한달에 한번씩 도전 가능!!! ★");
				System.out.println("==========================================================");
				System.out.println("[번호]\t[제목]\t\t\t\t[작성일]");

				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= eventList.size()) {
						break;
					} else {
						System.out.printf("%s\t%s \t\t%tF\n", eventList.get(j).getSeq(), eventList.get(j).getTitle(),
								eventList.get(j).getRegdate());
					}
				}
				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1,
						end / 10 + last == 0 ? 1 : end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("             P.이전 페이지 N.다음 페이지 S.이벤트도전  ");
				System.out.println("               A.추가하기  D.삭제하기 C. 뒤로가기");
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

				} else if (sel.equals("S") || sel.equals("S")) {
					int count = 0;
					System.out.print("게시글 번호입력: ");
					String boardNum = scan.nextLine();
					// 조회수 올리기

					for (int j = 0; j < playerList.size(); j++) {
						if (playerList.get(j).getSeq().equals(boardNum)
								&& playerList.get(j).getId().equals(myInfo.getId())) {
							if ((Calendar.getInstance().getTimeInMillis()
									- playerList.get(j).getPlayDay().getTimeInMillis()) / 1000 / 60 / 60 / 24 < 30) {
								System.out.printf("%tF 에 해당 이벤트를 도전하셨습니다.\n", playerList.get(j).getPlayDay());
								System.out.println("플레이 한 날부터 30일 후에 재도전 할 수 있습니다.");
								count = 1;
								pause();
								break;
							}
						}
					}
					if (count == 0) {
						if (boardNum.equals("1")) {
							coinGame(boardNum);
							addPlayList(boardNum);
							break;
						} else if (boardNum.equals("2")) {
							RPS(boardNum);
							addPlayList(boardNum);
							break;
						} else if (boardNum.equals("3")) {
							lotto(boardNum);
							addPlayList(boardNum);
							break;
						}
					}
					hitBoard(boardNum);//

				} else if (sel.equals("A") || sel.equals("a")) {
					// 작성하기
					if (myInfo.getId().equals("admin")) {
						writeEvent();
						if (eventList.size() % 10 == 1) {
							last = 1;
						}
					} else {
						System.out.println("작성할 수 있는 권한이 없습니다");
					}
					pause();

				} else if (sel.equals("D") || sel.equals("D")) {
					// 삭제하기
					if (myInfo.getId().equals("admin")) {
						System.out.print("번호를 입력해주세요: ");
						String strNum = scan.nextLine();
						while (checkNum(strNum) > 0) {
							System.out.println("번호를 잘못 입력하셨습니다");
							strNum = scan.nextLine();
						}

						for (int j = 0; j < eventList.size(); j++) {
							if (eventList.get(j).getSeq().equals(strNum)) {
								eventList.remove(j);
								System.out.println("해당 이벤트가 삭제되었습니다.");
								if (eventList.size() % 10 == 0 && i != 0) {
									if (i == eventList.size() / 10 + last) {
										i--;
									}
									last = 0;
									pause();
								}
								break;
							}
						}
					} else {
						System.out.println("삭제할 수 있는 권한이 없습니다.");
					}
				} else {
					// 뒤로가기

					loop = false;
					break;
				}
			}

		}
		mainPage.setEventList(eventList);
		mainPage.setPlayerList(playerList);
		mainPage.setMyInfo(myInfo);
		mainPage.setUserList(userList);

	}// main

	/***
	 * 이벤트 동전뒤집기 메소드
	 * @param boardNum 게시글 번호
	 */
	public void coinGame(String boardNum) {
		System.out.println("=======================================================================");
		System.out.println("		             [이벤트 게임] 동전 앞뒤맞추기 게임	");
		System.out.println("                         3번 다 맞출시 500원 증정!!");
		System.out.println("=======================================================================");

		int count = 0;
		Scanner sc = new Scanner(System.in);
		String input = "";
		int coin = 0;
		String sw = "";
		for (int i = 0; i < 3; i++) {

			while (true) {
				System.out.println("==== 동전 앞 뒤 맞추기 ====");
				System.out.print("숫자를 입력해주세요 (1.앞면/2.뒷면) : ");
				input = sc.nextLine();
				coin = (int) (Math.random() * (2) + 1);// 1~2

				System.out.println("-----------------------");
				if (input.equals("1")) {
					System.out.println("사용자 : 동전의 앞면을 선택했습니다.");
				} else if (input.equals("2")) {
					System.out.println("사용자 : 동전의 뒷면을 선택했습니다.");
				} else {
					System.out.println("알수없는 번호입니다.");
					pause();
					continue;
				}

				if (coin == 1) {
					System.out.println("PC : 동전의 앞면이 나왔습니다.");
				} else {
					System.out.println("PC : 동전의 뒷면이 나왔습니다.");
				}

				if (input.equals(String.valueOf(coin))) {
					System.out.println("결과 : 맞췄습니다!");
					count++;
				} else {
					System.out.println("결과 : 땡! 틀렸습니다!");
				}
				System.out.println();
				pause();
			}
		}
		
		if(count>=3) {
			System.out.println("다 맞추셨습니다.!! 대박~~ 500원 적립~~");
			myInfo.setMoney(myInfo.getMoney()+500);
			setMyInfoMoney();
		}

	}

	/***
	 * 이벤트 가위바위보 메소드
	 * @param boardNum 게시글번호
	 */
	public void RPS(String boardNum) {

		System.out.println("=======================================================================");
		System.out.println("		               [이벤트 게임]  가위바위보 게임	");
		System.out.println("                      3번다 이길 시 500원 증정!!");
		System.out.println("=======================================================================");

		System.out.println("게임은 3번 진행됩니다.");

		int count = 0;
		for (int i = 0; i < 3; i++) {
			int com = (int) (Math.random() * 3) + 1;

			int input = 0;
			while (true) {
				System.out.print("번호 입력[1:가위  2:바위  3:보] :");
				input = scan.nextInt();
				if (input >= 1 && input <= 3)
					break;
				System.out.println("[1:가위  2:바위  3:보] 중 하나만 선택해주세요(숫자)");
			}

			System.out.println();

			if (com == 1) {
				System.out.println("시스템 : 가위");
			} else if (com == 2) {
				System.out.println("시스템 : 바위");
			} else {
				System.out.println("시스템 :보");
			}

			if (input == 1)
				System.out.println("사람 : 가위");
			else if (input == 2)
				System.out.println("사람 : 바위");
			else
				System.out.println("사람 :보");

			System.out.println("[결과]");
			if (com == input) {
				System.out.println("비김");
			} else if (com == 1 && input == 2 || com == 2 && input == 3 || com == 3 && input == 1) {
				System.out.println("축하합니다 이겼습니다.");
				count++;
				addPlayList(boardNum);
				myInfo.setMoney(myInfo.getMoney() + 500);
				setMyInfoMoney();
			} else {
				System.out.println("아쉽습니다. 다음기회에.");
			}
		}

		System.out.println("\n\n");
		if (count == 3) {
			System.out.println("축하드립니다. 500원 적립!!");
		} else {
			System.out.println("아쉽지만 다음에 도전해주세요~!");
		}

		System.out.println(" 가위바위보 종료 ");
		scan.skip("\r\n");
		pause();

	}

	/***
	 * 이벤트 로또 메소드
	 * @param boardNum 게시글 번호
	 */
	public void lotto(String boardNum) {
		System.out.println("=======================================================================");
		System.out.println("			            [대박 이벤트]  행운의 로또 	");
		System.out.println("포인트 지급기준:    6개 당첨 3,000원     5개 당첨 2,000원      4개 당첨 1,000원   ");
		System.out.println("=======================================================================");

		System.out.println("	♡ 행운의 로또 당첨자 분들께 댁내의 무궁한 건강과 행복을 기원합니다. ♡");

		System.out.println();
		System.out.println();

		while (true) {
			System.out.println("숫자 6개를 입력해주세요.");
			System.out.println("입력할수 있는 숫자의 범위는 1 ~ 65입니다.");
			int arr[] = new int[6];
			System.out.print("입력(띄어쓰기 잘해주세요^^):");
			for (int i = 0; i < 6; i++) {
				arr[i] = scan.nextInt();
				if (!(arr[i] > 0 && arr[i] <= 65)) {
					System.out.println("입력할 수 있는 숫자의 범위를 넘었습니다.");
					i--;
				}
				for (int j = 0; j < i; j++) {
					if (arr[i] == arr[j]) {// 중복확인
						System.out.println("숫자가 중복되었습니다.");
						i--;
					}
					if (arr[i] < arr[j]) {// 오름차순 정리

						int temp = arr[i];
						arr[i] = arr[j];
						arr[j] = temp;
					}
				}

			} // for문

			// 컴퓨터 난수 생성

			Random ram = new Random();

			int comarr[] = new int[6];

			for (int i = 0; i < 6; i++) {
				comarr[i] = ram.nextInt(65) + 1;

				for (int j = 0; j < i; j++) {
					if (comarr[i] == comarr[j]) {
						i--; // 중복일시난수제거
					}
				}

				for (int j = 0; j < 6; j++) {
					if (comarr[i] < comarr[j]) {
						int temp = comarr[i];
						comarr[i] = comarr[j];
						comarr[j] = temp;
					}
				}

			} // for문

			System.out.println();
			System.out.print("입력한 숫자		:");
			for (int j = 0; j < arr.length; j++) {
				System.out.print(arr[j] + " ");
			}
			System.out.println("\n");

			System.out.print("로또 당첨 번호는 	:");
			for (int i = 0; i < 6; i++) {
				System.out.print(comarr[i] + " ");
			}
			System.out.println("입니다.");

			// 사용자가 입력한 숫자와 컴퓨터 난수 비교

			System.out.println();
			int count = 0;
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {
					if (arr[i] == comarr[j]) {
						count++;
					}
				}
			}

			if (count == 4) {
				System.out.println("3등당첨~! 축하합니다. 2,000원 증정!!^^");
				myInfo.setMoney(myInfo.getMoney() + 1000);
				setMyInfoMoney();
			} else if (count == 5) {
				System.out.println("2등당첨~! 축하합니다. 2,000원 증정!!^^");
				myInfo.setMoney(myInfo.getMoney() + 2000);
				setMyInfoMoney();
			} else if (count == 6) {
				System.out.println("1등당첨~! 축하합니다. 3,000원 증정!!^^");
				myInfo.setMoney(myInfo.getMoney() + 3000);
				setMyInfoMoney();
			} else {
				System.out.println("아쉽습니다. 다음기회에 ~!!");
			}

			break;

		} // while

		scan.skip("\r\n");
		pause();
	} // else if(seq.equals("3")){

	/***
	 * 이벤트 작성 메소드
	 */
	public void writeEvent() {

		eventList.sort(new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {

				return Integer.parseInt(o2.getSeq()) - Integer.parseInt(o1.getSeq());
			}
		});
		StartPage.loadWelcome();
		System.out.println("\n\n\n");

		System.out.println("===========================================");
		System.out.println("                 [이벤트추가]");
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

		String content = "";

		System.out.print("내용(종료:exit): ");
		while (true) {
			String temp = scan.nextLine();
			if (temp.equals("exit")) {
				break;
			}
			content += temp + "\r\n";
		}

		Event event = new Event();

		if (eventList.size() == 0) {
			event.setSeq("1");
		} else {
			event.setSeq(String.valueOf(Integer.parseInt(eventList.get(0).getSeq()) + 1));

		}
		event.setTitle(title);
		event.setRegdate(Calendar.getInstance());
		event.setHit(0);
		event.setContent(content);

		System.out.println("===========================================");
		System.out.println("           S.작성완료    C.작성취소");
		System.out.println("===========================================");
		System.out.print("입력: ");
		String sel = scan.nextLine();

		if (sel.equals("S") || sel.equals("s")) {
			event.setContent(content);
			eventList.add(event);
			System.out.println("작성이 완료되었습니다.");
		} else {
			System.out.println("작성이 취소되었습니다.");
		}
		pause();

	}

	/***
	 * 출력을 잠시 멈추는 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();
	}

	/***
	 * 이벤트 게시글의 조회수 저장메소드
	 * @param num 게시글번호
	 */
	public void hitBoard(String num) {

		for (int i = 0; i < eventList.size(); i++) {
			if (eventList.get(i).getSeq().equals(num)) {
				eventList.get(i).setHit(eventList.get(i).getHit() + 1);
			}
		}
	}

	/***
	 * 이벤트를 통해 얻은 보상을 저장하는 메소드
	 */
	public void setMyInfoMoney() {

		for (int i = 0; i < userList.size(); i++) {

			if (userList.get(i).getId().equals(myInfo.getId())) {
				userList.set(i, myInfo);
				break;
			}

		}

	}

	/***
	 * 게시글번호가 숫자인지 확인하는 메소드
	 * @param num 숫자
	 * @return 유효하지 않을시 1이상 리턴
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
	 * 이벤트 플레이 정보를 저장하는 메소드
	 * @param boardNum 게시글 번호
	 */
	public void addPlayList(String boardNum) {

		EventPlayer player = new EventPlayer();

		player.setSeq(boardNum);
		player.setId(myInfo.getId());
		player.setPlayDay(Calendar.getInstance());

		playerList.add(player);

	}

}// class Customerpost.java
