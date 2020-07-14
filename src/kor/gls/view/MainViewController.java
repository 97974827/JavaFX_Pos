package kor.gls.view;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javafx.fxml.FXML;
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
	private TableColumn<TodayCharge, String> column_charge_no; // ������� ��ȣ �÷� 
	
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
	
	// initialize() ������ ȣ��ȴ� 
	public MainViewController() {
		
	}
	
	@FXML
	private void initialize() {
		CommonUtil common = new CommonUtil();
		
		System.out.println(common.PROJECT_PATH);
		
		// 1. ���� ���� ���� ����-������ġ  �Ϸù�ȣ Ȯ�� 
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
			System.out.println("�Ϸù�ȣ���� : " + str_auth_data);
			
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// ���� ������� ��ư �ڵ鸵
	public void handleTodayDeviceSales() {
		
	}
	
	// ���� ������� ��ư �ڵ鸵
	public void handleTodayChargeSales() {
		
	}
	
	// ���� ����� �ڵ鸵 
	public void hanldeTodaySales() {
		
	}
	
	// ���� �ð� üũ 
	public void handlecurrentTimeClock() {
		
	}
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
}
