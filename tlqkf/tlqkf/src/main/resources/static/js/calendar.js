document.addEventListener("DOMContentLoaded", () => {
  let currentYear = new Date().getFullYear();
  let currentMonth = new Date().getMonth() + 1;

  // ✅ 현재 연도, 월의 매출 데이터 불러오기
  fetch(`/api/sales?year=${currentYear}&month=${currentMonth}`)
      .then(response => response.json())
      .then(data => {
        console.log("받은 데이터:", data); // 데이터 확인
        generateCalendar(data);
      })
      .catch(error => {
        console.error("매출 데이터 불러오기 오류:", error);
        generateCalendar([]);
      });

  function generateCalendar(salesData) {
    const calendar = document.getElementById("calendar");
    const monthYear = document.getElementById("month-year");
    calendar.innerHTML = "";

    const daysInMonth = new Date(currentYear, currentMonth, 0).getDate();
    const firstDay = new Date(currentYear, currentMonth - 1, 1).getDay();

    monthYear.textContent = `${currentYear}년 ${currentMonth}월`;

    const table = document.createElement("table");
    const headerRow = document.createElement("tr");

    // ✅ 요일 헤더 수정 ("월 매출액" → "주차별 매출액")
    const weekdays = ["일", "월", "화", "수", "목", "금", "토", "주차별 매출액"];
    weekdays.forEach((day, index) => {
      const th = document.createElement("th");
      th.textContent = day;
      if (index === 0) th.style.color = "red"; // 일요일 빨간색
      if (index === 6) th.style.color = "blue"; // 토요일 파란색
      headerRow.appendChild(th);
    });
    table.appendChild(headerRow);

    let currentRow = document.createElement("tr");
    for (let i = 0; i < firstDay; i++) {
      const td = document.createElement("td");
      td.innerHTML = "-";
      currentRow.appendChild(td);
    }

    let weekSales = Array(6).fill(0); // 주차별 매출액 유지
    let dailySums = Array(7).fill(0); // 요일별 합계
    let totalSales = 0; // 월 매출액
    let currentWeek = 0;

    for (let day = 1; day <= daysInMonth; day++) {
      const dayCell = document.createElement("td");

      // ✅ 날짜 비교 수정 (date: [YYYY, MM, DD] 배열 처리)
      const salesDataForDay = salesData.find(item => {
        if (Array.isArray(item.date) && item.date.length === 3) {
          const itemDate = new Date(item.date[0], item.date[1] - 1, item.date[2]).getDate();
          return itemDate === day;
        }
        return false;
      });

      // ✅ `salesAmount`가 없을 경우 0으로 설정
      const dailySales = salesDataForDay?.salesAmount ?? 0;

      totalSales += dailySales;
      weekSales[currentWeek] += dailySales;
      dailySums[new Date(currentYear, currentMonth - 1, day).getDay()] += dailySales;

      // ✅ 숫자 형식 변환 (오류 방지)
      dayCell.innerHTML = `<div class="date">${day}</div><div class="sales">${dailySales.toLocaleString()}</div>`;

      const dayOfWeek = new Date(currentYear, currentMonth - 1, day).getDay();
      if (dayOfWeek === 0) dayCell.style.color = "red"; // 일요일 빨간색
      if (dayOfWeek === 6) dayCell.style.color = "blue"; // 토요일 파란색

      currentRow.appendChild(dayCell);

      if (dayOfWeek === 6 || day === daysInMonth) {
        for (let i = dayOfWeek + 1; i < 7; i++) {
          const emptyCell = document.createElement("td");
          emptyCell.innerHTML = "-";
          currentRow.appendChild(emptyCell);
        }

        // ✅ 주차별 매출액 유지 (한글 레이블 추가)
        const weekCell = document.createElement("td");
        weekCell.innerHTML = `<div class="week-label">${currentWeek + 1}주차 매출액</div><div class="sales">${weekSales[currentWeek].toLocaleString()}</div>`;
        currentRow.appendChild(weekCell);

        table.appendChild(currentRow);
        currentRow = document.createElement("tr");
        currentWeek++;
      }
    }

    // ✅ 마지막 줄: 한글 레이블 + 요일별 합계 + 월 매출액 (모든 숫자는 파란색, 요일별 색상 적용)
    const totalRow = document.createElement("tr");
    totalRow.classList.add("total-row");

    weekdays.forEach((day, index) => {
      const totalCell = document.createElement("td");
      let salesAmount = index < 7 ? dailySums[index] ?? 0 : totalSales;

      totalCell.style.position = "relative"; // 절대 위치 적용을 위한 부모 설정
      totalCell.innerHTML = `
                <span style="position: absolute; top: 4px; left: 4px; font-weight: bold;
                    ${index === 0 ? "color: red;" : index === 6 ? "color: blue;" : "color: black;"}">
                    ${day}
                </span>
                <span class="sales" style="color: blue; font-weight: bold; display: block; margin-top: 20px;">
                    ${salesAmount.toLocaleString()}
                </span>
            `;
      totalRow.appendChild(totalCell);
    });

    table.appendChild(totalRow);
    calendar.appendChild(table);
  }

  // ✅ 이전/다음 달 버튼 기능 추가
  document.getElementById("prev").addEventListener("click", () => {
    changeMonth(-1);
  });

  document.getElementById("next").addEventListener("click", () => {
    changeMonth(1);
  });

  function changeMonth(direction) {
    currentMonth += direction;
    if (currentMonth < 1) {
      currentMonth = 12;
      currentYear--;
    } else if (currentMonth > 12) {
      currentMonth = 1;
      currentYear++;
    }
    fetch(`/api/sales?year=${currentYear}&month=${currentMonth}`)
        .then(response => response.json())
        .then(data => generateCalendar(data))
        .catch(error => {
          console.error("매출 데이터 불러오기 오류:", error);
          generateCalendar([]);
        });
  }
});
