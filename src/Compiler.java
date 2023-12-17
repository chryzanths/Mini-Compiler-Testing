import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler {

    public static List<String> lexemes = new ArrayList<>();
    public static List<String> tokens = new ArrayList<>();

    public static String semanticAnalysis() {

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

            assert pattern != null;
            boolean isValid = false;
            Matcher matcher= pattern.matcher(value);

            boolean matchFound = matcher.find();

            if (matchFound){
                isValid = true;
            }

            if (isValid){
                return "valid";
            } else {
                return "invalid";
            }

        }

    public static String syntaxAnalysis() {

        boolean isValid = true;

        //<data_type> <identifier> <assignment_operator> <value> <delimiter>

        String[] syntax = {"<data_type>", "<identifier>", "<assignment_operator>", "<value>", "<delimiter>"};


        if (tokens.size() == 5){
            for(int i = 0; i<5; i++) {

                boolean match = Objects.equals(tokens.get(i), syntax[i]);

                if (!match) {
                    isValid = false;
                    break;
                }

            }
        }

        if (isValid){
            return "valid";
        } else {
            return "invalid";
        }
    }

    public static String lexicalAnalysis(){

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
                    //validate = "There is an invalid lexeme, cannot be put into token";
                    //return validate;
                    System.out.println("Invalid lexeme: " + lexeme);
                }
            }

            if (isValid) {
                return "valid";
                // For checking
                //System.out.println(tokens);
                //validate = "The code passed Lexical Analysis";
                //return validate;
            } else {
                return "invalid";
            }

        }

    public static String openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        // txt file lang tinatanggap para di complicated
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files", "txt");
        fileChooser.setFileFilter(filter);

        // pang open nung pop up window pag pipili ng files
        int response = fileChooser.showOpenDialog(null);

        StringBuilder output = new StringBuilder();

        if(response == JFileChooser.APPROVE_OPTION){
            // dito i-scan contents ng file
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try (Scanner fileScanner = new Scanner(file)) {
                if (file.isFile()) { // check if yung file na in-upload is valid

                    while (fileScanner.hasNextLine()) {
                        String codeLine = fileScanner.nextLine();
                        System.out.println(codeLine);
                        output.append(codeLine).append("\n");
                        lexemes = Compiler.separateCode(codeLine);
                        System.out.println(lexemes);
                    }

                }

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        return output.toString();

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
