import SmartDevices.SmartDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class FornecedorEnergia {
    public String nomeEmpresa;
    public double imposto;
    public double valorBase; //de cada dispositivo
    public double desconto;
    private Map<String, Casa> conj_Casas; //id da casa e a casa
    private Map<String, List<String>> faturas;  //lista de faturas

    /**
     * Construtor por omissão
     */
    public FornecedorEnergia(){
        this.nomeEmpresa = "";
        this.imposto = 1.23;
        this.valorBase = 0;
        this.desconto = 0;
        this.conj_Casas = new HashMap<>();
        this.faturas = new HashMap<>();
    }

    /**
     * Contrutor parametrizado
     * @param nomeEmpresa
     * @param imposto
     * @param valorBase
     * @param desconto
     * @param faturas
     */
    public FornecedorEnergia(String nomeEmpresa, double imposto, double valorBase, double desconto, HashMap<String, Casa> conj_Casas, Map<String, List<String>> faturas ){
        this.nomeEmpresa = nomeEmpresa;
        this.imposto = 1.23;
        this.valorBase = valorBase;
        this.desconto = desconto;
        setConjCasas(conj_Casas);
        setFatura(faturas);
    }


    /**
     * Constutor por cópia
     * @param fe
     */
    public FornecedorEnergia (FornecedorEnergia fe){
        this.nomeEmpresa = fe.getNomeEmpresa();
        this.imposto = fe.getImposto();
        this.valorBase = fe.getValorBase();
        this.desconto = fe.getDesconto();
        this.conj_Casas = fe.getConjCasas();
        this.faturas = fe.getFaturas();
    }


    public boolean equals (Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        FornecedorEnergia fe = (FornecedorEnergia) o;
        return (fe.getNomeEmpresa().equals(this.nomeEmpresa) &&
                fe.getImposto() == this.imposto &&
                fe.getValorBase() == this.valorBase &&
                fe.getDesconto() == this.desconto &&
                fe.getConjCasas().equals(this.conj_Casas) &&
                fe.getFaturas().equals(this.faturas));
    }

    public FornecedorEnergia clone() {
        return new FornecedorEnergia(this);
    }

    public String toString() {

        String sb = "\n" + "Nome da Empresa: " + this.nomeEmpresa + "\n" +
                "Imposto: " + this.imposto + "\n" +
                "Valor Base: " + this.valorBase + "\n" +
                "Desconto: " + this.desconto + "\n" +
                "Casas: " + this.conj_Casas + "\n" +
                "Fatura: " + this.faturas + "\n";
        return sb;
    }


    public double getPrecoPorDispositivo(SmartDevice sd){
        return (this.valorBase * sd.getConsumoPorHora() * this.imposto) * (1 - (this.desconto/100));
    }

    public double getConsumoDivisao(Map<String, SmartDevice> div){
        double consumoDivisao = 0;

        for(SmartDevice disp: div.values()) {
            consumoDivisao += disp.getConsumoPorHora();
        }

        return consumoDivisao;
    }

    public double getConsumoDaCasa(Casa c){
        double consumoDaCasa = 0;

        for(SmartDevice device: c.getDispositivos().values()){
            consumoDaCasa += getPrecoPorDispositivo(device);
        }

        return consumoDaCasa;
    }

    public void addCasa (String NIF, Casa casa){
        conj_Casas.put(NIF, casa);
    }

    public void removeCasa (String NIF){
        conj_Casas.remove(NIF);
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

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Map<String, Casa> getConjCasas() {
        Map<String, Casa> newCasa = new HashMap<>();
        for(String NIF: this.conj_Casas.keySet()){
            newCasa.put(NIF, this.conj_Casas.get(NIF).clone());
        }
        return newCasa;
    }

    private void setConjCasas(HashMap<String, Casa> conj_Casas){
        Map<String, Casa> newCasa = new HashMap<>();
        for(String NIF: conj_Casas.keySet()){
            newCasa.put(NIF, conj_Casas.get(NIF).clone());
        }
        this.conj_Casas = newCasa;
    }


    public Map<String, List<String>> getFaturas() {
        Map<String, List<String>> newFaturas = new HashMap<>();
        for(String fat: this.faturas.keySet()) {
            List<String> lista = this.faturas.get(fat);
            List<String> novaLista = new ArrayList<String>();
            ListIterator<String> iter = lista.listIterator();
            while(iter.hasNext())
                novaLista.add(iter.next());
            newFaturas.put(fat, novaLista);
        }
        return newFaturas;
    }


    public void setFatura(Map<String, List<String>> faturas) {
        Map<String, List<String>> newFaturas = new HashMap<>();
        for(String fat: faturas.keySet()) {
            List<String> lista = faturas.get(fat);
            List<String> novaLista = new ArrayList<String>();
            ListIterator<String> iter = lista.listIterator();
            while(iter.hasNext())
                novaLista.add(iter.next());
            newFaturas.put(fat, novaLista);
        }
        this.faturas = newFaturas;
    }
}
