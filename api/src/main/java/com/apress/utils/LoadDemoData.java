package com.apress.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.apress.domain.Booking;
import com.apress.domain.Part;
import com.apress.domain.Product;
import com.apress.domain.User;
import com.apress.repository.BookingRepository;
import com.apress.repository.PartRepository;
import com.apress.repository.ProductRepository;
import com.apress.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoadDemoData implements ApplicationRunner {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PartRepository partRepository;

	@SuppressWarnings("unused")
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Loading database");

		User user1 = userRepository
				.save(User.builder().fullName("Administrator").mobile("123456789").email("admin@garage.com").build());
		User user2 = userRepository
				.save(User.builder().fullName("Mechanic-1").mobile("123456789").email("mechanic-1@garage.com")
				.role("mechanic").build());
		User user3 = userRepository
				.save(User.builder().fullName("Mechanic-2").mobile("123456789").email("mechanic-2@garage.com")
				.role("mechanic").build());
		User user4 = userRepository
				.save(User.builder().fullName("Customer-3").mobile("123456789").email("mechanic-3@garage.com")
				.role("Customer").build());
		User user5 = userRepository
				.save(User.builder().fullName("Customer-1").mobile("123456789").email("Customer-1@garage.com")
				.role("customer").build());
		User user6 = userRepository
				.save(User.builder().fullName("Customer-2").mobile("123456789").email("Customer-2@garage.com")
				.role("customer").build());
		User user7 = userRepository
				.save(User.builder().fullName("Customer-3").mobile("123456789").email("Customer-3@garage.com")
				.role("customer").build());

		Product product1 = productRepository
				.save(Product.builder().reference(UUID.randomUUID().toString()).name("Annual Service").category("base")
						.price(50).build());
		Product product2 = productRepository
				.save(Product.builder().reference(UUID.randomUUID().toString()).name("Major Service").category("base")
						.price(60).build());
		Product product3 = productRepository
				.save(Product.builder().reference(UUID.randomUUID().toString()).name("Repair or Fault").category("base")
						.price(70).build());
		Product product4 = productRepository
				.save(Product.builder().reference(UUID.randomUUID().toString()).name("Major Repair").category("base")
						.price(80).build());
		Product product5 = productRepository
				.save(Product.builder().reference(UUID.randomUUID().toString()).name("Wheel alignment")
						.category("extra").price(90).build());
		Product product6 = productRepository
				.save(Product.builder().reference(UUID.randomUUID().toString()).name("Grease and lubricat")
						.category("extra").price(70).build());
		Product product7 = productRepository
				.save(Product.builder().reference(UUID.randomUUID().toString()).name("Suspension").category("extra")
						.price(70).build());

		Part part1 = partRepository
				.save(Part.builder().sku(UUID.randomUUID().toString()).name("Engine motor oil").price(50).build());
		Part part2 = partRepository
				.save(Part.builder().sku(UUID.randomUUID().toString()).name("Filter oil").price(60).build());
		Part part3 = partRepository
				.save(Part.builder().sku(UUID.randomUUID().toString()).name("Filer ai").price(70).build());

		Booking booking1 = bookingRepository.save(
				Booking.builder()
				.reference(UUID.randomUUID().toString())
				.comments("comentario 1 comentario 1")
				.status("booked")
				.vehiculeBrand("Ford")
				.vehiculeEngine("diesel")
				.vehiculeModel("M5")
				.vehiculeNumberPlate("AAA-111")
				.part(part1)
				.mechanic(user2)
				.customer(user4)
				.baseProduct(product1)
				.extraProduct(product2)
				.extraProduct(product3).build());
		
	}
}