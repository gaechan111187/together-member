package main;

import java.util.ArrayList;
import java.util.List;

import member.MemberVO;


public class MainServiceImpl implements MainService {
	
	private static MainService service = new MainServiceImpl();
	
	public static MainService getService() {
		return service;
	}
	MainDAO dao = new MainDAO();
	
	
	@Override
	public List<MainVO> getFriends(String phone) {
		return dao.selectMyFriends(phone);
	}
	@Override
	public MainVO searchFriend(String searchPhone) {
		return dao.selectMyFriend(searchPhone);
	}
	
	
	@Override
	public String addFriend(MemberVO userVO, MainVO friendVO) {
		String result;
		if (dao.insertFriend(userVO, friendVO) == 1) {
			result = "친구 추가가 되었습니다.";
		} else {
			result = "친구 추가 실패.";
		}
		return result;
	}
	
	@Override
	public String getSource(String resources){
		return resources.substring(457).substring(resources.substring(457).indexOf("=")+1, resources.substring(457).indexOf("]"));
	}

}
