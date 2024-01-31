package io.springbootTransactionstarter.Customer;

public class CustomerBill {
	
	 private String customerName;
	 private double totalBill;
	    
	 public CustomerBill(String customerName, double totalBill) {
	        this.customerName = customerName;
	        this.totalBill = totalBill;
	    }

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}
	 
}
