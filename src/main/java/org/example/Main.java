package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "src\\main\\resources\\emails.txt";
        EmailValidator.findValidEmails(filePath);
    }
}
