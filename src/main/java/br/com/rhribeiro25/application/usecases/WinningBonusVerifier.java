package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

public interface WinningBonusVerifier {
    String verify(int winningCombinationsSize, String[][] matrix, Config config);
}
