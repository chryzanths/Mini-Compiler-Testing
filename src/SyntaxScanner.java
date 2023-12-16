

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxScanner
{
    public static void main(String[] args)
    {

        String tokens = "";

        Scanner input = new Scanner(System.in);

        System.out.print("Enter Source Language: ");
        String sourceCode = input.nextLine();

        System.out.print("Output:");

        // extracting value inside double quotes and grouping them together, it is to avoid strings splitting apart when there's a space
        Pattern p = Pattern.compile(".*\"([^\"]*)\".*");
        Matcher m1 = p.matcher(sourceCode);

        String[] arraySplit1 = sourceCode.split(" ");
        String[] arraySplit2 = sourceCode.split("");

        // extracting the numbers/integers
        String alteredInput = sourceCode.replaceAll("[abcdefghijklmnopqrstuvwxyz,=;]", "").replaceAll(" ", "");
        String alteredInput3 = sourceCode.replaceAll("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,=;]"," ").replaceAll(" ", "");

        List<String> tokenizer = new ArrayList<String>();

        // LEXICAL ANALYSIS
        for (String stringArray : arraySplit1)
        {
            if (stringArray.equals("int") || stringArray.equals("double") || stringArray.equals("char") || stringArray.equals("String"))
            {
                tokens = " <data_type> ";
                tokenizer.add(tokens);
                //System.out.print(tokens);
            }
            else if (stringArray.equals("="))
            {
                tokens = " <assignment_operator> ";
                tokenizer.add(tokens);
                //System.out.print(tokens);
            }
            else if (stringArray.contains("\"") && m1.matches() || stringArray.contains("\'") || stringArray.contains(".") || stringArray.equals(alteredInput))
            {
                tokens = " <value> ";
                tokenizer.add(tokens);
                //System.out.print(tokens);
            }
            else if (!stringArray.contains("\"") && !stringArray.contains("\'") && !stringArray.contains(".") && !stringArray.equals(alteredInput))
            {
                tokens = " <identifier> ";
                tokenizer.add(tokens);
                //System.out.print(tokens);
            }
            System.out.println(tokens);
        }

        for (String stringArray : arraySplit2) {

            if (stringArray.equals(";"))
            {
                tokens = " <delimiter> ";
                tokenizer.add(tokens);
                //System.out.print(tokens);
            }
        }

        System.out.println(tokens);

        // SYNTAX ANALYSIS

        List<String> tokenList = new ArrayList<String>();
        tokenList.add("<data_type>");
        tokenList.add("<identifier>");
        tokenList.add("<assignment_operator>");
        tokenList.add("<value>");
        tokenList.add("<delimiter>");


        Pattern pattern = Pattern.compile("(<data_type>)|(<identifier>)|(<assignment_operator>)|(<value>)|(<delimiter>)");
        Matcher matcher = pattern.matcher(tokens);
        boolean matchFound = matcher.find();
        if(matchFound) {
            System.out.println("Correct syntax");
        } else {
            System.out.println("Incorrect syntax");
        }

        // SEMANTICS ANALYSIS

        for (String stringArray : arraySplit1)
        {
            if (stringArray.equals("int") && !alteredInput.contains(".") && !alteredInput.contains("") && !alteredInput.contains("\""))
            {
                System.out.print("\nSemantics: ");
                System.out.println("Semantically Correct!");

                System.out.print("\nReason: ");
                System.out.println(stringArray + alteredInput);
            }
            else if (stringArray.equals("double") && alteredInput.contains("."))
            {
                System.out.print("\nSemantics: ");
                System.out.println("Semantically Correct!");

                System.out.print("\nReason: ");
                System.out.println(stringArray + alteredInput);
            }
            else if (stringArray.contains("String") && alteredInput3.equals("\"\"") || alteredInput3.equals("“”"))
            {
                System.out.print("\nSemantics: ");
                System.out.println("Semantically Correct!");

                System.out.print("\nReason: ");
                System.out.println(stringArray + alteredInput3);
            }
            else if (stringArray.contains("char") && alteredInput3.equals("\'\'"))
            {
                System.out.print("\nSemantics: ");
                System.out.println("Semantically Correct!");

                System.out.print("\nReason: ");
                System.out.println(stringArray + alteredInput3);
            }
            else
            {
                System.out.print("\nSemantics: ");
                System.out.println("Semantically Incorrect!");

                System.out.print("\nReason: ");
                System.out.println(stringArray + alteredInput3);
            }
            break;
        }

    }
}
