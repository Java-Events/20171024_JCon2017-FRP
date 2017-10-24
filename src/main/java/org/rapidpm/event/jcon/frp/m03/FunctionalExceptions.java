package org.rapidpm.event.jcon.frp.m03;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.frp.model.Result;

/**
 *
 */
public class FunctionalExceptions {


  public static void main(String[] args) {


    // could crash ;-)
    try {
      final int parseInt = Integer.parseInt("uuppss");
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    final int upsA = Stream
        .of("1" , "2" , "3" , "ups" , "4")
        //will crash
        .mapToInt(Integer::parseInt)
        .sum();

    final int upsB = Stream
        .of("1" , "2" , "3" , "ups" , "4")
        //will crash
        .mapToInt(s -> {
          try {
            return Integer.parseInt(s);
          } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0; // works only for sum()
          }
        })
        .sum();

    final int upsC = Stream
        .of("1" , "2" , "3" , "ups" , "4")
        .map(s -> {
          try {
            return Optional.of(Integer.parseInt(s));
          } catch (NumberFormatException e) {
            e.printStackTrace();
            return Optional.<Integer>empty();
          }
        })
        .filter(Optional::isPresent)
        .mapToInt(Optional::get)
        .sum();

    final int upsD = Stream
        .of("1" , "2" , "3" , "ups" , "4")
        .map(s -> {
          try {
            return Optional.of(Integer.parseInt(s));
          } catch (NumberFormatException e) {
            e.printStackTrace();
            return Optional.<Integer>empty();
          }
        })
        .flatMap(Optional::stream)
        .mapToInt(i->i)
        .sum();

    final int upsE = Stream
        .of("1" , "2" , "3" , "ups" , "4")
        .map(new CheckedFunction<String, Integer>() {
          @Override
          public Integer applyWithException(String s) throws Exception {
            return Integer.parseInt(s);
          }
        })
        .flatMap(Result::stream)
        .mapToInt(i->i)
        .sum();

    final int upsF = Stream
        .of("1" , "2" , "3" , "ups" , "4")
        .map((CheckedFunction<String, Integer>) s -> Integer.parseInt(s))
        .flatMap(Result::stream)
        .mapToInt(i->i)
        .sum();

    final int upsG = Stream
        .of("1" , "2" , "3" , "ups" , "4")
        .map((CheckedFunction<String, Integer>) Integer::parseInt)
        .flatMap(Result::stream)
        .mapToInt(i->i)
        .sum();

    Function<String, Result<Integer>> checkedFuncA = new CheckedFunction<String, Integer>() {
      @Override
      public Integer applyWithException(String s) throws Exception {
        return Integer.parseInt(s);
      }
    };

    Function<String, Result<Integer>> checkedFuncB
        = (CheckedFunction<String, Integer>) Integer::parseInt;

  }

}
