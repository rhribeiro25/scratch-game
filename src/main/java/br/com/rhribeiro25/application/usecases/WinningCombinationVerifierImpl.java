package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.*;

public class WinningCombinationVerifierImpl implements WinningCombinationVerifier {
    private Map<String, List<String>> winningCombinations;

    public WinningCombinationVerifierImpl() {
        this.winningCombinations = new HashMap<>();
    }

    @Override
    public Map<String, List<String>> verify(String[][] matrix, Config config) {
        verifySameSymbolCombinations(matrix, winningCombinations, config);
        verifyLinearCombinations(matrix, winningCombinations, config);
        return winningCombinations;
    }

    private void verifySameSymbolCombinations(String[][] matrix, Map<String, List<String>> winningCombinations, Config config) {
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

    private void verifyLinearCombinations(String[][] matrix, Map<String, List<String>> winningCombinations, Config config) {
        for(String combinationKey : config.getWinCombinations().keySet()){
            if(config.getWinCombinations().get(combinationKey).getGroup().equals("horizontally_linear_symbols")){
                verifyHorizontallyLinearSymbols(combinationKey, matrix, winningCombinations, config);
            }
            if(config.getWinCombinations().get(combinationKey).getGroup().equals("vertically_linear_symbols")){
                verifyVerticallyLinearSymbols(combinationKey, matrix, winningCombinations, config);
            }
            if(config.getWinCombinations().get(combinationKey).getGroup().equals("ltr_diagonally_linear_symbols")){
                verifyDiagonallyLinearSymbols(combinationKey, matrix, winningCombinations, config);
            }
            if(config.getWinCombinations().get(combinationKey).getGroup().equals("rtl_diagonally_linear_symbols")){
                verifyHorizontallyLinearSymbols(combinationKey, matrix, winningCombinations, config);
            }
        }
    }

    private void verifyHorizontallyLinearSymbols(String combinationKey, String[][] matrix,
                                                 Map<String, List<String>> winningCombinations, Config config){
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
                            winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(combinationKey);
                        }
                    }
                }
            }
        }
    }

    private void verifyVerticallyLinearSymbols(String combinationKey, String[][] matrix,
                                               Map<String, List<String>> winningCombinations, Config config){
        for(String symbol : config.getSymbols().keySet()){
            for(List<String> coveredAreas : config.getWinCombinations().get(combinationKey).getCoveredAreas()){
                int count = 0;
                for(String areas : coveredAreas){
                    String[] coordinates = areas.split(":");
                    int row = Integer.parseInt(coordinates[0]);
                    int column = Integer.parseInt(coordinates[1]);
                    if(matrix[row][column].equals(symbol)){
                        count++;
                        if(count == config.getRows()){
                            winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(combinationKey);
                        }
                    }
                }
            }
        }
    }

    private void verifyDiagonallyLinearSymbols(String combinationKey, String[][] matrix,
                                               Map<String, List<String>> winningCombinations, Config config){
        for(String symbol : config.getSymbols().keySet()){
            for(List<String> coveredAreas : config.getWinCombinations().get(combinationKey).getCoveredAreas()){
                int count = 0;
                for(String areas : coveredAreas){
                    String[] coordinates = areas.split(":");
                    int row = Integer.parseInt(coordinates[0]);
                    int column = Integer.parseInt(coordinates[1]);
                    if(matrix[row][column].equals(symbol)){
                        count++;
                        int maxDiagonally = Math.min(config.getRows(), config.getColumns());
                        if(count == maxDiagonally){
                            winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(combinationKey);
                        }
                    }
                }
            }
        }
    }
}
