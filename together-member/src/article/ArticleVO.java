package article;

public class ArticleVO {
	private int seq ; //시퀀스 번호
	private String title;    //제목
	private String content;   //컨텐츠
	private String regdate;  //등록일
	private String userid;  //글쓴이의 ID
	
	
	public int getSeq() {
		return seq;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getRegdate() {
		return regdate;
	}
	public String getUserid() {
		return userid;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
