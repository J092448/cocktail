package com.ontherocks.cocktail.model;

// Lombok 라이브러리 import: getter와 setter 메서드를 자동으로 생성

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

// Lombok 애노테이션: getter와 setter 메서드 자동 생성
@Getter
@Setter
// 회계 데이터를 저장 및 전달하기 위한 Data Transfer Object (DTO) 클래스
public class AccountingDataDTO {

    // 고유한 회계 데이터 ID
    private Long accountingId;

    // 회계 연도 (예: 2023)
    private Integer year;

    // 회계 월 (예: 1 ~ 12)
    private Integer month;

    // 총 매출 금액
    private BigDecimal sales;

    // 매출원가 (Cost of Sales)
    private BigDecimal costOfSales;

    // 자동으로 계산된 상품 원가
    private BigDecimal productCostAuto;

    // 수동으로 입력된 상품 원가
    private BigDecimal productCostManual;

    // 기초 재고 (시작 재고)
    private BigDecimal beginningInventory;

    // 기말 재고 (종료 재고)
    private BigDecimal endingInventory;

    // 매출 총이익 (Gross Profit)
    private BigDecimal grossProfit; // 추가된 필드

    // 판매관리비 (Selling, General & Administrative Expenses)
    private BigDecimal sgAndA; // 추가된 필드

    // 급여 비용
    private BigDecimal salary;

    // 운송비용
    private BigDecimal transportationCost;

    // 사무용품비
    private BigDecimal officeSuppliesCost;

    // 임대료 비용
    private BigDecimal rentExpense;

    // 영업이익 (Operating Income)
    private BigDecimal operatingIncome;

    // 영업외수익 (Non-operating Income)
    private BigDecimal nonOperatingIncome;

    // 영업외비용 (Non-operating Expense)
    private BigDecimal nonOperatingExpense;

    // 세전이익 (Pre-tax Income)
    private BigDecimal preTaxIncome;

    // 세금 비용 (Tax Expense)
    private BigDecimal taxExpense;

    // 당기순이익 (Net Income)
    private BigDecimal netIncome;

    // 사용자 ID (해당 데이터를 관리하는 사용자 식별자)
    private Integer userId;

    // 상품 매출
    private BigDecimal productSales;

    // 이전 월 데이터 관련 필드 (총 매출, 매출원가 등)
    private BigDecimal salesPrevious;
    private BigDecimal costOfSalesPrevious;
    private BigDecimal productCostAutoPrevious;
    private BigDecimal productCostManualPrevious;
    private BigDecimal beginningInventoryPrevious;
    private BigDecimal endingInventoryPrevious;
    private BigDecimal grossProfitPrevious;
    private BigDecimal sgAndAPrevious;
    private BigDecimal salaryPrevious;
    private BigDecimal transportationCostPrevious;
    private BigDecimal officeSuppliesCostPrevious;
    private BigDecimal rentExpensePrevious;
    private BigDecimal operatingIncomePrevious;
    private BigDecimal nonOperatingIncomePrevious;
    private BigDecimal nonOperatingExpensePrevious;
    private BigDecimal preTaxIncomePrevious;
    private BigDecimal taxExpensePrevious;
    private BigDecimal netIncomePrevious;

    // 날짜 필드 (데이터의 기준 날짜)
    private LocalDate date;

    // ✅ Lombok이 자동으로 생성하는 Getter 및 Setter 예시
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // ✅ `toString` 메서드 오버라이드: 객체 정보를 문자열로 표현
    @Override
    public String toString() {
        return "AccountingDataDTO{" +
                "date=" + date +
                ", userId=" + userId +
                ", sales=" + sales +
                ", costOfSales=" + costOfSales +
                ", productCostManual=" + productCostManual +
                ", beginningInventory=" + beginningInventory +
                ", endingInventory=" + endingInventory +
                ", grossProfit=" + grossProfit +
                ", sgAndA=" + sgAndA +
                ", salary=" + salary +
                ", transportationCost=" + transportationCost +
                ", officeSuppliesCost=" + officeSuppliesCost +
                ", rentExpense=" + rentExpense +
                ", operatingIncome=" + operatingIncome +
                ", nonOperatingIncome=" + nonOperatingIncome +
                ", nonOperatingExpense=" + nonOperatingExpense +
                ", preTaxIncome=" + preTaxIncome +
                ", taxExpense=" + taxExpense +
                ", netIncome=" + netIncome +
                ", month=" + month +
                ", year=" + year +
                ", productSales=" + productSales +
                '}';
    }

    // 추가적인 Getter와 Setter 명시 (Lombok 없이 사용할 경우)

    public Long getAccountingId() {
        return accountingId;
    }

    public void setAccountingId(Long accountingId) {
        this.accountingId = accountingId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    public BigDecimal getCostOfSales() {
        return costOfSales;
    }

    public void setCostOfSales(BigDecimal costOfSales) {
        this.costOfSales = costOfSales;
    }

    public BigDecimal getProductCostAuto() {
        return productCostAuto;
    }

    public void setProductCostAuto(BigDecimal productCostAuto) {
        this.productCostAuto = productCostAuto;
    }

    public BigDecimal getProductCostManual() {
        return productCostManual;
    }

    public void setProductCostManual(BigDecimal productCostManual) {
        this.productCostManual = productCostManual;
    }

    public BigDecimal getBeginningInventory() {
        return beginningInventory;
    }

    public void setBeginningInventory(BigDecimal beginningInventory) {
        this.beginningInventory = beginningInventory;
    }

    public BigDecimal getEndingInventory() {
        return endingInventory;
    }

    public void setEndingInventory(BigDecimal endingInventory) {
        this.endingInventory = endingInventory;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public BigDecimal getSgAndA() {
        return sgAndA;
    }

    public void setSgAndA(BigDecimal sgAndA) {
        this.sgAndA = sgAndA;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(BigDecimal transportationCost) {
        this.transportationCost = transportationCost;
    }

    public BigDecimal getOfficeSuppliesCost() {
        return officeSuppliesCost;
    }

    public void setOfficeSuppliesCost(BigDecimal officeSuppliesCost) {
        this.officeSuppliesCost = officeSuppliesCost;
    }

    public BigDecimal getRentExpense() {
        return rentExpense;
    }

    public void setRentExpense(BigDecimal rentExpense) {
        this.rentExpense = rentExpense;
    }

    public BigDecimal getOperatingIncome() {
        return operatingIncome;
    }

    public void setOperatingIncome(BigDecimal operatingIncome) {
        this.operatingIncome = operatingIncome;
    }

    public BigDecimal getNonOperatingIncome() {
        return nonOperatingIncome;
    }

    public void setNonOperatingIncome(BigDecimal nonOperatingIncome) {
        this.nonOperatingIncome = nonOperatingIncome;
    }

    public BigDecimal getNonOperatingExpense() {
        return nonOperatingExpense;
    }

    public void setNonOperatingExpense(BigDecimal nonOperatingExpense) {
        this.nonOperatingExpense = nonOperatingExpense;
    }

    public BigDecimal getPreTaxIncome() {
        return preTaxIncome;
    }

    public void setPreTaxIncome(BigDecimal preTaxIncome) {
        this.preTaxIncome = preTaxIncome;
    }

    public BigDecimal getTaxExpense() {
        return taxExpense;
    }

    public void setTaxExpense(BigDecimal taxExpense) {
        this.taxExpense = taxExpense;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

        public BigDecimal getProductSales() {
        return productSales;
    }

    public void setProductSales(BigDecimal productSales) {
        this.productSales = productSales;
    }

    public BigDecimal getSalesPrevious() {
        return salesPrevious;
    }

    public void setSalesPrevious(BigDecimal salesPrevious) {
        this.salesPrevious = salesPrevious;
    }

    public BigDecimal getCostOfSalesPrevious() {
        return costOfSalesPrevious;
    }

    public void setCostOfSalesPrevious(BigDecimal costOfSalesPrevious) {
        this.costOfSalesPrevious = costOfSalesPrevious;
    }

    public BigDecimal getProductCostAutoPrevious() {
        return productCostAutoPrevious;
    }

    public void setProductCostAutoPrevious(BigDecimal productCostAutoPrevious) {
        this.productCostAutoPrevious = productCostAutoPrevious;
    }

    public BigDecimal getProductCostManualPrevious() {
        return productCostManualPrevious;
    }

    public void setProductCostManualPrevious(BigDecimal productCostManualPrevious) {
        this.productCostManualPrevious = productCostManualPrevious;
    }

    public BigDecimal getBeginningInventoryPrevious() {
        return beginningInventoryPrevious;
    }

    public void setBeginningInventoryPrevious(BigDecimal beginningInventoryPrevious) {
        this.beginningInventoryPrevious = beginningInventoryPrevious;
    }

    public BigDecimal getEndingInventoryPrevious() {
        return endingInventoryPrevious;
    }

    public void setEndingInventoryPrevious(BigDecimal endingInventoryPrevious) {
        this.endingInventoryPrevious = endingInventoryPrevious;
    }

    public BigDecimal getGrossProfitPrevious() {
        return grossProfitPrevious;
    }

    public void setGrossProfitPrevious(BigDecimal grossProfitPrevious) {
        this.grossProfitPrevious = grossProfitPrevious;
    }

    public BigDecimal getSgAndAPrevious() {
        return sgAndAPrevious;
    }

    public void setSgAndAPrevious(BigDecimal sgAndAPrevious) {
        this.sgAndAPrevious = sgAndAPrevious;
    }

    public BigDecimal getSalaryPrevious() {
        return salaryPrevious;
    }

    public void setSalaryPrevious(BigDecimal salaryPrevious) {
        this.salaryPrevious = salaryPrevious;
    }

    public BigDecimal getTransportationCostPrevious() {
        return transportationCostPrevious;
    }

    public void setTransportationCostPrevious(BigDecimal transportationCostPrevious) {
        this.transportationCostPrevious = transportationCostPrevious;
    }

    public BigDecimal getOfficeSuppliesCostPrevious() {
        return officeSuppliesCostPrevious;
    }

    public void setOfficeSuppliesCostPrevious(BigDecimal officeSuppliesCostPrevious) {
        this.officeSuppliesCostPrevious = officeSuppliesCostPrevious;
    }

    public BigDecimal getRentExpensePrevious() {
        return rentExpensePrevious;
    }

    public void setRentExpensePrevious(BigDecimal rentExpensePrevious) {
        this.rentExpensePrevious = rentExpensePrevious;
    }

    public BigDecimal getOperatingIncomePrevious() {
        return operatingIncomePrevious;
    }

    public void setOperatingIncomePrevious(BigDecimal operatingIncomePrevious) {
        this.operatingIncomePrevious = operatingIncomePrevious;
    }

    public BigDecimal getNonOperatingIncomePrevious() {
        return nonOperatingIncomePrevious;
    }

    public void setNonOperatingIncomePrevious(BigDecimal nonOperatingIncomePrevious) {
        this.nonOperatingIncomePrevious = nonOperatingIncomePrevious;
    }

    public BigDecimal getNonOperatingExpensePrevious() {
        return nonOperatingExpensePrevious;
    }

    public void setNonOperatingExpensePrevious(BigDecimal nonOperatingExpensePrevious) {
        this.nonOperatingExpensePrevious = nonOperatingExpensePrevious;
    }

    public BigDecimal getPreTaxIncomePrevious() {
        return preTaxIncomePrevious;
    }

    public void setPreTaxIncomePrevious(BigDecimal preTaxIncomePrevious) {
        this.preTaxIncomePrevious = preTaxIncomePrevious;
    }

    public BigDecimal getTaxExpensePrevious() {
        return taxExpensePrevious;
    }

    public void setTaxExpensePrevious(BigDecimal taxExpensePrevious) {
        this.taxExpensePrevious = taxExpensePrevious;
    }

    public BigDecimal getNetIncomePrevious() {
        return netIncomePrevious;
    }

    public void setNetIncomePrevious(BigDecimal netIncomePrevious) {
        this.netIncomePrevious = netIncomePrevious;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
