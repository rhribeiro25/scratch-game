package br.com.rhribeiro25;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

public class Result {

    Double reward;
    String[][] matrix;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, List<String>> appliedWinningCombinations;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String appliedBonusSymbol;

    public Result(Double reward, String[][] matrix) {
        this.reward = reward;
        this.matrix = matrix;
    }

    public Result(Double reward, String[][] matrix, Map<String, List<String>> appliedWinningCombinations, String appliedBonusSymbol) {
        this.reward = reward;
        this.matrix = matrix;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
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
