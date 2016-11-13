package ai.search.frontier

import ai.search.Node

trait Frontier[A] {
  def add(node: Node[A]): Unit
  def isEmpty: Boolean
  def head: Node[A]
  def next: Node[A]
}
