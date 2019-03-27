package com.needayea.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@ComponentScan(basePackages = {"com.needayea.pay"})
@SpringBootApplication
@Controller
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

    /**
     *  跳转到订单页面
     * @return String
     */
    @RequestMapping(value = "/")
    public String sayHello(){
        return "index";
    }
}
