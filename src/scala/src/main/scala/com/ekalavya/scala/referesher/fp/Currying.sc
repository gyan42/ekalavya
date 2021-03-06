import scala.annotation.tailrec

/**
 * Created by mdhandapani on 10/8/15.
 */

//Currying -> Chain of arguments, each argument creates a function


/*
Let's look at another example, currying, which converts a
 function of N arguments into a function of one argument that
 returns another  function as its result.
 Here again, there is only one implementation that  typechecks.
 */
//def curry[A,B,C](f: (A, B) => C): A => (B => C)
///* Implement uncurry, which reverses the
// transformation of curry. Note that since => associates to the right,
// A => (B => C) can be written as A => B => C.
//*/
//
//def uncurry[A,B,C](f: A => B => C): (A, B) => C

//(arg1: Type1) : Type2 => {}
//Type1 => Type 2

def mul(mul: Int)(value: Int) : Int = mul * value

val table2 = mul(2) _

table2(3)

def someComplexFunction(a: Int)(b: Int) = a + b

val partialSum = someComplexFunction(2)_
val sum = partialSum(3)

///Curring vs PartialFunction
//http://stackoverflow.com/questions/14309501/scala-currying-vs-partially-applied-functions

def modN1(n: Int, x: Int) = ((x%n) == 0)
modN1(5, _: Int) //Int => Boolean

def modNCurried(n: Int)(x: Int) = ((x % n) == 0)
// modNCurried(5) //error
val modNCurriedPartial = modNCurried(5)_ //Int => Boolean
modNCurriedPartial(5)

modN1 _ //(Int, Int) => Boolean
modNCurried _ //Int => (Int => Boolean)

//what if we want multiple arg function to be curried ?
(modN1 _).curried
modNCurried _

//Scala doesn't seem to be able to infer the type when partially applying "normal" functions:
//modN1(5, _) //error

//Whereas that information is available for functions written using multiple parameter list notation:
modNCurried(5) _


println("/////////////////////////////////////////////////////////////////////////")
def adder(m: Int)(n: Int)(p: Int) = m + n + p
// The above definition does not return a curried function yet
// (adder: (m: Int)(n: Int)(p: Int)Int)
// To obtain a curried version we still need to transform the method.
// into a function value.

val currAdder = adder _
val add2 = currAdder(2)
val add5 = add2(3)
add5(5)

println("/////////////////////////////////////////////////////////////////////////")
def cat(s1: String)(s2: String) = s1 + s2

def cat1(s1: String) = (s2: String) => s1 + s2

def cat2(s1: String, s2: String) = s1+ s2

val c = cat("Aja")_
c(" tandra")

//val c1 = cat2("Aja")
//c1( "tantra")


def multiplier(i: Int)(factor: Int) = i * factor
val byFive = multiplier(5) _
val byTen = multiplier(10) _

byFive(5)
byTen(5)


println("-------------------------------------------------------")

//Lets dtart with higher order function
def square(x: Int): Int = x * x

def sumX(f: Int => Int, a: Int, b: Int): Int = {
  @tailrec
  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else loop(a+1, acc + f(a))
  }
  loop(a, 0)
}

def sumOfSquares2(a: Int, b: Int) = sumX(square, a,b)

sumOfSquares2(1,5)

sumX(x => x * x, 3, 5)

def product(f: Int => Int)(a: Int, b: Int): Int = {
  @tailrec
 def loop(a: Int, acc: Int): Int = {
  if (a > b) acc
  else loop(a+1, acc * f(a))
 }
 loop(a, 1)
}

def factorial(a:Int, b:Int) = product(x => x)(a,b)

factorial(1,5)

//Lets generalize above two operation with currying
def mapReduce(f: Int => Int)
             (combine:(Int,Int) => Int)
             (zero: Int)
             (a: Int, b: Int) = {

  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else  loop(a+1, combine(acc, f(a)))
  }

  loop(a, zero)
}

def sumMR(a: Int, b:Int) = mapReduce(x => x)((x,y) => x + y)(0)(a,b)

sumMR(1,5)








