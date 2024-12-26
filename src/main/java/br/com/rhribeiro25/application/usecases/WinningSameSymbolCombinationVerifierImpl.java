package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.*;

public class WinningSameSymbolCombinationVerifierImpl implements WinningCombinationVerifier {

    @Override
    public void verify(String[][] matrix, Config config, Map<String, List<String>> winningCombinations) {

        for(String symbol : config.getSymbols().keySet()){
            long countBySymbol = Arrays.stream(matrix)
                    .flatMap(Arrays::stream)
                    .filter(value -> value.equals(symbol))
                    .count();
            for(String combinationKey : config.getWinCombinations().keySet()){

                if(config.getWinCombinations().get(combinationKey).getWhen().equals("same_symbols")
                        && config.getWinCombinations().get(combinationKey).getCount() == countBySymbol){
                    winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(combinationKey);
                }
            }
        }
    }
}
