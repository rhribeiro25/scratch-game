package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardCalculatorImplTest {
    static LoadingConfig loadingConfig;
    static Config config;
    static RewardCalculatorImpl rewardCalculator;
    static Map<String, List<String>> winningCombinations1;
    static Map<String, List<String>> winningCombinations2;
    static Map<String, List<String>> winningCombinations3;

    @BeforeAll
    static void beforeAll() {
        loadingConfig = new LoadingConfigImpl();
        config = loadingConfig.setupConfigurations("src/main/resources/config_matrix[3][3].json");
        rewardCalculator = new RewardCalculatorImpl();

        winningCombinations1 = new HashMap<>();
        winningCombinations1.put("F", Arrays.asList("same_symbols_vertically", "same_symbols_horizontally", "same_symbols_diagonally_left_to_right", "same_symbols_diagonally_right_to_left"));

        winningCombinations2 = new HashMap<>();
        winningCombinations2.put("F", Arrays.asList("same_symbols_vertically", "same_symbols_diagonally_right_to_left"));
        winningCombinations2.put("C", Arrays.asList("same_symbol_6_times", "same_symbols_diagonally_left_to_right"));

        winningCombinations3 = new HashMap<>();
        winningCombinations3.put("A", Arrays.asList("same_symbol_4_times", "same_symbols_diagonally_left_to_right"));
        winningCombinations3.put("D", Arrays.asList("same_symbol_3_times", "same_symbols_horizontally"));

    }

    @Test
    @DisplayName("reward calculator with different rules")
    public void generate() {
        Double rewardMISS = rewardCalculator.calculate(10, winningCombinations1, null, config);
        Double rewardPlus500 = rewardCalculator.calculate(10, winningCombinations2, "+500", config);
        Double rewardMultiple10 = rewardCalculator.calculate(10, winningCombinations3, "10x", config);

        Assertions.assertEquals(rewardMISS, 1000);
        Assertions.assertEquals(rewardPlus500,975);
        Assertions.assertEquals(rewardMultiple10,775);
    }
}
