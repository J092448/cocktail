// ✅ DOMContentLoaded 이벤트: HTML 문서가 로드되었을 때 실행
document.addEventListener("DOMContentLoaded", () => {
  // 현재 연도와 월을 계산
  let currentYear = new Date().getFullYear(); // 현재 연도
  let currentMonth = new Date().getMonth() + 1; // 현재 월 (0부터 시작하므로 +1 필요)

  // ✅ 특정 연도와 월의 매출 데이터를 API에서 가져오는 함수
  function fetchSalesData(year, month) {
    fetch(`/api/calendar/sales?year=${year}&month=${month}`) // API 요청
        .then(response => response.json()) // 응답 데이터를 JSON으로 변환
        .then(data => {
          console.log("판매 데이터:", data); // 가져온 데이터 로그 출력
        })
        .catch(error => {
          console.error("판매 데이터 가져오기 오류:", error); // 오류 시 로그 출력
        });
  }

  // ✅ 현재 연도와 월의 매출 데이터를 가져오는 요청
  fetch(`/api/calendar/sales?year=${currentYear}&month=${currentMonth}`)
      .then(response => response.json()) // JSON 형식으로 변환
      .then(data => {
        console.log("받은 데이터:", data); // 데이터 확인
        generateCalendar(data); // 캘린더 생성 함수 호출
      })
      .catch(error => {
        console.error("매출 데이터 불러오기 오류:", error); // 오류 발생 시 로그 출력
        generateCalendar([]); // 빈 데이터로 캘린더 생성
      });

  // ✅ 캘린더 생성 함수
  function generateCalendar(salesData) {
    const calendar = document.getElementById("calendar"); // 캘린더 테이블
    const monthYear = document.getElementById("month-year"); // 월/연도 헤더
    calendar.innerHTML = ""; // 이전 캘린더 초기화

    const daysInMonth = new Date(currentYear, currentMonth, 0).getDate(); // 현재 월의 일수 계산
    const firstDay = new Date(currentYear, currentMonth - 1, 1).getDay(); // 첫 번째 날의 요일 계산

    monthYear.textContent = `${currentYear}년 ${currentMonth}월`; // 월/연도 표시

    const table = document.createElement("table"); // 테이블 요소 생성
    const headerRow = document.createElement("tr"); // 헤더 행 생성

    // ✅ 요일 헤더 생성
    const weekdays = ["일", "월", "화", "수", "목", "금", "토", "주차별 매출액"];
    weekdays.forEach((day, index) => {
      const th = document.createElement("th");
      th.textContent = day; // 요일 이름 설정
      if (index === 0) th.style.color = "red"; // 일요일 빨간색
      if (index === 6) th.style.color = "blue"; // 토요일 파란색
      headerRow.appendChild(th); // 헤더 행에 추가
    });
    table.appendChild(headerRow); // 테이블에 헤더 행 추가

    let currentRow = document.createElement("tr"); // 현재 행 생성
    for (let i = 0; i < firstDay; i++) {
      const td = document.createElement("td");
      td.innerHTML = "-"; // 빈 셀
      currentRow.appendChild(td);
    }

    let weekSales = Array(6).fill(0); // 주차별 매출액 배열
    let dailySums = Array(7).fill(0); // 요일별 매출 합계
    let totalSales = 0; // 월 매출액 초기화
    let currentWeek = 0; // 현재 주차 인덱스

    for (let day = 1; day <= daysInMonth; day++) {
      const dayCell = document.createElement("td");
      dayCell.classList.add("calendar-day"); // 클래스 추가

      // 수정된 부분: 특정 날짜의 매출 데이터 가져오기
      const salesDataForDay = salesData.find(item => {
        const itemDate = new Date(item.date);
        return itemDate.getDate() === day &&
            itemDate.getMonth() + 1 === currentMonth &&
            itemDate.getFullYear() === currentYear;
      });

      const dailySales = salesDataForDay ? salesDataForDay.salesAmount : 0;

      totalSales += dailySales; // 월 매출액에 추가
      weekSales[currentWeek] += dailySales; // 주차별 매출액에 추가
      dailySums[new Date(currentYear, currentMonth - 1, day).getDay()] += dailySales; // 요일별 매출 합계 추가

      // ✅ 날짜와 매출액 표시
      dayCell.innerHTML = `<div class="date">${day}</div><div class="sales">${dailySales.toLocaleString()}</div>`;

      const dayOfWeek = new Date(currentYear, currentMonth - 1, day).getDay();
      if (dayOfWeek === 0) dayCell.style.color = "red"; // 일요일 빨간색
      if (dayOfWeek === 6) dayCell.style.color = "blue"; // 토요일 파란색

      currentRow.appendChild(dayCell);

      // 한 주가 끝났거나 마지막 날이라면
      if (dayOfWeek === 6 || day === daysInMonth) {
        for (let i = dayOfWeek + 1; i < 7; i++) {
          const emptyCell = document.createElement("td");
          emptyCell.innerHTML = "-"; // 빈 셀 추가
          currentRow.appendChild(emptyCell);
        }

        // ✅ 주차 매출액 추가
        const weekCell = document.createElement("td");
        weekCell.innerHTML = `<div class="week-label">${currentWeek + 1}주차 매출액</div><div class="sales">${weekSales[currentWeek].toLocaleString()}</div>`;
        currentRow.appendChild(weekCell);

        table.appendChild(currentRow); // 현재 행을 테이블에 추가
        currentRow = document.createElement("tr"); // 새로운 행 생성
        currentWeek++;
      }
    }

    // ✅ 마지막 줄: 요일별 합계와 월 매출액 표시
    const totalRow = document.createElement("tr");
    totalRow.classList.add("total-row");

    weekdays.forEach((day, index) => {
      const totalCell = document.createElement("td");
      let salesAmount = index < 7 ? dailySums[index] ?? 0 : totalSales; // 일별 또는 월 매출액

      totalCell.style.position = "relative"; // 텍스트 절대 위치
      totalCell.innerHTML = `
        <span style="position: absolute; top: 4px; left: 4px; font-weight: bold;
            ${index === 0 ? "color: red;" : index === 6 ? "color: blue;" : "color: black;" }">
            ${day}
        </span>
        <span class="sales" style="color: blue; font-weight: bold; display: block; margin-top: 20px;">
            ${salesAmount.toLocaleString()}
        </span>
      `;
      totalRow.appendChild(totalCell);
    });

    table.appendChild(totalRow); // 총합 행 추가
    calendar.appendChild(table); // 테이블을 캘린더에 추가
  }

  // ✅ 이전/다음 달 버튼 클릭 이벤트 추가
  document.getElementById("prev").addEventListener("click", () => {
    changeMonth(-1); // 이전 달
  });

  document.getElementById("next").addEventListener("click", () => {
    changeMonth(1); // 다음 달
  });

  // ✅ 월 변경 처리 함수
  function changeMonth(direction) {
    currentMonth += direction;
    if (currentMonth < 1) {
      currentMonth = 12; // 1월 이전이면 12월로 변경
      currentYear--;
    } else if (currentMonth > 12) {
      currentMonth = 1; // 12월 이후면 1월로 변경
      currentYear++;
    }
    fetch(`/api/calendar/sales?year=${currentYear}&month=${currentMonth}`) // 새로운 데이터 요청
        .then(response => response.json())
        .then(data => generateCalendar(data)) // 캘린더 업데이트
        .catch(error => {
          console.error("매출 데이터 불러오기 오류:", error); // 오류 처리
          generateCalendar([]); // 빈 데이터로 캘린더 초기화
        });
  }
});
