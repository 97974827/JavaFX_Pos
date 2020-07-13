package kor.gls.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// 금일 세차기기 사용 내역 모델 클래스 
public class TodayDevice {
	
	private final StringProperty no; 			// 번호
	private final StringProperty end_time;   	// 일시
	private final StringProperty device_addr;	// 장비번호
	private final StringProperty device_name;	// 장비명
	private final StringProperty use_cash;		// 현금사용
	private final StringProperty use_card;		// 카드사용
	private final StringProperty card_num;		// 카드번호
	private final StringProperty remain_card;	// 카드잔액
	
	public TodayDevice() {
		this(null, null, null, null, null, null, null, null);
	}
	
	public TodayDevice(String no, String end_time, String device_addr, String device_name, String use_cash, String use_card, String card_num, String remain_card){
		this.no = new SimpleStringProperty(no);
		this.end_time = new SimpleStringProperty(end_time);
		this.device_addr = new SimpleStringProperty(device_addr);
		this.device_name = new SimpleStringProperty(device_name);
		this.use_cash = new SimpleStringProperty(use_cash);
		this.use_card = new SimpleStringProperty(use_card);
		this.card_num = new SimpleStringProperty(card_num);
		this.remain_card = new SimpleStringProperty(remain_card);
	}

	public StringProperty getNoProperty() {
		return no;
	}
	
	public void setNo(String no) {
		this.no.set(no);
	}
	
	public String getNo() {
		return no.get();
	}

	public StringProperty getEndTimeProperty() {
		return end_time;
	}
	
	public void setEndTime(String end_time) {
		this.end_time.set(end_time);
	}
	
	public String getEndTime() {
		return end_time.get();
	}

	public StringProperty getDeviceAddrProperty() {
		return device_addr;
	}
	
	public void setDeviceAddr(String device_addr) {
		this.device_addr.set(device_addr);
	}
	
	public String getDeviceAddr() {
		return device_addr.get();
	}

	public StringProperty getDeviceNameProperty() {
		return device_name;
	}
	
	public void setDeviceName(String device_name) {
		this.device_name.set(device_name);
	}
	
	public String getDeviceName() {
		return device_name.get();
	}

	public StringProperty getUseCashProperty() {
		return use_cash;
	}
	
	public void setUseCash(String use_cash) {
		this.use_cash.set(use_cash);
	}
	
	public String getUseCash() {
		return use_cash.get();
	}

	public StringProperty getUseCardProperty() {
		return use_card;
	}
	
	public void setUseCard(String use_card) {
		this.use_card.set(use_card);
	}
	
	public String getUseCard() {
		return use_card.get();
	}

	public StringProperty getCardNumProperty() {
		return card_num;
	}
	
	public void setgetCardNum(String card_num) {
		this.card_num.set(card_num);
	}
	
	public String getCardNum() {
		return card_num.get();
	}

	public StringProperty getRemainCardProperty() {
		return remain_card;
	}
	
	public void setRemainCard(String remain_card) {
		this.remain_card.set(remain_card);
	}
	
	public String getRemainCard() {
		return remain_card.get();
	}
	
	
}
