// 사업자 번호 부분
function formatBusinessNumber(input) {
  // 숫자만 남기기
  let value = input.value.replace(/[^0-9]/g, "");
  // 최대 10자리까지만 입력 가능
  if (value.length > 10) {
    value = value.slice(0, 10);
  }
  // 3자리, 5자리 뒤에 하이픈 추가
  if (value.length > 3) {
    value = value.slice(0, 3) + "-" + value.slice(3);
  }
  if (value.length > 6) {
    value = value.slice(0, 6) + "-" + value.slice(6);
  }
  input.value = value;
}

//전화번호, 휴대전화 부분
function formatPhoneNumber(input) {
  // 숫자만 남기기
  let value = input.value.replace(/[^0-9]/g, "");
  // 최대 11자리까지만 입력 가능
  if (value.length > 11) {
    value = value.slice(0, 11);
  }
  // 하이픈 추가
  if (value.length > 3) {
    value = value.slice(0, 3) + "-" + value.slice(3);
  }
  if (value.length > 7 && value.length <= 10) {
    value = value.slice(0, 7) + "-" + value.slice(7);
  } else if (value.length > 10) {
    value = value.slice(0, 8) + "-" + value.slice(8);
  }
  input.value = value;
}

document.getElementById("submitCompany").addEventListener("click", function () {
  // 알림창에서 확인과 취소 버튼 처리
  if (
    confirm(
      `업체 등록을 완료하시겠습니까? \n[확인]을 누르면 등록이 완료되며, [취소]를 누르면 작성 중인 업체 등록 페이지로 돌아갑니다.\n감사합니다.`
    )
  ) {
    // 확인을 누른 경우
    window.location.href = "../companyList/companyList.html";
  } else {
    // 취소를 누른 경우
    alert("업체 등록이 취소되었습니다. 작성 중인 업체 등록 페이지로 돌아갑니다.");
  }
});

document.getElementById("cancelCompany").addEventListener("click", function () {
  alert("업체 등록이 취소되었습니다. 페이지를 새로고침합니다.");
  location.reload(); // 페이지 새로고침
});
