# Java Overview

**🔹** Why Java?

- Before Java: Most languages were procedural, focusing on functions and step-by-step instructions.
- Java introduced object-oriented programming (OOP), focusing on data and state changes.
  - Example: In procedural code, you write a function to calculate area for Box
  - In Java, you'd create a Box class with properties and methods, encapsulating both data and behavior.

🔹 How Java Works?

1. Java source code (.java) is compiled by javac into bytecode (.class files).
2. Bytecode is an intermediate, platform-independent format.
3. The JVM (Java Virtual Machine) converts bytecode into machine-specific binary code.
4. The JVM is platform-dependent, so different OS need different JVMs.
5. JVM handles memory management, security, and can run other languages compiled to bytecode.
6. JRE (Java Runtime Environment): For running Java apps (used by end users).
7. JDK (Java Development Kit): For developing and running Java apps (includes JRE + dev tools).

![image1.png](../images/image1.png)


<div class="joplin-table-wrapper"><table><tbody><tr><th><p><strong>Development Tools</strong></p><ul><li><strong>javac</strong>: The Java compiler that converts .java files to bytecode (.class files)</li><li><strong>java</strong>: The launcher for Java applications</li><li><strong>javadoc</strong>: Tool for generating documentation from source code comments</li><li><strong>jar</strong>: Tool for packaging Java files into JAR (Java Archive) files</li><li><strong>javap</strong>: The Java class file disassembler</li><li><strong>jdb</strong>: Java Debugger</li><li><strong>jconsole</strong>: Monitoring and management console</li><li><strong>jshell</strong>: Java REPL (Read-Eval-Print Loop) interactive shell</li></ul></th><th><p><strong>Java Virtual Machine (JVM)</strong></p><ul><li><strong>Class Loader Subsystem</strong></li><li><strong>Runtime Data Areas</strong><ul><li>Heap Memory</li><li>Stack Memory</li><li>Method Area (non-Heap)</li><li>PC Registers</li><li>Native Method Stacks</li></ul></li><li><strong>Execution Engine</strong><ul><li>Interpreter</li><li>Just-In-Time (JIT) Compiler</li><li>Garbage Collector</li></ul></li><li><strong>Native Method Interface (JNI)</strong></li><li><strong>Native Method Libraries</strong></li></ul></th><th><p><strong>Java Class Libraries</strong></p><ul><li>java.lang</li><li>java.io</li><li>java.util</li><li>java.net</li><li>java.math</li><li>java.awt</li><li>javax packages</li><li>etc.</li></ul></th><th><p><strong>Java Standard Extensions</strong></p><ul><li>JavaFX</li><li>JDBC (Java Database Connectivity)</li><li>JNDI (Java Naming and Directory Interface)</li><li>etc.</li></ul></th></tr></tbody></table></div>
