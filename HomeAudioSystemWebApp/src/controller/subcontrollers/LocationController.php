<?php

trait LocationController
{
    /**
     *
     * Create a Location
     *
     * @param $location_name
     * @param $location_volume
     * @param $location_muted
     * @throws Exception
     */
    function createLocation($location_name, $location_volume, $location_muted)
    {
        // 1. Validate input
        $name = Inputvalidator::validate_input($location_name);
        $volume = Inputvalidator::validate_input($location_volume);
        $muted = Inputvalidator::validate_input($location_muted);

        $error = "";
        if ($name == null || strlen($name) == 0) {
            $error .= "@1Location name cannot be empty! ";
        }

        if ($volume == null || strlen($volume) == 0) {
            $error .= "@2Location volume cannot be empty! ";
        }

        if ($muted == null || strlen($muted) == 0) {
            $error .= "@3Location muted cannot be empty! ";
        }

        if (strlen($error) == 0) {
            // 2. Load all of the data
            $pm = new PersistenceHomeAudioSystem();
            $rm = $pm->loadDataFromStore();

            // 3. Add the new event
            $location = new Location($name, $volume, $muted);
            $rm->addLocation($location);

            // 4. Write all of the data
            $pm->writeDataToStore($rm);
        } else {
            throw new Exception(trim($error));
        }
    }

    /**
     *
     * Get a location by name
     *
     * @param $location_name
     * @return null
     */
    function getLocation($location_name)
    {
        $name = Inputvalidator::validate_input($location_name);

        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();

        foreach ($rm->getLocations() as $location)
            if (strcmp($location->getName(), $name) == 0)
                return $location;
        return null;
    }

    /**
     * Get all HAS Locations
     * @return array $locations
     */
    function getLocations()
    {
        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();
        return $rm->getLocations();
    }
}