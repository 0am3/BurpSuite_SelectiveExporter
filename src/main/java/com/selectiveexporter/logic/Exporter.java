package com.selectiveexporter.logic;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;

import java.util.List;

public class Exporter {
    private final MontoyaApi api;

    public Exporter(MontoyaApi api) {
        this.api = api;
    }

    public String exportToMarkdown(
            HttpRequest request, 
            HttpResponse response, 
            boolean includeReqHeaders, 
            boolean includeReqBody, 
            boolean includeResHeaders, 
            boolean includeResBody) {
        StringBuilder sb = new StringBuilder();
        sb.append("### HTTP Interaction\n\n");
        
        sb.append("#### Request\n");
        sb.append("`").append(request.method()).append(" ").append(request.url()).append("`\n\n");
        
        if (includeReqHeaders) {
            sb.append("**Headers:**\n");
            sb.append("```\n");
            request.headers().forEach(header -> sb.append(header.toString()).append("\n"));
            sb.append("```\n\n");
        }

        if (includeReqBody && request.body().length() > 0) {
            sb.append("**Body:**\n");
            sb.append("```json\n");
            sb.append(request.body().toString());
            sb.append("\n```\n\n");
        }

        if (response != null) {
            sb.append("#### Response\n");
            sb.append("**Status:** ").append(response.statusCode()).append("\n\n");

            if (includeResHeaders) {
                sb.append("**Headers:**\n");
                sb.append("```\n");
                response.headers().forEach(header -> sb.append(header.toString()).append("\n"));
                sb.append("```\n\n");
            }

            if (includeResBody && response.body().length() > 0) {
                sb.append("**Body:**\n");
                sb.append("```json\n");
                sb.append(response.body().toString());
                sb.append("\n```\n");
            }
        }
        
        return sb.toString();
    }
}
