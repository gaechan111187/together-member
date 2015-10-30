package member;

import java.util.List;
public class MemberServiceImpl implements MemberService {
	
	MemberDAO dao = new MemberDAO();
	
	@Override
	public String join(MemberVO o) {
		String result = null;
		if (dao.insert(o) == 1) {
			result = "회원가입이 완료되었습니다.";
		} else {
			result = "회원가입 실패했습니다. 다시 시도해주세요.";
		}
		return result;
	}
		
	
	@Override
	public Object login(String email, String password) {
		return dao.login(email, password);
	}
	
	@Override
	public MemberVO selectByPhone(int phoneNum) {
		return dao.searchByFriends(phoneNum);
	}



}