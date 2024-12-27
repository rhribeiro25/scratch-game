package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardCalculatorImpl implements RewardCalculator {

    @Override
    public double calculate(double betAmount, Map<String, List<String>> winningCombinations, String bonusSymbol, Config config) {
        List<Double> rewardBySymbolList = new ArrayList<>();
        double rewardBySymbol = 0;
        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();
            rewardBySymbol = betAmount * config.getSymbols().get(symbol).getRewardMultiplier();
            for (String combination : combinations) {
                rewardBySymbol *= config.getWinCombinations().get(combination).getRewardMultiplier();
            }
            rewardBySymbolList.add(rewardBySymbol);
        }
        return applyBonusSymbol(rewardBySymbolList, bonusSymbol, config);
    }

    private double applyBonusSymbol(List<Double> rewardList, String bonusSymbol, Config config) {
        double reward = 0;
        if(bonusSymbol != null) {
            for(String symbol : config.getSymbols().keySet()) {
                String impact = config.getSymbols().get(symbol).getImpact();
                Double rewardMultiplier = config.getSymbols().get(symbol).getRewardMultiplier();
                Integer extra = config.getSymbols().get(symbol).getExtra();
                int lastPosition = rewardList.size() - 1;
                if(symbol.equals(bonusSymbol)) {
                    if ("multiply_reward".equals(impact)) {
                        reward = rewardMultiplier * rewardList.get(lastPosition);
                        rewardList.remove(lastPosition);
                        reward += rewardList.stream().mapToDouble(Double::doubleValue).sum();
                    }
                    if ("extra_bonus".equals(impact)) {
                        reward = extra;
                        reward += rewardList.stream().mapToDouble(Double::doubleValue).sum();
                    }
                }
            }
        }
        else
            reward += rewardList.stream().mapToDouble(Double::doubleValue).sum();
        return reward;
    }
}

