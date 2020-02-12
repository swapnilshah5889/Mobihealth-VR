<?php


//Mobihealth Voice Recognition Apis for testing purpose only. 


require_once '/var/www/html/mobigram/Functions.php';

class APIS extends Functions {

    function __construct() {
        header('Content-Type: application/json');
        parent::__construct();
        
        if (isset($_POST['action'])) {
            if ($_POST['action'] == 'getDiagnosticTests') {
                $this->getDTests();
                
            }
            else if ($_POST['action'] == 'getDiagnosis') {
                $this->getDiagnosis();
                
            }
            else if($_POST['action'] == 'getIssues')
            {
                $this->getIsuues();
            }
            else if($_POST['action'] == 'getVitals')
            {
                $this->getVitals();
            }
            else if($_POST['action'] == 'getPatients')
            {
                $this->getMyPatients();
            }
            else if($_POST['action'] == 'getChildren')
            {
                $this->getChildren();
            }
            else if($_POST['action'] == 'getChildVitals'){
                $this->getChildVitals();
            }
            else if($_POST['action'] == 'insertPrescription'){
                $this->insertPrescription();
            }
            else if($_POST['action'] == 'getPrescriptionByID'){
                $this->getPrescriptionByID();
            }   
            else if($_POST['action'] == 'getAllPrescriptions'){
                $this->getAllPrescriptions();
            }
            else if($_POST['action'] == 'updatePrescription'){
                $this->updatePrescription();
            } 
            else if($_POST['action'] == 'Reschedule'){
                $this->Reschedule();
            }
            else if($_POST['action'] == 'map_data'){
                $this->Map_Data();
            }
            else if($_POST['action'] == 'getAlldatamap')
             {
                $this->getAlldatamap();
             } 
            else{
                $response = array('status' => false, 'error'=>'action missing');
                echo json_encode($response, true);
         }
        }


    }
    
    
    
     public function Map_Data2(){
        
         global $db;
         
        if (isset($_POST['data']) && isset($_POST['class']) && isset($_POST['device_id'])  && isset($_POST['device_id2']) ) {
            
            
            $data = $_POST['data'];
            $class = $_POST['class'];
            $device_id = $_POST['device_id'];
            $device_id2 = $_POST['device_id2'];
            $today = date("Y-m-d h:i:sa");
            $query1 = "SELECT data FROM `data_map` WHERE data = '$data'";
            $result1 = $db->FetchRow($query1);
            
            if($result1->num_rows > 0){
                
                $query2 = "UPDATE data_map SET count = count + 1,device_id = '$device_id', device_id2 = '$device_id2', datetime = '$today' WHERE data = '$data'";
                
                $result2 = $this->db->FetchRow($query2);
            
            
                if($result2){

                    $response = array('status' => "true", 'message'=>'count increased of : '.$data);
                    echo json_encode($response, true);
                }
                else{

                    $response = array('status' => "false", 'message'=>$result2);
                    echo json_encode($response, true);
                }
                
            }
            else{
                $query = "INSERT INTO data_map(data,count,class,datetime,device_id,device_id2) VALUES('$data','1','$class','$today','$device_id','$device_id2')";
            
                $result = $db->FetchRow($query);

                if ($result) {

                    $response = array('status' => "true", 'message'=>'data mapped');
                    echo json_encode($response, true);

                }
                else{
                    $response = array('status' => false, 'message'=>$result);
                    echo json_encode($response, true);
                }
            }
            
            
            
            
        }
        else{
            $response = array('status' => false, 'error'=>'parameters missing');
            echo json_encode($response, true);
        }
    }


    
    public function Map_Data(){
        
         global $db;
         
        if (isset($_POST['data']) && isset($_POST['class']) && isset($_POST['device_id'])  && isset($_POST['device_id2']) ) {
            
            
            $data = $_POST['data'];
            $class = $_POST['class'];
            $device_id = $_POST['device_id'];
            $device_id2 = $_POST['device_id2'];
            $today = date("Y-m-d h:i:sa");
            $query1 = "SELECT data FROM `data_map` WHERE data = '$data'";
            $result1 = $db->FetchRow($query1);
            
            $query = "INSERT INTO data_map(data,count,class,datetime,device_id,device_id2) VALUES('$data','1','$class','$today','$device_id','$device_id2')";
            
            $result = $db->FetchRow($query);

            if ($result) {

                $response = array('status' => "true", 'message'=>'data mapped');
                echo json_encode($response, true);

            }
            else{
                $response = array('status' => false, 'message'=>$result);
                echo json_encode($response, true);
            }
            
            
            
            
        }
        else{
            $response = array('status' => false, 'error'=>'parameters missing');
            echo json_encode($response, true);
        }
    }
    
    public function getAlldatamap(){
        
        global $db;
         
        
 

        $query = "SELECT DISTINCT data,class FROM `data_map`";

        $result = $db->FetchRow($query);

        $data_detail = [];

        if ($result->num_rows > 0) {

            while ($row = mysqli_fetch_assoc($result)) {

                $temp = array(
                        'data' => (string) $row['data'],
                        'map' => (string) $row['class']


                    );

                $data_detail[] = $temp;

            }


        }
        $total_records = $result->num_rows;
        $response = array('status' => true,'total_records' => (int) $total_records,'data'=> (array) $data_detail);
        echo json_encode($response, true);
        exit;
        
        
    }
    


    public function getDTests() {
        global $db;
        
        $query = "SELECT test_id,test_name FROM dig_test_data";
        $result = $db->FetchRow($query);
        $diagnostic_tests_data = [];
        
        if ($result->num_rows > 0) {
        
            while ($row = mysqli_fetch_assoc($result)) {
                $dtest_detail = array(
                    'test_id' => (int) $row['test_id'],
                    'test_name' => (string) $row['test_name']
                );

                $diagnostic_tests_data[] = $dtest_detail;
            }
        }

        $total_records = $result->num_rows;
        $response = array('status' => true, 'total_records' => (int) $total_records, 'data' => (array) $diagnostic_tests_data);
        echo json_encode($response, true);
        exit;
    }

    public function getDiagnosis() {
        
        global $db;
        
        $query = "SELECT id,observation FROM ep_observation WHERE language = 'en'";
        $result = $db->FetchRow($query);
        $diagnosis_data = [];
        
        if ($result->num_rows > 0) {
        
            while ($row = mysqli_fetch_assoc($result)) {
                $dtest_detail = array(
                    'id' => (int) $row['id'],
                    'diagnosis' => (string) $row['observation']
                );
                

                $diagnosis_data[] = $dtest_detail;
            }
        }

        $total_records = $result->num_rows;
        $response = array('status' => true, 'total_records' => (int) $total_records, 'data' => (array) $diagnosis_data);
        echo json_encode($response, true);
        exit;
        
    }

    public function getIsuues() {
        
          global $db;
        
        $query = "SELECT id,text FROM issue_master";
        $result = $db->FetchRow($query);
        $issue_data = [];
        
        if ($result->num_rows > 0) {
        
            while ($row = mysqli_fetch_assoc($result)) {
                $issue_detail = array(
                    'id' => (int) $row['id'],
                    'issues' => (string) $row['text']
                );

                $issue_data[] = $issue_detail;
            }
        }

        $total_records = $result->num_rows;
        $response = array('status' => true, 'total_records' => (int) $total_records, 'data' => (array) $issue_data);
        echo json_encode($response, true);
        exit;
        
    }

    public function getVitals() {
        
         if (isset($_POST['patient_id'])) {
            
                
                global $db;

                $userid = $_POST['patient_id'];
                
                $query = "SELECT * FROM vital_points WHERE user_id = '$userid'";
                $result = $db->FetchRow($query);
                $vitals_data = [];

                if ($result->num_rows > 0) {

                    while ($row = mysqli_fetch_assoc($result)) {
                        $vital_detail = array(
                            'id' => (int) $row['id'],
                            'patient_id' => (string) $row['user_id'],
                            'vital_id' => (string) $row['vital_id'],
                            'vd_id' => (string) $row['vd_id'],
                            'measured_on' => (string) $row['measured_on'],
                            'cond_json' => (string) $row['cond_json'],
                            'result_json' => (string) $row['result_json'],
                            'op_cond' => (string) $row['op_cond'],
                            'vd_point' => (string) $row['vd_point'],
                            'added_by' => (string) $row['added_by'],
                            'hp_id' => (string) $row['hp_id'],
                            'added_on' => (string) $row['added_on']
                        );

                        $vitals_data[] = $vital_detail;
                    }
                }

                $total_records = $result->num_rows;
                $response = array('status' => true, 'total_records' => (int) $total_records, 'data' => (array) $vitals_data);
                echo json_encode($response, true);
                exit;
                
            
         }
          else{
                $response = array('status' => false, 'error'=>'patient_id missing');
                echo json_encode($response, true);
         }
        
                
    }
    
    public function getMyPatients() {

        $required_params = ['drId'];
        if ($param = $this->Security->post_check($required_params)) {
            if ($_POST['drId'] != 0 && $_POST['drId'] > 0 && is_numeric($_POST['drId'])) {

                $dr_id = $_POST['drId'];
                $sub_query = " (SELECT appoinment_on FROM appoinment WHERE set_by = '$dr_id' AND userId = u.userId AND DATE(appoinment_on) >= '$current_date' ORDER BY appoinment_on ASC LIMIT 1)  AS next_appointment, ";
                $sub_query .=" (SELECT appoinment_on FROM appoinment WHERE set_by = '$dr_id' AND userId = u.userId AND DATE(appoinment_on) <= '$current_date' ORDER BY appoinment_on DESC LIMIT 1) AS last_appointment ";
                $query = "SELECT userId,firstName,lastName,userContactNumber,managed_id, user_image,age,gender, $sub_query FROM users u WHERE FIND_IN_SET('$dr_id',managed_id) ORDER BY firstName ASC";
                $result = $this->db->FetchRow($query);

                $patient_data = [];

                if ($result->num_rows > 0) {
                    while ($row = mysqli_fetch_assoc($result)) {

                        $t=time();
                        $dtime = $row['next_appointment'];
                        $temp = explode(" ",$dtime);
                        $today = date("Y-m-d");
                        if($temp[0] >= $today){
                            
                            $uid = $row['userId'];
                            $user_image = $row['user_image'];
                            $current_date = date('Y-m-d');


                            if (($user_image == null) || ($user_image == ' ')) {
                                $img = CDN_PATH . '/upload/user_image/by_default_pics.jpeg';
                            } else {
                                if (substr($user_image, 0, 6) == "upload") {
                                    $img = CDN_PATH .'/'. $user_image;
                                }
                                else {
                                    $img = $user_image;
                                }
                            }

                            $patient_detail = array(
                                'PatientId' => (int) $row['userId'],
                                'Fname' => (string) $row['firstName'],
                                'Lname' => (string) $row['lastName'],
                                'ContactNumber' => (string) $row['userContactNumber'],
                                'NextAppointment' => (string) $row['next_appointment'],
                                'LastAppointment' => (string) $row['last_appointment'],
                                'Age' => (int) $row['age'],
                                'Gender' => (string) $row['gender'],
                                'UserImage' => (string) $img
                            );


                                $patient_data[] = $patient_detail;
                        }
                        
                    }
                }

                $total_records = $result->num_rows;

                $response = array('status' => true, 'total_records' => (int) $total_records, 'data' => (array) $patient_data);
                echo json_encode($response, true);
                exit;
            } else {
                $this->InvalidUserError();
            }
        } else {
            $this->ParamsErrorJson();
        }
    }
    
    public function getChildren() {

        $required_params = ['drId'];
        if ($param = $this->Security->post_check($required_params)) {
            if ($_POST['drId'] != 0 && $_POST['drId'] > 0 && is_numeric($_POST['drId'])) {

                $dr_id = $_POST['drId'];
                $sub_query = " (SELECT appoinment_on FROM appoinment WHERE relation IN('Son','Daughter') AND set_by = '$dr_id' AND userId = u.userId AND DATE(appoinment_on) >= '$current_date' ORDER BY appoinment_on ASC LIMIT 1)  AS next_appointment, ";
                $sub_query .=" (SELECT appoinment_on FROM appoinment WHERE set_by = '$dr_id' AND userId = u.userId AND DATE(appoinment_on) <= '$current_date' ORDER BY appoinment_on DESC LIMIT 1) AS last_appointment ";
                $query = "SELECT userId,firstName,lastName,userContactNumber,managed_id, user_image,age,gender, $sub_query FROM users u WHERE FIND_IN_SET('$dr_id',managed_id) ORDER BY firstName ASC";
                $result = $this->db->FetchRow($query);

                $patient_data = [];

                if ($result->num_rows > 0) {
                    while ($row = mysqli_fetch_assoc($result)) {

                        $t=time();
                        $dtime = $row['next_appointment'];
                        $temp = explode(" ",$dtime);
                        $today = date("Y-m-d");
                        if($temp[0] >= $today ){
                            
                            $uid = $row['userId'];
                            $user_image = $row['user_image'];
                            $current_date = date('Y-m-d');


                            if (($user_image == null) || ($user_image == ' ')) {
                                $img = CDN_PATH . '/upload/user_image/by_default_pics.jpeg';
                            } else {
                                if (substr($user_image, 0, 6) == "upload") {
                                    $img = CDN_PATH .'/'. $user_image;
                                }
                                else {
                                    $img = $user_image;
                                }
                            }

                            $patient_detail = array(
                                'PatientId' => (int) $row['userId'],
                                'Fname' => (string) $row['firstName'],
                                'Lname' => (string) $row['lastName'],
                                'ContactNumber' => (string) $row['userContactNumber'],
                                'NextAppointment' => (string) $row['next_appointment'],
                                'LastAppointment' => (string) $row['last_appointment'],
                                'Age' => (int) $row['age'],
                                'Gender' => (string) $row['gender'],
                                'UserImage' => (string) $img
                            );


                                $patient_data[] = $patient_detail;
                        }
                        
                    }
                }

                $total_records = $result->num_rows;

                $response = array('status' => true, 'total_records' => (int) $total_records, 'data' => (array) $patient_data);
                echo json_encode($response, true);
                exit;
            } else {
                $this->InvalidUserError();
            }
        } else {
            $this->ParamsErrorJson();
        }
    }
    
    public function Reschedule()
    {
        $today = date("Y-m-d 11:00:00");
        $datetimetemp =date('Y-m-d H:i:s',strtotime('+15 minutes',strtotime($today)));
        
//        $ids = array("37252","37247","37245","37244","37243","37315","37314","37313","37312","37311","37316","37317","37318","37319","37320");
       
        $ids = array(37247,37245,37244,37243,37315,37314,37313,37312,37311,37316,37317,37318,37319,37320);
       
        $response = [];
        for($i =0;$i<14;$i++){
            
            $t = $ids[$i];
            
            $query = "UPDATE appoinment SET appoinment_on='$datetimetemp' WHERE id = '$t' ";
            $result = $this->db->FetchRow($query);
            
            
            if($result){
                
                $x = array(

                    'id' => $t,
                    'datetime' => $datetimetemp,
                    'result' => 'true'
                );
                $response[] = $x;
            }
            else{
                
                $x = array(

                    'id' => $t,
                    'datetime' => $datetimetemp,
                    'result' => 'false'
                );
                $response[] = $x;
            }
            $datetimetemp = date('Y-m-d H:i:s',strtotime('+15 minutes',strtotime($datetimetemp)));
        }
        
        $tresponse = array('status' => true, 'message'=>(array)$response);
        echo json_encode($tresponse, true);
        
        
        
    }
    public function getChildVitals(){
        
        if (isset($_POST['patient_id'])) {
            
                
                global $db;

                $userid = $_POST['patient_id'];
                
                $query = "SELECT * FROM patient_profile_data WHERE user_id = '$userid'";
                $result = $db->FetchRow($query);
                $vitals_data = [];

                if ($result->num_rows > 0) {

                    while ($row = mysqli_fetch_assoc($result)) {
                        
                        $json_data = json_decode($row['value_json'], true);
                        
                        $height = "";
                        $weight = "";
                        $bmi = "";
                        
                        foreach ($json_data as $mydata ) {
                            if($mydata[param_name] == "HEIGHT"){
                                $height = $mydata[param_value];
                            }
                            if($mydata[param_name] == "WEIGHT"){
                                $weight = $mydata[param_value];
                            }
                            if($mydata[param_name] == "BMI"){
                                $bmi = $mydata[param_value];
                            }
                           
                        }
                        if($height == false || $height == true){
                            $height = 0;
                        }
                        if($weight == false || $weight == true){
                            $weight = 0;
                        }
                        if($bmi == false || $bmi == true){
                            $bmi = 0;
                        }
                        $vital_detail = array(
                            'id' => (int) $row['id'],
                            'patient_id' => (string) $row['user_id'],
                            'Height' => $height,
                            'Weight' => $weight,
                            'BMI' => $bmi
                        );

                        $vitals_data[] = $vital_detail;
                    }
                }

                $total_records = $result->num_rows;
                $response = array('status' => true, 'total_records' => (int) $total_records, 'data' => (array) $vitals_data);
                echo json_encode($response, true);
                exit;
                
            
         }
          else{
                $response = array('status' => false, 'error'=>'patient_id missing');
                echo json_encode($response, true);
         }
        
    }
    
    
    public function insertPrescription(){
        
         global $db;
         
        if (isset($_POST['patient_id']) && isset($_POST['rx_json']) && isset($_POST['patient_type']) ) {
            
            
            $id = $_POST['patient_id'];
            $rx = $_POST['rx_json'];
            $type = $_POST['patient_type'];
            $today = date("Y-m-d H:i:s");
            
            $query = "INSERT INTO vr_patient_details(patient_id,rx_json,rx_datetime,patient_type) VALUES('$id','$rx','$today','$type')";
            
            $result = $db->FetchRow($query);
            
            if ($result) {
                
                $response = array('status' => "true", 'message'=>'Rx Inserted');
                echo json_encode($response, true);
                
            }
            else{
                $response = array('status' => false, 'message'=>$result);
                echo json_encode($response, true);
            }
            
            
        }
        else{
            $response = array('status' => false, 'error'=>'parameters missing');
            echo json_encode($response, true);
        }
    }

    

    public function getPrescriptionByID(){
        
         global $db;
         
        if (isset($_POST['patient_id']) ) {
           
            $id = $_POST['patient_id'];
            
            $query = "SELECT * FROM vr_patient_details WHERE patient_id = '$id' ORDER BY rx_datetime DESC LIMIT 1";
            
            $result = $db->FetchRow($query);
            
            $rx_detail = [];
            
            if ($result->num_rows > 0) {
                
                while ($row = mysqli_fetch_assoc($result)) {
                    
                    $rx_detail = array(
                            'id' => (int) $row['id'],
                            'patient_id' => (string) $row['patient_id'],
                            'patient_type' => (string) $row['patient_type'],
                            'rx_json' => (string) $row['rx_json'],
                            'rx_datetime' => (string) $row['rx_datetime']
                        );
                    $response = array('status' => true, 'data'=>$rx_detail);
                    echo json_encode($response, true);
                    
                }
                
                
            }
            else{
                $response = array('status' => false, 'message'=>$result);
                echo json_encode($response, true);
            }
            
            
        }
        else{
            $response = array('status' => false, 'error'=>'parameters missing');
            echo json_encode($response, true);
        }
        
    }
    public function getAllPrescriptions(){
        
          global $db;
         
        if (isset($_POST['patient_type']) ) {
           
            $ptype = $_POST['patient_type'];
            
            $query = "SELECT * FROM vr_patient_details WHERE patient_type = '$ptype'";
            
            $result = $db->FetchRow($query);
            
            $All_details = [];
            
            if ($result->num_rows > 0) {
                
                while ($row = mysqli_fetch_assoc($result)) {
                    
                    $rx_detail = array(
                            'id' => (int) $row['id'],
                            'patient_id' => (string) $row['patient_id'],
                            'patient_type' => (string) $row['patient_type'],
                            'rx_json' => (string) $row['rx_json'],
                            'rx_datetime' => (string) $row['rx_datetime']
                        );
                  $All_details[] = $rx_detail;
                    
                }
                
                  $response = array('status' => true,'total_records' => $result->num_rows, 'data'=>$All_details);
                    echo json_encode($response, true);
                
            }
            else{
                $response = array('status' => false, 'message'=>$result);
                echo json_encode($response, true);
            }
            
            
        }
        else{
            $response = array('status' => false, 'error'=>'parameters missing');
            echo json_encode($response, true);
        }
        
        
        
    }
    
   
    public function updatePrescription(){
        
        global $db;
         
        if( isset($_POST['patient_id']) && isset($_POST['rx_json']) && isset($_POST['patient_type']) ) {
            
            $id = $_POST['patient_id'];
            
            $rx = $_POST['rx_json'];
            
            $today = date("Y-m-d H:i:s");
            
            $type = $_POST['patient_type'];
            
            $sql = "UPDATE `vr_patient_details` SET rx_json = '$rx', rx_datetime = '$today', patient_type = '$type' WHERE patient_id='$id' ORDER BY id DESC LIMIT 1";        
            
            $result = $db->FetchRow($sql);
            
            if ($result) {
                
                $response = array('status' => "true", 'message'=>'Rx Updated');
                echo json_encode($response, true);
                
            }
            else{
                $response = array('status' => false, 'message'=>$result);
                echo json_encode($response, true);
            }
            
        }
        else{
            $response = array('status' => false, 'error'=>'parameters missing');
            echo json_encode($response, true);
        }
        
    
        
    }


    
}



    $obj = new APIS();
	

?>