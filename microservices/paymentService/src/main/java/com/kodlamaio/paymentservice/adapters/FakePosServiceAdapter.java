package com.kodlamaio.paymentservice.adapters;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class FakePosServiceAdapter implements PosService {
    @Override
    public void pay() {
        //boolean isPaymentSuccessful = new Random().nextBoolean();
        boolean isPaymentSuccessful = true;
        if (!isPaymentSuccessful){
            throw new RuntimeException("Payment failed.");
        }
    }
}
