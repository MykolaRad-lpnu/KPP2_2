import org.example.EmailValidator;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    private Path createTempFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }

    @Test
    public void testFindValidEmails_withValidEmails() throws IOException {
        String content = "valid1@example.com\n" +
                "another.valid@email.co.uk\n" +
                "test.email@domain.com";
        Path tempFile = createTempFile(content);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        EmailValidator.findValidEmails(tempFile.toString());

        String output = outputStream.toString();
        assertTrue(output.contains("Valid email found: valid1@example.com"));
        assertTrue(output.contains("Valid email found: another.valid@email.co.uk"));
        assertTrue(output.contains("Valid email found: test.email@domain.com"));

        Files.delete(tempFile);
    }

    @Test
    public void testFindValidEmails_withInvalidEmails() throws IOException {
        String content = "invalid-email@com\n" +
                "justtext@missingdotcom\n" +
                "@emptybeforeat.com";
        Path tempFile = createTempFile(content);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        EmailValidator.findValidEmails(tempFile.toString());

        String output = outputStream.toString();
        assertTrue(output.contains("No valid email addresses found."));

        Files.delete(tempFile);
    }

    @Test
    public void testFindValidEmails_withValidAndInvalidEmails() throws IOException {
        String content = "valid1@example.com\n" +
                "invalid-email@com\n" +
                "another.valid@email.co.uk\n" +
                "justtext@missingdotcom\n" +
                "test.email@domain.com\n" +
                "@emptybeforeat.com";

        Path tempFile = createTempFile(content);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        EmailValidator.findValidEmails(tempFile.toString());

        String output = outputStream.toString();
        assertTrue(output.contains("Valid email found: valid1@example.com"));
        assertTrue(output.contains("Valid email found: another.valid@email.co.uk"));
        assertTrue(output.contains("Valid email found: test.email@domain.com"));

        assertFalse(output.contains("valid-email@com"));
        assertFalse(output.contains("justtext@missingdotcom"));
        assertFalse(output.contains("@emptybeforeat.com"));

        Files.delete(tempFile);
    }


    @Test
    public void testFindValidEmails_withEmptyFile() throws IOException {
        Path tempFile = createTempFile("");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        EmailValidator.findValidEmails(tempFile.toString());

        String output = outputStream.toString();
        assertTrue(output.contains("No valid email addresses found."));

        Files.delete(tempFile);
    }

    @Test
    public void testFindValidEmails_withNoEmails() throws IOException {
        String content = "This is just some random text with no email addresses.";
        Path tempFile = createTempFile(content);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        EmailValidator.findValidEmails(tempFile.toString());

        String output = outputStream.toString();
        assertTrue(output.contains("No valid email addresses found."));

        Files.delete(tempFile);
    }
}
