package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.stock.StockCreateDto;
import com.example.shoppingmall.dto.member.MemberCreateDto;
import com.example.shoppingmall.dto.member.MemberInquiryDto;
import com.example.shoppingmall.dto.order.OrderCreateDto;
import com.example.shoppingmall.dto.order.OrderInquiryDto;
import com.example.shoppingmall.dto.order.OrderProductCreateDto;
import com.example.shoppingmall.dto.product.ProductCreateDto;
import com.example.shoppingmall.dto.product.ProductInquiryDto;
import com.example.shoppingmall.entity.*;
import com.example.shoppingmall.repository.MemberRepository;
import com.example.shoppingmall.repository.OrderProductRepository;
import com.example.shoppingmall.repository.OrdersRepository;
import com.example.shoppingmall.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Commit
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 주문생성테스트() {
        //Given
        MemberCreateDto memberCreateDto = new MemberCreateDto(
                "test2",
                "bbbb",
                "2222",
                "b",
                "2",
                "b"
        );
        MemberInquiryDto memberDto = memberService.addMember(memberCreateDto);
        Member member = memberRepository.findByUserId(memberDto.getUserId()).get();

        ProductCreateDto productDto = new ProductCreateDto(
                "Ptest3", 100, 120
        );
        ProductInquiryDto newProduct1 = productService.addProduct(productDto);

        productDto = new ProductCreateDto(
                "Ptest4", 100, 130
        );
        ProductInquiryDto newProduct2 = productService.addProduct(productDto);

        StockCreateDto stockDto = new StockCreateDto(
                WareHouse.KR, newProduct1.getProductId(),10000
        );
        stockService.addStock(stockDto);

        stockDto = new StockCreateDto(
                WareHouse.KR, newProduct2.getProductId(),10000
        );
        stockService.addStock(stockDto);

        // when (주문을 생성했더니)
        OrderCreateDto orderCreateDto = new OrderCreateDto(memberDto.getMemberId(), "test-address");
        OrderProductCreateDto orderProductDto1 = new OrderProductCreateDto(newProduct1.getProductId(),100);
        OrderProductCreateDto orderProductDto2 = new OrderProductCreateDto(newProduct1.getProductId(),200);
        OrderInquiryDto orderDto = orderService.createOrder(orderCreateDto, orderProductDto1, orderProductDto2);
        Orders order = ordersRepository.findById(orderDto.getOrderId()).get();
        // then
        assertThat(ordersRepository.findAllByMember(member)).hasSize(1);
        assertThat(orderProductRepository.findAllByOrders(order)).hasSize(2);
    }

    @Test
    void 전체주문취소테스트() {
        //Given - aaa가 주문한 등록된 order 가져오기
        MemberCreateDto memberCreateDto = new MemberCreateDto(
                "test1",
                "aaa",
                "1111",
                "a",
                "1",
                "a"
        );
        MemberInquiryDto memberDto = memberService.addMember(memberCreateDto);
        Member member = memberRepository.findByUserId(memberDto.getUserId()).get();
        /////////////////////////////////////////////////////////////////////////////////////
        MemberInquiryDto aaa = memberService.getOneMember("aaa");
        OrderInquiryDto orderByMember = orderService.getOneOrderByMember(aaa.getUserId());
        Orders order = null;
        OrderProduct orderProduct = null;
        Stock stock = null;
        //When
        if (orderByMember != null) {
            order = ordersRepository.findById(orderByMember.getOrderId()).get();
            orderProduct = order.getOrderProducts().get(0);
            orderService.cancelOrderProduct(orderProduct.getOrderProductId());
            stock = stockRepository.findByWareHouseAndProduct(WareHouse.KR, orderProduct.getProduct()).get();
        }

        //Then
        assertThat(order).isNotNull();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.TOTALCANCELED);
        assertThat(order.getTotalQuantity()).isEqualTo(0L);
        assertThat(orderProduct).isNotNull();
        assertThat(stock.getQuantity()).isEqualTo(10000L);
    }
}