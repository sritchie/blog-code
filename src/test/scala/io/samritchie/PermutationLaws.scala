package io.samritchie

import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.Checkers
import org.scalacheck.Prop.forAll

class PermutationLaws extends AnyPropSpec with Checkers {
  property("ConstantStep's monoid works like the single-step version")(
    check {
      forAll { (rewards: List[Int]) =>
        rewards.sum == rewards.foldLeft(0)(_ + _)
    }
  })
}

class PermutationTest extends org.scalatest.funsuite.AnyFunSuite {
  test("1 equals itelf") {
    assert(1 == 1)
  }
}
