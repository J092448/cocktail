package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.CustomUserDetails;
import com.ontherocks.cocktail.dto.EmployeeDto;
import com.ontherocks.cocktail.dto.WorkHoursDto;
import com.ontherocks.cocktail.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listEmployees(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "pages/employeeList"; // 직원 목록을 보여주는 HTML 템플릿
    }

    @GetMapping("/new")
    public String newEmployeeForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증 정보 가져오기
        Integer ownerId = null;

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); // CustomUserDetails로 캐스팅
            ownerId = userDetails.getUserId(); // userId 가져오기
            System.out.println("Current ownerId from authentication: " + ownerId); // 로그 추가
        } else {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        model.addAttribute("ownerId", ownerId); // ownerId 모델에 추가
        model.addAttribute("employee", new EmployeeDto()); // 새로운 EmployeeDto 추가
        return "pages/employeeForm"; // 직원 등록 폼 HTML 템플릿
    }

    @PostMapping
    public String createEmployee(@ModelAttribute EmployeeDto employeeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증 정보 가져오기
        Integer ownerId;

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); // CustomUserDetails로 캐스팅
            ownerId = userDetails.getUserId(); // userId 가져오기
        } else {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        employeeService.createEmployee(employeeDto, ownerId); // 사장 ID를 전달하여 직원 등록
        return "redirect:/employees"; // 직원 목록 페이지로 리다이렉트
    }

    @GetMapping("/work-hours")
    public String showWorkHoursForm(Model model) {
        model.addAttribute("workHoursDto", new WorkHoursDto());
        return "pages/workHoursForm"; // workHoursForm.html로 포워딩
    }

    @PostMapping("/work-hours")
    public String updateWorkHours(@ModelAttribute WorkHoursDto workHoursDto) {
        // 직원 이름을 사용하여 직원 ID를 찾고, 근무 시간을 업데이트합니다.
        employeeService.updateWorkHoursByName(workHoursDto.getEmployeeName(), workHoursDto.getHoursWorked());
        return "redirect:/employees"; // 직원 목록 페이지로 리디렉션
    }

    @GetMapping("/pay-salary")
    public String showPaySalaryForm(@RequestParam String employeeName, Model model) {
        EmployeeDto employee = employeeService.getEmployeeByName(employeeName); // Service를 통해 직원 정보 조회

        if (employee != null) {
            // 급여 계산
            BigDecimal totalSalary = employeeService.calculateSalary(employee.getEmployeeId()); // Service를 통해 급여 계산

            model.addAttribute("employee", employee);
            model.addAttribute("calculatedSalary", totalSalary);
            return "pages/pay-salary"; // 급여 지급 페이지로 이동
        } else {
            throw new IllegalArgumentException("직원이 존재하지 않습니다.");
        }
    }


    @PostMapping("/pay-salary")
    public String paySalary(@RequestParam String employeeName) {
        employeeService.paySalary(employeeName);
        return "redirect:/employees"; // 직원 목록 페이지로 리디렉션
    }
}
