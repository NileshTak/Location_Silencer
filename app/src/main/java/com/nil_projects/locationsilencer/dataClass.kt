package com.nil_projects.locationsilencer

class DataModel
{
    var ID : Int = 0
    var Name: String? = null
    var Latitude: String? = null
    var Longitude: String? = null

    constructor(Name: String,Longitude: String,Latitude: String) {
        this.Name = Name
        this.Latitude = Latitude
        this.Longitude = Longitude
    }

    constructor()
    {

    }
}