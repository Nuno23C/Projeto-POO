import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.io.*;

public class Cidade {
    public Map<String, Casa> casas = new HashMap<>(); // Todas as casas ---> Id da casa - Casa
    public Map<String, List<String>> fornecedores = new HashMap<>(); // Todos os fornecedores e as casas ---> nome do Fornecedor - Lista de casas
    private Map<String, List<String>> faturas = new HashMap<>(); // Todas as faturas de todas as casas ---> nome da Casas - Lista de faturas

    public void saveState(String nameOfFile) throws FileNotFoundException,IOException {
        try {
            FileOutputStream fos = new FileOutputStream(nameOfFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found!\n");
        }
    }

    public Cidade loadState(String nameOfFile) throws FileNotFoundException,IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(nameOfFile);
        ObjectInputStream oos = new ObjectInputStream(fis);
        Cidade cit =(Cidade) oos.readObject();
        oos.close();
        return cit;
    }

    public Cidade() {
        this.casas = new HashMap<>();
        this.fornecedores = new HashMap<>();
        this.faturas = new HashMap<>();
    }

    public Cidade(Map<String, Casa> casas, Map<String, List<String>> fornecedores, Map<String, List<String>> faturas) {
        this.casas = casas;
        this.fornecedores = fornecedores;
        this.faturas = faturas;
    }

    public Cidade(Cidade cidade) {
        this.casas = cidade.getCasas();
        this.fornecedores = cidade.getFornecedores();
        this.faturas = cidade.getFaturas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Casas:\n");
        sb.append(this.getCasas().size());
        for (Casa casa: this.casas.values()){
            sb.append(casa.toString());
            sb.append("\n");
        }
       // sb.append("\nFornecedores:\n");

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if(o == null || this.getClass() != o.getClass())
            return false;

        Cidade c = (Cidade) o;
        return (this.casas.equals(c.getCasas()) &&
                this.fornecedores.equals(c.getFornecedores()) &&
                this.faturas.equals(c.getFaturas()));
    }

    public Cidade clone() {
        return new Cidade(this);
    }

    public void add_Casa (String idCasa, Casa casa){
        casas.put(idCasa, casa);
    }

    public void remove_Casa (String idCasa){
        casas.remove(idCasa);
    }





    // Getters and Setters
    public Map<String, Casa> getCasas() {
        Map<String, Casa> newCasa = new HashMap<>();
        for(String NIF: this.casas.keySet()){
            newCasa.put(NIF, this.casas.get(NIF).clone());
        }
        return newCasa;
    }

    public void setCasas(HashMap<String, Casa> casas){
        Map<String, Casa> newCasa = new HashMap<>();
        for(String NIF: casas.keySet()){
            newCasa.put(NIF, casas.get(NIF).clone());
        }
        this.casas = newCasa;
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

    public Map<String, List<String>> getFornecedores() {
        Map<String, List<String>> newFornecedores = new HashMap<>();
        for(String f: this.fornecedores.keySet()) {
            List<String> lista = this.fornecedores.get(f);
            List<String> novaLista = new ArrayList<String>();
            ListIterator<String> iter = lista.listIterator();
            while(iter.hasNext())
                novaLista.add(iter.next());
            newFornecedores.put(f, novaLista);
        }
        return newFornecedores;
    }

    public void setFornecedores(Map<String, List<String>> fornecedores) {
        Map<String, List<String>> newFornecedores = new HashMap<>();
        for(String f: fornecedores.keySet()) {
            List<String> lista = fornecedores.get(f);
            List<String> novaLista = new ArrayList<String>();
            ListIterator<String> iter = lista.listIterator();
            while(iter.hasNext())
                novaLista.add(iter.next());
            newFornecedores.put(f, novaLista);
        }
        this.fornecedores = newFornecedores;
    }
}
