package com.example.controller.request;

import com.example.model.AtmServiceType;

public record ServiceModelRequest(String bank,
                                  String atmid,
                                  AtmServiceType serviceType,
                                  String serviceStatus,
                                  String userid,
                                  String observe_at) {
}
