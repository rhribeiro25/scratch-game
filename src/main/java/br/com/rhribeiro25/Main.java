package br.com.rhribeiro25;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final Random RANDOM = new Random();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        /*if (args.length < 2) {
            System.out.println("To use this application, you need to pass two arguments: " +
                    "\nUsage: java -jar <your-jar-file> --config <config_matrix[5][5].json> --betting-amount <amount>");
            return;
        }

        String configPath = args[0];
        int bettingAmount = Integer.parseInt(args[1]);*/

        String configPath = "C:/Users/rhrib/OneDrive/Documentos/Projects/scratch-game/src/main/resources/config.json";
        int bettingAmount = 10;

        if (configPath == null || bettingAmount <= 0) {
            System.out.println("Invalid arguments. Please provide a valid config file and betting amount greater than zero.");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode configJson = mapper.readTree(new File(configPath));
            Config config = mapper.treeToValue(configJson, Config.class);
            Game game = new Game(config, bettingAmount);
            game.play();
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(game.getResult());
            System.out.println(json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
