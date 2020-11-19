/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.CompanyCustomFieldDto;
import com.checksammy.loca.dto.HaulerPropertyExportDto;
import com.checksammy.loca.dto.PropertyDetailsDto;
import com.checksammy.loca.model.CompanyCustomField;
import com.checksammy.loca.model.FieldTypeInstance;
import com.checksammy.loca.model.PropertyDetailNote;
import com.checksammy.loca.model.PropertyDetails;
import com.checksammy.loca.model.User;
import com.checksammy.loca.repository.CompanyCustomFieldRepository;
import com.checksammy.loca.repository.FieldTypeInstanceRepository;
import com.checksammy.loca.repository.LocationPropertyDetailRepository;
import com.checksammy.loca.repository.PropertyDetailNoteRepository;
import com.checksammy.loca.repository.PropertyDetailRepository;
import com.checksammy.loca.repository.UserRepository;
import com.checksammy.loca.service.PropertyDetailsService;
import com.checksammy.loca.utility.ConvertFile;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class PropertyDetailsServiceImpl implements PropertyDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(PropertyDetailsServiceImpl.class);

	@Autowired
	PropertyDetailRepository repo;

	@Autowired
	CompanyCustomFieldRepository companCustomFieldRepo;

	@Autowired
	LocationPropertyDetailRepository locPropDetailRepo;

	@Autowired
	FieldTypeInstanceRepository fieldTypeRepository;

	@Autowired
	PropertyDetailNoteRepository propertyNotesRepo;

	@Autowired
	UserRepository userRepo;

	@Override
	public Page<PropertyDetails> getAll(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		return repo.findAll(pageable);
	}

	@Override
	public PropertyDetails save(PropertyDetails propertyDetails) {
		if (propertyDetails.getId() != null && propertyDetails.getId() > 0) {
//			propertyDetails.setUpdatedBy(propertyDetails.getCreatedBy());
			propertyDetails.setUpdatedTs(Instant.now());
		}
		propertyDetails = repo.save(propertyDetails);

		List<Long> companyCustomFieldId = new ArrayList<Long>();
		if (propertyDetails.getCompanyCustomField() != null && propertyDetails.getCompanyCustomField().size() > 0) {
			for (CompanyCustomField companyCustomField : propertyDetails.getCompanyCustomField()) {
				companyCustomField.setCompanyId(propertyDetails.getId());
				companyCustomFieldId.add(companyCustomField.getId());
			}
			companCustomFieldRepo.updateRequestCustomField(propertyDetails.getId(), companyCustomFieldId);
		}
		return propertyDetails;
	}

	@Override
	public void deleteById(Long id) {
		locPropDetailRepo.deleteByPropMgmtCompId(id);
		repo.deleteById(id);

	}

	@Override
	public List<PropertyDetails> getAllList() {
		return repo.findAll();
	}

	@Override
	public PropertyDetailsDto getSingleCompanyDetails(Long companyId) {
		PropertyDetailsDto propertyDetailsDto = new PropertyDetailsDto();
		Optional<PropertyDetails> proOptional = repo.findById(companyId);
		if (proOptional.isPresent()) {

			List<CompanyCustomFieldDto> companyCustomFieldDtos = new ArrayList<CompanyCustomFieldDto>();
			for (CompanyCustomField companyCustomField : proOptional.get().getCompanyCustomField()) {
				CompanyCustomFieldDto companyCustomFieldDto = new CompanyCustomFieldDto();
				Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
						.findById(companyCustomField.getFieldInstanceId());

				companyCustomFieldDto = companyCustomFieldDto.newSetCustomWithStringList(fieldTypeInstance.get(),
						companyCustomField);
				companyCustomFieldDtos.add(companyCustomFieldDto);
			}
			propertyDetailsDto = propertyDetailsDto.addNewData(proOptional.get(), companyCustomFieldDtos);
		}
		return propertyDetailsDto;
	}

	@Override
	public List<PropertyDetailsDto> getAllList2() {
		List<PropertyDetailsDto> propertyDetailsDtos = new ArrayList<PropertyDetailsDto>();
		List<PropertyDetails> propertyDetailsList = repo.findAll(Sort.by(Sort.Direction.ASC, "name"));
		for (PropertyDetails propertyDetails2 : propertyDetailsList) {
			PropertyDetailsDto propertyDetailsDto = new PropertyDetailsDto();
			List<CompanyCustomFieldDto> companyCustomFieldDtos = new ArrayList<CompanyCustomFieldDto>();
			for (CompanyCustomField companyCustomField : propertyDetails2.getCompanyCustomField()) {
				CompanyCustomFieldDto companyCustomFieldDto = new CompanyCustomFieldDto();
				Optional<FieldTypeInstance> fieldTypeInstance = fieldTypeRepository
						.findById(companyCustomField.getFieldInstanceId());

				companyCustomFieldDto = companyCustomFieldDto.newSetCustomWithStringList(fieldTypeInstance.get(),
						companyCustomField);
				companyCustomFieldDtos.add(companyCustomFieldDto);
			}
			propertyDetailsDto = propertyDetailsDto.addNewData(propertyDetails2, companyCustomFieldDtos);
			propertyDetailsDtos.add(propertyDetailsDto);
		}
		return propertyDetailsDtos;
	}

	@Override
	public String importXlXSheetList(Long userId, MultipartFile file) throws IOException {
		String status = "";
		User user = userRepo.findByUserId(userId);
		List<PropertyDetails> propertyDetailsList = new ArrayList<PropertyDetails>();
		try {
			String[] fileType = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
			String splitName = fileType[1];
			System.out.println(splitName);
			Map<String, String> mapping = new HashMap<String, String>();

			mapping.put("Hauler Company", "haulerCompany");
			mapping.put("Contract Signed Date", "conSingedDate");
			mapping.put("Address", "address");
			mapping.put("City", "city");
			mapping.put("State", "state");
			mapping.put("Postal /Zip", "zip");
			mapping.put("Contact Name", "priContName");
			mapping.put("Contact Title", "priContTitle");
			mapping.put("Phone #", "priPhone");
			mapping.put("E-Mail ", "priEmail");
			mapping.put("Secondary Contact Name", "secContName");
			mapping.put("Secondary Contact Title", "secContTitle");

			mapping.put("Secondary Phone #", "secPhone");
			mapping.put("Secondary E-Mail", "secContEmail");
			mapping.put("Company Website", "webSite");
			mapping.put("Number of Trucks", "noOfTruck");
			mapping.put("Truck Type (size)", "truckSize");
			mapping.put("Operating City 1", "opCity1");
			mapping.put("Operating City 2", "opCity2");
			mapping.put("Operating City 3", "opCity3");
			mapping.put("Operating City 4", "opCity4");
			mapping.put("Operating City 5", "opCity5");
			mapping.put("Operating City 6", "opCity6");
			mapping.put("Operating City 7", "opCity7");
			mapping.put("Additional Notes", "addiNote");
			File file2 = ConvertFile.convertMultiPartToFile(file);
			if (!file.isEmpty() && splitName.equals("csv")) {
				HeaderColumnNameTranslateMappingStrategy<HaulerPropertyExportDto> strategy = new HeaderColumnNameTranslateMappingStrategy<HaulerPropertyExportDto>();
				strategy.setType(HaulerPropertyExportDto.class);
				strategy.setColumnMapping(mapping);
				CSVReader csvReader = null;
				try {
					csvReader = new CSVReader(new FileReader(file2));
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage(), e.fillInStackTrace());
					status = "File not in proper format";
					return status;
				}
				CsvToBean csvToBean = new CsvToBean();
				List<HaulerPropertyExportDto> list = csvToBean.parse(strategy, csvReader);
				if (list.size() <= 0) {
					status = "File not in proper format";
					return status;
				}
				for (HaulerPropertyExportDto haulerPropertyExportDto : list) {
					PropertyDetails propertyDetails = new PropertyDetails();
					PropertyDetailNote propertyDetailNote = new PropertyDetailNote();
					try {
						propertyDetails = repo.findByName(haulerPropertyExportDto.getHaulerCompany());

						if (propertyDetails == null) {
							propertyDetails = new PropertyDetails();
						} else {
							if (propertyDetails.getPropertyNotes().size() > 0) {
								propertyDetailNote = propertyDetails.getPropertyNotes().stream()
										.skip(propertyDetails.getPropertyNotes().stream().count() - 1).findFirst()
										.get();
							}
//							System.out.println("Property Id -> "+ propertyDetails.getId());
						}
					} catch (Exception e) {
						logger.error(e.getLocalizedMessage());
						propertyDetails = new PropertyDetails();
					}

					propertyDetails = haulerPropertyExportDto.insertData(haulerPropertyExportDto, user,
							propertyDetails);
					if (propertyDetails.getName() != null && !propertyDetails.getName().equalsIgnoreCase("")) {
						propertyDetails = repo.save(propertyDetails);
					}

					if (!haulerPropertyExportDto.getAddiNote().equals(null)
							&& haulerPropertyExportDto.getAddiNote() != "") {
						if (propertyDetails.getId() != null && propertyDetails.getId() > 0) {
							propertyDetailNote.setNotes(haulerPropertyExportDto.getAddiNote());
							propertyDetailNote.setPropertyDetailId(propertyDetails.getId());
							propertyDetailNote.setCreatedUserDetails(user.getFirstName() + " " + user.getLastName());
							propertyDetailNote.setCreatedBy(userId);
							propertyDetailNote.setCreatedTs(Instant.now());
							propertyDetailNote = propertyNotesRepo.save(propertyDetailNote);
						}
					}
				}
			}

//			----------------------------------------- Import time on projection --------------
//			File file2 = ConvertFile.convertMultiPartToFile(file);
//			FileInputStream fis = new FileInputStream(file2);
//			XSSFWorkbook book = new XSSFWorkbook(fis);
//			XSSFSheet sheet = book.getSheetAt(0);
//			XSSFRow row = null;
//			User user = userRepo.findByUserId(userId);
//			List<PropertyDetails> propertyDetailsList = new ArrayList<PropertyDetails>();
//
//			int i = 3;
//			while ((row = sheet.getRow(i)) != null) {
//				int noOfColumns = sheet.getRow(2).getLastCellNum();
////				System.out.println(noOfColumns);
//				PropertyDetails propertyDetails = new PropertyDetails();
//				PropertyDetailNote propertyDetailNote = new PropertyDetailNote();
//				try {
//					propertyDetails = repo.findByName(row.getCell(0).toString());
//					if (propertyDetails == null) {
//						propertyDetails = new PropertyDetails();
//					} else {
//						if (propertyDetails.getPropertyNotes().size() > 0) {
//							propertyDetailNote = propertyDetails.getPropertyNotes().stream()
//									.skip(propertyDetails.getPropertyNotes().stream().count() - 1).findFirst().get();
//						}
//
//					}
//				} catch (Exception e) {
//					logger.error(e.getLocalizedMessage());
//					propertyDetails = new PropertyDetails();
//				}
//
//				List<String> operatingCityList = new ArrayList<String>();
//				String operatorCity = "";
//				int j = 0;
//				while (sheet.getRow(2).getCell(j) != null) {
////					System.out.println("Herder : " + sheet.getRow(2).getCell(j) + ": \t");
////					System.out.println(row.getCell(j));
//					String headerName = sheet.getRow(2).getCell(j).toString();
//
//					switch (headerName.replaceAll("\\s", "")) {
//					case "HaulerCompany":
//						try {
//							propertyDetails.setName(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//							propertyDetails.setName(null);
//						}
//
//						break;
//					case "ContractSignedDate":
//						try {
//							propertyDetails.setContractDate(row.getCell(j).getDateCellValue());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//							propertyDetails.setContractDate(null);
//						}
//
//						break;
//					case "Address":
//						try {
//							propertyDetails.setAddress(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "City":
//						try {
//							propertyDetails.setCity(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "State":
//						try {
//							propertyDetails.setState(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "Postal/Zip":
//						try {
//							propertyDetails.setZip(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "ContactName":
//						try {
//							propertyDetails.setPriContactName(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "ContactTitle":
//						try {
//							propertyDetails.setPriContactTitle(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "Phone#":
//						try {
//							propertyDetails.setPriContactPhone(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "E-Mail":
//						try {
//							propertyDetails.setPriContactEmail(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "SecondaryContactName":
//						try {
//							propertyDetails.setSecContactName(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "SecondaryContactTitle":
//						try {
//							propertyDetails.setSecContactTitle(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "SecondaryPhone#":
//						try {
//							propertyDetails.setSecContactPhone(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "SecondaryE-Mail":
//						try {
//							propertyDetails.setSecContactEmail(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "CompanyWebsite":
//						try {
//							propertyDetails.setCompanyWebsite(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "NumberofTrucks":
//						try {
//							propertyDetails.setOtherDetailNumberOfVehicle(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "TruckType(size)":
//						try {
//							propertyDetails.setVehicleType(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					case "AdditionalNotes":
//						try {
//							propertyDetailNote.setNotes(row.getCell(j).toString());
////							System.out.println("Print Value -> " + row.getCell(j));
//						} catch (Exception e) {
//						}
//
//						break;
//					default:
//						if (headerName.contains("Operating")) {
//							try {
//								if (row.getCell(j) != null && row.getCell(j).toString() != "") {
//									operatingCityList.add(row.getCell(j).toString());
//								}
//
//							} catch (Exception e) {
//							}
//
//						}
//						break;
//					}
//					j++;
//				}
//				propertyDetailNote.getNotes();
//				operatorCity = String.join(",", operatingCityList);
//				propertyDetails.setOperatingCitys(operatorCity);
//				propertyDetails.setIsDeleted(false);
//				if (propertyDetails.getId() != null && propertyDetails.getId() > 0) {
//					propertyDetails.setUpdatedBy(userId);
//					propertyDetails.setUpdatedTs(Instant.now());
//				} else {
//					propertyDetails.setCreatedBy(userId);
//					propertyDetails.setCreatedTs(Instant.now());
//					propertyDetails.setUpdatedBy(userId);
//					propertyDetails.setUpdatedTs(Instant.now());
//				}
//				propertyDetails = repo.save(propertyDetails);
//				if (propertyDetailNote.getNotes() != null) {
//					propertyDetailNote.setPropertyDetailId(propertyDetails.getId());
//					propertyDetailNote.setCreatedUserDetails(user.getFirstName() + " " + user.getLastName());
//					propertyDetailNote.setCreatedBy(userId);
//					propertyDetailNote.setCreatedTs(Instant.now());
//					propertyDetailNote = propertyNotesRepo.save(propertyDetailNote);
//				}
//				propertyDetailsList.add(propertyDetails);
//				i++;
//			}

			status = "Success";
		} catch (ParseException e) {
			logger.error(e.getLocalizedMessage());
			status = e.getLocalizedMessage();
			
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			status = e.getLocalizedMessage();
			
		}
		return status;
	}

}
