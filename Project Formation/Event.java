abstract class Event implements Comparable<Event> {
    private final int hour; 
    private final int priority;
    
    public Event(int hour, int priority) { 
        this.hour = hour; 
        this.priority = priority; 
    }

    public int getHour() { 
        return hour; 
    }

    public int getPriority() {
        return priority;
    }

    public abstract void process();

   @Override
    public int compareTo(Event o) {
        if (this.hour != o.hour) {
            return Integer.compare(this.hour, o.hour);
        }
        return Integer.compare(this.priority, o.priority);
    }
}