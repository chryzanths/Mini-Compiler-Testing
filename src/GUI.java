import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GUI extends JFrame implements ActionListener{

    JTextArea sourceCode, textField;
    JScrollPane scrollPane;
    JButton fileOpener, lexicalAnalyzer, syntaxAnalyzer, semanticsAnalyzer, clear;
    ImageIcon image, image2;
    JLabel label, label2;

    GUI(){

        image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("title.png")));
        label = new JLabel(image);
        label.setLabelFor(null);
        label.setSize(200, 200);

        image2 = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("down.png")));
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
        textField.setFont(new Font("Monospaced", Font.BOLD, 15));
        textField.setBackground(Color.black);
        textField.setForeground(Color.MAGENTA);
        textField.setEditable(false);

        //------ text field ----------------


        //------ text editor ----------------

        sourceCode = new JTextArea();
        sourceCode.setLineWrap(true);
        sourceCode.setWrapStyleWord(true);
        sourceCode.setFont(new Font("Monospaced", Font.BOLD, 15));
        sourceCode.setBackground(Color.black);
        sourceCode.setForeground(Color.white);
        //

        // pang scroll kase baka di kumasya sa editor pag madaming lines of code
        scrollPane = new JScrollPane(sourceCode);
        scrollPane.setPreferredSize(new Dimension(350,200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //------ text editor ----------------

        //------ file opener ----------------

        fileOpener = new JButton("File");
        fileOpener.setBackground(Color.darkGray);
        fileOpener.setForeground(Color.cyan);
        fileOpener.addActionListener(this);

        //------ file opener ----------------


        //------ lexical ----------------

        lexicalAnalyzer = new JButton("Lexical");
        lexicalAnalyzer.setBackground(Color.darkGray);
        lexicalAnalyzer.setForeground(Color.cyan);
        lexicalAnalyzer.addActionListener(this);
        lexicalAnalyzer.setEnabled(false);

        //------ lexical ----------------

        //------ syntax ----------------

        syntaxAnalyzer = new JButton("Syntax");
        syntaxAnalyzer.setBackground(Color.darkGray);
        syntaxAnalyzer.setForeground(Color.cyan);
        syntaxAnalyzer.addActionListener(this);
        syntaxAnalyzer.setEnabled(false);

        //------ syntax ----------------

        //------ semantics ----------------

        semanticsAnalyzer = new JButton("Semantic");
        semanticsAnalyzer.setBackground(Color.darkGray);
        semanticsAnalyzer.setForeground(Color.cyan);
        semanticsAnalyzer.addActionListener(this);
        semanticsAnalyzer.setEnabled(false);

        //------ semantics ----------------

        //------ clear button ----------------

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

        if(e.getSource()==fileOpener){
            String output = Compiler.openFile();
            sourceCode.setText(output);
            lexicalAnalyzer.setEnabled(true);
            fileOpener.setEnabled(false);
        }

        if(e.getSource()==lexicalAnalyzer){

            String result = Compiler.lexicalAnalysis();
            String output;
            //System.out.println(tokens);
            if (result.equals("invalid")) {
                output = "There is an invalid lexeme, cannot be put into token.";
            } else {
                output = "The code passed Lexical Analysis";
                syntaxAnalyzer.setEnabled(true);
            }
            lexicalAnalyzer.setEnabled(false);
            updateTextField(output);
        }

        if(e.getSource() == syntaxAnalyzer){
            String result = Compiler.syntaxAnalysis();
            String output;
            if (result.equals("valid")) {
                semanticsAnalyzer.setEnabled(true);
                output = "The code passed Syntax Analysis.";
            } else {
                output = "Syntax incorrect!";
            }
            syntaxAnalyzer.setEnabled(false);
            updateTextField(output);
        }

        if (e.getSource() == semanticsAnalyzer) {
            String result = Compiler.semanticAnalysis();
            String output;
            if (result.equals("valid")) {
                output = "The code passed Semantic Analysis.";
            } else {
                output = "The code is semantically incorrect!!";
            }
            semanticsAnalyzer.setEnabled(false);
            updateTextField(output);
        }

        if(e.getSource()== clear) {
            // if clrButton is pressed, it will set the text to empty or just nothing
            sourceCode.setText("");
            textField.setText("");
            fileOpener.setEnabled(true);
            lexicalAnalyzer.setEnabled(false);
            syntaxAnalyzer.setEnabled(false);
            semanticsAnalyzer.setEnabled(false);
        }

    }
    private void updateTextField(String result) {
        textField.setText(result);
    }

}
