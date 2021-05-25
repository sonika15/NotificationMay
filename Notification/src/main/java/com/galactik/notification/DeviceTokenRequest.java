package com.galactik.notification;

import com.google.gson.annotations.SerializedName;

/**
 * Device token registration.
 * Created on 10/10/18.
 *
 * @author Omkar Todkar.
 */
public class DeviceTokenRequest {

    @SerializedName("deviceUid")
    public String device;

    @SerializedName("userId")
    public String userId;

    public String source = "android";

    public String token;
    public String clientId="";
    public String clientName="";
    public String emailId="";
    public String mobileNo="";

    public DeviceTokenRequest(String device, String token, String userId) {
        this.device = device;
        this.userId = userId;
        this.token = token;
    }
}
