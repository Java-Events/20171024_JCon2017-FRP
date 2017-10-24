package org.rapidpm.event.jcon.frp.m04;

import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;

import org.rapidpm.frp.matcher.Case;
import org.rapidpm.frp.model.Result;

/**
 *
 */
public class CaseDemos {

  public static void main(String[] args) {


    String value = "";

    switch (value) {
      case "A":
        break;
      case "B":
        break;
      case "C":
        break;
      default:
        break;
    }

    if (value.equals("A")) {

    } else if (value.equals("B")) {

    } else if (value.equals("C")) {

    } else {
      //default
    }

    String r = value.equals("A")
               ? "x" : value.equals("B")
                       ? "x" : value.equals("C")
                               ? "x" : "default";

    Result<String> result = Case
        .match(
            Case.matchCase(() -> Result.success("default value")) ,
            Case.matchCase(() -> value.equals("A") , () -> Result.success("x")) ,
            Case.matchCase(() -> value.equals("B") , () -> Result.success("x")) ,
            Case.matchCase(() -> value.equals("C") , () -> Result.success("x")) ,
            Case.matchCase(() -> value.equals("X") , () -> Result.failure("error message"))
        );

    result.ifPresentOrElseAsync(
        success -> { /* something usefull */ } ,
        failed -> { /* something usefull */}
    );


    match(
        matchCase(() -> success("default value")) ,
        matchCase(() -> value.equals("A") , () -> success("x")) ,
        matchCase(() -> value.equals("B") , () -> success("x")) ,
        matchCase(() -> value.equals("C") , () -> success("x")) ,
        matchCase(() -> value.equals("X") , () -> failure("error message")))
        .ifPresentOrElseAsync(
            success -> { /* something usefull */ } ,
            failed -> { /* something usefull */}
        );


  }
}
