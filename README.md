# Java Expression Calculator

A desktop GUI calculator capable of parsing and evaluating complex mathematical expressions with arbitrary parentheses nesting, operator precedence, and implicit operations.

> 📝 **Academic Context:** 
> It was my first ever coding project developed during my first semester of Computer Engineering at **UPV (ETSINF)**. The main goal was to explore Java syntax, GUI development with Swing and to practice all the Java basics. I didn't want to use anything except raw Java, and at the moment had no idea about OOP, optimal algorithms etc. So I just used everything I knew at the moment (which is not a lot) and developed maybe crude, but completely functional calculator that can process complicated operations.

---

## 📸 Preview

<img width="382" height="686" alt="image" src="https://github.com/user-attachments/assets/0820804e-7602-4b4c-b8a0-d8a199843669" />


## ✨ Features & Handled Logic

Unlike simple calculators that evaluate operations sequentially (`2 + 3 * 4 = 20`), this engine parses the entire expression honoring mathematical order of operations:

- **Nested Parentheses:** Custom matching and resolution of arbitrary bracket depth.
- **Operator Precedence:** Correct evaluation order: Powers (`^`) → Multiplication/Division (`*`, `/`) → Addition/Subtraction (`+`, `-`).
- **Implicit Multiplication:** Automatically detects omitted multiplication signs (e.g., `2(3)` or `)(` → `2*(3)`).
- **Square Roots & Powers:** Handles square root extraction (`√`) and negative/fractional powers.
- **Robust Error Handling:** Explicit validation for division by zero (`DZ`), negative roots (`NR`), out-of-bounds numbers (`OOB`), and malformed parentheses.

---

## 🛠 Tech Stack

- **Language:** Java (JDK 17+)
- **UI Framework:** Java Swing & AWT
- **Build / Packaging:** Executable Runnable JAR

---

## 🚀 Getting Started

### 1. Download & Run Pre-built Binary
Download the latest executable `.jar` file directly from the [Releases](../../releases) tab:
```bash
java -jar Calculator.jar
