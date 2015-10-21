package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberServiceImpl implements MemberService{
	private static MemberService instance = new MemberServiceImpl();
	public static MemberService getInstance() {
		return instance;
	}
	MemberDAO dao = new MemberDAO();
	/**
	 * DML ->pstmt.executeUpdate()
	 */
	//로그인
	public String join(MemberVO o) {
		String temp= "";
		if (dao.insert(o) == 1) {
			System.err.println("회원가입 성공");
		}
		else {
			System.out.println("회원가입 실패");
		}
		return temp;
	}
	//비밀번호 변경
	@Override
	public int changePass(MemberVO o) {
		int changeres = 0;
		changeres = dao.update(o);
		return changeres;
	}
	//회원탈퇴
		@Override
		public int close(MemberVO o) {
			int closeres = 0;
			closeres = dao.delete(o.getUserid());
			return closeres;
		}
	
	/**
	 * DQL->stmt.executeQuery()
	 */
	
	//로그인
	public MemberVO login(String id, String pass) {
		MemberVO m= new MemberVO();
		return  dao.login(id, pass);
	}
	//전체회원수
	@Override
	public int count() {
		int count = 0;
		return count = dao.count();
	}
	//ID로 회원검색
	public MemberVO searchById(String id) {
		MemberVO m = new MemberVO();
			m = dao.selectOneBy(id);
		return m;
	}
	//이름으로 회원검색
	@Override
	public List<MemberVO> selectByName(String id) {
		List<MemberVO> list =new ArrayList<MemberVO>();
		list = dao.selectByName(id);
		return list;
	}
	

	
	//검색어로 검색
	@Override
	public List<MemberVO> searchBySearchword(String domain, String Searchword) {
		List<MemberVO> list =new ArrayList<MemberVO>();
		list = dao.selectSomeBy(domain, Searchword);
		return list;
	}
	//전체 회원목록
	@Override
	public List<MemberVO> getList() {
		List<MemberVO> list =new ArrayList<MemberVO>();
		list = dao.selectAll();
		return list;
	}
	

}
