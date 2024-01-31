package com.api.coolclub.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.api.coolclub.models.request.AddOperator;
import com.api.coolclub.models.request.UpdateOperatorReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetAllOperatorsRes;

/*
 * @Author Rohan_Sharma
*/

public interface OperatorService {

    /*
	 * ADD OPERATOR
     * @params (Operator data)
	*/
    public ResponseEntity<GenericRes<String>> addOperator(AddOperator operatorData);

    /*
	 * GET OPERATOR
	*/
    public ResponseEntity<GenericRes<List<GetAllOperatorsRes>>> getAllOperator();   

    /*
	 * UPDATE OPERATOR
     * @params (Operator data)
	*/
    public ResponseEntity<GenericRes<String>> updateOperator(UpdateOperatorReq operatorData);
}
