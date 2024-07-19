package de.pseifer.shardik

import de.pseifer.shardik.ReadEvalPrintLoop
import de.pseifer.shardik.CommandLineInterface
import de.pseifer.shardik.Configuration

// TODO:
// Save 'session' to script (i.e., what user entered in REPL).
//  - save. -- generate timestamp file 'session-<time>.shar'
//  - save "example.shar". -- save as 'example.shar'
//
// Save loaded ontology to file with 'export ""?'

/** Parse commaind line arguments and run REPL. */
@main def main(args: String*): Unit =
  // Initialize CLI parser and process the CLI arguments.
  CommandLineInterface(Configuration(), args).toConfiguration match
    // Exit in case of errors.
    case Left(e) =>
      println(e.show)
      System.exit(2)
    // Launch REPL/interpreter otherwise.
    case Right(rconf) => ReadEvalPrintLoop(rconf).run()
