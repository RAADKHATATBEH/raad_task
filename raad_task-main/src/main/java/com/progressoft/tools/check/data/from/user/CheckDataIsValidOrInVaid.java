package com.progressoft.tools.check.data.from.user;

import com.progressoft.tools.file.ReadAndWriteDataFromCsvFile;

import java.io.IOException;
import java.nio.file.Path;

public class CheckDataIsValidOrInVaid {
   static ReadAndWriteDataFromCsvFile readAndWriteDataFromCsvFile;
   static int data[];
    public static int[] sendDataToread(Path csvPath, Path destPath, String colToNormalize) {

        try {
            readAndWriteDataFromCsvFile =new ReadAndWriteDataFromCsvFile(csvPath.toString(), destPath.toString());
            data = readAndWriteDataFromCsvFile.readDataFromCsv(colToNormalize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void check(Path csvPath, Path destPath, String colToNormalize) {
        if(csvPath == null ||destPath == null||colToNormalize == null)
            throw new IllegalArgumentException("source file not found");

    }

    public static void checkColumIsFoundOrNot(int index,String nameOfCoum) {
        if(index  == 1) {
            throw new IllegalArgumentException("column "+ nameOfCoum +" not found");
        }
    }


}
