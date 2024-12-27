package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningDiagonallyRightToLeftCombinationVerifierImplTest {
    static LoadingConfig loadingConfig;
    static Config config;
    static WinningDiagonallyRightToLeftCombinationVerifierImpl winningDiagonallyRightToLeftCombinationVerifier;
    static String[][] matrix;
    static Map<String, List<String>> winningCombinations;

    @BeforeAll
    static void beforeAll() {
        loadingConfig = new LoadingConfigImpl();
        config = loadingConfig.setupConfigurations("src/main/resources/config_matrix[3][3].json");
        winningDiagonallyRightToLeftCombinationVerifier = new WinningDiagonallyRightToLeftCombinationVerifierImpl();
        winningCombinations = new HashMap<>();

        matrix = new String[3][3];
        matrix[0][0] = "A";
        matrix[0][1] = "B";
        matrix[0][2] = "C";

        matrix[1][0] = "F";
        matrix[1][1] = "C";
        matrix[1][2] = "F";

        matrix[2][0] = "C";
        matrix[2][1] = "B";
        matrix[2][2] = "MISS";
    }

    @Test
    @DisplayName("winning diagonally right to left combination verifier")
    public void verify() {
        winningDiagonallyRightToLeftCombinationVerifier.verify(matrix, config, winningCombinations);
        Assertions.assertTrue(winningCombinations.get("C").contains("same_symbols_diagonally_right_to_left"));
    }
}
