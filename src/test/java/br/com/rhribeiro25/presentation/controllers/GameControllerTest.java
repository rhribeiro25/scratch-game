package br.com.rhribeiro25.presentation.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameControllerTest {

    static GameController gameController;

    @BeforeAll
    static void beforeAll() {
        gameController = new GameController("src/main/resources/config_matrix[3][3].json");
    }

    @Test
    @DisplayName("winning vertically combination verifier")
    public void play() {
        gameController.play(10);
        Assertions.assertTrue(gameController.getResult().getReward() > 0);
        Assertions.assertTrue(gameController.getResult().getMatrix().length != 0);
    }
}
