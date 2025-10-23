abstract class Entity implements IEnergyProducer, IEnergyConsumer, ITradeable, WeatherObserver {
    private final String id;
    private double battery;
    private final double batteryCapacity;
    private final EnergySource energySource;
    private final Transformer transformer;

    // For Observer Pattern
    private double latestWeather = 1.0;

    // Finance and tracking
    private double totalCost = 0;
    private double totalIncome = 0;
    
    private double dailyProduced = 0;
    private double dailyConsumed = 0;
    private double dailySold = 0;

    private double dailyBought = 0;
    private double dailyIncome = 0;
    private double dailyCost = 0;

    private double totalProduced = 0;
    private double totalConsumed = 0;
    private double totalSold = 0;
    private double totalBought = 0;

    // Constructor 
    public Entity(String id, double batteryCapacity, EnergySource energySource, Transformer transformer) {
        this.id = id;
        this.batteryCapacity = batteryCapacity;
        this.battery = batteryCapacity / 2;
        this.energySource = energySource;
        this.transformer = transformer;
    }

    // Getters
    public String getId() { return id; }
    public double getBattery() { return battery; }
    public double getBatteryCapacity() { return batteryCapacity; }
    public EnergySource getEnergySource() { return energySource; }
    public Transformer getTransformer() { return transformer; }
    public double getLatestWeather() { return latestWeather; }

    // Setters (protected for subclass access)
    protected void setBattery(double battery) { this.battery = battery; }
    protected void setLatestWeather(double latestWeather) { this.latestWeather = latestWeather; }

    // Financial and energy tracking getters
    public double getTotalCost() { return totalCost; }
    public double getTotalIncome() { return totalIncome; }
    public double getDailyProduced() { return dailyProduced; }
    public double getDailyConsumed() { return dailyConsumed; }
    public double getDailySold() { return dailySold; }
    public double getDailyBought() { return dailyBought; }
    public double getDailyIncome() { return dailyIncome; }
    public double getDailyCost() { return dailyCost; }
    public double getTotalProduced() { return totalProduced; }
    public double getTotalConsumed() { return totalConsumed; }
    public double getTotalSold() { return totalSold; }
    public double getTotalBought() { return totalBought; }

    // Financial and energy tracking setters (protected)
    protected void setTotalProduced(double totalProduced) { this.totalProduced = totalProduced; }
    protected void setTotalConsumed(double totalConsumed) { this.totalConsumed = totalConsumed; }
    protected void setDailyProduced(double dailyProduced) { this.dailyProduced = dailyProduced; }
    protected void setDailyConsumed(double dailyConsumed) { this.dailyConsumed = dailyConsumed; }
    protected void setDailySold(double dailySold) { this.dailySold = dailySold; }
    protected void setDailyBought(double dailyBought) { this.dailyBought = dailyBought; }
    protected void setDailyIncome(double dailyIncome) { this.dailyIncome = dailyIncome; }
    protected void setDailyCost(double dailyCost) { this.dailyCost = dailyCost; }
    protected void setTotalSold(double totalSold) { this.totalSold = totalSold; }
    protected void setTotalBought(double totalBought) { this.totalBought = totalBought; }
    protected void setTotalIncome(double totalIncome) { this.totalIncome = totalIncome; }
    protected void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    @Override
    public double produceEnergy(double weatherFactor) {
        double produced = 0;
        try {
            if (energySource != null) {
                produced = energySource.produceEnergy(weatherFactor);
                storeEnergy(produced);
                dailyProduced += produced;
                System.out.printf("   [%s] Produced %.2f units from %s. Battery: %.2f%n",
                        id, produced, energySource.getName(), getBattery());
            }
        } catch (BatteryException e) {
            System.out.printf("   [%s] Battery overflow while producing energy: %s%n", id, e.getMessage());
        }
        return produced;
    }

    @Override
    public double consumeEnergy() {
        double consumption = getConsumptionAmount();
        try {
            useEnergy(consumption);
            dailyConsumed += consumption;
            System.out.printf("   [%s] Consumed %.2f units. Battery: %.2f%n", id, consumption, getBattery());
        } catch (BatteryException e) {
            System.out.printf("   [%s] Battery underflow while consuming energy: %s%n", id, e.getMessage());
        }
        return consumption;
    }

    public void storeEnergy(double amount) throws BatteryException {
        if (amount + getBattery() > getBatteryCapacity()) {
            throw new BatteryException(id + " Battery Overflow!!");
        }
        setBattery(getBattery() + amount);
    }

    public void useEnergy(double amount) throws BatteryException {
        if (getBattery() < amount) {
            throw new BatteryException(id + " Battery Underflow!!");
        }
        setBattery(getBattery() - amount);
    }

    @Override
    public void tradeEnergy(SmartGridManager manager) {
        if (transformer != null && !transformer.isOperational()) {
            System.out.printf("   [%s] Cannot trade: Transformer %s is offline!%n", id, transformer.getId());
            return;
        }

        double sellThreshold;
        double buyThreshold;

        if (getBatteryCapacity() <= 15) {
            sellThreshold = 10.5;
            buyThreshold = 6;
        } else if (getBatteryCapacity() <= 20) {
            sellThreshold = 14;
            buyThreshold = 8;
        } else {
            sellThreshold = 17.5;
            buyThreshold = 12.5;
        }

        double tradeAmount;
        double pricePerUnit = manager.getMarketPrice();

        try {
            if (getBattery() > sellThreshold) {
                tradeAmount = getBattery() - sellThreshold;
                useEnergy(tradeAmount);
                double money = tradeAmount * pricePerUnit;
                dailySold += tradeAmount;
                dailyIncome += money;
                manager.gridStoreEnergy(tradeAmount);
                manager.gridPay(money);
                System.out.printf("   [%s] Sold %.2f units to GRID for $%.2f. Battery: %.2f%n", id, tradeAmount, money, getBattery());
            } else if (getBattery() < buyThreshold) {
                tradeAmount = buyThreshold - getBattery();
                if (manager.gridUseEnergy(tradeAmount)) {
                    storeEnergy(tradeAmount);
                    double money = tradeAmount * pricePerUnit;
                    dailyBought += tradeAmount;
                    dailyCost += money;
                    manager.gridReceive(money);
                    System.out.printf("   [%s] Bought %.2f units from GRID for $%.2f. Battery: %.2f%n", id, tradeAmount, money, getBattery());
                } else {
                    System.out.printf("   [%s] Could not buy energy (GRID empty). Battery: %.2f%n", id, getBattery());
                }
            } else {
                System.out.printf("   [%s] No Trade Needed. Battery: %.2f%n", id, getBattery());
            }
        } catch (BatteryException e) {
            System.out.printf("   [%s] Battery error during trade: %s%n", id, e.getMessage());
        }
    }

    public void endOfDayUpdate() {
        totalProduced += dailyProduced;
        totalConsumed += dailyConsumed;
        totalSold += dailySold;
        totalBought += dailyBought;
        totalIncome += dailyIncome;
        totalCost += dailyCost;
        dailyProduced = dailyConsumed = dailySold = dailyBought = dailyIncome = dailyCost = 0;
    }

    public void printDailySummary() {
        double netEnergy = dailyProduced - dailyConsumed;
        double profit = dailyIncome - dailyCost;
        System.out.printf("%-10s | %10.2f | %11.2f | %10.2f | %10.2f | %9.2f | %10.2f | %8.2f%n",
                id, dailyProduced, dailyConsumed, netEnergy, dailyIncome, dailyCost, profit, getBattery());
    }

    public void printTotalSummary() {
        double netEnergy = totalProduced - totalConsumed;
        double profit = totalIncome - totalCost;
        System.out.printf("%-10s | %10.2f | %11.2f | %10.2f | %10.2f | %9.2f | %10.2f | %8.2f%n",
                id, totalProduced, totalConsumed, netEnergy, totalIncome, totalCost, profit, getBattery());
    }

    public abstract double getConsumptionAmount();

    @Override
    public void updateWeather(double weatherFactor) {
        setLatestWeather(weatherFactor);
    }

    public void demonstrateCoercion() {
        Object obj = this;
        if (obj instanceof Entity) {
            Entity e = (Entity) obj;
            System.out.println("Coercion demo: " + e.getId());
        }
    }
}



       