package com.innerproduct.ee.effects

import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit

object Timing extends App {
  val clock: MyIO[Long] = MyIO( () => System.currentTimeMillis())

  def time[A](action: MyIO[A]): MyIO[(FiniteDuration, A)] =
    for {
      start <- clock
      act <- action
      finish <- clock
    } yield(FiniteDuration(finish - start, TimeUnit.MILLISECONDS), act)

  val timedHello = Timing.time(MyIO.putStr("hello"))

  timedHello.unsafeRun() match {
    case (duration, _) => println(s"'hello' took $duration")
  }
}
