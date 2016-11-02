package ai.frontier

import ai.Node

trait Frontier[A] {
  def isEmpty(): Boolean
  def next(): Node[A]
  def add(node: Node[A]): Unit
}
