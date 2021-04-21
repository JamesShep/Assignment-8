package com.coderscampus.assignment;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        Assignment8 application = new Assignment8();

        List<Integer> numbers = Collections.synchronizedList(new ArrayList<>(1000));

        ExecutorService executor = Executors.newCachedThreadPool();

        List<CompletableFuture<Void>> tasks = new ArrayList<>(1000);

        for (int i=0; i<1000; i++) {
            CompletableFuture<Void> task = CompletableFuture.supplyAsync(application::getNumbers, executor)
                                                            .thenAccept(numbers::addAll);
                                                       tasks.add(task);
        }

        while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000 ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }

        // Below code was assisted through walkthrough
         Map<Integer, Integer> output = numbers.stream()
                .collect(Collectors.toMap(i -> i, i -> 1, (oldValue, newValue) -> oldValue + 1));
        System.out.println("Total number of numbers: " + output);

    }
}