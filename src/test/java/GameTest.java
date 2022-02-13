import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class GameTest {
  private ByteArrayInputStream iStream;
  private ByteArrayOutputStream oStream;
  private PrintStream pStream;
  private Game GameTest;

  @BeforeEach
  public void setupTest () {
    this.oStream = new ByteArrayOutputStream();
    this.pStream = new PrintStream(this.oStream);
  }

  @Test
  public void incrementGuessCountTest () {
    this.GameTest = new Game(System.in, System.out);
    this.GameTest.incrementGuessCount(); 
    int expected = 2;
    int actual = this.GameTest.incrementGuessCount();
    assertEquals(expected, actual);
  }

  @Test
  public void getUserGuessTest () {
    this.iStream = new ByteArrayInputStream("f\n".getBytes());
    this.GameTest = new Game(this.iStream, this.pStream);
    this.GameTest.generateRandomWord();
    String expected = "f";
    String actual = this.GameTest.getUserGuess();
    assertEquals(expected, actual);
  }

  @Test
  public void judgeUserGuessTest () {
    this.GameTest = new Game(System.in, System.out);
    this.GameTest.setRandomWord("random");
    this.GameTest.setTempUserGuess("a");
    String expected = "Your guess was correct.";
    String actual = this.GameTest.judgeUserGuess();
    assertEquals(expected, actual);
  }

  @Test
  public void insertIncorrectGuessTest () {
    this.GameTest = new Game(System.in, System.out);
    this.GameTest.setTempUserGuess("o");
    String expected = "o";
    String actual = this.GameTest.insertIncorrectGuess();
    assertEquals(expected, actual);
  }

  @Test
  public void insertCorrectGuessTest () {
    this.GameTest = new Game(System.in, System.out);
    this.GameTest.setTempUserGuess("o");
    String expected = "o";
    String actual = this.GameTest.insertCorrectGuess();
    assertEquals(expected, actual);
  }

  @Test
  public void getGameResultsTest () {
    this.GameTest = new Game(System.in, System.out);
    this.GameTest.setGuessCount(4);
    String expected = "\n_________" + "\n|                   |" + "\n|                  O" + "\n|                   |" + "\n|" + "\n|" + "\n|_______________________\n";
    String actual = this.GameTest.getGameResults();
    assertEquals(expected, actual);
  }

  @AfterEach
  public void destroyTest () {
    this.iStream = null;
    this.oStream = null;
    this.pStream = null;
    this.GameTest = null;
  }
}
