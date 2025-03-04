package com.ontherocks.cocktail.controller;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/phoneNumber")
public class PhoneNumberVerificationController {

    @PostConstruct
    public void init() {
        System.out.println("TWILIO_ACCOUNT_SID: " + System.getenv("TWILIO_ACCOUNT_SID"));
        System.out.println("TWILIO_AUTH_TOKEN: " + System.getenv("TWILIO_AUTH_TOKEN"));
    }


    // Twilio credentials
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String VERIFICATION_SERVICE_SID = "VAa33c22a53e6dc6c08755faeae91f0ef9"; // your verification service SID

    // 생성자에서 Twilio 초기화
    public PhoneNumberVerificationController() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @GetMapping(value = "/generateOTP")
    public ResponseEntity<String> generateOTP(@RequestParam String phoneNumber) {
        // 입력된 전화번호의 공백 제거
        phoneNumber = phoneNumber.trim();

// 전화번호가 +로 시작하지 않는 경우 + 추가
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+" + phoneNumber;
        }
        System.out.println("Received phone number: '" + phoneNumber + "'"); // 전화번호 출력

        // 전화번호가 올바른 형식인지 확인
        if (!phoneNumber.startsWith("+")) {
            return new ResponseEntity<>("Invalid phone number format. It should start with '+'.", HttpStatus.BAD_REQUEST);
        }

        try {
            Verification verification = Verification.creator(
                            VERIFICATION_SERVICE_SID,
                            phoneNumber,
                            "sms")
                    .create();

            // OTP 생성 후 상태를 출력
            System.out.println("OTP has been successfully generated for " + phoneNumber + ". Status: " + verification.getStatus());

            return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error generating OTP: " + e.getMessage());
            return new ResponseEntity<>("Failed to generate OTP: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<?> verifyUserOTP(@RequestParam String phoneNumber, @RequestParam String code) {
        // 전화번호에서 공백 제거
        phoneNumber = phoneNumber.trim();

        // 전화번호가 +로 시작하지 않는 경우 + 추가
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+" + phoneNumber;
        }

        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(VERIFICATION_SERVICE_SID)
                    .setTo(phoneNumber)
                    .setCode(code)
                    .create();

            // Verification 상태를 출력
            System.out.println("Verification status for " + phoneNumber + ": " + verificationCheck.getStatus());

            if ("approved".equals(verificationCheck.getStatus())) {
                return new ResponseEntity<>("코드 인증이 완료 되었습니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("코드 인증이 실패 하였습니다.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.err.println("Error verifying OTP: " + e.getMessage());
            return new ResponseEntity<>("코드 인증이 실패 하였습니다.: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
