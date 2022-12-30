package JavaFileExtractor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class JavaFileExtractor {
    public void extract(String projectPath, String extension, String outputPath) {
        long beforeTime = System.currentTimeMillis();
        File dir = new File(projectPath);
        String pattern = ".*\\." + extension;
        Collection files = FileUtils.listFiles(
                dir,
                new RegexFileFilter(pattern),
                DirectoryFileFilter.DIRECTORY
        );

        Iterator fileIterator = files.iterator();
        StringBuilder fileList = new StringBuilder();
        int cnt = 0;
        while(fileIterator.hasNext() == true){
            cnt ++;
            String n = fileIterator.next().toString();
            fileList.append(n);
            fileList.append("\n");

            if(cnt % 1000 == 0){
                writeOutput(fileList, outputPath);
                fileList.setLength(0);
            }
        }

        if (cnt % 1000 != 0){
            writeOutput(fileList, outputPath);
            fileList.setLength(0);
        }

        long afterTime = System.currentTimeMillis();
        long time = (afterTime - beforeTime)/1000;

        System.out.println("Number of files: " + cnt);
        System.out.println("The whole process took: " + time + "sec.");
    }

    private void writeOutput(StringBuilder fileList, String outputPath){
        BufferedWriter bw = null;
        try {
            File out = new File(outputPath);
            if (!out.exists()) {
                out.createNewFile();
            }
            FileWriter fw = new FileWriter(out, true);
            bw = new BufferedWriter(fw);
            bw.write(fileList.toString());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            try {
                if(bw!=null)
                    bw.close();
            } catch(Exception ex){
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
    }
}
