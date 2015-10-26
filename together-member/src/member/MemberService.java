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
	public Object login(String id, String pass);
	
	//친구 검색
	public MemberVO selectByPhone(int phoneNum);
		
}
