import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class TextEditor extends JFrame implements ActionListener {

    JTextArea sourceCode;
    JScrollPane scrollPane;
    JButton fileOpener;
    JButton lexicalAnalyzer;
    JButton syntaxAnalyzer;
    JButton semanticsAnalyzer;
    TextEditor(){
        // text editor pane
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Add source code");
        this.setSize(600, 400);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        //------ text editor ----------------
        sourceCode = new JTextArea();
        sourceCode.setLineWrap(true);
        sourceCode.setWrapStyleWord(true);
        sourceCode.setFont(new Font("Monospaced", Font.CENTER_BASELINE, 15)); //

        // pang scroll kase baka di kumasya sa editor pag madaming lines of code
        scrollPane = new JScrollPane(sourceCode);
        scrollPane.setPreferredSize(new Dimension(400,200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //------ text editor ----------------

        //------ file opener ----------------
        fileOpener = new JButton();
        fileOpener = new JButton("File");

        fileOpener.addActionListener(this);

        //------ file opener ----------------


        //------ lexical ----------------
        lexicalAnalyzer = new JButton();
        lexicalAnalyzer = new JButton("Lexical");
        lexicalAnalyzer.addActionListener(this);

        //------ lexical ----------------

        //------ syntax ----------------
        syntaxAnalyzer = new JButton();
        syntaxAnalyzer = new JButton("Syntax");
        syntaxAnalyzer.addActionListener(this);

        //------ syntax ----------------

        semanticsAnalyzer = new JButton();
        semanticsAnalyzer = new JButton("Semantic");
        semanticsAnalyzer.addActionListener(this);

        this.add(semanticsAnalyzer);
        this.add(syntaxAnalyzer);
        this.add(lexicalAnalyzer);
        this.add(fileOpener);
        this.add(scrollPane);

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
                            
                            if(e.getSource()==lexicalAnalyzer){

                                tokens = Testing.lexicalAnalysis(lexemes);
                            }
                            else if(e.getSource() == syntaxAnalyzer){
                                Testing.syntaxAnalysis(tokens, lexemes);
                            }
                            else if (e.getSource() == semanticsAnalyzer)
                            {
                                Testing.semanticAnalysis(tokens, lexemes);
                            }

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
    }
}
