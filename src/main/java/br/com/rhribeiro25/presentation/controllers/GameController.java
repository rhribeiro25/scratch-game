package br.com.rhribeiro25.presentation.controllers;

import br.com.rhribeiro25.application.usecases.*;
import br.com.rhribeiro25.domain.Config;
import br.com.rhribeiro25.domain.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    private final Config config;

    private final LoadingConfig loadingConfig;
    private final MatrixGenerator matrixGenerator;
    private final WinningCombinationVerifier winningCombinationVerifier;
    private final WinningBonusVerifier winningBonusVerifier;
    private final RewardCalculator rewardCalculator;

    private String[][] matrix;
    private double reward;
    private Map<String, List<String>> winningCombinations;
    private String bonusSymbol;

    public GameController(String configPath) {
        this.loadingConfig = new LoadingConfigImpl();
        this.matrixGenerator = new MatrixGeneratorImpl();
        this.winningCombinationVerifier = new WinningCombinationVerifierImpl();
        this.rewardCalculator = new RewardCalculatorImpl();
        this.winningBonusVerifier = new WinningBonusVerifierImpl();
        this.config = loadingConfig.loadingConfig(configPath);
        winningCombinations = new HashMap<>();
    }

    public void play(double betAmount) {
        matrix = matrixGenerator.generate(config);
        winningCombinations = winningCombinationVerifier.verify(matrix, config);
        bonusSymbol = winningBonusVerifier.verify(winningCombinations.size(), matrix, config);
        reward = rewardCalculator.calculate(betAmount, winningCombinations, bonusSymbol, config);
    }

    public Result getResult() {
        return new Result(reward, matrix, winningCombinations, bonusSymbol);
    }
}
