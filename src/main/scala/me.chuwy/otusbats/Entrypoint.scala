package me.chuwy.otusbats

//import me.chuwy.otusbats.Monad.MonadSyntax

object Entrypoint extends App {
  val opt: Option[Int] = Some(10)
  assert(opt.map(_+1) == Some(11))
}
