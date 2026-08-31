# Just-In-Time (JIT) Compiler

## Overview

For optimal performance, Java bridges the gap between interpreted bytecode and hardware-level execution using native machine code. Native code is the binary instruction set executed directly by the underlying processor.

```
Java Source (.java) ---> [javac] ---> Bytecode (.class) ---> [JVM Interpreter / JIT] ---> Native Machine Code
```

### High-Level Code vs Native Instructions

#### Java Code Example
```java
public int add(int a, int b) {
    return a + b;
}
```

#### Corresponding Native Assembly (x86 vs ARM)

- **x86 (Intel / AMD):**
  ```assembly
  mov eax, [ebp+8]    ; Load first parameter into eax register
  add eax, [ebp+12]   ; Add second parameter to eax
  ```

- **ARM64:**
  ```assembly
  ldr r0, [r7, #8]    ; Load first parameter into r0 register
  ldr r1, [r7, #12]   ; Load second parameter into r1 register
  add r0, r0, r1      ; Add r1 to r0
  ```

---

## What is the JIT Compiler?

The **Just-In-Time (JIT) Compiler** is an integral component of the JVM Execution Engine designed to accelerate bytecode execution at runtime:

- **Interpretation vs Compilation:** Standard interpretation executes bytecode instruction-by-instruction, incurring runtime interpretation overhead.
- **Hotspot Detection:** The JVM monitors program execution and identifies frequently executed sections of code (referred to as *hotspots* or *hot loops*).
- **Native Translation & Caching:** The JIT compiler compiles identified hotspots into native machine code on-the-fly and caches the compiled code in the **Code Cache** region of native memory.
- **Direct Execution:** Subsequent invocations of these hotspots bypass the interpreter and execute native code directly at hardware speed.

---

## Tiered Compilation Architecture

Modern HotSpot JVMs employ **Tiered Compilation** to balance fast application startup with maximum long-term peak performance:

| Level | Component | Description |
| :--- | :--- | :--- |
| **Tier 0** | **Interpreter** | Interprets raw bytecode immediately upon startup with zero compilation delay. |
| **Tier 1–3** | **C1 Compiler (Client)** | Performs fast compilation with basic profiling and lightweight optimizations for rapid responsiveness. |
| **Tier 4** | **C2 Compiler (Server)** | Performs heavy, profile-guided optimizations (dynamic inlining, loop unrolling, escape analysis, devirtualization) for peak throughput. |

---

## Benefits of JIT Compilation

- **Adaptive Profile-Guided Optimization (PGO):** JIT utilizes real-time runtime metrics (branch frequencies, actual type distributions) to apply optimizations impossible at static compile time.
- **Selective Compilation:** Only frequently called methods are compiled to native code, conserving memory and reducing CPU compilation overhead.
- **Continuous Deoptimization:** If runtime profiling assumptions change (e.g., a polymorphic call site is introduced), the JVM can deoptimize compiled code back to interpreted mode safely.

---

## JIT vs AOT (Ahead-of-Time) Compilation

| Feature | JIT (Just-In-Time) | AOT (Ahead-of-Time, e.g., GraalVM Native Image) |
| :--- | :--- | :--- |
| **Compilation Phase** | At runtime during application execution | Prior to execution during the build phase |
| **Startup Time** | Slower (requires JVM bootstrap, class loading, warm-up) | Near-instantaneous (direct binary launch) |
| **Memory Footprint** | Higher (JVM metadata, JIT compiler, Code Cache) | Significantly lower (compact executable binary) |
| **Peak Throughput** | Can be higher due to runtime adaptive profiling and dynamic devirtualization | High and predictable, but lacks runtime branch-profile feedback |
| **Ideal Use Case** | Long-running microservices, enterprise servers, batch processing | Serverless functions (AWS Lambda), CLI tools, short-lived tasks |
