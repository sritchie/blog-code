package io.samritchie

import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.Checkers
import org.scalacheck.Prop.forAll

class PermutationLaws extends AnyPropSpec with Checkers {
  property("ConstantStep's monoid works like the single-step version")(check {
    forAll { (rewards: List[Int]) =>
      rewards.sum == rewards.foldLeft(0)(_ + _)
    }
  })
}

class PermutationTest extends org.scalatest.funsuite.AnyFunSuite {
  import Permutation._

  val abc = Set("a", "b", "c")

  test("initial permutations code.") {
    assert(
      permutations(abc) == Set(
        List("a", "b", "c"),
        List("a", "c", "b"),
        List("b", "a", "c"),
        List("b", "c", "a"),
        List("c", "b", "a"),
        List("c", "a", "b")
      )
    )
  }

  test("all permutations methods work") {
    assert(permutations(abc) == permutations2(abc))
  }
}
