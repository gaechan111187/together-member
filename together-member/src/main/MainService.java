package main;

import java.util.List;

import member.MemberVO;
/**
 * @file_name : MemberService.java 
 * @author    : chanhok61@daum.net
 * @date      : 2015. 10. 2.
 * @story     :
 */
public interface MainService {

	
	public List<MainVO> getFriends(String phone);
	public MainVO searchFriend(String searchPhone);
	public String addFriend(MemberVO userVO, MainVO friendVO);
	
	public String getSource(String resources);		// check박스 검사
}

