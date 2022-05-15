import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Main implements Serializable {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        Cidade cidade = new Cidade();
        Menu mainMenu = new Menu(cidade, scan);
    }
}

