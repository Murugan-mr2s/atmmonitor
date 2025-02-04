package com.example.controller.request;


public record AtmPingModelRequest(
        String bank,
        String atmid,
        String status,
        String observe_at
) {
}
