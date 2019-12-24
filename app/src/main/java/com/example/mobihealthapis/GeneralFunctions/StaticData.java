package com.example.mobihealthapis.GeneralFunctions;

public class StaticData {



   public static int  VOICE_RECOGNITION_REQUEST_CODE=123;
   public static int  VR_NOMATCH_PROMPT=124;
   public static int  VR_MULTIPLE_SYMPTOMS=125;


    public static String[] Vitals={"height","weight","wait","head","circumference","temperature","hc"};

    public static String[] Filters = {"and","to","with","for","in","or"};

   public interface patient_details{

       public static int vitals = 0;
       public static int diagnosis = 2;
       public static int symptoms = 1;
       public static int medicine = 3;
       public static int diagnostic = 4;
       public static int advice = 5;
       public static int followup = 6;


   }
    public interface Adapter_identifier{

        public static int vitals = 0;
        public static int diagnosis = 2;
        public static int symptoms = 1;
        public static int medicine = 3;
        public static int diagnostic = 4;
        public static int advice = 5;
        public static int followup = 6;
        public static int patients = 7;

    }

}

