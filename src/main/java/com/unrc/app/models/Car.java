package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Car extends Model{
    static {
        validatePresenceOf("type");
    }
}