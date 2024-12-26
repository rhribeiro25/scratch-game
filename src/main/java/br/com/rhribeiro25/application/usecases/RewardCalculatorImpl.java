package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import java.util.List;
import java.util.Map;

public class RewardCalculatorImpl implements RewardCalculator {

    @Override
    public double calculate(double betAmount, Map<String, List<String>> winningCombinations, String bonusSymbol, Config config) {
        double reward = 0;
        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();
            double rewardBySymbol = betAmount * config.getSymbols().get(symbol).getRewardMultiplier();
            for (String combination : combinations) {
                rewardBySymbol *= config.getWinCombinations().get(combination).getRewardMultiplier();
            }
            reward += rewardBySymbol;
        }
        return applyBonusSymbol(reward, bonusSymbol, config);
    }

    private double applyBonusSymbol(double reward, String bonusSymbol, Config config) {
        if(bonusSymbol != null) {
            for(String symbol : config.getSymbols().keySet()) {
                String impact = config.getSymbols().get(symbol).getImpact();
                Double rewardMultiplier = config.getSymbols().get(symbol).getRewardMultiplier();
                Integer extra = config.getSymbols().get(symbol).getExtra();
                if(symbol.equals(bonusSymbol)) {
                    if ("multiply_reward".equals(impact))
                        return reward * rewardMultiplier;
                    if ("extra_bonus".equals(impact))
                        return reward + extra;
                }
            }
        }
        return reward;
    }
}

