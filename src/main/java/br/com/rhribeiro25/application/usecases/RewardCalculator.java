package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.List;
import java.util.Map;

public interface RewardCalculator {
    //Applying SOLID
    double calculate(double betAmount, Map<String, List<String>> winningCombinations, String appliedBonusSymbol, Config config);
}
