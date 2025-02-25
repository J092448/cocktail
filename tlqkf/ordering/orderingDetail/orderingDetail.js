document.addEventListener("DOMContentLoaded", function () {
  const orderContainer = document.getElementById("order-container");

  // "N/A" 텍스트에 빨간색 적용
  function applyRedColorToNA(text) {
    return text === "N/A" ? `<span style="color: red;">${text}</span>` : text;
  }

  // 테이블 생성
  const table = document.createElement("table");

  // 테이블 헤더 생성
  const headerHTML = `
    <thead>
        <tr>
            <th class="medium-grey-background">발주신청일자</th>
            <td id="order-date">${applyRedColorToNA("N/A")}</td>
            <th class="medium-grey-background">예상수령일자</th>
            <td id="expected-date" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background">발주물품종류</th>
            <td id="order-type">${applyRedColorToNA("N/A")}</td>
            <th class="medium-grey-background">상세품목</th>
            <td id="order-item" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background" rowspan="4">거래업체 정보</th>
            <th class="medium-grey-background">거래업체 상호명</th>
            <td id="company-name" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background">사업자 번호</th>
            <td id="business-number" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background">대표자 이름</th>
            <td id="representative-name" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background">휴대전화</th>
            <td id="phone" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background" colspan="5">발주 품목</th>
        </tr>
        <tr>
            <th class="medium-grey-background">NO.</th>
            <th class="medium-grey-background">품목명</th>
            <th class="medium-grey-background">수량</th>
            <th class="medium-grey-background">단가</th>
            <th class="medium-grey-background">금액</th>
        </tr>
    </thead>
  `;
  table.innerHTML = headerHTML;

  // 테이블 본문 생성
  const tableBody = document.createElement("tbody");
  const row = tableBody.insertRow();
  for (let i = 0; i < 5; i++) {
    const emptyCell = row.insertCell();
    emptyCell.innerHTML = applyRedColorToNA("N/A");
  }
  table.appendChild(tableBody);

  // 테이블 푸터 생성
  const footerHTML = `
    <tfoot>
        <tr>
            <th class="medium-grey-background" colspan="4">합계</th>
            <th>${applyRedColorToNA("N/A")}</th>
        </tr>
    </tfoot>
  `;
  table.innerHTML += footerHTML;

  // 테이블을 컨테이너에 추가
  orderContainer.appendChild(table);

  // 서버에서 데이터 가져오기
  fetch("http://localhost:3000/api/orders")
    .then((response) => response.json())
    .then((orderDetails) => {
      // 문서 번호 삽입
      document.getElementById("document-number").innerHTML = applyRedColorToNA(
        orderDetails.documentNumber || "no.000000"
      );

      // 데이터가 있을 경우 테이블 값 업데이트
      document.getElementById("order-date").innerHTML = applyRedColorToNA(orderDetails.orderDate || "N/A");
      document.getElementById("expected-date").innerHTML = applyRedColorToNA(orderDetails.expectedDate || "N/A");
      document.getElementById("order-type").innerHTML = applyRedColorToNA(orderDetails.orderType || "N/A");
      document.getElementById("order-item").innerHTML = applyRedColorToNA(orderDetails.orderItem || "N/A");
      document.getElementById("company-name").innerHTML = applyRedColorToNA(orderDetails.company.name || "N/A");
      document.getElementById("business-number").innerHTML = applyRedColorToNA(
        orderDetails.company.businessNumber || "N/A"
      );
      document.getElementById("representative-name").innerHTML = applyRedColorToNA(
        orderDetails.company.representativeName || "N/A"
      );
      document.getElementById("phone").innerHTML = applyRedColorToNA(orderDetails.company.phone || "N/A");

      // 기존 테이블 본문 지우기
      tableBody.innerHTML = "";

      // 새로운 데이터로 테이블 본문 생성
      if (orderDetails.orderItems.length > 0) {
        orderDetails.orderItems.forEach((item) => {
          const row = tableBody.insertRow();
          row.insertCell().textContent = item.no;
          row.insertCell().textContent = item.name;
          row.insertCell().textContent = item.quantity.toLocaleString();
          row.insertCell().textContent = item.price.toLocaleString();
          row.insertCell().textContent = (item.quantity * item.price).toLocaleString();
        });
      } else {
        const row = tableBody.insertRow();
        for (let i = 0; i < 5; i++) {
          const emptyCell = row.insertCell();
          emptyCell.innerHTML = applyRedColorToNA("N/A");
        }
      }
      table.appendChild(tableBody);

      // 테이블 푸터 업데이트
      const totalAmount = orderDetails.orderItems.reduce((sum, item) => sum + item.quantity * item.price, 0);
      const footerHTML = `
        <tfoot>
            <tr>
                <th class="medium-grey-background" colspan="4">합계</th>
                <th>${applyRedColorToNA(totalAmount.toLocaleString() || "N/A")}</th>
            </tr>
        </tfoot>
      `;
      table.innerHTML += footerHTML;
    })
    .catch((error) => {
      console.error("Error fetching order details:", error);
      // 데이터베이스에서 값을 받아오지 못한 경우 기본 값 설정
      document.getElementById("document-number").innerHTML = applyRedColorToNA("no.000000");
    });
});
