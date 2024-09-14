package org.controle;

import org.controle.repository.FileRepository;
import org.controle.service.ControleService;

public class Main {
    public static void main(String[] args) {
        ControleService service = new ControleService(new FileRepository());

//        service.save("Nubank,1225.2");
        service.close();
    }
}