package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.Arrays;

public class WinningBonusVerifierImpl implements WinningBonusVerifier {

    public String verify(int winningCombinationsSize, String[][] matrix, Config config){
        String bonusSymbol = Arrays.stream(matrix)
                .flatMap(Arrays::stream)
                .filter(config.getProbabilities().getBonusSymbols().getSymbols()::containsKey)
                .findFirst().orElse(null);
        if(winningCombinationsSize == 0 || "MISS".equals(bonusSymbol))
            return null;
        return bonusSymbol;
    }

}
