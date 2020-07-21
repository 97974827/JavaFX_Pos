package kor.gls.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import kor.gls.Main;
import kor.gls.model.TodayCharge;
import kor.gls.model.TodayDevice;
import kor.gls.util.CommonUtil;
import kor.gls.util.HttpUtil;
import kor.gls.util.WeatherUtil;

public class WeatherController {
	
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
	private ImageView image_top_weather; // 상단 날씨 이미지 
	
	@FXML
	private Label lbl_current_clock; // 현재시간
	
	@FXML
	private ImageView btn_small_setting_image; // 상단 작은 메인버튼
	
	@FXML 
	private ImageView btn_small_realtime_image; // 상단 작은 실시간 버튼 
	
	@FXML
	private Pane pane_weather;
	
	@FXML
	private Button btn_today_device;
	
	@FXML
	private Button btn_today_charge;
	
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
	private TableColumn<TodayCharge, String> column_charge_no; 		// 충전기기 번호 컬럼 
	
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
	
	@FXML
	private ImageView btn_show_info_image; // 하단 매출 보여주기 
	
	@FXML
	private ImageView btn_hide_info_image; // 하단 매출 숨기기
	
	@FXML
	private Label lbl_day_temp1;
	
	@FXML
	private Label lbl_day_temp2;
	
	@FXML
	private Label lbl_day_temp3;
	
	@FXML
	private Label lbl_day_temp4;
	
	@FXML
	private Label lbl_day_temp5;
	
	@FXML
	private Label lbl_day_temp6;
	
	@FXML
	private Label lbl_day_temp7;
	
	@FXML
	private Label lbl_day_temp8;
	
	@FXML
	private Label lbl_day_temp9;
	
	@FXML
	private Label lbl_day_temp10;

	@FXML
	private Label lbl_date1;
	
	@FXML
	private Label lbl_date2;
	
	@FXML
	private Label lbl_date3;
	
	@FXML
	private Label lbl_date4;
	
	@FXML
	private Label lbl_date5;
	
	@FXML
	private Label lbl_date6;
	
	@FXML
	private Label lbl_date7;
	
	@FXML
	private Label lbl_date8;
	
	@FXML
	private Label lbl_date9;
	
	@FXML
	private Label lbl_date10;
	
	@FXML
	private ImageView image_weather1;
	
	@FXML
	private ImageView image_weather2;
	
	@FXML
	private ImageView image_weather3;
	
	@FXML
	private ImageView image_weather4;
	
	@FXML
	private ImageView image_weather5;
	
	@FXML
	private ImageView image_weather6;
	
	@FXML
	private ImageView image_weather7;
	
	@FXML
	private ImageView image_weather8;
	
	@FXML
	private ImageView image_weather9;
	
	@FXML
	private ImageView image_weather10;
	
	@FXML
	private Main mainApp;
	
	public boolean CLOCK_STATE = false; 
	
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	WeatherUtil weather = new WeatherUtil();
	public String url = common.getUrl();
	
	// 금일 세차/충전 매출 데이터 리스트  
	private ObservableList<TodayDevice> today_device_data = FXCollections.observableArrayList();
	private ObservableList<TodayCharge> today_charge_data = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		handleTodayDeviceSales();
		handleTodayChargeSales();
		handlecurrentTimeStart();
		handleTodaySales();
		
		ViewWeather();
	}
	
	// 10일간 날씨 예보 
	private void ViewWeather() {
		
	}
	
	// 금일 세차기기 버튼 핸들링
	public void handleTodayDeviceSales() {
		today_device_data.clear();
		String req = "get_today_device_sales";
		String request = url + req;
		String data_connect = http.post(request);
		
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
					String use_time = job.get("time").toString(); // 사용시간 (테이블 목록엔 들어가지 않음) 
					
					// 형식에 맞게 데이터 변환 
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
					
					// 테이블에 금일 세차 리스트 객체 추가
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
				alert.setTitle("경고");
				alert.setHeaderText("에러");
				alert.setContentText("값이 제대로 전달 되지 않았습니다. 다시 시도해 주세요.");
				alert.showAndWait();
				return;
			}
		}
	}
	
	// 금일 충전기기 버튼 핸들링
	public void handleTodayChargeSales() {
		today_charge_data.clear();
		
		String req = "get_today_charger_sales";
		String request = url + req;
		String data_connect = http.post(request);
		
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
					
					// 형식에 맞게 데이터 변환 
					end_time = common.TimestampToDateString(end_time);
					
					current_money = String.format("%,d", Integer.parseInt(current_money));
					bonus = String.format("%,d", Integer.parseInt(bonus));
					charge = String.format("%,d", Integer.parseInt(charge));
					remain_card = String.format("%,d", Integer.parseInt(remain_card));
					card_num = card_num.toUpperCase();
					
					today_charge_data.add(new TodayCharge(str_no, end_time, device_addr, device_name, current_money, bonus, charge, remain_card, card_num));
				
				}
				
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
				
			} catch (ParseException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("경고");
				alert.setHeaderText("에러");
				alert.setContentText("값이 제대로 전달 되지 않았습니다. 다시 시도해 주세요.");
				alert.showAndWait();
				return;				
			}
		}
	}
	
	// 금일 매출액 핸들링 
	public void handleTodaySales() {
		String req = "get_today_sales_total";
		String request = url + req;
		String data_connect = http.post(request);
		
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
				JSONObject result = (JSONObject) jsonObject.get("result");
				
				String sales = result.get("sales").toString();
				String income = result.get("income").toString();
				String cash_sales = result.get("cash_sales").toString();
				String card_sales = result.get("card_sales").toString();
				String card_charge = result.get("card_charge").toString();
				
				lbl_today_sales.setText(String.format("%,d", Integer.parseInt(sales)) + " 원");
				lbl_today_income.setText(String.format("%,d", Integer.parseInt(income)) + " 원");
				lbl_today_cash_sales.setText(String.format("%,d", Integer.parseInt(cash_sales)) + " 원");
				lbl_today_card_sales.setText(String.format("%,d", Integer.parseInt(card_sales)) + " 원");
				lbl_today_card_charge.setText(String.format("%,d", Integer.parseInt(card_charge)) + " 원");
				
			} catch (ParseException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("경고");
				alert.setHeaderText("에러");
				alert.setContentText("값이 제대로 전달 되지 않았습니다  다시 시도해 주세요.");
				alert.showAndWait();
				return;
			}			
		}
	}
	
	// 세사기기 사용내역 클릭
	public void handleBtnDevice() {
		today_charge_data.clear();
		pane_charge.setVisible(false);
		pane_device_wash.setVisible(true);
		handleTodayDeviceSales();
	}
	
	// 충전기기 사용내역 클릭 
	public void hanldeBtnCharge() {
		today_device_data.clear();
		pane_device_wash.setVisible(false);
		pane_charge.setVisible(true);
		handleTodayChargeSales();
	}
			
	// 하단 매출 보여주기  
	public void handleShowInfo() {
		btn_show_info_image.setVisible(false);
		btn_hide_info_image.setVisible(true);
		pane_tail_info.setVisible(true);
	}
	
	// 하단 매출 숨기기
	public void hanldeHideInfo() {
		btn_show_info_image.setVisible(true);
		btn_hide_info_image.setVisible(false);
		pane_tail_info.setVisible(false);
	}
	
	// 현재 시간 체크 
	public void handlecurrentTimeStart() {
		CLOCK_STATE = true;
		Thread thread_clock = new Thread() {
			@Override
			public void run() {
				while(CLOCK_STATE) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
					String dTime = formatter.format(new Date()); // 시간 추출 
					
					// 주의할 점 : javaFX 어플리케이션 스레드는 시간을 요하는 작업을 하지 않도록 하는 것
					// 다른 작업 스레드를 생성해서 처리함 
					// 작업 스레드가 직접 UI컴포넌트를 변경 할 수 없기 때문에 변경이 필요한 경우 , 
					// 작업 스레드는 UI컴포넌트 변경 코드를 Runnable로 생성 가능 (run 오버라이딩)
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
		
	// 시계 멈추기 
	public void handlecurrentTimeStop() {
		CLOCK_STATE = false;
	}
	
	// 뒤로가기
	public void handleExit() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			AnchorPane main = (AnchorPane) loader.load();
			Main.rootLayout.setCenter(main);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 금일 세차 / 충전 데이터 리스트 반환
	public ObservableList<TodayDevice> getTodayDeviceData(){
		return today_device_data;
	}
	
	public ObservableList<TodayCharge> getTodayChargeData(){
		return today_charge_data;
	} 
	
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
