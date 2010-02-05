 package org.apache.hivemind.order;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.impl.ErrorLogImpl;
 import org.apache.hivemind.util.Defense;
 import org.apache.hivemind.util.StringUtils;
 
 public class Orderer
 {
   private final ErrorLog _errorLog;
   private final String _objectType;
   private List _orderingsList;
   private Map _orderingsMap;
   private Map _nodeMap;
   private Node _leader;
   private Node _trailer;
 
   public Orderer(ErrorHandler errorHandler, String objectType)
   {
     this(LogFactory.getLog(Orderer.class), errorHandler, objectType);
   }
 
   public Orderer(Log log, ErrorHandler errorHandler, String objectType)
   {
     this(new ErrorLogImpl(errorHandler, log), objectType);
   }
 
   public Orderer(ErrorLog errorLog, String objectType)
   {
     this._orderingsList = null;
 
     this._orderingsMap = null;
 
     this._nodeMap = null;
 
     Defense.notNull(errorLog, "errorLog");
     Defense.notNull(objectType, "objectType");
 
     this._errorLog = errorLog;
     this._objectType = objectType;
   }
 
   public void add(Object object, String name, String prereqs, String postreqs)
   {
     if (this._orderingsMap == null)
     {
       this._orderingsMap = new HashMap();
       this._orderingsList = new ArrayList();
     }
 
     ObjectOrdering o = getOrderable(name);
 
     if (o != null)
     {
       this._errorLog.error(OrdererMessages.duplicateName(this._objectType, name, object, o.getObject()), HiveMind.getLocation(object), null);
 
       return;
     }
 
     o = new ObjectOrdering(object, name, prereqs, postreqs);
 
     this._orderingsMap.put(name, o);
     this._orderingsList.add(o);
   }
 
   private ObjectOrdering getOrderable(String name)
   {
     return ((ObjectOrdering)this._orderingsMap.get(name));
   }
 
   public List getOrderedObjects()
   {
     if (this._orderingsMap == null) {
       return Collections.EMPTY_LIST;
     }
     try
     {
       this._nodeMap = new HashMap();
 
       initializeGraph();
 
       List localList = this._trailer.getOrder();
 
       return localList;
     }
     finally
     {
       this._nodeMap = null;
       this._leader = null;
       this._trailer = null;
     }
   }
 
   private void initializeGraph()
   {
     addNodes();
 
     if (this._leader == null) {
       this._leader = new Node(null, "*-leader-*");
     }
     if (this._trailer == null) {
       this._trailer = new Node(null, "*-trailer-*");
     }
     addDependencies();
   }
 
   private Node getNode(String name)
   {
     return ((Node)this._nodeMap.get(getOrderable(name)));
   }
 
   private void addNodes()
   {
     Iterator i = this._orderingsList.iterator();
 
     while (i.hasNext())
     {
       ObjectOrdering o = (ObjectOrdering)i.next();
 
       Node node = new Node(o.getObject(), o.getName());
 
       this._nodeMap.put(o, node);
 
       if ("*".equals(o.getPostreqs()))
       {
         if (this._leader == null)
           this._leader = node;
         else {
           this._errorLog.error(OrdererMessages.dupeLeader(this._objectType, o, getOrderable(this._leader.getName())), HiveMind.getLocation(o.getObject()), null);
         }
       }
 
       if ("*".equals(o.getPrereqs()))
       {
         if (this._trailer == null)
           this._trailer = node;
         else
           this._errorLog.error(OrdererMessages.dupeTrailer(this._objectType, o, getOrderable(this._trailer.getName())), HiveMind.getLocation(o.getObject()), null);
       }
     }
   }
 
   private void addDependencies()
   {
     Iterator i = this._orderingsList.iterator();
 
     while (i.hasNext())
     {
       ObjectOrdering o = (ObjectOrdering)i.next();
 
       addDependencies(o, getNode(o.getName()));
     }
   }
 
   private void addDependencies(ObjectOrdering orderable, Node node)
   {
     addPreRequisites(orderable, node);
     addPostRequisites(orderable, node);
     try
     {
       if (node != this._leader) {
         node.addDependency(this._leader);
       }
       if (node != this._trailer) {
         this._trailer.addDependency(node);
       }
 
     }
     catch (ApplicationRuntimeException ex)
     {
       String name = node.getName();
       ObjectOrdering trigger = getOrderable(name);
 
       this._errorLog.error(OrdererMessages.dependencyCycle(this._objectType, orderable, ex), HiveMind.getLocation(trigger.getObject()), ex);
     }
   }
 
   private void addPreRequisites(ObjectOrdering ordering, Node node)
   {
     String prereqs = ordering.getPrereqs();
 
     if ("*".equals(prereqs)) {
       return;
     }
     String[] names = StringUtils.split(prereqs);
 
     for (int i = 0; i < names.length; ++i)
     {
       String prename = names[i];
 
       Node prenode = getNode(prename);
 
       if (prenode == null)
       {
         this._errorLog.error(OrdererMessages.badDependency(this._objectType, prename, ordering), HiveMind.getLocation(ordering.getObject()), null);
       }
       else
       {
         try
         {
           node.addDependency(prenode);
         }
         catch (ApplicationRuntimeException ex)
         {
           this._errorLog.error(OrdererMessages.dependencyCycle(this._objectType, ordering, ex), HiveMind.getLocation(ordering.getObject()), ex);
         }
       }
     }
   }
 
   private void addPostRequisites(ObjectOrdering ordering, Node node)
   {
     String postreqs = ordering.getPostreqs();
 
     if ("*".equals(postreqs)) {
       return;
     }
     String[] names = StringUtils.split(postreqs);
 
     for (int i = 0; i < names.length; ++i)
     {
       String postname = names[i];
 
       Node postnode = getNode(postname);
 
       if (postnode == null)
       {
         this._errorLog.error(OrdererMessages.badDependency(this._objectType, postname, ordering), HiveMind.getLocation(ordering.getObject()), null);
       }
       else
       {
         try
         {
           postnode.addDependency(node);
         }
         catch (ApplicationRuntimeException ex)
         {
           this._errorLog.error(OrdererMessages.dependencyCycle(this._objectType, ordering, ex), HiveMind.getLocation(ordering.getObject()), ex);
         }
       }
     }
   }
 
   private static class Node
   {
     private Object _object;
     private String _name;
     private List _dependencies;
 
     public Node(Object o, String name)
     {
       this._object = o;
       this._name = name;
       this._dependencies = new ArrayList();
     }
 
     public String getName()
     {
       return this._name;
     }
 
     public void addDependency(Node n)
     {
       if (n.isReachable(this)) {
         throw new ApplicationRuntimeException("A cycle has been detected from the initial object [" + this._name + "]", HiveMind.getLocation(this._object), null);
       }
 
       this._dependencies.add(n);
     }
 
     private boolean isReachable(Node n)
     {
       boolean reachable = n == this;
       Iterator i = this._dependencies.iterator();
 
       while ((i.hasNext()) && (!(reachable)))
       {
         Node dep = (Node)i.next();
         reachable = (dep == n) ? true : dep.isReachable(n);
       }
 
       return reachable;
     }
 
     public List getOrder()
     {
       List result = new ArrayList();
       fillOrder(result);
 
       return result;
     }
 
     private void fillOrder(List result)
     {
       if (result.contains(this._object)) {
         return;
       }
       Iterator i = this._dependencies.iterator();
 
       while (i.hasNext())
       {
         Node dep = (Node)i.next();
         dep.fillOrder(result);
       }
 
       if (this._object != null)
         result.add(this._object);
     }
   }
 }