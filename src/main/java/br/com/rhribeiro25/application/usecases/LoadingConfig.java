package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

public interface LoadingConfig {
    //Applying SOLID
    Config setupConfigurations(String configPath);
}
