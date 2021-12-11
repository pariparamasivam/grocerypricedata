package com.app.grocery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.app.grocery.entity.GroceryPriceData;
import com.app.grocery.exception.constants.CommonConstants;
import com.app.grocery.repository.GroceryPriceDataRepository;
import com.app.grocery.service.exception.GroceryPriceDataBusinessException;

/**
 * Read the excel file from classpath location and load in grocery_price_data
 * table.
 *
 *
 */
@Component
public class GroceryAppRunner implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(GroceryAppRunner.class);
	@Autowired
	private GroceryPriceDataRepository groceryPriceDataRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		logger.info("Inside run method in {}", this.getClass());
		FileInputStream excelFileStream = null;
		Workbook workbook = null;
		List<GroceryPriceData> groceryPriceDataList = null;
		try {
			groceryPriceDataList = new ArrayList<>();
			// Date format with hypen dd-MM-yyyy.
			DateFormat formatterForHypen = new SimpleDateFormat(CommonConstants.DATE_FORMAT_HYPEN);
			// Date format with slash dd/MM/yyyy.
			DateFormat formatterForSlash = new SimpleDateFormat(CommonConstants.DATE_FORMAT_SLASH);
			logger.info("Get excel file from classpath location.");
			File file = ResourceUtils.getFile(CommonConstants.FILE_LOCATION);
			excelFileStream = new FileInputStream(file);

			workbook = new XSSFWorkbook(excelFileStream);

			Sheet sheet = workbook.getSheetAt(0);
			GroceryPriceData groceryPriceData = null;
			logger.info("Iterating rows in excelsheet and added set into object.");
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

				groceryPriceData = new GroceryPriceData();

				XSSFRow row = (XSSFRow) sheet.getRow(i);
				logger.info("Processing row number :" + row.getRowNum());

				row.getCell(3).setCellType(CellType.STRING);
				// If itemname is not empty then take item for further processing.
				Cell cell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (cell != null) {
					// Set Item Name in object.
					groceryPriceData.setItemName(row.getCell(1).getStringCellValue());
					// Date object cell.
					CellType cellType = row.getCell(2).getCellType();
					// Check date cell type STRING/NUMBER.
					switch (cellType) {
					case STRING:
						Date date = null;
						String dateValue = row.getCell(2).getStringCellValue();
						if (dateValue.contains("-")) {
							date = formatterForHypen.parse(dateValue);
						} else {
							date = formatterForSlash.parse(dateValue);
						}
						// Set Date value in object.
						groceryPriceData.setDateadded(date);
						break;
					case NUMERIC:
						// Set Date value in object.
						groceryPriceData.setDateadded(row.getCell(2).getDateCellValue());
						break;
					}
					// Price cell value.
					String priceVal = row.getCell(3).getStringCellValue();
					Double price = null;
					if (priceVal != null) {
						if (priceVal.equals(CommonConstants.STR_NULL)) {
							price = 0.0;
						} else {
							price = new Double(priceVal);
						}
					}
					// Set price data in object.
					groceryPriceData.setPrice(price);
					// Add object in list.
					groceryPriceDataList.add(groceryPriceData);
				}

			}
			logger.info("Loading data into In-memory database {}", this.getClass());
			groceryPriceDataRepository.saveAll(groceryPriceDataList);
			logger.info("Data loaded into In-memory database {}", this.getClass());
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException execpetion thrown " + e.getMessage() + "{}", this.getClass());
			throw new GroceryPriceDataBusinessException("NumberFormatException :" + e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException execpetion thrown " + e.getMessage() + "{}", this.getClass());
			throw new GroceryPriceDataBusinessException("FileNotFoundException :" + e.getMessage());
		} catch (IOException e) {
			logger.error("IOException execpetion thrown " + e.getMessage() + "{}", this.getClass());
			throw new GroceryPriceDataBusinessException("IOException :" + e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException execpetion thrown " + e.getMessage() + "{}", this.getClass());
			throw new GroceryPriceDataBusinessException("ParseException :" + e.getMessage());
		} finally {
			if (excelFileStream != null) {
				logger.info("Close InputStream Object.");
				excelFileStream.close();
			}
			if (workbook != null) {
				logger.info("Close Workbook Object.");
				workbook.close();
			}
		}

	}

}
