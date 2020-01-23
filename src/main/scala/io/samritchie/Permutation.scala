package io.samritchie

object Permutation {
  import scala.annotation.tailrec

  /**
    Okay, this is the basic way.
    */
  def permutations[A](items: Set[A]): Set[List[A]] =
    if (items.isEmpty) Set(List.empty)
    else {
      items.flatMap { x =>
        permutations(items - x).map(x :: _)
      }
    }

  /**
    This works in a similar way
    */
  def permutations2[A](input: Set[A]): Set[List[A]] = {

    def loop(items: List[A]): Set[List[A]] =
      items match {
        case Nil => Set(List.empty)
        case x :: xs =>
          loop(xs).flatMap { permutation =>
            (0 to permutation.size).map { i =>
              val (pre, post) = permutation.splitAt(i)
              pre ++ List(x) ++ post
            }
          }
      }
    loop(input.toList)
  }

  def kPermutations[A](items: Set[A], k: Int): Set[List[A]] = {
    assert(k >= 0 && k <= items.size)

    if (k == 0) Set(List.empty)
    else {
      items.flatMap { x =>
        kPermutations(items - x, k - 1).map(x :: _)
      }
    }
  }

  def combinations[A](items: Set[A], k: Int): Set[Set[A]] =
    kPermutations(items, k).map(_.toSet)

  def factorial(n: Int): Int =
    if (n == 0) 1 else n * factorial(n - 1)

  def numCombinations(n: Int, k: Int): Int =
    factorial(n) / (factorial(n - k) * factorial(k))

  // This gets ALL combinations...
  def powerset[A](items: Set[A]): Set[Set[A]] = {
    @tailrec
    def loop(remaining: List[A], ret: Set[Set[A]]): Set[Set[A]] =
      remaining match {
        case Nil     => ret
        case x :: xs => loop(xs, ret ++ ret.map(_ + x))
      }

    loop(items.toList, Set(Set.empty))
  }

  // nice short way that uses foldLeft to accumulate.
  def foldingPowerset[A](items: Set[A]): Set[Set[A]] =
    items.foldLeft(Set(Set.empty[A])) {
      case (acc, a) =>
        acc ++ acc.map(_ + a)
    }
}
//permutations(Set(1)) => List(List(1))
//permutations(Set(1,2)) => List(List(1,2), List(2,1))
//permutations(Set(1,2,3)) => List(List(1,2,3), List(1,3,2), List(2,1,3), List(2,3,1), List(3,1,2), List(3,2,1))

// 1,2 2,1     2,3 3,2
