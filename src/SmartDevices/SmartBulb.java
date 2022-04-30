package SmartDevices;

public class SmartBulb extends SmartDevice {
    public enum Estado {
        ON,
        OFF
    }
    public Estado estado;
    public enum Tonalidade {
        NEUTRAL,
        WARM,
        COLD
    }
    public Tonalidade tone;
    public double dimensões;
    public double consumoPorHora;

    /**
     * Construtor por omissão.
     */
    public SmartBulb() {
        super();
        this.tone = Tonalidade.NEUTRAL;
        this.dimensões = 0;
        this.consumoPorHora = 0;
    }

    /**
     * Construtor com parâmetros.
     * @param id identificação da SmartBulb.
     * @param estado Estado da SmartBulb.
     * @param tone Tonalidade da SmartBulb.
     */
    public SmartBulb(String id, Estado estado, Tonalidade tone, double dimensões) {
        super(id);
        this.estado = estado;
        this.tone = tone;
        this.dimensões = dimensões;
        if (estado == Estado.ON) {
            if (tone == Tonalidade.WARM)
                this.consumoPorHora = 30;
            else if (tone == Tonalidade.NEUTRAL)
                this.consumoPorHora = 20;
            else if (tone == Tonalidade.COLD)
                this.consumoPorHora = 10;
        } else {
            this.consumoPorHora = 0;
        }
    }

    /**
     * Construtor de cópia.
     * @param sb SmartBulbs passada como parâmetro.
     */
    public SmartBulb(SmartBulb sb) { // SB de SmartBulb (usem SC e SS)
        super();
        this.estado = sb.getEstado();
        this.tone = sb.getTone();
        this.dimensões = sb.getDimensõEs();
        this.consumoPorHora = sb.getConsumoPorHora();
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
        return (sb.getId().equals(this.id) &&
                sb.getEstado() == this.estado &&
                sb.getTone() == this.tone &&
                sb.getDimensõEs() == this.dimensões &&
                sb.getConsumoPorHora() == this.consumoPorHora);
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

        String sb = "\n" + "id: " + this.id + "\n" +
                    "Estado: " + this.estado + "\n" +
                    "Tonalidade: " + this.tone + "\n" +
                    "Dimensões: " + this.dimensões + "\n" +
                    "Consumo por hora: " + this.consumoPorHora + " \n";

        return sb;
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

    public double getDimensõEs() {
        return this.dimensões;
    }

    public void setDimensõEs(double dimensões) {
        this.dimensões = dimensões;
    }

    public double getConsumoPorHora() {
        return consumoPorHora;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }


}
