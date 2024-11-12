package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.order.OrderCreateDto;
import com.example.shoppingmall.dto.order.OrderInquiryDto;
import com.example.shoppingmall.dto.order.OrderProductCreateDto;
import com.example.shoppingmall.entity.*;
import com.example.shoppingmall.exception.StockNotExistException;
import com.example.shoppingmall.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public List<OrderInquiryDto> getAllOrdersByMember(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        List<Orders> orders = ordersRepository.findAllByMember(member);
        if (!orders.isEmpty()) {
            return orders.stream()
                    .map(order -> OrderInquiryDto.of(order))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public OrderInquiryDto getOneOrder(long orderId) {
        Optional<Orders> byId = ordersRepository.findById(orderId);
        if (byId.isPresent()) {
            return OrderInquiryDto.of(byId.get());
        }
        return null;
    }

    public OrderInquiryDto getOneOrderByMember(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        Optional<Orders> byMember = ordersRepository.findByMember(member);
        if (byMember.isPresent()) {
            Orders order = byMember.get();
            return OrderInquiryDto.of(order);
        }
        return null;
    }

    public OrderInquiryDto createOrder(OrderCreateDto orderDto, OrderProductCreateDto... orderProductDtos) {
        Member member = memberRepository.findById(orderDto.getMemberId()).get();
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductCreateDto orderProductDto : orderProductDtos) {
            Product product = productRepository.findById(orderProductDto.getProductId()).get();
            Stock stock = stockRepository.findByWareHouseAndProduct(WareHouse.KR, product).get();
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderProductDto.getQuantity(), product.getPrice(), stock);
            orderProducts.add(orderProduct);
        }

        Delivery delivery = new Delivery(
                null,
                null,
                orderDto.getAddress(),
                DeliveryStatus.PREPARED,
                LocalDateTime.now()
        );

        Orders orders = Orders.createOrders(member, delivery, orderProducts);
        delivery.setOrders(orders);

        Orders save = ordersRepository.save(orders);
        log.info("order: {}", save.getOrderId());
        OrderInquiryDto orderInquiryDto = OrderInquiryDto.of(save);
        log.info("orderInquiryDto: {}", orderInquiryDto);

        return orderInquiryDto;
    }

    public void cancelOrder(int orderId) {

    }

    public void cancelOrderProduct(long orderProductId) {
        //전체
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).get();
        Optional<Stock> byWarehouseAndProduct = stockRepository.findByWareHouseAndProduct(WareHouse.KR, orderProduct.getProduct());
        if (byWarehouseAndProduct.isPresent()) {
            Stock stock = byWarehouseAndProduct.get();
            //상품에 대한 캔슬, 재고 up
            orderProduct.cancelOrderProducts(stock);
            Orders order = orderProduct.getOrders();
            //오더 헤더에 대한 부분취소 / 전체취소 처리
            //A = 3, B = 2(취소) > 전체취소
            //A = 3, B = 2 > 부분취소
            List<OrderProduct> details = order.getOrderProducts();
            int countCancel = 0;
            for (OrderProduct detail : details) {
                if (detail.getOrderProductStatus() == OrderProductStatus.CANCELED) {
                    countCancel++;
                }
            }
            if (details.size() - countCancel > 1) {
                order.partialOrderCancel(orderProduct);
            } else {
                order.totalOrderCancel();
            }
            //order가 저장될 때 delivery와 orderProduct는 영속성 전이에 의해 함께 저장된다.
            Orders save = ordersRepository.save(order);
        } else {
            throw new StockNotExistException("재고 부족 : 주문 취소할 수 없습니다.");
        }
    }
}
