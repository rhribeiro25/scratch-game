package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

import java.util.List;
import java.util.Map;

public interface WinningCombinationVerifier {
    void verify(String[][] matrix, Config config, Map<String, List<String>> winningCombinations);
}