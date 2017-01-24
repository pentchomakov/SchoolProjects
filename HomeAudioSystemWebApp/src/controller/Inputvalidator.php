<?php
class InputValidator
{
    public static function validate_input($data)
    {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }
    
    public static function validate_date($data)
    {
    	$data = trim($data);
    	$data = stripslashes($data);
    	$data = htmlspecialchars($data);
    	$data = date('YYYY-MM-DD', strtotime($data));
    	return $data;
    }

    public static function validate_bool($bool){
        return is_bool($bool);
    }

    public static function validate_number($number){
        return is_numeric($number);
    }

    public static function checkNullOrEmpty($data){
        if ($data == null || strlen($data) == 0)
            throw new Exception("Data is null or empty");
    }

    public static function checkNull($data){
        if ($data == null)
            throw new Exception("Data is null or empty");
    }
}
?>