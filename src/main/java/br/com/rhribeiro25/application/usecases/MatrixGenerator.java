package br.com.rhribeiro25.application.usecases;

import br.com.rhribeiro25.domain.Config;

public interface MatrixGenerator {
    //Applying SOLID
    String[][] generate(Config config);
}