package com.xian.jdk.until;

public class PathManipulation {

    public static String manipulatePath(String path) {
        String checkPath = path;
        if (checkPath.startsWith("/")) {
            checkPath = checkPath.substring(1);
        }
        checkPath = checkPath.replace("/", ":");
        return checkPath;
    }

    public static void main(String[] args) {
        String path = "/example/path/to:manipulate";
        String manipulatedPath = manipulatePath(path);
        System.out.println(manipulatedPath);
    }
}
