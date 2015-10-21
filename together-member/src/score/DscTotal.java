package score;

import java.util.Comparator;

/**
 * @file_name : DscTotal.java 
 * @author    : chanhok61@daum.net
 * @date      : 2015. 10. 1.
 * @story     : 총점 내림차순
 */
public class DscTotal implements Comparator<ScoreVO>{

	@Override
	public int compare(ScoreVO front, ScoreVO back) {
		return (front.getTotal() > back.getTotal()) ? -1 :
			front.getTotal() < back.getTotal() ? 1 : 0; // 여기서 숫자는 위치값
		/**
		 * 삼항연산자
		 * (컨디션) ? true : false:
		 */
	}
	

}
