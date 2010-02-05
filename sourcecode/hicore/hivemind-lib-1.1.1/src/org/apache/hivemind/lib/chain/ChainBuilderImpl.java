 package org.apache.hivemind.lib.chain;
 
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodIterator;
 import org.apache.hivemind.service.MethodSignature;
 import org.apache.hivemind.util.ConstructorUtils;
 import org.apache.hivemind.util.Defense;
 
 public class ChainBuilderImpl
   implements ChainBuilder
 {
   private ClassResolver _classResolver;
   private ClassFactory _classFactory;
   private Map _implementations;
 
   public ChainBuilderImpl()
   {
     this._implementations = new HashMap();
   }
 
   public Object buildImplementation(Class commandInterface, List commands, String toString) {
     Defense.notNull(commandInterface, "commandInterface");
     Defense.notNull(commands, "commands");
     Defense.notNull(toString, "toString");
 
     Class instanceClass = findImplementationClass(commandInterface);
 
     return createInstance(instanceClass, commands, toString);
   }
 
   private synchronized Class findImplementationClass(Class commandInterface)
   {
     Class result = (Class)this._implementations.get(commandInterface);
 
     if (result == null)
     {
       result = constructImplementationClass(commandInterface);
 
       this._implementations.put(commandInterface, result);
     }
 
     return result;
   }
 
   private Class constructImplementationClass(Class commandInterface)
   {
     String name = ClassFabUtils.generateClassName(commandInterface);
 
     ClassFab cf = this._classFactory.newClass(name, Object.class);
 
     addInfrastructure(cf, commandInterface);
 
     addMethods(cf, commandInterface);
 
     return cf.createClass();
   }
 
   void addInfrastructure(ClassFab cf, Class commandInterface)
   {
     Class array = this._classResolver.findClass(commandInterface.getName() + "[]");
 
     cf.addInterface(commandInterface);
     cf.addField("_commands", array);
     cf.addField("_toString", String.class);
 
     BodyBuilder builder = new BodyBuilder();
 
     builder.begin();
     builder.addln("_commands = ({0}[]) $1.toArray(new {0}[0]);", commandInterface.getName());
     builder.addln("_toString = $2;");
     builder.end();
 
     cf.addConstructor(new Class[] { List.class, String.class }, null, builder.toString());
   }
 
   private Object createInstance(Class instanceClass, List commands, String toString)
   {
     return ConstructorUtils.invokeConstructor(instanceClass, new Object[] { commands, toString });
   }
 
   void addMethods(ClassFab cf, Class commandInterface)
   {
     MethodIterator mi = new MethodIterator(commandInterface);
 
     while (mi.hasNext())
     {
       MethodSignature sig = mi.next();
 
       addMethod(cf, commandInterface, sig);
     }
 
     if (!(mi.getToString()))
       addToString(cf);
   }
 
   void addMethod(ClassFab cf, Class commandInterface, MethodSignature sig)
   {
     Class returnType = sig.getReturnType();
 
     if (returnType.equals(Void.TYPE))
     {
       addVoidMethod(cf, commandInterface, sig);
       return;
     }
 
     String defaultValue = defaultForReturnType(returnType);
 
     BodyBuilder builder = new BodyBuilder();
     builder.begin();
 
     builder.addln("{0} result = {1};", ClassFabUtils.getJavaClassName(returnType), defaultValue);
 
     builder.addln("for (int i = 0; i < _commands.length; i++)");
 
     builder.begin();
     builder.addln("result = _commands[i].{0}($$);", sig.getName());
 
     builder.addln("if (result != {0}) break;", defaultValue);
 
     builder.end();
 
     builder.addln("return result;");
     builder.end();
 
     cf.addMethod(1, sig, builder.toString());
   }
 
   String defaultForReturnType(Class returnType)
   {
     if (!(returnType.isPrimitive())) {
       return "null";
     }
     if (returnType.equals(Boolean.TYPE)) {
       return "false";
     }
 
     return "0";
   }
 
   private void addVoidMethod(ClassFab cf, Class commandInterface, MethodSignature sig)
   {
     BodyBuilder builder = new BodyBuilder();
 
     builder.begin();
 
     builder.addln("for (int i = 0; i < _commands.length; i++)");
     builder.addln("_commands[i].{0}($$);", sig.getName());
 
     builder.end();
 
     cf.addMethod(1, sig, builder.toString());
   }
 
   void addToString(ClassFab cf)
   {
     MethodSignature sig = new MethodSignature(String.class, "toString", null, null);
 
     cf.addMethod(1, sig, "return _toString;");
   }
 
   public void setClassFactory(ClassFactory classFactory)
   {
     this._classFactory = classFactory;
   }
 
   public void setClassResolver(ClassResolver classResolver)
   {
     this._classResolver = classResolver;
   }
 }