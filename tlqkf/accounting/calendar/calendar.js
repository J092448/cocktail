let currentYear = 2025; // 현재 연도
let currentMonth = 1; // 현재 월

function generateCalendar() {
  const calendar = document.getElementById("calendar");
  const monthYear = document.getElementById("month-year");
  calendar.innerHTML = ""; // 기존 달력 내용 초기화

  const daysInMonth = new Date(currentYear, currentMonth, 0).getDate(); // 해당 월의 마지막 날
  const firstDay = new Date(currentYear, currentMonth - 1, 1).getDay(); // 해당 월의 첫 날의 요일

  // 월과 연도 표시
  monthYear.textContent = `${currentYear}년 ${currentMonth}월`;

  // 1행 8열의 표 생성
  const table = document.createElement("table");
  const headerRow = document.createElement("tr");
  headerRow.classList.add("header-row"); // 요일 헤더에 클래스 추가

  // 요일 헤더 추가
  const weekdays = ["일", "월", "화", "수", "목", "금", "토", "주차별 매출액"];
  weekdays.forEach((day, index) => {
    const th = document.createElement("th");
    th.textContent = day;
    th.classList.add("header"); // 요일 헤더에 클래스 추가
    if (index === 0) {
      // 일요일 열에 해당하는 경우
      th.classList.add("sunday");
    }
    if (index === 6) {
      // 토요일 열에 해당하는 경우
      th.classList.add("saturday");
    }
    headerRow.appendChild(th);
  });
  table.appendChild(headerRow);

  // 빈 칸 추가
  let currentRow = document.createElement("tr");
  for (let i = 0; i < firstDay; i++) {
    const td = document.createElement("td");
    td.innerHTML = "-"; // 빈 셀에 - 표시
    currentRow.appendChild(td);
  }
  table.appendChild(currentRow);

  // 날짜와 매출액 추가
  let weekSales = Array(6).fill(0); // 주차별 매출액 초기화 (6주차까지 지원)
  let dailySums = Array(7).fill(0); // 요일별 매출액 합계 초기화
  let totalSales = 0; // 총 매출액 초기화
  let currentWeek = 0; // 현재 주차

  for (let day = 1; day <= daysInMonth; day++) {
    const dayCell = document.createElement("td");

    // 일별 매출액 입력 (랜덤 값 사용)
    const dailySales = Math.floor(Math.random() * 100); // 랜덤 매출액
    totalSales += dailySales;
    weekSales[currentWeek] += dailySales; // 주차별 매출액 누적
    dailySums[new Date(currentYear, currentMonth - 1, day).getDay()] += dailySales; // 요일별 합계

    // 날짜와 매출액 표시
    dayCell.innerHTML = `
      <div class="date">${day}</div>
      <div class="sales">${dailySales}</div>
    `;
    if (new Date(currentYear, currentMonth - 1, day).getDay() === 0) {
      // 일요일 열에 해당하는 경우
      dayCell.classList.add("sunday");
    }
    if (new Date(currentYear, currentMonth - 1, day).getDay() === 6) {
      // 토요일 열에 해당하는 경우
      dayCell.classList.add("saturday");
    }
    currentRow.appendChild(dayCell);

    const dayOfWeek = new Date(currentYear, currentMonth - 1, day).getDay();

    if (dayOfWeek === 6 || day === daysInMonth) {
      // 주의 마지막 날이거나 월의 마지막 날인 경우
      // 빈 칸 채우기
      for (let i = dayOfWeek + 1; i < 7; i++) {
        const emptyCell = document.createElement("td");
        emptyCell.innerHTML = "-";
        currentRow.appendChild(emptyCell);
      }

      // 주차별 매출액 추가
      const weekCell = document.createElement("td");
      weekCell.innerHTML = `<div class="week-label">${currentWeek + 1}주차 매출액</div><div class="sales">${
        weekSales[currentWeek]
      }</div>`;
      currentRow.appendChild(weekCell);

      table.appendChild(currentRow);
      currentRow = document.createElement("tr");

      currentWeek++; // 주차 증가
    }
  }

  // 총합을 표시할 행 추가
  const totalRow = document.createElement("tr");
  totalRow.classList.add("total-row");

  // 각 요일의 합계 추가
  dailySums.forEach((sum, index) => {
    const totalCell = document.createElement("td");
    totalCell.innerHTML = `<div class="summary-label">${weekdays[index]}</div><div class="sales" style="color: blue; font-weight: normal;">${sum}</div>`;
    if (index === 0) {
      // 일요일 열에 해당하는 경우
      totalCell.classList.add("sunday");
    }
    if (index === 6) {
      // 토요일 열에 해당하는 경우
      totalCell.classList.add("saturday");
    }
    totalRow.appendChild(totalCell);
  });

  // 월 매출액 합계 추가
  const totalMonthlyCell = document.createElement("td");
  totalMonthlyCell.innerHTML = `<div class="summary-label" style="font-weight: normal;">월 매출액</div><div class="sales" style="color: blue; font-weight: normal;">${totalSales}</div>`;
  totalRow.appendChild(totalMonthlyCell);

  table.appendChild(totalRow);
  calendar.appendChild(table); // 달력에 표 추가
}

// 이전 달 또는 다음 달로 변경하는 함수
function changeMonth(direction) {
  currentMonth += direction; // 방향에 따라 월을 증가 또는 감소
  if (currentMonth < 1) {
    // 1월보다 작으면 12월로 설정하고 연도 감소
    currentMonth = 12;
    currentYear--;
  } else if (currentMonth > 12) {
    // 12월보다 크면 1월로 설정하고 연도 증가
    currentMonth = 1;
    currentYear++;
  }
  generateCalendar(); // 달력 재생성
}

// 페이지 로드 시 달력 생성
document.addEventListener("DOMContentLoaded", generateCalendar);
