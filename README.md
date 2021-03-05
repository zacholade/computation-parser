# computation-parser

A parser as part of the Foundation of Computation module.

After conversion of a grammar into chomsky normal form, we implemented this context-free grammar (CFG) inside of `MyGrammar.java`.

In `Parser.java`, we then made a parser which can parse grammers and return a boolean if a given string is accepted by the grammar. The algorithm used here is far from optimal, with a standard 'brute force' approach which generates all possible derivations in the grammar of length == word length. A check to see if the word is contained within this list of derivations is then made.

We then implemented a function which constructs parse trees for a given CFG and word.
