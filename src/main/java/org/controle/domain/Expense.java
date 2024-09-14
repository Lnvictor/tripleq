package org.controle.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.controle.config.Constants;

import java.util.HashMap;
import java.util.Map;

/*
 * I have no idea what i am doing
 * */
@Data
@AllArgsConstructor
public class Expense {
    private String description;
    private Double value;

    public static Expense of(Map<String, String> line) {
        return new Expense(line.get("Descricao"), Double.parseDouble(line.get("Valor")));
    }

    public static Expense of(String line) {
        Map<String, String> map = new HashMap<>();
        String[] l = line.split(",");
        map.put("Descricao", l[0]);
        map.put("Valor", l[1]);

        return of(map);
    }


    @Override
    public String toString(){
        return String.format("%s,%s", description, value);
    }
}
