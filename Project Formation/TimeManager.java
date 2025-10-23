class TimeManager {
    private int totalHours = 0;

    public void advanceTime(int hours) { 
        totalHours += hours; 
    }

    public int getCurrentDay() { 
        return totalHours / 24 + 1; 
    }

    public int getTotalHours() { 
        return totalHours; 
    }

    public int getCurrentHour() { 
        return totalHours % 24; 
    }

    public String getFormattedTime() { 
        return String.format("[Day %d, %02d:00]", getCurrentDay(), getCurrentHour()); 
    }
}