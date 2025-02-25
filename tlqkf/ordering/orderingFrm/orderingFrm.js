// 현재 날짜를 설정하는 함수
function setTodayDate() {
  var today = new Date();
  var year = today.getFullYear();
  var month = ("0" + (today.getMonth() + 1)).slice(-2);
  var day = ("0" + today.getDate()).slice(-2);
  var formattedDate = year + "년 " + month + "월 " + day + "일";
  document.getElementById("orderDate").innerText = "발주 신청 일자: " + formattedDate;

  // 예상 수령 일자 계산 (작성일 + 3일)
  var expectedDate = new Date(today);
  expectedDate.setDate(today.getDate() + 3);
  var expectedYear = expectedDate.getFullYear();
  var expectedMonth = ("0" + (expectedDate.getMonth() + 1)).slice(-2);
  var expectedDay = ("0" + expectedDate.getDate()).slice(-2);
  var formattedExpectedDate = expectedYear + "년 " + expectedMonth + "월 " + expectedDay + "일";
  document.getElementById("expectedDate").innerText = "예상 수령 일자: " + formattedExpectedDate;
}

// 순차적으로 번호를 자동 생성하는 함수
function generateOrderNumber() {
  var currentNumber = parseInt(localStorage.getItem("orderNumber") || "0");
  return currentNumber.toString().padStart(6, "0");
}

// 발주가 저장될 때 번호를 증가시키는 함수
function incrementOrderNumber() {
  var currentNumber = parseInt(localStorage.getItem("orderNumber") || "0");
  var nextNumber = currentNumber + 1;
  localStorage.setItem("orderNumber", nextNumber);
}

// DB에서 데이터를 가져와서 항목을 업데이트하는 함수
function fetchData() {
  document.getElementById("orderNumber").innerText = "no." + generateOrderNumber();

  fetch("https://your-api-endpoint.com/categories")
    .then((response) => response.json())
    .then((data) => {
      const categorySelect = document.getElementById("category");
      categorySelect.innerHTML = '<option value="">선택</option>';
      data.categories.forEach((category) => {
        const option = document.createElement("option");
        option.value = category.id;
        option.text = category.name;
        categorySelect.appendChild(option);
      });
    })
    .catch((error) => console.error("Error fetching categories:", error));

  fetch("https://your-api-endpoint.com/subcategories")
    .then((response) => response.json())
    .then((data) => {
      const subcategorySelect = document.getElementById("subcategory");
      subcategorySelect.innerHTML = '<option value="">선택</option>';
      data.subcategories.forEach((subcategory) => {
        const option = document.createElement("option");
        option.value = subcategory.id;
        option.text = subcategory.name;
        subcategorySelect.appendChild(option);
      });
    })
    .catch((error) => console.error("Error fetching subcategories:", error));

  fetch("https://your-api-endpoint.com/itemNames")
    .then((response) => response.json())
    .then((data) => {
      const itemNameSelect = document.getElementById("itemName");
      itemNameSelect.innerHTML = '<option value="">선택</option>';
      data.itemNames.forEach((itemName) => {
        const option = document.createElement("option");
        option.value = itemName.id;
        option.text = itemName.name;
        itemNameSelect.appendChild(option);
      });
    })
    .catch((error) => console.error("Error fetching item names:", error));

  fetch("https://your-api-endpoint.com/companies")
    .then((response) => response.json())
    .then((data) => {
      const companySelect = document.getElementById("company");
      companySelect.innerHTML = '<option value="">선택</option>';
      data.companies.forEach((company) => {
        const option = document.createElement("option");
        option.value = company.id;
        option.text = company.name;
        companySelect.appendChild(option);
      });
    })
    .catch((error) => console.error("Error fetching companies:", error));

  fetch("https://your-api-endpoint.com/businessNumber")
    .then((response) => response.json())
    .then((data) => {
      document.getElementById("businessNumber").value = data.businessNumber;
    })
    .catch((error) => console.error("Error fetching business number:", error));
}

// 선택한 물품의 단가를 가져오는 함수
function fetchUnitPrice(itemId) {
  fetch(`https://your-api-endpoint.com/unitPrice?itemId=${itemId}`)
    .then((response) => response.json())
    .then((data) => {
      document.getElementById("unitPrice").value = data.unitPrice.toLocaleString("ko-KR") + " 원";
      updateTotalAmount(); // 단가가 업데이트되면 총 발주 금액도 업데이트
    })
    .catch((error) => console.error("Error fetching unit price:", error));
}

// 총 발주 금액을 업데이트하는 함수
function updateTotalAmount() {
  var quantity = parseInt(document.getElementById("quantity").value) || 0; // 기본값 0으로 설정
  var unitPrice = parseInt(document.getElementById("unitPrice").value.replace(/[^0-9]/g, "")) || 0; // 기본값 0으로 설정
  var totalAmount = quantity * unitPrice;
  document.getElementById("totalAmount").value = totalAmount.toLocaleString("ko-KR") + " 원";
}

// 페이지 로드 시 현재 날짜 설정 및 데이터 가져오기
window.onload = function () {
  setTodayDate();
  fetchData();
  document.getElementById("unitPrice").value = "0 원"; // 기본값 0으로 설정
  document.getElementById("totalAmount").value = "0 원"; // 기본값 0으로 설정
  updateTotalAmount();
};

document.getElementById("itemName").addEventListener("change", function () {
  const selectedItemId = this.value;
  if (selectedItemId) {
    fetchUnitPrice(selectedItemId);
  } else {
    document.getElementById("unitPrice").value = "0 원";
    updateTotalAmount();
  }
});

document.getElementById("submitOrder").addEventListener("click", function () {
  alert("발주신청 되었습니다. 발주 목록 페이지로 이동합니다.");
  incrementOrderNumber(); // 발주가 저장될 때 번호를 증가시킴
  window.location.href = "../orderingList/orderingList.html"; // 발주 목록 페이지로 이동
});

document.getElementById("cancelOrder").addEventListener("click", function () {
  alert("발주가 취소되었습니다. 페이지를 새로고침합니다.");
  location.reload(); // 페이지 새로고침
});

document.getElementById("quantity").addEventListener("input", function () {
  updateTotalAmount();
});
