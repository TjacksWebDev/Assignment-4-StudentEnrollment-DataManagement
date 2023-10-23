package com.coderscampus.students;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class StudentListApp {
    public static void main(String[] args) {
        // Read the master list file
        String[] lines = readMasterList("masterlist.csv");

        // Separate the data into three arrays based on courses
        String[] course1List = new String[lines.length];
        String[] course2List = new String[lines.length];
        String[] course3List = new String[lines.length];

        int course1Count = 0;
        int course2Count = 0;
        int course3Count = 0;

        for (String line : lines) {
            String[] data = line.split(",");
            String course = data[2];

            if (course.startsWith("COMPSCI")) {
                course1List[course1Count++] = line;
            } else if (course.startsWith("APMTH")) {
                course2List[course2Count++] = line;
            } else if (course.startsWith("STAT")) {
                course3List[course3Count++] = line;
            }
        }

        // Sort the student arrays by grade in descending order if they are not empty
        if (course1Count > 0) {
            sortArrayByGrade(course1List, course1Count);
        }
        if (course2Count > 0) {
            sortArrayByGrade(course2List, course2Count);
        }
        if (course3Count > 0) {
            sortArrayByGrade(course3List, course3Count);
        }

        // Write the separated and sorted arrays to CSV files
        writeToFile("course1.csv", course1List, course1Count);
        writeToFile("course2.csv", course2List, course2Count);
        writeToFile("course3.csv", course3List, course3Count);
    }

    private static void sortArrayByGrade(String[] studentArray, int count) {
        Arrays.sort(studentArray, 0, count, new Comparator<String>() {
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

    public static String[] readMasterList(String filename) {
        String[] lines = new String[1000]; // Assuming a maximum of 1000 lines

        int lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("masterslist.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines[lineCount++] = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trim the array to the actual number of lines read
        return Arrays.copyOf(lines, lineCount);
    }

    public static void writeToFile(String filename, String[] studentArray, int count) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Student ID,Student Name,Course,Grade\n");
            for (int i = 0; i < count; i++) {
                writer.write(studentArray[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
