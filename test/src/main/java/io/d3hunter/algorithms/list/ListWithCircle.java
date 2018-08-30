package io.d3hunter.algorithms.list;

/**
 * for a linked list contains a circle, find length of the 'rope' before the circle, i.e
 * number of nodes before entering the circle
 * <pre>
 * a -> b -> c -> d
 *           ^    |
 *           |    v
 *           f <- e
 * </pre>
 * in the example above, length of rope is 2
 */

class Node {
    Node next;
}

public class ListWithCircle {
    public int lengthOfRope(Node head) {
        if (head == null) {
            return 0;
        }
        Node slow = head, fast = head;
        int steps = 0;
        while (fast != null && fast.next != null) {
            steps++;
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                int count = 0;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                    count++;
                }
                return count;
            }
        }
        while (slow != null) {
            steps++;
            slow = slow.next;
        }

        return steps;
    }

    public int lengthOfCircle(Node head) {
        if (head == null) {
            return 0;
        }
        Node slow = head, fast = head;
        int count = -1;
        boolean found = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            count++;
            if (slow == fast) {
                if (!found) {
                    found = true;
                    count = 0;
                } else {
                    return count;
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        Node[] nodes = new Node[6];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
            }
        }

        for (int i = 0; i < nodes.length + 1; i++) {
            if (i >= nodes.length) {
                nodes[nodes.length - 1].next = null;
            } else {
                nodes[nodes.length - 1].next = nodes[i];
            }
            final ListWithCircle listWithCircle = new ListWithCircle();
            System.out.println(listWithCircle.lengthOfRope(nodes[0]) + " " + listWithCircle.lengthOfCircle(nodes[0]));
        }
    }
}

