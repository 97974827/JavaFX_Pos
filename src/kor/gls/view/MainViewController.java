package kor.gls.view;

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

public class MainViewController {
	
	@FXML
	private Label lbl_top_self;
	
	@FXML
	private Label lbl_top_air;
	
	@FXML
	private Label lbl_top_mate;
	
	@FXML
	private Label lbl_weather_str;  // 금일 날씨 (흐림, 맑음) 
	
	@FXML
	private Label lbl_weather_temp; // 금일 온도
	
	@FXML
	private Label lbl_current_clock; // 현재시간
	
	@FXML
	private ImageView btn_small_setting_image; // 상단 작은 메인버튼
	
	@FXML 
	private ImageView btn_small_realtime_image; // 상단 작은 실시간 버튼 
	
	@FXML
	private Pane pane_main; // 이미지 버튼 메인 판넬
	
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
	private Pane pane_tail_info; // 사용내역 + 금일 매출액 전체 판넬
	
	@FXML
	private Pane pane_device_wash; // 세차기기 판넬
	
	@FXML
	private Pane pane_charge; // 충전기기 판넬
	
	@FXML
	private ScrollPane scroll_pane_device; // 세차기기 스크롤 판넬
	
	@FXML
	private ScrollPane scroll_pane_charge; // 충전기기 스크롤 판넬
	
	@FXML
	private TableView<TodayDevice> table_device_list; // 세차기기 테이블
	
	@FXML
	private TableView<TodayCharge> table_charge_list; // 충전기기 테이블 
	
	@FXML
	private Button btn_device_wash_info; // 세차기기 사용내역 버튼 
	
	@FXML
	private Button btn_charge_wash_info; // 충전기기 사용내역 버튼 
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_wash_no; // 세차기기 번호 컬럼
	
	@FXML
	private TableColumn<TodayDevice, String> column_device_end_time; // 세차기기 일시 컬럼
	
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
	private TableColumn<TodayCharge, String> column_charge_no; // 충전기기 번호 컬럼 
	
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
	private GridPane grid_pane_seller; // 매출 표 판넬
	
	@FXML
	private Label lbl_today_sales; // 금일 매출액 
	
	@FXML
	private Label lbl_today_income; // 금일 수입액
	
	@FXML
	private Label lbl_today_cash_sales; // 현금 매출액
	
	@FXML
	private Label lbl_today_card_sales; // 카드 매출액
	
	@FXML
	private Label lbl_today_card_charge; // 카드 충전액 
	
	private Main mainApp; // 메인 
	
	// initialize() 이전에 호출된다 
	public MainViewController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	// 금일 세차기기 버튼 핸들링
	public void handleTodayDeviceSales() {
		
	}
	
	// 금일 충전기기 버튼 핸들링
	public void handleTodayChargeSales() {
		
	}
	
	// 금일 매출액 핸들링 
	public void hanldeTodaySales() {
		
	}
	
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
}
