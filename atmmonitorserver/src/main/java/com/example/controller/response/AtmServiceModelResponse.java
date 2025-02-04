package com.example.controller.response;

import com.example.model.AtmServiceStatus;
import com.example.model.AtmServiceType;

import java.time.LocalDateTime;

public record AtmServiceModelResponse(String bank,
                                      String atmid,
                                      String userid,
                                      AtmServiceType service,
                                      AtmServiceStatus status,
                                      LocalDateTime observe_at,
                                      LocalDateTime created_at) {
}
