package kor.gls.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import kor.gls.Main;
import kor.gls.util.CommonUtil;
import kor.gls.util.HttpUtil;
import kor.gls.util.WeatherUtil;

public class MasterSettingController {
	
	@FXML
	private Pane pane_master;
	
	@FXML
	private GridPane gridpane_basic_config;
	
	@FXML
	private GridPane gridpane_hidden_config;
	
	@FXML
	private ChoiceBox<String> choicebox_manager_list;
	
	@FXML
	private TextField textfield_auth_data;
	
	@FXML
	private Label lbl_datacollect_connect;
	
	@FXML
	private Button btn_datacollect_connect;
	
	@FXML
	private ChoiceBox<String> choicebox_card_enable_state;
	
	@FXML
	private ChoiceBox<Integer> choicebox_card_address;
	
	@FXML
	private ChoiceBox<String> choicebox_device_addr; // 장비 번호
	                                   
	@FXML
	private ChoiceBox<String> choicebox_operater_condition; // 동작방식
	
	@FXML
	private ChoiceBox<String> choicebox_money_condition; // 결제방식

	@FXML
	private ChoiceBox<String> choicebox_coating_print; // 코딩출력
	
	@FXML
	private ChoiceBox<String> choicebox_wipping_enable; // 위핑사용
	
	@FXML
	private TextField textfield_wipping_temp; // 위핑온도 
	
	@FXML
	private Main mainApp;
	
	WeatherUtil weather = new WeatherUtil();
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	public String url = common.getUrl();
	
	Map<String, String> map_manager_list = new HashMap<String, String>(); // 공급업체 번호 , 이름 저장할 변수 
	
	@FXML
	private void initialize() {
		String req = "";
		String request = "";
		String data_connect = "";
		
		ObservableList<String> observableList_manager_name = FXCollections.observableArrayList();
		ObservableList<String> observableList_card_enable = FXCollections.observableArrayList("사용", "사용안함");
		ObservableList<Integer> observableList_card_addr = FXCollections.observableArrayList(1,2);
		
		ObservableList<String> observableList_self_list = FXCollections.observableArrayList();
		ObservableList<String> observableList_operator_condition = FXCollections.observableArrayList("정액제", "배속제");
		ObservableList<String> observableList_money_condition = FXCollections.observableArrayList("거치식", "터치식");
		ObservableList<String> observableList_coating_print = FXCollections.observableArrayList("코팅", "고압+코팅");
		ObservableList<String> observableList_wipping_enable = FXCollections.observableArrayList("사용", "사용안함");
		
		choicebox_card_enable_state.setItems(observableList_card_enable);
		choicebox_card_address.setItems(observableList_card_addr);
		choicebox_operater_condition.setItems(observableList_operator_condition);
		choicebox_money_condition.setItems(observableList_money_condition);
		choicebox_coating_print.setItems(observableList_coating_print);
		choicebox_wipping_enable.setItems(observableList_wipping_enable);
		
		req = "get_manager_list";
		request = url + req;
		data_connect = http.post(request);
		
		String str_manager_name = "";
		String str_manager_no = "";
		
		if(data_connect.equals("error")) {
			Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("경고");
        	alert.setHeaderText("연결 에러");
        	alert.setContentText("데이터 수집장치가 연결되어 있지 않습니다.");
        	alert.showAndWait();
        	return;
		} else {
			try {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
				JSONArray manager_array = (JSONArray) jsonObject.get("result");
				
				for(int i=0; i<manager_array.size(); i++) {
					JSONObject job = (JSONObject) manager_array.get(i);
					
					str_manager_name = job.get("manager_name").toString();
					str_manager_no = job.get("manager_no").toString();

					map_manager_list.put(str_manager_name, str_manager_no);
					observableList_manager_name.add(str_manager_name);
				}
				
				choicebox_manager_list.setItems(observableList_manager_name);
				
			} catch(Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.WARNING);
	        	alert.setTitle("경고");
	        	alert.setHeaderText("에러");
	        	alert.setContentText("값이 제대로 전달되지 않았습니다. 다시 시도해 주세요.");
	        	alert.showAndWait();
	        	return;
			}
		}
		
		String temp = ""; // 읽을 문자열 
		
		try {
			FileInputStream fis = new FileInputStream(common.PROJECT_PATH + "/sample/auth.data");
			
			int read_data = -1;
			while((read_data = fis.read()) != -1) {
				temp += (char) read_data;
			}
			
			// 디코딩 변환
			byte[] decoded = Base64.getDecoder().decode(temp);
			String str_auth_data = new String(decoded, StandardCharsets.UTF_8);
			//System.out.println("포스일련번호 추출성공 >> " + str_auth_data);
			fis.close();
			
			if(str_auth_data.equals("0000")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("경고");
				alert.setHeaderText("에러");
				alert.setContentText("기기 등록 후 사용 할 수 있습니다");
				alert.showAndWait();
				return;
			} 
			else {
				// 데이터 수집장치와 인증 확인 
				req = "get_master_config";
				
				Map<String, String> params = new HashMap<String, String>();
				params.put("auth_code", str_auth_data);
				
				request = url + req;
				data_connect = http.post(request, params);
				
				if (data_connect.equals("error")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("경고");
					alert.setHeaderText("연결 에러");
					alert.setContentText("데이터 수집장치가 연결되어 있지 않습니다.");
					alert.showAndWait();
					return;
				}
						
				try {
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
					JSONArray jsonarray_master_config = (JSONArray) jsonObject.get("result"); // object 키값 : result  
					
					for(int i=0; i<jsonarray_master_config.size(); i++) {
						JSONObject master_object = (JSONObject) jsonarray_master_config.get(i);
						
						String str_auth_success = master_object.get("auth").toString();
						String str_manager = master_object.get("manager_name").toString();
						String str_enable_card = master_object.get("enable_card").toString();
						String str_card_binary = master_object.get("card_binary").toString();
						// str_auth_success = "0"; // 실패 테스트 코드 
						//System.out.println("데이터 수집장치 성공여부(1:성공, 0:실패) : " + str_auth_success);
						
						if(str_auth_success.equals("0")) {
							textfield_auth_data.setText("인증되지 않았습니다.");
						} else {
							textfield_auth_data.setText(str_auth_data);
							choicebox_manager_list.setValue(str_manager);
						}
						
						if(str_enable_card.equals("0")) {
							choicebox_card_enable_state.setValue("사용안함");
						} else {
							choicebox_card_enable_state.setValue("사용");
						}
						
						if(str_card_binary.equals("2")) {
							choicebox_card_address.setValue(2);
						} else {
							choicebox_card_address.setValue(1);
						}
					}
				
				} catch(ParseException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("경고");
					alert.setHeaderText("에러");
					alert.setContentText("값이 제대로 전달 되지 않았습니다  다시 시도해 주세요.");
					alert.showAndWait();
					return;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("파일 읽기 오류");
		}
		
		
		req = "get_self_list";
		request = url + req;
		data_connect = http.post(request);
		
		if (data_connect.equals("error")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("연결 에러");
			alert.setContentText("데이터 수집장치가 연결되어 있지 않습니다.");
			alert.showAndWait();
			return;
		} else {
			try {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
				JSONArray self_array = (JSONArray) jsonObject.get("result");
				
				for(int i=0; i<self_array.size(); i++) {
					JSONObject job = (JSONObject) self_array.get(i);
					
					String str_self_addr = job.get("addr").toString();
					observableList_self_list.add(str_self_addr);
				}
				
				choicebox_device_addr.setItems(observableList_self_list);
				choicebox_device_addr.setValue("01");
			} catch(Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("경고");
				alert.setHeaderText("에러");
				alert.setContentText("값이 제대로 전달 되지 않았습니다  다시 시도해 주세요.");
				alert.showAndWait();
				return;
			
			}
		}
		
	}
	
	// 뒤로가기
	@FXML
	private void handleExit() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			AnchorPane main = (AnchorPane) loader.load();
			Main.rootLayout.setCenter(main);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 수집장치 연결 체크
	@FXML
	private void handleCheckDC() {
		boolean debug = true;
		boolean state = true;
		String str = "glstest.iptime.org";
		if(debug) {
			str = common.getUrl();
			str = str.substring(7, str.length()-7);
			System.out.println("Check url : " + str);	
		}
		
		try {
			state = InetAddress.getByName(str).isReachable(1000); // ping 체크 
			
			if(state) {
				lbl_datacollect_connect.setText("연결됨");
				lbl_datacollect_connect.setStyle("-fx-text-fill : blue");
			} else {
				lbl_datacollect_connect.setText("연결끊김");
				lbl_datacollect_connect.setStyle("-fx-text-fill : red");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 저장 후 종료
	@FXML
	private void handleOK() {
		String change_manager_name = choicebox_manager_list.getValue().toString();
		String change_manager_no = map_manager_list.get(change_manager_name);
		String change_auth_data = textfield_auth_data.getText().toString();
		String change_card_enable = choicebox_card_enable_state.getValue().toString();
		String change_card_binary = choicebox_card_address.getValue().toString();
		
		if(change_card_enable.equals("사용")) {
			change_card_enable = "1";
		} else {
			change_card_enable = "0";
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(common.PROJECT_PATH + "/sample/auth.data");
			
			byte[] encoded = Base64.getEncoder().encode(change_auth_data.getBytes());
			
			fos.write(encoded);
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("auth_code", change_auth_data);
		params.put("manager_no", change_manager_no);
		params.put("enable_card", change_card_enable);
		params.put("card_binary", change_card_binary);
		
		
		String req = "set_master_config";
		String request = url + req;
		String data_connect = http.post(request, params);
		
		if (data_connect.equals("error")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("연결 에러");
			alert.setContentText("데이터 수집장치가 연결되어 있지 않습니다.");
			alert.showAndWait();
			return;
		}
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
			String result = jsonObject.get("result").toString();
			
			if(result.equals("1")) {
				Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("확인");
	        	alert.setHeaderText("성공");
	        	alert.setContentText("저장되었습니다.");
	        	alert.showAndWait();
	        	
	        	try {
	        		FXMLLoader loader = new FXMLLoader();
	        		loader.setLocation(Main.class.getResource("view/MainView.fxml"));
	        		AnchorPane mainView = (AnchorPane) loader.load();
	    			Main.rootLayout.setCenter(mainView);
	    			MainViewController controller = loader.getController();
	    			controller.setMainApp(this.mainApp);
	    			
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        	
			} else {
				Alert alert = new Alert(AlertType.WARNING);
	        	alert.setTitle("경고");
	        	alert.setHeaderText("에러");
	        	alert.setContentText("저장에 실패했습니다.");
	        	alert.showAndWait();
			}
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("경고");
        	alert.setHeaderText("에러");
        	alert.setContentText("값이 제대로 전달 되지 않았습니다. 다시 시도해주세요");
        	alert.showAndWait();
        	return;
		}
		
	}
	
	// 설정불러오기
	@FXML
	private void handleLoadConfig() {
		String str_device_addr = choicebox_device_addr.getValue().toString();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device_addr", str_device_addr);
		
		String req = "get_hidden_config";
		String request = url + req;
		String data_connect = http.post(request, params);
		
		if (data_connect.equals("error")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("연결 에러");
			alert.setContentText("데이터 수집장치가 연결되어 있지 않습니다.");
			alert.showAndWait();
			return;
		}
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
			JSONArray hidden_array = (JSONArray) jsonObject.get("result");
			
			for(int i=0; i<hidden_array.size(); i++) {	
				JSONObject job = (JSONObject) hidden_array.get(i);
				
				String str_enable_type = job.get("enable_type").toString();			// 동작방식
				String str_pay_type = job.get("pay_type").toString();				// 결제방식
				String str_coating_type = job.get("coating_type").toString();       // 코팅출력
				String str_wipping_enable = job.get("wipping_enable").toString();	// 위핑사용
				String str_wipping_temp = job.get("wipping_temp").toString();		// 위핑온도 
				
				if(str_enable_type.equals("1")) {
					str_enable_type = "배속제";
				} else {
					str_enable_type = "정액제";
				}
				
				if(str_pay_type.equals("1")) {
					str_pay_type = "거치식";
				} else {
					str_pay_type = "터치식";
				}
				
				if(str_coating_type.equals("1")) {
					str_coating_type = "고압+코팅";
				} else {
					str_coating_type = "코팅";
				}
				
				if(str_wipping_enable.equals("1")) {
					str_wipping_enable = "사용";
				} else {
					str_wipping_enable = "사용안함";
				}
				
				choicebox_device_addr.setValue(str_device_addr);
				choicebox_operater_condition.setValue(str_enable_type);
				choicebox_money_condition.setValue(str_pay_type);
				choicebox_coating_print.setValue(str_coating_type);
				choicebox_wipping_enable.setValue(str_wipping_enable);
				textfield_wipping_temp.setText(str_wipping_temp);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("경고");
        	alert.setHeaderText("에러");
        	alert.setContentText("값이 제대로 전달 되지 않았습니다. 다시 시도해주세요");
        	alert.showAndWait();
        	return;
		}
		
	
	}
	
	// 설정 저장
	@FXML
	private void handleSaveConfig() {
		String change_device_addr = choicebox_device_addr.getValue();
		String change_enable_type = choicebox_operater_condition.getValue();
		String change_pay_type = choicebox_money_condition.getValue();
		String change_coating_type = choicebox_coating_print.getValue();
		String change_wipping_enable = choicebox_wipping_enable.getValue();
		String change_wipping_temp = textfield_wipping_temp.getText();
		
		if (change_device_addr.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("경고");
         	alert.setHeaderText("값 에러");
         	alert.setContentText("설정할 장비 번호를 선택해주세요");
         	alert.showAndWait();
         	return;
		}
		
		if (change_enable_type.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("경고");
         	alert.setHeaderText("값 에러");
         	alert.setContentText("동작 방식을 선택해주세요");
         	alert.showAndWait();
         	return;
		}
		
		if (change_pay_type.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("경고");
         	alert.setHeaderText("값 에러");
         	alert.setContentText("결제방식을 선택해주세요");
         	alert.showAndWait();
         	return;
		}
		
		if (change_coating_type.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("경고");
         	alert.setHeaderText("값 에러");
         	alert.setContentText("코팅출력을 선택해주세요");
         	alert.showAndWait();
         	return;
		}
		
		if (change_wipping_enable.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("경고");
         	alert.setHeaderText("값 에러");
         	alert.setContentText("위핑사용을 선택해주세요");
         	alert.showAndWait();
         	return;
		}
		
		if (change_wipping_temp.equals("") || Integer.parseInt(change_wipping_temp) <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("경고");
         	alert.setHeaderText("값 에러");
         	alert.setContentText("위핑온도를 올바르게 입력해주세요");
         	alert.showAndWait();
         	return;
		}
		
		
		if(change_enable_type.equals("배속제")) {
			change_enable_type = "1";
		} else {
			change_enable_type = "0";
		}
		
		if(change_pay_type.equals("거치식")) {
			change_pay_type = "1";
		} else {
			change_pay_type = "0";
		}
		
		if(change_coating_type.equals("고압+코팅")) {
			change_coating_type = "1";
		} else {
			change_coating_type = "0";
		}
		
		if(change_wipping_enable.equals("사용")) {
			change_wipping_enable = "1";
		} else {
			change_wipping_enable = "0";
		}
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device_addr", change_device_addr);
		params.put("enable_type", change_enable_type);
		params.put("pay_type", change_pay_type);
		params.put("coating_type", change_coating_type);
		params.put("wipping_enable", change_wipping_enable);
		params.put("wipping_temp", change_wipping_temp);
				
		String req = "set_hidden_config";
		String request = url + req;
		String data_connect = http.post(request, params);
		
		if (data_connect.equals("error")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("연결 에러");
			alert.setContentText("데이터 수집장치가 연결되어 있지 않습니다.");
			alert.showAndWait();
			return;
		}
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
			String result = jsonObject.get("result").toString();
			
			//if(result.equals("1")) {				
			Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("확인");
        	alert.setHeaderText("성공");
        	alert.setContentText("기기설정값을 변경했습니다.");
        	alert.showAndWait();
        	return;
			
//			} else {
//				Alert alert = new Alert(AlertType.WARNING);
//	        	alert.setTitle("경고");
//	        	alert.setHeaderText("실패");
//	        	alert.setContentText("저장에 실패했습니다.");
//	        	alert.showAndWait();
//	        	return;
//			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("경고");
        	alert.setHeaderText("에러");
        	alert.setContentText("값이 제대로 전달 되지 않았습니다. 다시 시도해주세요");
        	alert.showAndWait();
        	return;
		}
		
	}
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
