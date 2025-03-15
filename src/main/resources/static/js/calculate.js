document.addEventListener("DOMContentLoaded", (event) => {
  // 숫자를 세 자리마다 쉼표로 포맷하는 함수
  function formatNumber(number) {
    return Math.floor(number).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }

  // 숫자에서 소수점 이하를 제거하는 함수
  function removeDecimal(value) {
    return Math.floor(value); // 소수점 이하를 버림
  }

  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth() + 1;
  let previousYear = currentYear;
  let previousMonth = currentMonth - 1;

  if (previousMonth === 0) {
    previousMonth = 12;
    previousYear--;

  }
  console.log(`📌 현재 연도: ${currentYear}, 현재 월: ${currentMonth}`);
  console.log(`📌 전월 연도: ${previousYear}, 전월 월: ${previousMonth}`);

  // ✅ 요소 업데이트 함수 (null 값 방지)
  function updateElement(id, value) {
    const element = document.getElementById(id);
    if (element) {
      element.textContent = formatNumber(value !== null && value !== undefined ? value : 0);
    }
  }

  // ✅ 현재 월 데이터 업데이트 (사용자 입력 가능)
  function updateCurrentTable(data) {
    // 당월 매출액 처리 (외부 API에서 값을 가져와서 설정)
    fetch("/api/calendar/sales")
        .then(response => response.json())
        .then(salesData => {
          const totalSales = salesData.reduce((sum, entry) => sum + (entry.salesAmount || 0), 0);
          document.getElementById("salesCurrent").textContent = formatNumber(totalSales);
          calculateFinancials(); // 매출 데이터를 불러온 후 계산 실행
        })
        .catch(error => console.error("❌ 매출 데이터 불러오기 오류:", error));

    // 당월 상품매출원가(자동) 처리 (외부 API에서 값을 가져와서 설정)
    const productCostElement = document.getElementById("productCostAutoCurrent");
    if (productCostElement) { // 요소가 존재하는지 확인
      // 상품매출원가(자동)를 항상 API에서 받아오기
      fetch("/api/accounting/product-cost-auto")  // 서버 측 /product-cost-auto API 호출
          .then(response => response.json())
          .then(costData => {
            productCostElement.textContent = formatNumber(costData || 0);
          })
          .catch(error => console.error("❌ 상품 매출 원가 데이터 불러오기 오류:", error));
    } else {
      console.error("❌ 'productCostAutoCurrent' 요소가 없습니다. HTML에서 확인하세요.");
    }

    updateElement("costOfSalesCurrent", data.costOfSales);
    updateElement("productCostManual", data.productCostManual);
    updateElement("beginningInventory", data.beginningInventory);
    updateElement("endingInventory", data.endingInventory);
    updateElement("grossProfitCurrent", data.grossProfit);
    updateElement("sgAndACurrent", data.sgAndA);
    updateElement("salary", data.salary);
    updateElement("transportationCost", data.transportationCost);
    updateElement("officeSuppliesCost", data.officeSuppliesCost);
    updateElement("rentExpense", data.rentExpense);
    updateElement("operatingIncomeCurrent", data.operatingIncome);
    updateElement("nonOperatingIncomeCurrent", data.nonOperatingIncome);
    updateElement("nonOperatingExpenseCurrent", data.nonOperatingExpense);
    updateElement("preTaxIncomeCurrent", data.preTaxIncome);
    updateElement("taxExpenseCurrent", data.taxExpense);
    updateElement("netIncomeCurrent", data.netIncome);
  }

// ✅ 전월 데이터 업데이트 함수 (모든 필드 반영)
  function updatePreviousTable(data) {
    updateElement("salesPrevious", data.sales || 0);
    updateElement("costOfSalesPrevious", data.costOfSales || 0);
    updateElement("productCostAutoPrevious", data.productCostAuto || 0);
    updateElement("productCostManualPrevious", data.productCostManual || 0);
    updateElement("beginningInventoryPrevious", data.beginningInventory || 0);
    updateElement("endingInventoryPrevious", data.endingInventory || 0);
    updateElement("grossProfitPrevious", data.grossProfit || 0);
    updateElement("sgAndAPrevious", data.sgAndA || 0);
    updateElement("salaryPrevious", data.salary || 0);
    updateElement("transportationCostPrevious", data.transportationCost || 0);
    updateElement("officeSuppliesCostPrevious", data.officeSuppliesCost || 0);
    updateElement("rentExpensePrevious", data.rentExpense || 0);
    updateElement("operatingIncomePrevious", data.operatingIncome || 0);
    updateElement("nonOperatingIncomePrevious", data.nonOperatingIncome || 0);
    updateElement("nonOperatingExpensePrevious", data.nonOperatingExpense || 0);
    updateElement("preTaxIncomePrevious", data.preTaxIncome || 0);
    updateElement("taxExpensePrevious", data.taxExpense || 0);
    updateElement("netIncomePrevious", data.netIncome || 0);
  }

// ✅ 당월 데이터 불러오기
  fetch("/api/accounting/current")
      .then(response => {
        if (!response.ok) {
          throw new Error("❌ API 요청 실패: " + response.statusText);
        }
        return response.json();
      })
      .then(data => {
        console.log("📌 현재 데이터:", data);
        updateCurrentTable(data);  // ✅ 수정: 올바른 함수명 사용
        document.getElementById("salesCurrent").value = formatNumber(data.productSales || 0);
        document.getElementById("productCostManualCurrent").value = formatNumber(data.productCostManual || 0);
        document.getElementById("beginningInventoryCurrent").value = formatNumber(data.beginningInventory || 0);
        document.getElementById("endingInventoryCurrent").value = formatNumber(data.endingInventory || 0);
        document.getElementById("salaryCurrent").value = formatNumber(data.salary || 0);
        document.getElementById("transportationCostCurrent").value = formatNumber(data.transportationCost || 0);
        document.getElementById("officeSuppliesCostCurrent").value = formatNumber(data.officeSuppliesCost || 0);
        document.getElementById("rentExpenseCurrent").value = formatNumber(data.rentExpense || 0);

        // 데이터를 불러온 후 모든 입력 필드 값에 대해 포맷팅 및 계산 적용
        document.querySelectorAll(".amount input").forEach((input) => {
          input.value = formatNumber(parseFloat(input.value.replace(/,/g, "")) || 0);
        });

        calculateFinancials(); // 데이터 로드 후 계산 실행
      })
      .catch(error => {
        console.error("❌ 당월 데이터 불러오기 오류:", error);

        // ✅ 오류 발생 시 기본값 적용 (필드명 수정됨)
        updateCurrentTable({
          sales: 0,  // ✅ 변경됨
          costOfSales: 0,  // ✅ 변경됨
          productCostAuto: 0,  // ✅ 추가됨
          productCostManual: 0,  // ✅ 추가됨
          beginningInventory: 0,  // ✅ 추가됨
          endingInventory: 0,  // ✅ 추가됨
          grossProfit: 0,  // ✅ 추가됨
          sgAndA: 0,  // ✅ 변경됨
          salary: 0,
          transportationCost: 0,
          officeSuppliesCost: 0,
          rentExpense: 0,
          operatingIncome: 0,  // ✅ 변경됨
          nonOperatingIncome: 0,  // ✅ 추가됨
          nonOperatingExpense: 0,  // ✅ 추가됨
          preTaxIncome: 0,  // ✅ 추가됨
          taxExpense: 0,  // ✅ 추가됨
          netIncome: 0,  // ✅ 변경됨
        });
      });
  // ✅ 전월 데이터 불러오기
  fetch("/api/accounting/previous")
      .then(response => {
        if (!response.ok) {
          throw new Error("❌ API 요청 실패: " + response.statusText);
        }
        return response.json();
      })
      .then(data => {
        console.log("📌 전월 데이터:", data);
        updatePreviousTable(data);
      })
      .catch(error => {
        console.error("❌ 전월 데이터 불러오기 오류:", error);

        // ✅ 오류 발생 시 기본값 적용 (필드명 수정됨)
        updatePreviousTable({
          sales: 0,  // ✅ 변경됨
          costOfSales: 0,  // ✅ 변경됨
          productCostAuto: 0,  // ✅ 추가됨
          productCostManual: 0,  // ✅ 추가됨
          beginningInventory: 0,  // ✅ 추가됨
          endingInventory: 0,  // ✅ 추가됨
          grossProfit: 0,  // ✅ 추가됨
          sgAndA: 0,  // ✅ 변경됨
          salary: 0,
          transportationCost: 0,
          officeSuppliesCost: 0,
          rentExpense: 0,
          operatingIncome: 0,  // ✅ 변경됨
          nonOperatingIncome: 0,  // ✅ 추가됨
          nonOperatingExpense: 0,  // ✅ 추가됨
          preTaxIncome: 0,  // ✅ 추가됨
          taxExpense: 0,  // ✅ 추가됨
          netIncome: 0,  // ✅ 변경됨
        });
      });

  // 사용자 입력 필드를 포함한 계산 함수
  // ✅ 사용자 입력 필드를 포함한 계산 함수 (올바른 변수명 적용)
  function calculateFinancials(salesCurrent) {
    try {
      salesCurrent = salesCurrent || parseFloat(document.getElementById("salesCurrent").textContent.replace(/,/g, "")) || 0;
      const beginningInventory = parseFloat(document.getElementById("beginningInventoryCurrent").value.replace(/,/g, "")) || 0;
      const endingInventory = parseFloat(document.getElementById("endingInventoryCurrent").value.replace(/,/g, "")) || 0;
      const productCostManual = parseFloat(document.getElementById("productCostManualCurrent").value.replace(/,/g, "")) || 0;
      const salary = parseFloat(document.getElementById("salaryCurrent").value.replace(/,/g, "")) || 0;
      const transportationCost = parseFloat(document.getElementById("transportationCostCurrent").value.replace(/,/g, "")) || 0;
      const officeSupplies = parseFloat(document.getElementById("officeSuppliesCostCurrent").value.replace(/,/g, "")) || 0;
      const rent = parseFloat(document.getElementById("rentExpenseCurrent").value.replace(/,/g, "")) || 0;
      const nonOperatingIncome = parseFloat(document.getElementById("nonOperatingIncomeCurrent").textContent.replace(/,/g, "")) || 0;
      const nonOperatingExpense = parseFloat(document.getElementById("nonOperatingExpenseCurrent").textContent.replace(/,/g, "")) || 0;
      const taxExpense = parseFloat(document.getElementById("taxExpenseCurrent").textContent.replace(/,/g, "")) || 0;

      const costOfSales = beginningInventory + productCostManual - endingInventory;
      document.getElementById("costOfSalesCurrent").textContent = formatNumber(costOfSales);

      const grossProfit = salesCurrent - costOfSales;
      document.getElementById("grossProfitCurrent").textContent = formatNumber(grossProfit);

      const sgAndA = salary + transportationCost + officeSupplies + rent;
      document.getElementById("sgAndACurrent").textContent = formatNumber(sgAndA);

      const operatingIncome = grossProfit - sgAndA;
      document.getElementById("operatingIncomeCurrent").textContent = formatNumber(operatingIncome);

      const preTaxIncome = operatingIncome - nonOperatingExpense + nonOperatingIncome;
      document.getElementById("preTaxIncomeCurrent").textContent = formatNumber(preTaxIncome);

      const netIncome = preTaxIncome - taxExpense;
      document.getElementById("netIncomeCurrent").textContent = formatNumber(netIncome);

    } catch (error) {
      console.error("❌ 계산 오류:", error);
    }
  }

  // 사용자 입력 필드를 클릭하면 초기값 0 제거하는 이벤트
  document.querySelectorAll(".amount input").forEach((input) => {
    input.addEventListener("focus", function () {
      if (input.value === "0") {
        input.value = "";
      }
    });

    input.addEventListener("blur", function () {
      if (input.value === "") {
        input.value = "0";
      } else {
        input.value = formatNumber(input.value.replace(/,/g, ""));
      }
      calculateFinancials();
    });

    input.addEventListener("input", function () {
      input.value = input.value.replace(/[^0-9]/g, "");
    });
  });

    // ✅ saveData 함수 정의
  function saveData(event) {
    event.preventDefault(); // 기본 제출 방지

    const formData = {
      date: new Date().toISOString().split("T")[0],
      userId: 11,
      sales: parseFloat(document.getElementById("salesCurrent").textContent.replace(/,/g, "")) || 0,
      costOfSales: parseFloat(document.getElementById("costOfSalesCurrent").textContent.replace(/,/g, "")) || 0,
      productCostAuto: parseFloat(document.getElementById("productCostAutoCurrent").textContent.replace(/,/g, "")) || 0,
      productCostManual: parseFloat(document.getElementById("productCostManualCurrent").value.replace(/,/g, "")) || 0,
      beginningInventory: parseFloat(document.getElementById("beginningInventoryCurrent").value.replace(/,/g, "")) || 0,
      endingInventory: parseFloat(document.getElementById("endingInventoryCurrent").value.replace(/,/g, "")) || 0,
      grossProfit: parseFloat(document.getElementById("grossProfitCurrent").textContent.replace(/,/g, "")) || 0,
      sgAndA: parseFloat(document.getElementById("sgAndACurrent").textContent.replace(/,/g, "")) || 0,
      salary: parseFloat(document.getElementById("salaryCurrent").value.replace(/,/g, "")) || 0,
      transportationCost: parseFloat(document.getElementById("transportationCostCurrent").value.replace(/,/g, "")) || 0,
      officeSuppliesCost: parseFloat(document.getElementById("officeSuppliesCostCurrent").value.replace(/,/g, "")) || 0,
      rentExpense: parseFloat(document.getElementById("rentExpenseCurrent").value.replace(/,/g, "")) || 0,
      operatingIncome: parseFloat(document.getElementById("operatingIncomeCurrent").textContent.replace(/,/g, "")) || 0,
      nonOperatingIncome: parseFloat(document.getElementById("nonOperatingIncomeCurrent").textContent.replace(/,/g, "")) || 0,
      nonOperatingExpense: parseFloat(document.getElementById("nonOperatingExpenseCurrent").textContent.replace(/,/g, "")) || 0,
      preTaxIncome: parseFloat(document.getElementById("preTaxIncomeCurrent").textContent.replace(/,/g, "")) || 0,
      taxExpense: parseFloat(document.getElementById("taxExpenseCurrent").textContent.replace(/,/g, "")) || 0,
      netIncome: parseFloat(document.getElementById("netIncomeCurrent").textContent.replace(/,/g, "")) || 0,
      month: new Date().getMonth() + 1,
      year: new Date().getFullYear(),
      productSales: 0
    };
    console.log("저장할 데이터:", formData);

    fetch("/api/accounting/save", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(formData)
    })
        .then(response => {
          if (!response.ok) {
            throw new Error("❌ 저장 실패: " + response.statusText);
          }
          return response.json();
        })
        .then(data => {
          alert("데이터가 성공적으로 저장되었습니다.");
          console.log("✅ 저장 성공:", data);
        })
        .catch(error => {
          alert("데이터 저장에 실패했습니다.");
          console.error("❌ 데이터 저장 오류:", error);
        });
  }

// ✅ 버튼 클릭 시 saveData 함수 호출
  document.getElementById("saveButton").addEventListener("click", saveData);

// ✅ 초기 계산 실행
  calculateFinancials();
});
