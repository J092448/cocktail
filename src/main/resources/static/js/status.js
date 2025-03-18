function setStatusColor() {
    document.querySelectorAll(".menu-status").forEach(function (element) {
        var statusValue = element.innerText.trim();
        console.log("상태값 확인:", statusValue); // 디버깅 로그 추가

        // 기존 클래스 제거 후 새 클래스 추가 (중복 방지)
        element.classList.remove("status-available", "status-soldout", "status-unknown");

        if (statusValue === "판매중") {
            element.classList.add("status-available");
        } else if (statusValue === "품절") {
            element.classList.add("status-soldout");
        } else {
            element.classList.add("status-unknown");
        }
    });
}

// 페이지가 로드될 때 실행
document.addEventListener("DOMContentLoaded", setStatusColor);
