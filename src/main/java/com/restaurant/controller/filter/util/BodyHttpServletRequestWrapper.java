package com.restaurant.controller.filter.util;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BodyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] requestBody;

    public BodyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestBody = IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Помилка при отриманні тіла (body) запиту", e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedServletInputStream(requestBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        InputStreamReader isr = new InputStreamReader(getInputStream(), getCharacterEncoding());
        return new BufferedReader(isr);
    }

    public String getRequestBody() {
        return new String(requestBody);
    }
}

class CachedServletInputStream extends ServletInputStream {
    private final ByteArrayInputStream inputStream;

    public CachedServletInputStream(byte[] content) {
        this.inputStream = new ByteArrayInputStream(content);
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public boolean isFinished() {
        return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}
