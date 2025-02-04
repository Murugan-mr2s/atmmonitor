package com.example.controller.response;


import java.time.LocalDateTime;

public record AtmPingModelResponse(String bank,
                                   String atmid,
                                   String status,
                                   LocalDateTime observe_at,
                                   LocalDateTime created_at) {
}
