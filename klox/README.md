# klox

klox is a Kotlin implementation of a tree-walking interpreter for the Lox programming
language from the excellent book [Crafting Interpreters](https://craftinginterpreters.com/)
by Bob Nystrom.

This implementation is mostly a direct port of jlox (the original implementation in the
book) from Java to Kotlin, but with some minimal differences owing to use of Kotlin's
features such as null safety and sealed classes. For example, sealed classes completely
obviated the need for implementing the visitor pattern and writing a class generator
script like the one used in the book.

## Extra Features

In addition to the features developed in the book, klox has:

- Support for Unicode characters in identifiers. The ranges of allowed characters follow
  those defined in the [Unicode Standard Annex #31](https://unicode.org/reports/tr31/).

## Building

To build and run klox, a sufficiently recent version of the JVM must be installed.

If this requirement is met, run `./gradlew build` in the root of the repository to build
klox, or run `./gradlew run -q --console=plain` to both build and run klox.
