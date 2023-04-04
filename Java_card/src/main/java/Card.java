import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

public class Card {
    Long ownerID;
    String cardNum;
    Calendar issueDate;
    Calendar expDate;

    public Card(Long ownerID, Integer getDuration) {
        Integer duration = getDuration;
        if (getDuration < 1)
            duration = 1;
        if (getDuration > 15)
            duration = 15;
        this.ownerID = ownerID;

        this.issueDate = new GregorianCalendar();

        Calendar date = new GregorianCalendar();
        date.add(Calendar.YEAR, duration);
        this.expDate = date;

        do {
            this.cardNum = "2202 20" + ThreadLocalRandom.current().nextInt(0, 10) +
                    ThreadLocalRandom.current().nextInt(0, 10);
            for (int i = 0; i < 2; i++) {
                this.cardNum += " ";
                for (int j = 0; j < 4; j++)
                    this.cardNum += ThreadLocalRandom.current().nextInt(0, 10);
            }
        } while (!isUniqueCardNum());
    }
    private boolean isUniqueCardNum() {
        for (Client client: Main.clients.values())
            for (Card card: client.cards)
                if (this.cardNum.equalsIgnoreCase(card.cardNum))
                    return false;
        return true;
    }
}
