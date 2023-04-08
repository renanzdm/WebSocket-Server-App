package com.webfluxsocket.demo;

import java.util.List;

public class Mensagem {
    private String domain;
    private List<String> endpoints;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }

}
