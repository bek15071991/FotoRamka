package com.example.fotoramka.HttpMethod;

import org.apache.http.client.methods.HttpPost;

public class HttpPropFind extends HttpPost {
    @Override
    public String getMethod() {
        return "PROPFIND";
    }

    public HttpPropFind(String uri) {
        super(uri);
    }

    public HttpPropFind() {

    }
}
