package com.example.controller.request;

public record MediaModelRequest(String bank,
                                String atmid,
                                String path,
                                String observe_at) {
}
