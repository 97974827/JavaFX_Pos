package kor.gls.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtil {
	
	public boolean DEBUG = true; // 디버그 모드

	public static String PROJECT_PATH = System.getProperty("user.dir"); // 프로젝트 경로 
	
		
	// URL 요청 
	public String getUrl() {
		String url = "http://192.168.0.200:5000/";
		
		if (DEBUG) {
			url = "http://glstest.iptime.org:50000/";
//			url = "http://glsd3.iptime.org:50000/";
		}
	
		return url;
	}
	
	// 타임스탬프 -> Str Date
	public String TimestampToDateString(String time_stamp) {
		
		long long_time = Long.parseLong(time_stamp);
		Date date = new Date(long_time*1000L);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		simple.setTimeZone(TimeZone.getTimeZone("GMT+9"));
		String str_date = simple.format(date);
		
		return str_date;
	}
	
	
	// TestCode
	public static void main(String[] args) {
		CommonUtil c = new CommonUtil();
		c.TimestampToDateString("1594788967");
	}
	
}
