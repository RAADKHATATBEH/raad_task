package com.progressoft.tools.NormalizerData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import com.progressoft.tools.Normalizer;
import com.progressoft.tools.ScoringSummary;
import com.progressoft.tools.ScoringData.ScorData;
import com.progressoft.tools.check.data.from.user.CheckDataIsValidOrInVaid;
import com.progressoft.tools.file.ReadAndWriteDataFromCsvFile;

public class NormalizerData implements Normalizer {

	ReadAndWriteDataFromCsvFile readAndWriteDataFromCsvFile;
    private int[] data;
    private BigDecimal[] standardizedData;
    private BigDecimal num;

	@Override
	public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {
        CheckDataIsValidOrInVaid.check(csvPath,destPath,colToStandardize);
        data = CheckDataIsValidOrInVaid.sendDataToread(csvPath, destPath, colToStandardize);
        ScorData summary = new ScorData(data);
         standardizedData = new BigDecimal[data.length];
        for(int i = 0; i < data.length; i++) {
             num = new BigDecimal(data[i]);
            standardizedData[i] = num.subtract(summary.mean()).
                    divide(summary.standardDeviation(), RoundingMode.HALF_EVEN).
                    setScale(2, RoundingMode.HALF_EVEN);
        }
        addNewColumNormalizedDataOrStandardizedDataInCsv(standardizedData, colToStandardize, "_z",csvPath,destPath);
        return summary;
	}

	@Override
	public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {
        CheckDataIsValidOrInVaid.check(csvPath,destPath,colToNormalize);
        data = CheckDataIsValidOrInVaid.sendDataToread(csvPath, destPath, colToNormalize);
        ScorData summary = new ScorData(data);
        BigDecimal[] normalizedData = new BigDecimal[data.length];
        for(int i = 0; i < data.length; i++) {
            BigDecimal number = new BigDecimal(data[i]);
            normalizedData[i] = number.subtract(summary.min()).divide(summary.max().subtract(summary.min()), RoundingMode.HALF_EVEN);
        }
        addNewColumNormalizedDataOrStandardizedDataInCsv(normalizedData, colToNormalize, "_mm",csvPath,destPath);
        return summary;
	}

    private void addNewColumNormalizedDataOrStandardizedDataInCsv(BigDecimal[] normalizedData, String colToNormalize, String mm,Path csvPath, Path destPath) {
        readAndWriteDataFromCsvFile = new ReadAndWriteDataFromCsvFile(csvPath.toString(),destPath.toString());
        if (mm == "_mm")
	    readAndWriteDataFromCsvFile.writeDataINCsv(normalizedData, colToNormalize, "_mm");
        else
            readAndWriteDataFromCsvFile.writeDataINCsv(normalizedData, colToNormalize, "_z");

    }


}
