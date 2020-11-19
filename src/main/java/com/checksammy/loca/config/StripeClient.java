package com.checksammy.loca.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

@Component
public class StripeClient {
	
	private static final Logger logger = LoggerFactory.getLogger(StripeClient.class);

	//TODO: remove below hard code 	
	@Autowired
	StripeClient() {
		Stripe.apiKey = "sk_test_iyJbfTvg5HFC4UYLF4VR3pU600Uys6edjf"; //sk_test_EZIFHKNczwsfBs5WxzTWLJF3
	}

	public Charge chargeCreditCard(String token, Double amount)throws Exception {
		Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        System.out.println(charge);
        return charge;
	}
	
	public Customer createCustomer(String token, String email) throws Exception {
	    Map<String, Object> customerParams = new HashMap<String, Object>();
	    customerParams.put("email", email);
	    customerParams.put("source", token);
	    Customer customer = Customer.create(customerParams);
	    System.out.println(customer);
	    return customer;
	}
	
//	charged amount already present customer By customerId
	public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
	    String sourceCard =     Customer.retrieve(customerId).getDefaultSource();
	    Map<String, Object> chargeParams = new HashMap<String, Object>();
	    chargeParams.put("amount", amount);
	    chargeParams.put("currency", "USD");
	    chargeParams.put("customer", customerId);
	    chargeParams.put("source", sourceCard);
	    Charge charge = Charge.create(chargeParams);
	    return charge;
	}
	
	public Charge chargeCustomerCardWithCustomerId(String customerId, Double amount) throws Exception {
	    String sourceCard =     Customer.retrieve(customerId).getDefaultSource();
	    Map<String, Object> chargeParams = new HashMap<String, Object>();
	    chargeParams.put("amount", (int)(amount * 100));
	    chargeParams.put("currency", "USD");
	    chargeParams.put("customer", customerId);
	    chargeParams.put("source", sourceCard);
	    Charge charge = Charge.create(chargeParams);
	    return charge;
	}

}
