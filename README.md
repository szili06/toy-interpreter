# Toy Language Interpreter - In Progress

An interpreter for a small imperative toy language, written in Java 25 with Gradle and JUnit 5. The project follows the Model–View–Controller (MVC) pattern. Programs are written as trees of Java objects, and the interpreter shows every intermediate program state.

## Features

**Current**

- Types `int`, `bool`, and `string`, with default values for declared variables (`0`, `false`, `""`)
- Arithmetic expressions (`+`, `-`, `*`, `/`), logic expressions (`and`, `or`), and relational expressions (`<`, `<=`, `==`, `!=`, `>`, `>=`)
- Variable declaration, assignment, `print`, `if … then … else`, `nop`, and sequential composition
- File statements `openRFile`, `readFile`, and `closeRFile`, which read a text file with one integer per line
- A file table in the program state that maps open file names to their readers
- Generic ADTs: stack, dictionary (symbol table and file table), and list (output), each backed by `java.util`
- A checked exception hierarchy rooted in `MyException`, with clear error messages
- Step-by-step display of the program state (`ExeStack`, `SymTable`, `Out`, `FileTable`)
- Log file: after every step, the program state is appended to a text file
- Text menu for choosing and running the example programs
- JUnit tests for ADTs, expressions, statements, and the controller

**Planned**

- Command-based text menu (Command pattern), so that adding an example means adding a command
- Example programs that use files and relational expressions, with the menu entries for them
- Tests for the log file

## Requirements

- JDK 25 (for example Eclipse Temurin or Oracle JDK)
- Nothing else. The Gradle wrapper (`./gradlew`) downloads the correct Gradle version automatically.

## Building and running

Linux and macOS:

```bash
./gradlew build   # compiles the project and runs all tests
./gradlew test    # runs the tests only
./gradlew run     # starts the text menu
```

Windows: use `gradlew.bat` instead of `./gradlew`.

The text menu shows one option per example program. Enter `0` to exit.

```
 0 : exit
 1 : (int v; (v = 2; print(v)))
 2 : (int a; (int b; (a = (2 + (3 * 5)); (b = (a + 1); print(b)))))
 3 : (bool a; (int v; (a = true; ((if (a) then (v = 2) else (v = 3)); print(v)))))
Input the option:
```

### Input files for file statements

`openRFile`, `readFile`, and `closeRFile` read a text file with one positive integer per line, for example:

```
15
50
```

Relative file names are resolved against the directory the program runs in, which is the project root when started with `./gradlew run`.

### Log files

Each program writes its trace to a log file in the project root. Each state in the log has four sections: `ExeStack:`, `SymTable:`, `Out:`, and `FileTable:`. The log file is opened in append mode, so delete old logs before a demonstration. Log files are listed in `.gitignore` and are not committed.

## Project structure

```
toy-interpreter/
├── settings.gradle.kts
├── gradle/libs.versions.toml     # dependency versions
├── gradlew, gradlew.bat, gradle/wrapper/
└── app/
    ├── build.gradle.kts
    └── src/
        ├── main/java/interpreter/
        │   ├── Main.java          # entry point: builds the menu
        │   ├── model/             # PrgState, adt/, exp/, stmt/, type/, value/
        │   ├── repository/        # IRepository, Repository (states and log file)
        │   ├── controller/        # Controller: one step and all steps
        │   ├── view/              # TextMenu, example programs
        │   └── exceptions/        # MyException and subclasses
        └── test/java/interpreter/ # tests, in the same packages
```

## Design

- **Model:** Types, values, expressions, and statements are records (or small classes). Records give `equals`, `hashCode`, and `toString` for free, and every statement and expression prints itself in infix form, so the trace and the log file show the program as it was written.
- **ADTs:** `IStack`, `IDictionary`, and `IList` are generic interfaces, each implemented by a class that wraps a `java.util` collection.
- **Program state:** `PrgState` holds the execution stack, symbol table, output list, and file table. `PrgState.of(program)` creates a state with empty structures.
- **Repository:** Stores the program states and appends each state to the log file.
- **Controller:** Runs one step (`oneStep`) or the whole program (`allStep`), and sends each state to the repository and the display.
- **View:** Prints the program states and the menu.
- **Errors:** Every error is a `MyException` (or a subclass) with a clear message. Java exceptions such as `IOException` are caught inside the statement that causes them and rethrown as interpreter exceptions, so the menu never crashes.

## Language

### Grammar

```
Prg  ::= Stmt
Stmt ::= Stmt1 ; Stmt2 | Type Id | Id = Exp | print(Exp)
       | if Exp then Stmt1 else Stmt2 | nop
       | openRFile(Exp) | readFile(Exp, Id) | closeRFile(Exp)
Type ::= int | bool | string
Value ::= Number | true | false | "text"
Exp  ::= Value | Id | Exp1 op Exp2
       | Exp1 op Exp2, op ∈ {<, <=, ==, !=, >, >=}
```

Here `op` in `Exp1 op Exp2` stands for `+`, `-`, `*`, `/`, `and`, or `or`.

There is no parser yet. Programs are written directly as object trees in `view/Examples.java`, so operator precedence follows the shape of the tree.

### Examples

| Program | Output |
|---|---|
| `int v; v = 2; print(v)` | `2` |
| `int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)` | `18` |
| `bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)` | `2` |

### Execution

The program state consists of:

| Structure | Content |
|---|---|
| `ExeStack` | statements still to execute, top first |
| `SymTable` | declared variables and their values |
| `Out` | printed values, in order |
| `FileTable` | open files, mapped to their readers |

One step pops the top statement and applies its rule. Execution ends when `ExeStack` is empty.

## Testing

Tests use JUnit 5 and live in `app/src/test/java`, in the same packages as the code they test. Each test builds its own state, and no test depends on another or on the order of execution. Tests that use files create them in a temporary folder with `@TempDir`, so the project folder is never touched.

Run them with `./gradlew test`. The HTML report is written to `app/build/reports/tests/test/index.html`.
