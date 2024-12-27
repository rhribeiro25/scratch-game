package br.com.rhribeiro25.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Result {

    Double reward;
    String[][] matrix;
    private Map<String, List<String>> appliedWinningCombinations;
    private String appliedBonusSymbol;

    public Result(Double reward, String[][] matrix, Map<String, List<String>> appliedWinningCombinations, String appliedBonusSymbol) {
        this.reward = reward;
        this.matrix = matrix;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    @Override
    public String toString() {
        return "{" +
                matrixToString() + ", " +
                rewardToString() +
                appliedWinningCombinationsToString() +
                appliedBonusSymbolToString() +
                " </br> }";
    }

    private String matrixToString(){
        StringBuilder matrixFormated = new StringBuilder(" </br> \"matrix\": [");
        for (int i = 0; i < matrix.length; i++) {
            matrixFormated.append(i != 0 ? "]," : "").append(" </br> [");
            for (int j = 0; j < matrix[0].length; j++) {
                matrixFormated.append("\"").append(matrix[i][j]).append("\"").append(j != matrix[0].length-1 ? ", " : "");
            }
        }
        matrixFormated.append("] </br> ]");
        return matrixFormated.toString();
    }

    private String rewardToString(){
        String end = "";
        if(!appliedWinningCombinations.isEmpty())
            end = ", ";
        if(reward == 0)
            return "</br> \"reward\": 0" + end;
        else
            return "</br> \"reward\": " + reward + end;
    }

    private String appliedWinningCombinationsToString(){
        if(appliedWinningCombinations.isEmpty())
            return "";
        else {
            StringBuilder winningCombinationsFormated = new StringBuilder("</br> \"applied_winning_combinations\": {");
            Set<String> keys = appliedWinningCombinations.keySet();
            int count = 0;
            for (String key : keys) {
                winningCombinationsFormated.append(count != 0 ? "], " : "").append("</br> ").append("\"")
                        .append(key).append("\"").append(": [");
                count++;
                for (int j = 0; j < appliedWinningCombinations.get(key).size(); j++) {
                    winningCombinationsFormated.append("\"").append(appliedWinningCombinations.get(key).get(j)).append("\"")
                            .append(j != appliedWinningCombinations.get(key).size() - 1 ? ", " : "");
                }
            }
            winningCombinationsFormated.append("] </br> }").append(appliedBonusSymbol != null ? ", " : "");
            return winningCombinationsFormated.toString();
        }
    }

    private String appliedBonusSymbolToString(){
        if(appliedBonusSymbol != null && !appliedWinningCombinations.isEmpty())
            return "</br> \"applied_bonus_symbol\": " + appliedBonusSymbol;
        else
            return "";
    }

    public static class ResultBuilder {
        private Double reward;
        private String[][] matrix;
        private Map<String, List<String>> appliedWinningCombinations;
        private String appliedBonusSymbol;

        public ResultBuilder reward(Double reward) {
            this.reward = reward;
            return this;
        }

        public ResultBuilder matrix(String[][] matrix) {
            this.matrix = matrix;
            return this;
        }

        public ResultBuilder appliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) {
            this.appliedWinningCombinations = appliedWinningCombinations;
            return this;
        }

        public ResultBuilder appliedBonusSymbol(String appliedBonusSymbol) {
            this.appliedBonusSymbol = appliedBonusSymbol;
            return this;
        }

        public Result build() {
            return new Result(reward, matrix, appliedWinningCombinations, appliedBonusSymbol);
        }
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
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
