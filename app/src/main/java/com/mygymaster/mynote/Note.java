package com.mygymaster.mynote;

/**
 * Created by logvinov on 28.04.2015.
 */
public class Note {
    //private variables
    int _id;
    String _name;
    String _date;

    // Empty constructor
    public Note(){

    }
    // constructor
    public Note(int id, String name, String date){
        this._id = id;
        this._name = name;
        this._date = date;
    }

    // constructor
    public Note(String name, String date){
        this._name = name;
        this._date = date;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getDate(){
        return this._date;
    }

    // setting phone number
    public void setDate(String date){
        this._date = date;
    }
}
