package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class S1106 {
    public boolean parseBoolExpr(String expression) {
        Stack<Character> stack = new Stack<>();
        for (int idx = 0; idx < expression.length(); ++idx) {
            switch (expression.charAt(idx)) {
                case ')':
                    Set<Boolean> inner = new HashSet<>();
                    while (stack.peek() != '(') {
                        inner.add('t' == stack.pop());
                    }
                    stack.pop();//'('
                    char logicOperator = stack.pop();
                    boolean answer = false;
                    switch (logicOperator) {
                        case '|':
                            answer = false;
                            for (boolean each : inner) {
                                answer |= each;
                            }
                            break;
                        case '&':
                            answer = true;
                            for (boolean each : inner) {
                                answer &= each;
                            }
                            break;
                        case '!':
                            answer = !inner.iterator().next();
                            break;
                    }
                    stack.add(answer ? 't' : 'f');
                    break;
                case ',':
                    // no-op
                    break;
                case '|', '&', '!', '(', 't', 'f':
                    stack.add(expression.charAt(idx));
                    break;
            }
        }
        return stack.pop() == 't';
    }

    @Test
    void test() {
        System.out.println(parseBoolExpr("!(&(f,t))"));
    }
}
