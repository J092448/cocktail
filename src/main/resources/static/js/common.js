// function showAlert() {
//     const msg = new URLSearchParams(window.location.search).get('msg'); // 쿼리 파라미터에서 msg 가져오기
//     const error = new URLSearchParams(window.location.search).get('error'); // 쿼리 파라미터에서 error 가져오기
//
//     if (msg) {
//         alert(msg); // 성공 메시지가 있을 때 alert을 띄움
//     }
//     if (error) {
//         alert(error); // 실패 메시지가 있을 때 alert을 띄움
//     }
// }

function checkUsername() {
    const username = $("#username").val();
    if (username === "") {
        alert("아이디를 입력해주세요.");
        return;
    }

    $.ajax({
        url: '/check-username', // URL 수정
        type: 'POST',
        data: {username: username},
        success: function (response) {
            alert(response); // 사용 가능한 아이디
        },
        error: function (xhr) {
            alert(xhr.responseText); // 이미 사용 중인 아이디
        }
    });
}

function validateRegisterPassword() {
    const password = $("#password").val();
    const confirmPassword = $("#confirmPassword").val();
    console.log("Password:", password);
    console.log("Confirm Password:", confirmPassword);

    // 비밀번호 규약: 소문자, 숫자, 특수문자 포함, 8자 이상
    const passwordPattern = /^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-z0-9!@#$%^&*]{8,}$/;

    if (!passwordPattern.test(password)) {
        alert("비밀번호는 소문자, 숫자, 특수문자를 포함하고 8자 이상이어야 합니다.");
        return false;
    }

    if (password !== confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    return true; // 비밀번호가 유효할 경우
}
function validateChangePassword() {
    const newPassword = $("#newPassword").val();
    const confirmPassword = $("#confirmPassword").val();

    // 비밀번호 규약: 소문자, 숫자, 특수문자 포함, 8자 이상
    const passwordPattern = /^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-z0-9!@#$%^&*]{8,}$/;

    if (!passwordPattern.test(newPassword)) {
        alert("비밀번호는 소문자, 숫자, 특수문자를 포함하고 8자 이상이어야 합니다.");
        return false;
    }


    if (newPassword !== confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }
    return true; // 비밀번호가 유효할 경우
}

function formatBusinessNumber() {
    const input = document.getElementById("businessNumber");
    let value = input.value.replace(/\D/g, ''); // 숫자 이외의 문자를 제거
    if (value.length > 10) {
        value = value.slice(0, 10); // 최대 10자리로 제한
    }
    if (value.length >= 6) {
        value = value.slice(0, 3) + '-' + value.slice(3, 5) + '-' + value.slice(5, 10); // 3-5-10 형식으로 하이픈 추가
    } else if (value.length >= 3) {
        value = value.slice(0, 3) + '-' + value.slice(3); // 3자리 후에 하이픈 추가
    }
    input.value = value; // 포맷팅된 값으로 업데이트
}




