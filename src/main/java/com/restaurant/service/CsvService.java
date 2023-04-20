package com.restaurant.service;

import com.restaurant.model.Meal;
import com.restaurant.model.MealGroup;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.MealRepository;
import com.restaurant.service.dto.ImportMealDto;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final CsvParser parser;
    private final BeanListProcessor<ImportMealDto> rowProcessor;
    private static final String CSV_TYPE = "text/csv";
    private static final String EXCEL_CSV_TYPE = "application/vnd.ms-excel";

    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final MealRepository mealRepository;
    private final RestaurantService restaurantService;
    private final ImportService importService;
    private static final String DELIMITER = ",";


    public String parseAndSave(MultipartFile file, Long restaurantId) {
        if (EXCEL_CSV_TYPE.equals(file.getContentType()) || CSV_TYPE.equals(file.getContentType())) {
            try {
                InputStream inputStream = file.getInputStream();
                parser.parse(inputStream);
                clearDatabaseFromPreviousMeals(restaurantId);
                rowProcessor.getBeans().forEach(dto -> importService.storeToDatabase(dto, restaurantId));
                return "Save of file " + file.getOriginalFilename() + " was successful";
            } catch (IOException e) {
//                throw new UploadExceptions(UploadExceptions.Error.SAVE_WAS_NOT_SUCCESSFUL);
            }
        }
//        throw new UploadExceptions(UploadExceptions.Error.INVALID_FILE_FORMAT);
        return "INVALID_FILE_FORMAT";
    }

    private void clearDatabaseFromPreviousMeals(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        restaurant.removeAllMealGroups();
        restaurantService.save(restaurant);
    }

    public byte[] export() {
        addHeader();
        addRows();
        return byteArrayOutputStream.toByteArray();
    }

    private void addHeader() {
        Arrays.stream(createHeader())
                .map(String::getBytes)
                .forEach(this::write);
    }

    private String[] createHeader() {
        return new String[]{"name" + DELIMITER, "price"};
    }

    private void addRows() {
        mealRepository.findAll().stream()
                .map(this::createRow)
                .forEach(row -> row.forEach(this::write));
    }

    private List<byte[]> createRow(Meal meal) {
        List<byte[]> bytesList = new ArrayList<>();

        byte[] price = meal.getPrice().toString().getBytes(StandardCharsets.UTF_8);

        bytesList.add("\n".getBytes());
        bytesList.add(meal.getName().getBytes(StandardCharsets.UTF_8));
        bytesList.add(DELIMITER.getBytes());
        bytesList.add(price);

        return bytesList;
    }

    private void write(byte[] bytes) {
        try {
            byteArrayOutputStream.write(bytes);
        } catch (IOException e) {
//            throw new ExportExceptions(ExportExceptions.Error.WRITE_TO_CSV_IS_BAD);
        }
    }

}
