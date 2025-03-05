document.addEventListener("DOMContentLoaded", function () {
  const companyContainer = document.getElementById("companyDetail-container");

  // 테이블 생성
  const table = document.createElement("table");

  // 테이블 헤더 생성
  const headerHTML = `
  <thead>
    <tr>
      <th class="medium-grey-background">업체번호</th>
      <td colspan="2 id="company-number">N/A</td>
      <th class="medium-grey-background">업체 등록일자</th>
      <td id="registration-date">N/A</td>
    </tr>
    <tr>
      <th class="medium-grey-background">거래품목</th>
      <td colspan="2"><input type="text" id="trade-item" value="N/A"></td>
      <th class="medium-grey-background">업체정보 수정일자</th>
      <td id="modification-date" colspan="2">N/A</td>
    </tr>
    <tr>
      <th class="medium-grey-background" rowspan="6">거래업체정보</th>
      <th class="medium-grey-background">업체명</th>
      <td colspan="4"><input type="text" id="company-name" value="N/A"></td>
    </tr>
    <tr>
      <th class="medium-grey-background">업체명(영문)</th>
      <td colspan="4"><input type="text" id="company-name-en" value="N/A"></td>
    </tr>
    <tr>
      <th class="medium-grey-background">사업자 번호</th>
      <td colspan="4"><input type="text" id="business-number" value="N/A"></td>
    </tr>
    <tr>
      <th class="medium-grey-background">대표자 이름</th>
      <td colspan="4"><input type="text" id="representative-name" value="N/A"></td>
    </tr>
    <tr>
      <th class="medium-grey-background">휴대전화</th>
      <td colspan="4"><input type="text" id="mobile-phone" value="N/A"></td>
    </tr>
    <tr>
      <th class="medium-grey-background">전화번호</th>
      <td colspan="4"><input type="text" id="phone-number" value="N/A"></td>
    </tr>
    <tr>
      <th class="medium-grey-background" colspan="6">거래 품목</th>
    </tr>
    <tr>
      <th class="medium-grey-background" >NO.</th>
      <th class="medium-grey-background" colspan="3">품목명</th>
      <th class="medium-grey-background" colspan="2">단가</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><input type="text" id="noCell" value="N/A" readonly></td>
      <td colspan="3"><input type="text"  id="nameCell" value="N/A"></td>
      <td colspan="2"><input type="text" id="priceCell" value="N/A"></td>
    </tr>
  </tbody>

  `;

  table.innerHTML = headerHTML;

  // // 테이블 본문 생성
  // const tableBody = document.createElement("tbody");
  // table.appendChild(tableBody);

  // 서버에서 데이터 가져오기
  fetch("http://서버주소/api/companies")
    .then((response) => response.json())
    .then((companyDetails) => {
      // 데이터가 있을 경우 테이블 값 업데이트
      document.getElementById("company-number").textContent = companyDetails.companyNumber || "N/A";
      document.getElementById("registration-date").textContent = companyDetails.registrationDate || "N/A";
      document.getElementById("modification-date").textContent = companyDetails.modificationDate || "N/A";
      document.getElementById("trade-item").value = companyDetails.tradeItem || "N/A";
      document.getElementById("company-name").value = companyDetails.name || "N/A";
      document.getElementById("company-name-en").value = companyDetails.nameEn || "N/A";
      document.getElementById("business-number").value = companyDetails.businessNumber || "N/A";
      document.getElementById("representative-name").value = companyDetails.representativeName || "N/A";
      document.getElementById("mobile-phone").value = companyDetails.mobilePhone || "N/A";
      document.getElementById("phone-number").value = companyDetails.phoneNumber || "N/A";
      document.getElementById("noCell").value = companyDetails.noCell || "N/A";
      document.getElementById("nameCell").value = companyDetails.nameCell || "N/A";
      document.getElementById("priceCell").value = companyDetails.priceCell || "N/A";

      // 기존 테이블 본문 지우기
      tableBody.innerHTML = "";
    })
    .catch((error) => {
      console.error("Error fetching company details:", error);
      // 데이터베이스에서 값을 받아오지 못한 경우 기본 값 설정
      document.getElementById("company-number").textContent = "N/A";
      document.getElementById("registration-date").textContent = "N/A";
      document.getElementById("modification-date").textContent = "N/A";
      document.getElementById("trade-item").value = "N/A";
      document.getElementById("company-name").value = "N/A";
      document.getElementById("company-name-en").value = "N/A";
      document.getElementById("business-number").value = "N/A";
      document.getElementById("representative-name").value = "N/A";
      document.getElementById("mobile-phone").value = "N/A";
      document.getElementById("phone-number").value = "N/A";
      document.getElementById("noCell").value = "N/A";
      document.getElementById("nameCell").value = "N/A";
      document.getElementById("priceCell").value = "N/A";
    });

  // 테이블을 컨테이너에 추가
  companyContainer.appendChild(table);

  // 정보삭제 버튼과 정보 수정 버튼 생성
  const buttonContainer = document.createElement("div");
  buttonContainer.className = "button-container"; // 클래스 이름 설정

  const cancelButton = document.createElement("button");
  cancelButton.id = "cancel-button";
  cancelButton.textContent = "수정 취소";

  const saveButton = document.createElement("button");
  saveButton.id = "save-button";
  saveButton.textContent = "저장";

  buttonContainer.appendChild(cancelButton);
  buttonContainer.appendChild(saveButton);

  // wrapper와 버튼 컨테이너를 컨테이너에 추가
  companyContainer.appendChild(buttonContainer);

  // 이벤트 리스너 추가
  document.getElementById("cancel-button").addEventListener("click", function () {
    if (
      confirm(
        `업체 정보 수정을 취소하시겠습니까?\n[확인]을 누르면 업체상세페이지로 돌아가며, [취소]를 누르면 계속 수정하실 수 있습니다.\n`
      )
    ) {
      // 확인을 누른 경우
      window.location.href = "../tradeCompanyDatail/tradeCompanyDatail.html";
    }
  });

  // 저장 버튼 이벤트 리스너 추가
  document.getElementById("save-button").addEventListener("click", function () {
    // 현재 날짜 가져오기
    const currentDate = new Date().toISOString().split("T")[0];

    // 입력 필드에서 값 가져오기
    const updatedDetails = {
      companyNumber: document.getElementById("company-number").textContent,
      registrationDate: document.getElementById("registration-date").textContent,
      modificationDate: currentDate, // 정보 수정일자를 오늘 날짜로 설정
      tradeItem: document.getElementById("trade-item").value,
      name: document.getElementById("company-name").value,
      nameEn: document.getElementById("company-name-en").value,
      businessNumber: document.getElementById("business-number").value,
      representativeName: document.getElementById("representative-name").value,
      mobilePhone: document.getElementById("mobile-phone").value,
      phoneNumber: document.getElementById("phone-number").value,
      items: Array.from(document.querySelectorAll("#companyDetail-container tbody tr")).map((row) => ({
        no: row.cells[0].textContent, // NO. 필드는 수정하지 않음
        name: row.cells[1].querySelector("input").value,
        price: parseInt(row.cells[2].querySelector("input").value.replace(/,/g, ""), 10),
      })),
    };

    // 업데이트된 정보를 서버에 전송
    fetch("http://서버주소/api/updateCompanyDetails", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedDetails),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);
        alert("✅저장 되었습니다✅"); // 저장 완료 알림창
        window.location.href = "../tradeCompanyDatail/tradeCompanyDatail.html"; // 저장 후 페이지 이동
      })
      .catch((error) => {
        alert("❌저장되지 않았습니다❌"); // 저장 완료 알림창

        console.error("Error:", error);
      });
  });
});
