package com.kbyai.faceattribute;

import android.graphics.Bitmap;

public class Person {
    public String id;
    public String name;
    public String phone;
    public Bitmap face;
    public byte[] templates;

    public Person() {

    }

    public Person(String id, String name, String phone, Bitmap face, byte[] templates) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.face = face;
        this.templates = templates;
    }
}
