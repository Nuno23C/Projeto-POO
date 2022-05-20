package SmartDevices;

public class SmartCamera extends SmartDevice {
    private enum Estado {
        ON,
        OFF
    }
    private Estado estado;
    private int n_estado;
    private int x;
    private int y;
    private long tempoLigada;
    private double tamanhoPacote;
    private double consumoBase;
    private double consumoF;

    /**
     * Construtor por omissão.
     */
    public SmartCamera() {
        super();
        this.estado = Estado.OFF;
        this.x = 0;
        this.y = 0;
        this.tempoLigada = 0;
        this.tamanhoPacote = 0;
        this.consumoBase = 0;
        this.consumoF = 0;
    }

    /**
     * Construtor parametrizado
     * @param id
     * @param estado
     * @param resolucao
     */
    public SmartCamera(String id, Estado estado, int x, int y, double tamanhoPacote, double consumoBase){
        super(id);
        this.estado = estado;
        this.x = x;
        this.y = y;
        this.tamanhoPacote = tamanhoPacote;
        this.consumoBase = consumoBase;
        this.consumoF = consumoBase * x * y;
    }

    public SmartCamera(String id, int n_estado, int x, int y, double tamanhoPacote, double consumoBase){
        super(id);
        this.n_estado = n_estado;
        this.x = x;
        this.y = y;
        this.tamanhoPacote = tamanhoPacote;
        this.consumoBase = consumoBase;
        this.consumoF = consumoBase * x * y;
    }

    /**
     * Construtor de cópia.
     * @param sc SmartCamera passada como parâmetro.
     */
    public SmartCamera(SmartCamera sc) {
        super();
        this.estado = sc.getEstado();
        this.x = sc.getX();
        this.y = sc.getY();
        this.tempoLigada = sc.getTempoLigada();
        this.tamanhoPacote = sc.getTamanhoPacote();
        this.consumoBase = sc.getConsumoBase();
        this.consumoF = sc.getConsumoF();
    }

    /**
     * Método que verifica se os duas SmartCameras são iguais.
     * @param o SmartCamera passada como parâmetro.
     * @return True se as SmartCamera forem iguais | False se as SmartCamera forem diferentes.
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        SmartCamera sc = (SmartCamera) o;
        return (sc.getId().equals(this.getId()) &&
                sc.getEstado() == this.estado &&
                sc.getX() == this.x &&
                sc.getY() == this.y &&
                sc.getTamanhoPacote() == this.tamanhoPacote &&
                sc.getConsumoBase() == this.consumoBase) &&
                sc.getConsumoF() == this.consumoF;
    }

    /**
     * Método que representa a SmartCamera em formato de texto.
     * @return String com as características da SmartCamera.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Decice ID:" + "this.id" + "\n");
        sb.append("Mode: " + this.estado + "\n");
        sb.append("Resolution: " + "(" + this.x + "x" + this.y + ")" + "\n");
        sb.append("File size: " + this.tamanhoPacote + "sec" + "\n");
        sb.append("Device base consumption: " + this.consumoBase + "kWh" + " \n");
        sb.append("Device consumption: " + this.consumoF + "kWh" + "\n");

        return sb.toString();
    }

    /**
     * Método que cria um clone da SmartCamera.
     * @return Clone da SmartCamera.
     */
    public SmartCamera clone() {
        return new SmartCamera(this);
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getTempoLigada() {
        return this.tempoLigada;
    }

    public double getTamanhoPacote() {
        return tamanhoPacote;
    }

    public double getConsumoBase() {
        return consumoBase;
    }

    public void setConsumoBase(double consumoBase) {
        this.consumoBase = consumoBase;
    }

    public double getConsumoF() {
        return consumoF;
    }

    public void setConsumoF(double consumoF) {
        this.consumoF = consumoF;
    }
}
