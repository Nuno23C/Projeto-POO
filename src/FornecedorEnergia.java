import SmartDevices.SmartDevice;

public class FornecedorEnergia {
    public String nomeEmpresa;
    public double imposto;
    public double valorBase; //de cada dispositivo

    /**
     * Construtor por omissão
     */
    public FornecedorEnergia(){
        this.nomeEmpresa = "";
        this.imposto = 1.23;
        this.valorBase = 0;
    }

    /**
     * Contrutor parametrizado
     * @param nomeEmpresa
     * @param imposto
     * @param valorBase
     */
    public FornecedorEnergia(String nomeEmpresa, double imposto, double valorBase){
        this.nomeEmpresa = nomeEmpresa;
        this.imposto = 1.23;
        this.valorBase = valorBase;
    }

    /**
     * Constutor por cópia
     * @param fe
     */
    public FornecedorEnergia (FornecedorEnergia fe){
        this.nomeEmpresa = fe.getNomeEmpresa();
        this.imposto = fe.getImposto();
        this.valorBase = fe.getValorBase();
    }

    public boolean equals (Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        FornecedorEnergia fe = (FornecedorEnergia) o;
        return (fe.getNomeEmpresa().equals(this.nomeEmpresa) &&
                fe.getImposto() == this.imposto &&
                fe.getValorBase() == this.valorBase);
    }

    public FornecedorEnergia clone() {
        return new FornecedorEnergia(this);
    }

    public String toString() {

        String sb = "\n" + "Nome da Empresa: " + this.nomeEmpresa + "\n" +
                "Imposto: " + this.imposto + "\n" +
                "Valor Base: " + this.valorBase + "\n";

        return sb;
    }

    public double getPrecoPorDispositivo(SmartDevice sd){
        return (this.valorBase * sd.getConsumoPorHora() * this.imposto) * 0.9;
    }


    // Getters and Setters
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }
}
