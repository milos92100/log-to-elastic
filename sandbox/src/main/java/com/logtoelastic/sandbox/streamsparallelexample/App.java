package com.logtoelastic.sandbox.streamsparallelexample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) throws InterruptedException {

        //  parallelStreamWithForkJoinPool();

//        new Thread(App::parallelStreamWithForkJoinPool).start();
//        new Thread(App::parallelStreamWithForkJoinPool).start();
//        new Thread(App::parallelStreamWithForkJoinPool).start();
//        new Thread(App::parallelStreamWithForkJoinPool).start();


        new Thread(App::parallelStreamWithCommonThreadPool).start();
        new Thread(App::parallelStreamWithCommonThreadPool).start();
        new Thread(App::parallelStreamWithCommonThreadPool).start();
        new Thread(App::parallelStreamWithCommonThreadPool).start();
        // parallelStreamWithCommonThreadPool();
    }

    public static void parallelStreamWithForkJoinPool() {
        System.out.println("active processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("started...");
        long now = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = null;
        try {

            forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            var result = forkJoinPool.submit(() ->
                    Stream.of(1, 1, 1, 1) //
                            .parallel()
                            .map(val -> {
                                        try {
                                            System.out.println("var: " + val + " | th-id: " + Thread.currentThread().getId() + "; th-name: " + Thread.currentThread().getName());
                                            Thread.sleep(1000 * val);
                                        } catch (InterruptedException e) {
                                            return null;
                                        }
                                        return "val=" + val;
                                    }
                            ).collect(Collectors.toList())
            ).get();

            Thread.sleep(1000);
            result.forEach(System.out::println);

            System.out.println("duration: " + (System.currentTimeMillis() - now) + " ms");

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }


    public static void parallelStreamWithCommonThreadPool() {
        System.out.println("active processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("started...");
        long now = System.currentTimeMillis();

        try {
            var result = Stream.of(1, 1, 1, 1) //
                    .parallel()
                    .map(val -> {
                                try {
                                    System.out.println("var: " + val + " | th-id: " + Thread.currentThread().getId() + "; th-name: " + Thread.currentThread().getName());
                                    Thread.sleep(1000 * val);
                                } catch (InterruptedException e) {
                                    return null;
                                }
                                return "val=" + val;
                            }
                    ).collect(Collectors.toList());

            Thread.sleep(1000);
            result.forEach(System.out::println);

            System.out.println("duration: " + (System.currentTimeMillis() - now) + " ms");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }
    }

}
