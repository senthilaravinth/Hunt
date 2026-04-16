package com.learn.calculator;

public class App {
    public static void main(String[] args) {
    App myCalc = new App();
    
    System.out.println("Hello World!");

    // Add "" + to force the integer result into a String format
    System.out.println("Subtraction Result: " + myCalc.subtract(6, 4));
    System.out.println("Addition Result: " + myCalc.add(2, 5));
}

    public int add(int a, int b) { 
        return a + b; 
    }

    public int subtract(int a, int b) { 
        return a - b; 
    }
}