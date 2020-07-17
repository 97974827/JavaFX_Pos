package kor.gls.view;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kor.gls.Main;
import kor.gls.util.CommonUtil;
import kor.gls.util.HttpUtil;

public class LoginController {
	
	@FXML
	private Pane pane_login;
	
	@FXML
	private Label lbl_admin_id;
	
	@FXML
	private Label lbl_admin_pw;
	
	@FXML
	private Button btn_admin_success;
	
	@FXML
	private Button btn_admin_cancel;
	
	@FXML
	private ImageView image_admin_title;
	
	@FXML
	private TextField text_field_admin_id;
	
	@FXML
	private PasswordField text_field_admin_pw;
	
	HttpUtil http = new HttpUtil();
	CommonUtil common = new CommonUtil();
	
	public String url = common.getUrl();
	
	private Main mainApp;
	
	private String str_login_kinds; // �޴� ���� ���ú��� : ȯ�漳�� / ������ȸ / ����̷� - �������� 
	
	
	public LoginController() {	
	}
	
	@FXML
	private void initialize() {
		
		// �н����� �ʵ� Enter key Event
		text_field_admin_pw.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {  
					handlebtnSuccess();
				}
			}
		});
	}
	
	// Ȯ�� 
	public void handlebtnSuccess() {
		
		// ����� �Է� �� ��������  
		String id = text_field_admin_id.getText();
		String pw = text_field_admin_pw.getText();
		
		String db_id = "";
		String db_pw = "";
		String master_pw = "";
		
		String req = "";
		String request = "";
		String data_connect = "";
		
		req = "get_pos_config";
		request = url + req;
		data_connect = http.post(request);
		
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
				JSONObject object_pos = (JSONObject) jsonObject.get("pos_config");
				
				db_id = object_pos.get("shop_id").toString();
				db_pw = object_pos.get("shop_pw").toString();
				master_pw = object_pos.get("admin_pw").toString();
				
			} catch (Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���");
				alert.setHeaderText("����");
				alert.setContentText("���� ����� ���� ���� �ʾҽ��ϴ�. �ٽ� �õ��� �ּ���.");
				alert.showAndWait();
				return;
			}
			
			// ������ �α��� ����
			if(id.equals(db_id) && pw.equals(db_pw)) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/SettingView.fxml"));
					AnchorPane pane_setting = (AnchorPane) loader.load();
					Main.rootLayout.setCenter(pane_setting);
					
					SettingController setting = loader.getController();
					setting.setMainApp(mainApp);
					
				} catch(Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���");
					alert.setHeaderText("����");
					alert.setContentText("���� ����� ���޵��� �ʾҽ��ϴ�. �ٽ� �õ��� �ּ���.");
					alert.showAndWait();
					return;
				}
			// ������ �α��� ���� 
			} else if(id.equals(db_id) && pw.equals(master_pw)) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/MasterSettingView.fxml"));
					AnchorPane master = (AnchorPane) loader.load();
					Main.rootLayout.setCenter(master);
					
					MasterSettingController mastersetting = loader.getController();
					mastersetting .setMainApp(mainApp);
					
				} catch(Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���");
					alert.setHeaderText("����");
					alert.setContentText("���� ����� ���޵��� �ʾҽ��ϴ�. �ٽ� �õ��� �ּ���.");
					alert.showAndWait();
					return;
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("����");
				alert.setHeaderText("�α��� ����");
				alert.setContentText("���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�. �ùٸ� ���� �Է��� �ּ���.");
				alert.showAndWait();
				return;
			}
			
		}
		
	}
	
	// ���
	public void handlebtnCancel() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			AnchorPane main = (AnchorPane) loader.load();
			Main.rootLayout.setCenter(main);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLoginKinds(String str_login_kinds) {
		this.str_login_kinds = str_login_kinds;
	}
	
	public String getLoginKinds() {
		return str_login_kinds;
	}
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
