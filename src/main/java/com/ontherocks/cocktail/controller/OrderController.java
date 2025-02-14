package com.ontherocks.cocktail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/pages/menuOrder")
    public String menuOrderPage() {
        return "menuOrder";
    }

    @GetMapping("/pages/menuDetail")
    public String menuDetailPage() {
        return "menuDetail";
    }

    @GetMapping("/pages/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/pages/orderList")
    public String orderListPage() {
        return "orderList";
    }

}
