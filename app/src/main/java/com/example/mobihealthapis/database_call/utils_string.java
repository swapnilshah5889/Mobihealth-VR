package com.example.mobihealthapis.database_call;

public class utils_string {

    public static String BASE_URL = "http://dev.mobihealth.in/";
    //http://dev.mobihealth.in/mobile_url/mobile_vr_apis.php
    public interface API_URL{
        String SEARCH_MED=BASE_URL+"mobile_url/medicine/Medicine.php";
        String PATIENTS=BASE_URL+"mobile_url/Patients.php";
        String VR_APIS=BASE_URL+"mobile_url/mobile_vr_apis.php";

    }


}
