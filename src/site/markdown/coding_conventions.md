Forewords
---------

This document describes a list of coding conventions that are required
for code submissions to the project. By default, the coding conventions
for most Open Source Projects should follow the existing coding
conventions in the code that you are working on. For example, if the
bracket is on the line after the if statement, then you should write all
your code to have that convention.

Below is a list of coding conventions that are specific to this project.
Anything else not specifically mentioned here should follow the official
[Sun Java Coding
Conventions](http://java.sun.com/docs/codeconv/html/CodeConvTOC.doc.html).

### Project specific coding conventions

1.  Brackets

    All brackets (class, method, if, try, etc) must begin and end on a
    new line. Example :

        public class SomeClass {
            public void someMethod() {
                if (xxx) {
                }
            }
        }

    Brackets are mandatory, even for single line statements!

        // Incorrect
        if (expression)
            // some code

        // Correct
        if (expression) {
            // some code
        }

2.  Blank Spaces

    keywords followed by a parenthesis should be separated by a space.
    Example :

        while (true) {
            // some code
        }

    Blank space should appear after commas in argument lists. Binary
    operators should be separated from their operands by spaces :

        a += c + d;
        a = (a + b) / (c * d);

        while (d++ = s++) {
            n++;
        }

        printSize("size is " + foo + "\n");

3.  Indentations

    4 spaces. NO tabs . Period. We understand that a lot of you like to
    use tabs, but the fact of the matter is that in a distributed
    development environment, when the commit messages get sent to a
    mailing list, they are almost impossible to read if you use tabs.

4.  Comments

    Javadoc SHOULD exist on all your class members (methods + class
    variables), including the private ones. Also, if you are working
    on existing code and there currently isn't a javadoc for that
    method/class/variable or whatever, then you should contribute
    and add it. This will improve the project as a whole.

    Also add code comments when you think it's necessary (like
    assumptions), especially when the code is not obvious.

5.  Class variables

    Class variables should not have any prefix and must be referenced
    using the this object. Example :

        public class SomeClass
        {
            private String someString;
        [...]
            public void someMethod() {
                logger.debug("Value = " + this.someString);
            }
        }

6.  Parameter names

    Method parameters should not have any prefix. For example :

        public void someMethod(String className)
        {
        }

7.  Line length

    Avoid lines longer than 120 characters for Code, comments, ...

8.  Qualified imports

    All import statements should containing the full class name of
    classes to import and should not use the "\*" notation :

    An example:

        // Correct
        import java.util.Date;
        import java.net.HttpURLConnection;

        // Not correct
        import java.util.*;
        import java.net.*;
