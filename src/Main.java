import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {

        Menu mainMenu = new Menu();

/*
        LocalDateTime dataInicial = LocalDateTime.now();
        System.out.println(dataInicial);

        Period p1 = Period.ofDays(10);

        LocalDateTime dataPretendida = dataInicial.plusDays(p1.getDays());
        System.out.println(dataPretendida);

        SmartDevice Lamp_1 = new SmartBulb("Lampada1", SmartBulb.Estado.ON, SmartBulb.Tonalidade.NEUTRAL, 20);
        SmartDevice Cam_1 = new SmartCamera("Camera1", SmartCamera.Estado.ON, 1080, dataInicial);
        SmartDevice Speak_1 = new SmartSpeaker("Coluna1", SmartSpeaker.Estado.ON, "Sony", 12, "RFM", 10);
        SmartDevice Lamp_2 = new SmartBulb("Lampada2", SmartBulb.Estado.ON, SmartBulb.Tonalidade.NEUTRAL, 30);
        SmartDevice Lamp_3 = new SmartBulb("Lampada3", SmartBulb.Estado.ON, SmartBulb.Tonalidade.NEUTRAL, 15);


        Casa casa1 = new Casa("casa1", "Rua das bananas azuis", "Jacinto", "123456789");
        casa1.add_Divisao("Quarto");
        casa1.add_Dispositivo_NaCasa("Quarto", Lamp_1);
        casa1.add_Dispositivo_NaCasa("Quarto", Speak_1);

        casa1.add_Divisao("Sala");
        casa1.add_Dispositivo_NaCasa("Sala", Cam_1);
        casa1.add_Dispositivo_NaCasa("Sala", Lamp_2);

        System.out.println(casa1.toString());

        // System.out.println("------------------------------------");
        // System.out.println(casa1.toString());
*/
    }
}

