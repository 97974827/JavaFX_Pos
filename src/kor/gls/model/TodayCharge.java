package kor.gls.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// ���� ���� ���� �� Ŭ����  
public class TodayCharge {

	private final StringProperty no;			// ����ȣ 
	private final StringProperty end_time;		// ����ð�
	private final StringProperty device_addr;	// ����ȣ
	private final StringProperty device_name;	// ����
	private final StringProperty current_money; // ���Աݾ�
	private final StringProperty charge;		// ������
	private final StringProperty bonus;			// ���� 
	private final StringProperty remain_card;	// ī���ܾ�
	private final StringProperty card_num;		// ī���ȣ
	
	public TodayCharge() {
		this(null, null, null, null, null, null, null, null, null);
	}
	
	public TodayCharge(String no, String end_time, String device_addr, String device_name, String current_money, String bonus, String charge, String remain_card, String card_num) {
		this.no = new SimpleStringProperty(no);
		this.end_time = new SimpleStringProperty(end_time);
		this.device_addr = new SimpleStringProperty(device_addr);
		this.device_name = new SimpleStringProperty(device_name);
		this.current_money = new SimpleStringProperty(current_money);
		this.bonus = new SimpleStringProperty(bonus);
		this.charge = new SimpleStringProperty(charge);
		this.remain_card = new SimpleStringProperty(remain_card);
		this.card_num = new SimpleStringProperty(card_num);
	}
	
	public String getNo() {
		return no.get();
	}
	
	public void setNo(String no) {
		this.no.set(no);
	}
	
	public StringProperty noProperty() {
		return no;
	}
	
	public String getEndTime() {
		return end_time.get();
	}
	
	public void setEndTime(String end_time) {
		this.end_time.set(end_time);
	}
	
	public StringProperty endTimeProperty() {
		return end_time;
	}
	
	public String getDeviceName() {
		return device_name.get();
	}
	
	public void setDeviceName(String device_name) {
		this.device_name.set(device_name);
	}
	
	public StringProperty deviceNameProperty() {
		return device_name;
	}

	public String getDeviceAddr() {
		return device_addr.get();
	}
	
	public void setDeviceAddr(String device_addr) {
		this.device_addr.set(device_addr);
	}
	
	public StringProperty deviceAddrProperty() {
		return device_addr;
	}

	public String getCardNum() {
		return card_num.get();
	}
	
	public void setCardNum(String card_num) {
		this.card_num.set(card_num);
	}
	
	public StringProperty cardNumProperty() {
		return card_num;
	}
	
	public String getRemainCard() {
		return remain_card.get();
	}
	
	public void setRemainCard(String remain_card) {
		this.remain_card.set(remain_card);
	}
	
	public StringProperty remainCardProperty() {
		return remain_card;
	}
	
	public String getCurrentMoney() {
		return current_money.get();
	}
	
	public void setCurrentMoney(String current_money) {
		this.current_money.set(current_money);
	}
	
	public StringProperty currentMoneyProperty() {
		return current_money;
	}
	
	public String getCharge() {
		return charge.get();
	}
	
	public void setCharge(String charge) {
		this.charge.set(charge);
	}
	
	public StringProperty chargeProperty() {
		return charge;
	}
	
	public String getBonus() {
		return bonus.get();
	}
	
	public void setBonus(String bonus) {
		this.bonus.set(bonus);
	}
	
	public StringProperty bonusProperty() {
		return bonus;
	}

}
