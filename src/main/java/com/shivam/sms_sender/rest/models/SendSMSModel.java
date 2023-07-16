package com.shivam.sms_sender.rest.models;

import lombok.Data;

import java.util.List;

public record SendSMSModel(List<String> recipients, String message) {}