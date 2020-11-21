package com.example.photorun;

import java.io.Serializable;

public class Tag implements Serializable {
    private String type;
    private String value;

    public Tag(String t, String v){
        this.type = t;
        this.value = v;
    }
    public String getType(){
        return type;
    }
    public String getValue(){
        return value;
    }
    public String toString(){
        return this.type + " - "+ this.value;
    }
    public boolean equals(Tag t) {
        if(value.contentEquals(t.getValue()) && type.contentEquals(t.getType())) {
            return true;
        }
        return false;
    }
}
