package com.example.controller.response;

import java.time.LocalDateTime;

public record AtmMediaModelResponse(String bank,
                                    String atmid,
                                    String path,
                                    LocalDateTime observed_at,
                                    LocalDateTime created_at) {
}
