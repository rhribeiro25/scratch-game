package br.com.rhribeiro25.infra.controller;

import br.com.rhribeiro25.application.usecases.*;
import br.com.rhribeiro25.domain.Config;
import br.com.rhribeiro25.domain.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    private final MatrixGenerator matrixGenerator;
    private final WinningCombinationVerifier winningCombinationVerifier;
    private final WinningBonusVerifier winningBonusVerifier;
    private final RewardCalculator rewardCalculator;

    private String[][] matrix;
    private double reward;
    private Map<String, List<String>> winningCombinations;
    private String bonusSymbol;

    public GameController(Config config) {
        this.matrixGenerator = new MatrixGeneratorImpl(config);
        this.winningCombinationVerifier = new WinningCombinationVerifierImpl(config);
        this.rewardCalculator = new RewardCalculatorImpl(config);
        this.winningBonusVerifier = new WinningBonusVerifierImpl(config);
        winningCombinations = new HashMap<>();
    }

    public void play(double betAmount) {
        matrix = matrixGenerator.generate();
        winningCombinations = winningCombinationVerifier.verify(matrix);
        bonusSymbol = winningBonusVerifier.verify(winningCombinations.size(), matrix);
        reward = rewardCalculator.calculate(betAmount, winningCombinations, bonusSymbol);
    }

    public Result getResult() {
        return new Result(reward, matrix, winningCombinations, bonusSymbol);
    }
}
