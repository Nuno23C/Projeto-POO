import SmartDevices.SmartDevice;

import java.io.Serializable;
import java.util.*;

public class Casa implements Serializable {
    private String idCasa;
    private String morada;
    private String nome;
    private String NIF;
    public Map<String, SmartDevice> dispositivos;
    public Map<String, List<String>> divisoes; // Nome da divisão - Dispositivos
    private FornecedorEnergia fornecedor;

    public Casa() {
        this.idCasa = "";
        this.morada = "";
        this.nome = "";
        this.NIF = "";
        this.dispositivos = new HashMap<>();
        this.divisoes = new HashMap<>();
        this.fornecedor = null;
    }

    public Casa(String idCasa, String morada, String nome, String NIF, Map<String, SmartDevice> dispositivos, Map<String, List<String>> divisoes, FornecedorEnergia fornecedor) {
        this.idCasa = idCasa;
        this.morada = morada;
        this.nome = nome;
        this.NIF = NIF;
        setDispositivos(dispositivos);
        setDivisoes(divisoes);
        this.fornecedor = fornecedor;
    }

    public Casa(String idCasa, String morada, String nome, String NIF, Map<String, SmartDevice> dispositivos, Map<String, List<String>> divisoes) {
        this.idCasa = idCasa;
        this.morada = morada;
        this.nome = nome;
        this.NIF = NIF;
        setDispositivos(dispositivos);
        setDivisoes(divisoes);
    }

    public Casa(String idCasa, String morada, String nome, String NIF, FornecedorEnergia fornecedor) {
        this.idCasa = idCasa;
        this.morada = morada;
        this.nome = nome;
        this.NIF = NIF;
        this.divisoes = new HashMap<String,List<String>>();
        this.dispositivos = new HashMap<String,SmartDevice>();
        this.fornecedor = fornecedor;
    }

    public Casa(Casa casa) {
        this.idCasa = casa.getIdCasa();
        this.morada = casa.getMorada();
        this.nome = casa.getNome();
        this.NIF = casa.getNIF();
        this.dispositivos = casa.getDispositivos();
        this.divisoes = casa.getDivisoes();
        this.fornecedor = getFornecedor();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Casa c = (Casa) o;
        return (c.getIdCasa().equals(this.idCasa) &&
                c.getMorada().equals(this.morada) &&
                c.getNome().equals(this.nome) &&
                c.getNIF().equals(this.NIF) &&
                c.getDispositivos().equals(this.dispositivos) &&
                c.getDivisoes().equals(this.divisoes) &&
                c.getFornecedor().equals(this.fornecedor));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("House id: " + this.idCasa + "\n");
        sb.append("Adress: " + this.morada + "\n");
        sb.append("House owner: " + this.nome + "\n");
        sb.append("NIF: " + this.NIF + "\n");
        sb.append("Casa id: " + this.idCasa + "\n");
        sb.append("\n");
        //...

        return sb.toString();
    }

    public Casa clone() {
        return new Casa(this);
    }

    public void turn_On_Divisao(String div) {
        if(this.divisoes.containsKey(div)) {
            for(String deviceID: this.divisoes.get(div)) {
                dispositivos.get(deviceID).turnOn();
            }
        }
    }

    public void turn_Off_Divisao(String div) {
        if(this.divisoes.containsKey(div)) {
            for(String deviceID: this.divisoes.get(div)) {
                dispositivos.get(deviceID).turnOff();
            }
        }
    }

    public void turn_On_Casa() {
        for (SmartDevice disp: this.dispositivos.values()){
            disp.turnOn();
        }
    }

    public void turn_Off_Casa() {
        for (SmartDevice disp: this.dispositivos.values()){
            disp.turnOff();
        }
    }

    public void add_Divisao(String div) {
        if(!this.divisoes.containsKey(div)) {
            List<String> ids = new ArrayList<>();
            this.divisoes.put(div,ids);
        }
    }

    public void remove_Divisao(String div) {
        if(this.divisoes.containsKey(div)) {
            for(String sd_ID: this.divisoes.get(div)) {
                this.divisoes.remove(sd_ID);
                this.dispositivos.remove(sd_ID);
            }
            this.divisoes.remove(div);
        }
    }


    public void setNomeDaDivisao(String divName, String newDivName) {
        if (this.divisoes.containsKey(divName)) {
            List<String> ids = this.divisoes.get(divName);
            this.divisoes.remove(divName);
            divisoes.put(divName, ids);
        }
    }


    public void add_DispositivoNaDivisao(String div, SmartDevice sd){
        if (this.divisoes.containsKey(div)){
            List<String> ids = this.divisoes.get(div);
            if (!ids.contains(sd.getId())){
                ids.add(sd.getId());
                this.dispositivos.put(sd.getId(), sd);
            }
        }
        else{
            add_Divisao(div);
            List<String> ids = this.divisoes.get(div);
            if (!ids.contains(sd.getId())){
                ids.add(sd.getId());
                this.dispositivos.put(sd.getId(), sd);
            }
        }
    }

    public void remove_DispositivoNaDivisao(String id, String div) {
        if (this.dispositivos.containsKey(id)) {
            divisoes.get(div).remove(id);
            dispositivos.remove(id);
        }
    }

    public void add_Fornecedor(FornecedorEnergia fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String listaDevices(){
        StringBuilder sb = new StringBuilder();
        sb.append("List of devices");
        sb.append("{");
        for(String idDevice: dispositivos.keySet()){
            sb.append(idDevice);
        }
        sb.append("}");

        return sb.toString();
    }

    public String infoDevice(String idDevice){
        StringBuilder sb = new StringBuilder();

        sb.append(dispositivos.get(idDevice).toString());

        return sb.toString();
    }







    // Getters and Setters
    public String getIdCasa() {
        return this.idCasa;
    }

    public void setIdCasa(String idCasa) {
        this.idCasa = idCasa;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public Map<String, SmartDevice> getDispositivos() {
        return this.dispositivos;
    }

    public void setDispositivos(Map<String, SmartDevice> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public Map<String, List<String>> getDivisoes() {
        return this.divisoes;
    }

    public void setDivisoes(Map<String, List<String>> divisoes) {
        this.divisoes = divisoes;
    }

    public FornecedorEnergia getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(FornecedorEnergia fornecedor) {
        this.fornecedor = fornecedor;
    }
}
