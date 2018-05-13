package common

import scala.collection.mutable

/**
  * bidirectional map using mutable.TreeMap
  */
class Bimap[A,B](implicit val ordA: Ordering[A],implicit val ordB: Ordering[B]) {
  val map_a2b = new mutable.TreeMap[A,B]()
  val map_b2a = new mutable.TreeMap[B,A]()

  def getB(a:A):Option[B] = map_a2b.get(a)
  def getA(b:B):Option[A] = map_b2a.get(b)

  def set(a:A,b:B):Unit = {
    map_a2b += (a->b)
    map_b2a += (b->a)
  }
}
