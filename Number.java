package project3;

import java.util.NoSuchElementException;


/*
 * The Number class is used to represent positive integers. It provides several operations to
 * manipulate numbers including addition, multiplication and comparisons.
 * There is no limit to the number of digits in a Number Object
 * The numbers are represented in the form of a linked list. Each node in the list contains a single
 * digit.
 *
 * The ones digit is stored in the first node, the tens in the second and so on.
 *
 * @author Alessandro Landi
 */

public class Number<Integer> {

    private class Node{
        public int data;
        public Node next;

        public Node (int data){
            this.data = data;
            this.next = null;

        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Number(){
        head = null;
        tail = null;
        size = 0;
    }


    /*
     * Creates a Number object with value represented by String s.
     * The String should consist of decimal digits only.
     * @param String s a string representation of the number
     * @throws NullPointerException if s is null and  IllegalArgumentException
     * if s contains any illegal characters.
     */

    public Number(String s) throws IllegalArgumentException, NullPointerException {
        if(s.equals(null)){
            throw new NullPointerException("Invalid string: null");
        }
        for( int j = 0; j < s.length(); j++){
            if(Character.getNumericValue(s.charAt(j)) > 9 ){
                throw new IllegalArgumentException("InvalidArgumentException: string contains illegal characters");
            }
        }

        String input = s;
        head = null;
        tail = null;
        size = 0;

        int temp = 0;
        int pos = 0;
        for(int i = input.length()-1; i >= 0; i-- ) {
            temp = Character.getNumericValue(input.charAt(i));
            this.add(temp, pos);
            pos++;

        }


    }

    /*
     * Adds an element "it" at position pos, shifts elements starting at pos by
     * one position to the right (higher position values)
     * throws NoSuchElementException if pos < 0 or pos > size
     */

    public boolean add (int it, int pos) throws NullPointerException, NoSuchElementException{
        if(pos < 0 || pos > size){
            throw new NoSuchElementException("Invalid position");
        }

        int counter = 0;
        Node n = new Node(it);

        if (size == 0){
            head = n;
            tail = n;
        }
        else if (pos == 0){
            n.next = head;
            head = n;
        }
        else if (pos == size){
            tail.next = n;
            tail = tail.next;
        } else{
            Node current = head;
            while (counter < pos - 1){
                current = current.next;
                counter++;
            }
            n.next = current.next;
            current.next = n;
        }
        size++;
        return true;

    }

    /*
     * Remove and return a value at a specified position in the linked list.
     * Indexing of the values starts at 0.
     * @param pop position of the value to be removed, valid positions are 0 <= pos < size
     * @return the element that was removed
     * @throws NoSuchElementException when specified position is not valid
     */

    public int removeAt(int pos) throws NullPointerException, NoSuchElementException{
        if (pos < 0 || pos > size - 1){
            throw new NoSuchElementException("Invalid position");
        }

        if(pos == 0){
            int tmp = head.data;
            head = head.next;
            if(size == 1){
                tail = null;
            }
            size--;
            return tmp;
        }

        Node current = head;
        int stopPos = pos - 1 ;
        int counter = 0;

        while(counter < stopPos){
            current = current.next;
            counter++;
        }
        int tmp = current.next.data;
        current.next = current.next.next;

        if(pos == size - 1){
            tail = current;
        }
        size--;
        return tmp;

    }

    /*
     * Takes two Linked List Number<Integer> together and returns the sum of
     * the numerical values the Linked Lists represent as another linked list
     * @param Number o is the linked list Number<Integer> to be added.
     * @returns Number<Integer> Linked List of the sum of the two linked lists being added
     * @throws NullPointerException if param Number o is null.
     */

    public Number add(Number o) throws NullPointerException{
        if (o.equals(null)) {
            throw new NullPointerException("Invalid object other: null");
        }

        Number<Integer> other = (Number<Integer>) o;
        Number<Integer> result = new Number<Integer>();

        Node current1 = head;
        Node current2 = other.head;

        int num1, num2, sum = 0, digit = 0, pos = 0, temp = 0;

        while(current1 != null || current2 != null ){

            if(current1 == null){
                num1 = 0;
                num2 = current2.data;
            }
            else if(current2 == null){
                num2 = 0;
                num1 = current1.data;
            }else{
                num1 = current1.data;
                num2 = current2.data;
            }

            sum = num1 + num2 + temp;
            digit = sum % 10;
            temp = sum / 10;
            result.add(digit, pos);

            if(current1 != null){
                current1 = current1.next;
            }
            if(current2 != null){
                current2 = current2.next;
            }
            pos++;

        }
        if(temp > 0){
            result.add(temp, pos);
        }

        return result;

    }

    /*
     * Compares this Number with Number o for order. Returns a negative integer
     * if this Number's value is strictly smaller than the value of Number o.
     * Returns a postive integer if this Number's value is strictly greater than
     * the value of Number O. Returns zero if two values are the same.
     * @Parameters Number o the object to be compared to this object.
     * @returns a negative integer, zero, or a positive integer as this Number o is less than,
     * equal to, or greater than Number o.
     * @throws NullPointerException if Number o is null.
     */

    public int compareTo(Number o){
        if(this.size == o.size ){
            int count = 0;
            Node current1 = head;
            Node current2 = o.head;

            while(current1 != null){
                if(current1.data != current2.data){
                    return current1.data - current2.data;
                }
                current1 = current1.next;
                current2 = current2.next;
            }

        }
        if(this.size > o.size){
            return 1;
        }
        else if(this.size < o.size){
            return -1;
        }

        return 0;
    }

    /*
     * Determines if this Number is equal to Number o. Two Number objects are equal
     * if all of their digits are the same and in the same order, and if they
     * have the same number of digits.
     * @Parameters Number o the object to be compared to this object.
     * @returns true if two objects are equal, false otherwise.
     * @Overrides equals in class Object
     */

    public boolean equals(Number o){
        if(this == o){
            return true;
        }
        if(o == null){
            return false;
        }
        if(!(o instanceof Number<?>)){
            return false;
        }
        Number<?> other = (Number<?>) o;
        if(this.size != other.size ){
            return false;
        }

        int count = 0;
        Node current1 = head;
        Node current2 = o.head;

        while(current1 != null){
            if(current1.data != current2.data){
                return false;
            }
            current1 = current1.next;
            current2 = current2.next;
        }

        return true;
    }

    /*
     * Returns the number of digits in this object.
     * @returns the number of digits in this object.
     *
     */

    public int length(){
        return size;
    }

    /*
     * Takes two Linked List Number<Integer> together and returns the product of
     * the numerical values the Linked Lists represent as another linked list
     * @param Number o is the linked list Number<Integer> to be multiplied.
     * @returns Number<Integer> Linked List of the product of the two linked lists being multiplied
     * @throws NullPointerException if param Number o is null.
     */

    public Number multiply(Number o) throws NullPointerException{
        if(o.equals(null)){
            throw new NullPointerException("Invalid other: other is null");
        }
        Number<Integer> other = (Number<Integer>) o;
        Number<Integer> result = new Number<Integer>();

        Node current1 = head;
        Node current2 = other.head;
        int counter = 0;
        int num1, num2;


        while(current1 != null){
            if(current1 == null){
                num2 = current2.data;
                num1 = 0;
            }
            else if(current2 == null){
                num2 = 0;
                num1 = current1.data;

            } else{
                num1 = current1.data;
                num2 = current2.data;
            }

            Number<Integer> temp = other.multiplyByDigit(num1);

            //adds a 0 to the end of each temp to properly perform long hand multiplication
            //counter increases every time the for loop ends to add another 0
            for(int j = counter; j > 0; j--){
                temp.add(0, 0);

            }

            result = result.add(temp);



            if(current1 != null){
                current1 = current1.next;

            }
            if(current2 != null){
                current2 = current2.next;

            }

            counter++;
        }

        return result;
    }

    /*
     * Takes a Linked List Number<Integer>  and returns the product of the linked list
     * and a digit int.
     * @param int digit is the number being multiplied to the linked list Number
     * @returns Number<Integer> Linked List of the product of the linked list and the digit
     * @throws IllegalArgumentException if the digit has more than one digit or if it's negative.
     */

    public Number multiplyByDigit(int digit) throws IllegalArgumentException{
        if( digit > 9 || digit < 0){
            throw new IllegalArgumentException("Invalid digit: not a single digit or negative");
        }
        Number<Integer> result = new Number<Integer>();

        Node current = head;
        int counter = 0;
        int product = 0;
        int current_digit = 0;
        int pos = 0;

        //carry digit
        int temp = 0;

        while(current != null ){

            product = (current.data * digit) + temp;
            current_digit = product % 10;
            temp = product / 10;


            result.add(current_digit, pos);


            current = current.next;
            counter++;
            pos++;

        }
        if(temp > 0){
            result.add(temp, pos);
        }

        return result;
    }

    /*
     * toStringerHelper to reverse the linked list
     * @return reverses the linked list to properly output the correct
     * string representation of the linked list.
     */

    private String toStringHelper(Node n){
        if(n == null)
            return "";
        return toStringHelper(n.next) + n.data;
    }

    /*
     * Produces string representation of this list.
     * The string contains all elements stored in the list, one per line.
     * @return returns the string representation of this list
     * @Overrides toString in class Object
     */

    @Override
    public String toString () {

        return toStringHelper(head);
    }

}

