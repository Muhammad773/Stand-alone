class RepairEvent extends Event {
    private final Transformer transformer;
    private final TimeManager timeManager;
    private final SmartGridManager manager;  

    public RepairEvent(int hour, Transformer transformer, TimeManager timeManager, SmartGridManager manager) {
        super(hour, 0); 
        this.manager = manager;
        this.transformer = transformer; 
        this.timeManager = timeManager; 
    }
    
    @Override
    public void process() {
        transformer.repair();
        System.out.printf("%s Control command executed: Transformer %s repaired.\n", timeManager.getFormattedTime(), transformer.getId());

        manager.reportRepair(transformer);
    }
}

