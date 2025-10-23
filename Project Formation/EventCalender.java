import java.util.PriorityQueue;

class EventCalendar {
    private final PriorityQueue<Event> queue = new PriorityQueue<>();

    public void scheduleEvent(Event e) { 
        queue.add(e); 
    }

    public Event nextEvent() { 
        return queue.poll(); 
        // return null if no event is there
    }

    public boolean isEmpty() { 
        return queue.isEmpty();
    }
}

