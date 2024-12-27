package br.com.rhribeiro25;

import br.com.rhribeiro25.presentation.controllers.GameController;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length < 4) {
            System.out.println("To use this application, you need to pass two arguments: " +
                    "\nUsage: java -jar <your-jar-file> --config <config_matrix.json> --betting-amount <amount>");
            return;
        }
        List<String> argsList = Arrays.asList(args);
        int indexConfig = argsList.indexOf("--config") + 1;
        int indexBettingAmount = argsList.indexOf("--betting-amount") + 1;

        String configPath = args[indexConfig];
        int bettingAmount = Integer.parseInt(args[indexBettingAmount]);


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
