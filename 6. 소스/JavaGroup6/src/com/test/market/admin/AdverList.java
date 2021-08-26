package com.test.market.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Scanner;

import com.test.market.MainPage;
import com.test.market.StartPage;

/***
 * 관리자를 위한 광고 추가/삭제 클래스
 * @author 6조
 * 
 *
 */
public class AdverList {
	
	private static ArrayList<Adver> advertisementList;
	private static Scanner scan;
	private static MainPage mainPage;

	
	static {
		advertisementList = new ArrayList<Adver>();
		scan = new Scanner(System.in);
		mainPage = new MainPage();
	}

	/***
	 * 
	 * @param advertisementList 광고정보가 담긴 ArrayList
	 * MainPage에서 받아온다
	 */
	public AdverList(ArrayList<Adver> advertisementList) {
		this.advertisementList=advertisementList;
	}
	
	/***
	 * 광고 목록들을 보여주기위한 메소드  
	 */
	public void showAdverList() {
		
		
		
		int end = advertisementList.size();
		int last = 0;

		boolean loop = true;
		while (loop) {
			StartPage.loadWelcome();
			System.out.println("\n\n\n");
			if (end % 10 > 0) {
				last = 1;
			}

			for (int i = 0; i <= end / 10 + last;) {
				advertisementList.sort(new Comparator<Adver>() {
					
					@Override
					public int compare(Adver o1, Adver o2) {

						return Integer.parseInt(o1.getSeq())-Integer.parseInt(o2.getSeq());
					}
				});
				System.out.println("==========================================================");
				System.out.println("                        [광고리스트]");
				System.out.println("==========================================================");
				System.out.println("[번호]\t[내용]\t\t\t\t[상표]");
				
				for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
					if (j >= advertisementList.size()) {
						break;
					} else {
						String tempContent = advertisementList.get(j).getContent().replace("\r\n"," ");
							System.out.printf("%s\t%s \t\t%s",advertisementList.get(j).getSeq(),tempContent.length()<10?tempContent:tempContent.substring(0,10)+"...",advertisementList.get(j).getContent().substring(advertisementList.get(j).getContent().indexOf("-"),advertisementList.get(j).getContent().length()-1));
					}
				}
				System.out.println("==========================================================");
				System.out.printf("                         %d/%d\n", i + 1, end / 10 + last==0?1:end / 10 + last);
				System.out.println("==========================================================");
				System.out.println("                  P.이전 페이지 N.다음 페이지  ");
				System.out.println("                A.추가하기  D.삭제하기 C. 뒤로가기");
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
					// 작성하기
					writeAdver();
					if(advertisementList.size()%10 ==1) {
						last=1;
					}
				} else if (sel.equals("D") || sel.equals("D")) {
					// 삭제하기
					System.out.print("번호를 입력해주세요: ");
					String strNum = scan.nextLine();
					while(checkNum(strNum)>0) {
						System.out.println("번호를 잘못 입력하셨습니다");
						strNum=scan.nextLine();
					}

					for(int j=0;j<advertisementList.size();j++) {
						if(advertisementList.get(j).getSeq().equals(strNum)) {
							advertisementList.remove(j);
							System.out.println("해당 광고가 삭제되었습니다.");
							if(advertisementList.size()%10==0&& i!=0) {
								if(i==advertisementList.size()/10+last) {
									i--;
								}
								last=0;
								pause();
							}
							break;
						}
					}
				}else {
					// 뒤로가기
					mainPage.setAdvertisementList(advertisementList);
					loop = false;
					break;
				}
			}
		}
		pause();
	}
	
	/***
	 * 
	 * @param num 정수인지 확인하는 매개변수
	 * @return 정수가아닌 글자가 나올시 정수가 아닌 수만큼 리턴
	 */
	public int checkNum(String num) {
		int count=0;
		for(int i=0;i<num.length();i++) {
			char c = num.charAt(i);
			if(!(c>='0'&&c<='9')) {
				count++;
			}
		}
		return count;
	}
	
	
	/***
	 * 광고를 추가하기 위한 메소드
	 */
	public void writeAdver() {
		
		advertisementList.sort(new Comparator<Adver>() {
			
			@Override
			public int compare(Adver o1, Adver o2) {

				return Integer.parseInt(o2.getSeq())-Integer.parseInt(o1.getSeq());
			}
		});
		StartPage.loadWelcome();
		System.out.println("\n\n\n");
		
		System.out.println("===========================================");
		System.out.println("                 [광고추가]");
		System.out.println("===========================================");
		System.out.println();
		System.out.print("내용(종료:exit): ");
		
		String content="";
		Adver adver = new Adver();
		
		if(advertisementList.size()==0) {
			adver.setSeq("1");
		}else {
			adver.setSeq(String.valueOf(Integer.parseInt(advertisementList.get(0).getSeq())+1));
			
		}
		adver.setWriteDay(Calendar.getInstance());
		
		
		while(true) {
			String temp=scan.nextLine();
			if(temp.equals("exit")) {
				break;
			}
			content += temp+"\r\n";
		}
		System.out.println("===========================================");
		System.out.println("           S.작성완료    C.작성취소");
		System.out.println("===========================================");
		System.out.print("입력: ");
		String sel = scan.nextLine();
			
		if(sel.equals("S")||sel.equals("s")) {
			adver.setContent(content);
			advertisementList.add(adver);
			System.out.println("작성이 완료되었습니다.");
		}else {
			System.out.println("작성이 취소되었습니다.");
		}
		pause();
		
	}
	

	/***
	 * 
	 * @param advertisementList 다른 클래스로부터 광고리스트를 받아오기 위한 메소드
	 */
	public static void setAdvertisementList(ArrayList<Adver> advertisementList) {
		AdverList.advertisementList = advertisementList;
	}
	
	
	/***
	 * 출력을 잠시 멈추기 위한 메소드
	 */
	public void pause() {
		System.out.println("엔터를 누르시면 다음으로 진행합니다.");
		scan.nextLine();

	}
	
}
