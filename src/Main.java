import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        LocalDateTime dataInicial = LocalDateTime.now();
        System.out.println(dataInicial);

        Period p1 = Period.ofDays(10);

        LocalDateTime dataPretendida = dataInicial.plusDays(p1.getDays());
        System.out.println(dataPretendida);

        SmartDevice d1 = new SmartBulb("Lampada1", SmartBulb.Estado.ON, 30, SmartBulb.Tonalidade.NEUTRAL);
        SmartDevice d2 = new SmartCamera("Camera1",SmartCamera.Estado.ON, 1080, dataInicial);
        SmartDevice d3 = new SmartSpeaker("Coluna1", SmartSpeaker.Estado.ON, "Sony", 12, "RFM", 20);

        /*
        ArrayList<SmartDevice> dispositivos = new ArrayList<>();
        dispositivos.add(d1);
        dispositivos.add(d2);
        dispositivos.add(d3);

        for(SmartDevice d: dispositivos) {
            System.out.println(d);
        }
        */

        List<String> idsQuarto = new ArrayList<>();
        List<String> idsSala = new ArrayList<>();
        Map<String, List<String>> divisoes = new HashMap<>();
        idsQuarto.add(d1.getId());
        idsQuarto.add(d2.getId());
        divisoes.put("Quarto", idsQuarto);
        idsSala.add(d3.getId());
        divisoes.put("Sala", idsSala);

        Map<String, SmartDevice> dispositivos = new HashMap<>();
        dispositivos.put(d1.getId(), d1);
        dispositivos.put(d2.getId(), d2);
        dispositivos.put(d3.getId(), d3);

        for(String d: dispositivos.keySet()) {
            System.out.println(dispositivos.get(d));
        }

        for(String div: divisoes.keySet()) {
            System.out.println(divisoes.get(div));
        }

        Casa casa1 = new Casa("Rua", "Jacinto", "123", dispositivos, divisoes);

        Map<String, Casa> conj_Casas = new HashMap<>();
        conj_Casas.put("Casa1", casa1);

        //casa1.turn_Off_Divisao("Quarto");
        //casa1.turn_Off_Casa();
        //d1.turnOff();

        System.out.println("--------------------------------------");

        for(String d: dispositivos.keySet()) {
            System.out.println(dispositivos.get(d));
        }

        for(String div: divisoes.keySet()) {
            System.out.println(divisoes.get(div));
        }
    }
}

