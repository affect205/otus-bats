package me.chuwy.otusbats

import scala.util.Try

trait Monad[F[_]] extends Functor[F] { self =>
  def flatMap[A, B](a: F[A])(f: A => F[B]): F[B]
  def point[A](a: A): F[A]
  def flatten[A](ffa: F[F[A]]): F[A] = flatMap(ffa)(fa => fa)
}

object Monad {

  implicit val optInstance: Monad[Option] = new Monad[Option] {
    override def map[A, B](a: Option[A])(f: A => B): Option[B] = a.map(f)

    override def flatMap[A, B](a: Option[A])(f: A => Option[B]): Option[B] = a.flatMap(f)
    override def point[A](a: A): Option[A] = Some(a)
  }

  implicit val listInstance: Monad[List] = new Monad[List] {
    override def map[A, B](a: List[A])(f: A => B): List[B] = a.map(f)
    override def flatMap[A, B](a: List[A])(f: A => List[B]): List[B] = a.flatMap(f)
    override def point[A](a: A): List[A] = List(a)
  }

  implicit val tryInstance: Monad[Try] = new Monad[Try] {
    override def map[A, B](a: Try[A])(f: A => B): Try[B] = a.map(f)
    override def flatMap[A, B](a: Try[A])(f: A => Try[B]): Try[B] = a.flatMap(f)
    override def point[A](a: A): Try[A] = Try(a)
  }

  def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev

  implicit class MonadSyntax[F[_], A](a: F[A]) {
    def map[B](f: A => B)(implicit monad: Monad[F]): F[B] = monad.map(a)(f)
    def flatMap[B](f: A => F[B])(implicit monad: Monad[F]): F[B] = monad.flatMap(a)(f)
    def flatten(ffa: F[F[A]])(implicit monad: Monad[F]): F[A] = monad.flatten(ffa)
  }
}
