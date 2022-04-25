package SmartDevices;

public class SmartCamera extends SmartDevice {
    public double resolucao;
    public double tamanhoPacote;
    public double consumoPorHora;

    /**
     * Construtor por omissão.
     */
    public SmartCamera() {
        super();
        this.resolucao = 0;
        this.tamanhoPacote = 0;
        this.consumoPorHora = 0;
    }

    /**
     * Construtor com parâmetros.
     * @param id identificação da SmartCamera.
     * @param estado Estado da SmartCamera.
     * @param resolucao Resolucao da SmartCamera.
     * @param tamanhoPacote Tamanho do Pacote transferido
     */
    public SmartCamera(String id, Estado estado, double resolucao, double tamanhoPacote){
        super(id,estado);
        this.resolucao = resolucao;
        this.tamanhoPacote = tamanhoPacote;
        if (resolucao > 0 && tamanhoPacote > 0)
            this.consumoPorHora = resolucao*tamanhoPacote;
        else
            this.consumoPorHora = 0;
    }

    /**
     * Construtor de cópia.
     * @param sc SmartCamera passada como parâmetro.
     */
    public SmartCamera(SmartCamera sc) { // SC de SmartCamera
        super();
        this.resolucao = sc.getResolucao();
        this.tamanhoPacote = sc.getTamanhoPacote();
        this.consumoPorHora = sc.getConsumoPorHora();
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
        return (sc.getId().equals(this.id) &&
                sc.getEstado() == this.estado &&
                sc.getResolucao() == this.resolucao &&
                sc.getTamanhoPacote() == this.tamanhoPacote &&
                sc.getConsumoPorHora() == this.consumoPorHora);
    }

    /**
     * Método que cria um clone da SmartCamera.
     * @return Clone da SmartCamera.
     */
    public SmartCamera clone() {
        return new SmartCamera(this);
    }

    /**
     * Método que representa a SmartCamera em formato de texto.
     * @return String com as características da SmartCamera.
     */
    public String toString() {

        String sc = "\n" + "id: " + this.id + "\n" +
                    "Estado: " + this.estado + "\n" +
                    "Resolucao: " + this.resolucao + "\n" +
                    "Tamanho do Pacote: " + this.tamanhoPacote + "\n" +
                    "Consumo por hora: " + this.consumoPorHora + " \n";

        return sc;
    }

    // Getters and Setters
    public double getResolucao() {
        return resolucao;
    }

    public void setResolucao(double resolucao) {
        this.resolucao = resolucao;
    }

    public double getTamanhoPacote() {
        return tamanhoPacote;
    }

    public void setTamanhoPacote(double tamanhoPacote) {
        this.tamanhoPacote = tamanhoPacote;
    }

    @Override
    public double getConsumoPorHora() {
        return consumoPorHora;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }



}
