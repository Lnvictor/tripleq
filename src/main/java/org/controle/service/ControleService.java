package org.controle.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.controle.domain.Expense;
import org.controle.repository.FileRepository;

import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
public class ControleService {
    private FileRepository repository;

    public void save(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.of("pt", "BR"));
        String filename = formatter.format(LocalDate.now()) + "_orcamento.csv";
        String[] lines = line.split(",");
        Map<String, String> lineMap = new HashMap<>();
        lineMap.put("Descricao", lines[0]);
        lineMap.put("Valor", lines[1]);
        Expense expense = Expense.of(lineMap);

        repository.save(expense, filename);
    }

    public void close() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.of("pt", "BR"));
        String month = formatter.format(LocalDate.now());
        String filename = month + "_orcamento.csv";
        List<Expense> expenses = repository.read(filename);
        double total = expenses.stream().mapToDouble(Expense::getValue).sum();

        repository.close("closed\\" + filename, total);
    }
}
