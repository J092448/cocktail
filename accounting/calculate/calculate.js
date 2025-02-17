document.addEventListener("DOMContentLoaded", function () {
  // 데이터베이스에서 데이터 가져오기
  function fetchDataFromDatabase() {
    return fetch("/api/data")
      .then((response) => response.json())
      .then((data) => {
        return data;
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }

  // 숫자를 세 자리마다 쉼표로 포맷하는 함수
  function formatNumber(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }

  // 데이터베이스에서 불러온 값을 HTML에 반영
  function updateHTMLWithData(data) {
    Object.keys(data).forEach((key) => {
      const element = document.getElementById(key);
      if (element) {
        element.textContent = formatNumber(data[key]);
      }
    });
  }

  // 데이터베이스에서 데이터 가져오기
  const { currentData, previousData } = fetchDataFromDatabase();

  // 현재 데이터베이스에서 불러온 값을 HTML에 반영
  updateHTMLWithData(currentData);

  // 전월 데이터베이스에서 불러온 값을 HTML에 반영
  updateHTMLWithData(previousData);

  // 사용자 입력 필드를 포함한 계산 함수
  function calculateFinancials() {
    const salesCurrent = parseFloat(document.getElementById("salesCurrent").textContent.replace(/,/g, "")) || 0;
    const costOfSalesCurrent =
      parseFloat(document.getElementById("costOfSalesCurrent").textContent.replace(/,/g, "")) || 0;
    const productCostAuto = parseFloat(document.getElementById("productCostAuto").textContent.replace(/,/g, "")) || 0;
    const productCostManual = parseFloat(document.getElementById("productCostManual").value.replace(/,/g, "")) || 0;
    const beginningInventory = parseFloat(document.getElementById("beginningInventory").value.replace(/,/g, "")) || 0;
    const endingInventory = parseFloat(document.getElementById("endingInventory").value.replace(/,/g, "")) || 0;
    const salary = parseFloat(document.getElementById("salary").value.replace(/,/g, "")) || 0;
    const freight = parseFloat(document.getElementById("freight").value.replace(/,/g, "")) || 0;
    const officeSupplies = parseFloat(document.getElementById("officeSupplies").value.replace(/,/g, "")) || 0;
    const rent = parseFloat(document.getElementById("rent").value.replace(/,/g, "")) || 0;
    const nonOperatingIncomeCurrent =
      parseFloat(document.getElementById("nonOperatingIncomeCurrent").textContent.replace(/,/g, "")) || 0;
    const nonOperatingExpenseCurrent =
      parseFloat(document.getElementById("nonOperatingExpenseCurrent").textContent.replace(/,/g, "")) || 0;
    const taxExpenseCurrent =
      parseFloat(document.getElementById("taxExpenseCurrent").textContent.replace(/,/g, "")) || 0;

    const costOfSales = productCostAuto + productCostManual + beginningInventory - endingInventory;
    document.getElementById("costOfSalesCurrent").textContent = formatNumber(costOfSales);

    const grossProfit = salesCurrent - costOfSales;
    document.getElementById("grossProfitCurrent").textContent = formatNumber(grossProfit);

    const sgAndA = salary + freight + officeSupplies + rent;
    document.getElementById("sgAndACurrent").textContent = formatNumber(sgAndA);

    const operatingIncome = grossProfit - sgAndA;
    document.getElementById("operatingIncomeCurrent").textContent = formatNumber(operatingIncome);

    const preTaxIncome = operatingIncome - nonOperatingExpenseCurrent + nonOperatingIncomeCurrent;
    document.getElementById("preTaxIncomeCurrent").textContent = formatNumber(preTaxIncome);

    const netIncome = preTaxIncome - taxExpenseCurrent;
    document.getElementById("netIncomeCurrent").textContent = formatNumber(netIncome);

    // 전월 데이터를 위한 계산식
    const salesPrevious = parseFloat(document.getElementById("salesPrevious").textContent.replace(/,/g, "")) || 0;
    const costOfSalesPrevious =
      parseFloat(document.getElementById("costOfSalesPrevious").textContent.replace(/,/g, "")) || 0;
    const productCostAutoPrev =
      parseFloat(document.getElementById("productCostAutoPrev").textContent.replace(/,/g, "")) || 0;
    const productCostManualPrev =
      parseFloat(document.getElementById("productCostManualPrev").textContent.replace(/,/g, "")) || 0;
    const beginningInventoryPrev =
      parseFloat(document.getElementById("beginningInventoryPrev").textContent.replace(/,/g, "")) || 0;
    const endingInventoryPrev =
      parseFloat(document.getElementById("endingInventoryPrev").textContent.replace(/,/g, "")) || 0;
    const salaryPrevious = parseFloat(document.getElementById("salaryPrevious").textContent.replace(/,/g, "")) || 0;
    const freightPrevious = parseFloat(document.getElementById("freightPrevious").textContent.replace(/,/g, "")) || 0;
    const officeSuppliesPrevious =
      parseFloat(document.getElementById("officeSuppliesPrevious").textContent.replace(/,/g, "")) || 0;
    const rentPrevious = parseFloat(document.getElementById("rentPrevious").textContent.replace(/,/g, "")) || 0;
    const nonOperatingIncomePrevious =
      parseFloat(document.getElementById("nonOperatingIncomePrevious").textContent.replace(/,/g, "")) || 0;
    const nonOperatingExpensePrevious =
      parseFloat(document.getElementById("nonOperatingExpensePrevious").textContent.replace(/,/g, "")) || 0;
    const taxExpensePrevious =
      parseFloat(document.getElementById("taxExpensePrevious").textContent.replace(/,/g, "")) || 0;

    const costOfSalesPrev = productCostAutoPrev + productCostManualPrev + beginningInventoryPrev - endingInventoryPrev;
    document.getElementById("costOfSalesPrevious").textContent = formatNumber(costOfSalesPrev);

    const grossProfitPrev = salesPrevious - costOfSalesPrev;
    document.getElementById("grossProfitPrevious").textContent = formatNumber(grossProfitPrev);

    const sgAndAPrev = salaryPrevious + freightPrevious + officeSuppliesPrevious + rentPrevious;
    document.getElementById("sgAndAPrevious").textContent = formatNumber(sgAndAPrev);

    const operatingIncomePrev = grossProfitPrev - sgAndAPrev;
    document.getElementById("operatingIncomePrevious").textContent = formatNumber(operatingIncomePrev);

    const preTaxIncomePrev = operatingIncomePrev - nonOperatingExpensePrevious + nonOperatingIncomePrevious;
    document.getElementById("preTaxIncomePrevious").textContent = formatNumber(preTaxIncomePrev);

    const netIncomePrev = preTaxIncomePrev - taxExpensePrevious;
    document.getElementById("netIncomePrevious").textContent = formatNumber(netIncomePrev);
  }

  // 사용자 입력 필드 변경 시 모든 계산 재실행
  document.getElementById("productCostManual").addEventListener("input", calculateFinancials);
  document.getElementById("beginningInventory").addEventListener("input", calculateFinancials);
  document.getElementById("endingInventory").addEventListener("input", calculateFinancials);
  document.getElementById("salary").addEventListener("input", calculateFinancials);
  document.getElementById("freight").addEventListener("input", calculateFinancials);
  document.getElementById("officeSupplies").addEventListener("input", calculateFinancials);
  document.getElementById("rent").addEventListener("input", calculateFinancials);

  // 초기 계산 실행
  calculateFinancials();

  // 사용자 입력 필드에 세 자리마다 쉼표를 추가하는 이벤트
  document.querySelectorAll(".amount input").forEach((input) => {
    input.addEventListener("input", function () {
      input.value = formatNumber(input.value.replace(/,/g, ""));
    });
  });
});
