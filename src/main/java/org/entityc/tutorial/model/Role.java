//
// This is class for enum:
//
//   Name:        Role
//   Description: 
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//
package org.entityc.tutorial.model;

public enum Role {
    // Is only allowed to view tutorials.
    STUDENT(1, "Student"),
    // Is allowed to view, modify and create new tutorials.
    INSTRUCTOR(2, "Instructor"),
    // Is allowed to do what the Instructor can do but also change the role of users.
    ADMINISTRATOR(3, "Administrator"),
    ;

    Role(int numberValue, String title) {
        this.numberValue = numberValue;
        this.title = title;
    }


    private String title;
    public String getTitle() {
        return title;
    }
    private int numberValue;

    public int getNumberValue() {
        return numberValue;
    }

    static public Role numberValueOf(int numberValue) {
        for (Role item : values()) {
            if (item.numberValue == numberValue) {
                return item;
            }
        }
        return null;
    }

}

