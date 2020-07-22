package kor.gls.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class WeatherUtil {
	
	// RSS 주소  - 금일 ~ 모레까지 가져올수 날씨 가져올수 있음 
	private String rssFeed = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=%s";
	
	
	// 해당 Zone 정보를 주어 1일 날씨 정보 가져오기  
	public List<Map<String, String>> getWeatherRssZoneParse(String str_zone) {
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		try {
			SAXBuilder parser = new SAXBuilder();				// Parser 객체생성
			parser.setIgnoringElementContentWhitespace(true);	
			
			String url = String.format(rssFeed, str_zone);
			Document doc = parser.build(url);					// doc = XML문서 전체 나타냄 
			Element root = doc.getRootElement();				// 하나의 태그를 나타냄 
			
			Element channel = root.getChild("channel");			// 하위 태그 
			Element item = channel.getChild("item");
			Element description = item.getChild("description");
			Element body = description.getChild("body");
			
			List<Element> list = body.getChildren("data");
			
			for(int i=0; i<list.size(); i++) {
				Element el = (Element) list.get(i);
				
				String seq = el.getAttributeValue("seq"); 		// 순번
				//if(seq.equals("8")) break;  // 24시간 데이터만 추출 
				
				Map<String, String> data = new LinkedHashMap<String, String>();
				
				String wf_kor = el.getAttributeValue("wfKor");	// 날씨
				String temp = el.getAttributeValue("temp");		// 온도
				
				data.put("seq", seq);
				data.put("wfKor", wf_kor);
				data.put("temp", temp);
				
				// data요소의 자식요소들을 하나씩 꺼내서 저장 : (요소이름, 요소의 테스트노드)
				for(Element dataChild : el.getChildren()) {
					data.put(dataChild.getName(), dataChild.getTextTrim());
	    		    //<hour>3</hour>
                    //data.put("hour","3");
				}
				
//				System.out.println(data);
				result.add(data);
			}

			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	// 날씨문자열 값으로 이미지 변환하기 
	public String changeWeatherImage(String wfKor) {
		String str_image_url = "";
		
		switch(wfKor) {
		case "맑음":
			str_image_url = "sunny.png";
			break;
		
		case "흐림":
			str_image_url = "cloud.png";
			break;
		
		case "구름 조금":
			str_image_url = "small_cloud.png";
			break;
			
		case "구름 많음":
			str_image_url = "many_cloud.png";
			break;
		
		case "비":
		case "소나기":
		case "흐리고 비":
			str_image_url = "rainning.png";
			break;
			
		case "구름많고 비":
			str_image_url = "moment_rainning.png";
		break;
		
		case "구름많고 비/눈":
			str_image_url = "moment_ranning_snow.png";
		break;
		
		case "구름많고 눈/비":
			str_image_url = "moment_snow_rainning.png";
		break;
			
		case "흐리고 비/눈":
			str_image_url = "rainning_snow.png";
		break;
		
		case "눈/비":
		case "흐리고 눈/비":
			str_image_url = "snow_ranning.png";
		break;
		
		case "흐리고 눈":
		case "눈":
			str_image_url = "snow.png";
		break;
		
		default:
			str_image_url = "sunny.png";
			break;
		}
		
		return str_image_url;
	}
	
	public static void main(String[] args) {
		WeatherUtil weather = new WeatherUtil();
		weather.getWeatherRssZoneParse("2920062400");
	}
}	
