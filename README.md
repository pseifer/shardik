<p align="center">
  <img width="800" height="140" src="resources/shardik.svg?raw=true">
</p>

# SHAR Direct Interactive Knowledgebase

- [Using SHARDIK](#using-shardik)
  - [Command Line Interface](#command-line-interface)
  - [Language](#language)
- [References and Examples](#references-and-examples)

*SHARDIK* is first and foremost a REPL for interacting with HermiT (and some [other](#references-and-examples) OWL-API reasoners) directly by defining axioms (or loading OWL ontologies) and checking axioms for entailment and satisfiability. It is also possible to load and execute the various available commands from a file, making *SHARDIK* scriptable -- indeed, *SHARDIK* returns its final result (e.g., entailment of some axioms) as its exit code.

## Using SHARDIK

*SHARDIK* can be most easily invoked via the included script ```shardik```. Let us first look at using ```shardik``` to interpret script files. Consider a file [kb.shar](resources/kb.shar) containing the following axiom definitions:

```
:NiceChild ⊑ :Child ⊓ :Nice
:Child ⊑ :Person
:Person ⊑ :Agent
:Agent ⊑ ∃:knows.:Agent
```

Invoking ```./shardik resources/kb.shar``` parses and then loads these four axioms in HermiT, before terminating and printing nothing. Exciting. So let us extend the *SHARDIK* source file with a few more lines, introducing some addtional commands and entailment checks ([example.shar](resources/example.shar)):

```
-- This is the knowledge base (and a comment).
:NiceChild ⊑ :Child ⊓ :Nice
:Child ⊑ :Person
:Person ⊑ :Agent
:Agent ⊑ ∃:knows.:Agent
info.

-- These are the tests to be performed.
⊢ :NiceChild ⊑ ∃:knows.:Agent
result.
```

Both "info." and "result." are commands. These commands (recognizable from the "." terminating them) perform certain actions, in this case, "info." prints all preceeding, not-yet-printed axioms added to the KB, and "result." prints the results of all preceeding entailment tests that were not yet printed. Another useful command -- when in a REPL session -- is "help." for help and "quit." to quit. Most commands can be shortened to a single letter (e.g., "i." instead of "info."), though this is again mostly useful in the interactive REPL.

The preceeding document, when passed to ```shardik``` (```./shardik resources/example.shar```), will print all axioms and the entailment result. It will also terminate with return code '0', because the final entailment is true. If we were only interested in the result of this single entailment, we could also call ```./shardik resources/kb.shar --entails ":Child ⊑ :Agent"``` (with the initial four-lined file, otherwise, we would print stuff) and the exit code would indicate the result. We could also make this again more human-readable by using ```./shardik resources/kb.shar --entails ":Child ⊑ :Agent" --command "result."```, which will (finally) call the "result." command.

Let's next call ```./shardik --repl resources/kb.shar```. This will throw us into an interactive REPL session, after executing the ```kb.shar``` script, that is, the respective axioms are already loaded. Since we supplied a script file, *SHARDIK* assumes that we want to be in *entailment mode*: Any axiom we enter at the REPL will be checked for entailment. If we want to add additional axioms instead, we can use the command "normal." to return to *normal mode*, where axioms are added, unless prefixed with "⊢" (or `:-`), just as when using a script file. Usually, it is easiest to write axioms to a file, load it into the REPL, and then experiment in *entailment mode*. Of course, all commands are available at the REPL as well.

### Command Line Interface

We start with printing helpful information about the CLI.

```sh
./shardik --help
```

Usually, a REPL session is started when the flag ```--repl``` is supplied. However, in some cases (e.g., when no script file, no entailment test, and no command is supplied), *SHAR* will automatically launch a REPL.

```sh
./shardik --repl
```

Start a REPL session, loading axioms from ```resources/kb.shar```.

```sh
./shardik --repl resources/kb.shar
```

Run a script file (without starting a REPL) and possibly produce output (running commands in the script, if any). Return, as exit code, the result of the final entailment or satisfiability test, if any.

```sh
./shardik resources/example.shar
```

Load axioms from a file but supress any output; terminate with exit code depending on the entailment of the supplied axiom.

```sh
./shardik --silent resources/kb.shar --entails ":NiceChild ⊑ ∃:knows.:Agent"
```

Start a REPL session, loading the [Wine](https://www.w3.org/TR/owl-guide/wine.rdf) ontology and an appropriate default prefix, defined in SPARQL syntax in the file [wine.prefix](resources/wine.prefix).

```sh
./shardik --owl "https://www.w3.org/TR/owl-guide/wine.rdf" --prefixes resources/wine.prefix
```

Do the same as before, but instead of launching a REPL session, prove that RedWine is Wine and terminate with the appropriate exit code. Note, that this makes *SHAR* a useful tool in the context of more general purpose shell scripting.

```sh
./shardik --owl "https://www.w3.org/TR/owl-guide/wine.rdf" --prefixes resources/wine.prefix --entails ":RedWine ⊑ :Wine"
```

A bonus feature of the launcher script allows for watching a file for changes and refreshing *SHARDIK*, by relaunching it automatically. This requires the utility tool `entr` and is only supported on GNU/Linux and macOS systems (and possibly other UNIX or UNIX-like systems).

```sh
./shardik --watch resources/example.shar
```

### Language

*SHARDIK* has a few basic features, outlined in this section. Each line is processed as one of a few categories: Comments, prefixed with either ```//```, ```#```, or ```--```; commands, terminated with ```.```; entailment tests (in normal mode) prefixed with ```⊢``` or ```:-```; satisfiability tests ending with ``` ?``` (optional); and axioms to be added to the knowledge base (in normal mode; in entailment mode, any axiom is interpreted as an entailment test).

Concept expressions, entailment tests, and axioms can be specified using a formal Unicode-based syntax, or an ASCII-only variant. The correspondence and meaning of each symbol is listed in the following table.

| Symbol Name | ASCII | Unicode |
|-|-|-|
| Top | `#t` | `⊤` |
| Bottom | `#f` | `⊥` |
| Union | `\|` | `⊔` |
| Intersection | `&` | `⊓` |
| Universal | `#A` | `∀` |
| Existential | `#E` | `∃` |
| Complement | `!` | `¬` |
| Inclusion | `<<` | `⊑` |
| Equivalency | `==` | `≡` |
| Entailment | `:-` | `⊢` |
| Inverse (Role) | `-` | `-` |
| Inclusion (Role) | `<-` | `<-` |

## References and Examples

- [HermiT Reasoner](http://www.hermit-reasoner.com/)
- [Openllet Reasoner](https://github.com/Galigator/openllet)
- [JFact Reasoner](https://jfact.sourceforge.net/)
- The [SHAR](https://github.com/pseifer/shar) library, on which *SHARDIK* is built

## Shardik Logo

The Shardik Logo is based on the [DL](https://dl.kr.org/logo.html) logo, includes [this bear](https://commons.wikimedia.org/wiki/File:West_berlin_bear.svg) (CC-BY-SA) and is distributed under the same licence.
