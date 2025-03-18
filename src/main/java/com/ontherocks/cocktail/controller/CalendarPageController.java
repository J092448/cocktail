package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.model.AccountingDataDTO;
import com.ontherocks.cocktail.model.CalendarDTO;
import com.ontherocks.cocktail.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarPageController {


    // 캘린더 페이지를 반환하는 메서드 (예: HTML 페이지 이름)
    @GetMapping("/calendar")
    public String showCalendarPage(Model model) {
        // "calendar.html" 페이지를 반환하여 렌더링
        return "pages/calendar";
    }
}
