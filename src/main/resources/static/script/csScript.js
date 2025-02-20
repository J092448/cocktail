document.addEventListener("DOMContentLoaded", () => {
    const addButtons = document.querySelectorAll(".add-to-cart");

    addButtons.forEach(button => {
        button.addEventListener("click", () => {
            alert("장바구니에 추가되었습니다!");
            // 장바구니에 추가하는 로직 구현
        });
    });
});
