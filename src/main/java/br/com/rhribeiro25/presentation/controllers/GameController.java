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
    private final WinningCombinationVerifier winningSameSymbolCombinationVerifier;
    private final WinningCombinationVerifier winningVerticallyCombinationVerifier;
    private final WinningCombinationVerifier winningHorizontallyCombinationVerifier;
    private final WinningCombinationVerifier winningDiagonallyLeftToRightCombinationVerifier;
    private final WinningCombinationVerifier winningDiagonallyRightToLeftCombinationVerifier;
    private final WinningBonusVerifier winningBonusVerifier;
    private final RewardCalculator rewardCalculator;

    private String[][] matrix;
    private double reward;
    private Map<String, List<String>> winningCombinations;
    private String bonusSymbol;

    public GameController(String configPath) {
        this.loadingConfig = new LoadingConfigImpl();
        this.matrixGenerator = new MatrixGeneratorImpl();
        this.winningSameSymbolCombinationVerifier = new WinningSameSymbolCombinationVerifierImpl();
        this.winningVerticallyCombinationVerifier = new WinningVerticallyCombinationVerifierImpl();
        this.winningHorizontallyCombinationVerifier = new WinningHorizontallyCombinationVerifierImpl();
        this.winningDiagonallyLeftToRightCombinationVerifier = new WinningDiagonallyLeftToRightCombinationVerifierImpl();
        this.winningDiagonallyRightToLeftCombinationVerifier = new WinningDiagonallyRightToLeftCombinationVerifierImpl();
        this.rewardCalculator = new RewardCalculatorImpl();
        this.winningBonusVerifier = new WinningBonusVerifierImpl();
        this.config = loadingConfig.setupConfigurations(configPath);
        winningCombinations = new HashMap<>();
    }

    public void play(double betAmount) {
        matrix = matrixGenerator.generate(config);
        winningSameSymbolCombinationVerifier.verify(matrix, config, winningCombinations);
        winningVerticallyCombinationVerifier.verify(matrix, config, winningCombinations);
        winningHorizontallyCombinationVerifier.verify(matrix, config, winningCombinations);
        winningDiagonallyLeftToRightCombinationVerifier.verify(matrix, config, winningCombinations);
        winningDiagonallyRightToLeftCombinationVerifier.verify(matrix, config, winningCombinations);
        bonusSymbol = winningBonusVerifier.verify(winningCombinations.size(), matrix, config);
        reward = rewardCalculator.calculate(betAmount, winningCombinations, bonusSymbol, config);
    }

    public Result getResult() {
        return new Result.ResultBuilder()
            .reward(reward)
            .matrix(matrix)
            .appliedWinningCombinations(winningCombinations)
            .appliedBonusSymbol(bonusSymbol)
            .build();
    }
}
