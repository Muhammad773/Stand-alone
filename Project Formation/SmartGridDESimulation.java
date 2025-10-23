import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

// --- Main simulation class ---
public class SmartGridDESimulation {
    public static void main(String[] args) {
        // Setup
        SmartGridManager manager = new SmartGridManager();
        Transformer t1 = new Transformer("TX1");
        Transformer t2 = new Transformer("TX2");

        manager.addTransformer(t1);
        manager.addTransformer(t2);

        WindFarm windFarm = new WindFarm("WF1", 80, t1);

        House house1 = new House("House1", 70, t1);
        Factory factory1 = new Factory("Factory1", 120, t2);
        EV ev1 = new EV("EV1", 50, t1);

        manager.addEntity(house1);
        manager.addEntity(factory1);
        manager.addEntity(ev1);
        manager.addEntity(windFarm);

        // Demonstrate coercion
        house1.demonstrateCoercion();

        // --- Demonstrate subtyping and multityping ---
        System.out.println("\n--- Subtyping and Multityping Demo ---");
        House demoHouse = house1;

        // Subtyping
        Entity entityRef = demoHouse;
        System.out.println("Entity ID (via Entity): " + entityRef.getId());

        // Multityping
        IEnergyProducer producerRef = demoHouse;
        IEnergyConsumer consumerRef = demoHouse;
        ITradeable tradeableRef = demoHouse;
        WeatherObserver observerRef = demoHouse;

        try {
            double produced = producerRef.produceEnergy(1.0);
            System.out.println("Produced (via IEnergyProducer): " + produced);
        } catch (BatteryException e) {
            System.out.println("Production error: " + e.getMessage());
        }

        try {
            double consumed = consumerRef.consumeEnergy();
            System.out.println("Consumed (via IEnergyConsumer): " + consumed);
        } catch (BatteryException e) {
            System.out.println("Consumption error: " + e.getMessage());
        }

        tradeableRef.tradeEnergy(manager); 
        observerRef.updateWeather(1.2);    // via WeatherObserver
         
        demoHouse.printDailySummary();

        // Use generic registry
        EntityRegistry<Entity> registry = new EntityRegistry<>();
        registry.register(house1); // all should of same extended class Entity
        registry.register(factory1);
        registry.register(ev1);
        registry.register(windFarm);

        // Observer pattern: WeatherStation
        WeatherStation ws = new WeatherStation();

        // Register all entities as observers to the WeatherStation
        for (Entity e : manager.getEntities()) {
            ws.registerObserver(e);
        }

        // Set the initial weather factor, notifying observers
        ws.setWeatherFactor(manager.getWeatherFactor());

        // Simulation
        int totalHours = 96; // 4 days
        TimeManager timeManager = new TimeManager();
        EventCalendar calendar = new EventCalendar();

        // Schedule events
        for (int hour = 0; hour < totalHours; hour++) { // 0 to 95
            if (hour % 6 == 0) // Every 6 hours
                calendar.scheduleEvent(new WeatherEvent(hour, manager, ws, timeManager));
            if (hour % 6 == 0) { // 6 hours, for every Entity:
                for (Entity e : manager.getEntities()) {
                    calendar.scheduleEvent(new ProductionEvent(hour, e, manager, timeManager));
                    calendar.scheduleEvent(new ConsumptionEvent(hour, e, timeManager));
                }
            }
            if (hour == 15) calendar.scheduleEvent(new FaultEvent(hour, t1, timeManager, manager));
            if (hour == 18) calendar.scheduleEvent(new RepairEvent(hour, t1, timeManager, manager));
            if (hour % 24 == 23) calendar.scheduleEvent(new TradeEvent(hour, manager, timeManager));
        }

        // Simulation loop
        int lastDay = 0;
        while (!calendar.isEmpty()) {
            Event e = calendar.nextEvent();
            timeManager.advanceTime(e.getHour() - timeManager.getTotalHours());
            e.process();

            // At end of each day, print summary
            if (timeManager.getCurrentHour() == 23) {
                int day = timeManager.getCurrentDay();
                System.out.printf("\n========== DAILY SUMMARY FOR DAY %d ==========\n", day);
                System.out.printf("%-10s | %10s | %11s | %10s | %10s | %9s | %10s | %8s\n",
                        "Entity", "Prod (kWh)", "Cons (kWh)", "Net (kWh)", "Income ($)", "Cost ($)", "Profit ($)", "Battery");
                System.out.println("----------------------------------------------------------------------------------------------------");
                for (Entity ent : manager.getEntities()) {
                    ent.printDailySummary();
                    ent.endOfDayUpdate();
                }
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.printf("Grid Energy: %.2f units | Grid Money: $%.2f\n", manager.getGridEnergy(), manager.getGridMoney());
                System.out.println("==================================================");
                lastDay = day;
            }
        }

        // Final cumulative summary
        System.out.printf("\nFinal Cumulative Summary After %d Days:\n", lastDay);
        System.out.printf("%-10s | %10s | %11s | %10s | %10s | %9s | %10s | %8s\n",
                "Entity", "Prod (kWh)", "Cons (kWh)", "Net (kWh)", "Income ($)", "Cost ($)", "Profit ($)", "Battery");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Entity ent : manager.getEntities()) {
            ent.printTotalSummary();
        }
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
