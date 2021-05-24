package com.galactik.notification;

import com.google.gson.annotations.SerializedName;

public class DeviceSubscribe {
    @SerializedName("visitorId")
    private String visitorId;

    @SerializedName("subscription")
    private Subscription subscription;

    @SerializedName("subscriptionType")
    private String subscriptionType;

    public DeviceSubscribe(String visitorId, Subscription subscription, String subscriptionType) {
        this.visitorId = visitorId;
        this.subscription = subscription;
        this.subscriptionType = subscriptionType;
    }
}
