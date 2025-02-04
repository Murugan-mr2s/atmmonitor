package com.example.model;

public record AtmSerivceModel(String bank,
        String atmid,
        String userid,
        AtmServiceType serviceType,
        String serviceStatus,
        String observe_at) implements AtmDataInf {

}
