package br.com.rhribeiro25.application.usecases;

import java.util.List;
import java.util.Map;

public interface WinningCombinationVerifier {
    Map<String, List<String>> verify(String[][] matrix);
}