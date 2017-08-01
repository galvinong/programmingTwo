package exerciseeight;

import java.io.*;

/**
 * Created by Galvin on 5/6/2015.
 */
public class FileWrite {

    FileOutputStream fileOutputStream = null;
    FileWriter fileWriter = null;
    int[] intByte = new int[1000];
    int[] intChar = new int[1000];
    int i = 0;
    String checkFileName;

    public void writeByte(Integer num, String fileName) {
        checkFileName = fileName;
        try {
            fileOutputStream = new FileOutputStream(new File(fileName));
            Writer out = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
            out.write(num.toString());
//            intByte[i] = num;
            out.flush();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void writeChar(Integer num, String filename) {
        checkFileName = filename;
        try {
            fileWriter = new FileWriter(new File(filename));
            fileWriter.write(num.toString());
//            intChar[i] = num;
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] getIntByte() {
        return intByte;
    }

    public int[] getIntChar() {
        return intChar;
    }


    public String getFileName() {
        return checkFileName;
    }
}
