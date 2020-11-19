package com.checksammy.loca.controller;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.config.StripeClient;
import com.checksammy.loca.dto.PaymentLinkDto;
import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.InvoiceTransaction;
import com.checksammy.loca.model.PaymentLinkHashCode;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.UserCardMapping;
import com.checksammy.loca.service.EmailService;
import com.checksammy.loca.service.InvoiceService;
import com.checksammy.loca.service.InvoiceTransactionService;
import com.checksammy.loca.service.PaymentLinkHashCodeService;
import com.checksammy.loca.service.UserCardMappingService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.WorkStatusService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;
import com.stripe.model.Charge;
import com.stripe.model.Charge.PaymentMethodDetails;
import com.stripe.model.Charge.PaymentMethodDetails.Card;
import com.stripe.model.Customer;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/payment")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	private static final String ADMIN = "admin_portal";

	private static final String CLIENT = "client_portal";

	@Autowired
	private StripeClient stripeClient;

	@Autowired
	private UserService userService;

	@Autowired
	private InvoiceTransactionService invoiceTranService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private WorkStatusService workStatusService;

	@Autowired
	private UserCardMappingService userCardMappingService;

	@Autowired
	private PaymentLinkHashCodeService payCodeService;

	@Autowired
	private EmailService emailService;

//	@PostMapping("/charge")
//    public Charge chargeCard(HttpServletRequest request) throws Exception {
//        String token = request.getHeader("token");
//        Double amount = Double.parseDouble(request.getHeader("amount"));
//        return stripeClient.chargeCreditCard(token, amount);
//    }

	@SuppressWarnings("unused")
	@PostMapping("/charge")
	public ResponseEntity<ServiceResponse> chargeCard(@RequestBody InvoiceTransaction invoiceTransaction,
			HttpServletRequest request) throws Exception {
		ServiceResponse response = new ServiceResponse();
		String finalStatus = "";
		String responseStatus = "";
		try {
			String token = request.getHeader("token");
			String customerId = request.getHeader("customerId");
			Double amount = invoiceTransaction.getPaidAmount();
			if (customerId != null && !customerId.equals("")) {
				System.out.println(customerId);
				Charge charge = stripeClient.chargeCustomerCardWithCustomerId(customerId, amount);
				System.out.println(charge);
				String status = "";
				Long statusId = 0L;
				if (charge != null) {
					invoiceTransaction
							.setAvlBalance(invoiceTransaction.getInvoiceAmount() - invoiceTransaction.getPaidAmount());
//					invoiceTransaction.setTransToken(charge.getId());
					invoiceTransaction.setCustomerId(customerId);
					invoiceTransaction.setPayDate(Instant.now());
					invoiceTransaction.setCreatedTs(Instant.now());
					invoiceTransaction.setChargeId(charge.getId());
					invoiceTransaction.setPaymentFrom(invoiceTransaction.getPaymentFrom());
					invoiceTransaction = invoiceTranService.saveTransaction(invoiceTransaction);
//					userService.updateCardCustomerId(user.getUserId(), customer.getId());
					if (invoiceTransaction.getAvlBalance() > 0) {
						status = "Awaiting Payment";
						statusId = 2L;
					} else if (invoiceTransaction.getAvlBalance().equals(0.00)) {
						status = "Completed";
						statusId = 5L;
					} else {
						status = "Completed";
						statusId = 5L;
					}
					invoiceService.updatePaymentStatus(invoiceTransaction.getInvoiceId(), status, statusId,
							invoiceTransaction.getAvlBalance(), Instant.now(), invoiceTransaction.getPaymentFrom());
				}
				finalStatus = "Transaction completed successfully";
				responseStatus = ConstantUtil.RESPONSE_SUCCESS;
			} else {
				System.out.println("Token -> " + token);
				if (invoiceTransaction.getSaveCardTag()) {
					User user = userService.getUserId(invoiceTransaction.getUserId());
					Customer customer = stripeClient.createCustomer(token, user.getUsername());
					System.out.println(customer.getSources().getData().get(0));

					if (customer != null) {

						Charge charge = stripeClient.chargeCustomerCardWithCustomerId(customer.getId(), amount);
						System.out.println(charge);
//						
						String status = "";
						Long statusId = 0L;
						if (charge != null) {
							PaymentMethodDetails methodDetails = charge.getPaymentMethodDetails();
							Card dto = methodDetails.getCard();
							invoiceTransaction.setAvlBalance(
									invoiceTransaction.getInvoiceAmount() - invoiceTransaction.getPaidAmount());
							invoiceTransaction.setTransToken(customer.getDefaultSource());
							invoiceTransaction.setCustomerId(customer.getId());
							invoiceTransaction.setPayDate(Instant.now());
							invoiceTransaction.setCreatedTs(Instant.now());
							invoiceTransaction.setChargeId(charge.getId());
							invoiceTransaction.setPaymentFrom(invoiceTransaction.getPaymentFrom());
							invoiceTransaction = invoiceTranService.saveTransaction(invoiceTransaction);
//							userService.updateCardCustomerId(user.getUserId(), customer.getId());
							if (invoiceTransaction.getAvlBalance() > 0) {
								status = "Awaiting Payment";
								statusId = 2L;
							} else if (invoiceTransaction.getAvlBalance().equals(0.00)) {
								status = "completed";
								statusId = 5L;
							} else {
								status = "completed";
								statusId = 5L;
							}
							invoiceService.updatePaymentStatus(invoiceTransaction.getInvoiceId(), status, statusId,
									invoiceTransaction.getAvlBalance(), Instant.now(),
									invoiceTransaction.getPaymentFrom());
							UserCardMapping userCardMapping = userCardMappingService.createUserCard(user.getUserId(),
									customer.getId(), dto);
						}
						finalStatus = "Transaction completed successfully";
						responseStatus = ConstantUtil.RESPONSE_SUCCESS;
					} else {
						finalStatus = "Customer not created";
						responseStatus = ConstantUtil.RESPONSE_FAILURE;
					}

				} else {
					Charge charge = stripeClient.chargeCreditCard(token, amount);
					System.out.println(charge);
					if (charge != null) {
						String status = "";
						Long statusId = 0L;
						invoiceTransaction.setAvlBalance(
								invoiceTransaction.getInvoiceAmount() - invoiceTransaction.getPaidAmount());
						invoiceTransaction.setTransToken(token);
						invoiceTransaction.setCustomerId(null);
						invoiceTransaction.setPayDate(Instant.now());
						invoiceTransaction.setCreatedTs(Instant.now());
						invoiceTransaction.setChargeId(charge.getId());
						invoiceTransaction.setPaymentFrom(invoiceTransaction.getPaymentFrom());
						invoiceTransaction = invoiceTranService.saveTransaction(invoiceTransaction);

						if (invoiceTransaction.getAvlBalance() > 0) {
							status = "Awaiting Payment";
							statusId = 2L;
						} else if (invoiceTransaction.getAvlBalance().equals(0.00)) {
							status = "Completed";
							statusId = 5L;
						} else {
							status = "Completed";
							statusId = 2L;
						}
						invoiceService.updatePaymentStatus(invoiceTransaction.getInvoiceId(), status, statusId,
								invoiceTransaction.getAvlBalance(), Instant.now(), invoiceTransaction.getPaymentFrom());

						finalStatus = "Transaction completed successfully";
						responseStatus = ConstantUtil.RESPONSE_SUCCESS;
					} else {
						finalStatus = "Transaction not completed";
						responseStatus = ConstantUtil.RESPONSE_FAILURE;
					}
				}

				if (invoiceTransaction.getPaymentFrom().equalsIgnoreCase(CLIENT)) {
					payCodeService.deleteByInvoiceAndJobId(invoiceTransaction.getInvoiceId(), invoiceTransaction.getJobId());
				}
				response.setReturnObject(finalStatus);
				response.setStatus(responseStatus);
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			response.setErrorMessage(e.getLocalizedMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/send/link")
	public ResponseEntity<ServiceResponse> sendPaymentLink(@RequestBody PaymentLinkDto paymentLinkDto)
			throws Exception {
		ServiceResponse response = new ServiceResponse();
		try {
			Optional<Invoice> invoice = invoiceService.findInvoiceById(paymentLinkDto.getInvoiceId());
			if (invoice.isPresent()) {
				payCodeService.deleteByInvoiceAndJobId(paymentLinkDto.getInvoiceId(), paymentLinkDto.getJobId());
				User user = userService.findById(invoice.get().getUserId().getUserId());
				String hashCodeString = getRandomNumberString();

				PaymentLinkHashCode paymentLinkHashCode = new PaymentLinkHashCode();
				paymentLinkHashCode.setInvoiceId(invoice.get().getId());
				paymentLinkHashCode.setJobId(paymentLinkDto.getJobId());
				paymentLinkHashCode
						.setUserId(invoice.get().getUserId() != null ? invoice.get().getUserId().getUserId() : null);
				paymentLinkHashCode.setCreatedBy(invoice.get().getCreatedBy());
				paymentLinkHashCode.setHashCode(hashCodeString);
				paymentLinkHashCode.setIsDeleted(false);
				paymentLinkHashCode = payCodeService.saveData(paymentLinkHashCode);

				String emailContent = "<html>\r\n" + "<body>\r\n" + "<p>Hello " + user.getFirstName() + " "
						+ user.getLastName() + ",</p>\r\n" + "<p>here's your invoice from CheckSammy Inc. for $"
						+ invoice.get().getFinalTotal() + ".</p>\r\n"
						+ "<p>Pay your invoice here <a href=\"http://3.96.13.253/#/admin/payment-link/"
						+ hashCodeString + "\">http://3.96.13.253/#/admin/payment-link/"
						+ hashCodeString + "</a></p>\r\n" + "</body>\r\n" + "</html>";
				String subject = "Payment Link";
				emailService.sendEmailWithLinkOfPayment(emailContent, user.getUsername(), subject);
			}
			response.setReturnObject("completed");
			response.setStatus(invoice.isPresent() ? ConstantUtil.RESPONSE_SUCCESS : ConstantUtil.RESPONSE_FAILURE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Update payment Link data after charge
	 * 
	 * @param paymentLinkDto
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/delete/hashcode/{hashCode}")
	public ResponseEntity<ServiceResponse> updateChange(@PathVariable String hashCode) throws Exception {
		ServiceResponse response = new ServiceResponse();
		try {
			payCodeService.completedPaymentOnInvoice(hashCode);
			response.setReturnObject("deleted");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/find/data/{hashCode}")
	public ResponseEntity<ServiceResponse> findDataByHashCode(@PathVariable String hashCode) throws Exception {
		ServiceResponse response = new ServiceResponse();
		try {

			Invoice invoiceData = payCodeService.findInvoiceData(hashCode);
			response.setReturnObject(invoiceData);
			response.setStatus(invoiceData != null ? ConstantUtil.RESPONSE_SUCCESS : ConstantUtil.RESPONSE_FAILURE);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}
	
	public static String getRandomNumberString() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
}
