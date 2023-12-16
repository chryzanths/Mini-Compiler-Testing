import java.util.Objects;

import java.util.Scanner;

import java.util.regex.*;

import java.util.ArrayList;

import java.util.List;



    public class Testing {

        public static void main (String[] args) {
            new TextEditor();
        }


        public static void semanticAnalysis(List<String> tokens, List<String> lexemes) {

            Pattern pattern;

            String dataType = lexemes.get(tokens.indexOf("<data_type>"));

            String value = lexemes.get(tokens.indexOf("<value>"));



            // For testing if the data type is corrected determined

            //System.out.println(dataType);

            // To set the pattern to match based on the data type

            switch (dataType) {

                case "int" -> pattern = Pattern.compile("^-?\\d+$");

                case "String" -> pattern = Pattern.compile("^\".*\"$");

                case "char" -> pattern = Pattern.compile("^'[a-zA-Z]'$");

                case "double" -> pattern = Pattern.compile("^-?\\d*\\.?\\d+\\.?$");

                case "float" -> pattern = Pattern.compile("^-?\\d*\\.?\\d+\\.?f$");

                default -> pattern = null;

            }

            // method to check if the value matches with the pattern

            assert pattern != null;

            boolean isValid = false;

            Matcher matcher;

            matcher = pattern.matcher(value);

            boolean matchFound = matcher.find();

            if (matchFound){

                isValid = true;

            }



            if (isValid){

                System.out.println("The code passed Lexical Analysis");

                System.out.println("Congratulations! The code passed all three analysis.");

            } else {

                System.out.println("Semantically Incorrect!");

            }

        }



        public static void syntaxAnalysis(List<String> tokens, List<String> lexemes) {

            boolean isValid = true;

            //<data_type> <identifier> <assignment_operator> <value> <delimiter>

            String[] syntax = {"<data_type>", "<identifier>", "<assignment_operator>", "<value>", "<delimiter>"};


            if (tokens.size() == 5){

                for(int i = 0; i<5; i++) {

                    boolean match = Objects.equals(tokens.get(i), syntax[i]);

                    if (!match){

                        isValid = false;

                        System.out.println("Syntax is incorrect.");

                    }

                }

            } else {

                System.out.println("Syntax is incorrect.");

            }

            if (isValid){

                System.out.println("The code passed Syntax Analysis");

            }

        }



        public static List<String> lexicalAnalysis(List<String> lexemes){

            List<String> tokens = new ArrayList<>();
            String[] regEx = {"int|double|float|char|String", "=", ";", "(^-?\\d+$)|(^\".*\"$)|(^'[a-zA-Z]'$)|(^-?\\d*\\.?\\d+\\.?$)|(^-?\\d*\\.?\\d+\\.?f$)","([a-zA-Z_$][a-zA-Z0-9_$]*)"};
            String[] tokenName = {"data_type", "assignment_operator", "delimiter", "value", "identifier"};

            boolean isValid = true;

            for (String lexeme : lexemes) {

                boolean isfound = false;
                boolean isDataType = false;

                for(int i=0; i<5; i++) {

                    Pattern pattern = Pattern.compile(regEx[i]);
                    Matcher matcher = pattern.matcher(lexeme);
                    boolean found = matcher.find();

                    if (found){

                        isfound = true;

                        if (i == 0) {
                            isDataType = true;
                        }

                        if (i<4){
                            tokens.add("<" + tokenName[i] + ">");
                        } else {
                            if (!isDataType){
                                tokens.add("<" + tokenName[i] + ">");

                            }
                        }
                    }
                }

                if (!isfound) {
                    isValid = false;
                    System.out.println("There is an invalid lexeme, cannot be put into token");
                }
            }

            if (isValid) {

                // For checking
                //System.out.println(tokens);

                System.out.println("The code passed Lexical Analysis");
            }
            return tokens;
        }


        public static List<String> separateCode(String code) {

            List<String> lexemes = new ArrayList<>();

            Pattern pattern = Pattern.compile("(;)|(=)|(\".*\")|('[a-zA-Z]')|(-?\\d*\\.?\\d+\\.?f)|([a-zA-Z_$][a-zA-Z0-9_$]*)|(-?\\d*\\.?\\d+\\.?)");

            Matcher matcher = pattern.matcher(code);

            while (matcher.find()) {

                String lexeme = matcher.group();

                if (!lexeme.matches("\\s*")) {

                    lexemes.add(lexeme);

                }

            }

            // For checking

            //System.out.println(lexemes);

            return lexemes;

        }
}
