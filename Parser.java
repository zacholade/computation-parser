import java.util.ArrayList;
import java.util.List;

import computation.contextfreegrammar.*;
import computation.parser.*;
import computation.parsetree.*;
import computation.derivation.*;

public class Parser implements IParser {

  private List<Rule> getRulesBySymbol(Symbol v, ContextFreeGrammar cfg) {
    List<Rule> rulesToReturn = new ArrayList<Rule>();
    if (v.isTerminal()) {  // Check if the symbol is a terminal first. This shouldnt happen in our code though!! If so just return empty list of rules.
      return rulesToReturn;
    }

    List<Rule> rules = cfg.getRules();
    for (Rule r: rules) {
      if (r.getVariable().equals(v)) {
        rulesToReturn.add(r);
      }
    }
    return rulesToReturn;
  }

  private List<Derivation> generateDerivations(ContextFreeGrammar cfg, int n) {
    List<Derivation> allDerivations = new ArrayList<Derivation>();

    Variable startVariable = cfg.getStartVariable();
    Derivation zerothDerivation = new Derivation(new Word(startVariable));
    allDerivations.add(zerothDerivation);

    List<Derivation> derivationsToAdd = new ArrayList<Derivation>();
    for (int i = 0; i < n; i++) {
      for (Derivation dToAdd: derivationsToAdd) {
        allDerivations.add(dToAdd);
      }
      derivationsToAdd = new ArrayList<Derivation>();

      for (Derivation derivation: allDerivations) {
        Word word = derivation.getLatestWord();

        int wordIndex = 0;
        boolean added = false;
        for (Symbol symbol: word) {
          if (symbol.isTerminal()) {
            wordIndex++;
            continue;
          }

          List<Rule> rules = getRulesBySymbol(symbol, cfg);

          for (Rule rule: rules) {
            Word expansion = rule.getExpansion();
            Word newWord = word.replace(wordIndex, expansion);
            
            if (added) {
              Derivation newDerivation = new Derivation(derivation);
              newDerivation.addStep(newWord, rule, wordIndex);
              derivationsToAdd.add(newDerivation);
            } else {
              derivation.addStep(newWord, rule, wordIndex);
              added = true;
            }
          }
          wordIndex++;
        }
      }
    }
    return allDerivations;
  }

  public boolean isInLanguage(ContextFreeGrammar cfg, Word w){
    int wordLength = w.length();
    int numberDerivations = (2 * wordLength) - 1;

    List<Derivation> allDerivations = generateDerivations(cfg, numberDerivations);

    for (Derivation derivation: allDerivations) {
      for (Step step: derivation) {
        System.out.println(step);
      }
      System.out.println("----------------------");
      // if (w.equals(derivation.getLatestWord())) {
      //   return true;
      // }
    }
    return false;
  }

  public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {
    return null;
  }
}