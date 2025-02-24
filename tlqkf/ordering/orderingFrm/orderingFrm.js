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

// DB에서 데이터를 가져와서 항목을 업데이트하는 함수
function fetchData() {
  fetch("https://your-api-endpoint.com/orderNumber")
    .then((response) => response.json())
    .then((data) => {
      document.getElementById("orderNumber").innerText = "no." + data.orderNumber;
    })
    .catch((error) => console.error("Error fetching order number:", error));

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
      document.getElementById("unitPrice").value = data.unitPrice + " 원";
    })
    .catch((error) => console.error("Error fetching unit price:", error));
}

// 페이지 로드 시 현재 날짜 설정 및 데이터 가져오기
window.onload = function () {
  setTodayDate();
  fetchData();
};

document.getElementById("itemName").addEventListener("change", function () {
  const selectedItemId = this.value;
  if (selectedItemId) {
    fetchUnitPrice(selectedItemId);
  } else {
    document.getElementById("unitPrice").value = "";
  }
});

document.getElementById("submitOrder").addEventListener("click", function () {
  alert("발주신청 되었습니다. 발주 목록 페이지로 이동합니다.");
});

document.getElementById("cancelOrder").addEventListener("click", function () {
  alert("발주가 취소되었습니다. 발주 목록 페이지로 이동합니다.");
});

document.getElementById("quantity").addEventListener("input", function () {
  var quantity = parseInt(this.value);
  if (quantity < 1) {
    this.value = 1;
    quantity = 1;
  }
  var unitPrice = parseInt(document.getElementById("unitPrice").value.replace(/[^0-9]/g, ""));
  var totalAmount = quantity * unitPrice;
  document.getElementById("totalAmount").value = totalAmount.toLocaleString("ko-KR") + " 원";
});
