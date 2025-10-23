class ConsumptionEvent extends Event {
    private final Entity entity;
    private final TimeManager timeManager;

    public ConsumptionEvent(int hour, Entity entity, TimeManager timeManager) {
        super(hour, 2); 
        this.entity = entity; 
        this.timeManager = timeManager;
    }
    
    @Override
    public void process() {
        System.out.printf("\n%s Consumption Event for %s\n", timeManager.getFormattedTime(), entity.getId());
        entity.consumeEnergy();
    }
}
