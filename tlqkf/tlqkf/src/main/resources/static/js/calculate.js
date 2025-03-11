document.addEventListener("DOMContentLoaded", () => {
  // 숫자를 세 자리마다 쉼표로 포맷하는 함수
  function formatNumber(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }

  // 데이터베이스에서 불러온 값을 HTML에 반영
  function updateHTMLWithData(data) {
    Object.keys(data).forEach((key) => {
      const element = document.getElementById(key);
      if (element) {
        element.textContent = formatNumber(data[key] || 0);
      }
    });
  }

  // 서버에서 당월 회계 데이터를 불러와 HTML에 반영
  fetch("/api/accounting/current")
      .then(response => response.json())
      .then(data => {
        document.getElementById("salesCurrent").textContent = data.productSales || 0;
        document.getElementById("salary").value = data.salary || 0;
        document.getElementById("freight").value = data.transportationCost || 0;
        document.getElementById("officeSupplies").value = data.officeSuppliesCost || 0;
        document.getElementById("rent").value = data.rentExpense || 0;
      });

  // 데이터베이스에서 값을 가져와 HTML에 반영
  fetch("/currentData")
      .then((response) => response.json())
      .then((data) => updateHTMLWithData(data))
      .catch((error) => console.error("Error fetching current data:", error));

  fetch("/previousData")
      .then((response) => response.json())
      .then((data) => updateHTMLWithData(data))
      .catch((error) => console.error("Error fetching previous data:", error));

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

  // 사용자 입력 필드에 세 자리마다 쉼표를 추가하는 함수
  function formatNumberInput(input) {
    const value = input.value.replace(/,/g, ""); // 기존 쉼표 제거
    if (!isNaN(value) && value !== "") {
      input.value = formatNumber(value); // 세 자리마다 쉼표 추가
    } else {
      input.value = "0"; // 입력값이 없으면 0으로 설정
    }
  }

  // 사용자 입력 필드를 클릭하면 초기값 0 제거하는 이벤트
  document.querySelectorAll(".amount input").forEach((input) => {
    input.addEventListener("focus", function () {
      if (input.value === "0") {
        input.value = ""; // 클릭할 때 "0" 제거
      }
    });

    input.addEventListener("blur", function () {
      if (input.value === "") {
        input.value = "0"; // 입력값이 없으면 0으로 설정
      } else {
        formatNumberInput(input); // 입력창에서 벗어나면 세 자리마다 쉼표 추가
      }
      calculateFinancials(); // 사용자 입력 후 계산 함수 호출
    });

    input.addEventListener("input", function () {
      input.value = input.value.replace(/[^0-9]/g, ""); // 숫자만 입력 가능
    });
  });
  function saveData() {
    // 폼 데이터 수집
    const form = document.getElementById('dataForm');
    const formData = new FormData(form);

    // JSON 형식으로 데이터 변환
    const data = {};
    formData.forEach((value, key) => {
      data[key] = value;
    });

    // 서버로 데이터 전송
    fetch('/save-data', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log('Success:', data);
          alert('데이터가 성공적으로 저장되었습니다!');
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('데이터 저장 중 오류가 발생했습니다.');
        });
  }

  // 초기 계산 실행
  calculateFinancials();
});
