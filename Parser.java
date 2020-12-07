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
        // We need to make a copy of the derivation. This is because if we need to branch our derivation,
        // we only find out once we've already changed the current derivation. At which point we need the
        // original derivation.
        Derivation copyDerivation = new Derivation(derivation);
        Word word = derivation.getLatestWord();

        int wordIndex = 0;
        boolean added = false;  // Bool denoting if we already made a change for this step in this derivation. If true, branch off
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
              Derivation newDerivation = new Derivation(copyDerivation);
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
    int numberDerivations;
    if (wordLength == 0) {
      numberDerivations = 0;
    } else {
      numberDerivations = (2 * wordLength) - 1;
    }

    List<Derivation> allDerivations = generateDerivations(cfg, numberDerivations);

    for (Derivation derivation: allDerivations) {
      if (w.equals(derivation.getLatestWord())) {
        return true;
      }
    }
    return false;
  }

  private ParseTreeNode buildParseTreeNode(Derivation d) {
    Word finalWord = d.getLatestWord();
    List<ParseTreeNode> endNodes = new ArrayList<ParseTreeNode>();
    for (Symbol s: finalWord) {
      endNodes.add(new ParseTreeNode(s));
    }
    
    for (Step s: d) {
      Rule parentRule = s.getRule();
      if (parentRule == null) {
        break;
      }
      Symbol parentSymbol = parentRule.getVariable();
      int stepIndex = s.getIndex();
      Word expansion = s.getRule().getExpansion();
      if (expansion.length() > 1) {
        ParseTreeNode parentNode = new ParseTreeNode(parentSymbol, endNodes.get(stepIndex), endNodes.get(stepIndex + 1));
        endNodes.remove(stepIndex);
        endNodes.remove(stepIndex);
        endNodes.add(stepIndex, parentNode);
      } else {
        ParseTreeNode parentNode = new ParseTreeNode(parentSymbol, endNodes.get(stepIndex));
        endNodes.remove(stepIndex);
        endNodes.add(stepIndex, parentNode);
      }

    }
    return endNodes.get(0);
  }

  public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {
    int wordLength = w.length();
    int numberDerivations;
    if (wordLength == 0) {
      numberDerivations = 0;
    } else {
      numberDerivations = (2 * wordLength) - 1;
    }

    List<Derivation> allDerivations = generateDerivations(cfg, numberDerivations);

    Derivation d = null;
    for (Derivation derivation: allDerivations) {
      if (w.equals(derivation.getLatestWord())) {
        d = derivation;
      }
    }
    ParseTreeNode ptn = null;
    if (d != null) {
      ptn = buildParseTreeNode(d);
    } else {
      ptn = null;
    }
    return ptn;
  }
}
