package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static com.cleancoder.args.ArgsException.ErrorCode.INVALID_ARGUMENT_NAME;

import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible for processing the {@code schema} argument 
 in {@link com.cleancoder.args.Args#Args Args} constructor and creating mappings between
 schemas and marshalers.
 * @see com.cleancoder.args.Args
 */
public class ArgsData {
  public Map<Character, ArgumentMarshaler> marshalers;
  public Set<Character> argsFound;
  public ListIterator<String> currentArgument;
   
  /**
  * Splits the {@code schema} string by commas. 
  * @param schema It is defined in the main file. 
  <li>Example schema string {@code "f,s*,n#,a##,p[*]"}.</li>
  * @throws ArgsException ArgsException {@link com.cleancoder.args.ArgsException}
  */
  public void parseSchema(String schema) throws ArgsException {
    for (String element : schema.split(",")) {
      if (element.length() > 0) {
        parseSchemaElement(element.trim());
      }
    }
  }
  
  /**
   * Creates mapping between {@code elementTail} and relevant marshalers by comparing
   {@code elementTail} with predefined tail strings.
   <li> Tail strings: {@code "*", "#", "##", "[*], "&"}.</li>
   * Also, calls {@link com.cleancoder.args.ArgsData#validateSchemaElementId 
   validateSchemaElementId} method to validate schema element id.
   * @param element schema string as defined in main program.
   * @throws ArgsException Throws exception {@code INVALID_ARGUMENT_FORMAT} 
   if matching tail string is not found.
   * @see com.cleancoder.args.ArgumentMarshaler
   * @see com.cleancoder.args.BooleanArgumentMarshaler
   * @see com.cleancoder.args.StringArgumentMarshaler
   * @see com.cleancoder.args.IntegerArgumentMarshaler
   * @see com.cleancoder.args.DoubleArgumentMarshaler
   * @see com.cleancoder.args.StringArrayArgumentMarshaler
   * @see com.cleancoder.args.MapArgumentMarshaler
   */
  private void parseSchemaElement(String element) throws ArgsException {
    char elementId = element.charAt(0);
    String elementTail = element.substring(1);
    validateSchemaElementId(elementId);
    if (elementTail.length() == 0) {
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    } else if (elementTail.equals("*")) {
      marshalers.put(elementId, new StringArgumentMarshaler());
    } else if (elementTail.equals("#")) {
      marshalers.put(elementId, new IntegerArgumentMarshaler());
    } else if (elementTail.equals("##")) {
      marshalers.put(elementId, new DoubleArgumentMarshaler());
    } else if (elementTail.equals("[*]")) {
      marshalers.put(elementId, new StringArrayArgumentMarshaler());
    } else if (elementTail.equals("&")) {
      marshalers.put(elementId, new MapArgumentMarshaler());
    } else {
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
    }
  }

  /**
   * Checks if the first character of each substring in {@code schema} is a letter or not.
   * @param elementId first character of the schema substring. 
   <li>Example: in schema string {@code "f,s*,3#"}, substrings are <i>f, s*, 3# ...</i>,
   <i>f</i> and <i>s*</i> are acceptable but <i>3#</i> is not acceptable.</li>
   * @throws ArgsException {@code INVALID_ARGUMENT_NAME} if the first character is not a letter.
   */
  private void validateSchemaElementId(char elementId) throws ArgsException {
    if (!Character.isLetter(elementId)) {
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
    }
  }
}