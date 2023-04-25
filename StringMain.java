import java.util.ArrayList;
import java.util.Scanner;

public class StringMain {
    static void stringDuplicate(String str) {
        String string = str.toLowerCase();
        boolean duplicates = true;
        //  string.replaceAll(" ","");
        //  string.toLowerCase();
        char[] chars = string.toCharArray();
        int i, j, count = 0;
        System.out.println("The duplicate characters are: ");
        for (i = 0; i < chars.length; i++) {
            for (j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    count++;
//                    if(chars[i]!='0') {
//                        System.out.println(chars[i]);
//                    }
                    chars[j] = '0';
                    duplicates = false;
                }
            }
            if (count > 0 && chars[i] != '0') {
                System.out.println(chars[i]);
            }
            count = 0;
        }
        if (duplicates) {
            System.out.println("There is no duplicates");
        }
    }
    static  void stringReverse(String str)
    {
        if(str.length()>0)
        {
            System.out.print(str.substring(str.length()-1));
            stringReverse(str.substring(0,str.length()-1));
        }
    }

    public static void stringCount(String string) {
        int i,j,count=1;
        char[] chars = string.toLowerCase().toCharArray();
        for(i=0;i<chars.length;i++)
        {
            for(j=i+1;j<chars.length;j++)
            {
                if(chars[i] == chars[j] && chars[i]!='0')
                {
                    count++;
                    chars[j] = '0';
                }
            }
            if(chars[i]!='0' && chars[i]!=' ') {
                System.out.println("Character: " + chars[i] + " has occured " + count + " times");
            }
            count=1;
        }
    }
    public static String swap(String str,int a,int b)
    {
        char[] arr = str.toCharArray();
        char ch = arr[a];
        arr[a] = arr[b];
        arr[b] = ch;
        return String.valueOf(arr);
    }
    public static void generatePermutations(String str,int start,int end)
    {
        int i;
        if(start == end-1)
        {
            System.out.println(str);
        }
        else
        {
            for (i = start; i < end; i++)
            {
                str = swap(str, start, i);
                generatePermutations(str, start + 1, end);
            }
        }
    }
    public static boolean characterChecker1(ArrayList<Character> characterArrayList,char check)
    {
        int i,count = 1;
        for(i=0;i<characterArrayList.size();i++)
        {
            if (check == characterArrayList.get(i)) {
                count = 0;
            }
        }

        if(count == 0) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Enter your choice");
            System.out.println("1.Print duplicates of a string");
            System.out.println("2.Reverse string using recursion");
            System.out.println("3.Count the characters");
            System.out.println("4.Find the permutations of the string");
            System.out.println("5.Find the maximum count of substring without repeated characters");
            System.out.println("6.Making the strings same length and appending them");
            System.out.println("7.Exit");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: {
                    System.out.println("Enter the string");
                    String string = scanner.nextLine();
                    stringDuplicate(string);
                }
                break;
                case 2: {
                    System.out.println("Enter the string");
                    String string = scanner.nextLine();
                    System.out.println("The reversed string is");
                    stringReverse(string);
                    System.out.println(" ");
                }
                break;
                case 3: {
                    System.out.println("Enter the string");
                    String string = scanner.nextLine();
                    stringCount(string);
                }
                break;
                case 4: {
                    System.out.println("Enter the string");
                    String str = scanner.nextLine();
                    char[] chars = str.toCharArray();
                    System.out.println("The permutations are: ");
                    generatePermutations(str,0,chars.length);
                }
                break;
                case 5:{
//                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter the string");
                    String string = scanner.nextLine();
                    char[] chars = string.toCharArray();
                    ArrayList<Character> characterArrayList = new ArrayList<>();
                    ArrayList<Character> longestSubString = new ArrayList<>();
                    char check;
                    int count =0;
                    int maxCount =-1;
                    int i;
                    int a=0;
                    for(i=0;i<chars.length;i++) {
                        check = chars[i];
                        if (characterChecker1(characterArrayList, check)) {
                            characterArrayList.add(check);
                            count = characterArrayList.size();
                        }
                        else {
                            // System.out.println(characterArrayList);
                            count = 0;
                            i--;
                            characterArrayList.clear();
                        }
                        if (count > maxCount) {
                            //System.out.println(characterArrayList);
                            maxCount = count;
                            longestSubString = (ArrayList<Character>) characterArrayList.clone();
                        }
                    }
                    //System.out.println(characterArrayList);
                    System.out.println("The longest substring without repeating characters ");
                    System.out.println(longestSubString);
                    System.out.println("The count is "+maxCount);

                }
                break;
                case 6:{
//                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter the string 1");
                    String str1 = scanner.nextLine();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(str1);
                    //System.out.println(stringBuilder.length());
                    System.out.println("Enter the string 2");
                    String str2 = scanner.nextLine();
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append(str2);
                    int lenStr1 = stringBuilder.length();
                    int lenStr2 =  stringBuilder1.length();
                    int diff;
                    if(lenStr1 == lenStr2)
                    {
                        stringBuilder.append(stringBuilder1);
                        //   System.out.println(stringBuilder);
                    }
                    else if(lenStr1>lenStr2)
                    {
                        diff =  lenStr1-lenStr2;
                        System.out.println(diff);
                        stringBuilder.delete(0,diff);
                        stringBuilder.append(stringBuilder1);
                    }
                    else
                    {
                        diff = lenStr2-lenStr1;
                        stringBuilder1.delete(0,diff);
                        stringBuilder.append(stringBuilder1);
                    }
                    System.out.println("The Appended String is: ");
                    System.out.println(stringBuilder);
                }
                break;
                case 7: {
                    choice = 8;
                }
                    break;
                default:
                    System.out.println("Enter the correct choice");
            }
        }while(choice!=8);
    }
}
