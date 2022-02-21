package com.progressoft.tools.check.data.from.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ConvertAndManageData {

    public static int[] convertDataFromStringToInt(ArrayList<String> columNum) {
        return columNum.stream().mapToInt(Integer::parseInt).toArray();
    }

    public static int[] getDataColumnFromCsv(int index, String line, String[] number, BufferedReader bufferedReader) throws IOException {

        ArrayList<String> columNum = new ArrayList<String>();
        line = bufferedReader.readLine();

        while (line != null){
            while(line != null) {
                number = line.split(",");
                columNum.add(number[index]);
                line = bufferedReader.readLine();
            }
        }

        return convertDataFromStringToInt(columNum);
    }

    public static BigDecimal sqrt(BigDecimal x) {
        return BigDecimal.valueOf(StrictMath.sqrt(x.doubleValue()));
    }

    public static BigDecimal parseBigDecimal(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_EVEN);
    }
}
