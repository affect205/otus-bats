package me.chuwy.otusbats

object Entrypoint extends App {
  val opt: Option[Int] = Some(10)
  assert(opt.map(_+1) == Some(11))
}
