import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;
import java.time.LocalDateTime;
import java.time.Period;

public class Main {
    public static void main(String[] args) {
        LocalDateTime dataInicial = LocalDateTime.now();
        System.out.println(dataInicial);

        Period p1 = Period.ofDays(10);

        LocalDateTime dataPretendida = dataInicial.plusDays(p1.getDays());
        System.out.println(dataPretendida);

        SmartDevice d1 = new SmartBulb("Lampada1", SmartBulb.Estado.ON, 30, SmartBulb.Tonalidade.NEUTRAL);
        SmartDevice d2 = new SmartCamera("Camera1", SmartCamera.Estado.ON, 1080, dataInicial);
        SmartDevice d3 = new SmartSpeaker("Coluna1", SmartSpeaker.Estado.ON, "Sony", 12, "RFM", 10);

        Casa casa1 = new Casa("Rua da Bouça", "Nuno", "258272821");
        casa1.add_Divisao("Quarto");
        casa1.add_Dispositivo_NaCasa("Quarto", d1);
        casa1.add_Dispositivo_NaCasa("Quarto", d2);
        casa1.add_Divisao("Sala");
        casa1.add_Dispositivo_NaCasa("Sala", d3);

        System.out.println(casa1.toString());

        casa1.remove_Desivao("Quarto");

        System.out.println("------------------------------------");

        System.out.println(casa1.toString());
    }

}

