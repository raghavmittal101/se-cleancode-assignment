
<img src="https://raw.githubusercontent.com/matiassingers/awesome-readme/master/icon.png" align="right" />

# JavaArgs
### Software Engineering Course Assignment</b> <br> Submitted by Raghav Mittal [2018701023] <br> <a href="www.iiit.ac.in">IIIT-H</a>
<br>
<br>

**JavaArgs** is a java version of Args that is described in the book "Clean Code: A Handbook of Agile Software Craftsmanship" by Robert C. Martin a.k.a. Uncle Bob:older_man:.

This piece of code has been originally forked from the Args program described in: [Uncle Bob](http://butunclebob.com/ArticleS.UncleBob.CleanCodeArgs) :octocat:. 
I have further tried to implement a clean coding practice on this source code written in Java:coffee: as part of Assignment :pencil:.

## Table of Contents
1. [Usage](#usage) :runner::heavy_check_mark:   
1.1[Schema](#schema)  
1.2 [ArgsMain.java](#argsmain)
2. [Understanding the code](#utc)  
2.1 [JavaDocs](#jd) :page_with_curl:  
3. [Objective - Clean code](#objective) :dart:  
3.1 [Code coverage](#coverage) :white_check_mark:  
3.3 [Linting improvements](#linting) :triangular_flag_on_post:  
3.2 [Code smells](#smells) :hotsprings:  
4. [Licence](#licence) :page_facing_up:



### Usage<a name="usage" /> :runner::heavy_check_mark:
You will need to implement a main class to run this file.
#### Schema<a name="schema" />
Refer to the following schema to implemnt main class:

|Schema types |Argument data types|
|---|---
|char | boolean
|char*|string
|char#|Integer
 |char##|double
 |char[*]|one element of a string array.

Example schema: `(f,s*,n#,a##,p[*])`

Coresponding command line: 
```
"-f -s Bob -n 1 -a 3.2 -p e1 -p e2 -p e3"
```

#### `ArgsMain.java`<a name="argsmain" />

You may use the following code to implement the main class ```ArgsMain.java```

```Java
package com.cleancoder.args;

import java.util.Map;

public class ArgsMain {

  /**
  * This method is used to add two integers. This is
  * a the simplest form of a class method, just to
  * show the usage of various javadoc Tags.
  * @param args This is the first paramter to addNum method
  */
  public static void main(String[] args) {
    try {
      Args arg = new Args("l,p#,d*,b##, c[*], m&", args);
      boolean logging = arg.getBoolean('l');
      int port = arg.getInt('p');
      double dob = arg.getDouble('b');
      String[] cs = arg.getStringArray('c');
      Map<String, String> ma = arg.getMap('m');
     
      String directory = arg.getString('d');
      executeApplication(logging, port, directory, dob, cs, ma);
    } catch (ArgsException e) {
      System.out.printf("Argument error: %s\n", e.errorMessage());
    }
  }

  private static void executeApplication(boolean logging, int port, String directory, double dob, String[] cs, Map<String, String> ma) {
    System.out.printf("logging is %s, port:%d, directory:%s, double:%f, stringArray:%s, map:%s",logging, port, directory, dob, cs[0], ma);
  }
}
```

## Understanding the Code<a name="utc" />

### JavaDocs<a name="jd" /> :page_with_curl:

To understand the code better follow the [JavaDocs](https://raw.githack.com/raghavmittal101/se-cleancode-assignment/master/docs/index.html) given for the code.

Javadocs for tests are not added. For more discussion on JavaDocs for test check this [Stackoverflow](https://stackoverflow.com/a/2968153/3801905).

## Objective - Clean Code<a name="objective" /> :dart:
The goal of this exercise is to implement clean code practices in the code for JavaArgs and that include the following :

1. [Improve code-coverage](#cc-improvements)
    * [Remove unused lines of code](#lines-removed)
    * [Add tests for uncovered code](#lines-added)
2. [Improve linting](#linting)
3. [Remove code smells](#smells)
    * [God class](#smells)
    * [Long method](#smells)

For the above changes some tools were used which needs to be mentioned here :

* Code smell: [Jdeodrant](http://jdeodorant.com/) integration in Eclipse.
* Code Coverage: [Junit](https://junit.org/junit5/) 
* Linting: [CheckStyle](https://marketplace.eclipse.org/content/checkstyle-plug) integration in Eclipse 

### Code Coverage:<a name="coverage" /> :white_check_mark:

* Code coverage has been <span style="color:green">imporved</span> from 88.5% to **91%** overall. 
* Code coverage is **97%** if tests are not considered. As per the discussion online, tests are generally not covered during the coveraged for more information check this [Stackoverflow](https://stackoverflow.com/a/24958299/3801905) link.

#### Code Coverage Improvements<a name="cc-improvements" />

Below mentioned are the refactored pieces of code that helped imporve Code Coverage:

#### ```ArgsException.java```<a name="lines-removed" /> 

Unused lines were <b style="color:darkred">removed</b>:x: as they were affecting it significantly.

```Java
// these lines were removed
public  ArgsException() {}

public  ArgsException(String message) {super(message);}

public void setErrorCode(ErrorCode  errorCode) {  this.errorCode = errorCode;}
```
</br>

#### `ArgsExceptionTest.java`<a name="lines-added" />
`testOkMessage()` was <b style="color:green">added</b>:heavy_plus_sign: because <i>**OK**</i> enum was not covered
```Java
// following lines were added
public void testOkMessage() throws Exception {

ArgsException e = new ArgsException(OK, 'x', null);

assertEquals("TILT: Should not get here.", e.errorMessage());

}
```


#### Linting Improvements<a name="linting" /> :triangular_flag_on_post:

#### `ArgsData.java`

Following unused line was <b style="color:darkred">removed</b>:x:

```Java
public ArgsData() {}

```

#### `ArgsException.java`
<i>Return</i> statement shifted from outside of switch case to default case inside switch case.

#### <b style="color:orange">Modifications</b>:large_orange_diamond: done accross the package

* Removed wildcards from all import statement in the project. [also from java utils]

* There are various ways to add *imports* in java and one of the two ways is adding imports with ```wildcards (*)```, I chose to remove all the wildcards and manually add imports taking a reference from the following discussion [Stackoverflow](https://stackoverflow.com/questions/147454/why-using-a-wild-card-with-a-java-import-statement-bad/147461#147461).

* Followed some code conventions where I added ```static``` imports before all the util imports  and arranged alphabetically.

### Code Smells<a name="smells" /> :hotsprings:
Major code smells were found in the code. In order to remove those code smells, <b>modifications</b>:large_orange_diamond: were done in `Args.java` and a new file `ArgsData.java` was <b style="color:green">added</b>:heavy_plus_sign:.
<!-- 
Those were in 

1. **God Class**
..* for ```schema``` Extract Class
2. **Long Method**
..* Extract Method for variable criteria **'m'**
 -->

The changes that are stated below are in-coherence with the following manual which clearly states the decomposition methodology. For details refer to [Jdeodrant](https://users.encs.concordia.ca/~nikolaos/jdeodorant/index.php?option=com_content&view=article&id=45).


Smell type | Refactoring Type | Variable Criteria | File <span style="color:orange">modified</span>:large_orange_diamond:/<span style="color:darkgreen">added</span>:heavy_plus_sign:
--- | --- | --- | ---
Long method | Extract method | <i>m</i> | `Args.java` <span style="color:orange">modified</span>
God Class | Extract method | <i>schema</i> | `ArgsData.java` <span style="color:darkgreen">added</span>;<br> `Args.java` <span style="color:orange"> modified</span>
---

File ```ArgsData.java``` was <b style="color:darkgreen">created</b>:heavy_plus_sign: in order to decompose the god class. Following Code from ```Args.java``` has been decompsed into ```ArgsData.java```

```Java
private void parseSchemaElement(String element) throws ArgsException { 
    char elementId = element.charAt(0); 
    String elementTail = element.substring(1); 
    validateSchemaElementId(elementId); 
    if (elementTail.length() == 0) 
      marshalers.put(elementId, new BooleanArgumentMarshaler()); 
    else if (elementTail.equals("*")) 
      marshalers.put(elementId, new StringArgumentMarshaler()); 
    else if (elementTail.equals("#")) 
      marshalers.put(elementId, new IntegerArgumentMarshaler()); 
    else if (elementTail.equals("##")) 
      marshalers.put(elementId, new DoubleArgumentMarshaler()); 
    else if (elementTail.equals("[*]")) 
      marshalers.put(elementId, new StringArrayArgumentMarshaler()); 
    else if (elementTail.equals("&")) 
      marshalers.put(elementId, new MapArgumentMarshaler()); 
    else 
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail); 
  } 

  private void validateSchemaElementId(char elementId) throws ArgsException { 
    if (!Character.isLetter(elementId)) 
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null); 
  } 

public boolean getBoolean(char arg) { 
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg)); 
  } 

  public String getString(char arg) { 
    return StringArgumentMarshaler.getValue(marshalers.get(arg)); 
  } 

  public int getInt(char arg) { 
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg)); 
  } 

  public double getDouble(char arg) { 
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg)); 
  } 

  public String[] getStringArray(char arg) { 
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg)); 
  } 

  public Map<String, String> getMap(char arg) { 
    return MapArgumentMarshaler.getValue(marshalers.get(arg)); 
  } 
} 
```

## License<a name="licence" /> :page_facing_up:
[MIT](https://choosealicense.com/licenses/mit/)
