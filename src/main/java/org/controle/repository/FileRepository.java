package org.controle.repository;

import org.controle.config.Constants;
import org.controle.domain.Expense;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {
    public void save(Expense expense, String filename) {
        Path path = Paths.get(Constants.EXPENSES_ROOT_DIRECTORY, filename);

        if (Files.notExists(path)) {
            this.createFileWithHeaders(filename, Constants.HEADERS);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(expense.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean createFileWithHeaders(String filename, String[] headers) {
        Path path = Paths.get(Constants.EXPENSES_ROOT_DIRECTORY, filename);
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(String.join(",", headers));
            writer.newLine();
            writer.flush();

            return Files.exists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Expense> read(String filename) {
        Path path = Paths.get(Constants.EXPENSES_ROOT_DIRECTORY, filename);
        List<Expense> expenses = new ArrayList<>();

        boolean isFirstLine = true;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (reader.ready()) {
                String line = reader.readLine();

                //Skipping headers line
                if (!isFirstLine) {
                    expenses.add(Expense.of(line));
                }
                isFirstLine = false;
            }

            return expenses;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(String filename, Double total) {
        Path path = Paths.get(Constants.EXPENSES_ROOT_DIRECTORY, filename);
        Double diff = Constants.FIXED_INCOME - total;

        if (Files.notExists(path)) {
            createFileWithHeaders(filename, Constants.CLOSED_HEADERS);
        }

        try {
            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
            writer.write(String.format("%s,%s,%s", total, Constants.FIXED_INCOME, diff));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
