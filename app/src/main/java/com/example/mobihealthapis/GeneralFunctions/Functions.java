package com.example.mobihealthapis.GeneralFunctions;

import androidx.annotation.Nullable;

import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.Models.Issues;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Functions {

    public static boolean CheckData(String temp,String[] dataset) {

        for(int i = 0; i < dataset.length; i++){

            if(temp.equals(dataset[i])){
                return true;
            }

        }
        return false;

    }
    //vitals

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    //height
    public static HashMap<String, Double> getVitals(String[] arr)
    {
        HashMap<String,Double> Vitals = new HashMap<>();


        for(int i = 0;i<arr.length;i++)
        {
            if(arr[i].equals("height") && hasNext((i+1), arr.length) )
            {
                String a =(String) arr[i+1];
                if(isNumeric(a))
                {
                    Vitals.put("height",Double.parseDouble(a));
                }

            }

            if(arr[i].equals("weight") && hasNext((i+1), arr.length) )
            {
                String a =(String) arr[i+1];
                if(isNumeric(a))
                {
                    Vitals.put("weight",Double.parseDouble(a));
                }

            }

            if((arr[i].equals("head") || arr[i].equals("hc")) && hasNext((i+1), arr.length) )
        {
            if(hasNext((i+1), arr.length)){
                if(isNumeric(arr[i+1])){
                    Vitals.put("head",Double.parseDouble(arr[i+1]));
                }


                else if(arr[i+1].equals("circumference") && hasNext((i+2),arr.length)){
                    String a =(String) arr[i+2];
                    if(isNumeric(a))
                    {
                        Vitals.put("head",Double.parseDouble(a));
                    }

                }

            }


        }
            if(arr[i].equals("temperature") && hasNext((i+1), arr.length) )
            {
                String a =(String) arr[i+1];
                if(isNumeric(a))
                {
                    Vitals.put("temperature",Double.parseDouble(a));
                }

            }




        }
        return Vitals;
    }

public static boolean hasNext(int pos,int size){
        if(pos<size)
            return true;
        else
            return false;

}

    public static boolean IsInList(int pos, int size){
        if(size == 0){
            return false;
        }
        if(pos<=size && pos>0)
            return true;
        else
            return false;

    }


    public static List<Issues.Data> FilterIssues(int position, List<String> filtered_symptopms, List<Issues.Data> issueResults) {

        while(position < (filtered_symptopms.size())){


            for(int i = 0; i < issueResults.size(); i++){

                if(!issueResults.get(i).getIssues().toLowerCase().contains(filtered_symptopms.get(position))){
                    issueResults.remove(issueResults.get(i));
                }

            }
            position++;
        }
        return issueResults;
    }


    public static List<Diagnosis.Data> FilterDiagnosis(int position, List<String> filtered_symptopms, List<Diagnosis.Data> issueResults) {

        while(position < (filtered_symptopms.size())){


            for(int i = 0; i < issueResults.size(); i++){

                if(!issueResults.get(i).getDiagnosis().toLowerCase().contains(filtered_symptopms.get(position))){
                    issueResults.remove(issueResults.get(i));
                }

            }
            position++;
        }
        return issueResults;
    }



    public static List<DiagnosticTests.Data> FilterDiagnosticTest(int position, List<String> filtered_symptopms, List<DiagnosticTests.Data> issueResults) {

        while(position < (filtered_symptopms.size())){


            for(int i = 0; i < issueResults.size(); i++){

                if(!issueResults.get(i).getTest_name().toLowerCase().contains(filtered_symptopms.get(position))){
                    issueResults.remove(issueResults.get(i));
                }

            }
            position++;
        }
        return issueResults;
    }
}
