class Transformer {
    private final String id;
    private boolean isOperational = true;

    public Transformer(String id) { 
        this(id, true); 
    } 

    public Transformer(String id, boolean isOperational) { 
        this.id = id;
        this.isOperational = isOperational; 
    }

    public String getId() { 
        return id; 
    }

    public boolean isOperational() { 
        return isOperational; 
    }
    
    public void setOperational(boolean operational) { 
        isOperational = operational; 
    }

    public void repair() {
        isOperational = true;
        System.out.printf("Transformer %s repaired and operational.%n", id);
    }
}