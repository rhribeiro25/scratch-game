package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixGeneratorImpl implements MatrixGenerator {

    @Override
    public String[][] generate(Config config) {
        String[][] matrix = new String[config.getRows()][config.getColumns()];
        applyStandardSymbolProbabilities(matrix,config);
        applyBonusSymbol(matrix, config);
        return matrix;
    }

    private void applyStandardSymbolProbabilities(String[][] matrix, Config config) {
        Map<String, Double> cellStdSymbols = new HashMap<>();

        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                int row = i;
                int column = j;

                config.getProbabilities().getStandardSymbols().stream()
                    .filter(stdSymbol -> stdSymbol.getRow() == row && stdSymbol.getColumn() == column)
                    .findFirst()
                    .ifPresent(stdSymbol -> {
                        double totalWeightStdSymbol = stdSymbol.getSymbols().values().stream()
                                .mapToDouble(Integer::intValue)
                                .sum();

                        if (totalWeightStdSymbol == 0) {
                            throw new IllegalArgumentException("Total weight can not be zero for cell (" + row + ", " + column + ")");
                        }

                        for(String symbol : stdSymbol.getSymbols().keySet()) {
                            cellStdSymbols.put(symbol, (stdSymbol.getSymbols().get(symbol)/totalWeightStdSymbol));
                        }
                        LinkedHashMap<String, Double> sortedCellStdSymbols = cellStdSymbols.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (e1, e2) -> e1,
                                    LinkedHashMap::new
                            ));

                        matrix[row][column] = this.applySymbolProbabilityByCell(sortedCellStdSymbols);
                    }
                );
            }
        }
    }

    private void applyBonusSymbol(String[][] matrix, Config config) {
        Map<String, Double> cellBonusSymbols = new HashMap<>();

        double totalWeightBonusSymbol = config.getProbabilities().getBonusSymbols().getSymbols().values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        if (totalWeightBonusSymbol == 0) {
            throw new IllegalArgumentException("Total weight can not be zero for bonus symbols!");
        }

        for(String symbol : config.getProbabilities().getBonusSymbols().getSymbols().keySet()) {
            cellBonusSymbols.put(symbol, (config.getProbabilities().getBonusSymbols().getSymbols().get(symbol)/totalWeightBonusSymbol));
        }
        LinkedHashMap<String, Double> sortedCellBonusSymbols = cellBonusSymbols.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        Random rand = new Random();
        int randomRow = rand.nextInt(config.getRows());
        int randomColunm = rand.nextInt(config.getColumns());

        matrix[randomRow][randomColunm] = this.applySymbolProbabilityByCell(sortedCellBonusSymbols);
    }

    public String applySymbolProbabilityByCell(LinkedHashMap<String, Double> sortedCellProbabilities) {

        Random rand = new Random();
        double randomValue = rand.nextDouble(); // Value between 0.0 and 1.0 (0% and 100%)

        Double lastValue = 0.0;
        int count = 0;
        for(String key : sortedCellProbabilities.keySet()){
            if(count == 0 && randomValue < sortedCellProbabilities.get(key)){
                return key;
            }
            else if(count > 0 && randomValue < (lastValue + sortedCellProbabilities.get(key))){
                return key;
            }
            lastValue += sortedCellProbabilities.get(key);
            count++;
        }
        return "";
    }
}
