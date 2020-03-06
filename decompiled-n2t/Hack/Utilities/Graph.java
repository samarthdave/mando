package Hack.Utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Graph {
  private HashMap graph = new HashMap();
  
  private boolean hasCircle;
  
  public void addEdge(Object paramObject1, Object paramObject2) {
    checkExistence(paramObject1);
    checkExistence(paramObject2);
    Set set = (Set)this.graph.get(paramObject1);
    set.add(paramObject2);
  }
  
  private void checkExistence(Object paramObject) {
    if (!this.graph.keySet().contains(paramObject)) {
      HashSet hashSet = new HashSet();
      this.graph.put(paramObject, hashSet);
    } 
  }
  
  public boolean isEmpty() {
    return this.graph.keySet().isEmpty();
  }
  
  public boolean pathExists(Object paramObject1, Object paramObject2) {
    HashSet hashSet = new HashSet();
    return doPathExists(paramObject1, paramObject2, hashSet);
  }
  
  private boolean doPathExists(Object paramObject1, Object paramObject2, Set paramSet) {
    boolean bool = false;
    paramSet.add(paramObject1);
    Set set = (Set)this.graph.get(paramObject1);
    if (set != null) {
      Iterator iterator = set.iterator();
      while (iterator.hasNext() && !bool) {
        Object object = iterator.next();
        bool = object.equals(paramObject2);
        if (!bool && !paramSet.contains(object))
          bool = doPathExists(object, paramObject2, paramSet); 
      } 
    } 
    return bool;
  }
  
  public Object[] topologicalSort(Object paramObject) {
    this.hasCircle = false;
    HashSet hashSet1 = new HashSet();
    HashSet hashSet2 = new HashSet();
    Vector vector = new Vector();
    doTopologicalSort(paramObject, vector, hashSet1, hashSet2);
    Object[] arrayOfObject = new Object[vector.size()];
    for (byte b = 0; b < arrayOfObject.length; b++)
      arrayOfObject[b] = vector.elementAt(arrayOfObject.length - b - 1); 
    return arrayOfObject;
  }
  
  private void doTopologicalSort(Object paramObject, Vector paramVector, Set paramSet1, Set paramSet2) {
    paramSet1.add(paramObject);
    paramSet2.add(paramObject);
    Set set = (Set)this.graph.get(paramObject);
    if (set != null)
      for (Object object : set) {
        if (paramSet2.contains(object))
          this.hasCircle = true; 
        if (!paramSet1.contains(object))
          doTopologicalSort(object, paramVector, paramSet1, paramSet2); 
      }  
    paramSet2.remove(paramObject);
    paramVector.addElement(paramObject);
  }
  
  public boolean hasCircle() {
    return this.hasCircle;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Utilities/Graph.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */