package org.rapidpm.event.jcon.frp.m02;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.rapidpm.frp.model.Result;

/**
 *
 */
public class OptionalOnSteroids {


  public static void main(String[] args) {

    final Optional<Object> empty = Optional.empty();
    final Optional<String> hello = Optional.of("Hello");
    final Optional<Object> nothing = Optional.ofNullable(null);

    Optional
        .of(1)
        .ifPresentOrElse(
            value -> System.out.println("integer = " + value) ,
            () -> System.out.println("nothing = " + nothing));

    hello.ifPresent(System.out::println);

    // since 8
    final boolean present = hello.isPresent();
    final String value = hello.get();

    //since 9
    final Stream<String> stream = hello.stream();

    final Optional<String> or = hello.or(() -> Optional.of("something else"));

    final String orElse = hello.orElse("something else");
    final String orElseThrow = hello.orElseThrow(() -> new RuntimeException("and go.."));

    final Optional<Integer> mappedA = hello.map(input -> Integer.valueOf(input));
    final Optional<Integer> mappedB = hello.map(Integer::valueOf);

    final Optional<String> filtered = hello.filter(s -> s.contains("H"));

    final Optional<Integer> flatMaped = hello
        .flatMap(s -> Optional.of(Integer.parseInt(s)));


    final Result<String> helloResult = Result.success("Hello");
    final Result<Integer> failed = Result.failure("ups..");


    helloResult.ifAbsent(() -> System.out.println("nothing here"));
    helloResult.ifPresent((v) -> System.out.println(v));

    helloResult.ifPresentOrElseAsync(
        success -> System.out.println("success = " + success) ,
        failure -> System.out.println("failure = " + failure)
    );

    helloResult.ifPresentOrElse(
        success -> System.out.println("success = " + success) ,
        failure -> System.out.println("failure = " + failure)
    );

    helloResult.isAbsent();
    helloResult.isPresent();

    final Result<String> next = helloResult
        .thenCombine("next" ,
                     (s1 , s2) -> Result.success(s1 + s2));

    final CompletableFuture<Result<String>> nextAsyncA
        = helloResult.thenCombineAsync("next" ,
                                       (s1 , s2) -> Result.success(s1 + s2));


    final CompletableFuture<Result<String>> cf = helloResult
        .thenCombineAsync((Supplier<String>)() -> "next" ,
                          (s1 , s2) -> Result.success(s1 + s2.get()));

    final CompletableFuture<Result<Integer>> cfFuncA = helloResult
        .thenCombineAsync((Function<String, Integer>)(s)-> Integer.parseInt(s) ,
                          (s , f) -> Result.success(f.apply(s)));


    final CompletableFuture<Result<Integer>> cfFuncB = helloResult
        .thenCombineAsync((Function<String, Integer>) Integer::parseInt ,
                          (s , f) -> Result.success(f.apply(s)));



    final Optional<String> s = helloResult.toOptional();
    final Result<String> result = Result.fromOptional(s);

    final Result<String> asFailure = helloResult.asFailure();


  }
}
