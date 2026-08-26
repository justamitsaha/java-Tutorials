# JIT

For faster operation java utilizes native code. Native  code is the code executed directly by processor.

Java code

```java
public int add(int a, int b) {
return a + b;
}

```

Native code

**x86 (Intel/AMD) Example: For a simple integer addition:**
```
mov eax, [ebp+8]    ; Load first parameter into eax register

add eax, [ebp+12]   ; Add second parameter to eax
```

**ARM Example:** For the same operation:
```
ldr r0, [r7, #8]    ; Load first parameter into r0 register

ldr r1, [r7, #12]   ; Load second parameter into r1

add r0, r0, r1      ; Add r1 to r0
```

Java 🡪(by javac)class files 🡪byte code (by java) 🡪 (JIT caches)Native code for the OS

🔹 What is JIT?

- JIT (Just-In-Time) compiler is a part of the JVM that optimizes Java application performance at runtime.
- It converts frequently used bytecode ("hotspots") into native machine code, allowing faster execution.

🔹 How It Works:

1. When a Java program starts, the JVM interprets bytecode line by line (slow due to interpretation overhead).
2. The JVM monitors execution and identifies frequently run code (hotspots).
3. The JIT compiler compiles only these hotspots into native machine code.
4. Future calls to these hotspots run directly as machine code, skipping interpretation — much faster.

🔹 Benefits of JIT:

- Improves performance over time (Java apps "warm up").
- Optimizes only what's needed, saving memory and startup time.
- Adapts to real-time execution patterns for better optimization.
- Suitable for long-running apps like servers, where performance gradually becomes near-native.

🔹 JIT vs AOT:

| Feature               | JIT (Just-In-Time)       | AOT (Ahead-Of-Time)        |
| --------------------- | ------------------------ | -------------------------- |
| Compiles code         | At runtime (on-the-fly)  | Before execution           |
| Startup time          | Faster                   | Slower                     |
| Runtime optimization  | Yes (adaptive)           | No (fixed at compile time) |
| Performance over time | Improves (after warm-up) | Stable, less adaptive      |
