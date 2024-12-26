package br.com.rhribeiro25.application.usecases;

import java.util.List;
import java.util.Map;

public interface RewardCalculator {
    double calculate(double betAmount, Map<String, List<String>> winningCombinations, String appliedBonusSymbol);
}
