package org.rapidpm.event.jcon.frp.m01;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.rapidpm.frp.Transformations;

/**
 *
 */
public class FunctionDemos {

  static <A, B, R> Function<BiFunction<A, B, R>, Function<A, Function<B, R>>> curryBiFunction() {
    return (func) -> a -> b -> func.apply(a , b);
  }


  static <A, B, R> Function<BiFunction<A, B, R>, Function<A, Function<B, R>>> step01() {
    Function<
        BiFunction<A, B, R>,
        Function<A, Function<B, R>>> transform
        = new Function<BiFunction<A, B, R>, Function<A, Function<B, R>>>() {
      @Override
      public Function<A, Function<B, R>> apply(BiFunction<A, B, R> fIn) {
        return new Function<A, Function<B, R>>() {
          @Override
          public Function<B, R> apply(A a) {
            return (b) -> fIn.apply(a , b);
          }
        };
      }
    };
    return transform;
  }

  static <A, B, R> Function<BiFunction<A, B, R>, Function<A, Function<B, R>>> step02() {
    Function<
        BiFunction<A, B, R>,
        Function<A, Function<B, R>>> transform
        = new Function<BiFunction<A, B, R>, Function<A, Function<B, R>>>() {
      @Override
      public Function<A, Function<B, R>> apply(BiFunction<A, B, R> fIn) {
        return a -> (b) -> fIn.apply(a , b);
      }
    };
    return transform;
  }

  static <A, B, R> Function<BiFunction<A, B, R>, Function<A, Function<B, R>>> step03() {
    Function<
        BiFunction<A, B, R>,
        Function<A, Function<B, R>>> transform
        = fIn -> a -> b -> fIn.apply(a , b);
    return transform;
  }


  public static void main(String[] args) {

    final Function<String, Function<Integer, Integer>> f
        = FunctionDemos
        .<String, Integer, Integer>step01()
        .apply((a , b) -> Integer.valueOf(a) + b);


//    BiFunction<String, Integer, Integer> add = new BiFunction<String, Integer, Integer>() {
//      public Integer apply(String a , Integer b) {
//        return Integer.valueOf(a) + b ;
//      }
//    };


    final BiFunction<String, Integer, Integer> add = (a , b) -> Integer.valueOf(a) + b;



    final Function<String, Function<Integer, Integer>> addCurried = Transformations
        .<String, Integer, Integer>curryBiFunction()
        .apply(add);

    System.out.println(addCurried.apply("2").

        apply(1));

    System.out.println(
        Transformations
            .<String, Integer, Integer>unCurryBifunction()
            .apply(addCurried)
            .apply("3" , 2));
  }


}
