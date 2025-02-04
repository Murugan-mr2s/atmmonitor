package com.example.model;

public record MediaModel(
        String bank,
        String atmid,
        String path,
        String observe_at
) implements AtmDataInf {


}
