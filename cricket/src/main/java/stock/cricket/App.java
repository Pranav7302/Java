package stock.cricket;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Scanner;
public class App 
{
	public static int randomNumber()
	{
     Random random = new Random();
     int random_number = random.nextInt(10) + 1;
     return random_number;
	}
	public static int tossNumber()
	{
     Random random = new Random();
     int random_number = random.nextInt(2) + 1;
     return random_number;
	}
    public static void main( String[] args ) throws NamingException, SQLException
    {
    	  Scanner scanner = new Scanner(System.in);
    	  ArrayList<String> toss = new ArrayList<>();
    	  toss.add("Head");
    	  toss.add("Tail");
          int userOut = 1,userTotal=0,compOut=1,compTotal=0;
          System.out.println("Let's Start");
          System.out.println("Select Heads/Tails");
          System.out.println("1.Heads");
          System.out.println("2.Tails");
          int choice = scanner.nextInt();
          int tossWinner = tossNumber();
          if(choice == tossWinner)
          {
        	  System.out.println("Select Bat/Bowl");
              System.out.println("1.Bat");
              System.out.println("2.Bowl");
              int userChoice = scanner.nextInt();
              if(userChoice == 1) {
                  System.out.println("You chose Batting");
                  do {
                      System.out.println("Enter ur number for batting:");
                      int bat = scanner.nextInt();
                      int bowl = randomNumber();
                      System.out.println("Computer :" + bowl);
                      if (bat != bowl) {
                          userTotal += bat;
                      } else {
                          System.out.println("Out!!!");
                          userOut = 0;
                      }
                  } while (userOut != 0);
                  System.out.println("Batting score : " + userTotal);
                  System.out.println("You are Bowling");
                  do {
                      if (compTotal <= userTotal) {
                          if (compTotal == userTotal) {
                              System.out.println("Match is in tie...");
                          }
                          System.out.println("Enter ur number for bowling:");
                          int bowl = scanner.nextInt();
                          int bat = randomNumber();
                          System.out.println("Computer :" + bat);
                          if (bowl != bat) {
                              compTotal += bat;
                          } else {
                              System.out.println("Out!!!");
                              if (compTotal < userTotal) {
                                  System.out.println("Computer score:"+compTotal);
                                  System.out.println("You won");
                              }
                              compOut = 0;
                          }
                      }
                      else
                      {
                          System.out.println("Computer score:"+compTotal);
                          System.out.println("Computer won");
                          compOut =0;
                      }
                  } while (compOut != 0);
                  if (compTotal == userTotal) {
                      System.out.println("Match is tied");
                  }
              }
              else
              {
                  System.out.println("You chose bowling");
                  do {
                      System.out.println("Enter ur number for bowling:");
                      int bowl = scanner.nextInt();
                      int bat = randomNumber();
                      System.out.println("Computer :"+bat);
                      if(bat != bowl)
                      {
                          compTotal+=bat;
                      }
                      else
                      {
                          System.out.println("Out!!!");
                          compOut=0;
                      }
                  }while(compOut!=0);
                  System.out.println("Computer Score :"+compTotal);
                  System.out.println("You are Batting");
                  do {
                      if(userTotal<=compTotal) {
                          if(compTotal == userTotal)
                          {
                              System.out.println("Match is in tie...");
                          }
                          System.out.println("Enter ur number for batting:");
                          int bowl = randomNumber();
                          int bat = scanner.nextInt();
                          System.out.println("Computer :" + bowl);
                          if (bat != bowl) {
                              userTotal += bat;
                          } else {
                              System.out.println("Out!!!");
                              System.out.println("Your Total :"+userTotal);
                              if(userTotal<compTotal) {
                                  System.out.println("Your score:"+userTotal);
                                  System.out.println("Computer won");
                              }
                              userOut = 0;
                          }
                      }
                      else {
                          System.out.println("Your score:"+userTotal);
                          System.out.println("You won");
                          userOut =0;
                      }
                  }while(userOut!=0);
                  if(compTotal == userTotal)
                  {
                      System.out.println("Match is tied");
                  }
              }
          }
          else
          {
              System.out.println("Computer won the toss");
              if(tossNumber() == 1)
              {
                  System.out.println("Computer chose batting");
                  do {
                      System.out.println("Enter ur number for bowling:");
                      int bowl = scanner.nextInt();
                      int bat = randomNumber();
                      System.out.println("Computer :"+bat);
                      if(bat != bowl)
                      {
                          compTotal+=bat;
                      }
                      else
                      {
                          System.out.println("Out!!!");
                          compOut=0;
                      }
                  }while(compOut!=0);
                  System.out.println("Computer score:"+compTotal);
                  System.out.println("You are Batting");
                  do {
                      if(userTotal<=compTotal) {
                          if(compTotal == userTotal)
                          {
                              System.out.println("Match is in tie...");
                          }
                          System.out.println("Enter ur number for batting:");
                          int bowl = randomNumber();
                          int bat = scanner.nextInt();
                          System.out.println("Computer :" + bowl);
                          if (bat != bowl) {
                              userTotal += bat;
                          } else {
                              System.out.println("Out!!!");
                              if(userTotal<compTotal) {
                                  System.out.println("Your score:"+userTotal);
                                  System.out.println("Computer won");
                              }
                              userOut = 0;
                          }
                      }
                      else {
                          System.out.println("Your score:"+userTotal);
                          System.out.println("You won");
                          userOut =0;
                      }
                  }while(userOut!=0);
                  if(compTotal == userTotal)
                  {
                      System.out.println("Match is tied");
                  }
              }
              else
              {
                  System.out.println("Computer chose Bowling");
                  do {
                      System.out.println("Enter ur number for batting:");
                      int bat = scanner.nextInt();
                      int bowl = randomNumber();
                      System.out.println("Computer :" + bowl);
                      if (bat != bowl) {
                          userTotal += bat;
                      } else {
                          System.out.println("Out!!!");
                          userOut = 0;
                      }
                  } while (userOut != 0);
                  System.out.println("Batting score : " + userTotal);
                  System.out.println("You are Bowling");
                  do {
                      if (compTotal <= userTotal) {
                          if (compTotal == userTotal) {
                              System.out.println("Match is in tie...");
                          }
                          System.out.println("Enter ur number for bowling:");
                          int bowl = scanner.nextInt();
                          int bat = randomNumber();
                          System.out.println("Computer :" + bat);
                          if (bat != bowl) {
                              compTotal += bat;
                          } else {
                              System.out.println("Out!!!");
                              if (compTotal < userTotal) {
                                  System.out.println("Your score:"+compTotal);
                                  System.out.println("You won");
                              }
                              compOut = 0;
                          }
                      } else {
                          System.out.println("Your score:"+compTotal);
                          System.out.println("Computer won");
                          compOut=0;
                      }
                  } while (compOut != 0);
                  if (compTotal == userTotal) {
                      System.out.println("Match is tied");
                  }
              }
          }
    }
}
