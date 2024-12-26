package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.List;
import java.util.Map;

public class RewardCalculatorImpl implements RewardCalculator {
    private final Config config;

    public RewardCalculatorImpl(Config config) {
        this.config = config;
    }

    @Override
    public double calculate(double betAmount, Map<String, List<String>> winningCombinations, String bonusSymbol) {
        double reward = 0;
        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();

            double symbolReward = betAmount * config.getSymbols().get(symbol).getRewardMultiplier();
            for (String combination : combinations) {
                symbolReward *= config.getWinCombinations().get(combination).getRewardMultiplier();
            }
            reward += applyBonus(symbolReward, bonusSymbol);
        }
        return reward;
    }

    private double applyBonus(double reward, String bonusSymbol) {
        if(bonusSymbol != null) {
            if (bonusSymbol.equals("10x"))
                reward *= 10;
            if (bonusSymbol.equals("5x"))
                reward *= 5;
            if (bonusSymbol.equals("+1000"))
                reward += 1000;
            if (bonusSymbol.equals("+500"))
                reward += 500;
        }
        return reward;
    }
}

