package com.ontherocks.tlqkf.controller;

import ch.qos.logback.core.model.Model;
import com.ontherocks.tlqkf.model.CalendarDTO;
import com.ontherocks.tlqkf.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CalendarCont {

    @Autowired
    CalendarService calendarService;

    @GetMapping("/calendar")
    public String calendar(Model model) {


        return "calendar";
    }



}
