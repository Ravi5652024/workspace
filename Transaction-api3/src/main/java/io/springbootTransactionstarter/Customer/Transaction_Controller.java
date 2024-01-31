package io.springbootTransactionstarter.Customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class Transaction_Controller {

	@Autowired //marks as dependency injection(assign) 
	// basically look for the instance if created
	private TransactionService transactionService;// done to make only one instance
	
	//GET Request (read)
	
	@RequestMapping("/ShoppingDetail")
	//This will automatically convert list of object or variabe to json
	//This function used to return all topics only
	public List<Transaction_Details> getAllTransactionDetails() {
		return  transactionService.getAllTransaction();
	}
	//This function used to return specific topic only
		
	@RequestMapping("/ShoppingDetail/{trans_id}")
	public Transaction_Details getTransactionDetails(@PathVariable String trans_id) // This annotion refer this is same as pass in RequestMapping
	{
		return transactionService.getTransaction(trans_id);
	}	
	
	
	
	
	//POST Request(create )
	
	@RequestMapping(method=RequestMethod.POST,value="/ShoppingDetail") // this for pass funtion in requestion mapping and value is url
	public void addTransaction(@RequestBody Transaction_Details details) {
			transactionService.addTransaction(details);
			
			
		}
		
		
	//PUT Request(update)
		
	@RequestMapping(method=RequestMethod.PUT,value="/ShoppingDetail/{trans_id}") // this for pass funtion in requestion mapping and value is url
	public void updateTransaction(@RequestBody Transaction_Details details,@PathVariable String trans_id) {
		transactionService.updateTopic(trans_id,details);
				
				
	}
			
			
	//DELETE Request
		
	@RequestMapping(method=RequestMethod.DELETE,value="/ShoppingDetail/{trans_id}")
	public void  deleteTransaction(@PathVariable String trans_id) // This annotation refer this is same as pass in RequestMapping
	{
			transactionService.deleteTransaction(trans_id);
	}
	
	
	
	//Show Total Bill of all user
	@RequestMapping("/ShoppingDetail/bill")
	public List<CustomerBill> getAllCustomerBill() {
		return  transactionService.getAllCustomerBill();
	}
	
	
	@RequestMapping("/ShoppingDetail/bill/{cust_name}")
    public Double getTotalBillForCustomer(@PathVariable String cust_name) {
        return transactionService.getTotalBillForCustomer(cust_name);
    }
	
	
		

}
