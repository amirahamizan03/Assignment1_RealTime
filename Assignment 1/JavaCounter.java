import java.io.*;

public class JavaCounter {
    public static void main(String[] args) {
        File folder = new File("Real-time"); 

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The folder 'Real-time' does not exist or is not a directory.");
            return;
        }

        File[] files = folder.listFiles(); 

        int javaFileCount = 0;
        int issueCount = 0;

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    javaFileCount++;
                    issueCount += countIssues(file); 
                }
            }
        }
     
        System.out.println("Number of Java Files = " + javaFileCount);
        System.out.println("Number of Issues = " + issueCount);
    }

    public static int countIssues(File file) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("// Issue") || line.contains("// Solved")) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + file.getName() + " - " + e.getMessage());
        }

        return count;
    }
}
