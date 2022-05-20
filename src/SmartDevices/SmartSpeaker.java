package SmartDevices;

public class SmartSpeaker extends SmartDevice {
    private enum Estado {
        ON,
        OFF
    }
    private Estado estado;
    private int n_estado;
    private String marca;
    private int volume;
    private String radioOnline;
    private double consumoBase;
    private double consumoF;

    /**
     * Construtor por omissão.
     */
    public SmartSpeaker() {
        super();
        this.estado = Estado.OFF;
        this.marca = "";
        this.volume = 0;
        this.radioOnline = "";
        this.consumoBase = 0;
        this.consumoF = 0;
    }

    public SmartSpeaker(String id, Estado estado, String marca, int volume, String radioOnline, double consumoBase) {
        super(id);
        this.estado = estado;
        this.marca = marca;
        this.volume = volume;
        this.radioOnline = radioOnline;
        this.consumoBase = consumoBase;
        this.consumoF = volume * consumoBase;
    }

    public SmartSpeaker(String id, int n_estado, String marca, int volume, String radioOnline, double consumoBase) {
        super(id);
        this.n_estado = n_estado;
        this.marca = marca;
        this.volume = volume;
        this.radioOnline = radioOnline;
        this.consumoBase = consumoBase;
        this.consumoF = volume * consumoBase;
    }

    /**
    * Construtor por cópia.
    * @param ss
    */
    public SmartSpeaker (SmartSpeaker ss) {
        super();
        this.estado = ss.getEstado();
        this.marca = ss.getMarca();
        this.volume = ss.getVolume();
        this.radioOnline = ss.getRadioOnline();
        this.consumoBase = ss.getConsumoBase();
        this.consumoF = ss.getConsumoF();
    }

    public boolean equals (Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        SmartSpeaker ss = (SmartSpeaker) o;
        return (ss.getId().equals(this.getId()) &&
                ss.getEstado() == this.estado &&
                ss.getMarca().equals(this.marca) &&
                ss.getVolume() == this.volume &&
                ss.getRadioOnline().equals(this.radioOnline) &&
                ss.getConsumoBase() == this.consumoBase &&
                ss.getConsumoF() == this.consumoF);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Device ID: " + this.getId() + "\n");
        sb.append("Mode: " + this.estado + "\n");
        sb.append("Brand: " + this.marca + "\n");
        sb.append("Online radio: " + this.radioOnline + "\n");
        sb.append("Volume: " + this.volume + "\n");
        sb.append("Device base consumption: " + this.consumoBase + "kWh" + "\n");
        sb.append("Device consumption: " + this.consumoF + "kWh" + "\n");

        return sb.toString();
    }

    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }

    public void turnOn() {
        this.estado = Estado.ON;
    }

    public void turnOff() {
        this.estado = Estado.OFF;
    }







    // Getters and Setters
    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getN_estado() {
        return this.n_estado;
    }

    public void setN_estado(int n_estado) {
        this.n_estado = n_estado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getRadioOnline() {
        return radioOnline;
    }

    public void setRadioOnline(String radioOnline) {
        this.radioOnline = radioOnline;
    }

    public double getConsumoBase() {
        return this.consumoBase;
    }

    public void setConsumoBase(double consumoBase) {
        this.consumoBase = consumoBase;
    }

    public double getConsumoF() {
        return this.consumoF;
    }

    public void setConsumoF(double consumoF) {
        this.consumoF = consumoF;
    }
}
