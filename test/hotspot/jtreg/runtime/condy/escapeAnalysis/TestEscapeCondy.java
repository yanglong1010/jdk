/*
 * Copyright (c) 2019, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 8216970
 * @summary Ensure escape analysis can handle an ldc of a dynamic
 *          constant whose return type is an array of boolean.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile TestEscapeThroughInvokeWithCondy$A.jasm
 * @compile TestEscapeThroughInvokeWithCondy.jasm
 * @compile TestEscapeCondy.java
 * @run driver TestEscapeCondy
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.compiler.InMemoryJavaCompiler;

public class TestEscapeCondy {
    public static void main(String args[]) throws Throwable {
        // 1. Test escape analysis of a method that contains
        //    a ldc instruction of a condy whose return type is an array of boolean
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
             "-XX:CompileCommand=dontinline,runtime.condy.TestEscapeThroughInvokeWithCondy::create",
             "runtime.condy.TestEscapeThroughInvokeWithCondy");
        OutputAnalyzer oa = new OutputAnalyzer(pb.start());
        oa.shouldContain("Test has successfully analyzed ldc bytecode within method create");
        oa.shouldHaveExitValue(0);
    }
}
