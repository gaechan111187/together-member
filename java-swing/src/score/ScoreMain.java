package score;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import member.MemberService;
import member.MemberServiceImpl;

public class ScoreMain {
public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	ScoreService service = new ScoreServiceImpl();
	MemberService mservice = new MemberServiceImpl();
	List<ScoreVO> list = new ArrayList<ScoreVO>();
	ScoreVO g =new ScoreVO();
	
	while (true) {
		System.out.println("1:성적등록 2:학적부리스트보기 3:ID로검색 4:과목별검색 5:성적순위출력 6:ID순출력 7:종료");
		
		switch (scanner.nextInt()) {
		case 1:
			// id가 존재 해야만 성적입력가능하게 
			// 존재하지않으면 본교학생이 아닙니다. 메시지 보여주세요
			System.out.println("ID입력");
			String userid = scanner.next();
			if (mservice.searchById(userid).getUserid()!=null && service.searchByUserid(userid).getUserid()==null) {
				System.out.println("JAVA점수입력");
				int java = scanner.nextInt();
				System.out.println("JSP점수입력");
				int jsp = scanner.nextInt();
				System.out.println("HTML점수입력");
				int html = scanner.nextInt();
				System.out.println("JAVASCRIPT점수입력");
				int javascript = scanner.nextInt();
				System.out.println("ORACLE점수입력");
				int oracle = scanner.nextInt();
				System.out.println("SPRING점수입력");
				int spring = scanner.nextInt();
				ScoreVO score = new ScoreVO(java, jsp, html, javascript, oracle, spring, userid);
				service.input(score);
				break;
			} else {
				System.out.println("성적을 등록할수 없습니다.");
				break;
			}			
		case 2:
			list = service.getList();
			for (ScoreVO gd : list) {
				System.out.println(gd);
			}
			break;
		case 3:
			System.out.println("ID입력");
			String searchId = scanner.next();
			if (mservice.searchById(searchId).getUserid()!=null) {
				System.out.println(service.searchByUserid(searchId));
				break;
			} else {
				System.out.println("본교학생이 아닙니다.");
				break;
			}
		case 4:
			System.out.println("검색하실 속성(1.JAVA, 2.JSP, 3.HTML)과 값을 입력해주세요.");
			int col = scanner.nextInt();
			switch (col) {
			case 1:
				System.out.println("JAVA점수 입력");
				list = service.searchByScore("java",scanner.nextInt());
				break;
			case 2:
				System.out.println("JSP점수 입력");
				list = service.searchByScore("jsp",scanner.nextInt());
				break;
			case 3:
				System.out.println("HTML점수 입력");
				list = service.searchByScore("html",scanner.nextInt());
					break;
			default:
				break;
			}
			if (list.isEmpty()==false) {
				for (ScoreVO sc : list) {
					System.out.println(sc);
				}
			} else {
				System.out.println("검색한 값이 존재하지 않습니다.");
			}
			break;
		case 5:
			list = service.descByTotal();
			for (ScoreVO gd : list) {
				System.out.println(gd);
			}
			break;
		case 6:
			list = service.getList();
			for (ScoreVO gd : list) {
				System.out.println(gd);
			}
			break;
		case 7:	return;
		default:break;
		}
	}
}
}
