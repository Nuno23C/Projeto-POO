import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        LocalDateTime dataAtual = LocalDateTime.now();
        System.out.println(dataAtual);

        Period p1 = Period.ofDays(10);

        LocalDateTime dataPretendida = dataAtual.plusDays(p1.getDays());
        System.out.println(dataPretendida);

        SmartDevice d1 = new SmartBulb("Lampada1", SmartDevice.Estado.ON, SmartBulb.Tonalidade.NEUTRAL);
        SmartDevice d2 = new SmartCamera("Camera1",SmartDevice.Estado.ON, 1080, dataAtual, dataPretendida);
        SmartDevice d3 = new SmartSpeaker("Coluna1", SmartDevice.Estado.ON, "Sony", 12, "RFM", 20);

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
    }
}

