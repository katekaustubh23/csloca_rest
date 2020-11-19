package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.QuoteDto;
import com.checksammy.loca.dto.QuoteSendDto;
import com.checksammy.loca.dto.QuotesCustomInstanceDto;
import com.checksammy.loca.dto.QuotesInternalNotesDto;
import com.checksammy.loca.dto.ZapierPrimaryEmail;
import com.checksammy.loca.dto.ZapierQuoteDto;
import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.QuotesCustomInstance;
import com.checksammy.loca.model.QuotesInternalNotes;
import com.checksammy.loca.model.QuotesProductAndService;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.WorkStatus;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.repository.JobRequestRepository;
import com.checksammy.loca.repository.LocationRepository;
import com.checksammy.loca.repository.ProductServiceRepository;
import com.checksammy.loca.repository.QuoteRepository;
import com.checksammy.loca.repository.QuotesCustomInstanceRepository;
import com.checksammy.loca.repository.QuotesInternalNotesRepository;
import com.checksammy.loca.repository.QuotesProductAndServiceReposiotry;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.repository.WorkStatusRepository;
import com.checksammy.loca.service.QuoteService;
import com.google.gson.Gson;

@Service
public class QuoteServiceImpl implements QuoteService {

	private static final Logger logger = LoggerFactory.getLogger(QuoteServiceImpl.class);

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private QuotesCustomInstanceRepository quoteCuInstanceRepository;

	@Autowired
	FieldTypeInstanceRepository fieldTypeRepository;

	@Autowired
	private QuotesProductAndServiceReposiotry quoteProReposiotry;

	@Autowired
	private JobRequestRepository jobRequestRepository;

	@Autowired
	private WorkStatusRepository workStatusRepo;

	@Autowired
	private QuotesInternalNotesRepository quotesInternalNotesRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ProductServiceRepository productServiceRepository;
	
	@Autowired
	BinTypeRespository binTypeRespository;

	public static final String STATUS = "Draft";

	@Override
	public List<QuoteSendDto> getAll() {
		List<QuoteSendDto> newQuoteDto = new ArrayList<QuoteSendDto>();
		List<Quote> QuoteList = quoteRepository.findAll();
		for (Quote quote : QuoteList) {
			try {
				QuoteSendDto quoteDto = new QuoteSendDto();
				User user = userRepo.findByUserId(quote.getUserId());
//					TODO: InternalNotes
				List<QuotesInternalNotesDto> quotesInternalNotesDtos = new ArrayList<QuotesInternalNotesDto>();
//					List<QuotesInternalNotes> quotesInternalNotes = quotesInternalNotesRepo.findByQuoteId(quoteId);
				List<QuotesInternalNotes> quotesInternalNotes = new ArrayList<QuotesInternalNotes>();
				List<QuotesCustomInstanceDto> quotesCustomInstanceDtos = new ArrayList<QuotesCustomInstanceDto>();
				if (quote.getQuotesInternalNotes() != null && quote.getQuotesInternalNotes().size() > 0) {
					quotesInternalNotes = new ArrayList<QuotesInternalNotes>(quote.getQuotesInternalNotes());

					for (QuotesInternalNotes quotesInternalNotes2 : quotesInternalNotes) {
						QuotesInternalNotesDto quotesInternalNotesDto = new QuotesInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(quotesInternalNotes2.getCreatedBy());
							quotesInternalNotesDto = quotesInternalNotesDto.addedRealtedUserwithData(realtedUser,
									quotesInternalNotes2);
							quotesInternalNotesDtos.add(quotesInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}
					}
				}
				if (quote.getQuotesCustomField() != null && quote.getQuotesCustomField().size() > 0) {
					for (QuotesCustomInstance quotesCustomInstance : quote.getQuotesCustomField()) {
						QuotesCustomInstanceDto quotesCustomInstanceDto = new QuotesCustomInstanceDto();
						Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
								.findById(quotesCustomInstance.getFieldInstanceId());

						quotesCustomInstanceDto = quotesCustomInstanceDto.addCustomField(fieldTypeInstance.get(),
								quotesCustomInstance);
						quotesCustomInstanceDtos.add(quotesCustomInstanceDto);
					}
				}

//					TODO: Quotes Mapp
				quoteDto = quoteDto.getQuotesData(quote, quotesInternalNotesDtos, user, quotesCustomInstanceDtos);
				newQuoteDto.add(quoteDto);

			} catch (Exception e) {
				logger.debug(e.getLocalizedMessage());
			}
		}

		return newQuoteDto;
	}

	@Override
	public Quote saveData(QuoteDto quoteDto) {
		Quote newQuote = quoteDto.setQuotes(quoteDto);
		newQuote = quoteRepository.save(newQuote);
		quoteRepository.updateLocationId(newQuote.getId(), quoteDto.getLocationId());
		List<QuotesCustomInstance> quoCustomInstances = new ArrayList<QuotesCustomInstance>();
		List<QuotesProductAndService> quoProductAndServices = new ArrayList<QuotesProductAndService>();
		List<QuotesInternalNotes> quotesInternalNotes = new ArrayList<QuotesInternalNotes>();
//		if (quoteDto.getJobRequestId() != null && quoteDto.getJobRequestId() > 0) {
//			jobRequestRepository.updateStatus(quoteDto.getJobRequestId(), STATUS);
//		}
		if (quoteDto.getQuotesCustomField() != null && quoteDto.getQuotesCustomField().size() > 0) {
			for (QuotesCustomInstance quotesCustomField : newQuote.getQuotesCustomField()) {
				quotesCustomField.setQuoteId(newQuote.getId());
				quoCustomInstances.add(quotesCustomField);
			}
			quoCustomInstances = quoteCuInstanceRepository.saveAll(quoCustomInstances);
		}

		if (quoteDto.getQuotesProductService() != null && quoteDto.getQuotesProductService().size() > 0) {
			for (QuotesProductAndService quoteProductAndService : newQuote.getQuotesProductService()) {
				quoteProductAndService.setQuotesId(newQuote.getId());
				quoProductAndServices.add(quoteProductAndService);
			}
			quoProductAndServices = quoteProReposiotry.saveAll(quoProductAndServices);
		}

		if (quoteDto.getQuotesInternalNotes() != null && quoteDto.getQuotesInternalNotes().size() > 0) {
			for (QuotesInternalNotes quotesInternalNote : newQuote.getQuotesInternalNotes()) {
				quotesInternalNote.setQuoteId(newQuote.getId());
				quotesInternalNotes.add(quotesInternalNote);
			}
			quotesInternalNotes = quotesInternalNotesRepo.saveAll(quotesInternalNotes);
		}
		return newQuote;
	}

	@Override
	public QuoteSendDto getById(Long quotesId) {
		QuoteSendDto quoteDto = new QuoteSendDto();
		Optional<Quote> quote = quoteRepository.findById(quotesId);
		Quote quoteGet = new Quote();
		try {
			if (quote.isPresent()) {
				User user = userRepo.findByUserId(quote.get().getUserId());
//				TODO: InternalNotes
				List<QuotesInternalNotesDto> quotesInternalNotesDtos = new ArrayList<QuotesInternalNotesDto>();
//				List<QuotesInternalNotes> quotesInternalNotes = quotesInternalNotesRepo.findByQuoteId(quoteId);
				List<QuotesInternalNotes> quotesInternalNotes = new ArrayList<QuotesInternalNotes>();
				List<QuotesCustomInstanceDto> quotesCustomInstanceDtos = new ArrayList<QuotesCustomInstanceDto>();
				if (quote.get().getQuotesInternalNotes() != null && quote.get().getQuotesInternalNotes().size() > 0) {
					quotesInternalNotes = new ArrayList<QuotesInternalNotes>(quote.get().getQuotesInternalNotes());

					for (QuotesInternalNotes quotesInternalNotes2 : quotesInternalNotes) {
						QuotesInternalNotesDto quotesInternalNotesDto = new QuotesInternalNotesDto();
						try {
							User realtedUser = userRepo.findByUserId(quotesInternalNotes2.getCreatedBy());
							quotesInternalNotesDto = quotesInternalNotesDto.addedRealtedUserwithData(realtedUser,
									quotesInternalNotes2);
							quotesInternalNotesDtos.add(quotesInternalNotesDto);
						} catch (Exception e) {
							logger.debug(e.getCause().getLocalizedMessage());
						}

					}

					for (QuotesCustomInstance quotesCustomInstance : quote.get().getQuotesCustomField()) {
						QuotesCustomInstanceDto quotesCustomInstanceDto = new QuotesCustomInstanceDto();
						Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
								.findById(quotesCustomInstance.getFieldInstanceId());

						quotesCustomInstanceDto = quotesCustomInstanceDto.addCustomField(fieldTypeInstance.get(),
								quotesCustomInstance);
						quotesCustomInstanceDtos.add(quotesCustomInstanceDto);
					}
				}
//				TODO: Quotes Mapp
				
				quoteDto = quoteDto.getQuotesData(quote.get(), quotesInternalNotesDtos, user, quotesCustomInstanceDtos);
				
				
				if(quote.get().getServiceAsBintypeId() != null) {
					Optional<BinType> bin = binTypeRespository.findById(quote.get().getServiceAsBintypeId());
					quoteDto.setServiceAsBintype(bin.isPresent()? bin.get().getName(): null);
				}
			}

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}

		return quoteDto;
	}

	@Override
	public Boolean deleteById(Long quotesId) {
		Boolean status = false;
		try {
			quoteRepository.deleteToUpdate(quotesId);
			status = true;
		} catch (Exception e) {
			logger.debug(e.getCause().getMessage());
			status = false;
		}
		return status;
	}

	@Override
	public String changeStatus(Long quotesId, Long statusId) {
		Optional<WorkStatus> status = workStatusRepo.findById(statusId);
		quoteRepository.updateStatus(quotesId, status.get().getStatusName(), status.get().getId());
		return status.get().getStatusName();
	}

	@Override
	public Quote saveDataZapier(List<HashMap<String, String>> zapierQuote) {
		logger.info("data -> ", zapierQuote.get(0));
		Optional<User> PresentUser = null;
		try {
//			PresentUser = userRepo.findUserData(zapierQuote.get(0).getClient().getFirst_name(),
//					zapierQuote.get(0).getClient().getPrimary_email().getAddress());
//			if (PresentUser.isEmpty()) {
//				ZapierPrimaryEmail zapierPrimaryEmail = new ZapierPrimaryEmail();
//				zapierPrimaryEmail = (ZapierPrimaryEmail) zapierQuote.get(0).getClient().getPrimary_email();
//				Gson g = new Gson();
//				String convertedToString = String.valueOf(zapierQuote.get(0).getClient().getPrimary_email());
//				ZapierPrimaryEmail p = g.fromJson(convertedToString, ZapierPrimaryEmail.class);
//				logger.info("List ->" + p);
//				zapierPrimaryEmail = g.fromJson(convertedToString, ZapierPrimaryEmail.class);
//				logger.info(zapierQuote.getClient().getPrimary_email());
//				logger.info("List ->" + zapierPrimaryEmail);
//				PresentUser = userRepo.findUserData(zapierQuote.get(0).getClient().getFirst_name(),
//						zapierPrimaryEmail.getAddress());
//			}
		} catch (Exception e) {
//			logger.error("Field is Object ->" + e);
//			ZapierPrimaryEmail zapierPrimaryEmail = new ZapierPrimaryEmail();
//			Gson g = new Gson();
//			String convertedToString = String.valueOf(zapierQuote.get(0).getClient().getPrimary_email());
//			ZapierPrimaryEmail p = g.fromJson(convertedToString, ZapierPrimaryEmail.class);
//			logger.info("List ->" + p);
//			zapierPrimaryEmail = g.fromJson(convertedToString, ZapierPrimaryEmail.class);
//			zapierPrimaryEmail = objMapper.convertValue(zapierQuote.getClient().getPrimary_email(), ZapierPrimaryEmail.class);
//			logger.info("List ->" + zapierPrimaryEmail);
//			PresentUser = userRepo.findUserData(zapierQuote.get(0).getClient().getFirst_name(),
//					zapierPrimaryEmail.getAddress());
		}
		Quote quoteNew = new Quote();
//		quoteNew.setQuoteNumber(zapierQuote.get(0).getQuote_number().toString());
//		quoteNew.setStatus(zapierQuote.get(0).getState());
//		quoteNew.setJobQuotesTitle(zapierQuote.get(0).getJob_description());
//		if (PresentUser.isPresent()) {
//			List<Location> newLocation = locationRepository
//					.findByCompanyNameOfUser(zapierQuote.get(0).getClient().getCompany_name());
//			quoteNew = zapierQuote.get(0).addedQuote(zapierQuote.get(0), newLocation);
//			Optional<Location> locationOptional = newLocation.stream().filter(
//					filterData -> (filterData.getStreetName().equalsIgnoreCase(zapierQuote.get(0).getProperty().getStreet1())
//							&& filterData.getCity().equalsIgnoreCase(zapierQuote.get(0).getProperty().getCity())))
//					.findAny();
//			quoteNew.setUserId(PresentUser.get().getUserId());
//			quoteNew = quoteRepository.save(quoteNew);
//
//			if (locationOptional.isPresent()) {
//				quoteRepository.updateLocationId(quoteNew.getId(), locationOptional.get().getId());
//				quoteNew.setLocationId(locationOptional.get());
//			}
//			List<ProductService> productService = productServiceRepository.findAll();

//			List<QuotesProductAndService> quotesProductAndService = zapierQuote.addedQuoteProduct(zapierQuote, productService, quoteNew);
//		
//			quotesProductAndService = quoteProReposiotry.saveAll(quotesProductAndService);
//		}
		
		for (HashMap<String, String> hashMap : zapierQuote) {
			System.out.println(hashMap.keySet());
			switch (hashMap.keySet().toString()) {
			case "[id]":
				quoteNew.setId(Long.parseLong(hashMap.get("id")));
				break;
			case "[quote_number]":
				quoteNew.setQuoteNumber(hashMap.get("quote_number"));
				break;
			case "[state]":
				quoteNew.setStatus(hashMap.get("state"));
				break;
			case "[job_description]":
				quoteNew.setJobQuotesTitle(hashMap.get("job_description"));
				break;

			default:
				break;
			}
		}
//		System.out.println(quoteNew);

		return quoteNew;
	}

	@Override
	public Quote saveDataZapierTestLimitedData(ZapierQuoteDto zapierQuote) {
		Quote quoteNew = zapierQuote.addedQuoteTestPorpose(zapierQuote);
		quoteNew = quoteRepository.save(quoteNew);
		return quoteNew;
	}
}
