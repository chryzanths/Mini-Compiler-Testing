import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class TextEditor extends JFrame implements ActionListener {

    JTextArea sourceCode, textField;
    JScrollPane scrollPane;
    JButton fileOpener, lexicalAnalyzer, syntaxAnalyzer, semanticsAnalyzer, clear;
    ImageIcon image, image2;
    JLabel label, label2;

    TextEditor(){

        image = new ImageIcon(this.getClass().getResource("title.png"));
        label = new JLabel(image);
        label.setLabelFor(null);
        label.setSize(200, 200);

        image2 = new ImageIcon(this.getClass().getResource("down.png"));
        label2 = new JLabel(image2);
        label2.setLabelFor(null);
        label2.setSize(200, 200);

        // text editor pane
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Compiler by Atole, Amualla, Garcia");
        this.setSize(400, 550);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.gray);


        //------ test field ----------------

        textField = new JTextArea();
        textField.setBounds(100, 25, 250, 50);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setFont(new Font("Monospaced", Font.CENTER_BASELINE, 15));
        textField.setBackground(Color.black);
        textField.setForeground(Color.MAGENTA);
        textField.setEditable(false);

        //------ text field ----------------


        //------ text editor ----------------
        sourceCode = new JTextArea();
        sourceCode.setLineWrap(true);
        sourceCode.setWrapStyleWord(true);
        sourceCode.setFont(new Font("Monospaced", Font.CENTER_BASELINE, 15));
        sourceCode.setBackground(Color.black);
        sourceCode.setForeground(Color.white);
        //

        // pang scroll kase baka di kumasya sa editor pag madaming lines of code
        scrollPane = new JScrollPane(sourceCode);
        scrollPane.setPreferredSize(new Dimension(350,200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //------ text editor ----------------

        //------ file opener ----------------
        fileOpener = new JButton();
        fileOpener = new JButton("File");
        fileOpener.setBackground(Color.darkGray);
        fileOpener.setForeground(Color.cyan);
        fileOpener.addActionListener(this);

        //------ file opener ----------------


        //------ lexical ----------------
        lexicalAnalyzer = new JButton();
        lexicalAnalyzer = new JButton("Lexical");
        lexicalAnalyzer.setBackground(Color.darkGray);
        lexicalAnalyzer.setForeground(Color.cyan);
        lexicalAnalyzer.addActionListener(this);

        //------ lexical ----------------

        //------ syntax ----------------
        syntaxAnalyzer = new JButton();
        syntaxAnalyzer = new JButton("Syntax");
        syntaxAnalyzer.setBackground(Color.darkGray);
        syntaxAnalyzer.setForeground(Color.cyan);
        syntaxAnalyzer.addActionListener(this);

        //------ syntax ----------------

        //------ semantics ----------------
        semanticsAnalyzer = new JButton();
        semanticsAnalyzer = new JButton("Semantic");
        semanticsAnalyzer.setBackground(Color.darkGray);
        semanticsAnalyzer.setForeground(Color.cyan);
        semanticsAnalyzer.addActionListener(this);
        //------ semantics ----------------

        //------ clear button ----------------

        clear = new JButton();
        clear = new JButton("Clear");
        clear.setBackground(Color.darkGray);
        clear.setForeground(Color.cyan);
        clear.addActionListener(this);

        //------ clear ----------------

        this.add(label);
        this.add(textField);
        this.add(scrollPane);
        this.add(fileOpener);
        this.add(lexicalAnalyzer);
        this.add(syntaxAnalyzer);
        this.add(semanticsAnalyzer);
        this.add(clear);
        this.add(label2);



        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //------ file opener ----------------

        List<String> lexemes = new ArrayList<>();
        List<String> tokens = new ArrayList<>();

        if(e.getSource()==fileOpener){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            // txt file lang tinatanggap para di complicated
            FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files", "txt");
            fileChooser.setFileFilter(filter);

            // pang open nung pop up window pag pipili ng files
            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION){
                // dito i-scan contents ng file
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileScanner = null;

                try {
                    fileScanner = new Scanner(file);
                    if(file.isFile()){ // check if yung file na in-upload is valid
                        while(fileScanner.hasNextLine()){
                            // ewan pero dito na papasok yung seperation ng strings sa maeextract sa file
                            lexemes = Testing.separateCode(String.valueOf(file));
                            String codeLine = fileScanner.nextLine()+"\n";
                            sourceCode.append(codeLine); // this means na kung ano yung laman nung file na naupload, magrereflect sa text box

                        }
                    }

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileScanner.close();
                }
            }
        }
        if(e.getSource()==lexicalAnalyzer){

            String result;
            tokens = Testing.lexicalAnalysis(lexemes);
            if (tokens == null) {
                result = "There is an invalid lexeme, cannot be put into token.";
            } else {
                result = "The code passed Lexical Analysis";
            }
            updateTextField(result);
        }
        if(e.getSource() == syntaxAnalyzer){
            String result = Testing.syntaxAnalysis(tokens, lexemes);
            updateTextField(result);
        }
        if (e.getSource() == semanticsAnalyzer)
        {
            Testing.semanticAnalysis(tokens, lexemes);
            //textField.setText(String.valueOf());
        }
        if(e.getSource()== clear) {
            // if clrButton is pressed, it will set the text to empty or just nothing
            sourceCode.setText("");
            textField.setText("");
        }

    }
    private void updateTextField(String result) {
        textField.setText(result);
    }
}
