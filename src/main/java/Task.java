public class Task {

    String name, date, color, properyName;
    public Task(String name, String date, String color, String propertyName) {
        this.name = name;
        this.date = date;
        this.color = color;
        this.properyName = propertyName;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getColor() {
        return this.color;
    }

    public String getPropertyName(){return this.properyName;}
}
