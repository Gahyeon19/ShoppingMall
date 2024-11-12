package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.order.OrderCreateDto;
import com.example.shoppingmall.dto.order.OrderInquiryDto;
import com.example.shoppingmall.dto.order.OrderProductCreateDto;
import com.example.shoppingmall.entity.Orders;
import com.example.shoppingmall.service.MemberService;
import com.example.shoppingmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderApiController {
    private final OrderService orderService;
    private final MemberService memberService;

//    @GetMapping("/list/{userId}")
//    private String viewUserOrders(@PathVariable(name = "userId") String userId, Model model) {
//        model.addAttribute("orders", orderService.getOneOrderByMember(userId));
//        return "order/userOrders";
//    }

    // 해당 유저의 주문 내역 조회
    @GetMapping("/members/{userId}")
    public List<OrderInquiryDto> getAllOrdersForOneMember(@PathVariable("userId") String userId) {
        return orderService.getAllOrdersByMember(userId);
    }

    @GetMapping("/{orderId}")
    public OrderInquiryDto getOneOrder(@PathVariable("orderId") long orderId) {
        return orderService.getOneOrder(orderId);
    }

    @PutMapping
    public OrderInquiryDto createNewOrder(@RequestBody OrderCreateDto orderDto,
                                          @RequestBody OrderProductCreateDto... orderProductDtos) {
        return orderService.createOrder(orderDto, orderProductDtos);
    }





    //주문등록
    @GetMapping("/add")
    private String addOrder(Model model) {
        model.addAttribute("order", new Orders());
        return "order/orderAdd";
    }

    @PostMapping("/add")
    private String createOrder(@RequestBody Orders orders) {
        OrderCreateDto orderCreateDto = new OrderCreateDto(
                orders.getMember().getMemberId(),
                orders.getMember().getAddress()
        );

        orderService.createOrder(orderCreateDto);
        return "redirect:/orders";
    }


    //주문취소
    @GetMapping("/cancel")
    private String cancel(@RequestParam(name = "orderId") int orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order";
    }

    @PostMapping("/cancel")
    private String cancelOrder() {

        return "order/cancelOrder";
    }


    //주문상태변경


}
