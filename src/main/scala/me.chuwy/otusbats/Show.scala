package me.chuwy.otusbats

import java.util.Objects


trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances (`Int`, `String`, `Boolean`)
  implicit val intInstance: Show[Int] = (a: Int) => a.toString

  implicit val strInstance: Show[String] = (a: String) => a

  implicit val boolInstance: Show[Boolean] = (a: Boolean) => a.toString

  // 1.2 Instances with conditional implicit

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] = (a: List[A]) => a.map(ev.show).mkString

  implicit def setShow[A](implicit ev: Show[A]): Show[Set[A]] = (a: Set[A]) => a.map(ev.show).mkString

  // 2. Summoner (apply)
  def apply[A](implicit ev: Show[A]): Show[A] = ev

  // 3. Syntax extensions
  implicit class ShowOps[A](a: A) { // Implicit classes are required to convert class A to class Show[A] implicitly
    def show(implicit ev: Show[A]): String = ev.show(a)

    /** Transform list of `A` into `String` with custom separator, beginning and ending.
     *  For example: "[a, b, c]" from `List("a", "b", "c")`
     *
     *  @param separator. ',' in above example
     *  @param begin. '[' in above example
     *  @param end. ']' in above example
     */
    def mkString_(list: List[A], separator: String, begin: String, end: String)(implicit ev: Show[A]): String = {
      list.map(ev.show).mkString(begin, separator, end) // I'm not sure...
    }
  }

  // 4. Helper constructors
  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = (a: A) => Objects.toString(a, "")
  
  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = from[A](f)

  def from[A](f: A => String): Show[A] = (a: A) => f(a)
}
