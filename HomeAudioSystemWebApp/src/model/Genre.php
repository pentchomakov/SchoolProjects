<?php

/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.24.0-3fa5fec modeling language!*/

class Genre
{

    //------------------------
    // STATIC VARIABLES
    //------------------------

    public static $Pop = 1;
    public static $Rock = 2;
    public static $Alternative = 3;
    public static $Rap = 4;
    public static $RB = 5;
    public static $Classical = 6;
    public static $Jazz = 7;
    public static $Contemporary = 8;
    public static $Country = 9;
    public static $Electronic = 10;
    public static $Latin = 11;
    public static $Gospel = 12;
    public static $Comedy = 13;
    public static $Reggae = 14;
    public static $AmericanRoots = 15;

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct()
    {
    }

    //------------------------
    // INTERFACE
    //------------------------

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
    }

}



?>