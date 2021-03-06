/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js API               **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2013, LAMP/EPFL        **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \    http://scala-lang.org/     **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */



package scala.scalajs

/** Types, methods and values for interoperability with JavaScript libraries.
 *
 *  This package is only relevant to the Scala.js compiler, and should not be
 *  referenced by any project compiled to the JVM.
 *
 *  == Guide ==
 *
 *  General documentation on Scala.js is available at
 *  [[http://www.scala-js.org/doc/]].
 *
 *  == Overview ==
 *
 *  The trait [[js.Any]] is the super type of all JavaScript values.
 *
 *  All class, trait and object definitions that inherit, directly or
 *  indirectly, from [[js.Any]] do not have actual implementations in Scala.
 *  They are only the manifestation of static types representing libraries
 *  written directly in JavaScript. It is not possible to implement yourself
 *  a subclass of [[js.Any]]: all the method definitions will be ignored when
 *  compiling to JavaScript.
 *
 *  Implicit conversions to and from standard Scala types to their equivalent
 *  in JavaScript are provided. For example, from Scala functions to JavaScript
 *  functions and back.
 *
 *  The most important subclasses of [[js.Any]] are:
 *  - [[js.Dynamic]], a dynamically typed interface to JavaScript APIs
 *  - [[js.Object]], the superclass of all statically typed JavaScript classes,
 *    which has subclasses for all the classes standardized in ECMAScript 5.1,
 *    among which:
 *    - [[js.Array]]
 *    - [[js.Function]] (and subtraits with specific number of parameters)
 *    - [[js.ThisFunction]] and its subtraits for functions that take the
 *      JavaScript `this` as an explicit parameters
 *    - [[js.Dictionary]] to access the properties of an object in a
 *      dictionary-like way
 *    - [[js.Date]]
 *    - [[js.RegExp]]
 *
 *  The trait [[js.Dynamic]] is a special subtrait of [[js.Any]]. It can
 *  represent any JavaScript value in a dynamically-typed way. It is possible
 *  to call any method and read and write any field of a value of type
 *  [[js.Dynamic]].
 *
 *  There are no explicit definitions for JavaScript primitive types, as one
 *  could expect, because the corresponding Scala types stand in their stead:
 *  - [[Boolean]] is a primitive JavaScript boolean
 *  - [[Double]] is a primitive JavaScript number
 *  - [[String]] is a primitive JavaScript string
 *  - [[Unit]] is the type of the JavaScript undefined value
 *  - [[Null]] is the type of the JavaScript null value
 *
 *  [[js.UndefOr]] gives a [[scala.Option]]-like interface where the JavaScript
 *  value `undefined` takes the role of `None`.
 */
package object js {
  /** The undefined value. */
  @inline def undefined: js.UndefOr[Nothing] =
    ().asInstanceOf[js.UndefOr[Nothing]]

  /** Tests whether the given value is undefined. */
  @inline def isUndefined(v: scala.Any): Boolean =
    v.asInstanceOf[scala.AnyRef] eq undefined

  /** Returns the type of `x` as identified by `typeof x` in JavaScript. */
  def typeOf(x: Any): String = sys.error("stub")

  /** Invokes any available debugging functionality.
   *  If no debugging functionality is available, this statement has no effect.
   *
   *  MDN
   *
   *  Browser support:
   *  - Has no effect in Rhino nor, apparently, in Firefox
   *  - In Chrome, it has no effect unless the developer tools are opened
   *    beforehand.
   */
  def debugger(): Unit = sys.error("stub")

  /** Evaluates JavaScript code and returns the result. */
  @inline def eval(x: String): Any =
    js.Dynamic.global.eval(x)

  /** Denotes a method body as native JavaScript. For use in facade types:
   *
   *  {{{
   *  class MyJSClass extends js.Object {
   *    def myMethod(x: String): Int = js.native
   *  }
   *  }}}
   */
  def native: Nothing = sys.error("A method defined in a JavaScript raw " +
      "type of a Scala.js library has been called. This is most likely " +
      "because you tried to run Scala.js binaries on the JVM. Make sure you " +
      "are using the JVM version of the libraries.")
}
