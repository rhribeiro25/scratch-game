package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningVerticallyCombinationVerifierImplTest {
    static LoadingConfig loadingConfig;
    static Config config;
    static WinningVerticallyCombinationVerifierImpl winningVerticallyCombinationVerifier;
    static String[][] matrix;
    static Map<String, List<String>> winningCombinations;

    @BeforeAll
    static void beforeAll() {
        loadingConfig = new LoadingConfigImpl();
        config = loadingConfig.setupConfigurations("src/main/resources/config_matrix[3][3].json");
        winningVerticallyCombinationVerifier = new WinningVerticallyCombinationVerifierImpl();
        winningCombinations = new HashMap<>();

        matrix = new String[3][3];
        matrix[0][0] = "A";
        matrix[0][1] = "B";
        matrix[0][2] = "C";

        matrix[1][0] = "A";
        matrix[1][1] = "E";
        matrix[1][2] = "F";

        matrix[2][0] = "A";
        matrix[2][1] = "E";
        matrix[2][2] = "MISS";
    }

    @Test
    @DisplayName("winning vertically combination verifier")
    public void verify() {
        winningVerticallyCombinationVerifier.verify(matrix, config, winningCombinations);
        Assertions.assertTrue(winningCombinations.get("A").contains("same_symbols_vertically"));
    }
}
