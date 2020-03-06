import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;

// 
// Decompiled by Procyon v0.5.36
// 

public class TextComparer
{
    public static void main(final String[] array) {
        if (array.length != 2) {
            System.err.println("Usage: java TextComparer <file name> <file name>");
            System.exit(-1);
        }
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(array[0]));
        }
        catch (IOException ex) {
            System.err.println("Cannot open " + array[0]);
            System.exit(-1);
        }
        try {
            bufferedReader2 = new BufferedReader(new FileReader(array[1]));
        }
        catch (IOException ex2) {
            System.err.println("Cannot open " + array[1]);
            System.exit(-1);
        }
        int i = 0;
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String removeSpaces = removeSpaces(line);
                final String line2 = bufferedReader2.readLine();
                if (line2 == null) {
                    System.out.println("Second file is shorter (only " + i + " lines)");
                    System.exit(-1);
                }
                else {
                    final String removeSpaces2 = removeSpaces(line2);
                    if (!removeSpaces.equals(removeSpaces2)) {
                        System.out.println("Comparison failure in line " + i + ":");
                        System.out.println(removeSpaces);
                        System.out.println(removeSpaces2);
                        System.exit(-1);
                    }
                }
                ++i;
            }
            if (bufferedReader2.readLine() != null) {
                System.out.println("First file is shorter (only " + i + " lines)");
                System.exit(-1);
            }
        }
        catch (IOException ex3) {
            System.err.println("IO error while reading files");
            System.exit(-1);
        }
        try {
            bufferedReader.close();
            bufferedReader2.close();
        }
        catch (IOException ex4) {
            System.err.println("Could not close files");
            System.exit(-1);
        }
        System.out.println("Comparison ended successfully");
    }
    
    private static String removeSpaces(final String str) {
        int n = 0;
        int i;
        StringBuffer sb;
        for (i = 0, sb = new StringBuffer(str); i < sb.length(); ++i) {
            if (sb.charAt(i) != ' ') {
                if (n != i) {
                    sb.setCharAt(n, sb.charAt(i));
                }
                ++n;
            }
        }
        sb.setLength(n);
        return sb.toString().trim();
    }
}
