package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatrixGeneratorImplTest {

    static LoadingConfig loadingConfig;
    static Config config;
    static MatrixGenerator matrixGenerator;
    static String[][] matrix;

    @BeforeAll
    static void beforeAll() {
        loadingConfig = new LoadingConfigImpl();
        config = loadingConfig.setupConfigurations("src/main/resources/config_matrix[3][3].json");
        matrixGenerator = new MatrixGeneratorImpl();
        matrix = new String[config.getRows()][config.getColumns()];
    }

    @Test
    @DisplayName("verify matrix size and bonus symbol")
    public void generate() {
        matrix = matrixGenerator.generate(config);
        int matrixSize = matrix.length * matrix[0].length;
        int countBonusSymbols = 0;
        for (String[] rows : matrix){
            for(String cell : rows){
                if(config.getProbabilities().getBonusSymbols().getSymbols().containsKey(cell))
                    countBonusSymbols++;
            }
        }
        Assertions.assertEquals(matrixSize, 9);
        Assertions.assertEquals(countBonusSymbols, 1);
    }

}

