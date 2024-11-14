package org.example;

import java.io.*;
import java.util.regex.*;

public class EmailValidator {

    public static void findValidEmails(String filePath) {
        String emailPattern = "^[A-Za-z0-9](?!.*\\.\\.)([A-Za-z0-9.]{4,28}[A-Za-z0-9])@[A-Za-z0-9.]+\\.[A-Za-z]{2,}$";

        String content = readFile(filePath);
        Pattern pattern = Pattern.compile("\\b\\S*@\\S*\\b");
        Matcher matcher = pattern.matcher(content);

        boolean foundValidEmail = false;
        while (matcher.find()) {
            String word = matcher.group();
            if (word.matches(emailPattern)) {
                System.out.println("Valid email found: " + word);
                foundValidEmail = true;
            }
        }

        if (!foundValidEmail) {
            System.out.println("No valid email addresses found.");
        }
    }

    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
