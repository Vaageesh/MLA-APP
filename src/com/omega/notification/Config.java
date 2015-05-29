package com.omega.notification;

/**
 * Created by vageesh on 4/23/15.
 */
public interface Config {
    // used to share GCM regId with application server - using php app server
    //static final String APP_SERVER_URL = "http://10.0.0.84/test/gcm_main.php?shareRegId=1";
    static final String APP_SERVER_URL = "http://rsdoddamani.co.in/app/php_scripts/gcm_main.php?shareRegId=1";
    //static final String APP_SERVER_URL = "http://192.168.1.2/test/gcm_main.php?shareRegId=1";

    // GCM server using java
    // static final String APP_SERVER_URL =
    // "http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

    // Google Project Number
    static final String GOOGLE_PROJECT_ID = "561410524960";
    static final String MESSAGE_KEY = "m";
}
