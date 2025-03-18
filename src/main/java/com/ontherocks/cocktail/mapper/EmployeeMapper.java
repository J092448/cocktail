package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.EmployeeDto;
import com.ontherocks.cocktail.dto.SalaryRecordDto;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    void insertEmployee(EmployeeDto employeeDto); // 직원 등록
    List<EmployeeDto> selectAllEmployees(); // 직원 목록 조회
    BigDecimal calculateSalary(Integer employeeId); // 급여 계산
    EmployeeDto selectEmployeeByName(String employeeName);
    void updateEmployeeTotalHours(EmployeeDto employee);
    void insertSalaryRecord(SalaryRecordDto salaryRecord); // 급여 기록 저장 메서드 추가
}