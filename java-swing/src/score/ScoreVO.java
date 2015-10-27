package score;

import java.io.Serializable;

import global.SQL;

public class ScoreVO implements SQL, Serializable{
	private static final long serialVersionUID = 1L;
	
	private int score_seq;
	private int java;
	private int jsp;
	private int html;
	private int javascript;
	private int oracle;
	private int spring;
	private String userid;
	
	public int getScore_seq() {
		return score_seq;
	}
	public int getJava() {
		return java;
	}

	public int getJsp() {
		return jsp;
	}

	public int getHtml() {
		return html;
	}

	public int getJavascript() {
		return javascript;
	}

	public int getOracle() {
		return oracle;
	}

	public int getSpring() {
		return spring;
	}

	public String getUserid() {
		return userid;
	}

	public void setScore_seq(int score_seq) {
		this.score_seq = score_seq;
	}
	
	public void setJava(int java) {
		this.java = java;
	}

	public void setJsp(int jsp) {
		this.jsp = jsp;
	}

	public void setHtml(int html) {
		this.html = html;
	}

	public void setJavascript(int javascript) {
		this.javascript = javascript;
	}

	public void setOracle(int oracle) {
		this.oracle = oracle;
	}

	public void setSpring(int spring) {
		this.spring = spring;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public ScoreVO() {
	}
	
	public ScoreVO(int java, int jsp, int html, int javascript, int oracle, int spring, String userid) {
		this.java = java;
		this.jsp = jsp;
		this.html = html;
		this.javascript = javascript;
		this.oracle = oracle;
		this.spring = spring;
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "성적표 [번호=" + score_seq + ", java=" + java + ", jsp=" + jsp + ", html=" + html + ", javascript="
				+ javascript + ", oracle=" + oracle + ", spring=" + spring + ", userid=" + userid + "]";
	}

	@Override
	public String insert() {
		String query = "insert into score "
				+ "(score_seq, java, jsp, html, javascript, oracle, spring, userid) "
				+ "values(score_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
		/*String query = "insert into score "
				+ "(java, jsp, html, javascript, oracle, spring, userid) "
				+ "values(?,?,?,?,?,?,?)";*/
		return query;
	}

	@Override
	public String update() {
		String query = "update score "
				+"set java=?, jsp=?, html=?, javascript=?, oracle=?, spring=? where userid = ?";
		return query;
	}

	@Override
	public String selectAll() {
		String query = "select * from score order by userid asc";
		return query;
	}
	
	@Override
	public String selectOneBy(String s) {
		String query = "select * from score where userid ="+make(s);
		return query;
	}
	

	public String selectSomeBy(String subject, int score) {
		String query = "select * from score where "+subject+" >= "+score;
		return query;
	}

	@Override
	public String count() {
		String query = "select count(*) as count from score";
		return query;
	}

	@Override
	public String make(String s) {
		return "'"+s+"'";
	}
	public int getTotal(){
		return java+jsp+html+javascript+oracle+spring;
	}
	@Override
	public String selectSomeBy(String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
