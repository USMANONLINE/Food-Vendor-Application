package com.assesment.vgginternship;

import com.assesment.vgginternship.orderstatus.OrderStatusEntity;
import com.assesment.vgginternship.orderstatus.OrderStatusRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeDB implements CommandLineRunner {
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    
    @Override
    public void run(String... strings) throws Exception {
        List<OrderStatusEntity> orderStatus = Arrays.asList(
                new OrderStatusEntity("ACTIVE"),
                new OrderStatusEntity("WAITING"),
                new OrderStatusEntity("COMPLETE"),
                new OrderStatusEntity("CANCEL")
        );
        Optional<OrderStatusEntity> order = orderStatusRepository.findByName("ACTIVE");
        if (!order.isPresent()) {
            orderStatusRepository.saveAll(orderStatus);
        }
    }
}
