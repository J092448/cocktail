// ✅ DOMContentLoaded 이벤트: HTML 문서의 DOM이 로드되었을 때 실행
document.addEventListener("DOMContentLoaded", (event) => {
  // 숫자를 세 자리마다 쉼표로 포맷하는 함수
  function formatNumber(number) {
    return Math.floor(number).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    /*
      - `Math.floor(number)`: 소수점 이하를 버림
      - `toString()`: 숫자를 문자열로 변환
      - `replace(/\B(?=(\d{3})+(?!\d))/g, ",")`: 정규식을 사용하여 3자리마다 쉼표 추가
    */
  }

  // 숫자에서 소수점 이하를 제거하는 함수
  function removeDecimal(value) {
    return Math.floor(value); // 소수점 이하를 버림
  }

  // ✅ 현재 연도와 월 계산
  const currentYear = new Date().getFullYear(); // 현재 연도
  const currentMonth = new Date().getMonth() + 1; // 현재 월 (0부터 시작하므로 +1 필요)
  let previousYear = currentYear;
  let previousMonth = currentMonth - 1;

  // 만약 전월이 0이라면 (1월의 이전은 전년도 12월로 설정)
  if (previousMonth === 0) {
    previousMonth = 12;
    previousYear--;
  }
  console.log(`📌 현재 연도: ${currentYear}, 현재 월: ${currentMonth}`);
  console.log(`📌 전월 연도: ${previousYear}, 전월 월: ${previousMonth}`);

  // ✅ 특정 요소의 텍스트를 업데이트하는 함수
  function updateElement(id, value) {
    const element = document.getElementById(id); // 요소 ID로 DOM 요소 찾기
    if (element) {
      element.textContent = formatNumber(value !== null && value !== undefined ? value : 0);
      /*
        - value가 null이나 undefined가 아니면 포맷팅하여 설정
        - 값이 없을 경우 기본값 0을 설정
      */
    }
  }

  // ✅ 현재 월 데이터를 업데이트하는 함수
  function updateCurrentTable(data) {
    // 당월 매출액 데이터 처리
    fetch("/api/calendar/sales")
        .then(response => response.json()) // API 응답을 JSON으로 변환
        .then(salesData => {
          const totalSales = salesData.reduce((sum, entry) => sum + (entry.salesAmount || 0), 0);
          /*
            - reduce()를 사용하여 모든 매출 데이터 합산
            - salesAmount가 없을 경우 기본값 0으로 처리
          */
          document.getElementById("salesCurrent").textContent = formatNumber(totalSales);
          calculateFinancials(); // 매출 데이터를 가져온 후 계산 실행
        })
        .catch(error => console.error("❌ 매출 데이터 불러오기 오류:", error));

    // 당월 상품매출원가(자동) 처리
    const productCostElement = document.getElementById("productCostAutoCurrent");
    if (productCostElement) {
      fetch("/api/accounting/product-cost-auto") // 서버 API 호출
          .then(response => response.json())
          .then(costData => {
            productCostElement.textContent = formatNumber(costData || 0);
          })
          .catch(error => console.error("❌ 상품 매출 원가 데이터 불러오기 오류:", error));
    } else {
      console.error("❌ 'productCostAutoCurrent' 요소가 없습니다. HTML에서 확인하세요.");
    }

    // 각 필드 값을 업데이트
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

  // ✅ 전월 데이터를 업데이트하는 함수
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

  // ✅ 당월 데이터 API 호출
  fetch("/api/accounting/current")
      .then(response => {
        if (!response.ok) {
          throw new Error("❌ API 요청 실패: " + response.statusText);
        }
        return response.json(); // JSON 응답 변환
      })
      .then(data => {
        console.log("📌 현재 데이터:", data);
        updateCurrentTable(data); // 당월 데이터 업데이트
        document.getElementById("salesCurrent").value = formatNumber(data.productSales || 0);
        document.getElementById("productCostManualCurrent").value = formatNumber(data.productCostManual || 0);
        document.getElementById("beginningInventoryCurrent").value = formatNumber(data.beginningInventory || 0);
        document.getElementById("endingInventoryCurrent").value = formatNumber(data.endingInventory || 0);
        document.getElementById("salaryCurrent").value = formatNumber(data.salary || 0);
        document.getElementById("transportationCostCurrent").value = formatNumber(data.transportationCost || 0);
        document.getElementById("officeSuppliesCostCurrent").value = formatNumber(data.officeSuppliesCost || 0);
        document.getElementById("rentExpenseCurrent").value = formatNumber(data.rentExpense || 0);

        // 모든 입력 필드 값 포맷팅
        document.querySelectorAll(".amount input").forEach((input) => {
          input.value = formatNumber(parseFloat(input.value.replace(/,/g, "")) || 0);
        });

        calculateFinancials(); // 계산 실행
      })
      .catch(error => {
        console.error("❌ 당월 데이터 불러오기 오류:", error);

        // API 오류 발생 시 기본값 적용
        updateCurrentTable({
          sales: 0,
          costOfSales: 0,
          productCostAuto: 0,
          productCostManual: 0,
          beginningInventory: 0,
          endingInventory: 0,
          grossProfit: 0,
          sgAndA: 0,
          salary: 0,
          transportationCost: 0,
          officeSuppliesCost: 0,
          rentExpense: 0,
          operatingIncome: 0,
          nonOperatingIncome: 0,
          nonOperatingExpense: 0,
          preTaxIncome: 0,
          taxExpense: 0,
          netIncome: 0,
        });
      });

    // ✅ API를 통해 전월 데이터 가져오기
    fetch("/api/accounting/previous")
        .then(response => {
          if (!response.ok) {
            throw new Error("❌ API 요청 실패: " + response.statusText); // 응답이 성공적이지 않으면 예외 발생
          }
          return response.json(); // JSON 응답 변환
        })
        .then(data => {
          console.log("📌 전월 데이터:", data); // API에서 가져온 전월 데이터를 로그로 출력
          updatePreviousTable(data); // 전월 데이터로 테이블 업데이트
        })
        .catch(error => {
          console.error("❌ 전월 데이터 불러오기 오류:", error);

          // 기본값으로 테이블 초기화
          updatePreviousTable({
            sales: 0,
            costOfSales: 0,
            productCostAuto: 0,
            productCostManual: 0,
            beginningInventory: 0,
            endingInventory: 0,
            grossProfit: 0,
            sgAndA: 0,
            salary: 0,
            transportationCost: 0,
            officeSuppliesCost: 0,
            rentExpense: 0,
            operatingIncome: 0,
            nonOperatingIncome: 0,
            nonOperatingExpense: 0,
            preTaxIncome: 0,
            taxExpense: 0,
            netIncome: 0,
          });
        });

    // ✅ 재무 계산 처리 함수
    function calculateFinancials(salesCurrent) {
      try {
        // 입력 필드에서 값 가져오기 및 기본값 설정
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

        // 계산 수행
        const costOfSales = beginningInventory + productCostManual - endingInventory; // 매출원가 계산
        document.getElementById("costOfSalesCurrent").textContent = formatNumber(costOfSales);

        const grossProfit = salesCurrent - costOfSales; // 매출 총이익 계산
        document.getElementById("grossProfitCurrent").textContent = formatNumber(grossProfit);

        const sgAndA = salary + transportationCost + officeSupplies + rent; // 판매관리비 계산
        document.getElementById("sgAndACurrent").textContent = formatNumber(sgAndA);

        const operatingIncome = grossProfit - sgAndA; // 영업이익 계산
        document.getElementById("operatingIncomeCurrent").textContent = formatNumber(operatingIncome);

        const preTaxIncome = operatingIncome - nonOperatingExpense + nonOperatingIncome; // 세전이익 계산
        document.getElementById("preTaxIncomeCurrent").textContent = formatNumber(preTaxIncome);

        const netIncome = preTaxIncome - taxExpense; // 순이익 계산
        document.getElementById("netIncomeCurrent").textContent = formatNumber(netIncome);

      } catch (error) {
        console.error("❌ 계산 오류:", error);
      }
    }

    // ✅ 사용자 입력 필드에 초점을 맞췄을 때 초기값 제거
    document.querySelectorAll(".amount input").forEach((input) => {
      input.addEventListener("focus", function () {
        if (input.value === "0") {
          input.value = ""; // 초기값이 0인 경우 제거
        }
      });

      // 입력 필드에서 포커스가 해제되었을 때 값 포맷
      input.addEventListener("blur", function () {
        if (input.value === "") {
          input.value = "0"; // 빈 값일 경우 0 설정
        } else {
          input.value = formatNumber(input.value.replace(/,/g, "")); // 쉼표 추가
        }
        calculateFinancials(); // 입력 후 계산 재실행
      });

      // 입력 중 숫자만 허용
      input.addEventListener("input", function () {
        input.value = input.value.replace(/[^0-9]/g, ""); // 숫자 이외의 문자는 제거
      });
    });

    // ✅ 데이터 저장 함수
    function saveData(event) {
      event.preventDefault(); // 기본 제출 동작 방지

      // 저장할 데이터를 객체로 구성
      const formData = {
        date: new Date().toISOString().split("T")[0], // 오늘 날짜
        userId: 11, // 사용자 ID
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

      // API를 통해 데이터 저장 요청
      fetch("/api/accounting/save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(formData) // formData 객체를 JSON 문자열로 변환하여 요청 본문에 포함
      })
          .then(response => {
            // 응답 상태 확인
            if (!response.ok) {
              // 응답이 성공적이지 않다면 오류를 발생시킴
              throw new Error("❌ 저장 실패: " + response.statusText);
            }
            return response.json(); // JSON 형식으로 응답 데이터를 반환
          })
          .then(data => {
            // 저장 성공 시 사용자에게 알림 및 데이터 확인
            alert("데이터가 성공적으로 저장되었습니다."); // 사용자에게 성공 메시지 표시
            console.log("✅ 저장 성공:", data); // 저장된 데이터를 콘솔에 출력
          })
          .catch(error => {
            // 오류 발생 시 처리
            alert("데이터 저장에 실패했습니다."); // 사용자에게 오류 메시지 표시
            console.error("❌ 데이터 저장 오류:", error); // 오류를 콘솔에 출력
          });
    }

    // ✅ saveData 함수가 버튼 클릭 이벤트와 연결
    document.getElementById("saveButton").addEventListener("click", saveData);

    // ✅ 페이지 로드 시 초기 계산 실행
    calculateFinancials();
  });
