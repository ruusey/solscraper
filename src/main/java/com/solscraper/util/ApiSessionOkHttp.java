package com.solscraper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
@Data
public class ApiSessionOkHttp implements ApiSession {
    private static final MediaType JSON = MediaType.get("application/json");
    private OkHttpClient client;
    private String baseUrl;
    private ObjectMapper mapper;
    private String bearerToken;
    private String accessToken;

    public ApiSessionOkHttp(String baseUrl) {
        this.client = new OkHttpClient();
        this.baseUrl = baseUrl;
        mapper = new ObjectMapper();
        log.info("Created API session to " + this.baseUrl);
    }

    public String executeGet(String endpoint) throws IOException {

        Request request = new Request.Builder().url(this.baseUrl + endpoint).get()
				.addHeader("Authority", "explorer-api.mainnet-beta.solana.com")
        		.addHeader("Accept", "*/*")
        		.addHeader("Accept-Language", "en-US,en;q=0.9")
        		.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
        		.addHeader("Sec-Ch-Ua-Mobile", "?0")
        		.addHeader("Sec-Ch-Ua-Platform", "\"Linux\"")
        		.addHeader("Sec-Fetch-Dest", "empty")
        		.addHeader("Sec-Fetch-Mode", "cors")
        		.addHeader("Sec-Fetch-Site", "same-site")
        		//.addHeader("Sol-Au", "Tq7=s2S-Q-r0N=B9dls02fKHyE1HyYa6aT5ezmIrm")
        		.addHeader("Sec-Ch-Ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"")
        		.addHeader("Solana-Client", "js/0.0.0-development")
        		.addHeader("Content-Type", "application/json")
        		.addHeader("Dnt", "1")
        		.addHeader("Origin", "https://explorer.solana.com")
        		.addHeader("Referrer", "https://explorer.solana.com/")
                .build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    public String executeGet(String endpoint, Map<String, String> queryParams) throws IOException {
        boolean first = true;
        for (Entry<String, String> query : queryParams.entrySet()) {
            if (first) {
                endpoint += ("?" + query.getKey() + "=" + query.getValue());
                first = false;
            } else {
                endpoint += ("&" + query.getKey() + "=" + query.getValue());
            }
        }
        Request request = new Request.Builder().url(this.baseUrl + endpoint).get()
                .addHeader("Content-Type", "application/json").build();

        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    public String executePost(String url, Object json) throws IOException {
        RequestBody body = RequestBody.create(JSON, this.asJsonString(json));
        Request request = new Request.Builder().url(this.baseUrl + url)
        		.addHeader("Authority", "explorer-api.mainnet-beta.solana.com")
        		.addHeader("Accept", "*/*")
        		.addHeader("Accept-Language", "en-US,en;q=0.9")
        		.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.37 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
        		.addHeader("Sec-Ch-Ua-Mobile", "?0")
        		.addHeader("Sec-Ch-Ua-Platform", "\"Linux\"")
        		.addHeader("Sec-Fetch-Dest", "empty")
        		.addHeader("Sec-Fetch-Mode", "cors")
        		.addHeader("Sec-Fetch-Site", "same-site")
        		//.addHeader("Sol-Au", "Tq7=s2S-Q-r0N=B9dls02fKHyE1HyYa6aT5ezmIrm")
        		.addHeader("Sec-Ch-Ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"")
        		.addHeader("Solana-Client", "js/0.0.0-development")
        		.addHeader("Content-Type", "application/json")
        		.addHeader("Dnt", "1")
        		.addHeader("Origin", "https://explorer.solana.com")
        		.addHeader("Referrer", "https://explorer.solana.com/")
        		.post(body).build();       
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    public String executePost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(this.baseUrl + url)
        		.addHeader("Authority", "explorer-api.mainnet-beta.solana.com")
        		.addHeader("Accept", "*/*")
        		.addHeader("Accept-Language", "en-US,en;q=0.9")
        		.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
        		.addHeader("Sec-Ch-Ua-Mobile", "?0")
        		.addHeader("Sec-Ch-Ua-Platform", "\"Linux\"")
        		.addHeader("Sec-Fetch-Dest", "empty")
        		.addHeader("Sec-Fetch-Mode", "cors")
        		.addHeader("Sec-Fetch-Site", "same-site")
        		//.addHeader("Sol-Au", "Tq7=s2S-Q-r0N=B9dls02fKHyE1HyYa6aT5ezmIrm")
        		.addHeader("Sec-Ch-Ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"")
        		.addHeader("Solana-Client", "js/0.0.0-development")
        		.addHeader("Content-Type", "application/json")
        		.addHeader("Dnt", "1")
        		.addHeader("Origin", "https://explorer.solana.com")
        		.addHeader("Referrer", "https://explorer.solana.com/")
        		.post(body).build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    public String executePost(String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder().url(this.baseUrl + url)
        		.addHeader("Authority", "explorer-api.mainnet-beta.solana.com")
        		.addHeader("Accept", "*/*")
        		.addHeader("Accept-Language", "en-US,en;q=0.9")
        		.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
        		.addHeader("Sec-Ch-Ua-Mobile", "?0")
        		.addHeader("Sec-Ch-Ua-Platform", "\"Linux\"")
        		.addHeader("Sec-Fetch-Dest", "empty")
        		.addHeader("Sec-Fetch-Mode", "cors")
        		.addHeader("Sec-Fetch-Site", "same-site")
        		//.addHeader("Sol-Au", "Tq7=s2S-Q-r0N=B9dls02fKHyE1HyYa6aT5ezmIrm")
        		.addHeader("Sec-Ch-Ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"")
        		.addHeader("Solana-Client", "js/0.0.0-development")
        		.addHeader("Content-Type", "application/json")
        		.addHeader("Dnt", "1")
        		.addHeader("Origin", "https://explorer.solana.com")
        		.addHeader("Referrer", "https://explorer.solana.com/")
        		.post(body).build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    public String executeDelete(String url) throws IOException {
        Request request = new Request.Builder().url(this.baseUrl + url).delete().build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    public String executePatch(String url, Object json) throws IOException {
        RequestBody body = RequestBody.create(JSON, this.asJsonString(json));
        Request request = new Request.Builder().url(this.baseUrl + url).patch(body).build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    public String executePatch(String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder().url(this.baseUrl + url).patch(body).build();
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Failed " + response.code() + " " + response.body().string());
            }
        }
    }

    private <T> String asJsonString(T obj) {
        String result = null;
        try {
            result = this.mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON response");
        }
        return result;
    }

    public void printJson(Object o) {
        log.info(this.asJsonString(o));
    }

    public <T> T parseResponse(String response, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        if (response == null || response.length() == 0) {
            response = "{}";
        }
        try {
            return mapper.readValue(response, clazz);
        } catch (Exception e) {
            throw e;
        }
    }

    public <T> T parseResponse(String response, Class<T> clazz, ObjectMapper mapper) throws Exception {
        try {
            return mapper.readValue(response, clazz);
        } catch (Exception e) {
            throw e;
        }
    }

    public <T> List<T> parseResponseList(String response, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static void main(String[] args) {
    	ApiSessionOkHttp httpClient = new ApiSessionOkHttp("https://dpat-vulture.upscapital.com/api/Token/GetToken?userIn=Vampire%231&passwordIn=%231Vampire");
    	try {
        	String response = httpClient.executeGet("");
        	log.info(response);
    	}catch(Exception e) {
    		log.error(e.getMessage());
    	}
    	
    }
}
