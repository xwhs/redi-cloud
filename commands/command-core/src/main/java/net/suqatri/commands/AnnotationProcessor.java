/*
 * Copyright (c) 2016-2018 Daniel Ennis (Aikar) - MIT License
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.suqatri.commands;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Set;

@Deprecated /* @deprecated UNFINISHED */
public interface AnnotationProcessor <T extends Annotation> {

    @Nullable
    default Set<Class<?>> getApplicableParameters() {
        return null;
    }

    default void onBaseCommandRegister(BaseCommand command, T annotation) {

    }
    default void onCommandRegistered(RegisteredCommand command, T annotation) {

    }
    default void onParameterRegistered(RegisteredCommand command, int parameterIndex, Parameter p, T annotation) {

    }
    default void onPreComand(CommandOperationContext context) {

    }
    default void onPostComand(CommandOperationContext context) {

    }
    default void onPreContextResolution(CommandExecutionContext context) {

    }
    default void onPostContextResolution(CommandExecutionContext context, Object resolvedValue) {

    }

}