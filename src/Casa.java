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

    public Casa(String morada, String nome, String NIF) {
        this.morada = morada;
        this.nome = nome;
        this.NIF = NIF;
        this.divisoes = new HashMap<String,List<String>>();
        this.dispositivos = new HashMap<String,SmartDevice>();
    }

    public Casa(Casa casa) {
        this.morada = casa.getMorada();
        this.nome = casa.getNome();
        this.NIF = casa.getNIF();
        this.dispositivos = casa.getDispositivos();
        this.divisoes = casa.getDivisoes();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Casa c = (Casa) o;
        return (c.getMorada().equals(this.morada) &&
                c.getNome().equals(this.nome) &&
                c.getNIF().equals(this.NIF) &&
                c.getDispositivos().equals(this.dispositivos) &&
                c.getDivisoes().equals(this.divisoes));
    }

    public String toString() {
        String sb = "\n" + "Morada: " + this.morada + "\n" +
                    "Nome: " + this.nome + "\n" +
                    "NIF: " + this.NIF + "\n" +
                    "Divisões: " + this.divisoes + "\n" +
                    "Dispositivos: " + this.dispositivos + "\n";

        return sb;
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
        //this.dispositivos.values().forEach(s->s.turnOff());
    }

    public void add_Divisao(String div) {
        if(!this.divisoes.containsKey(div)) {
            List<String> ids = new ArrayList<>();
            this.divisoes.put(div,ids);
        }
    }

    public void remove_Desivao(String div) {
        if(this.divisoes.containsKey(div)) {
            this.divisoes.remove(div);
            // FALTA REMOVER DISPOSITIVOS
        }
    }

    public void add_Dispositivo_NaCasa(String div, SmartDevice disp){
        if (this.divisoes.containsKey(div)){
            List<String> ids = this.divisoes.get(div);
            if (!ids.contains(disp.getId())){
                ids.add(disp.getId());
                this.dispositivos.put(disp.getId(), disp);
            }
        }
        else{
            add_Divisao(div);
            List<String> ids = this.divisoes.get(div);
            if (!ids.contains(disp.getId())){
                ids.add(disp.getId());
                this.dispositivos.put(disp.getId(), disp);
            }
        }
    }

    public void remove_Dispositivo(String disp) {

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
