package io.springbootTransactionstarter.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TransactionService {

	private List<Transaction_Details> details=new ArrayList<>(Arrays.asList(
			new Transaction_Details("1","Ravi","Phone",10000),
			new Transaction_Details("2","Archit","Television",18000),
			new Transaction_Details("3","Rajat","Fridge",12000),
			new Transaction_Details("4","Ravi","Trolley",6000),
			new Transaction_Details("5","Rajat","Clothes",3000)
			));
	
	// Return all topic
	public List<Transaction_Details> getAllTransaction(){
		return details;
	}
	
	//Return only specific topic
	public Transaction_Details getTransaction(String trans_id) {
		return details.stream().filter(t -> t.getTrans_id().equals(trans_id)).findFirst().get();
		// This is way to iterate over list of topics and matches the id the pass in the function		
	}
	
	public void addTransaction(Transaction_Details detail) {
		
		details.add(detail);// add is list fuction
	}
	
	public void updateTopic(String trans_id,Transaction_Details detail ) {
		
		//Upadate code if matches
		for(int i=0;i<details.size();i++) {
			Transaction_Details t=details.get(i);
			if (t.getTrans_id().equals(trans_id)) {
				details.set(i, detail);
				return;
			}
		}
		
	}
	
	public void  deleteTransaction(String trans_id) {
		details.removeIf(t -> t.getTrans_id().equals(trans_id));
	
	}
	

	// For all Customer
	public List<CustomerBill> getAllCustomerBill() {
        // Group transactions by customer name and calculate total bill for each customer
        Map<String, Double> customerBills = details.stream()
                .collect(Collectors.groupingBy(Transaction_Details::getCust_name,
                        Collectors.summingDouble(Transaction_Details::getAmount)));

        // Convert the map to a list of CustomerBill objects
        List<CustomerBill> result = customerBills.entrySet().stream()
                .map(entry -> new CustomerBill(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return result;
    }
	
	
	// For specific customer
	public Double getTotalBillForCustomer(String customerName) {
        // Calculate the total bill for a specific customer
        Double totalBill = details.stream()
                .filter(transaction -> transaction.getCust_name().equals(customerName))
                .mapToDouble(Transaction_Details::getAmount)
                .sum();

        return totalBill;
    }
	
	
	
	
	
	
}
	
	
	
	


//basically just creating singleton class
