import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import computation.contextfreegrammar.*;

public class MyGrammar {
	public static ContextFreeGrammar makeGrammar() {
		// You can write your code here to make the context-free grammar from the assignment
		Variable S = new Variable('S');
		Variable Q = new Variable('Q');
		Variable T = new Variable('T');
		Variable F = new Variable('F');
		Variable B = new Variable('B');
		Variable Y = new Variable('Y');
		Variable Z = new Variable('Z');
		Variable L = new Variable('L');
		Variable R = new Variable('R');
		Variable A = new Variable('A');
		Variable M = new Variable('M');

		Terminal lb = new Terminal('(');
		Terminal rb = new Terminal(')');
		Terminal plus = new Terminal('+');
		Terminal times = new Terminal('*');
		Terminal one = new Terminal('1');
		Terminal zero = new Terminal('0');
		Terminal x = new Terminal('x');

		// S -> QY
		Rule s1 = new Rule(S, new Word(Q, Y));
		// S -> TZ
		Rule s2 = new Rule(S, new Word(T, Z));
		// S -> LB
		Rule s3 = new Rule(S, new Word(L, B));
		// S -> 1
		Rule s4 = new Rule(S, new Word(one));
		// S -> 0
		Rule s5 = new Rule(S, new Word(zero));
		// S -> x
		Rule s6 = new Rule(S, new Word(x));

		// Q -> QY
		Rule q1 = new Rule(Q, new Word(Q, Y));
		// Q -> TZ
		Rule q2 = new Rule(Q, new Word(T, Z));
		// Q -> LB
		Rule q3 = new Rule(Q, new Word(L, B));
		// Q -> 1
		Rule q4 = new Rule(Q, new Word(one));
		// Q -> 0
		Rule q5 = new Rule(Q, new Word(zero));
		// Q -> x
		Rule q6 = new Rule(Q, new Word(x));

		// T -> TZ
		Rule t1 = new Rule(T, new Word(T, Z));
		// T -> LB
		Rule t2 = new Rule(T, new Word(L, B));
		// T -> 1
		Rule t3 = new Rule(T, new Word(one));
		// T -> 0
		Rule t4 = new Rule(T, new Word(zero));
		// T -> x
		Rule t5 = new Rule(T, new Word(x));

		// F -> LB
		Rule f1 = new Rule(F, new Word(L, B));
		// F -> 1
		Rule f2 = new Rule(F, new Word(one));
		// F -> 0
		Rule f3 = new Rule(F, new Word(zero));
		// F -> x
		Rule f4 = new Rule(F, new Word(x));

		// B -> QR
		Rule b1 = new Rule(B, new Word(Q, R));

		// Y -> AT
		Rule y1 = new Rule(Y, new Word(A, T));

		// Z -> MF
		Rule z1 = new Rule(Z, new Word(M, F));

		// L -> (
		Rule l1 = new Rule(L, new Word(lb));

		// R -> )
		Rule r1 = new Rule(R, new Word(rb));

		// A -> +
		Rule a1 = new Rule(A, new Word(plus));

		// M -> *
		Rule m1 = new Rule(M, new Word(times));

		List<Rule> rules = new ArrayList<Rule>();
		rules.add(s1);
		rules.add(s2);
		rules.add(s3);
		rules.add(s4);
		rules.add(s5);
		rules.add(s6);
		rules.add(q1);
		rules.add(q2);
		rules.add(q3);
		rules.add(q4);
		rules.add(q5);
		rules.add(q6);
		rules.add(t1);
		rules.add(t2);
		rules.add(t3);
		rules.add(t4);
		rules.add(t5);
		rules.add(f1);
		rules.add(f2);
		rules.add(f3);
		rules.add(f4);
		rules.add(b1);
		rules.add(y1);
		rules.add(z1);
		rules.add(l1);
		rules.add(r1);
		rules.add(a1);
		rules.add(m1);

		ContextFreeGrammar cfg = new ContextFreeGrammar(rules);
		return cfg;
	}
}
