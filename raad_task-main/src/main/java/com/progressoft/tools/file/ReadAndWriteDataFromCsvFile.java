package com.progressoft.tools.file;

import com.progressoft.tools.check.data.from.user.CheckDataIsValidOrInVaid;
import com.progressoft.tools.check.data.from.user.ConvertAndManageData;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReadAndWriteDataFromCsvFile {
    private String csvPath ;
    private String destPath;
    private String []number;
    private String line;
    private BufferedReader bufferedReader;
    private FileReader br;
    public ReadAndWriteDataFromCsvFile(String csvPath, String destPath) {
            this.destPath = destPath;
            this.csvPath = csvPath;
    }

    public int[] readDataFromCsv(String nameOfCoum) throws IOException {

      int index = readData(nameOfCoum);
        CheckDataIsValidOrInVaid.checkColumIsFoundOrNot(index,nameOfCoum);
        int []colum = ConvertAndManageData.getDataColumnFromCsv(index,line,number,bufferedReader);
        bufferedReader.close();
        br.close();
        return colum;
    }

    private int readData(String nameOfCoum) throws IOException {
        br = new FileReader(csvPath);
        bufferedReader = new BufferedReader(br);
        line = bufferedReader.readLine();
        number = line.split(",");
        int i = 0;
        int index=1;
        while (i <number.length){
            if(number[i].equals(nameOfCoum)) {
                index = i;
                break;
            }
            i++;
        }
        return index;
    }

    public  void writeDataINCsv(BigDecimal[] data, String targetCol, String suffix) {

        try {
            int sIndex =readData(targetCol);
            String []newTokens = new String[number.length + 1];


            int index = -1;
            for(int i = 0; i < number.length; i++) {
                newTokens[i + (index != -1 ? 1 : 0)] = number[i];
                if(number[i].equals(targetCol)) {
                    index = i;
                    newTokens[i + 1] = number[i] + suffix;
                }
            }
            String newLines = String.join(",", newTokens) + "\n";
            line = bufferedReader.readLine();

            int dataIndex = 0;
            while(line != null) {
                number = line.split(",");
                boolean foundInValues = false;
                for(int i = 0; i < number.length; i++) {
                    newTokens[i + (foundInValues ? 1 : 0)] = number[i];
                    if(i == index) {
                        foundInValues = true;
                        newTokens[i + 1] = data[dataIndex++].setScale(2, RoundingMode.HALF_EVEN).toString();
                    }
                }
                newLines = newLines.concat(String.join(",", newTokens) + "\n");
                line = bufferedReader.readLine();
            }
            FileWriter writer = new FileWriter(destPath);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(newLines);
            bufferWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}