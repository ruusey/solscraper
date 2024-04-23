package com.solscraper.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ApiSession {
    public String executeGet(String endpoint) throws IOException;

    public String executeGet(String endpoint, Map<String, String> json) throws IOException;

    public String executePost(String endpoint) throws IOException;

    public String executePost(String endpoint, Object json) throws IOException;

    public String executeDelete(String endpoint) throws IOException;

    public String executePatch(String endpoint) throws IOException;

    public String executePatch(String endpoint, Object json) throws IOException;

    public <T> List<T> parseResponseList(String response, Class<T> clazz) throws Exception;

    public <T> T parseResponse(String response, Class<T> clazz) throws Exception;

    public <T> T parseResponse(String response, Class<T> clazz, ObjectMapper mapper) throws Exception;


}
