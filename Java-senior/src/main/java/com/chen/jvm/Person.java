package com.chen.jvm;

/**
 * @author Robert V
 * @create 2022-10-19-下午6:39
 */
public class Person {

    private static String a = "fadf";
    private final String b = "ccc";
    public static void main(String[] args) {
        ClassLoader appClass = Person.class.getClassLoader();
        System.out.println("appClass = " + appClass);

        ClassLoader extClass = appClass.getParent();
        System.out.println("extClass = " + extClass);

        ClassLoader bootClass = extClass.getParent();
        System.out.println("bootClass = " + bootClass);
    }
}
