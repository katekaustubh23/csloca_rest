package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.model.JobExpenses;

public interface JobExpensesService {

	List<JobExpenses> getAllListByJobId(Long jobId);

	JobExpenses saveJobExpenses(JobExpenses saveJobExpenses);

	Optional<JobExpenses> getExpensesById(Long expenseId);

	String saveRequestAttachments(Long internalNotesId, MultipartFile[] files);

}
