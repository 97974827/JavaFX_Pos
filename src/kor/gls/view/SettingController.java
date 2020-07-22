package kor.gls.view;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import kor.gls.Main;
import kor.gls.util.CommonUtil;
import kor.gls.util.HttpUtil;
import kor.gls.util.WeatherUtil;

public class SettingController {
	
	@FXML
	private Label lbl_top_self;
	
	@FXML
	private Label lbl_top_air;
	
	@FXML
	private Label lbl_top_mate;
	
	@FXML
	private ImageView image_top_weather;
	
	@FXML
	private Label lbl_weather_str;
	
	@FXML
	private Label lbl_weather_temp;
	
	@FXML
	private Label lbl_current_clock;
	
	@FXML
	private ImageView image_small_setting_btn;
	
	@FXML
	private ImageView image_small_real_btn;
	
	@FXML
	private Pane pane_setting;
	
	@FXML
	private GridPane gridpane_device_count; // ��� ����
	
	@FXML
	private GridPane gridpane_basic_config; // �⺻ ����
	
	@FXML
	private GridPane gridpane_other_config; // ��Ÿ ����
	
	@FXML
	private ChoiceBox<Integer> choice_self_count;
	
	@FXML
	private ChoiceBox<Integer> choice_air_count;
	
	@FXML
	private ChoiceBox<Integer> choice_mate_count;
	
	@FXML
	private ChoiceBox<Integer> choice_charger_count;
	
	@FXML
	private ChoiceBox<Integer> choice_touch_count;
	
	@FXML
	private ChoiceBox<Integer> choice_coin_count;
	
	@FXML
	private ChoiceBox<Integer> choice_bill_count;
	
	@FXML
	private ChoiceBox<Integer> choice_kiosk_count;
	
	@FXML
	private ChoiceBox<Integer> choice_reader_count;
	
	@FXML
	private ChoiceBox<Integer> choice_garage_count;
	
	@FXML
	private TextField textfield_admin_id;
	
	@FXML
	private TextField textfield_admin_pw;
	
	@FXML
	private TextField textfield_shop_id; // ����ID = ������ ID
	
	@FXML
	private TextField textfield_shop_name;
	
	@FXML
	private TextField textfield_shop_tel;
	
	@FXML
	private TextField textfield_shop_ceo;
	
	@FXML
	private TextField textfield_shop_addr;
	
	@FXML
	private TextField textfield_shop_business_num;
	
	@FXML
	private CheckBox checkbox_seller_pw_enable_state; // �����ڷ� ��ȣ���
	
	@FXML
	private CheckBox checkbox_seller_main_state; // ����� ǥ��
	
	@FXML
	private ChoiceBox<String> choicebox_weather_area; // ���� ����
	
	@FXML
	private TextField textfield_weather_url_zone; // ���� URL 
	
	@FXML
	private CheckBox checkbox_member_bonus; // ȸ������� ���
	
	@FXML
	private Label lbl_pos_version; // ���� ����
	
	@FXML
	private Label lbl_dc_version; // ������ ������ġ ����
	
	@FXML
	private TextField textfield_master_card_num;
	
	@FXML
	private Label lbl_manager_no;
	
	@FXML
	private Button btn_setting_back;
	
	@FXML
	private Button btn_setting_save_and_exit; // ���� �� ����
	
	public boolean CLOCK_STATE = true; // ���� �ð� ���� ���� 
	
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	WeatherUtil weather = new WeatherUtil();
	
	public String url = common.getUrl();
	
	
	@FXML
	private Main mainApp;
	
	// JavaFX �� �÷��� Ŭ������ �߰�
	private ObservableList<Integer> observablelist_self_count = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
	private ObservableList<Integer> observablelist_air_count = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
	private ObservableList<Integer> observablelist_mate_count = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
	private ObservableList<Integer> observablelist_charger_count = FXCollections.observableArrayList(0,1,2,3,4,5);
	private ObservableList<Integer> observablelist_touch_count = FXCollections.observableArrayList(0,1,2,3,4,5);
	private ObservableList<Integer> observablelist_coin_count = FXCollections.observableArrayList(0,1,2,3,4,5);
	private ObservableList<Integer> observablelist_bill_count = FXCollections.observableArrayList(0,1,2,3,4,5);
	private ObservableList<Integer> observablelist_kiosk_count = FXCollections.observableArrayList(0,1,2,3,4,5);
	private ObservableList<Integer> observablelist_reader_count = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
	private ObservableList<Integer> observablelist_garage_count = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
	private ObservableList<String> observablelist_weather_area = FXCollections.observableArrayList("����", "��õ", "����", "����", "����", "��õ", "����", "����", "û��", "����", "����", "����", "����", "����", "����", "����", "����", "�뱸", "�ȵ�", "����", "�λ�", "���", "â��", "����", "������");
	
	public SettingController() {
	}
	
	@FXML
	private void initialize() {
		
		String req = "";
		String request = "";
		String data_connect = "";
		
		try {
			File file_auth = new File(common.PROJECT_PATH + "/sample/version.data");
			FileReader reader = new FileReader(file_auth);
			int file_cnt;
			String encode_str = ""; 
			
			while((file_cnt = reader.read()) != -1) {
				encode_str += (char) file_cnt;
			}
			
			reader.close();
			lbl_pos_version.setText(encode_str);
			
		} catch(Exception e) {
			System.out.println("���� ���� ���� ����");
			e.printStackTrace();
		}
		
		handlecurrentTimeStart();
		
		// ���� ����Ʈ �߰�
		choice_self_count.setItems(observablelist_self_count);
		choice_air_count.setItems(observablelist_air_count);
		choice_mate_count.setItems(observablelist_mate_count);
		choice_charger_count.setItems(observablelist_charger_count);
		choice_touch_count.setItems(observablelist_touch_count);
		choice_coin_count.setItems(observablelist_coin_count);
		choice_bill_count.setItems(observablelist_bill_count);
		choice_kiosk_count.setItems(observablelist_kiosk_count);
		choice_reader_count.setItems(observablelist_reader_count);
		choice_garage_count.setItems(observablelist_garage_count);
		choicebox_weather_area.setItems(observablelist_weather_area);
		
		
		req = "get_pos_config";
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
			// ���� �������� �ҷ����� 
			try {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
				JSONObject pos_config = (JSONObject) jsonObject.get("pos_config");
				
				String str_self = pos_config.get("self_count").toString();
				String str_air = pos_config.get("air_count").toString();
				String str_mate = pos_config.get("mate_count").toString();
				String str_charger = pos_config.get("charger_count").toString();
				String str_coin = pos_config.get("coin_count").toString();
				String str_bill = pos_config.get("bill_count").toString();
				String str_touch = pos_config.get("touch_count").toString();
				String str_kiosk = pos_config.get("kiosk_count").toString();
				String str_reader = pos_config.get("reader_count").toString(); 
				String str_garage = pos_config.get("garage_count").toString();
				
				// �⺻���� ���� �Ľ�
				String str_shop_id = pos_config.get("shop_id").toString();	// �����ڷα��� ID
				String str_shop_pw = pos_config.get("shop_pw").toString();
				String str_shop_no = pos_config.get("shop_no").toString();	// ������ ID = ���� ID
				String str_shop_name = pos_config.get("shop_name").toString();
				String str_shop_tel = pos_config.get("shop_tel").toString();
				String str_shop_ceo = pos_config.get("ceo").toString();
				String str_shop_addr = pos_config.get("addr").toString();
				String str_shop_business_num = pos_config.get("business_number").toString();
				
				String str_seller_pw_enable = pos_config.get("encry").toString();    // �����ڷ� ��ȣ�ÿ�
				String str_seller_enable = pos_config.get("list_enable").toString(); // ���� �� ǥ�� ���� 
				String str_weather_area = pos_config.get("weather_area").toString();
				String weather_url_zone = pos_config.get("weather_url").toString();
				String str_master_card = pos_config.get("master_card_num").toString();
				String str_manager_no = pos_config.get("manager_no").toString();
				String str_set_vip = pos_config.get("set_vip").toString();
				String str_dc_version = pos_config.get("dc_version").toString();
				
				List<Map<String, String>> weather_data = new ArrayList<Map<String, String>>(); 
				weather_data = weather.getWeatherRssZoneParse(weather_url_zone);
				
				for(int i=0; i<weather_data.size(); i++) {
					if(i==0) {
						String weather_day = weather_data.get(i).get("day"); 
						
						String temper = weather_data.get(i).get("temp"); // �µ�
						String wfKor = weather_data.get(i).get("wfKor"); // ����
					
						lbl_weather_str.setText(wfKor);
						lbl_weather_temp.setText(temper + "��C");
						
						String str_image = weather.changeWeatherImage(wfKor);
						Image image = new Image("File:resources/" + str_image);
						image_top_weather.setImage(image);
					}
				}
				
				// ��� ������ ���� �Է� 
				lbl_top_self.setText(str_self);
				lbl_top_air.setText(str_air);
				lbl_top_mate.setText(str_mate);
				
				// üũ�ڽ� �����Է�
				choice_self_count.setValue(Integer.parseInt(str_self));
				choice_air_count.setValue(Integer.parseInt(str_air));
				choice_mate_count.setValue(Integer.parseInt(str_mate));
				choice_charger_count.setValue(Integer.parseInt(str_charger));
				choice_touch_count.setValue(Integer.parseInt(str_touch));
				choice_coin_count.setValue(Integer.parseInt(str_coin));
				choice_bill_count.setValue(Integer.parseInt(str_bill));
				choice_kiosk_count.setValue(Integer.parseInt(str_kiosk));
				choice_reader_count.setValue(Integer.parseInt(str_reader));
				choice_garage_count.setValue(Integer.parseInt(str_garage));
				choicebox_weather_area.setValue(str_weather_area);
				
				textfield_admin_id.setText(str_shop_id);
				textfield_admin_pw.setText(str_shop_pw);
				textfield_shop_id.setText(str_shop_no);
				textfield_shop_name.setText(str_shop_name);
				textfield_shop_tel.setText(str_shop_tel);
				textfield_shop_ceo.setText(str_shop_ceo);
				textfield_shop_addr.setText(str_shop_addr);
				textfield_shop_business_num.setText(str_shop_business_num);
				textfield_master_card_num.setText(str_master_card);
				lbl_manager_no.setText(str_manager_no);
				
				if(str_seller_pw_enable.equals("1")) {
					checkbox_seller_pw_enable_state.setSelected(true);
				} else{
					checkbox_seller_pw_enable_state.setSelected(false);
				}
				
				if(str_seller_enable.equals("1")) {
					checkbox_seller_main_state.setSelected(true);
				} else {
					checkbox_seller_main_state.setSelected(false);
				}
				
				if(str_set_vip.equals("1")) {
					checkbox_member_bonus.setSelected(true);
				} else {
					checkbox_member_bonus.setSelected(false);
				}
				
				textfield_weather_url_zone.setText(weather_url_zone);
				lbl_dc_version.setText(str_dc_version);
				
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
	
	// ���� �� ���� 
	@FXML
	private void handleOK() {
		String change_str_self_count = choice_self_count.getValue().toString();
		String change_str_air_count = choice_air_count.getValue().toString();
		String change_str_mate_count = choice_mate_count.getValue().toString();
		String change_str_charger_count = choice_charger_count.getValue().toString();
		String change_str_touch_count = choice_touch_count.getValue().toString();
		String change_str_coin_count = choice_coin_count.getValue().toString();
		String change_str_bill_count = choice_bill_count.getValue().toString();
		String change_str_kiosk_count = choice_kiosk_count.getValue().toString();
		String change_str_reader_count = choice_reader_count.getValue().toString();
		String change_str_garage_count = choice_garage_count.getValue().toString();
		
		String change_str_admin_id = textfield_admin_id.getText().toString();
		String change_str_admin_pw = textfield_admin_pw.getText().toString();
		String change_str_shop_id = textfield_shop_id.getText().toString();
		String change_str_shop_name = textfield_shop_name.getText().toString();
		String change_str_shop_tel = textfield_shop_tel.getText().toString();
		String change_str_shop_ceo = textfield_shop_ceo.getText().toString();
		String change_str_shop_addr = textfield_shop_addr.getText().toString();
		String change_str_shop_busniess_num = textfield_shop_business_num.getText().toString();
		
		String change_str_seller_pw_enable = "0";
		if(checkbox_seller_pw_enable_state.isSelected()) {
			change_str_seller_pw_enable = "1";
		} 
		
		String change_str_seller_main_state = "0";
		if(checkbox_seller_main_state.isSelected()) {
			change_str_seller_main_state = "1";
		}
		
		String change_str_set_vip_bonus = "0";
		if(checkbox_member_bonus.isSelected()) {
			change_str_set_vip_bonus = "1";
		}
		
		String change_str_weather_area = choicebox_weather_area.getValue().toString();
		String change_str_weather_zone = textfield_weather_url_zone.getText().toString();
		String change_str_master_card = textfield_master_card_num.getText().toString();
		String change_str_manager_no = lbl_manager_no.getText().toString();
		String change_str_pos_version = lbl_pos_version.getText().toString();
		String change_str_dc_version = lbl_dc_version.getText().toString();
		
		// ������ġ�� ���� �Ķ�������� 
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("self_count", change_str_self_count);
		params.put("air_count", change_str_air_count);
		params.put("mate_count", change_str_mate_count);
		params.put("charger_count", change_str_charger_count);
		params.put("coin_count", change_str_coin_count);
		params.put("bill_count", change_str_bill_count);
		params.put("touch_count", change_str_touch_count);
		params.put("kiosk_count", change_str_kiosk_count);
		params.put("reader_count", change_str_reader_count);
		params.put("garage_count", change_str_garage_count);
		
		params.put("shop_id", change_str_admin_id);
		params.put("shop_pw", change_str_admin_pw);
		params.put("shop_no", change_str_shop_id);
		params.put("shop_name", change_str_shop_name);
		params.put("shop_tel", change_str_shop_tel);
		params.put("ceo", change_str_shop_ceo);
		params.put("addr", change_str_shop_addr);
		params.put("business_number", change_str_shop_busniess_num);
		params.put("manager_no", change_str_manager_no);
		
		params.put("encry", change_str_seller_pw_enable);
		params.put("list_enable", change_str_seller_main_state);
		params.put("weather_area", change_str_weather_area);
		params.put("weather_url", change_str_weather_zone);
		params.put("master_card_num", change_str_master_card);
		params.put("set_vip", change_str_set_vip_bonus);
		
		String req = "set_pos_config";
		String request = url + req;
		String data_connect = http.post(request, params);
		
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
				String result = jsonObject.get("result").toString();
				
				if(result.equals("1")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Ȯ��");
					alert.setHeaderText("����");
					alert.setContentText("�����Ͽ����ϴ�.");
					alert.showAndWait();
				}
				
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/MainView.fxml"));
					AnchorPane main = (AnchorPane) loader.load();
					Main.rootLayout.setCenter(main);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			} catch(Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.WARNING);
	        	alert.setTitle("���");
	        	alert.setHeaderText("����");
	        	alert.setContentText("���忡 �����߽��ϴ�.");
	        	alert.showAndWait();
			}
		}
		
//		int DEBUGING_POINT = 12;
//		System.out.println(DEBUGING_POINT);
	}
	
	// ���� �ð� üũ 
	public void handlecurrentTimeStart() {
		CLOCK_STATE = true;
		Thread thread_clock = new Thread() {
			@Override
			public void run() {
				while(CLOCK_STATE) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
					String dTime = formatter.format(new Date()); // �ð� ���� 
					
					// ������ �� : javaFX ���ø����̼� ������� �ð��� ���ϴ� �۾��� ���� �ʵ��� �ϴ� ��
					// �ٸ� �۾� �����带 �����ؼ� ó���� 
					// �۾� �����尡 ���� UI������Ʈ�� ���� �� �� ���� ������ ������ �ʿ��� ��� , 
					// �۾� ������� UI������Ʈ ���� �ڵ带 Runnable�� ���� ���� (run �������̵�)
					Platform.runLater(() -> {
						lbl_current_clock.setText(dTime);
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
					
				}
			}
		};

		thread_clock.setDaemon(true);
		thread_clock.start();
	}
		
	// �ð� ���߱� 
	public void handlecurrentTimeStop() {
		CLOCK_STATE = false;
	}
	
	public ObservableList<Integer> getSelfData(){
		return observablelist_self_count;
	}
	
	public ObservableList<Integer> getAirData(){
		return observablelist_air_count;
	}
	
	public ObservableList<Integer> getMateData(){
		return observablelist_mate_count;
	}
	
	public ObservableList<Integer> getChargerData(){
		return observablelist_charger_count;
	}
	
	public ObservableList<Integer> getTouchData(){
		return observablelist_touch_count;
	}
	
	public ObservableList<Integer> getCoinData(){
		return observablelist_coin_count;
	}
	
	public ObservableList<Integer> getBillData(){
		return observablelist_bill_count;
	}
	
	public ObservableList<Integer> getKioskData(){
		return observablelist_kiosk_count;
	}
	
	public ObservableList<Integer> getGarageData(){
		return observablelist_garage_count;
	}
	
	public ObservableList<Integer> getReaderData(){
		return observablelist_reader_count;
	}
	
	public ObservableList<String> getWeatherAreaData(){
		return observablelist_weather_area;
	}
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
