package ru.nsu.gstubarev.pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import ru.nsu.gstubarev.pizza.impl.BakerImpl;
import ru.nsu.gstubarev.pizza.impl.ClientImpl;
import ru.nsu.gstubarev.pizza.impl.DeliverymanImpl;
import ru.nsu.gstubarev.pizza.impl.GeneratorTime;
import ru.nsu.gstubarev.pizza.impl.JsonParserConf;
import ru.nsu.gstubarev.pizza.impl.StorageImpl;
import ru.nsu.gstubarev.pizza.intefaces.IgeneratorTime;
import ru.nsu.gstubarev.pizza.intefaces.IparserConf;
import ru.nsu.gstubarev.pizza.intefaces.Istorage;
import ru.nsu.gstubarev.pizza.records.Configuration;
import ru.nsu.gstubarev.pizza.records.Order;

/**
 * Main class that configures and runs the pizzeria simulation.
 */
public class Pizzeria {

    /**
     * Entry point of the simulation.
     * Initializes resources, starts worker threads, and handles graceful shutdown.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        IparserConf parser = new JsonParserConf();
        Configuration config = parser.parse("config.json");
        System.out.println("Pizzeria opened! Config: " + config);

        BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
        Istorage storage = new StorageImpl(config.storageCapacity());
        IgeneratorTime deliveryTimeGen = new GeneratorTime(config.minDeliveryTimeMs(), config.maxDeliveryTimeMs());

        ExecutorService clientPool = Executors.newSingleThreadExecutor();
        ExecutorService bakersPool = Executors.newFixedThreadPool(config.bakersCount());
        ExecutorService deliverymanPool = Executors.newFixedThreadPool(config.deliverymanCount());

        List<BakerImpl> bakers = new ArrayList<>();
        List<DeliverymanImpl> couriers = new ArrayList<>();

        ClientImpl client = new ClientImpl(orderQueue);
        clientPool.execute(client);

        for (int i = 0; i < config.bakersCount(); i++) {
            BakerImpl baker = new BakerImpl(i + 1, config.bakerSpeedMs(), orderQueue, storage);
            bakers.add(baker);
            bakersPool.execute(baker);
        }

        for (int i = 0; i < config.deliverymanCount(); i++) {
            int randomTrunkCapacity = 2 + (i % 3);
            DeliverymanImpl courier = new DeliverymanImpl(i + 1, randomTrunkCapacity, deliveryTimeGen, storage);
            couriers.add(courier);
            deliverymanPool.execute(courier);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Shift ended! No new orders. ---");
        client.stopOrdering();
        clientPool.shutdownNow();

        bakers.forEach(BakerImpl::stopWork);
        bakersPool.shutdown();
        try {
            while (!bakersPool.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("Baker is working");
            }
        } catch (InterruptedException e) {
            bakersPool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        couriers.forEach(DeliverymanImpl::stopWork);
        deliverymanPool.shutdownNow();
        try {
            while (!deliverymanPool.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Deliveryman is working");
            }
        } catch (InterruptedException e) {
            deliverymanPool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Pizzeria closed. All orders done.");
    }
}