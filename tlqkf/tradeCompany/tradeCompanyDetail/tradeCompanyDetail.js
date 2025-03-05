document.addEventListener("DOMContentLoaded", function () {
  const companyContainer = document.getElementById("companyDetail-container");

  // "N/A" 텍스트에 빨간색 적용
  function applyRedColorToNA(text) {
    return text === "N/A" ? '<span style="color: red;">' + text + "</span>" : text;
  }

  // wrapper 생성
  const wrapper = document.createElement("div");

  // 테이블 생성
  const table = document.createElement("table");

  // 테이블 헤더 생성
  const headerHTML = `
    <thead>
        <tr>
            <th class="medium-grey-background">업체번호</th>
            <td id="company-number">${applyRedColorToNA("N/A")}</td>
            <th class="medium-grey-background">업체 등록일자</th>
            <td id="registration-date" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background">거래품목</th>
            <td id="trade-item">${applyRedColorToNA("N/A")}</td>
            <th class="medium-grey-background">업체정보 수정일자</th>
            <td id="modification-date" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background" rowspan="6">거래업체정보</th>
            <th class="medium-grey-background">업체명</th>
            <td id="company-name" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background">업체명(영문)</th>
            <td id="company-name-en" colspan="3">${applyRedColorToNA("N/A")}</td>
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
            <td id="mobile-phone" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background">전화번호</th>
            <td id="phone-number" colspan="3">${applyRedColorToNA("N/A")}</td>
        </tr>
        <tr>
            <th class="medium-grey-background" colspan="5">거래 품목</th>
        </tr>
        <tr>
            <th class="medium-grey-background" colspan="2">NO.</th> <!-- NO. 부분에 colspan=2 설정 -->
            <th class="medium-grey-background" colspan="2">품목명</th>
            <th class="medium-grey-background" colspan="2">단가</th>
        </tr>
         <tr>
            <td id="noCell" colspan="2">${applyRedColorToNA("N/A")}</td> <!-- NO. 부분에 colspan=2 설정 -->
            <td id="nameCell" colspan="2">${applyRedColorToNA("N/A")}</td>
            <td id="priceCell" colspan="2">${applyRedColorToNA("N/A")}</td>
        </tr>
    </thead>
  `;
  table.innerHTML = headerHTML;

  // 테이블 본문 생성
  const tableBody = document.createElement("tbody");
  table.appendChild(tableBody);

  // wrapper에 테이블과 버튼 컨테이너를 추가
  wrapper.appendChild(table);

  // wrapper를 컨테이너에 추가
  companyContainer.appendChild(wrapper);

  // 서버에서 데이터 가져오기
  fetch("http://192.168.0.24:3306/api/companies")
    .then((response) => response.json())
    .then((companyDetails) => {
      // 데이터가 있을 경우 테이블 값 업데이트
      document.getElementById("company-number").innerHTML = applyRedColorToNA(companyDetails.companyNumber || "N/A");
      document.getElementById("registration-date").innerHTML = applyRedColorToNA(
        companyDetails.registrationDate || "N/A"
      );
      document.getElementById("modification-date").innerHTML = applyRedColorToNA(
        companyDetails.modificationDate || "N/A"
      );
      document.getElementById("trade-item").innerHTML = applyRedColorToNA(companyDetails.tradeItem || "N/A");
      document.getElementById("company-name").innerHTML = applyRedColorToNA(companyDetails.name || "N/A");
      document.getElementById("company-name-en").innerHTML = applyRedColorToNA(companyDetails.nameEn || "N/A");
      document.getElementById("business-number").innerHTML = applyRedColorToNA(companyDetails.businessNumber || "N/A");
      document.getElementById("representative-name").innerHTML = applyRedColorToNA(
        companyDetails.representativeName || "N/A"
      );
      document.getElementById("mobile-phone").innerHTML = applyRedColorToNA(companyDetails.mobilePhone || "N/A");
      document.getElementById("phone-number").innerHTML = applyRedColorToNA(companyDetails.phoneNumber || "N/A");

      document.getElementById("noCell").innerHTML = applyRedColorToNA(companyDetails.noCell || "N/A");
      document.getElementById("nameCell").innerHTML = applyRedColorToNA(companyDetails.nameCell || "N/A");
      document.getElementById("priceCell").innerHTML = applyRedColorToNA(companyDetails.priceCell || "N/A");
      // 기존 테이블 본문 지우기
      tableBody.innerHTML = "";

      // 새로운 데이터로 테이블 본문 생성
      if (companyDetails.items.length > 0) {
        companyDetails.items.forEach((item) => {
          const row = tableBody.insertRow();
          const noCell = row.insertCell();
          noCell.innerHTML = applyRedColorToNA(item.no);
          noCell.setAttribute("colspan", 2); // NO. 부분에 colspan=2 설정
          const nameCell = row.insertCell();
          nameCell.innerHTML = applyRedColorToNA(item.name);
          nameCell.setAttribute("colspan", 2); // 품목명 셀 병합
          const priceCell = row.insertCell();
          priceCell.innerHTML = applyRedColorToNA(item.price.toLocaleString());
          priceCell.setAttribute("colspan", 2); // 단가 셀 병합
        });
      } else {
        const row = tableBody.insertRow();
        const noCell = row.insertCell();
        noCell.innerHTML = applyRedColorToNA("N/A");
        noCell.setAttribute("colspan", 2); // NO. 부분에 colspan=2 설정
        const nameCell = row.insertCell();
        nameCell.innerHTML = applyRedColorToNA("N/A");
        nameCell.setAttribute("colspan", 2); // 품목명
        const priceCell = row.insertCell();
        priceCell.innerHTML = applyRedColorToNA("N/A");
        priceCell.setAttribute("colspan", 2); // 단가
      }
      table.appendChild(tableBody);
    })
    .catch((error) => {
      console.error("Error fetching company details:", error);
      // 데이터베이스에서 값을 받아오지 못한 경우 기본 값 설정
      document.getElementById("company-number").innerHTML = applyRedColorToNA("N/A");
    });
  // 테이블을 컨테이너에 추가
  companyContainer.appendChild(table);

  // 정보삭제 버튼과 정보 수정 버튼 생성
  const buttonContainer = document.createElement("div");
  buttonContainer.className = "button-container"; // 클래스 이름 설정

  const deleteButton = document.createElement("button");
  deleteButton.id = "delete-button";
  deleteButton.textContent = "업체 삭제";

  const editButton = document.createElement("button");
  editButton.id = "edit-button";
  editButton.textContent = "정보 수정";

  buttonContainer.appendChild(deleteButton);
  buttonContainer.appendChild(editButton);

  // 버튼 컨테이너를 컨테이너에 추가
  companyContainer.appendChild(buttonContainer);

  // 이벤트 리스너 추가
  document.getElementById("delete-button").addEventListener("click", function () {
    if (
      confirm(
        `정말 해당 거래 업체를 삭제하시겠습니까? 삭제된 정보는 되돌릴 수 없습니다. \n[확인]을 누르면 삭제되며, [취소]를 누르면 업체 상세 정보 페이지로 돌아갑니다.\n`
      )
    ) {
      // 확인을 누른 경우
      window.location.href = "../tradeCompanyList/tradeCompanyList.html";
    }
  });

  document.getElementById("edit-button").addEventListener("click", function () {
    window.location.href = "../tradeCompanyDatailEdit/tradeCompanyDatailEdit.html";
  });
});
