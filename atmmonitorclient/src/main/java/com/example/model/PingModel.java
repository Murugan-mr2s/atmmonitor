package com.example.model;

import java.time.LocalDateTime;

public record PingModel(String bank,
                        String atmid,
                        String observe_at,
                        AtmStatus status) implements AtmDataInf{
}
