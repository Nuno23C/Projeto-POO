package SmartDevices;

public class SmartBulb extends SmartDevice {
    private enum Estado {
        ON,
        OFF
    }
    private Estado estado;
    private int n_estado;
    private enum Tonalidade {
        NEUTRAL,
        WARM,
        COLD
    }
    private Tonalidade tone;
    private String string_tone;
    private double dimensao;
    private double consumoBase;
    private double consumoF;

    /**
     * Construtor por omissão.
     */
    public SmartBulb() {
        super();
        this.tone = Tonalidade.NEUTRAL;
        this.dimensao = 0;
        this.consumoBase = 0;
        this.consumoF = 0;
    }

    /**
     * Construtor com parâmetros.
     * @param id identificação da SmartBulb.
     * @param estado Estado da SmartBulb.
     * @param tone Tonalidade da SmartBulb.
     */
    public SmartBulb(String id, Estado estado, Tonalidade tone, double dimensao, double consumoBase) {
        super(id);
        this.estado = estado;
        this.tone = tone;
        this.dimensao = dimensao;
        this.consumoBase = consumoBase;
        this.consumoF = consumoBase;
    }

    public SmartBulb(String id, int n_estado, String string_tone, double dimensao, double consumoBase) {
        super(id);
        this.n_estado = n_estado;
        this.string_tone = string_tone;
        this.dimensao = dimensao;
        this.consumoBase = consumoBase;
        this.consumoF = consumoBase;
    }

    /**
     * Construtor de cópia.
     * @param sb SmartBulbs passada como parâmetro.
     */
    public SmartBulb(SmartBulb sb) { // SB de SmartBulb (usem SC e SS)
        super();
        this.estado = sb.getEstado();
        this.tone = sb.getTone();
        this.dimensao = sb.getDimensao();
        this.consumoBase = sb.getConsumoBase();
        this.consumoF = sb.getConsumoF();
    }

    /**
     * Método que verifica se os duas SmartBulbs são iguais.
     * @param o SmartBBulb passada como parâmetro.
     * @return True se as SmartBulbs forem iguais | False se as SmartBulbs forem diferentes.
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        SmartBulb sb = (SmartBulb) o;
        return (sb.getId().equals(this.getId()) &&
                sb.getEstado() == this.estado &&
                sb.getTone() == this.tone &&
                sb.getDimensao() == this.dimensao &&
                sb.getConsumoBase() == this.consumoF);
    }

    /**
     * Método que cria um clone da SmartBulb.
     * @return Clone da SmartBulb.
     */
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    /**
     * Método que representa a SmartBulb em formato de texto.
     * @return String com as características da SmartBulb.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Device id: " + this.getId() + "\n");
        sb.append("Mode: " + this.estado + "\n");
        sb.append("Tone: " + this.tone + "\n");
        sb.append("Dimension: " + this.dimensao +  "cm" + "\n");
        sb.append("Device base consumption: " + this.consumoBase + "kWh" + " \n");
        sb.append("Device consumption: " + this.consumoF + "kWh" + " \n");

        return sb.toString();
    }


    public void turnOn() {
        this.estado = Estado.ON;
    }

    public void turnOff() {
        this.estado = Estado.OFF;
    }

    /**
     * Método que muda a tonalidade da SmartBulb para NEUTRAL.
     */
    public void turn_NEUTRAL() {
        this.tone = Tonalidade.NEUTRAL;
    }

    /**
     * Método que muda a tonalidade da SmartBulb para WARM.
     */
    public void turn_WARM() {
        this.tone = Tonalidade.WARM;
    }

    /**
     * Método que muda a tonalidade da SmartBulb para COLD.
     */
    public void turn_COLD() {
        this.tone = Tonalidade.COLD;
    }






    // Getters and Setters
    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Tonalidade getTone() {
        return tone;
    }

    public void setTone(Tonalidade tone) {
        this.tone = tone;
    }

    public String getString_tone() {
        return this.string_tone;
    }

    public void setString_tone(String string_tone) {
        this.string_tone = string_tone;
    }

    public int getN_estado() {
        return this.n_estado;
    }

    public void setN_estado(int n_estado) {
        this.n_estado = n_estado;
    }

    public double getDimensao() {
        return this.dimensao;
    }

    public void setDimensao(double dimensao) {
        this.dimensao = dimensao;
    }

    public double getConsumoBase() {
        return consumoBase;
    }

    public void setConsumoPorHora(double consumoBase) {
        this.consumoBase = consumoBase;
    }

    public double getConsumoF() {
        return consumoF;
    }

    public void setConsumoF(double consumoF) {
        this.consumoF = consumoF;
    }


}
