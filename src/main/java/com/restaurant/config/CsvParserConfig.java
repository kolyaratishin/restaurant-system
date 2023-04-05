package com.restaurant.config;

import com.restaurant.service.dto.ImportMealDto;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvParserConfig {

    @Bean(name = "rowProcessor")
    public BeanListProcessor<ImportMealDto> rowProcessor() {
        return new BeanListProcessor<>(ImportMealDto.class);
    }

    @Bean
    public CsvParser csvParser(BeanListProcessor<ImportMealDto> rowProcessor) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setProcessor(rowProcessor);

        return new CsvParser(settings);
    }

}
