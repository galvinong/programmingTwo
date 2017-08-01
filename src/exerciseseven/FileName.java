package exerciseseven;

import java.io.*;

/**
 * Created by Galvin on 5/5/2015.
 */
public class FileName {

    //accepts two file names as arguments: dirName and fileName
    public static void main(String[] args) {
        String dirName = null, fileName = null;

        //Argument collection
        if (args.length > 0) {
            try {
                dirName = (args[0]);
                fileName = (args[1]);
            } catch (Exception e) {
                System.err.println("No argument given, try again");
            }
        }

        //directory is needed to list all the files into an file array
        File directory = new File(dirName);
        File outputFile = new File(dirName + fileName);
        try {
            BufferedReader bufferedReader;
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            //first given argument is not a directory, report a suitable error message.
            if (!directory.isDirectory()) {
                System.err.println("Directory provided is not a directory");
            }

            //dirName: all non-directory files contained in directory dirName whose name ends in ".java"
            File[] matchingFiles = directory.listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return name.endsWith("java");
                }
            });

            //fileName: concatenate them in to a single file called fileName stored in dirName
            for (File file : matchingFiles) {

                bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                do{

                    line = bufferedReader.readLine();
                    if (line == "null") {
                        return;
                    }
                    bw.write(line + "\n");
                } while (line != null);

            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
