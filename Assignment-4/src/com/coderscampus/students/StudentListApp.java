package com.coderscampus.students;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentListApp {

    public static void main(String[] args) {
        // Read the master list file
        List<String> lines = readMasterList("masterlist.csv");

        // Separate the data into three lists based on courses
        List<String> course1List = new ArrayList<>();
        List<String> course2List = new ArrayList<>();
        List<String> course3List = new ArrayList<>();

        for (String line : lines) {
            String[] data = line.split(",");
            String course = data[2];

            if (course.startsWith("COMPSCI")) {
                course1List.add(line);
            } else if (course.startsWith("APMTH")) {
                course2List.add(line);
            } else if (course.startsWith("STAT")) {
                course3List.add(line);
            }
        }

        // Sort the student lists by grade in descending order if they are not empty
        if (!course1List.isEmpty()) {
            sortListByGrade(course1List);
        }
        if (!course2List.isEmpty()) {
            sortListByGrade(course2List);
        }
        if (!course3List.isEmpty()) {
            sortListByGrade(course3List);
        }

        // Write the separated and sorted lists to CSV files
        writeToFile("course1.csv", course1List);
        writeToFile("course2.csv", course2List);
        writeToFile("course3.csv", course3List);
    }

    private static void sortListByGrade(List<String> studentList) {
        Collections.sort(studentList, new Comparator<String>() {
            @Override
            public int compare(String student1, String student2) {
                // Split and parse the grade for comparison
                int grade1 = Integer.parseInt(student1.split(",")[3]);
                int grade2 = Integer.parseInt(student2.split(",")[3]);

                // Sort in descending order
                return Integer.compare(grade2, grade1);
            }
        });
    }

    public static List<String> readMasterList(String filename) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("masterslist.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static void writeToFile(String filename, List<String> studentList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Student ID,Student Name,Course,Grade\n");
            for (String studentData : studentList) {
                writer.write(studentData + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
