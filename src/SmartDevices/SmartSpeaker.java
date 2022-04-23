package SmartDevices;

public class SmartSpeaker extends SmartDevice {
    public String marca;
    public int volume;
    public String radioOnline;
    public double consumoBase;
    public double consumoPorHora;

    /**
     * Construtor por omissão.
     */
    public SmartSpeaker() {
        super();
        this.marca = "";
        this.volume = 0;
        this.radioOnline = "";
        this.consumoBase = 0;
        this.consumoPorHora = 0;
    }

    public SmartSpeaker(String id, Estado estado, String marca, int volume, String radioOnline, double consumoBase) {
        super(id,estado);
        this.marca = marca;
        this.volume = volume;
        this.radioOnline = radioOnline;
        this.consumoBase = consumoBase;
        this.consumoPorHora = volume*consumoBase;
    }

    /**
    * Construtor por cópia.
    * @param ss
    */
    public SmartSpeaker (SmartSpeaker ss) {
        super();
        this.marca = ss.getMarca();
        this.volume = ss.getVolume();
        this.radioOnline = ss.getRadioOnline();
        this.consumoBase = ss.getConsumo();
        this.consumoPorHora = ss.getConsumoPorHora();
    }

    public boolean equals (Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        SmartSpeaker ss = (SmartSpeaker) o;
        return (ss.getId().equals(this.id) &&
                ss.getEstado() == this.estado &&
                ss.getMarca().equals(this.marca) &&
                ss.getVolume() == this.volume &&
                ss.getRadioOnline().equals(this.radioOnline) &&
                ss.getConsumo() == this.consumoBase &&
                ss.getConsumoPorHora() == this.consumoPorHora);
    }

    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }

    public String toString() {

        String sb = "\n" + "id: " + this.id + "\n" +
                "Estado: " + this.estado + "\n" +
                "Marca: " + this.marca + "\n" +
                "Radio online a transmitir: " + this.radioOnline + "\n" +
                "Volume: " + this.volume + "\n" +
                "Consumo Base do SmartSpeaker: " + this.consumoBase + "\n" +
                "Consumo por hora: " + this.consumoPorHora + "\n";

        return sb;
    }

    public void change_Volume(int v) {
        this.volume = v;
    }

    public void change_Radio(String r) {
        this.radioOnline = r;
    }

    // Não tem sentido mudar a marca e nao mudar o consumo...
    public void change_Marca(String m) {
        this.marca = m;
    }

    // same
    public void change_ConsumoBase(double c) {
        this.consumoBase = c;
    }

    // Getters and Setters
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

    public double getConsumo() {
        return consumoBase;
    }

    public void setConsumo(double consumo) {
        this.consumoBase = consumo;
    }

    public double getConsumoPorHora() {
        return consumoPorHora;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }
}