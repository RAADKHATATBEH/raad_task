package com.progressoft.tools.ScoringData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import com.progressoft.tools.ScoringSummary;
import com.progressoft.tools.check.data.from.user.ConvertAndManageData;

public class ScorData implements ScoringSummary {
    private final BigDecimal[] data;
    private BigDecimal[] sortedData;
    private BigDecimal StandardDeviation;
    private final BigDecimal lengthOfData;
    private BigDecimal average ;

    public ScorData(int[] data) {
        this.data = Arrays.stream(data).mapToObj(BigDecimal::new).toArray(BigDecimal[]::new);
        lengthOfData = new BigDecimal(data.length);
        this.sort(this.data);
    }

    @Override
    public BigDecimal mean() {
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal i : data) {
            sum = sum.add(i);
        }

        average=sum.divide(lengthOfData, RoundingMode.HALF_EVEN);
        return ConvertAndManageData.parseBigDecimal(average);
    }

    @Override
    public BigDecimal standardDeviation() {
        if(average == null)
            this.mean();

        BigDecimal sigma = new BigDecimal(0);
        for (BigDecimal i : data) {
            sigma = sigma.add((i.subtract(average)).pow(2));
        }

        StandardDeviation = sigma.divide(lengthOfData, RoundingMode.HALF_EVEN);
        StandardDeviation = ConvertAndManageData.sqrt(StandardDeviation);
        return ConvertAndManageData.parseBigDecimal(StandardDeviation);
    }

    @Override
    public BigDecimal variance() {
        return ConvertAndManageData.parseBigDecimal(StandardDeviation.pow(2));
    }

    @Override
    public BigDecimal median() {
        if(sortedData.length % 2 > 0)
            return ConvertAndManageData.parseBigDecimal( sortedData[sortedData.length / 2]);
        else
            return ConvertAndManageData.parseBigDecimal( sortedData[sortedData.length / 2].add(sortedData[sortedData.length / 2 + 1]).divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN));
    }

    @Override
    public BigDecimal min() {
        return ConvertAndManageData.parseBigDecimal(sortedData[0]);
    }

    @Override
    public BigDecimal max() {
        return ConvertAndManageData.parseBigDecimal(sortedData[sortedData.length-1]);
    }

    public void sort(BigDecimal[] sorted) {
        sortedData = Arrays.copyOf(data, data.length );
        Arrays.sort(sortedData);
    }


}

