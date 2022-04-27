import SmartDevices.SmartDevice;
import java.util.*;

public class Casa {
    private String morada;
    private String nome;
    private String NIF;
    public Map<String, SmartDevice> dispositivos;
    public Map<String, List<String>> divisoes;

    public Casa() {
        this.morada = "";
        this.nome = "";
        this.NIF = "";
        this.dispositivos = new HashMap<>();
        this.divisoes = new HashMap<>();
    }

    public Casa(String morada, String nome, String NIF, Map<String, SmartDevice> dispositivos, Map<String, List<String>> divisoes) {
        this.morada = morada;
        this.nome = nome;
        this.NIF = NIF;
        setDispositivos(dispositivos);
        setDivisoes(divisoes);
    }

    public Casa(Casa casa) {
        this.morada = casa.getMorada();
        this.nome = casa.getNome();
        this.NIF = casa.getNIF();
        this.dispositivos = casa.getDispositivos();
        this.divisoes = casa.getDivisoes();
    }

    public Casa clone() {
        return new Casa(this);
    }

    // Map<String, List<String>> divisoes;
    // Map<String, SmartDevice> dispositivos;

    public void turn_On_Divisao(String div) {
        for(String deviceID: this.divisoes.get(div)){
            dispositivos.get(deviceID).turnOn();
        }
    }

    public void turn_Off_Divisao(String div) {
        for(String deviceID: this.divisoes.get(div)) {
            dispositivos.get(deviceID).turnOff();
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



    // Getters and Setters
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

    // NÃO TESTADO
    public Map<String, SmartDevice> getDispositivos() {
        Map<String, SmartDevice> newDev = new HashMap<>();
        for(String id: this.dispositivos.keySet()){
            newDev.put(id, this.dispositivos.get(id).clone());
        }
        return newDev;
    }

    // NÃO TESTADO
    public void setDispositivos(Map<String, SmartDevice> dispositivos) {
        Map<String, SmartDevice> newDev = new HashMap<>();
        for(String id: dispositivos.keySet()) {
            newDev.put(id, dispositivos.get(id).clone());
        }
        this.dispositivos = newDev;
    }

    // NÃO TESTADO
    public Map<String, List<String>> getDivisoes() {
        Map<String, List<String>> newDev = new HashMap<>();
        for(String div: this.divisoes.keySet()) {
            List<String> lista = this.divisoes.get(div);
            List<String> novaLista = new ArrayList<String>();
            ListIterator<String> iter = lista.listIterator();
            while(iter.hasNext())
                novaLista.add(iter.next());
            newDev.put(div, novaLista);
        }
        return newDev;
    }

    // NÃO TESTADO
    public void setDivisoes(Map<String, List<String>> divisoes) {
        Map<String, List<String>> newDev = new HashMap<>();
        for(String div: divisoes.keySet()) {
            List<String> lista = divisoes.get(div);
            List<String> novaLista = new ArrayList<String>();
            ListIterator<String> iter = lista.listIterator();
            while(iter.hasNext())
                novaLista.add(iter.next());
            newDev.put(div, novaLista);
        }
        this.divisoes = newDev;
    }


}
