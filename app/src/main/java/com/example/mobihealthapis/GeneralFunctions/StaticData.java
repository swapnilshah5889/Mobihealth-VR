package com.example.mobihealthapis.GeneralFunctions;

import java.util.ArrayList;
import java.util.List;

public class StaticData {


    public static int VOICE_RECOGNITION_REQUEST_CODE = 123;

    public static int VR_NOMATCH_PROMPT_SYMPTOM = 124;
    public static int VR_NOMATCH_PROMPT_DIAGNOSIS = 127;
    public static int VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST = 129;
    public static int VR_NOMATCH_PROMPT_MEDICINES = 131;

    public static int VR_MULTIPLE_SYMPTOMS = 125;
    public static int VR_MULTIPLE_DIAGNOSIS = 126;
    public static int VR_MULTIPLE_DIAGNOSTIC_TEST = 128;
    public static int VR_MULTIPLE_MEDICINES = 130;
    public static int VR_MULTIPLE_ADVICES = 132;
    public static int VR_UPDATE_ADVICE = 133;
    public static int VR_UPDATE_SYMPTOMS = 134;


    public static String PREF_PATIENT = "PREF_PATIENT";


    public static String[] Vitals = {"height", "weight", "wait", "head", "circumference", "temperature", "hc"};

    public static String[] Filters = {"and", "to", "with", "for", "in", "or"};

    public interface patient_details {

        public static int vitals = 0;
        public static int diagnosis = 2;
        public static int symptoms = 1;
        public static int medicine = 3;
        public static int diagnostic = 4;
        public static int advice = 5;
        public static int followup = 6;


    }

    public interface Adapter_identifier {

        public static int vitals = 0;
        public static int diagnosis = 2;
        public static int symptoms = 1;
        public static int medicine_add = 3;
        public static int diagnostic = 4;
        public static int advice = 5;
        public static int followup = 6;
        public static int patients = 7;
        public static int advice_add = 9;

        public static int medicine_delete = 8;

    }

    public static String[] Dosage_freq = {"daily", "alternate", "alternative", "alternatively", "weekly", "monthly", "15", "fifteen"};
    public static String[] Dosage_Timing = {"before", "after"};
    public static String[] Timings = {"morning", "noon", "afternoon", "evening", "after noon"};


    public static String[] DateFilters = {"today", "tomorrow"};
    public static List<String> MonthsFilters = new ArrayList<>();

    public static List<String> months() {
        MonthsFilters.add("january");
        MonthsFilters.add("february");
        MonthsFilters.add("march");
        MonthsFilters.add("april");
        MonthsFilters.add("may");
        MonthsFilters.add("june");
        MonthsFilters.add("july");
        MonthsFilters.add("august");
        MonthsFilters.add("september");
        MonthsFilters.add("october");
        MonthsFilters.add("november");
        MonthsFilters.add("december");
        return MonthsFilters;
    }
}

