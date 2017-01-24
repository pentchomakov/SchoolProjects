<?php

/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.24.0-3fa5fec modeling language!*/

class Location
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Location Attributes
    public $name;
    public $volume;
    public $muted;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct($aName, $aVolume, $aMuted)
    {
        $this->name = $aName;
        $this->volume = $aVolume;
        $this->muted = $aMuted;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public function setName($aName)
    {
        $wasSet = false;
        $this->name = $aName;
        $wasSet = true;
        return $wasSet;
    }

    public function setVolume($aVolume)
    {
        $wasSet = false;
        $this->volume = $aVolume;
        $wasSet = true;
        return $wasSet;
    }

    public function setMuted($aMuted)
    {
        $wasSet = false;
        $this->muted = $aMuted;
        $wasSet = true;
        return $wasSet;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getVolume()
    {
        return $this->volume;
    }

    public function getMuted()
    {
        return $this->muted;
    }

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
    }

}