package kor.gls.view;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import kor.gls.Main;
import kor.gls.model.TodayCharge;
import kor.gls.model.TodayDevice;
import kor.gls.util.CommonUtil;
import kor.gls.util.HttpUtil;

public class MainViewController {
	
	@FXML
	private Label lbl_top_self;
	
	@FXML
	private Label lbl_top_air;
	
	@FXML
	private Label lbl_top_mate;
	
	@FXML
	private Label lbl_weather_str;  // ���� ���� (�帲, ����) 
	
	@FXML
	private Label lbl_weather_temp; // ���� �µ�
	
	@FXML
	private Label lbl_current_clock; // ����ð�
	
	@FXML
	private ImageView btn_small_setting_image; // ��� ���� ���ι�ư
	
	@FXML 
	private ImageView btn_small_realtime_image; // ��� ���� �ǽð� ��ư 
	
	@FXML
	private Pane pane_main; // �̹��� ��ư ���� �ǳ�
	
	@FXML
	private ImageView btn_show_info_image; // �ϴ� ���� �����ֱ� 
	
	@FXML
	private ImageView btn_hide_info_image; // �ϴ� ���� �����
	
	@FXML
	private ImageView btn_setting_image;
	
	@FXML
	private ImageView btn_weather_image; 
	
	@FXML
	private ImageView btn_usbcharge_image;
	
	@FXML
	private ImageView btn_seller_image;
	
	@FXML
	private ImageView btn_device_config_image;
	
	@FXML
	private ImageView btn_member_image;
	
	@FXML
	private ImageView btn_blackcard_image;
	
	@FXML
	private ImageView btn_device_info_image;
	
	@FXML
	private ImageView btn_addon_image;
	
	@FXML
	private ImageView btn_member_list_image;
	
	@FXML
	private ImageView btn_card_list_image;
	
	@FXML
	private ImageView btn_realtime_image;
	
	@FXML
	private Pane pane_tail_info; // ��볻�� + ���� ����� ��ü �ǳ�
	
	@FXML
	private Pane pane_device_wash; // ������� �ǳ�
	
	@FXML
	private Pane pane_charge; // ������� �ǳ�
	
	@FXML
	private ScrollPane scroll_pane_device; // ������� ��ũ�� �ǳ�
	
	@FXML
	private ScrollPane scroll_pane_charge; // ������� ��ũ�� �ǳ�
	
	@FXML
	private TableView<TodayDevice> table_device_list; // ������� ���̺�
	
	@FXML
	private TableView<TodayCharge> table_charge_list; // ������� ���̺� 
	
	@FXML
	private Button btn_device_wash_info; // ������� ��볻�� ��ư 
	
	@FXML
	private Button btn_charge_wash_info; // ������� ��볻�� ��ư 
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_wash_no; // ������� ��ȣ �÷�
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_end_time; // ������� �Ͻ� �÷�
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_wash_addr; 
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_wash_name; 
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_use_cash; 
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_use_card; 
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_card_num; 
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_remain_card;
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_no; 		// ������� ��ȣ �÷� 
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_end_time;
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_device_addr;
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_device_name;
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_current_money;
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_bonus;
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_current_charge;

	@FXML
	private TableColumn<TodayCharge, String> column_charge_remain_card;
	
	@FXML
	private TableColumn<TodayCharge, String> column_charge_card_num;
	
	@FXML
	private GridPane grid_pane_seller; // ���� ǥ �ǳ�
	
	@FXML
	private Label lbl_today_sales; // ���� ����� 
	
	@FXML
	private Label lbl_today_income; // ���� ���Ծ�
	
	@FXML
	private Label lbl_today_cash_sales; // ���� �����
	
	@FXML
	private Label lbl_today_card_sales; // ī�� �����
	
	@FXML
	private Label lbl_today_card_charge; // ī�� ������ 
	
	private Main mainApp; // ���� 
	
	public boolean CLOCK_STATE = true; // ���� �ð� ���� ���� 
	
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	
	public String url = common.getUrl();
	
	// ���� ����/���� ���� ������ ����Ʈ  
	private ObservableList<TodayDevice> today_device_data = FXCollections.observableArrayList();
	private ObservableList<TodayCharge> today_charge_data = FXCollections.observableArrayList();
	
	// initialize() ������ ȣ��ȴ� 
	public MainViewController() {
		
	}
	
	@FXML
	private void initialize() {
		
		String req = "";				// @������ġ url
		String request = "";			// @url + req 
		String data_connect = ""; 		// @HTTP post return value
		
		
		// ���� ���� ���� ����-������ġ  �Ϸù�ȣ Ȯ�� 
		try {
			File file_auth = new File(common.PROJECT_PATH + "/sample/auth.data");
			FileReader reader = new FileReader(file_auth);
			int file_cnt;
			String encode_str = ""; 
			
			while((file_cnt = reader.read()) != -1) {
				encode_str += (char) file_cnt;
			}
			
			// ���ڵ� ��ȯ
			byte[] decoded = Base64.getDecoder().decode(encode_str);
			String str_auth_data = new String(decoded, StandardCharsets.UTF_8);
			//System.out.println("�����Ϸù�ȣ ���⼺�� >> " + str_auth_data);
			reader.close();
			
			if(str_auth_data.equals("0000")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���");
				alert.setHeaderText("����");
				alert.setContentText("��� ��� �� ��� �� �� �ֽ��ϴ�");
				alert.showAndWait();
				hideMenu();
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
						// str_auth_success = "0"; // ���� �׽�Ʈ �ڵ� 
						//System.out.println("������ ������ġ ��������(1:����, 0:����) : " + str_auth_success);
						
						if(str_auth_success.equals("0")) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("���");
							alert.setHeaderText("����");
							alert.setContentText("��� �� �� ���� ��� �Դϴ�.");
							alert.showAndWait();
							hideMenu();
							return;
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
			System.out.println("initialize Auth Exception : ");
			e.printStackTrace();
		}
		
		handleTodayChargeSales();
		handleTodayDeviceSales();
		hanldeTodaySales();
		handlecurrentTimeStart();
		
		// ���� ���� �ҷ����� 
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
			try {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(data_connect);
				JSONObject object_pos_config = (JSONObject) jsonObject.get("pos_config"); 
				
				String str_self_count = object_pos_config.get("self_count").toString();
				String str_air_count = object_pos_config.get("air_count").toString();
				String str_mate_count = object_pos_config.get("mate_count").toString();
				
				// UI component change
				lbl_top_self.setText(str_self_count);
				lbl_top_air.setText(str_air_count);
				lbl_top_mate.setText(str_mate_count);
								
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
	
	}
	
	// ���� ������� ��ư �ڵ鸵
	public void handleTodayDeviceSales() {
		String req = "get_today_device_sales";
		String request = url + req;
		String data_connect = http.post(request);
		
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
				JSONArray device_array = (JSONArray) jsonObject.get("result");
				
				for(int i=0; i<device_array.size(); i++) {
					JSONObject job = (JSONObject) device_array.get(i);
					
					int no = i+1;
					String str_no = Integer.toString(no);
					String end_time = job.get("end_time").toString();
					String device_addr = job.get("device_addr").toString();
					String device_name = job.get("device_name").toString();
					String cash = job.get("cash").toString();
					String card = job.get("card").toString();
					String remain_card = job.get("remain_card").toString();
					String card_num = job.get("card_num").toString();
					String use_time = job.get("time").toString(); // ���ð� (���̺� ��Ͽ� ���� ����) 
					
					// ���Ŀ� �°� ������ ��ȯ 
					end_time = common.TimestampToDateString(end_time);
					
					if(Integer.parseInt(cash) > 0 || Integer.parseInt(card) == 0) {
						card_num = "-";
					} else {
						card_num = card_num.toUpperCase();
					}
					
					cash = String.format("%,d", Integer.parseInt(cash));
					card = String.format("%,d", Integer.parseInt(card));
					remain_card = String.format("%,d", Integer.parseInt(remain_card));
					
					
					today_device_data.add(new TodayDevice(str_no, end_time, device_addr, device_name, cash, card, card_num, remain_card));
					
					// ���̺� ���� ���� ����Ʈ ��ü �߰�
					table_device_list.setItems(getTodayDeviceData());  
					column_device_wash_no.setCellValueFactory(cellData -> cellData.getValue().getNoProperty());
					column_device_end_time.setCellValueFactory(cellData -> cellData.getValue().getEndTimeProperty());
					column_device_wash_addr.setCellValueFactory(cellData -> cellData.getValue().getDeviceAddrProperty());
					column_device_wash_name.setCellValueFactory(cellData -> cellData.getValue().getDeviceNameProperty());
					column_device_use_cash.setCellValueFactory(cellData -> cellData.getValue().getUseCashProperty());
					column_device_use_card.setCellValueFactory(cellData -> cellData.getValue().getUseCardProperty());
					column_device_card_num.setCellValueFactory(cellData -> cellData.getValue().getCardNumProperty());
					column_device_remain_card.setCellValueFactory(cellData -> cellData.getValue().getRemainCardProperty());
				}
				
			} catch (ParseException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���");
				alert.setHeaderText("����");
				alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�. �ٽ� �õ��� �ּ���.");
				alert.showAndWait();
				return;
			}
		}
	}
	
	// ���� ������� ��ư �ڵ鸵
	public void handleTodayChargeSales() {
		String req = "get_today_charger_sales";
		String request = url + req;
		String data_connect = http.post(request);
		
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
				JSONArray charge_array = (JSONArray) jsonObject.get("result");
				
				for(int i=0; i<charge_array.size(); i++) {
					JSONObject job = (JSONObject) charge_array.get(i);
					
					int no = i+1;
					String str_no = Integer.toString(no);
					String end_time = job.get("input_date").toString();
					String device_addr = job.get("device_addr").toString();
					String device_name = job.get("device_name").toString();
					String current_money = job.get("money").toString();
					String bonus = job.get("bonus").toString();
					String charge = job.get("charge").toString();
					String remain_card = job.get("remain_card").toString();
					String card_num = job.get("card_num").toString();
					
					// ���Ŀ� �°� ������ ��ȯ 
					end_time = common.TimestampToDateString(end_time);
					
					current_money = String.format("%,d", Integer.parseInt(current_money));
					bonus = String.format("%,d", Integer.parseInt(bonus));
					charge = String.format("%,d", Integer.parseInt(charge));
					remain_card = String.format("%,d", Integer.parseInt(remain_card));
					card_num = card_num.toUpperCase();
					
					today_charge_data.add(new TodayCharge(str_no, end_time, device_addr, device_name, current_money, bonus, charge, remain_card, card_num));
					
					table_charge_list.setItems(getTodayChargeData());
					column_charge_no.setCellValueFactory(cellData -> cellData.getValue().noProperty());
					column_charge_end_time.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
					column_charge_device_addr.setCellValueFactory(cellData -> cellData.getValue().deviceAddrProperty());
					column_charge_device_name.setCellValueFactory(cellData -> cellData.getValue().deviceNameProperty());
					column_charge_current_money.setCellValueFactory(cellData -> cellData.getValue().currentMoneyProperty());
					column_charge_bonus.setCellValueFactory(cellData -> cellData.getValue().bonusProperty());
					column_charge_current_charge.setCellValueFactory(cellData -> cellData.getValue().chargeProperty());
					column_charge_remain_card.setCellValueFactory(cellData -> cellData.getValue().remainCardProperty());
					column_charge_card_num.setCellValueFactory(cellData -> cellData.getValue().cardNumProperty());
					
				}
				
			} catch (ParseException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���");
				alert.setHeaderText("����");
				alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�. �ٽ� �õ��� �ּ���.");
				alert.showAndWait();
				return;				
			}
		}
	}
	
	// ���� ����� �ڵ鸵 
	public void hanldeTodaySales() {
		String req = "get_today_sales_total";
		String request = url + req;
		String data_connect = http.post(request);
		
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
				JSONObject result = (JSONObject) jsonObject.get("result");
				
				String sales = result.get("sales").toString();
				String income = result.get("income").toString();
				String cash_sales = result.get("cash_sales").toString();
				String card_sales = result.get("card_sales").toString();
				String card_charge = result.get("card_charge").toString();
				
				lbl_today_sales.setText(String.format("%,d", Integer.parseInt(sales)) + " ��");
				lbl_today_income.setText(String.format("%,d", Integer.parseInt(income)) + " ��");
				lbl_today_cash_sales.setText(String.format("%,d", Integer.parseInt(cash_sales)) + " ��");
				lbl_today_card_sales.setText(String.format("%,d", Integer.parseInt(card_sales)) + " ��");
				lbl_today_card_charge.setText(String.format("%,d", Integer.parseInt(card_charge)) + " ��");
				
			} catch (ParseException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���");
				alert.setHeaderText("����");
				alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�  �ٽ� �õ��� �ּ���.");
				alert.showAndWait();
				return;
			}			
		}
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
	
	// ������ ��볻�� Ŭ��
	public void handleBtnDevice() {
		today_charge_data.clear();
		pane_charge.setVisible(false);
		pane_device_wash.setVisible(true);
		handleTodayDeviceSales();
	}
	
	// ������� ��볻�� Ŭ�� 
	public void hanldeBtnCharge() {
		today_device_data.clear();
		pane_device_wash.setVisible(false);
		pane_charge.setVisible(true);
		handleTodayChargeSales();
	}
	
	// ���� ���� �� ���� �޴� ����� 
	public void hideMenu() {
		btn_weather_image.setVisible(false);
		btn_usbcharge_image.setVisible(false);
		btn_seller_image.setVisible(false);
		btn_device_config_image.setVisible(false);
		btn_member_image.setVisible(false);
		btn_blackcard_image.setVisible(false);
		btn_device_info_image.setVisible(false);
		btn_addon_image.setVisible(false);
		btn_member_list_image.setVisible(false);
		btn_card_list_image.setVisible(false);
		btn_realtime_image.setVisible(false);
		btn_small_setting_image.setVisible(false);
		btn_small_realtime_image.setVisible(false);
		pane_tail_info.setVisible(false);
		btn_hide_info_image.setVisible(false);
	}
	
	// �ϴ� ���� �����ֱ�  
	public void handleShowInfo() {
		btn_show_info_image.setVisible(false);
		btn_hide_info_image.setVisible(true);
		pane_tail_info.setVisible(true);
	}
	
	// �ϴ� ���� �����
	public void hanldeHideInfo() {
		btn_show_info_image.setVisible(true);
		btn_hide_info_image.setVisible(false);
		pane_tail_info.setVisible(false);
	}
	
	// �ð� ���߱� 
	public void handlecurrentTimeStop() {
		CLOCK_STATE = false;
	}
	
	// ���� ���� / ���� ������ ����Ʈ ��ȯ
	public ObservableList<TodayDevice> getTodayDeviceData(){
		return today_device_data;
	}
	
	public ObservableList<TodayCharge> getTodayChargeData(){
		return today_charge_data;
	} 
	
	// �������� ���ư��� 
	// @param mainApp
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
}
