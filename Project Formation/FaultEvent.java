class FaultEvent extends Event {
    private final Transformer transformer;
    private final TimeManager timeManager;
    private final SmartGridManager manager; 

    public FaultEvent(int hour, Transformer transformer, TimeManager timeManager, SmartGridManager manager) {
        super(hour, 0); 
        this.transformer = transformer; 
        this.timeManager = timeManager;
        this.manager = manager;
    }
    
    @Override
    public void process() {
        transformer.setOperational(false);
        System.out.printf("%s Fault detected at Transformer %s! Transformer is now offline.\n", timeManager.getFormattedTime(), transformer.getId());

        manager.reportFault(transformer);
    }
}