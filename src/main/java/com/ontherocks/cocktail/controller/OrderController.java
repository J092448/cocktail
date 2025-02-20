package com.ontherocks.cocktail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/csPages/menuOrder")
    public String menuOrderPage() {
        return "/csPages/menuOrder";
    }

    @GetMapping("/csPages/menuDetail")
    public String menuDetailPage() {
        return "/csPages/menuDetail";
    }

    @GetMapping("/csPages/cart")
    public String cartPage() {
        return "/csPages/cart";
    }

    @GetMapping("/csPages/orderList")
    public String orderListPage() {
        return "/csPages/orderList";
    }

}
