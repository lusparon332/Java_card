import org.junit.Test;

public class ClientCreationsTest {

    @Test
    public void birthDateTestWrongs() {
        String date = "20.20.2000";
        assert !Main.isCorrectBirthDate(date);

        date = "12.12.1800";
        assert !Main.isCorrectBirthDate(date);

        date = "02.30.2002";
        assert !Main.isCorrectBirthDate(date);

        date = "02.29.2001";
        assert !Main.isCorrectBirthDate(date);

        date = "09.31.1999";
        assert !Main.isCorrectBirthDate(date);

        date = "09.31.1999";
        assert !Main.isCorrectBirthDate(date);

        date = "05.05.2015";
        assert !Main.isCorrectBirthDate(date);

        date = "12.1.1996";
        assert !Main.isCorrectBirthDate(date);

        date = "1.1.1996";
        assert !Main.isCorrectBirthDate(date);

        date = "11.231.192454";
        assert !Main.isCorrectBirthDate(date);

        date = "00.00.1981";
        assert !Main.isCorrectBirthDate(date);

        date = "Hello, World!";
        assert !Main.isCorrectBirthDate(date);
    }

    @Test
    public void birthDateTestCorrects() {
        String date = "01.01.2000";
        assert Main.isCorrectBirthDate(date);

        date = "02.29.2000";
        assert Main.isCorrectBirthDate(date);

        date = "03.31.1989";
        assert Main.isCorrectBirthDate(date);

        date = "12.12.2007";
        assert Main.isCorrectBirthDate(date);

        date = "08.31.1971";
        assert Main.isCorrectBirthDate(date);
    }

}
