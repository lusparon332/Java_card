import org.junit.Test;

import java.util.Calendar;

public class CardTest {

    @Test
    public void datesBoundsTest() {
        Card card = new Card(Long.valueOf(0), 0);
        card.issueDate.add(Calendar.YEAR, 1);
        assert card.issueDate.get(Calendar.YEAR) == card.expDate.get(Calendar.YEAR);

        card = new Card(Long.valueOf(0), -777);
        card.issueDate.add(Calendar.YEAR, 1);
        assert card.issueDate.get(Calendar.YEAR) == card.expDate.get(Calendar.YEAR);

        card = new Card(Long.valueOf(0), 25);
        card.issueDate.add(Calendar.YEAR, 15);
        assert card.issueDate.get(Calendar.YEAR) == card.expDate.get(Calendar.YEAR);

        card = new Card(Long.valueOf(0), 20);
        card.issueDate.add(Calendar.YEAR, 15);
        assert card.issueDate.get(Calendar.YEAR) == card.expDate.get(Calendar.YEAR);

        card = new Card(Long.valueOf(0), 777);
        card.issueDate.add(Calendar.YEAR, 15);
        assert card.issueDate.get(Calendar.YEAR) == card.expDate.get(Calendar.YEAR);
    }

    @Test
    public void issueExpDatesTest() {
        Card card = new Card(Long.valueOf(0), 5);
        assert card.issueDate.before(card.expDate);

        card = new Card(Long.valueOf(0), 1);
        assert card.issueDate.before(card.expDate);

        card = new Card(Long.valueOf(0), 0);
        assert card.issueDate.before(card.expDate);

        card = new Card(Long.valueOf(0), 15);
        assert card.issueDate.before(card.expDate);

        card = new Card(Long.valueOf(0), 20);
        assert card.issueDate.before(card.expDate);

        card = new Card(Long.valueOf(0), 777);
        assert card.issueDate.before(card.expDate);
    }

    @Test
    public void cardsNumTest() {
        Card card = new Card(Long.valueOf(0), 5);
        assert card.cardNum.length() == 19;
        assert card.cardNum.charAt(4) == ' ' && card.cardNum.charAt(9) == ' ' && card.cardNum.charAt(14) == ' ';

        card = new Card(Long.valueOf(0), 0);
        assert card.cardNum.length() == 19;
        assert card.cardNum.charAt(4) == ' ' && card.cardNum.charAt(9) == ' ' && card.cardNum.charAt(14) == ' ';

        card = new Card(Long.valueOf(0), 20);
        assert card.cardNum.length() == 19;
        assert card.cardNum.charAt(4) == ' ' && card.cardNum.charAt(9) == ' ' && card.cardNum.charAt(14) == ' ';
    }
}
