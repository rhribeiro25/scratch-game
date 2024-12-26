package br.com.rhribeiro25;

import br.com.rhribeiro25.presentation.controllers.GameController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final Random RANDOM = new Random();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("To use this application, you need to pass two arguments: " +
                    "\nUsage: java -jar <your-jar-file> --config <config_matrix[5][5].json> --betting-amount <amount>");
            return;
        }

        String configPath = args[0];
        int bettingAmount = Integer.parseInt(args[1]);


        if (configPath.isBlank() || bettingAmount <= 0) {
            System.out.println("Invalid arguments. Please provide a valid config file and betting amount greater than zero.");
            return;
        }

        try {
            GameController gameController = new GameController(configPath);
            gameController.play(bettingAmount);
            System.out.println(gameController.getResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
