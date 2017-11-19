
/**
 * Represents a Priority Queue.  Supports the standard operations add and poll (removeMin), in addition
 * to the operation reduceKey, which adjusts a element's position in the heap due to a known value change. 
 * (reduceKey must be called explicitly in order for the effect to occur).<br><br>
 * 
 * Yes, the proper spelling of Q in this context is Queue. But this class is called PriorityQ anyway.
 * 
 * @author Luke Dramko
 */

public class PriorityQ<T extends Comparable<T>>
{
    private Comparable[] elements;
    private int endPosition = 0;

    /**
     * Constructor for objects of class PriorityQ
     */
    public PriorityQ() {
        elements = new Comparable[10];
    }
    
    /**
     * Creates a PriorityQ with the backing array of the specified initial capacity.
     */
    public PriorityQ(int size) {
        elements = new Comparable[size];
    }
    
    /**
     * Adds the element to the Priority Queue.
     */
    public void add(T addition) {
        if (endPosition == elements.length) {
            Comparable[] temp = new Comparable[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                temp[i] = (T)(elements[i]);
            }
            elements = temp;
        }
        
        elements[endPosition] = addition;
        bubbleUp(endPosition++);  //Postfix operator: line is executed, then variable is incrimented
    }
    
    /**
     * Removes the head of the PriorityQueue.  Returns null if the Queue is empty.
     */
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        if (endPosition == 1) {
            T temp = (T)elements[0];
            elements[0] = null;
            endPosition--;
            return temp;
        }
        T temp = (T)elements[0];
        swap(endPosition - 1, 0);
        elements[endPosition - 1] = null;
        endPosition--;
        //Restore the Heap Property
        bubbleDown(0);
        
        return temp;
    }
    
    /**
     * Uses bubble-up or bubble down to put a value who's key has changed in the proper place in the heap.
     */
    public void decreaseKey(T toChange) {
        int index = indexOf(toChange);
        if (index == -1) //The specified element is not in the array.           
            return;
        
        //It is not possible to both bubble up and down; at most one of these method calls will change position.
        bubbleUp(index);
        bubbleDown(index);
    }
    
    /**
     * Returns true if the heap is empty - that is, there's no more elements in it.
     */
    public boolean isEmpty() {
        return endPosition == 0;
    }
    
    /**
     * Returns the size of the priority queue; in other words, the number of elements in it.
     */
    public int size() {
        return endPosition;
    }
    
    /**
     * Linear-searches the heap for the index of a particular element, then returns that index.
     * 
     * Comparisions are done by memory location.
     */
    public int indexOf(T e) {
        for (int i = 0; i < elements.length; i++) {
            if (e.equals(elements[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /* Private helper methods listed here */
    
    /**
     * Performs the bubble-up operation necessary to maintain the heap property.
     */
    private void bubbleUp(int currentPosition) {
        int parent = (currentPosition - 1) / 2;
        if (((T)elements[parent]).compareTo((T)elements[currentPosition]) > 0) {
            swap(parent, currentPosition);
            bubbleUp(parent);
        }
    }
    
    /**
     * Performs the bubble-down operation necessary to maintain the heap property.
     */
    private void bubbleDown(int currentPosition) {
        int left = currentPosition * 2 + 1;
        int right = currentPosition * 2 + 2;
        //Check if the left or right is out of bounds of the array.  This is a special case
        //that is handeled seprately.  This happens when you're reaching the bottom row of the heap.
        if (left >= elements.length || elements[left] == null) {
            return;
        }
        //Now we know left is not null or out of bounds
        
        if (right >= elements.length || elements[right] == null) {
            if (((T)elements[left]).compareTo((T)elements[currentPosition]) < 0) {
                swap(left, currentPosition);
            }
            return;
        }
        
        //Standard heap bubble-down.
        if (((T)elements[left]).compareTo((T)elements[currentPosition]) < 0 || ((T)elements[right]).compareTo((T)elements[currentPosition]) < 0) {
            if (((T)elements[left]).compareTo((T)elements[right]) < 0) {
                swap(left, currentPosition);
                bubbleDown(left);
            } else {
                swap(right, currentPosition);
                bubbleDown(right);
            }
        }
    }
    
    /**
     * Swaps the elements at the two specified positions.
     */
    private void swap(int pos1, int pos2) {
        Comparable temp = elements[pos1];
        elements[pos1] = elements[pos2];
        elements[pos2] = temp;
    }
    
    /* End of private helper methods */
    
    /**
     * Returns a String representation of the PriorityQueue in the style of the toString method
     * of the Java collections framework.
     */
    public String toString() {
        if (endPosition == 0) {
            return "[]";
        }
        
        StringBuilder text = new StringBuilder("[");
        for (Comparable e : elements) {
            if (e != null) {
                text.append(e);
                text.append(", ");
            }
        }
        int commaCorrectionPosition = text.lastIndexOf(", ");
        try {
             text.replace(commaCorrectionPosition, commaCorrectionPosition + 2 , "");
        } catch (StringIndexOutOfBoundsException e) {}
        text.append("]");
        return text.toString();
    }
}
