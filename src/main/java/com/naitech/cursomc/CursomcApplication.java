package com.naitech.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.naitech.cursomc.domain.Address;
import com.naitech.cursomc.domain.BankSlipPayment;
import com.naitech.cursomc.domain.CardPayment;
import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.domain.City;
import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.domain.ClientOrder;
import com.naitech.cursomc.domain.Payment;
import com.naitech.cursomc.domain.Product;
import com.naitech.cursomc.domain.State;
import com.naitech.cursomc.domain.enums.ClientType;
import com.naitech.cursomc.domain.enums.PaymentStatus;
import com.naitech.cursomc.repositories.AddressRepository;
import com.naitech.cursomc.repositories.CategoryRepository;
import com.naitech.cursomc.repositories.CityRepository;
import com.naitech.cursomc.repositories.ClientRepository;
import com.naitech.cursomc.repositories.OrderRepository;
import com.naitech.cursomc.repositories.PaymentRepository;
import com.naitech.cursomc.repositories.ProductRepository;
import com.naitech.cursomc.repositories.StateRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category category1 = new Category(null, "Informatica");
		Category category2 = new Category(null, "Escritorio");

		Product product1 = new Product(null, "Computador", 2000.00);
		Product product2 = new Product(null, "Impressora", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);

		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product3));

		product1.getCategories().addAll(Arrays.asList(category1));
		product2.getCategories().addAll(Arrays.asList(category1, category2));
		product3.getCategories().addAll(Arrays.asList(category1));

		categoryRepository.saveAll(Arrays.asList(category1, category2));
		productRepository.saveAll(Arrays.asList(product1, product2, product3));

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "Sao Paulo");

		City city1 = new City(null, "Uberlandia", state1);
		City city2 = new City(null, "Sao Paulo", state2);
		City city3 = new City(null, "Campinas", state2);

		state1.getCities().addAll(Arrays.asList(city1));
		state2.getCities().addAll(Arrays.asList(city2, city3));

		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));

		Client client1 = new Client(null, "Maria Silva", "maria@gmail.com", "00000000000", ClientType.PESSOAFISICA);

		client1.getPhones().addAll(Arrays.asList("000000000", "111111111"));

		Address address1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", client1, city1);
		Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38220777", client1, city2);

		client1.getAddresses().addAll(Arrays.asList(address1, address2));

		clientRepository.saveAll(Arrays.asList(client1));
		addressRepository.saveAll(Arrays.asList(address1, address2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		ClientOrder order1 = new ClientOrder(null, sdf.parse("30/09/2024 10:32"), client1, address1);
		ClientOrder order2 = new ClientOrder(null, sdf.parse("30/12/2024 10:32"), client1, address2);

		Payment payment1 = new CardPayment(null, PaymentStatus.QUITADO, order1, 6);
		order1.setPayment(payment1);

		Payment payment2 = new BankSlipPayment(null, PaymentStatus.PENDENTE, order2, sdf.parse("30/12/2024 10:32"),
				null);
		order2.setPayment(payment2);

		client1.getOrders().addAll(Arrays.asList(order1, order2));
		
		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(payment1, payment2));
	}

}