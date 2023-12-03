package project;

import java.util.Scanner;

public class Game {

    Dice dice = new Dice();
    Scanner input = new Scanner(System.in);
    String[][] scoreTable = {
            {"Category", "Player 1", "Player 2"},
            {"Ones", "", ""},
            {"Twos", "", ""},
            {"Threes", "", ""},
            {"Fours", "", ""},
            {"Fives", "", ""},
            {"Sixes", "", ""},
            {"Sequence", "", ""},
            {"Total", "", ""},
    };
    int[] dices = new int[5];
    int deferCount = 0;

    boolean[] selectedCategoriesPlayer1 = new boolean[8];
    boolean[] selectedCategoriesPlayer2 = new boolean[8];

    public void welcome() {
        System.out.println("Would you like to enter your username?(Y/N) ");
        String choice = input.nextLine().toLowerCase();
        if (choice.equals("y")) {
            for (int i = 0; i < 2; i++) {
                System.out.println("Please enter " + (i + 1) + " username: ");
                scoreTable[0][i + 1] = input.next();
            }
        } else if (choice.equals("n")) {
            System.out.println("Okay, going directly to the game");
        }
    }

  public void play() {
      for (int i = 1; i <= 8 ;i++) {
          
          int throwsLeft = 3;
          int category = 0;
          int currentPlayer = 1;
          if (i % 2 == 0) { 
              currentPlayer = 2;
          }
              while(throwsLeft > 0) {
              if (throwsLeft == 3) {
                  System.out.println(scoreTable[0][currentPlayer] + " you threw: ");
                  rollFive();
                  showDices();
                  System.out.println("\nOptions \naside some dices(1) \ndefer a throw(2) ");
                  String choosen = input.next();
                  //SETTING ASIDE FROM A START
                  if (choosen.equals("1")) {
                      System.out.println("First sleect a category(1-7): ");
                      category = input.nextInt();
                      System.out.println("You chose " + scoreTable[category][0]);
                      System.out.println("Enter how many numbers you are going to set aside: ");
                      int aside = input.nextInt();
                      int[] save = new int[aside];

                      for (int j = 0; j < aside; j++) {
                          System.out.print("Enter index of a number you want to save(0-4): ");
                          int temp = input.nextInt();
                          save[j] = dices[temp];
                      }
                      rollFive();
                      for (int x = 0; x < save.length; x++) {
                          dices[x] = save[x];
                      }
                      System.out.println("Your new set is:");
                      showDices();
                      throwsLeft--;
                  }
                  //DEFERING A THROW
                  else if(choosen.equals("2")){
                      rollFive();
                      System.out.println("Your new dices are:");
                      showDices();
                      throwsLeft--;
                      System.out.println("First sleect a category(1-7): ");
                      category = input.nextInt();
                      System.out.println("Category seclected: "+ scoreTable[category][0]);
                  }
                  //throws 2 and 3
              } 
              else {
                  System.out.println("Options \naside some dices(1) \ndefer a throw(2) ");
                  String choosen = input.next();
                  if (choosen.equals("1")) {
                      
                      System.out.println("How many dices would you like to save? ");
                      int saved = input.nextInt();
                      int[] save = new int[saved];
                      for (int j = 0; j < saved; j++) {
                          System.out.print("Enter index of a number you want to save(0-4): ");
                          int temp = input.nextInt();
                          save[j] = dices[temp];
                      }
                      rollFive();
                      for (int x = 0; x < save.length; x++) {
                          dices[x] = save[x];
                      }
                      System.out.println("Your new set is:");
                      showDices();
                      throwsLeft--;
                  }
                  else if(choosen.equals("2")){
                      rollFive();
                      System.out.println("Your new dices are:");
                      showDices();
                      throwsLeft--;
                  }
              }

      }
          System.out.println("Your turn ended, here is updated score Table:");
          System.out.println("You used all your additional throws");
                  System.out.println("Your final dices are: ");
                  showDices();
                  if(category == 7){
                      scoreTable[category][currentPlayer] =Integer.toString(addUnique(dices));
                  }
                  else{
                      scoreTable[category][currentPlayer] = Integer.toString(addDices(dices, category));
                  }
                  System.out.println();
                    printScoreTable();
                  if(i ==8){ calculateScore();
                  }
      }
  }
   






    public void printScoreTable() {
        System.out.println("------------------------------------");
        for (String[] scoreTable1 : scoreTable) {
            for (String scoreTable11 : scoreTable1) {
                System.out.printf("| %-9s ", scoreTable11);
            }
            System.out.println("|");
            System.out.println("------------------------------------");
        }

        System.out.println();
    }
   

    public void rollFive() {
        for (int j = 0; j < 5; j++) {
            dices[j] = dice.Roll();
        }
    }

    public void showDices() {
        for (int i = 0; i < dices.length; i++) {
            System.out.print(dices[i] + " ");
        }
    }
   

  

    public static int addDices(int[] arr, int target) {
        int sum = 0;
        for (int i : arr) {
            if(i == target){
                sum+=target;
            }
        }
        return sum;
    }

    public void calculateScore() {
        for (int player = 1; player <= 2; player++) {
            int totalPoints = 0;

            for (int row = 1; row <= 6; row++) {
                if (!scoreTable[row][player].isEmpty()) {
                    totalPoints += Integer.parseInt(scoreTable[row][player]);
                }
            }

            scoreTable[7][player] = String.valueOf(totalPoints);
        }

        printScoreTable();
    }
    public void writeScore(int sum, int row, int player ){
         scoreTable[row][player] = Integer.toString(sum);
        
    }
    private static int addUnique(int[] numbers) {
        int sum = 0;
        boolean[] seen = new boolean[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            if (!seen[i]) {
                sum += numbers[i];

                // Mark other occurrences of the same number as seen
                for (int j = i + 1; j < numbers.length; j++) {
                    if (numbers[i] == numbers[j]) {
                        seen[j] = true;
                    }
                }
            }
        }

        return sum;
    }
}