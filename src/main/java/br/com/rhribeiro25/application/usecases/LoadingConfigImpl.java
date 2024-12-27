package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class LoadingConfigImpl implements LoadingConfig{

    public LoadingConfigImpl() {
    }

    @Override
    public Config setupConfigurations(String configPath) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode configJson = mapper.readTree(new File(configPath));
            return mapper.treeToValue(configJson, Config.class);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
