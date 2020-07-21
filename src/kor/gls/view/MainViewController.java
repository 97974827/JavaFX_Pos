package kor.gls.view;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import kor.gls.Main;
import kor.gls.model.TodayCharge;
import kor.gls.model.TodayDevice;
import kor.gls.util.CommonUtil;
import kor.gls.util.HttpUtil;
import kor.gls.util.WeatherUtil;

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
	private ImageView image_top_weather; // 상단 날씨 이미지 
	
	@FXML
	private Label lbl_current_clock; // 현재시간
	
	@FXML
	private ImageView btn_small_setting_image; // 상단 작은 메인버튼
	
	@FXML 
	private ImageView btn_small_realtime_image; // 상단 작은 실시간 버튼 
	
	@FXML
	private Pane pane_main; // 이미지 버튼 메인 판넬
	
	@FXML
	private ImageView btn_show_info_image; // 하단 매출 보여주기 
	
	@FXML
	private ImageView btn_hide_info_image; // 하단 매출 숨기기
	
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
	
	private Main mainApp; // 메인 
	
	public boolean CLOCK_STATE = true; // 현재 시간 상태 변수 
	
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	
	public String url = common.getUrl();
	
	// 금일 세차/충전 매출 데이터 리스트  
	private ObservableList<TodayDevice> today_device_data = FXCollections.observableArrayList();
	private ObservableList<TodayCharge> today_charge_data = FXCollections.observableArrayList();
	
	// initialize() 이전에 호출된다 
	public MainViewController() {
		
	}
	
	@FXML
	private void initialize() {
		
		String req = "";				// @수집장치 url
		String request = "";			// @url + req 
		String data_connect = ""; 		// @HTTP post return value
		
		
		// 파일 생성 이후 포스-수집장치  일련번호 확인 
		try {
			File file_auth = new File(common.PROJECT_PATH + "/sample/auth.data");
			FileReader reader = new FileReader(file_auth);
			int file_cnt;
			String encode_str = ""; 
			
			while((file_cnt = reader.read()) != -1) {
				encode_str += (char) file_cnt;
			}
			
			// 디코딩 변환
			byte[] decoded = Base64.getDecoder().decode(encode_str);
			String str_auth_data = new String(decoded, StandardCharsets.UTF_8);
			//System.out.println("포스일련번호 추출성공 >> " + str_auth_data);
			reader.close();
			
			if(str_auth_data.equals("0000")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("경고");
				alert.setHeaderText("에러");
				alert.setContentText("기기 등록 후 사용 할 수 있습니다");
				alert.showAndWait();
				hideMenu();
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
						// str_auth_success = "0"; // 실패 테스트 코드 
						//System.out.println("데이터 수집장치 성공여부(1:성공, 0:실패) : " + str_auth_success);
						
						if(str_auth_success.equals("0")) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("경고");
							alert.setHeaderText("에러");
							alert.setContentText("사용 할 수 없는 기기 입니다.");
							alert.showAndWait();
							hideMenu();
							return;
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
			System.out.println("initialize Auth Exception : ");
			e.printStackTrace();
		}
		
		handleTodayChargeSales();
		handleTodayDeviceSales();
		hanldeTodaySales();
		handlecurrentTimeStart();
		
		// 포스 설정 불러오기 
		req = "get_pos_config";
		request = url + req; 
		data_connect = http.post(request);
		
		// 지역날씨 zone
		String weather_url_zone = "";
		
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
				JSONObject object_pos_config = (JSONObject) jsonObject.get("pos_config"); 
				
				String str_self_count = object_pos_config.get("self_count").toString();
				String str_air_count = object_pos_config.get("air_count").toString();
				String str_mate_count = object_pos_config.get("mate_count").toString();
				weather_url_zone = object_pos_config.get("weather_url").toString();
				
				// UI component change
				lbl_top_self.setText(str_self_count);
				lbl_top_air.setText(str_air_count);
				lbl_top_mate.setText(str_mate_count);
								
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
		
		// 날씨 정보 가져오기
		WeatherUtil weatherClass = new WeatherUtil();
		
		List<Map<String, String>> weather_data = new ArrayList<Map<String, String>>(); 
		weather_data = weatherClass.getWeatherRssZoneParse(weather_url_zone);
		
		for(int i=0; i<weather_data.size(); i++) {
			String weather_day = weather_data.get(i).get("day"); 
			
			String temper = weather_data.get(i).get("temp"); // 온도
			String wfKor = weather_data.get(i).get("wfKor"); // 날씨
		
			lbl_weather_str.setText(wfKor);
			lbl_weather_temp.setText(temper + "ºC");
			
			String str_image = weatherClass.changeWeatherImage(wfKor);
			Image image = new Image("File:resources/" + str_image);
			image_top_weather.setImage(image);
		}
		
		req = "start_thread";
		request = url + req;
		data_connect = http.post(request);
		
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
				alert.setTitle("경고");
				alert.setHeaderText("에러");
				alert.setContentText("값이 제대로 전달 되지 않았습니다. 다시 시도해 주세요.");
				alert.showAndWait();
				return;				
			}
		}
	}
	
	// 금일 매출액 핸들링 
	public void hanldeTodaySales() {
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
	
	// 인증 실패 시 메인 메뉴 숨기기 
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
	
	// 환경설정에 마우스 누를 때
	@FXML
	private void handleBtnSettingPressed() {
		Image image = new Image("File:resources/setting_btn_active.png");
		btn_setting_image.setImage(image);
	}
	
	// 환경설정에 마우스 뗄 때
	@FXML
	private void handleBtnSettingReleased(){
		Image image = new Image("File:resources/setting_btn_disable.png");
		btn_setting_image.setImage(image);
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Login.fxml"));
			AnchorPane pane_login = (AnchorPane) loader.load();
			
			Main.rootLayout.setCenter(pane_login);
			
			LoginController login_controller = loader.getController();
			login_controller.setMainApp(mainApp);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 일기예보에 마우스 누를 때 
	@FXML
	private void handleBtnWeatherPressed() {
		Image image = new Image("File:resources/wather_btn_active.png");
		btn_weather_image.setImage(image);
	}
	
	// 일기예보에 마우스 뗄 때
	@FXML
	private void handleBtnWeatherReleased(){
		Image image = new Image("File:resources/wather_btn_disable.png");
		btn_weather_image.setImage(image);
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/WeatherView.fxml"));
			AnchorPane weather = (AnchorPane) loader.load();
			
			Main.rootLayout.setCenter(weather);
			
			WeatherController controller = loader.getController();
			controller.setMainApp(mainApp);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	@FXML
	private void handleBtnUsbChargePressed() {
		Image image = new Image("File:resources/charger_btn_active.png");
		btn_usbcharge_image.setImage(image);
	}
	
	@FXML
	private void handleBtnUsbChargeReleased(){
		Image image = new Image("File:resources/charger_btn_disable.png");
		btn_usbcharge_image.setImage(image);
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/USBChargeView.fxml"));
			AnchorPane pane_charge = (AnchorPane) loader.load();
			
			Main.rootLayout.setCenter(pane_charge);
			
			USBChargeController controller = loader.getController();
			controller.setMainApp(mainApp);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleBtnSellerPressed() {
		Image image = new Image("File:resources/seller_btn_active.png");
		btn_seller_image.setImage(image);
	}
	
	@FXML
	private void handleBtnSellerReleased() {
		Image image = new Image("File:resources/seller_btn_disable.png");
		btn_seller_image.setImage(image);		
	}
	 
	@FXML
	private void handleBtnDeviceConfigPressed() {
		Image image = new Image("File:resources/device_btn_active.png");
		btn_device_config_image.setImage(image);
	}
	
	@FXML
	private void handleBtnDeviceConfigReleased() {
		Image image = new Image("File:resources/device_btn_disable.png");
		btn_device_config_image.setImage(image);		
	}
	
	@FXML
	private void handleBtnMemberPressed() {
		Image image = new Image("File:resources/member_btn_active_1.png");
		btn_member_image.setImage(image);
	}
	
	@FXML
	private void handleBtnMemberReleased() {
		Image image = new Image("File:resources/member_btn_disable_1.png");
		btn_member_image.setImage(image);		
	}
	@FXML
	private void handleBtnBlackCardPressed() {
		Image image = new Image("File:resources/blacklist_btn_active.png");
		btn_blackcard_image.setImage(image);
	}
	
	@FXML
	private void handleBtnBlackCardReleased() {
		Image image = new Image("File:resources/blacklist_btn_disable.png");
		btn_blackcard_image.setImage(image);		
	}
	
	@FXML
	private void handleBtnDeviceInfoPressed() {
		Image image = new Image("File:resources/deviceinfo_btn_active.png");
		btn_device_info_image.setImage(image);
	}
	@FXML
	private void handleBtnDeviceInfoReleased() {
		Image image = new Image("File:resources/deviceinfo_btn_disable.png");
		btn_device_info_image.setImage(image);		
	}
	
	@FXML
	private void handleBtnAddOnPressed() {
		Image image = new Image("File:resources/addon_btn_active.png");
		btn_addon_image.setImage(image);
	}
	
	@FXML
	private void handleBtnAddOnReleased() {
		Image image = new Image("File:resources/addon_btn_disable.png");
		btn_addon_image.setImage(image);		
	}
	
	@FXML
	private void handleBtnMemberListPressed() {
		Image image = new Image("File:resources/memberlist_btn_active.png");
		btn_member_list_image.setImage(image);
	}
	
	@FXML
	private void handleBtnMemberListReleased() {
		Image image = new Image("File:resources/memberlist_btn_disable.png");
		btn_member_list_image.setImage(image);		
	}
	
	@FXML
	private void handleBtnCardListPressed() {
		Image image = new Image("File:resources/card_btn_active.png");
		btn_card_list_image.setImage(image);
	}
	
	@FXML
	private void handleBtnCardListReleased() {
		Image image = new Image("File:resources/card_btn_disable.png");
		btn_card_list_image.setImage(image);		
	}
	
	@FXML
	private void handleBtnRealTimePressed() {
		Image image = new Image("File:resources/monitor_btn_active.png");
		btn_realtime_image.setImage(image);
	}
	
	@FXML
	private void handleBtnRealTimeReleased() {
		Image image = new Image("File:resources/monitor_btn_disable.png");
		btn_realtime_image.setImage(image);		
	}
	
	// 금일 세차 / 충전 데이터 리스트 반환
	public ObservableList<TodayDevice> getTodayDeviceData(){
		return today_device_data;
	}
	
	public ObservableList<TodayCharge> getTodayChargeData(){
		return today_charge_data;
	} 
	
	// 메인으로 돌아가기 
	// @param mainApp
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
}
