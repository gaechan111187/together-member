package member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberMain {
	/**
	 * "안녕하세요".substring(0,2); 0이상 2미만 "안녕" (1,3)은 "녕하"
	 */
	public static void main(String[] args) {
		/**
		 * CRUD Create : 추가 Read : 검색 Update : 수정 Delete : 삭제
		 */
		Scanner scanner = new Scanner(System.in);
		MemberService service = MemberServiceImpl.getInstance();
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO m = new MemberVO();
		while (true) {
			System.out.println("1:회원가입 2:로그인 3:총회원수 4:ID검색 5.이름으로 검색 6.조건 검색7.전체회원정보 8.회원정보수정 9.회원탈퇴 10.종료");

			switch (scanner.nextInt()) {
			case 1:
				System.out.println("아이디 입력");
				String userid = scanner.next();
				if (service.searchById(userid).getUserid() != null) {
					System.out.println("이미 가입된 아이디 입니다.");
					break;
				}
				
				System.out.println("비번 입력");
				String password = scanner.next();
				System.out.println("이름 입력");
				String name = scanner.next();
				System.out.println("생년입력");
				String birth = scanner.next();
				System.out.println("전화번호");
				String phone = scanner.next();
				System.out.println("이메일");
				String email = scanner.next();
				System.out.println("성별");
				String gender = scanner.next();
				System.out.println("주소입력");
				String addr = scanner.next();

				MemberVO temp = new MemberVO(userid, password, name, birth, phone, email, gender, addr);
				
				System.out.println(service.join(temp));

				// System.out.println("반갑습니다."+name+"님");
				break;
			case 2:
				System.out.println("아이디 입력 ,비번입력");
				String idck = scanner.next();
				String passck = scanner.next();
				
				if (service.login(idck, passck).getUserid() != null) {
					System.out.println("환영합니다.");
				} else {
					System.out.println("다시 로그인 하세요");
					break;
				}
				// String flag = result.substring(0, 2);
				// switch (flag) {
				// case "환영":System.out.println(result);
				// break;//현실에서는 로그인 성공 페이지로 전환
				// case "비번":System.out.println(result);
				// break;// 현실에서는 로그인 페이지 다시 보여줌
				// case "입력":System.out.println(result);
				// break; // 현실에서는 회원가입 페이지 보여줌
				//
				// default:
				// break;
				// }

				break;
			case 3:
				System.out.println("총 회원수는 " + service.count() + "명 입니다.");

				break;
			case 4:
				System.out.println("ID로 검색합니다. ID를 입력하세요");
				String searchId = scanner.next();
				m = service.searchById(searchId);
				if (m.getUserid() != null) {
					System.out.println(m.toString());
				} else {
					System.out.println("검색한 ID가 존재하지 않습니다.");
				}
				
				break;
			case 5:
				System.out.println("이름으로 검색합니다. 이름을 입력하세요");
				list = service.selectByName(scanner.next());
				if (!list.isEmpty()) {
					for (MemberVO mem : list) {
						System.out.println(mem);
					}
				}

				else {
					System.out.println("없는 이름 입니다.");

				}
				break;
			case 6:
				System.out.println("검색할 항목을 정해주세요. 1:이름 2:주소 3:성별");
				int sub = scanner.nextInt();
				switch (sub) {
				case 1:
					System.out.println("검색할 이름 입력");
					list = service.searchBySearchword("name", scanner.next());
					break;
				case 2:
					System.out.println("검색할 주소 입력");
					list = service.searchBySearchword("addr", scanner.next());
					break;
				case 3:
					System.out.println("검색할 성별 입력");
					list = service.searchBySearchword("gender", scanner.next());
					break;

				default:
					break;

				}
				for (MemberVO vo : list) {
					System.out.println(vo);
				}
				break;
			case 7:
				list = service.getList();
				for (MemberVO mem : list) {
					System.out.println(mem);
				}

				break;

			case 8:
				System.out.println("아이디 입력 ,비번입력");
				String idck2 = scanner.next();
				String passck2 = scanner.next();
				m = service.login(idck2, passck2);
				if (m != null) {
					System.out.println("환영합니다.");
				} else {
					System.out.println("로그인 실패입니다.다시 로그인하세요");
					break;
				}

				System.out.println("수정 할 항목을 정해주세요.(비번 : password, 주소:addr)");
				String column = scanner.next();
				System.out.println("수정값을 입력하세요");
				String changeVal = scanner.next();
				switch (column) {
				case "password":
					m.setPassword(changeVal);
					service.changePass(m);
					break;
				case "addr":
					m.setAddr(changeVal);
					service.changePass(m);
					break;

				default:break;
				}
				System.out.println("정보가 수정되었습니다.");
				
				break;

			case 9:
				System.out.println("탈퇴하실 ID와 비번을 입력하세요");
				String idde = scanner.next();
				String psde = scanner.next();
				m = service.login(idde, psde);
				if (m.getUserid() != null) {
					System.out.println("삭제하시겠습니까? (y/n)");
					String yn = scanner.next();
					System.out.println(yn);
					if (yn.equals("y")) {
						service.close(m);
						System.out.println("삭제되었습니다.");
					}
					else {System.out.println("취소되었습니다.");
						break;
					}
				} else {
					System.out.println("로그인 실패입니다.다시 로그인하세요");
					break;
				}
				
				break;

			case 10:
				return;

			default:
				break;
			}

		}
	}


}
