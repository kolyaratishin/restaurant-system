package com.restaurant.controller.filter.auth;

import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;

import java.util.List;


public abstract class Handler {
    protected Handler nextHandler;

    public static Handler link(List<Handler> list) {
        Handler first = list.get(0);
        Handler temp = first;
        for (int i = 1; i < list.size(); i++) {
            Handler nextInChain = list.get(i);
            temp.nextHandler = nextInChain;
            temp = nextInChain;
        }
        return first;
    }

    public boolean handle(String username, String password, BodyHttpServletRequestWrapper request) throws Exception{
        try {
            if (authoritiesCheck(username, password, request)) {
                return true;
            } else if (this.nextHandler == null) {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.nextHandler.handle(username, password, request);
    }

    public abstract boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) throws Exception;
}
