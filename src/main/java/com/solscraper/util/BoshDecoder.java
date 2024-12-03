package com.solscraper.util;

import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BoshDecoder {

       public static byte[] decodeBosh(String encodedBosh) {
           return Base64.getDecoder().decode(encodedBosh);
       }
       
       public static JsonNode parseBosh(byte[] decodedBosh) throws Exception {
           ObjectMapper mapper = new ObjectMapper();
           return mapper.readTree(decodedBosh);
       }
   }