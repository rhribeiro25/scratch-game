package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WinningBonusVerifierImplTest {
    static LoadingConfig loadingConfig;
    static Config config;
    static WinningBonusVerifierImpl winningBonusVerifier;
    static String[][] matrix;
    static String[][] matrixMiss;

    @BeforeAll
    static void beforeAll() {
        loadingConfig = new LoadingConfigImpl();
        config = loadingConfig.setupConfigurations("src/main/resources/config_matrix[3][3].json");
        winningBonusVerifier = new WinningBonusVerifierImpl();
        matrix = new String[3][3];
        matrix[0][0] = "A";
        matrix[0][1] = "B";
        matrix[0][2] = "C";

        matrix[1][0] = "A";
        matrix[1][1] = "E";
        matrix[1][2] = "F";

        matrix[2][0] = "A";
        matrix[2][1] = "E";
        matrix[2][2] = "+500";

        matrixMiss = new String[3][3];
        matrixMiss[0][0] = "A";
        matrixMiss[0][1] = "B";
        matrixMiss[0][2] = "C";

        matrixMiss[1][0] = "A";
        matrixMiss[1][1] = "E";
        matrixMiss[1][2] = "F";

        matrixMiss[2][0] = "MISS";
        matrixMiss[2][1] = "E";
        matrixMiss[2][2] = "A";
    }

    @Test
    @DisplayName("verify matrix size and bonus symbol")
    public void verify() {
        String symbolPlus500 = winningBonusVerifier.verify(1, matrix, config);
        String symbolNull = winningBonusVerifier.verify(0, matrix, config);
        String symbolMiss = winningBonusVerifier.verify(0, matrixMiss, config);

        Assertions.assertEquals(symbolPlus500,"+500");
        Assertions.assertNull(symbolNull);
        Assertions.assertNull(symbolMiss);
    }
}
