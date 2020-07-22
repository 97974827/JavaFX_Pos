package kor.gls.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import kor.gls.Main;

public class USBChargeController {
	
	
	@FXML
	private Main mainApp;

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
			MainViewController main_controller = loader.getController();
			main_controller.setMainApp(mainApp);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 카드읽기
	@FXML
	private void handleReadCard() {
		
	}
	
	// 카드 충전 
	@FXML
	private void handleCardCharge() {
		
	}
	
	// 카드 등록 시작 (스레드) 
	@FXML
	private void handleNewCard() {
		
	}
	
	// 카드 등록 중지 
	@FXML
	private void handleStopCard() {
		
	}
	
	// 카드정보보기 탭메뉴1
	@FXML
	private void handleTabMenu1() {
		
	}
	
	// 카드 충전 탭메뉴2
	private void handleTabMenu2() {
		
	}

	// 신규카드 등록 탭메뉴3
	private void handleTabMenu3() {
		
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
	
	// TODO : TEST
	public static void main(String[] args) {
		USBChargeController usb = new USBChargeController();
		
	}
}
