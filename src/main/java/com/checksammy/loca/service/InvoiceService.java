package com.checksammy.loca.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import com.checksammy.loca.dto.BillingInvoiceDto;
import com.checksammy.loca.dto.InvoiceDto;
import com.checksammy.loca.dto.InvoiceSchedulerDateDto;
import com.checksammy.loca.dto.JobVisitReportFilterDto;
import com.checksammy.loca.dto.TransactionReportDto;
import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.InvoiceSchedularDate;

public interface InvoiceService {

	InvoiceDto saveInoviceData(InvoiceDto invoiceSave) throws UnsupportedEncodingException, IOException;

	List<InvoiceDto> getAll();

	InvoiceDto getById(Long invoiceId);

	void updatePaymentStatus(Long invoiceId, String status, Long statusId, Double double1, Instant paidDate, String string);

	void updatePaymentStatusOnly(Long invoiceId, String status, Long statusId);

	List<Invoice> findInvoiceBy(Long jobId);

	List<BillingInvoiceDto> filterInvoiceData(List<Invoice> invoiceRealtedToJob, Long timezone);

	List<BillingInvoiceDto> findInvoiceSchedule(Long jobId,Long timezone);

	List<Invoice> findAllInvoiceWithFilter(String visitWithin, String visitWithin2, String type);

	List<TransactionReportDto> filterAllDataMapp(List<Invoice> iteratorTransactionList, JobVisitReportFilterDto filterDto);

	void exportReportToEmail(List<TransactionReportDto> transactionReportDtos, JobVisitReportFilterDto filterDto,
			Integer timezone);

	Optional<Invoice> findInvoiceById(Long invoiceId);

}
