package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.*;

public class WinningHorizontallyCombinationVerifierImpl implements WinningCombinationVerifier {

    @Override
    public void verify(String[][] matrix, Config config, Map<String, List<String>> winningCombinations) {

        for(String combinationKey : config.getWinCombinations().keySet()) {
            if (config.getWinCombinations().get(combinationKey).getGroup().equals("horizontally_linear_symbols")) {
                for(String symbol : config.getSymbols().keySet()){
                    for(List<String> coveredAreas : config.getWinCombinations().get(combinationKey).getCoveredAreas()){
                        int count = 0;
                        for(String areas : coveredAreas){
                            String[] coordinates = areas.split(":");
                            int row = Integer.parseInt(coordinates[0]);
                            int column = Integer.parseInt(coordinates[1]);
                            if(matrix[row][column].equals(symbol)){
                                count++;
                                if(count == config.getColumns()){
                                    if(winningCombinations.get(symbol) == null)
                                        winningCombinations.put(symbol, new ArrayList<>());
                                    if(!winningCombinations.get(symbol).contains(combinationKey))
                                        winningCombinations.get(symbol).add(combinationKey);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
