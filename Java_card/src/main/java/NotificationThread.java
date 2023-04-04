import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NotificationThread extends Thread {

    public void run() {
        while (true) {
            Calendar now = new GregorianCalendar();
            for (Long clientID: Main.clients.keySet()) {
                Client curClient = Main.clients.get(clientID);
                for (int i = 0; i < curClient.cards.size(); i++) {
                    if (curClient.cards.get(i).expDate.before(now)) {
                        curClient.cards.remove(i);
                        Main.addCard(clientID, 5);
                        sendNotification(clientID);
                        break;
                    }
                }
            }
        }
    }

    public void sendNotification(Long clientID) {
        try(FileWriter writer = new FileWriter("notifications.txt", true))
        {
            String text = (new GregorianCalendar()).getTime() +
                    ": Client with ID " + clientID +
                    " has expired card. The card was closed, a new one was created instead.\n";
            writer.append(text);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
