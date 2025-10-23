class TradeEvent extends Event {
    private final SmartGridManager manager;
    private final TimeManager timeManager;

    public TradeEvent(int hour, SmartGridManager manager, TimeManager timeManager) {
        super(hour, 3); 
        this.manager = manager; 
        this.timeManager = timeManager;
    }
    
    @Override
    public void process() {
        System.out.printf("\n%s Trade Event (Centralized Grid Trading)\n", timeManager.getFormattedTime());
        for (Entity e : manager.getEntities()) {
            e.tradeEnergy(manager);
        }
    }
}