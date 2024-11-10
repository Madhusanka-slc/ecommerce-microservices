package com.slc.ecommerce.order;

import com.slc.ecommerce.customer.CustomerClient;
import com.slc.ecommerce.exception.BusinessException;
import com.slc.ecommerce.kafka.OrderConfirmation;
import com.slc.ecommerce.kafka.OrderProducer;
import com.slc.ecommerce.orderline.OrderLineRequest;
import com.slc.ecommerce.orderline.OrderLineService;
import com.slc.ecommerce.payment.PaymentClient;
import com.slc.ecommerce.payment.PaymentRequest;
import com.slc.ecommerce.product.ProductClient;
import com.slc.ecommerce.product.PurchaseRequest;
import com.slc.ecommerce.product.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {

        // check the customer
        var customer = customerClient.findCustomerById(request.customerId()).orElseThrow(() -> new BusinessException(format("Cannot create order:: Noo Customer exists with the provided ID %s", request.customerId())));

        // purchase the products --> product-ms (RestTemplate)
        List<PurchaseResponse> purchaseProducts = productClient.purchaseProducts(request.products());

        // persist order lines
        Order order = orderRepository.save(mapper.toOrder(request));
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }


        // start payment process
        // TODO
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);


        // send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );


        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream().map(mapper::fromOrder).collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId).map(mapper::fromOrder).orElseThrow(() -> new BusinessException(format("Cannot find order:: No Order exists with the provided ID %s", orderId)));
    }
}
