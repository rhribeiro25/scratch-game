package br.com.rhribeiro25;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private Config config;
    private double betAmount;
    private String[][] matrix;
    private double reward;
    private Map<String, List<String>> appliedWinningCombinations;
    private String appliedBonusSymbol;

    public Game(Config config, double betAmount) {
        this.config = config;
        this.betAmount = betAmount;
        generateMatrix();
        this.reward = 0;
        this.appliedWinningCombinations = new HashMap<>();
        this.appliedBonusSymbol = null;
    }

    public void play() {
        calculateReward();
        applyWinningCombinations();
    }

    private void generateMatrix() {

        this.matrix = new String[config.getRows()][config.getColumns()];

        this.calculateStdSymbols();

        this.calculateBonusSymbols();
    }

    public void calculateBonusSymbols() {
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
        matrix[randomRow][randomColunm] = this.applyingSymbolProbability(sortedCellBonusSymbols);
    }

    public void calculateStdSymbols() {
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

                            matrix[row][column] = this.applyingSymbolProbability(sortedCellStdSymbols);
                    }
                );
            }
        }
    }

    public String applyingSymbolProbability(LinkedHashMap<String, Double> sortedCellProbabilities) {

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

    private void calculateReward() {

    }

    private void applyWinningCombinations() {

    }

    public Result getResult() {
        return new Result(reward, matrix, appliedWinningCombinations, appliedBonusSymbol);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public void setAppliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) {
        this.appliedWinningCombinations = appliedWinningCombinations;
    }

    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    public void setAppliedBonusSymbol(String appliedBonusSymbol) {
        this.appliedBonusSymbol = appliedBonusSymbol;
    }
}

