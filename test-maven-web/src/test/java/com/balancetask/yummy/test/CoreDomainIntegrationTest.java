package com.balancetask.yummy.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.balancetask.yummy.CoreConfig;
import com.balancetask.yummy.OrderDetails;
import com.balancetask.yummy.OrderService;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class})
public class CoreDomainIntegrationTest {

  @Autowired
  OrderService orderService;

  @Test
  public void addANewOrderToTheSystem() {

    orderService.createOrder(new OrderDetails());

    List<OrderDetails> allOrders = orderService.requestAllOrders();

    assertEquals(1, allOrders.size());
  }
}
