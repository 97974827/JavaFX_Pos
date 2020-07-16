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
	
	// RSS �ּ� 
	private String rssFeed = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=%s";
	
	
	// �ش� Zone ������ �־� �Ϸ�ġ ���� ���� ��������  
	public List<Map<String, String>> getWeatherRssZoneParse(String str_zone) {
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		try {
			SAXBuilder parser = new SAXBuilder();				// Parser ��ü����
			parser.setIgnoringElementContentWhitespace(true);	
			
			String url = String.format(rssFeed, str_zone);
			Document doc = parser.build(url);					// doc = XML���� ��ü ��Ÿ�� 
			Element root = doc.getRootElement();				// �ϳ��� �±׸� ��Ÿ�� 
			
			Element channel = root.getChild("channel");			// ���� �±� 
			Element item = channel.getChild("item");
			Element description = item.getChild("description");
			Element body = description.getChild("body");
			
			List<Element> list = body.getChildren("data");
			
			for(int i=0; i<list.size(); i++) {
				Element el = (Element) list.get(i);
				
				String seq = el.getAttributeValue("seq"); 		// ����
				if(seq.equals("8")) break;  // 24�ð� �����͸� ���� 
				
				Map<String, String> data = new LinkedHashMap<String, String>();
				
				String wf_kor = el.getAttributeValue("wfKor");	// ����
				String temp = el.getAttributeValue("temp");		// �µ�
				
				data.put("seq", seq);
				data.put("wfKor", wf_kor);
				data.put("temp", temp);
				
				for(Element dataChild : el.getChildren()) {
					data.put(dataChild.getName(), dataChild.getTextTrim());
				}
				
				//System.out.println(data);
				result.add(data);
			}

			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	// �������ڿ� ������ �̹��� ��ȯ�ϱ� 
	public String changeWeatherImage(String wfKor) {
		String str_image_url = "";
		
		switch(wfKor) {
		case "����":
			str_image_url = "sunny.png";
			break;
		
		case "�帲":
			str_image_url = "cloud.png";
			break;
		
		case "���� ����":
			str_image_url = "small_cloud.png";
			break;
			
		case "���� ����":
			str_image_url = "many_cloud.png";
			break;
		
		case "��":
		case "�ҳ���":
		case "�帮�� ��":
			str_image_url = "rainning.png";
			break;
			
		case "�������� ��":
			str_image_url = "moment_rainning.png";
		break;
		
		case "�������� ��/��":
			str_image_url = "moment_ranning_snow.png";
		break;
		
		case "�������� ��/��":
			str_image_url = "moment_snow_rainning.png";
		break;
			
		case "�帮�� ��/��":
			str_image_url = "rainning_snow.png";
		break;
		
		case "��/��":
		case "�帮�� ��/��":
			str_image_url = "snow_ranning.png";
		break;
		
		case "�帮�� ��":
		case "��":
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
