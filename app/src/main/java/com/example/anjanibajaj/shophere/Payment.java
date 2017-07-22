package com.example.anjanibajaj.shophere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.razorpay.PaymentResultListener;

public class Payment extends AppCompatActivity implements PaymentResultListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}
