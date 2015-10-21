package member;

import java.util.List;

/**
 * @file_name : MemberService.java 
 * @author    : chanhok61@daum.net
 * @date      : 2015. 10. 2.
 * @story     :
 */
public interface MemberService {
	/**
	 * DML ->pstmt.executeUpdate()
	 */
	//회원가입
	public String join(MemberVO o);
	
	public int changePass(MemberVO o);
	
	public int close(MemberVO o);
	
	/**
	 * DQL->stmt.executeQuery()
	 */
	//로그인
		public MemberVO login(String id, String pass);
		//총회원수
		public int count();
		//아이디검색
		public MemberVO searchById(String id);
		//이름 검색(중복적용)
		public List<MemberVO> selectByName(String id);
		//키워드검색(중복적용)
		public List<MemberVO> searchBySearchword(String domain, String Searchword);
		//전체회원목록
		public List<MemberVO> getList();
}
