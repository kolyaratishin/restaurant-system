package com.restaurant.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExportExceptions extends RuntimeException {

    private final Error error;

    @Getter
    @AllArgsConstructor
    public enum Error {
        WRITE_TO_CSV_IS_BAD("Writing data to bytes was unsuccessful");

        private final String message;
    }

}
