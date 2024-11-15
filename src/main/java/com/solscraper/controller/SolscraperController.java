package com.solscraper.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solscraper.service.SolcraperWebSocket;
import com.solscraper.util.ApiUtils;

@RestController
public class SolscraperController {
	private final transient SolcraperWebSocket ws;
	
	public SolscraperController(@Autowired SolcraperWebSocket ws) {
		this.ws = ws;
	}
	
    @GetMapping(value = "crash-stats", produces = { "application/json" })
    public ResponseEntity<?> getTopStats(final HttpServletRequest request) {
        ResponseEntity<?> res = null;
        try {
            res = ApiUtils.buildSuccess(ws.getSortedMap());
        } catch (Exception e) {
            e.printStackTrace();
            res = ApiUtils.buildAndLogError("Failed to get top characters", e.getMessage());
        }
        return res;
    }
}
