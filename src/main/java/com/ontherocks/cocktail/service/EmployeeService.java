package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.EmployeeDto;
import com.ontherocks.cocktail.dto.SalaryRecordDto;
import com.ontherocks.cocktail.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    // 사장 ID를 사용하여 직원 등록하는 메소드
    public void createEmployee(EmployeeDto employeeDto, Integer ownerId) {
        // 등록할 직원의 userId는 ownerId로 설정
        employeeDto.setUserId(ownerId);

        // 직원 등록 로직
        employeeMapper.insertEmployee(employeeDto);
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeMapper.selectAllEmployees(); // 직원 목록 반환
    }

    public BigDecimal calculateSalary(Integer employeeId) {
        return employeeMapper.calculateSalary(employeeId); // 급여 계산
    }

    public EmployeeDto getEmployeeByName(String employeeName) {
        return employeeMapper.selectEmployeeByName(employeeName); // 직원 이름으로 직원 정보 조회
    }

    public void paySalary(String employeeName) {
        EmployeeDto employee = employeeMapper.selectEmployeeByName(employeeName);

        if (employee != null) {
            // 1. 직원의 급여를 계산
            BigDecimal totalSalary = employeeMapper.calculateSalary(employee.getEmployeeId());

            // 2. 급여 기록 생성
            SalaryRecordDto salaryRecord = new SalaryRecordDto();
            salaryRecord.setEmployeeId(employee.getEmployeeId());
            salaryRecord.setAmount(totalSalary);
            salaryRecord.setPaymentDate(LocalDate.now());

            // 3. 급여 기록 저장
            employeeMapper.insertSalaryRecord(salaryRecord);

            // 4. 마지막 월급 지급 날짜 업데이트
            employee.setLastResetDate(LocalDate.now());
            employeeMapper.updateEmployeeTotalHours(employee); // 수정된 employee를 저장

            // 5. 총 일한 시간 초기화
            employee.setTotalHoursWorked(BigDecimal.ZERO); // 총 일한 시간을 0으로 설정
            employeeMapper.updateEmployeeTotalHours(employee); // 수정된 employee를 저장
        } else {
            throw new IllegalArgumentException("직원이 존재하지 않습니다.");
        }
    }

    public void updateWorkHoursByName(String employeeName, BigDecimal hoursWorked) {
        // 직원 이름을 사용하여 직원 정보를 조회
        EmployeeDto employee = employeeMapper.selectEmployeeByName(employeeName);

        if (employee != null) {
            // 기존 총 일한 시간이 null인 경우를 처리
            BigDecimal totalHoursWorked = employee.getTotalHoursWorked();
            if (totalHoursWorked == null) {
                totalHoursWorked = BigDecimal.ZERO; // null일 경우 초기화
            }

            // 기존 총 일한 시간에 추가
            BigDecimal newTotalHours = totalHoursWorked.add(hoursWorked);
            employee.setTotalHoursWorked(newTotalHours);

            // 총 일한 시간을 업데이트하는 메소드 호출
            employeeMapper.updateEmployeeTotalHours(employee);
        } else {
            throw new IllegalArgumentException("직원이 존재하지 않습니다.");
        }
    }

}
