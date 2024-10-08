package com.intbyte.wizbuddy.user.controller;

import com.intbyte.wizbuddy.security.JwtUtil;
import com.intbyte.wizbuddy.user.domain.info.*;
import com.intbyte.wizbuddy.user.dto.EmployeeDTO;
import com.intbyte.wizbuddy.user.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.service.EmployeeService;
import com.intbyte.wizbuddy.user.service.EmployerService;
import com.intbyte.wizbuddy.user.service.UserService;
import com.intbyte.wizbuddy.user.vo.request.*;
import com.intbyte.wizbuddy.user.vo.response.*;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private JwtUtil jwtUtil;
    private Environment env;
    private ModelMapper modelMapper;
    private UserService userService;
    private EmployerService employerService;
    private EmployeeService employeeService;

    @Autowired
    public UserController(JwtUtil jwtUtil, Environment env, ModelMapper modelMapper, UserService userService, EmployerService employerService, EmployeeService employeeService) {
        this.jwtUtil = jwtUtil;
        this.env = env;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.employerService = employerService;
        this.employeeService = employeeService;
    }

    @Operation(summary = "사장 회원가입")
    @PostMapping("/employer")
    public ResponseEntity<ResponseInsertEmployerVO> registerEmployer(@RequestBody RequestInsertEmployerVO request) {
        SignInUserInfo signInUserInfo = modelMapper.map(request.getNewUser(), SignInUserInfo.class);
        RegisterEmployerInfo registerEmployerInfo = modelMapper.map(request.getNewEmployer(), RegisterEmployerInfo.class);

        ResponseInsertEmployerVO responseUser = userService.signInEmployer(signInUserInfo, registerEmployerInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Operation(summary = "직원 회원가입")
    @PostMapping("/employee")
    public ResponseEntity<ResponseInsertEmployeeVO> registerEmployee(@RequestBody RequestInsertEmployeeVO request) {
        SignInUserInfo signInUserInfo = modelMapper.map(request.getNewUser(), SignInUserInfo.class);
        RegisterEmployeeInfo registerEmployeeInfo = modelMapper.map(request.getNewEmployee(), RegisterEmployeeInfo.class);

        ResponseInsertEmployeeVO responseUser =  userService.signInEmployee(signInUserInfo, registerEmployeeInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Operation(summary = "관리자 - 사장 전체 조회")
    @GetMapping("/employers")
    public ResponseEntity<List<EmployerDTO>> getEmployers() {
        List<EmployerDTO> response = employerService.findAllEmployer();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "관리자 - 직원 전체 조회")
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        List<EmployeeDTO> response = employeeService.findAllEmployee();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "사장 단 건 조회")
    @GetMapping("employer/{employerCode}")
    public ResponseEntity<ResponseFindEmployerVO> getEmployer(@PathVariable("employerCode") String employerCode) {
        EmployerDTO employerDTO = employerService.getByEmployerCode(employerCode);

        ResponseFindEmployerVO findUser = modelMapper.map(employerDTO, ResponseFindEmployerVO.class);

        return ResponseEntity.status(HttpStatus.OK).body(findUser);
    }

    @Operation(summary = "직원 단 건 조회")
    @GetMapping("employee/{employeeCode}")
    public ResponseEntity<ResponseFindEmployeeVO> getEmployee(@PathVariable("employeeCode") String employeeCode) {
        EmployeeDTO employeeDTO = employeeService.getByEmployeeCode(employeeCode);

        ResponseFindEmployeeVO findUser = modelMapper.map(employeeDTO, ResponseFindEmployeeVO.class);

        return ResponseEntity.status(HttpStatus.OK).body(findUser);
    }

    @Operation(summary = "사장 정보 수정")
    @PatchMapping("/employer/edit")
    public ResponseEntity<Void> modifyEmployer(@RequestBody RequestEditEmployerVO request) {
        EditEmployerInfo editEmployerInfo = modelMapper.map(request, EditEmployerInfo.class);

        employerService.modifyEmployer(editEmployerInfo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "직원 정보 수정")
    @PatchMapping("/employee/edit")
    public ResponseEntity<Void> modifyEmployer(@RequestBody RequestEditEmployeeVO request) {
        EditEmployeeInfo editEmployeeInfo = modelMapper.map(request, EditEmployeeInfo.class);

        employeeService.modifyEmployee(editEmployeeInfo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "사장 탈퇴 요청")
    @PatchMapping("/employer/delete")
    public ResponseEntity<Void> deleteEmployer(@RequestBody RequestDeleteEmployerVO request) {
        DeleteEmployerInfo deleteEmployerInfo = modelMapper.map(request, DeleteEmployerInfo.class);

        employerService.deleteEmployer(deleteEmployerInfo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "직원 탈퇴 요청")
    @PatchMapping("/employee/delete")
    public ResponseEntity<Void> deleteEmployer(@RequestBody RequestDeleteEmployeeVO request) {
        DeleteEmployeeInfo deleteEmployeeInfo = modelMapper.map(request, DeleteEmployeeInfo.class);

        employeeService.deleteEmployee(deleteEmployeeInfo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}