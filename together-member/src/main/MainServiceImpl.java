package main;

import java.util.ArrayList;
import java.util.List;



public class MainServiceImpl implements MainService {
	private static MainService service = new MainServiceImpl();
	
	public static MainService getService() {
		return service;
	}
	MainDAO dao = new MainDAO();
	
	@Override
	public List<MainVO> getFriends() {
		return dao.selectMyFriends();
	}
}