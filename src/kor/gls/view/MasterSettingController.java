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
	private ChoiceBox<String> choicebox_device_addr; // ��� ��ȣ
	                                   
	@FXML
	private ChoiceBox<String> choicebox_operater_condition; // ���۹��
	
	@FXML
	private ChoiceBox<String> choicebox_money_condition; // �������

	@FXML
	private ChoiceBox<String> choicebox_coating_print; // �ڵ����
	
	@FXML
	private ChoiceBox<String> choicebox_wipping_enable; // ���λ��
	
	@FXML
	private TextField textfield_wipping_temp; // ���οµ� 
	
	@FXML
	private Main mainApp;
	
	WeatherUtil weather = new WeatherUtil();
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	public String url = common.getUrl();
	
	Map<String, String> map_manager_list = new HashMap<String, String>(); // ���޾�ü ��ȣ , �̸� ������ ���� 
	
	@FXML
	private void initialize() {
		String req = "";
		String request = "";
		String data_connect = "";
		
		ObservableList<String> observableList_manager_name = FXCollections.observableArrayList();
		ObservableList<String> observableList_card_enable = FXCollections.observableArrayList("���", "������");
		ObservableList<Integer> observableList_card_addr = FXCollections.observableArrayList(1,2);
		
		ObservableList<String> observableList_self_list = FXCollections.observableArrayList();
		ObservableList<String> observableList_operator_condition = FXCollections.observableArrayList("������", "�����");
		ObservableList<String> observableList_money_condition = FXCollections.observableArrayList("��ġ��", "��ġ��");
		ObservableList<String> observableList_coating_print = FXCollections.observableArrayList("����", "���+����");
		ObservableList<String> observableList_wipping_enable = FXCollections.observableArrayList("���", "������");
		
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
        	alert.setTitle("���");
        	alert.setHeaderText("���� ����");
        	alert.setContentText("������ ������ġ�� ����Ǿ� ���� �ʽ��ϴ�.");
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
	        	alert.setTitle("���");
	        	alert.setHeaderText("����");
	        	alert.setContentText("���� ����� ���޵��� �ʾҽ��ϴ�. �ٽ� �õ��� �ּ���.");
	        	alert.showAndWait();
	        	return;
			}
		}
		
		String temp = ""; // ���� ���ڿ� 
		
		try {
			FileInputStream fis = new FileInputStream(common.PROJECT_PATH + "/sample/auth.data");
			
			int read_data = -1;
			while((read_data = fis.read()) != -1) {
				temp += (char) read_data;
			}
			
			// ���ڵ� ��ȯ
			byte[] decoded = Base64.getDecoder().decode(temp);
			String str_auth_data = new String(decoded, StandardCharsets.UTF_8);
			//System.out.println("�����Ϸù�ȣ ���⼺�� >> " + str_auth_data);
			fis.close();
			
			if(str_auth_data.equals("0000")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���");
				alert.setHeaderText("����");
				alert.setContentText("��� ��� �� ��� �� �� �ֽ��ϴ�");
				alert.showAndWait();
				return;
			} 
			else {
				// ������ ������ġ�� ���� Ȯ�� 
				req = "get_master_config";
				
				Map<String, String> params = new HashMap<String, String>();
				params.put("auth_code", str_auth_data);
				
				request = url + req;
				data_connect = http.post(request, params);
				
				if (data_connect.equals("error")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���");
					alert.setHeaderText("���� ����");
					alert.setContentText("������ ������ġ�� ����Ǿ� ���� �ʽ��ϴ�.");
					alert.showAndWait();
					return;
				}
						
				try {
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
					JSONArray jsonarray_master_config = (JSONArray) jsonObject.get("result"); // object Ű�� : result  
					
					for(int i=0; i<jsonarray_master_config.size(); i++) {
						JSONObject master_object = (JSONObject) jsonarray_master_config.get(i);
						
						String str_auth_success = master_object.get("auth").toString();
						String str_manager = master_object.get("manager_name").toString();
						String str_enable_card = master_object.get("enable_card").toString();
						String str_card_binary = master_object.get("card_binary").toString();
						// str_auth_success = "0"; // ���� �׽�Ʈ �ڵ� 
						//System.out.println("������ ������ġ ��������(1:����, 0:����) : " + str_auth_success);
						
						if(str_auth_success.equals("0")) {
							textfield_auth_data.setText("�������� �ʾҽ��ϴ�.");
						} else {
							textfield_auth_data.setText(str_auth_data);
							choicebox_manager_list.setValue(str_manager);
						}
						
						if(str_enable_card.equals("0")) {
							choicebox_card_enable_state.setValue("������");
						} else {
							choicebox_card_enable_state.setValue("���");
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
					alert.setTitle("���");
					alert.setHeaderText("����");
					alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�  �ٽ� �õ��� �ּ���.");
					alert.showAndWait();
					return;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("���� �б� ����");
		}
		
		
		req = "get_self_list";
		request = url + req;
		data_connect = http.post(request);
		
		if (data_connect.equals("error")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���");
			alert.setHeaderText("���� ����");
			alert.setContentText("������ ������ġ�� ����Ǿ� ���� �ʽ��ϴ�.");
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
				alert.setTitle("���");
				alert.setHeaderText("����");
				alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�  �ٽ� �õ��� �ּ���.");
				alert.showAndWait();
				return;
			
			}
		}
		
	}
	
	// �ڷΰ���
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
	
	// ������ġ ���� üũ
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
			state = InetAddress.getByName(str).isReachable(1000); // ping üũ 
			
			if(state) {
				lbl_datacollect_connect.setText("�����");
				lbl_datacollect_connect.setStyle("-fx-text-fill : blue");
			} else {
				lbl_datacollect_connect.setText("�������");
				lbl_datacollect_connect.setStyle("-fx-text-fill : red");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// ���� �� ����
	@FXML
	private void handleOK() {
		String change_manager_name = choicebox_manager_list.getValue().toString();
		String change_manager_no = map_manager_list.get(change_manager_name);
		String change_auth_data = textfield_auth_data.getText().toString();
		String change_card_enable = choicebox_card_enable_state.getValue().toString();
		String change_card_binary = choicebox_card_address.getValue().toString();
		
		if(change_card_enable.equals("���")) {
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
			alert.setTitle("���");
			alert.setHeaderText("���� ����");
			alert.setContentText("������ ������ġ�� ����Ǿ� ���� �ʽ��ϴ�.");
			alert.showAndWait();
			return;
		}
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
			String result = jsonObject.get("result").toString();
			
			if(result.equals("1")) {
				Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Ȯ��");
	        	alert.setHeaderText("����");
	        	alert.setContentText("����Ǿ����ϴ�.");
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
	        	alert.setTitle("���");
	        	alert.setHeaderText("����");
	        	alert.setContentText("���忡 �����߽��ϴ�.");
	        	alert.showAndWait();
			}
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("���");
        	alert.setHeaderText("����");
        	alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�. �ٽ� �õ����ּ���");
        	alert.showAndWait();
        	return;
		}
		
	}
	
	// �����ҷ�����
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
			alert.setTitle("���");
			alert.setHeaderText("���� ����");
			alert.setContentText("������ ������ġ�� ����Ǿ� ���� �ʽ��ϴ�.");
			alert.showAndWait();
			return;
		}
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
			JSONArray hidden_array = (JSONArray) jsonObject.get("result");
			
			for(int i=0; i<hidden_array.size(); i++) {	
				JSONObject job = (JSONObject) hidden_array.get(i);
				
				String str_enable_type = job.get("enable_type").toString();			// ���۹��
				String str_pay_type = job.get("pay_type").toString();				// �������
				String str_coating_type = job.get("coating_type").toString();       // �������
				String str_wipping_enable = job.get("wipping_enable").toString();	// ���λ��
				String str_wipping_temp = job.get("wipping_temp").toString();		// ���οµ� 
				
				if(str_enable_type.equals("1")) {
					str_enable_type = "�����";
				} else {
					str_enable_type = "������";
				}
				
				if(str_pay_type.equals("1")) {
					str_pay_type = "��ġ��";
				} else {
					str_pay_type = "��ġ��";
				}
				
				if(str_coating_type.equals("1")) {
					str_coating_type = "���+����";
				} else {
					str_coating_type = "����";
				}
				
				if(str_wipping_enable.equals("1")) {
					str_wipping_enable = "���";
				} else {
					str_wipping_enable = "������";
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
        	alert.setTitle("���");
        	alert.setHeaderText("����");
        	alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�. �ٽ� �õ����ּ���");
        	alert.showAndWait();
        	return;
		}
		
	
	}
	
	// ���� ����
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
         	alert.setTitle("���");
         	alert.setHeaderText("�� ����");
         	alert.setContentText("������ ��� ��ȣ�� �������ּ���");
         	alert.showAndWait();
         	return;
		}
		
		if (change_enable_type.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("���");
         	alert.setHeaderText("�� ����");
         	alert.setContentText("���� ����� �������ּ���");
         	alert.showAndWait();
         	return;
		}
		
		if (change_pay_type.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("���");
         	alert.setHeaderText("�� ����");
         	alert.setContentText("��������� �������ּ���");
         	alert.showAndWait();
         	return;
		}
		
		if (change_coating_type.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("���");
         	alert.setHeaderText("�� ����");
         	alert.setContentText("��������� �������ּ���");
         	alert.showAndWait();
         	return;
		}
		
		if (change_wipping_enable.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("���");
         	alert.setHeaderText("�� ����");
         	alert.setContentText("���λ���� �������ּ���");
         	alert.showAndWait();
         	return;
		}
		
		if (change_wipping_temp.equals("") || Integer.parseInt(change_wipping_temp) <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
         	alert.setTitle("���");
         	alert.setHeaderText("�� ����");
         	alert.setContentText("���οµ��� �ùٸ��� �Է����ּ���");
         	alert.showAndWait();
         	return;
		}
		
		
		if(change_enable_type.equals("�����")) {
			change_enable_type = "1";
		} else {
			change_enable_type = "0";
		}
		
		if(change_pay_type.equals("��ġ��")) {
			change_pay_type = "1";
		} else {
			change_pay_type = "0";
		}
		
		if(change_coating_type.equals("���+����")) {
			change_coating_type = "1";
		} else {
			change_coating_type = "0";
		}
		
		if(change_wipping_enable.equals("���")) {
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
			alert.setTitle("���");
			alert.setHeaderText("���� ����");
			alert.setContentText("������ ������ġ�� ����Ǿ� ���� �ʽ��ϴ�.");
			alert.showAndWait();
			return;
		}
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
			String result = jsonObject.get("result").toString();
			
			//if(result.equals("1")) {				
			Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Ȯ��");
        	alert.setHeaderText("����");
        	alert.setContentText("��⼳������ �����߽��ϴ�.");
        	alert.showAndWait();
        	return;
			
//			} else {
//				Alert alert = new Alert(AlertType.WARNING);
//	        	alert.setTitle("���");
//	        	alert.setHeaderText("����");
//	        	alert.setContentText("���忡 �����߽��ϴ�.");
//	        	alert.showAndWait();
//	        	return;
//			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("���");
        	alert.setHeaderText("����");
        	alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�. �ٽ� �õ����ּ���");
        	alert.showAndWait();
        	return;
		}
		
	}
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
