package edu.sdsu.cs.datastructures;
import java.util.Collections;
import java.util.LinkedList;

public class UnbalancedMap <K extends Comparable<K>, V> implements IMap<K,V>{
    private Node root;
    private LinkedList <V> valuesList;
    private LinkedList <K> keys;
    private LinkedList <K> keysTwo;
    private int currentSize;

    public UnbalancedMap()
    {
        root=null;
    }

    public UnbalancedMap(IMap<K,V> source)
    {
        root = null;
        for(K key: source.keyset())
        {
            this.add(key,source.getValue(key));
        }
    }

    private class Node
    {
        public K key;
        public V value;
        Node left,right,parent;

        private Node(K k, V v)
        {
            this.key=k;
            this.value=v;
        }
        public Node()
        {
            key=null;
            value=null;
        }


        private V getValue(Node current, K key)
        {
            if(current==null)
            {
                return null;
            }
            int result= current.key.compareTo(key);
            if(result == 0){
                return current.value;
            }
            if (result > 0) {
                    return getValue(current.left, key);

            }
            else{
                    return getValue(current.right, key);
                }
        }

        private boolean contains(Node current, K key)
        {
            if(current==null)
            {
                return false;
            }
            int result= current.key.compareTo(key);
            if(result == 0){
                return true;
            }
            if (result > 0) {
                return contains(current.left, key);

            }
            else{
                return contains(current.right, key);
            }
        }




        private boolean add(K k,V v)
        {
            int result= key.compareTo(k);
            if(result==0){
                return false;
            }
            else if(result<0){
                if(right==null){
                    right=new Node(k,v);
                    currentSize++;
                    return true;
                }
                else {
                    return right.add(k, v);
                }
            }
            else if(result>0){
                if(left==null){
                    left=new Node(k,v);
                    currentSize++;
                    return true;
                }
                else {
                    return left.add(k, v);
                }
            }
            return false;
        }

        public Node smallest(Node n)
        {
            Node curr = n;
            while(curr.left != null){
                curr = curr.left;
            }
            return curr;
        }

        private Node inOrderSuccessor(Node node) {

            if (node.right != null) {
                return smallest(node.right);
            }

            Node p = node.parent;
            while (p != null && node == p.right) {
                node = p;
                p = p.parent;
            }
            return p;
        }

    }

    public boolean contains(K key)
    {
        return contains(root,key);
    }

    private boolean contains(Node current, K key)
    {
        if(current == null)
        {
            return false;
        }
        int result = current.key.compareTo(key);
        while(result != 0) {

            if (result > 0) {
                current = current.left;
                if(current == null)
                {
                    return false;
                }
                else{
                    result = current.key.compareTo(key);
                }
            }
            else{
                current = current.right;
                if(current == null)
                {
                    return false;
                }
                else{
                    result = current.key.compareTo(key);
                }
            }
        }
        return true;
    }

    public boolean add(K key, V value) {
        if(root==null){
            root=new Node(key,value);
            currentSize=1;
            return true;
        }
        else return root.add(key, value);
    }

    private int childCount(Node n){
        int count = 0;
        if(n.left != null){
            count++;
        }
        if(n.right != null){
            count++;
        }
        return count;
    }

    public V delete(K key)
    {
        V v;
        if(!contains(key)){
            return null;
        }

        if(root==null)
        {
            currentSize=0;
            return null;
        }

        //delete root with 0 children
        if((root.left==null)&&(root.right==null))
        {
            V val=root.value;
            root=null;
            currentSize=0;
            return val;
        }

        Node parent = findParent(key);

        //delete root with 1 child and two children
        if(root.key.compareTo(key)==0 && parent==null){
            if(childCount(root) == 1){
                if(root.left != null){
                    Node left = root.left;
                    v = root.value;
                    root.left = null;
                    root = left;
                    return v;
                }
            }
            currentSize--;
            V val;
            Node successor;
            Node toDelete = root;
            val = toDelete.value;
            successor = root.inOrderSuccessor(toDelete); // = 10
            toDelete.value = successor.value;
            toDelete.key= successor.key;
            return val;
        }


        //delete node w 0 children
        if(parent.right != null) {
            if (parent.right.right == null && parent.right.left == null) {
                if (parent.right.key.compareTo(key) == 0) {
                    v = parent.right.value;
                    parent.right = null;
                    currentSize--;
                    return v;
                }
            }
        }

        if(parent.left != null) {
            if (parent.left.left == null && parent.left.right == null) {
                if (parent.left != null && parent.left.key.compareTo(key) == 0) {
                    v = parent.left.value;
                    parent.left = null;
                    currentSize--;
                    return v;
                }
            }
        }

        //delete with 1 child
        if(parent.right != null) {
            if (parent.right.key.compareTo(key) == 0) {
                Node toDelete = parent.right;
                v = toDelete.value;
                if (toDelete.left == null && toDelete.right != null) {
                    parent.right = toDelete.right;
                    currentSize--;
                    return v;
                }
                if (toDelete.right == null && toDelete.left != null) {
                    parent.right = toDelete.left;
                    currentSize--;
                    return v;
                }
            }
        }

        if (parent.left.key.compareTo(key) == 0) {
                Node toDelete = parent.left;
                v = toDelete.value;
                if (toDelete.left == null && toDelete.right != null) {
                    parent.left = toDelete.right;
                    currentSize--;
                    return v;
                }
                if (toDelete.right == null && toDelete.left != null) {
                    parent.left = toDelete.left;
                    currentSize--;
                    return v;
                }
        }

        //delete with 2 children
        if(childCount(parent.left) == 2 || childCount(parent.right) == 2){
            currentSize--;
            Node successor;
            Node toDelete;
            V val;

            if ((parent.left.key.compareTo(key) == 0)){
                toDelete = parent.left;
                val= toDelete.value;
                successor = root.inOrderSuccessor(toDelete); // = 10
                toDelete.value = successor.value;
                toDelete.key= successor.key;
                return val;
            }

            if (parent.right.key.compareTo(key) == 0){
                toDelete = parent.right;
                val= toDelete.value;
                successor = root.inOrderSuccessor(toDelete); // = 10
                toDelete.value = successor.value;
                toDelete.key= successor.key;
                return val;
            }
        }
        return null;
    }

    private boolean hasChild(Node parent){
        if(parent == null){
            return false;
        }
        if(parent.left == null && parent.right == null){
            return false;
        }
        return true;
    }

    public Node findParent(K key){
        Node current = root;
        int result;
        if(current == null){
            return null;
        }
        if(current.key.compareTo(key) == 0){
            return null;
        }
        while(hasChild(current)){
            result = current.key.compareTo(key);
            if(result > 0){
                if(current.left.key.compareTo(key) == 0){
                    return current;
                }
                else{
                    current = current.left;
                }
            }
            else{
                if(current.right.key.compareTo(key) == 0){
                    return current;
                }
                else{
                    current = current.right;
                }
            }
        }
        return null;
    }


    public V getValue(K key)
    {
        return getValue(key);
    }



    private V getValue(K key)
    {
        Node current = root;
        if(current==null)
        {
            return null;
        }
        int result= current.key.compareTo(key);

        while(result != 0)
        {
            if (result > 0) {
                current = current.left;
                if(current == null)
                {
                    return null;
                }
                else{
                    result = current.key.compareTo(key);
                }
            }
            else{
                current = current.right;
                if(current == null)
                {
                    return null;
                }
                else{
                    result = current.key.compareTo(key);
                }
            }
        }
        return current.value;

    }




    public K getKey(V value)
    {
        if(root==null)
        {
            return null;
        }
        else return getKey(root,value);
    }

    private K getKey(Node current,V value)
    {
        if(current.value.equals(value)){
            return current.key;
        }
        if(current.left != null){
            if( getKey(current.left,value) != null)
                return getKey(current.left,value);
        }
        if(current.right!=null) {
            return getKey(current.right, value);
        }
        return null;
    }

    public Iterable<K> getKeys(V value)
    {
        keysTwo= new LinkedList();
        if(root==null)
        {
            return null;
        }
        else return getKeys(root,value);

    }

    private Iterable<K> getKeys(Node current,V value)
    {
        if(current==null){
            return null;
        }

        getKeys(current.left,value);
        if(current.value.equals(value)){
            keysTwo.add(current.key);
        }
        getKeys(current.right,value);

        return keysTwo;
    }

    public int size()
    {
        return currentSize;
    }

    public boolean isEmpty()
    {
        return size()==0;
    }

    public void clear()
    {
        currentSize=0;
        root=null;
    }

    public Iterable<K> keyset()
    {
        keys = new LinkedList<>();
        iterateNodesKeys(root);
        Collections.sort(keys);
        return keys;
    }

    private void iterateNodesKeys(Node node)
    {
        if(node==null){
            return;
        }
        iterateNodesKeys(node.left);
        if(!keys.contains(node.key))
            keys.add(node.key);
        iterateNodesKeys(node.right);
        return;
    }

    public Iterable<V> values()
    {
        valuesList= new LinkedList();
        for(K key: keyset())
        {
            valuesList.add(getValue(key));
        }
        return valuesList;
    }

}
