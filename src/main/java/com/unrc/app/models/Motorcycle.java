package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motorcycle extends Model{
    static {
        validatePresenceOf("type");
    }
}