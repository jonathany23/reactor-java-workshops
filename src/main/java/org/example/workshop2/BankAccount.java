package org.example.workshop2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BankAccount {
    private List<Transaction> transactions;

    public BankAccount() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // TODO 1: Implementar getTotalBalance utilizando streams y reduce
    public Optional<Double> getTotalBalance() {
        return transactions.stream()
                .map(transaction -> transaction.getType().equals("deposit") ? transaction.getAmount() : -transaction.getAmount())
                .reduce(Double::sum);
    }

    // TODO 2: Implementar getDeposits utilizando streams y filter
    public Optional<List<Transaction>> getDeposits() {
        return Optional.of(transactions.stream()
                .filter(transaction -> transaction.getType().equals("deposit"))
                .toList());
    }

    // TODO 3: Implementar getWithdrawals utilizando streams y filter
    public Optional<List<Transaction>> getWithdrawals() {
        return Optional.of(transactions.stream()
                .filter(transaction -> "withdrawal".equals(transaction.getType()))
                .toList());
    }

    // TODO 4: Implementar filterTransactions utilizando Function y streams
    public Optional<List<Transaction>> filterTransactions(Function<Transaction, Boolean> predicate) {
        return Optional.of(transactions.stream()
                .filter(predicate::apply)
                .toList());
    }

    // TODO 5: Implementar getTotalDeposits utilizando getDeposits y mapToDouble
    public Optional<Double> getTotalDeposits() {
        return getDeposits()
                .map(list -> list.stream()
                        .mapToDouble(Transaction::getAmount)
                        .sum());
    }

    // TODO 6: Implementar getLargestWithdrawal utilizando getWithdrawals y max
    public Optional<Transaction> getLargestWithdrawal() {
        return getWithdrawals()
                .flatMap(list -> list.stream()
                .max(Comparator.comparingDouble(Transaction::getAmount)));
    }

    // TODO 7: Implementar getTransactionsOnDate utilizando streams y filter
    public Optional<List<Transaction>> getTransactionsOnDate(String date) {
        return Optional.of(transactions.stream()
                .filter(trx -> trx.getDate().equals(date)).toList());
    }

    // TODO 8: Implementar getAverageTransactionAmount utilizando streams y mapToDouble
    public OptionalDouble getAverageTransactionAmount() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .average();
    }

    // TODO 9: Implementar getTransactionsWithAmountGreaterThan utilizando streams y filter
    public Optional<List<Transaction>> getTransactionsWithAmountGreaterThan(double amount) {
        return Optional.of(transactions.stream()
                .filter(trx -> trx.getAmount() > amount)
                .toList());
    }

    // TODO 10: Implementar transfer utilizando addTransaction
    public static void transfer(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
        sourceAccount.addTransaction(new Transaction(amount, "withdrawal", "2024-08-30"));
        targetAccount.addTransaction(new Transaction(amount, "deposit", "2024-08-30"));
    }

    // TODO 11: Implementar getTotalWithdrawals utilizando getWithdrawals y mapToDouble
    public Optional<Double> getTotalWithdrawals() {
        return getWithdrawals()
                .map(list -> list.stream()
                        .mapToDouble(Transaction::getAmount)
                        .sum());
    }

    // TODO 12: Implementar getTransactionsSummary utilizando streams, map y collect
    public Map<String, Double> getTransactionsSummary() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType, Collectors.summingDouble(Transaction::getAmount)));
    }
}
