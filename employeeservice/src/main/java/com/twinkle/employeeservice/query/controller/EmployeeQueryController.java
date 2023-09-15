package com.twinkle.employeeservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twinkle.employeeservice.EmployeeserviceApplication;
import com.twinkle.employeeservice.query.model.EmployeeReponseModel;
import com.twinkle.employeeservice.query.queries.GetAllEmployeeQuery;
import com.twinkle.employeeservice.query.queries.GetEmployeesQuery;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {
	
	@Autowired 
	private QueryGateway queryGateway;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeserviceApplication.class);
	
	@GetMapping("/{employeeId}")
    public EmployeeReponseModel getEmployeeDetail(@PathVariable String employeeId) {
        GetEmployeesQuery getEmployeesQuery = new GetEmployeesQuery();
        getEmployeesQuery.setEmployeeId(employeeId);

        EmployeeReponseModel employeeReponseModel =
        queryGateway.query(getEmployeesQuery,
                ResponseTypes.instanceOf(EmployeeReponseModel.class))
                .join();

        return employeeReponseModel;
    }
	@GetMapping
	public List<EmployeeReponseModel> getAllEmployee(){
		GetAllEmployeeQuery getAllEmployeeQuery = new GetAllEmployeeQuery();
		List<EmployeeReponseModel> list = queryGateway.query(
				getAllEmployeeQuery, 
				ResponseTypes.multipleInstancesOf(EmployeeReponseModel.class))
				.join();
		logger.info("Danh sach nhan vien: " + list.toString());
		return list;
	}
}
