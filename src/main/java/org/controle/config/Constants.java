package org.controle.config;

import lombok.Data;

@Data
public final class Constants {
    public static final String EXPENSES_ROOT_DIRECTORY = "C:\\Users\\victo\\personal\\git\\controle_custos\\src\\orcamentos";
    public static final String[] HEADERS = new String[]{"Descricao", "Valor"};
    public static final String[] CLOSED_HEADERS = new String[]{"Total", "Salario", "Sobra"};
    public static final Double FIXED_INCOME = 5700D;
}
