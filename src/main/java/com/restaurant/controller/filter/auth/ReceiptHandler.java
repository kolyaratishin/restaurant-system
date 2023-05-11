package com.restaurant.controller.filter.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.controller.request.ReceiptAddMealRequest;
import com.restaurant.model.Receipt;
import com.restaurant.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiptHandler extends Handler {

    private final String RECEIPT_URI = "/api/receipt";
    private final ObjectMapper objectMapper;
    private final ReceiptService receiptService;

    @Override
    public boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(RECEIPT_URI)) {
            String method = request.getMethod();
            if (method.equals("GET") || method.equals("PUT") || method.equals("DELETE")) {
                Long receiptId = getReceiptIdFromPath(requestURI);
                Receipt receipt = receiptService.getReceiptById(receiptId);
                return receipt.getTable().getRestaurant().getOwner().getUsername().equals(username);
            } else if (method.equals("POST")) {
                if (requestURI.startsWith(RECEIPT_URI + "/meal")) {
                    ReceiptAddMealRequest receiptAddMealRequest = getReceiptAddMealRequest(request);
                    Receipt receipt = receiptService.getReceiptById(receiptAddMealRequest.getReceiptId());
                    return receipt.getTable().getRestaurant().getOwner().getUsername().equals(username);
                } else {
                    Long receiptId = getReceiptIdFromPath(requestURI);
                    Receipt receipt = receiptService.getReceiptById(receiptId);
                    return receipt.getTable().getRestaurant().getOwner().getUsername().equals(username);
                }
            }
        }
        return false;
    }

    private Long getReceiptIdFromPath(String requestURI) {
        String[] split = requestURI.split("/");
        String restUri = split[split.length - 1];
        return Long.valueOf(restUri.split("/")[0]);
    }

    private ReceiptAddMealRequest getReceiptAddMealRequest(BodyHttpServletRequestWrapper requestWrapper) {
        try {
            return objectMapper.readValue(requestWrapper.getRequestBody(), ReceiptAddMealRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
