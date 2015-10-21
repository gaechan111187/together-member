package score;

import java.util.ArrayList;
import java.util.List;


public interface ScoreService {
	// DML
	public String input(ScoreVO score);
	
	// DQL
	public List<ScoreVO> getList();
	public ScoreVO searchByUserid(String userid);
	public ArrayList<ScoreVO> searchByName(String name);
	public ArrayList<ScoreVO> descByTotal();			// 성적을 내림차순으로 정렬(300,270,230,...)
	public ArrayList<ScoreVO> ascByName();			// 성적을 이름에따라 오름차순으로 정렬(ㄱ,ㄴ,ㄷ,...)
	public void ascByTotal();
	public ArrayList<ScoreVO> searchByScore(String subject, int score);
	/*public int compare(ScoreVO front, ScoreVO back);
	public int compareString(ScoreVO front, ScoreVO back);*/
}
