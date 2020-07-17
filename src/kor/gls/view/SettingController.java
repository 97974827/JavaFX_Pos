package kor.gls.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import kor.gls.Main;
import kor.gls.util.CommonUtil;
import kor.gls.util.HttpUtil;

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
	private ImageView image_small_setting_btn;
	
	@FXML
	private ImageView image_small_real_btn;
	
	@FXML
	private Pane pane_setting;
	
	@FXML
	private GridPane gridpane_device_count; // 기기 수량
	
	@FXML
	private GridPane gridpane_basic_config; // 기본 설정
	
	@FXML
	private GridPane gridpane_other_config; // 기타 설정
	
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
	private TextField textfield_shop_id; // 매장ID = 세차장 ID
	
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
	private CheckBox checkbox_seller_pw_enable_state; // 매출자료 암호사용
	
	@FXML
	private CheckBox checkbox_seller_main_state; // 매출계 표시
	
	@FXML
	private ChoiceBox<String> choicebox_weather_area; // 날씨 지역
	
	@FXML
	private TextField textfield_weather_url_zone; // 날씨 URL 
	
	@FXML
	private CheckBox checkbox_member_bonus; // 회원등급제 사용
	
	@FXML
	private Label lbl_pos_version; // 포스 버젼
	
	@FXML
	private Label lbl_dc_version; // 데이터 수집장치 버젼
	
	@FXML
	private Button btn_seller_back;
	
	@FXML
	private Button btn_seller_save_and_exit; // 저장 후 종료
	
	public boolean CLOCK_STATE = true; // 현재 시간 상태 변수 
	
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	
	public String url = common.getUrl();
	
	@FXML
	private Main mainApp;
	
	// JavaFX 뷰 컬렉션 클래스에 추가
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
	private ObservableList<String> observablelist_weather_area = FXCollections.observableArrayList("서울", "인천", "수원", "파주", "광주", "춘천", "원주", "강릉", "청주", "대전", "서산", "세종", "전주", "군산", "광주", "목포", "여수", "대구", "안동", "포항", "부산", "울산", "창원", "제주", "서귀포");
	
	public SettingController() {
	}
	
	@FXML
	private void initialize() {
		
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
	
	// 저장 후 종료 
	@FXML
	private void handleOK() {
		
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
