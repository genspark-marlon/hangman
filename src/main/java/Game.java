import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Game {
  private String randomWord;
  private HashMap<Character, Integer> letterOccurrences;
  private HashMap<Character, ArrayList<Integer>> letterIndexes;
  private StringBuilder userGuess;
  private StringBuilder incorrectGuesses;
  private String tempUserGuess;
  private String userDecision;
  private int maxGuesses;
  private int guessCount;
  private Scanner iStream;
  private PrintStream pStream;

  public Game(InputStream iStream, PrintStream pStream) {
    this.iStream = new Scanner(iStream);
    this.pStream = pStream;
    this.letterOccurrences = new HashMap<Character, Integer>();
    this.letterIndexes = new HashMap<Character, ArrayList<Integer>>();
    this.userGuess = new StringBuilder();
    this.incorrectGuesses = new StringBuilder();
    this.maxGuesses = 8;
    this.guessCount = 0;
  }

  public Game () {
    this.letterOccurrences = new HashMap<Character, Integer>();
    this.letterIndexes = new HashMap<Character, ArrayList<Integer>>();
    this.userGuess = new StringBuilder();
    this.incorrectGuesses = new StringBuilder();
    this.maxGuesses = 8;
    this.guessCount = 0;
    if (this.iStream == null) {
      this.iStream = new Scanner(System.in);
    }
    if (this.pStream == null) {
      this.pStream = System.out;
    }
  }

  public int incrementGuessCount () {
    this.guessCount++;
    return this.guessCount;
  }

  public String getUserGuess () {
    this.pStream.println(String.format("You've got %s guesses left. Take a guess.", this.maxGuesses - this.guessCount));
    this.tempUserGuess = this.iStream.nextLine();
    this.judgeUserGuess();
    return this.tempUserGuess;
  }

  public String judgeUserGuess () {
    String result = "";
    if (!this.randomWord.contains(this.tempUserGuess)) {
      result = "Your guess was incorrect.";
      this.pStream.println(result);
      this.insertIncorrectGuess();
    } else {
      result = "Your guess was correct.";
      this.pStream.println(result);
      this.insertCorrectGuess();
    }
    this.getGameResults();
    return result;
  }

  public String insertIncorrectGuess () {
    this.incorrectGuesses.append(this.tempUserGuess);
    this.guessCount++;
    return this.tempUserGuess;
  }

  public String insertCorrectGuess () {
    if (!this.letterOccurrences.isEmpty()) {
      while (this.letterOccurrences.get(this.tempUserGuess.charAt(0)) > 0) {
        ArrayList<Integer> listOfIndexes = this.letterIndexes.get(this.tempUserGuess.charAt(0));
        int firstOccurence = listOfIndexes.get(0);
        this.userGuess.replace(firstOccurence, firstOccurence + 1, this.tempUserGuess);
        listOfIndexes.remove(0);
        this.letterOccurrences.replace(this.tempUserGuess.charAt(0), this.letterOccurrences.get(this.tempUserGuess.charAt(0)) - 1);
      }
    }
    return this.tempUserGuess;
  }

  public void generateRandomWord () {
    String[] words = {"puppy", "pool", "avalanche"};
    this.randomWord = words[(int) Math.floor(Math.random() * words.length)];
    for(int i = 0; i < this.randomWord.length(); i++) {
      char letter = this.randomWord.charAt(i);
      this.userGuess.append("_");
      if (this.letterOccurrences.containsKey(letter)) {
        this.letterOccurrences.replace(letter, this.letterOccurrences.get(letter) + 1);
      } else {
        this.letterOccurrences.put(letter, 1);
      }
      if (this.letterIndexes.containsKey(letter)) {
        ArrayList<Integer> listOfIndexes = this.letterIndexes.get(letter);
        listOfIndexes.add(i);
        this.letterIndexes.put(letter, listOfIndexes);
      } else {
        ArrayList<Integer> freshListOfIndexes = new ArrayList<Integer>();
        freshListOfIndexes.add(i);
        this.letterIndexes.put(letter, freshListOfIndexes);
      }
    }
  }

  // method for testing only
  public void setRandomWord (String str) {
    this.randomWord = str;
  }

  // method for testing only
  public void setTempUserGuess (String str) {
    this.tempUserGuess = str;
  }

  public void getGameResults () {
    System.out.println(this.incorrectGuesses);
    System.out.println(this.userGuess);
    System.out.println(this.guessCount);
    if (this.guessCount == 1) {
      this.pStream.println("\n_________" + "\n|" + "\n|" + "\n|" + "\n|" + "\n|" + "\n|_______________________\n");
    } else if (this.guessCount == 2) {
      this.pStream.println("\n_________" + "\n|                   |" + "\n|" + "\n|" + "\n|" + "\n|" + "\n|_______________________\n");
    } else if (this.guessCount == 3) {
      this.pStream.println("\n_________" + "\n|                   |" + "\n|                  O" + "\n|" + "\n|" + "\n|" + "\n|_______________________\n");
    } else if (this.guessCount == 4) {
      this.pStream.println("\n_________" + "\n|                   |" + "\n|                  O" + "\n|                   |" + "\n|" + "\n|" + "\n|_______________________\n");
    } else if (this.guessCount == 5) {
      this.pStream.println("\n_________" + "\n|                   |" + "\n|                  O" + "\n|               ---|" + "\n|" + "\n|" + "\n|_______________________\n");
    } else if (this.guessCount == 6) {
      this.pStream.println("\n_________" + "\n|                   |" + "\n|                  O" + "\n|               ---|---" + "\n|" + "\n|" + "\n|_______________________\n");
    } else if (this.guessCount == 7) {
      this.pStream.println("\n_________" + "\n|                   |" + "\n|                  O" + "\n|               ---|---" + "\n|                  /" + "\n|                /" + "\n|_______________________\n");
    } else if (this.guessCount == 8) {
      this.pStream.println("\n_________" + "\n|                   |" + "\n|                  O" + "\n|               ---|---" + "\n|                  /\\" + "\n|                /    \\" + "\n|_______________________");
    }

    this.pStream.println("Your incorrect guesses: " + this.incorrectGuesses.toString());
    this.pStream.println("Your correct guesses thus far: " + this.userGuess.toString());
  }

  public void initialize () {
    this.generateRandomWord();
    this.playHangman();
  }

  public void playHangman () {
    do {
      this.getUserGuess();
    } while (this.guessCount < this.maxGuesses && this.userGuess.indexOf("_") != -1);
    this.shutdown();
  }

  public void shutdown () {
    System.out.println("The game is over because you've lost or won.");
  }
}
