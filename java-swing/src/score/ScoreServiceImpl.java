package score;

import java.util.ArrayList;
import java.util.List;

public class ScoreServiceImpl implements ScoreService {
	private static ScoreService service = new ScoreServiceImpl();
	
	public static ScoreService getService() {
		return service;
	}
	ScoreDAO dao = ScoreDAO.getInstance();
	
	@Override
	public String input(ScoreVO score) {
		String temp = "";
		if (dao.insert(score)!=0) {
			temp = "점수가 등록되었습니다.";
		} else {
			temp = "실패했습니다.";
		}
		return temp;
	}

	@Override
	public List<ScoreVO> getList() {
		return dao.selectAll();
	}

	@Override
	public ScoreVO searchByUserid(String userid) {
		return (ScoreVO) dao.selectOneBy(userid);
	}

	@Override
	public ArrayList<ScoreVO> searchByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ScoreVO> descByTotal() {
		DscTotal dscTotal = new DscTotal();
		ArrayList<ScoreVO> temp = new ArrayList<ScoreVO>();
	/*	
		for (int i = 0; i < vec.size()-1; i++) {
			for (int j = 0; j < vec.size()-1-i; j++) {
				if (vec.get(j).getTotal()<vec.get(j+1).getTotal()) {
					GradeVO tempGrade = vec.get(j);
					vec.set(j, vec.get(j+1));
					vec.set(j+1, tempGrade);
				}
			}
		}	*/	
		return temp;
	}

	@Override
	public ArrayList<ScoreVO> ascByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ascByTotal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ScoreVO> searchByScore(String subject, int grade) {
		return (ArrayList<ScoreVO>) dao.selectSomeBy(subject, grade);
	}

}
