package SmartDevices;

import java.io.Serializable;

public abstract class SmartDevice implements Serializable {
    public String id;

    public SmartDevice() {
        this.id = "";
    }

    public SmartDevice(String id) {
        this.id = id;
    }

    public SmartDevice(SmartDevice dispositivo) {
        this.id = dispositivo.getId();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        SmartDevice sd = (SmartDevice) o;
        return (sd.getId().equals(this.id));
    }

    public String toString() {

        String sb = "\n" + "id: " + this.id + "\n";

        return sb;
    }

    public abstract SmartDevice clone();

    public abstract void turnOn();

    public abstract void turnOff();

    public abstract double getConsumoPorHora();






    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
