package kor.gls.view;

import java.io.FileInputStream;
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
		ObservableList<String> observableList_coating_print = FXCollections.observableArrayList("���", "���+����");
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
		
	}
	
	// �����ҷ�����
	@FXML
	private void handleConfigLoad() {
		
	}
	
	// ���� ����
	@FXML
	private void handleSaveConfig() {
		
	}
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
