package com.example.mobihealthapis.GeneralFunctions;

import androidx.annotation.Nullable;

import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.Models.Issues;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.StringTokenizer;

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

    public static String getfrequency(LinkedList linkedList){

        ListIterator li = linkedList.listIterator(0);

        while (li.hasNext()) {

            String result1 = CheckDataLL(li.next().toString(), StaticData.Dosage_freq);

            //"daily","alternate","alternative","alternatively","weekly","monthly","15","fifteen"

            if(result1!= null){
                switch (result1){

                    case "15":
                        if(li.hasNext()){
                            if(li.next().toString().equals("days") &&
                                    !li.previous().toString().equals("for")){
                                return result1;
                            }
                            else
                                return "Daily";
                        }


                    case "fifteen":
                        if(li.hasNext()){
                            if(li.next().toString().equals("days") &&
                                    !li.previous().toString().equals("for")){
                                return result1;
                            }
                            else
                                return "Daily";
                        }
                    default:
                        return result1;
                }
            }


        }

        return "Daily";

    }

    public static String updatefrequency(LinkedList linkedList){

        ListIterator li = linkedList.listIterator(0);

        while (li.hasNext()) {

            String result1 = CheckDataLL(li.next().toString(), StaticData.Dosage_freq);

            //"daily","alternate","alternative","alternatively","weekly","monthly","15","fifteen"

            if(result1!= null){
                switch (result1){

                    case "15":
                        if(li.hasNext()){
                            if(li.next().toString().equals("days") &&
                                    !li.previous().toString().equals("for")){
                                return result1;
                            }
                            else
                                return null;
                        }


                    case "fifteen":
                        if(li.hasNext()){
                            if(li.next().toString().equals("days") &&
                                    !li.previous().toString().equals("for")){
                                return result1;
                            }
                            else
                                return null;
                        }
                    default:
                        return result1;
                }
            }


        }

        return null;

    }

    public static String getDuration(LinkedList rawPrescriptions) {
        ListIterator li = rawPrescriptions.listIterator(0);

        while (li.hasNext()) {


            if (li.next().toString().equals("for") ||  li.next().toString().equals("duration"))  {
                String temp=li.next().toString();
                if (temp.equals("a")) {
                    String value = li.next().toString();
                    String temptime = "";
                    if(li.hasNext()){
                        temptime = li.next().toString();

                        switch (temptime){

                            case "month" :
                                return value + " "+temptime;

                            case "months" :
                                return value + " "+temptime;

                            case "day" :
                                return value + " "+temptime;

                            case "days" :
                                return value + " "+temptime;

                            case "week" :
                                return value + " "+temptime;

                            case "weeks" :
                                return value + " "+temptime;

                            case "year" :
                                return value + " "+temptime;
                        }

                    }

                    return value;
                }
                else {
                    //String value = li.next().toString();
                    String temptime = "";
                    if(li.hasNext()){
                        temptime = li.next().toString();

                        switch (temptime){

                            case "month" :
                                return temp + " "+temptime;

                            case "months" :
                                return temp + " "+temptime;

                            case "day" :
                                return temp + " "+temptime;

                            case "days" :
                                return temp + " "+temptime;

                            case "week" :
                                return temp + " "+temptime;

                            case "weeks" :
                                return temp + " "+temptime;

                            case "year" :
                                return temp + " "+temptime;
                        }

                    }
                    return temp;
                }

            }


        }
        return null;
    }

    public static String getMedDosage(LinkedList rawPrescriptions,String medicine){
        String rawdata = "";

        ListIterator li = rawPrescriptions.listIterator(0);
        if(rawPrescriptions.size()>1 && medicine !=null)  {
            while(li.hasNext()) {

                rawdata += li.next().toString()+" ";

            }



            int position = rawdata.lastIndexOf(medicine)+medicine.length();

            String medcut = rawdata.substring(position, rawdata.length());

            StringTokenizer st = new StringTokenizer(medcut, " ");
            String[] dosagearr = new String[st.countTokens()];
            int x = 0;
            while(st.hasMoreTokens()){
                dosagearr[x] = st.nextToken();
                x++;
            }

            try{
                if(!(dosagearr[1].equals("times") || dosagearr[1].equals("time"))){

                    try{
                        int temp = Integer.parseInt(dosagearr[0]);
                        return dosagearr[0];
                    }
                    catch (Exception e){
                        //Toast.makeText(this, "Catch : Command Not Recognized !", Toast.LENGTH_SHORT).show();
                        return null;
                    }


                }
                else{
                    return null;
                }
            }
            catch (Exception e){
                return null;
            }


        }
        else
            return null;

    }

    public static String getMedTimings(LinkedList linkedList) {

        ListIterator li = linkedList.listIterator(0);

        String tempdata = "";

        while (li.hasNext()){
            tempdata+=li.next().toString()+" ";
        }

        for(int i = 0; i < StaticData.Dosage_Timing.length; i++){

            if(tempdata.contains(StaticData.Dosage_Timing[i])){

                switch (StaticData.Dosage_Timing[i]){

                    case "before" :
                        return "Before Food";

                    case "after" :
                        return "After Food";

                    default:
                        return null;
                }

            }

        }
        return null;
    }


    public static double[] getDailyTimings(LinkedList linkedList){
        double[] temp = new double[3];



            temp[0]=temp[1]=temp[2] =0;
            String rawdata = "";


            ListIterator li = linkedList.listIterator(0);

            while(li.hasNext()){

                //String result = CheckDataLL(li.next().toString(),StaticData.Timings);
                rawdata +=li.next().toString()+" ";

            }
            if((rawdata.contains("thrice")||
                    rawdata.contains("3 times")||
                    rawdata.contains("3 time")||
                    rawdata.contains("three times") ||
                    rawdata.contains("three time")) ||
                    rawdata.contains("price a day")){

                temp[0]=temp[1]=temp[2] =1;
                return temp;

            }
            else{
                for(int i =0; i<StaticData.Timings.length;i++){

                    if(rawdata.contains(StaticData.Timings[i])){
                        switch (StaticData.Timings[i]){

                            //"morning","noon","afternoon","evening"
                            case "morning" :
                                temp[0] = 1;
                                break;


                            case "noon" :
                                temp[1] = 1;
                                break;

                            case "afternoon" :
                                temp[1] = 1;
                                break;

                            case "after noon" :
                                temp[1] = 1;
                                break;

                            case "evening" :
                                temp[2] = 1;
                                break;


                        }
                    }

                }
            }




        return temp;


    }

    public static String CheckDataLL(String temp,String[] dataset) {

        for(int i = 0; i < dataset.length; i++){

            if(temp.equals(dataset[i])){
                return dataset[i];
            }

        }
        return null;

    }
}
