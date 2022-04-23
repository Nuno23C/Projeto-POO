import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;

import java.util.ArrayList;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SmartDevice d1 = new SmartBulb("Lampada1", SmartDevice.Estado.ON, SmartBulb.Tonalidade.NEUTRAL);
        SmartDevice d2 = new SmartCamera("Camera1",SmartDevice.Estado.ON,700,2000);
        SmartDevice d3 = new SmartSpeaker("Coluna1", SmartDevice.Estado.ON, "Sony", 12, "RFM", 20);
        ArrayList<SmartDevice> dispositivos = new ArrayList<>();
        dispositivos.add(d1);
        dispositivos.add(d2);
        dispositivos.add(d3);

        for(SmartDevice d: dispositivos) {
            System.out.println(d);
        }

        List<String> ids = new ArrayList<>();
        Map<String, List<String>> divisoes = new HashMap<>();

    }
}

