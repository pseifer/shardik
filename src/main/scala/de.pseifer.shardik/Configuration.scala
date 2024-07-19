package de.pseifer.shardik

import de.pseifer.shar.core.ReasonerInitialization
import de.pseifer.shar.reasoning.DLReasoner
import de.pseifer.shar.core.PrefixMapping
import de.pseifer.shar.reasoning.HermitReasoner
import de.pseifer.shar.core.EmptyInitialization

/** Repl Configuration.
  *
  * @param reasoner
  *   constructor for a `DLReasoner`; only required if custom instance of
  *   `DLReasoner` is defined
  * @param init
  *   a custom reasoner initialization (e.g., ontology IRI).
  * @param script
  *   a sequence of lines to execute.
  * @param prefixes
  *   a custom, predefined prefix mapping
  * @param defaultIsSharPrefix
  *   use the prefix 'shar:' as default (':')
  * @param noisy
  *   Always output to stdout (e.g., for entailment ⊢),
  *   instead of only for certain commands.
  * @param silent
  *   Supress command outputs.
  * @param interactive
  *   Launch interactive mode.
  * @param entailmentMode
  *   Start in entailmentMode.
  * @param infoline
  *   Information to be displayed at launch.
  */
case class Configuration(
    reasoner: ReasonerInitialization => DLReasoner = HermitReasoner(_),
    init: ReasonerInitialization = EmptyInitialization(),
    script: Seq[String] = Seq(),
    prefixes: PrefixMapping = PrefixMapping.default,
    defaultIsSharPrefix: Boolean = true,
    noisy: Boolean = false,
    silent: Boolean = false,
    interactive: Boolean = false,
    entailmentMode: Boolean = false,
    infoline: String = "shar"
)
