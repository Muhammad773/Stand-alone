class ProductionEvent extends Event {
    private final Entity entity;
    private final SmartGridManager manager;
    private final TimeManager timeManager;

    public ProductionEvent(int hour, Entity entity, SmartGridManager manager, TimeManager timeManager) {
        super(hour, 1);
        this.entity = entity; 
        this.manager = manager; 
        this.timeManager = timeManager;
    }
    
    @Override
    public void process() {
        System.out.printf("\n%s Production Event for %s\n", timeManager.getFormattedTime(), entity.getId());
        entity.produceEnergy(manager.getWeatherFactor());
    }
}
