package com.ontherocks.cocktail.Contoroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SalesMenuController {
    @RequestMapping("/Sale")
    public String home(Model model) {
        return "SalesMenuList";
    }
}
