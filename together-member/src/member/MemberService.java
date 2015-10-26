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
	
	/**
	 * DQL->stmt.executeQuery()
	 */
	//로그인
	public MemberVO login(String id, String pass);
	//이름 검색(중복적용)
	public List<MemberVO> selectByName(String id);
		
}
