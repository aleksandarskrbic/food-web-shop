package com.aki.web.foodorder.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.aki.web.foodorder.domain.Item;
import com.aki.web.foodorder.domain.Order;
import com.aki.web.foodorder.domain.enums.Status;
import com.aki.web.foodorder.domain.enums.Type;
import com.aki.web.foodorder.repository.OrderRepository;

public class OrderService {
	
	private final OrderRepository orderRepository;
	
	public OrderService() {
		orderRepository = new OrderRepository();
	}
	
	public List<Order> getAllOrders() {
		List<Order> orders = orderRepository.getAllOrders();

		if (orders != null)
			return orders;

		return null;
	}
	
	public Order getOrderById(String id) {
		return orderRepository.getAllOrders()
							  .stream()
							  .filter(o -> o.getId().equals(id))
							  .findFirst()
							  .orElse(null);
	}
	
	public void addOrder(Order order) {
		List<Order> orders = orderRepository.getAllOrders();
		if (order == null) {
			orders = new ArrayList<>();
			orders.add(order);
			orderRepository.saveOrders(orders);
		} else {
			orders.add(order);
			orderRepository.saveOrders(orders);
		}
	}
	
	public void addDeliverer(String orderId, String delivererId) {
		List<Order> orders = orderRepository.getAllOrders();
		
		for (Order o : orders) {
			if (o.getId().equals(orderId)) {
				o.setDeliveryPersonId(delivererId);
				o.setStatus(Status.DELIVERY_IN_PROGRESS);
				orderRepository.saveOrders(orders);
				return;
			}
		}
	}

	public List<Order> getUserOrders(String username) {
		if (orderRepository.getAllOrders() == null) {
			return null;
		}
		
		return orderRepository.getAllOrders().stream()
											 .filter(order -> order.getUserId().equals(username))
											 .collect(Collectors.toList());
											 
	}

	public List<Order> getPendingOrders() {
		if (orderRepository.getAllOrders() == null) {
			return null;
		}
		
		return orderRepository.getAllOrders().stream()
											 .filter(order -> order.getDeliveryPersonId() == null)
											 .collect(Collectors.toList());
	}

	public Order getCurrentOrder(String username) {
		List<Order> orders = orderRepository.getAllOrders().stream()
										    .filter(order -> order.getDeliveryPersonId() != null )
										    .collect(Collectors.toList());
		
		for (Order o : orders) {
			if (o.getDeliveryPersonId().equals(username) && o.getStatus() == Status.DELIVERY_IN_PROGRESS) {
				return o;
			}
		}
		
		return null;
	}

	public void finishOrder(String id) {
		List<Order> orders = orderRepository.getAllOrders();
		for (Order o : orders) {
			if (o.getId().equals(id)) {
				o.setStatus(Status.DELIVERED);
				o.getStatus();
				orderRepository.saveOrders(orders);
				return;
			}
		}
	}

	public List<Order> getPrevOrders(String username,  String role) {
		if (orderRepository.getAllOrders() == null) {
			return null;
		}
		
		if (role.equals("deliverer")) {
			List<Order> orders = orderRepository.getAllOrders().stream()
															   .filter(order -> order.getDeliveryPersonId() != null)
															   .collect(Collectors.toList());
			
			return orders.stream()
						 .filter(order -> order.getDeliveryPersonId().equals(username) && order.getStatus() == Status.DELIVERED)
						 .collect(Collectors.toList());
		}
		
		List<Order> orders = orderRepository.getAllOrders().stream()
														   .filter(order -> order.getUserId() != null)
														   .collect(Collectors.toList());
		
		return orders.stream()
					 .filter(order -> order.getUserId().equals(username) && order.getStatus() == Status.DELIVERED)
					 .collect(Collectors.toList());
									 
	}

	public List<String> getBusyDeliverers() {
		List<Order> orders = orderRepository.getAllOrders();
		List<String> ret = new ArrayList<>();
		
		for (Order o : orders) {
			if (o.getStatus() == Status.DELIVERY_IN_PROGRESS) {
				ret.add(o.getDeliveryPersonId());
			}
		}
		
		return ret;
	}
	
	public List<String> getPopularDrinks() {
		List<Order> orders = orderRepository.getAllOrders();
		List<String> articles = new ArrayList<>();
		
		for (Order o : orders) {
			List<Item> items = o.getItems();
			for (Item i : items) {
				if (i.getArticle().getType() == Type.BEVERAGE) {
					articles.add(i.getArticle().getName());
				}
			}
		}
        
        Map<String, Long> counts = articles.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		ValueComparator comp = new ValueComparator(counts);
        
        Map<String, Long> sorted =  new TreeMap<>(comp);
        sorted.putAll(counts);
        
        List<String> pop = new ArrayList<>(sorted.keySet());
		
		if (pop.size() > 10) {
			return pop.stream()
					  .limit(10)
					  .collect(Collectors.toList());
		}
		
		return pop;
	}

	public List<String> getPopularFood() {
		List<Order> orders = orderRepository.getAllOrders();
		List<String> articles = new ArrayList<>();
		
		for (Order o : orders) {
			List<Item> items = o.getItems();
			for (Item i : items) {
				if (i.getArticle().getType() == Type.FOOD) {
					articles.add(i.getArticle().getName());
				}
			}
		}
        
        Map<String, Long> counts = articles.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		ValueComparator comp = new ValueComparator(counts);
        
        Map<String, Long> sorted = new TreeMap<>(comp);
        sorted.putAll(counts);
        
        List<String> pop = new ArrayList<>(sorted.keySet());
		
		if (pop.size() > 10) {
			return pop.stream()
					  .limit(10)
					  .collect(Collectors.toList());
		}
		
		return pop;
		
	}
}

class ValueComparator implements Comparator<String> {
	
    private Map<String, Long> base;

    public ValueComparator(Map<String, Long> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}

