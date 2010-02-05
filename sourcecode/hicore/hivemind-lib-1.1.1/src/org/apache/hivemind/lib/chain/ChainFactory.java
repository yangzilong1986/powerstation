 package org.apache.hivemind.lib.chain;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.order.Orderer;
 
 public class ChainFactory
   implements ServiceImplementationFactory
 {
   private ChainBuilder _chainBuilder;
 
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     List contributions = (List)factoryParameters.getFirstParameter();
 
     Orderer orderer = new Orderer(factoryParameters.getErrorLog(), "command");
 
     Iterator i = contributions.iterator();
     while (i.hasNext())
     {
       ChainContribution cc = (ChainContribution)i.next();
       orderer.add(cc, cc.getId(), cc.getAfter(), cc.getBefore());
     }
 
     List ordered = orderer.getOrderedObjects();
 
     List commands = new ArrayList(ordered.size());
 
     i = ordered.iterator();
     while (i.hasNext())
     {
       ChainContribution cc = (ChainContribution)i.next();
 
       commands.add(cc.getCommand());
     }
 
     String toString = "<ChainImplementation for " + factoryParameters.getServiceId() + "(" + factoryParameters.getServiceInterface().getName() + ")>";
 
     return this._chainBuilder.buildImplementation(factoryParameters.getServiceInterface(), commands, toString);
   }
 
   public void setChainBuilder(ChainBuilder chainBuilder)
   {
     this._chainBuilder = chainBuilder;
   }
 }