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
	
	// �ڷΰ���
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
	
	// ī���б�
	@FXML
	private void handleReadCard() {
		
	}
	
	// ī�� ���� 
	@FXML
	private void handleCardCharge() {
		
	}
	
	// ī�� ��� ���� (������) 
	@FXML
	private void handleNewCard() {
		
	}
	
	// ī�� ��� ���� 
	@FXML
	private void handleStopCard() {
		
	}
	
	// ī���������� �Ǹ޴�1
	@FXML
	private void handleTabMenu1() {
		
	}
	
	// ī�� ���� �Ǹ޴�2
	private void handleTabMenu2() {
		
	}

	// �ű�ī�� ��� �Ǹ޴�3
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
