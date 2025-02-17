document.addEventListener("DOMContentLoaded", function () {
  const orderContainer = document.getElementById("order-container");

  // 데이터베이스에서 받아온 데이터를 저장하는 변수 (예시 데이터)
  const orderDetails = {
    documentNumber: "no.000001",
    orderDate: "2025.02.02",
    expectedDate: "2025.02.05",
    orderType: "주류",
    orderItem: "화이트럼",
    company: {
      name: "(주) XX주류",
      businessNumber: "000-00-00000",
      representativeName: "민머리",
      phone: "010-1111-1111",
    },
    orderItems: [
      { no: 1, name: "감보", quantity: 2, price: 10000 },
      { no: 2, name: "제품2", quantity: 5, price: 20000 },
      // 다른 데이터들...
    ],
  };

  // 문서 번호 삽입
  document.getElementById("document-number").textContent = orderDetails.documentNumber;

  // 테이블 생성
  const table = document.createElement("table");

  // 테이블 헤더 생성
  const headerHTML = `
        <thead>
            <tr>
                <th class="medium-grey-background">발주신청일자</th>
                <td>${orderDetails.orderDate}</td>
                <th class="medium-grey-background">예상수령일자</th>
                <td colspan="3">${orderDetails.expectedDate}</td>
            </tr>
            <tr>
                <th class="medium-grey-background">발주물품종류</th>
                <td>${orderDetails.orderType}</td>
                <th class="medium-grey-background">상세품목</th>
                <td colspan="3">${orderDetails.orderItem}</td>
            </tr>
            <tr>
                <th class="medium-grey-background" rowspan="4">거래업체 정보</th>
                <th class="medium-grey-background">거래업체 상호명</th>
                <td colspan="3">${orderDetails.company.name}</td>
            </tr>
            <tr>
                <th class="medium-grey-background">사업자 번호</th>
                <td colspan="3">${orderDetails.company.businessNumber}</td>
            </tr>
            <tr>
                <th class="medium-grey-background">대표자 이름</th>
                <td colspan="3">${orderDetails.company.representativeName}</td>
            </tr>
            <tr>
                <th class="medium-grey-background">휴대전화</th>
                <td colspan="3">${orderDetails.company.phone}</td>
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
  orderDetails.orderItems.forEach((item) => {
    const row = tableBody.insertRow();
    row.insertCell().textContent = item.no;
    row.insertCell().textContent = item.name;
    row.insertCell().textContent = item.quantity.toLocaleString();
    row.insertCell().textContent = item.price.toLocaleString();
    row.insertCell().textContent = (item.quantity * item.price).toLocaleString();
  });
  table.appendChild(tableBody);

  // 테이블 푸터 생성
  const totalAmount = orderDetails.orderItems.reduce((sum, item) => sum + item.quantity * item.price, 0);
  const footerHTML = `
        <tfoot>
            <tr>
                <th class="medium-grey-background" colspan="4">합계</th>
                <th>${totalAmount.toLocaleString()}</th>
            </tr>
        </tfoot>
    `;
  table.innerHTML += footerHTML;

  // 테이블을 컨테이너에 추가
  orderContainer.appendChild(table);
});
