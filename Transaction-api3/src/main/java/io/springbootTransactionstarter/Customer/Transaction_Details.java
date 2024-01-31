package io.springbootTransactionstarter.Customer;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction_Details {
	private String trans_id;
	private String cust_name;
	private String purchased_item;
	private int amount;
//	public Transaction_Details() {
//		
//	}
//	
//	public Transaction_Details(String trans_id, String cust_name, String purchased_item, int amount) {
//		super();
//		this.trans_id = trans_id;
//		this.cust_name = cust_name;
//		this.purchased_item = purchased_item;
//		this.amount = amount;
//	}
//	
//	
//
//	public String getTrans_id() {
//		return trans_id;
//	}
//
//	public void setCust_id(String cust_id) {
//		this.trans_id = trans_id;
//	}
//
//	public String getCust_name() {
//		return cust_name;
//	}
//
//	public void setCust_name(String cust_name) {
//		this.cust_name = cust_name;
//	}
//
//	public String getPurchased_item() {
//		return purchased_item;
//	}
//
//	public void setPurchased_item(String purchased_item) {
//		this.purchased_item = purchased_item;
//	}
//
//	public int getAmount() {
//		return amount;
//	}
//
//	public void setAmount(int amount) {
//		this.amount = amount;
//	}
//	
//	
//	
	

}

