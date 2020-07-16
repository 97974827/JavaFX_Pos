package kor.gls;
	
import java.io.IOException;

import javax.swing.event.AncestorEvent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kor.gls.view.MainViewController;


public class Main extends Application {
	
	private Stage primaryStage;
	public static BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("HyunJin");
		
		initRootLayout();
		showRootLayout();
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			AnchorPane mainview = (AnchorPane) loader.load();
			
			// ��� ���� 
			rootLayout.setCenter(mainview);
			
			// ���� ���ø����̼��� ��Ʈ�ѷ��� �̿��� �� �ְ� �Ѵ� 
			MainViewController main_controller = loader.getController();
			main_controller.setMainApp(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}
	
	// ���� �������� ��ȯ 
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	// UI ���� �޼��� 
	public static void main(String[] args) {
		launch(args);
	}
}
