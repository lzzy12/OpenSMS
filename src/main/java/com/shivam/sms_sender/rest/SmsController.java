package com.shivam.sms_sender.rest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.api.Http;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.shivam.sms_sender.rest.models.RegisterDevice;
import com.shivam.sms_sender.rest.models.SendSMSModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class SmsController {
    List<RegisterDevice> registeredDevices;

    SmsController() {
        registeredDevices = new ArrayList<>();
    }

    @PostMapping("/sms")
    @ResponseBody
    ResponseEntity<Object> sendSMS(@RequestBody SendSMSModel data) throws FirebaseMessagingException {
        final String res = FirebaseMessaging.getInstance().send(Message.builder()
                .setToken(registeredDevices.get(0).fcmToken())
                .putData("recipients", data.recipients().get(0))
                .putData("message", data.message()).build());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/device")
    @ResponseBody
    ResponseEntity<Object> registerDevice(@RequestBody RegisterDevice device) {
        if (device.phone() == null || device.phone().isEmpty() || device.fcmToken() == null || device.fcmToken().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to register device");
        }
        registeredDevices.add(device);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
