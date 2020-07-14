package kor.gls.util;

public class CommonUtil {
	
	private String url;
	
	public static String PROJECT_PATH = System.getProperty("user.dir");
	
	public String getUrl() {
		return url;
	}
	
	public CommonUtil() {
		//System.out.println("프로젝트 경로 : " + PROJECT_PATH);
	}
	
}
