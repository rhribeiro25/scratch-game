package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningSameSymbolCombinationVerifierImplTest {
    static LoadingConfig loadingConfig;
    static Config config;
    static WinningSameSymbolCombinationVerifierImpl winningSameSymbolCombinationVerifier;
    static String[][] matrix;
    static Map<String, List<String>> winningCombinations;

    @BeforeAll
    static void beforeAll() {
        loadingConfig = new LoadingConfigImpl();
        config = loadingConfig.setupConfigurations("src/main/resources/config_matrix[3][3].json");
        winningSameSymbolCombinationVerifier = new WinningSameSymbolCombinationVerifierImpl();
        winningCombinations = new HashMap<>();

        matrix = new String[7][7];
        matrix[0][0] = "A";
        matrix[0][1] = "A";
        matrix[0][2] = "A";
        matrix[0][3] = "B";
        matrix[0][4] = "B";
        matrix[0][5] = "F";
        matrix[0][6] = "F";

        matrix[1][0] = "B";
        matrix[1][1] = "B";
        matrix[1][2] = "C";
        matrix[1][3] = "C";
        matrix[1][4] = "C";
        matrix[1][5] = "F";
        matrix[1][6] = "F";

        matrix[2][0] = "C";
        matrix[2][1] = "C";
        matrix[2][2] = "E";
        matrix[2][3] = "D";
        matrix[2][4] = "D";
        matrix[2][5] = "F";
        matrix[2][6] = "F";

        matrix[3][0] = "D";
        matrix[3][1] = "D";
        matrix[3][2] = "D";
        matrix[3][3] = "D";
        matrix[3][4] = "E";
        matrix[3][5] = "F";
        matrix[3][6] = "F";

        matrix[4][0] = "E";
        matrix[4][1] = "E";
        matrix[4][2] = "E";
        matrix[4][3] = "E";
        matrix[4][4] = "E";
        matrix[4][5] = "MISS";
        matrix[4][6] = "MISS";

        matrix[5][0] = "MISS";
        matrix[5][1] = "MISS";
        matrix[5][2] = "MISS";
        matrix[5][3] = "MISS";
        matrix[5][4] = "MISS";
        matrix[5][5] = "MISS";
        matrix[5][6] = "MISS";

        matrix[6][0] = "";
        matrix[6][1] = "";
        matrix[6][2] = "";
        matrix[6][3] = "";
        matrix[6][4] = "";
        matrix[6][5] = "";
        matrix[6][6] = "";
    }

    @Test
    @DisplayName("winning same symbol combination verifier")
    public void verify() {
        winningSameSymbolCombinationVerifier.verify(matrix, config, winningCombinations);
        Assertions.assertTrue(winningCombinations.get("A").contains("same_symbol_3_times"));
        Assertions.assertTrue(winningCombinations.get("B").contains("same_symbol_4_times"));
        Assertions.assertTrue(winningCombinations.get("C").contains("same_symbol_5_times"));
        Assertions.assertTrue(winningCombinations.get("D").contains("same_symbol_6_times"));
        Assertions.assertTrue(winningCombinations.get("E").contains("same_symbol_7_times"));
        Assertions.assertTrue(winningCombinations.get("F").contains("same_symbol_8_times"));
        Assertions.assertTrue(winningCombinations.get("MISS").contains("same_symbol_9_times"));
    }
}
