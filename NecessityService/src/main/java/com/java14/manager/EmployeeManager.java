package com.java14.manager;


import com.java14.dto.response.EmployeeAuthIdResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(url = "http://localhost:9094/api/v1/employee", name = "employeemanager")
public interface EmployeeManager {

    @GetMapping("/get-employee-by-authId")
     EmployeeAuthIdResponseDto getEmployeeByAuthId(@RequestParam Long authId);

    @GetMapping("/get-mail-by-id")
    String getEmployeeByEmail(@RequestParam String id);

    @GetMapping("/years-leave-count/{id}/{yearsLeave}")
     ResponseEntity<Boolean> yearsLeaveCount(@PathVariable String id, @PathVariable Integer yearsLeave);
    @GetMapping("/get-employee-name-by-id")
    String getEmployeeNameById(@RequestParam String id) ;


    @GetMapping("/get-employee-surname-by-id")
    String getEmployeeSurnameById(@RequestParam String id) ;
    @GetMapping ("/getEmployeeIdByToken")
     String getEmployeeIdByToken(@RequestParam String token);


    @PostMapping("/get-salary-with-amount")
     Boolean getSalaryWithAmount (@RequestParam String employeeId,@RequestParam Double amount);


}

