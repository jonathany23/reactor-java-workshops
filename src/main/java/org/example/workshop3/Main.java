package org.example.workshop3;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nMap");
        Flux<Transaction> transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Integer> rewards = transactions.map(tx -> tx.getAmount() * 2);
        rewards.subscribe(System.out::println); // Outputs: 200, 400

        System.out.println("\nFilter");
        Flux<Transaction> transactions2 = Flux.just(new Transaction(50), new Transaction(150));
        Flux<Transaction> largeTransactions = transactions2.filter(tx -> tx.getAmount() > 100);
        largeTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 150

        System.out.println("\nFlatmap");
        Flux<Transaction> transactions3 = Flux.just(new Transaction(100), new Transaction(200));
        Flux<String> transactionDetails = transactions3.flatMap(Main::getTransactionDetails);
        transactionDetails.subscribe(System.out::println);

        System.out.println("\nZip");
        Flux<Transaction> transactions4 = Flux.just(new Transaction(100), new Transaction(200));
        Flux<User> users = Flux.just(new User("Alice"), new User("Bob"));
        Flux<String> transactionUserDetails = Flux.zip(transactions4, users, (tx, user) -> user.getName() + " made a transaction of " + tx.getAmount());
        transactionUserDetails.subscribe(System.out::println); // Outputs: Alice made a transaction of 100, Bob made a transaction of 200

        System.out.println("\nMerge");
        Flux<Transaction> account1Transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Transaction> account2Transactions = Flux.just(new Transaction(300), new Transaction(400));
        Flux<Transaction> allTransactions = Flux.merge(account1Transactions, account2Transactions);
        allTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 100, 200, 300, 400

        System.out.println("\ncollectList");
        Flux<Transaction> transactions5 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300));
        Mono<List<Transaction>> transactionList = transactions5.collectList();
        transactionList.subscribe(list -> System.out.println("Collected " + list.size() + " transactions")); // Outputs: Collected 3 transactions

        System.out.println("\nreduce");
        Flux<Transaction> transactions6 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300));
        Mono<Integer> totalAmount = transactions6.map(Transaction::getAmount).reduce(0, Integer::sum);
        totalAmount.subscribe(System.out::println); // Outputs: 600

        System.out.println("\nmergeWith");
        Flux<String> account1Notifications = Flux.just("Tx1: $100", "Tx2: $200");
        Flux<String> account2Notifications = Flux.just("Tx3: $300", "Tx4: $400");
        Flux<String> allNotifications = account1Notifications.mergeWith(account2Notifications);
        allNotifications.subscribe(System.out::println); // Outputs: Tx1: $100, Tx2: $200, Tx3: $300, Tx4: $400

        System.out.println("\nconcatWith");
        Flux<Transaction> day1Transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Transaction> day2Transactions = Flux.just(new Transaction(300), new Transaction(400));
        Flux<Transaction> allTransactions2 = day1Transactions.concatWith(day2Transactions);
        allTransactions2.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 100, 200, 300, 400

        System.out.println("\nswitchIfEmpty");
        Flux<Transaction> transactions7 = Flux.empty();
        Flux<Transaction> transactionsWithDefault = transactions7.switchIfEmpty(Flux.just(new Transaction(0)));
        transactionsWithDefault.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 0

        System.out.println("\ntake");
        Flux<Transaction> transactions8 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> firstThreeTransactions = transactions8.take(3);
        firstThreeTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 100, 200, 300

        System.out.println("\ntakeLast");
        Flux<Transaction> transactions9 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> lastTwoTransactions = transactions9.takeLast(2);
        lastTwoTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 400, 500

        System.out.println("\nskip");
        Flux<Transaction> transactions10 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> remainingTransactions = transactions10.skip(3);
        remainingTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 400, 500

        System.out.println("\nskipLast");
        Flux<Transaction> transactions11 = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300), new Transaction(400), new Transaction(500));
        Flux<Transaction> initialTransactions = transactions11.skipLast(2);
        initialTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 100, 200, 300

        System.out.println("\nMono.fromCallable");
        Mono<String> customerInfo = Mono.fromCallable(() -> getCustomerInfo(12345));
        customerInfo.subscribe(System.out::println); // Outputs: Customer information
    }

    private static Mono<String> getTransactionDetails(Transaction tx) {
        // Supongamos que esto hace una llamada asíncrona para obtener detalles
        return Mono.just("Details for transaction: " + tx.getAmount());
    }

    private static String getCustomerInfo(int customerId) {
        // Supongamos que esto hace una llamada para obtener información del cliente
        return "Customer info for ID: " + customerId;
    }
}
