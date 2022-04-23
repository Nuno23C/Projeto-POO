package SmartDevices;

public abstract class SmartDevice {
    public enum Estado {
        ON,
        OFF
    }
    public Estado estado;
    public String id;

    public SmartDevice() {
        this.id = "";
        this.estado = Estado.OFF;
    }

    public SmartDevice(String id, Estado x) {
        this.id = id;
        this.estado = x;
    }

    public SmartDevice(SmartDevice dispositivo) {
        this.id = dispositivo.getId();
        this.estado = dispositivo.getEstado();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        SmartBulb sb = (SmartBulb) o;
        return (sb.getId().equals(this.id) &&
                sb.getEstado() == this.estado);
    }

    public String toString() {

        String sb = "\n" + "id: " + this.id + "\n" +
                    " Estado: " + this.estado + "\n";

        return sb;
    }

    /*
    public SmartDevice clone() {
        return new SmartDevice(this);
    }
    */

    public void turnOn() {
        this.estado = Estado.ON;
    }

    public void turnOff() {
        this.estado = Estado.OFF;
    }

    public abstract double getConsumoPorHora();

    // Getters and Setters
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
