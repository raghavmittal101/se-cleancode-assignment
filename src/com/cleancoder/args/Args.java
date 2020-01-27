
/**
 * <a href = "https://github.com/raghavmittal101/se-project/tree/dev">Link to Github Repo of this package.</a>
 * @author Raghav Mittal
 */

package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.UNEXPECTED_ARGUMENT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * This is the root class. All other methods 
 are invoked either directly or indirectly from this class.
 * @see Args
 * @see ArgsData
 * @see ArgsException
 */
public class Args {
  private ArgsData data = new ArgsData();
  
  /**
  * Processes {@code args} string array according to schema defined in {@code schema} string.
  * @param schema Set of schema definitions(elementId + elementTail) separated by commas.
  * <li>Example: {@code "p#, ch*, d##"} where elementId is <i>p</i> and elementTail is <i>#</i>. 
  Similarly in <i>ch*</i>, <i>ch</i> and <i>*</i> are elementId and elementTail respectively.</li>
  * @param args  String array which may contain command line input. 
  * <li>Example: {@code "-p 34 -ch abcd -d 3.2"}</li>
  * @throws ArgsException {@link com.cleancoder.args.ArgsException}
  * @see com.cleancoder.args.ArgsData
  */
  public Args(String schema, String[] args) throws ArgsException {
    data.marshalers = new HashMap<Character, ArgumentMarshaler>();
    data.argsFound = new HashSet<Character>();   
    data.parseSchema(schema);
    parseArgumentStrings(Arrays.asList(args));
  }

  /**
  * Parses out {@code elementId}(s) from the {@code args} string.
  * Passes them further to validate and identify the marshaler corresponding to them.
  * @param argsList List of substrings of {@code args} string.
  * @throws ArgsException {@link com.cleancoder.args.ArgsException}
  * @see com.cleancoder.args.ArgsData#parseSchemaElement method.
  */
  private void parseArgumentStrings(List<String> argsList) throws ArgsException {
    for (data.currentArgument = argsList.listIterator(); data.currentArgument.hasNext();) {
      String argString = data.currentArgument.next();
      if (argString.startsWith("-")) {
        parseArgumentCharacters(argString.substring(1));
      } else {
        data.currentArgument.previous();
        break;
      }
    }
  }

  /**
  * Passes the {@code elementId}(s) extracted from {@code args}
  to {@link com.cleancoder.args.Args#parseArgumentCharacter parseArgumentCharacter} method.
  * @param argChars List of {@code elementId}s extracted from {@code args} string.
  * @throws ArgsException {@link com.cleancoder.args.ArgsException}
  */
  private void parseArgumentCharacters(String argChars) throws ArgsException {
    for (int i = 0; i < argChars.length(); i++) {
      parseArgumentCharacter(argChars.charAt(i));
    }
  }

  /**
  * Checks if the 
  * Validates {@code elementId} character given in command line argument
  * to check if there exists a matching schema.
  * @param argChar schema given in command line input
  * @throws ArgsException {@code UNEXPECTED_ARGUMENT} if flag don't match with schema.
  * @see com.cleancoder.args.ArgsData#argsFound method.
  */
  private void parseArgumentCharacter(char argChar) throws ArgsException {
    ArgumentMarshaler m = setArgumentCharacter(argChar);
    if (m == null) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    } else {
      data.argsFound.add(argChar);
    }
  }

  /**
  * Checks if the {@code elementId} given in {@code args} string exists in 
  {@link com.cleancoder.args.ArgsData#marshalers map} map. 
  * If {@code True} then {@code marshaler} corresponding to that {@code elementId} is executed.
  * @param argChar element from list of {@code elementId}(s) extracted from {@code args} string.
  * @return m Marshaler method if specified in the map for given 
  {@code argChar} else returns null.
  * @throws ArgsException {@link com.cleancoder.args.ArgsException}
  * @see com.cleancoder.args.ArgsData
  */
  private ArgumentMarshaler setArgumentCharacter(char argChar) throws ArgsException {
    ArgumentMarshaler m = data.marshalers.get(argChar);
    if (m == null) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    } else {
      try {
        m.set(data.currentArgument);
      } catch (ArgsException e) {
        e.setErrorArgumentId(argChar);
        throw e;
      }
    }
    return m;
  }

  public boolean has(char arg) {
    return data.argsFound.contains(arg);
  }

  public int nextArgument() {
    return data.currentArgument.nextIndex();
  }

  public boolean getBoolean(char arg) {
    return BooleanArgumentMarshaler.getValue(data.marshalers.get(arg));
  }

  public String getString(char arg) {
    return StringArgumentMarshaler.getValue(data.marshalers.get(arg));
  }

  public int getInt(char arg) {
    return IntegerArgumentMarshaler.getValue(data.marshalers.get(arg));
  }

  public double getDouble(char arg) {
    return DoubleArgumentMarshaler.getValue(data.marshalers.get(arg));
  }

  public String[] getStringArray(char arg) {
    return StringArrayArgumentMarshaler.getValue(data.marshalers.get(arg));
  }

  public Map<String, String> getMap(char arg) {
    return MapArgumentMarshaler.getValue(data.marshalers.get(arg));
  }
}