document.addEventListener("DOMContentLoaded", async () => {
  let companies = [];
  const rowsPerPage = 10;
  let currentPage = 1;

  async function fetchCompanies() {
    try {
      const response = await fetch("http://192.168.0.24:3306/cocktail_bar_erp");
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      companies = await response.json();
    } catch (error) {
      console.error("Error fetching companies:", error);
      companies = [];
    } finally {
      updateTable();
      displayPagination();
    }
  }

  function loadState() {
    const savedState = JSON.parse(localStorage.getItem("companiesState"));
    if (savedState) {
      companies.forEach((company) => {
        const savedCompany = savedState.find((o) => o.번호 === company.번호);
        if (savedCompany) {
          company.상태 = savedCompany.상태;
        }
      });
    }
  }

  function saveState() {
    localStorage.setItem("companiesState", JSON.stringify(companies));
  }

  function displayTablePage(page) {
    companies.sort((a, b) => parseInt(b.번호) - parseInt(a.번호));
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedCompanies = companies.slice(start, end);
    const tableBody = document.querySelector("#companyTable tbody");
    tableBody.innerHTML = "";
    if (companies.length === 0) {
      const emptyMessageRow = document.createElement("tr");
      const emptyMessageCell = document.createElement("td");
      emptyMessageCell.colSpan = 5;
      emptyMessageCell.textContent = "등록된 업체가 없습니다.";
      emptyMessageCell.classList.add("empty-message");
      emptyMessageRow.appendChild(emptyMessageCell);
      tableBody.appendChild(emptyMessageRow);
    } else {
      paginatedCompanies.forEach((company) => {
        const row = document.createElement("tr");
        row.innerHTML = `
                  <td><a href="tradeCompany.html?companyNumber=${company.번호}">${company.번호}</a></td>
                  <td>${company.품목명}</td>
                  <td>${company.신청일자}</td>
                  <td>${company.수량}</td>
                  <td>${company.대표자}</td>
              `;
        tableBody.appendChild(row);
      });
    }
  }

  function displayPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";
    const totalPages = Math.ceil(companies.length / rowsPerPage);
    const startPage = Math.floor((currentPage - 1) / 10) * 10 + 1;
    const endPage = Math.min(startPage + 9, totalPages);

    const prevButton = document.createElement("button");
    prevButton.textContent = "이전";
    prevButton.classList.add("prev");
    prevButton.addEventListener("click", () => {
      if (currentPage > 1) {
        currentPage--;
        updateTable();
        saveState(); // 테이블 업데이트 후 상태 저장
      }
    });
    pagination.appendChild(prevButton);

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
        saveState(); // 페이지 변경 후 상태 저장
      });
      pagination.appendChild(button);
    }

    const nextButton = document.createElement("button");
    nextButton.textContent = "다음";
    nextButton.classList.add("next");
    nextButton.addEventListener("click", () => {
      if (currentPage < totalPages) {
        currentPage++;
        updateTable();
        saveState(); // 테이블 업데이트 후 상태 저장
      }
    });
    pagination.appendChild(nextButton);

    const applyCompanyButton = document.createElement("button");
    applyCompanyButton.textContent = "업체 등록";
    applyCompanyButton.classList.add("apply-company");
    applyCompanyButton.addEventListener("click", () => {
      window.location.href = "../tradeCompany/tradeCompany.html";
      saveState(); // 업체 등록 후 상태 저장
    });
    pagination.appendChild(applyCompanyButton);

    if (companies.length === 0) {
      const button = document.createElement("button");
      button.textContent = 1;
      button.classList.add("page");
      button.disabled = true;
      pagination.insertBefore(button, nextButton);
    }
  }

  async function fetchCompanyList() {
    try {
      const response = await fetch("https://your-api-endpoint.com/companyList");
      const data = await response.json();
      const companyList = document.getElementById("companyList");
      data.companies.forEach((company) => {
        const listItem = document.createElement("li");
        listItem.className = "company-item";
        listItem.innerHTML = `<a href="companyDetails.html?companyNumber=${company.companyNumber}">거래 업체 번호: no.${company.companyNumber}</a>`;
        companyList.appendChild(listItem);
      });
    } catch (error) {
      console.error("Error fetching company list:", error);
    }
  }

  function updateTable() {
    displayTablePage(currentPage);
    displayPagination();
    saveState(); // 테이블 업데이트 후 상태 저장
  }

  window.onload = function () {
    fetchCompanyList();
  };

  loadState();
  await fetchCompanies();
  displayPagination();
});
