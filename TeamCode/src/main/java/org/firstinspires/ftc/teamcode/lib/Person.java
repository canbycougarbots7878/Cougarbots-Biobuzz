package org.firstinspires.ftc.teamcode.lib;

public class Person {
    public String name;
    public int age;
    public double height;
    public boolean disabled;

    public Person() {
        this.name = "John Person";
        this.age = 40;
        this.height = 72;
        this.disabled = false;
    }
    public Person(String name, int age, double height, boolean disabled) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.disabled = disabled;
    }
}