package com.km086.server.service;

import java.io.InputStream;

public interface ProductParseService {

    void parse(Long merchantId,InputStream inputStream) throws RuntimeException ;
}
