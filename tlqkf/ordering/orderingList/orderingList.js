document.addEventListener("DOMContentLoaded", async () => {
  let orders = [];
  const rowsPerPage = 10; // rowsPerPage 변수 정의
  let currentPage = 1;

  async function fetchOrders() {
    try {
      const response = await fetch("http://192.168.0.24:3306/cocktail_bar_erp");
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      orders = await response.json();
    } catch (error) {
      console.error("Error fetching orders:", error);
      orders = []; // 데이터를 불러오지 못하면 빈 배열로 설정
    } finally {
      updateTable();
      displayPagination();
    }
  }

  function loadState() {
    const savedState = JSON.parse(localStorage.getItem("ordersState"));
    if (savedState) {
      orders.forEach((order) => {
        const savedOrder = savedState.find((o) => o.번호 === order.번호);
        if (savedOrder) {
          order.상태 = savedOrder.상태;
        }
      });
    }
  }
  // 발주 목록을 동적으로 추가하는 함수
  function fetchOrderingList() {
    fetch("https://your-api-endpoint.com/orderingList")
      .then((response) => response.json())
      .then((data) => {
        const orderList = document.getElementById("orderingList");
        data.orders.forEach((order) => {
          const listItem = document.createElement("li");
          listItem.className = "order-item";
          listItem.innerHTML = `<a href="orderDetails.html?orderNumber=${order.orderNumber}">발주 번호: no.${order.orderNumber}</a>`;
          orderList.appendChild(listItem);
        });
      })
      .catch((error) => console.error("Error fetching order list:", error));
  }
  function saveState() {
    localStorage.setItem("ordersState", JSON.stringify(orders));
  }

  function displayTablePage(page) {
    orders.sort((a, b) => parseInt(b.번호) - parseInt(a.번호));

    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedOrders = orders.slice(start, end);
    const tableBody = document.querySelector("#orderTable tbody");
    tableBody.innerHTML = "";

    if (orders.length === 0) {
      const emptyMessageRow = document.createElement("tr");
      const emptyMessageCell = document.createElement("td");
      emptyMessageCell.colSpan = 6;
      emptyMessageCell.textContent = "발주 내역이 없습니다.";
      emptyMessageCell.classList.add("empty-message");
      emptyMessageRow.appendChild(emptyMessageCell);
      tableBody.appendChild(emptyMessageRow);
    } else {
      paginatedOrders.forEach((order) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${order.번호}</td>
          <td>${order.품목명}</td>
          <td>${order.신청일자}</td>
          <td>${order.수량}</td>
          <td class="${
            order.상태 === "수령완료" ? "received-status" : order.상태 === "신청완료" ? "applied-status" : ""
          }">${order.상태}</td>
          <td>
            ${
              order.상태 === "신청완료"
                ? `<button class="apply-order" data-order-number="${order.번호}">수령</button>`
                : ""
            }
          </td>
        `;
        row.addEventListener("click", (event) => {
          if (!event.target.matches("button")) {
            if (order.번호 === "1") {
              window.location.href = `/path/to/your/orderingDetail.html`;
            } else {
              window.location.href = `/path/to/your/orderingDetailPage.html?orderNumber=${order.번호}`;
            }
          }
        });
        tableBody.appendChild(row);
      });

      document.querySelectorAll("button[data-order-number]").forEach((button) => {
        button.addEventListener("click", (event) => {
          event.stopPropagation();
          const orderNumber = event.currentTarget.getAttribute("data-order-number");
          handleReceive(orderNumber, event.currentTarget);
        });
      });
    }
  }

  function displayPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";

    const prevButton = document.createElement("button");
    prevButton.textContent = "이전";
    prevButton.classList.add("prev");
    prevButton.addEventListener("click", () => {
      if (currentPage > 1) {
        currentPage -= 10;
        if (currentPage < 1) currentPage = 1;
        updateTable();
      }
    });
    pagination.appendChild(prevButton);

    const totalPages = Math.ceil(orders.length / rowsPerPage);
    const startPage = Math.floor((currentPage - 1) / 10) * 10 + 1;
    const endPage = Math.min(startPage + 9, totalPages);

    // 페이지네이션 버튼 생성
    const buttons = [];
    for (let i = startPage; i <= endPage; i++) {
      const button = document.createElement("button");
      button.textContent = i;
      button.classList.add("page");
      if (i === currentPage) {
        button.disabled = true;
      }
      button.addEventListener("click", () => {
        currentPage = i;
        updateTable();
      });
      buttons.push(button);
    }

    const nextButton = document.createElement("button");
    nextButton.textContent = "다음";
    nextButton.classList.add("next");
    nextButton.addEventListener("click", () => {
      if (currentPage < totalPages) {
        currentPage += 10;
        if (currentPage > totalPages) currentPage = totalPages;
        updateTable();
      }
    });

    buttons.forEach((button) => pagination.insertBefore(button, nextButton));
    pagination.appendChild(nextButton);

    const applyOrderButton = document.createElement("button");
    applyOrderButton.textContent = "발주 신청";
    applyOrderButton.classList.add("apply-order");
    applyOrderButton.addEventListener("click", () => {
      window.location.href = "../orderingFrm/orderingFrm.html";
    });
    pagination.appendChild(applyOrderButton);

    // 데이터가 없는 경우 기본 페이지네이션 및 '발주 내역이 없습니다.' 메시지 표시
    if (orders.length === 0) {
      const button = document.createElement("button");
      button.textContent = 1;
      button.classList.add("page");
      button.disabled = true;
      pagination.insertBefore(button, nextButton); // 다음 버튼 앞에 추가
    }
  }

  function handleReceive(orderNumber, button) {
    if (confirm("물품을 수령하셨습니까? 확인을 누르면 더 이상 발주 상태를 바꿀 수 없습니다.")) {
      console.log(`handleReceive 호출됨 - orderNumber: ${orderNumber}`);
      const order = orders.find((o) => o.번호 === orderNumber);
      if (order) {
        console.log(`이전 상태: ${order.상태}`);
        order.상태 = "수령완료";
        console.log(`변경된 상태: ${order.상태}`);
        saveState();
        button.style.display = "none";
        updateTable();
      }
    }
  }

  function updateTable() {
    displayTablePage(currentPage);
    displayPagination();
  }

  function displayEmptyMessage() {
    const tableBody = document.querySelector("#orderTable tbody");
    tableBody.innerHTML =
      "<tr><td colspan='6' style='text-align: center; color: gray;'>발주 내역이 없습니다.</td></tr>";
  }
  // 페이지 로드 시 발주 목록을 가져옴
  window.onload = function () {
    fetchOrderList();
  };
  // 초기 상태 로드
  loadState();
  // 초기 데이터 로드
  await fetchOrders();
  // 상태와 페이지네이션을 항상 표시
  displayPagination();
});
