# Mini Compiler

A mini compiler project created by **Jhan Ellen Atole, Chryzle Amualla,** and **Marianne Dale Garcia**

A compiler is a program that translates source code into executable programs. The compilation process involves several phases;  lexical analysis, syntax analysis, semantic analysis, intermediate code generation, optimization, and code generation.

As a part of our Final completion for the course "Theory of Programming Languages", we have created a compiler project that emulates the first 3 phases of a compiler; lexical analysis, syntax analysis, semantic analysis.

Here, you can see our user interface. It is clear that our design inspiration has a groovy retro vibe. Our compiler project is a nod to the roots of computing, and our design inspiration takes a page from the past for a reason. Compilers, in their essence, are the pioneers of programming languages aka the OGs of computing, if you will. So, why the retro theme? Because, just like compilers, it's a tribute to the classic era of computing.

#

<img width="289" alt="Screenshot 2023-12-18 000455" src="https://github.com/chryzanths/Mini-Compiler/assets/104879763/a92929a6-729e-4544-8b55-44f76e5c5707">

#

- Firstly, the file reader system. It allows you to easily load a file. Once the file is loaded, the system kicks off the lexical analysis phase.
- This initial step, which is lexical analysis, breaks down your code into meaningful tokens, laying the groundwork for the subsequent stages. It involves breaking down the source code into smaller parts called tokens, such as keywords, identifiers, and punctuation. The output of this phase is a stream of tokens that can be easily processed by the next phase, which is the syntax analyzer.
-  Syntax analysis serves as the second phase of a compiler. After a click of its button, it checks the relationships between the tokens and the overall structure of the code. The main aim is to ensure that the source code follows the rules of the programming language's grammar. It generates a parse tree, which represents the syntactic structure of the source code.
-  Lastly in this emulation is Semantic Analysis. This third phase of a compiler involves type checking, ensuring that identifiers are declared before use, and checking for other semantic errors. It checks the meaning of the source code to ensure that it makes sense and is semantically correct.
-  The code text area displays the text output and the clear button resets the application as it clears both the result text area and code text area. 

#

![403606597_3413850365498624_4896989404277438624_n](https://github.com/chryzanths/Mini-Compiler/assets/104879763/aa66cba6-e679-42f9-828d-1c50f83fac57)
