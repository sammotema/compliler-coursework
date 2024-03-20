package com.arjunsk.compiler.ck;

import com.arjunsk.compiler.ck.domain.tree.ParseTree;
import com.arjunsk.compiler.ck.lexer.Lexer;
import com.arjunsk.compiler.ck.parser.Parser;
import com.arjunsk.compiler.ck.visitor.codegenerator.CodeGeneratorVisitor;
import com.arjunsk.compiler.ck.visitor.interpreter.InterpreterVisitor;
import com.arjunsk.compiler.ck.visitor.semantic.SemanticAnalyzer;
import com.arjunsk.compiler.utils.FileReaderUtil;
import org.junit.Assert;
import org.junit.Test;

public class CkCompilerTest {

  @Test
  public void test_e2e() {
    // 0. Input Code
    final String sourceCode = FileReaderUtil.getResourceFileAsString("input/CgSample.ck");

    // 1. Lexer
    assert sourceCode != null;
    Lexer lexer = new Lexer(sourceCode);

    // 2. Parser
    Parser parser = new Parser(lexer);
    ParseTree tree = parser.parseProgram();

    Assert.assertNotNull(tree);

    // 3. Semantic Analyzer Visitor
    tree.accept(new SemanticAnalyzer());

    // 4.1 Interpreter Visitor
    tree.accept(new InterpreterVisitor());

    // 4.2 Compiler Visitor
    tree.accept(new CodeGeneratorVisitor());
  }
}
