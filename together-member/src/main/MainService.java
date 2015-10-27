package main;

import java.util.List;
/**
 * @file_name : MemberService.java 
 * @author    : chanhok61@daum.net
 * @date      : 2015. 10. 2.
 * @story     :
 */
public interface MainService {
	public List<MainVO> getFriends();
	public MainVO searchFriend(int searchPhone);
}
