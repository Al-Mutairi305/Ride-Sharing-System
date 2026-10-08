public class DateTime implements IDateTime {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public DateTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d", month, day, year, hour, minute);
    }

    @Override
    public int compareTo(IDateTime other) {
        if (this.year != other.getYear()) {
            return Integer.compare(this.year, other.getYear());
        }
        if (this.month != other.getMonth()) {
            return Integer.compare(this.month, other.getMonth());
        }
        if (this.day != other.getDay()) {
            return Integer.compare(this.day, other.getDay());
        }
        if (this.hour != other.getHour()) {
            return Integer.compare(this.hour, other.getHour());
        }
        return Integer.compare(this.minute, other.getMinute());
    }       
    
}
