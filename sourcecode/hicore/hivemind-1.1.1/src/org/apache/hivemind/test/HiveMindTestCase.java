 package org.apache.hivemind.test;
 
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Locale;
 import junit.framework.Assert;
 import junit.framework.AssertionFailedError;
 import junit.framework.TestCase;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.ModuleDescriptorProvider;
 import org.apache.hivemind.Registry;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.impl.DefaultClassResolver;
 import org.apache.hivemind.impl.LocationImpl;
 import org.apache.hivemind.impl.RegistryBuilder;
 import org.apache.hivemind.impl.XmlModuleDescriptorProvider;
 import org.apache.hivemind.internal.ser.ServiceSerializationHelper;
 import org.apache.hivemind.util.ClasspathResource;
 import org.apache.hivemind.util.PropertyUtils;
 import org.apache.hivemind.util.URLResource;
 import org.apache.log4j.AppenderSkeleton;
 import org.apache.log4j.Category;
 import org.apache.log4j.Level;
 import org.apache.log4j.LogManager;
 import org.apache.log4j.Logger;
 import org.apache.log4j.spi.LoggingEvent;
 import org.apache.oro.text.regex.Pattern;
 import org.apache.oro.text.regex.Perl5Compiler;
 import org.apache.oro.text.regex.Perl5Matcher;
 import org.easymock.MockControl;
 import org.easymock.classextension.MockClassControl;
 
 public abstract class HiveMindTestCase extends TestCase
 {
   private ClassResolver _classResolver;
   protected String _interceptedLoggerName;
   protected StoreAppender _appender;
   private static Perl5Compiler _compiler;
   private static Perl5Matcher _matcher;
   private List _controls;
   private static final MockControlFactory _interfaceMockControlFactory = new InterfaceMockControlFactory(null);
   private static MockControlFactory _classMockControlFactory;
   private int _line;
 
   public HiveMindTestCase()
   {
     this._controls = new ArrayList();
 
     this._line = 1;
   }
 
   protected Resource getResource(String file)
   {
     URL url = super.getClass().getResource(file);
 
     if (url == null) {
       throw new NullPointerException("No resource named '" + file + "'.");
     }
     return new URLResource(url);
   }
 
   protected static void assertListsEqual(Object[] expected, List actual)
   {
     assertListsEqual(expected, actual.toArray());
   }
 
   protected static void assertListsEqual(Object[] expected, Object[] actual)
   {
     Assert.assertNotNull(actual);
 
     int min = Math.min(expected.length, actual.length);
 
     for (int i = 0; i < min; ++i) {
       Assert.assertEquals("list[" + i + "]", expected[i], actual[i]);
     }
     Assert.assertEquals("list length", expected.length, actual.length);
   }
 
   protected static void unreachable()
   {
     throw new AssertionFailedError("This code should be unreachable.");
   }
 
   protected void interceptLogging(String loggerName)
   {
     Logger logger = LogManager.getLogger(loggerName);
 
     logger.removeAllAppenders();
 
     this._interceptedLoggerName = loggerName;
     this._appender = new StoreAppender();
     this._appender.activateOptions();
 
     logger.setLevel(Level.DEBUG);
     logger.setAdditivity(false);
     logger.addAppender(this._appender);
   }
 
   protected List getInterceptedLogEvents()
   {
     return this._appender.getEvents();
   }
 
   protected void tearDown()
     throws Exception
   {
     super.tearDown();
 
     if (this._appender != null)
     {
       this._appender = null;
 
       Logger logger = LogManager.getLogger(this._interceptedLoggerName);
       logger.setLevel(null);
       logger.setAdditivity(true);
       logger.removeAllAppenders();
     }
 
     PropertyUtils.clearCache();
 
     ServiceSerializationHelper.setServiceSerializationSupport(null);
   }
 
   protected void assertExceptionSubstring(Throwable ex, String substring)
   {
     String message = ex.getMessage();
     Assert.assertNotNull(message);
 
     int pos = message.indexOf(substring);
 
     if (pos < 0)
       throw new AssertionFailedError("Exception message (" + message + ") does not contain [" + substring + "]");
   }
 
   protected void assertExceptionRegexp(Throwable ex, String pattern)
     throws Exception
   {
     String message = ex.getMessage();
     Assert.assertNotNull(message);
 
     setupMatcher();
 
     Pattern compiled = _compiler.compile(pattern);
 
     if (_matcher.contains(message, compiled)) {
       return;
     }
     throw new AssertionFailedError("Exception message (" + message + ") does not contain regular expression [" + pattern + "].");
   }
 
   protected void assertRegexp(String pattern, String actual)
     throws Exception
   {
     setupMatcher();
 
     Pattern compiled = _compiler.compile(pattern);
 
     if (_matcher.contains(actual, compiled)) {
       return;
     }
     throw new AssertionFailedError("\"" + actual + "\" does not contain regular expression[" + pattern + "].");
   }
 
   protected Throwable findNestedException(ApplicationRuntimeException ex)
   {
     Throwable cause = ex.getRootCause();
 
     if ((cause == null) || (cause == ex)) {
       return ex;
     }
     if (cause instanceof ApplicationRuntimeException) {
       return findNestedException((ApplicationRuntimeException)cause);
     }
     return cause;
   }
 
   private void assertLoggedMessage(String message, List events, int index)
   {
     LoggingEvent e = (LoggingEvent)events.get(index);
 
     Assert.assertEquals("Message", message, e.getMessage());
   }
 
   protected void assertLoggedMessages(String[] messages)
   {
     List events = getInterceptedLogEvents();
 
     for (int i = 0; i < messages.length; ++i)
     {
       assertLoggedMessage(messages[i], events, i);
     }
   }
 
   protected void assertLoggedMessage(String message)
   {
     assertLoggedMessage(message, getInterceptedLogEvents());
   }
 
   protected void assertLoggedMessage(String message, List events)
   {
     int count = events.size();
 
     for (int i = 0; i < count; ++i)
     {
       LoggingEvent e = (LoggingEvent)events.get(i);
 
       String eventMessage = String.valueOf(e.getMessage());
 
       if (eventMessage.indexOf(message) >= 0) {
         return;
       }
     }
     throw new AssertionFailedError("Could not find logged message: " + message);
   }
 
   protected void assertLoggedMessagePattern(String pattern) throws Exception
   {
     assertLoggedMessagePattern(pattern, getInterceptedLogEvents());
   }
 
   protected void assertLoggedMessagePattern(String pattern, List events) throws Exception
   {
     setupMatcher();
 
     Pattern compiled = null;
 
     int count = events.size();
 
     for (int i = 0; i < count; ++i)
     {
       LoggingEvent e = (LoggingEvent)events.get(i);
 
       String eventMessage = e.getMessage().toString();
 
       if (compiled == null) {
         compiled = _compiler.compile(pattern);
       }
       if (_matcher.contains(eventMessage, compiled)) {
         return;
       }
     }
 
     throw new AssertionFailedError("Could not find logged message with pattern: " + pattern);
   }
 
   private void setupMatcher()
   {
     if (_compiler == null) {
       _compiler = new Perl5Compiler();
     }
     if (_matcher == null)
       _matcher = new Perl5Matcher();
   }
 
   protected Registry buildFrameworkRegistry(String file)
     throws Exception
   {
     return buildFrameworkRegistry(new String[] { file });
   }
 
   protected Registry buildFrameworkRegistry(String[] files)
     throws Exception
   {
     ClassResolver resolver = getClassResolver();
 
     List descriptorResources = new ArrayList();
     for (int i = 0; i < files.length; ++i)
     {
       Resource resource = getResource(files[i]);
 
       descriptorResources.add(resource);
     }
 
     ModuleDescriptorProvider provider = new XmlModuleDescriptorProvider(resolver, descriptorResources);
 
     return buildFrameworkRegistry(provider);
   }
 
   protected Registry buildFrameworkRegistry(ModuleDescriptorProvider customProvider)
   {
     ClassResolver resolver = getClassResolver();
 
     RegistryBuilder builder = new RegistryBuilder();
 
     builder.addModuleDescriptorProvider(new XmlModuleDescriptorProvider(resolver));
     builder.addModuleDescriptorProvider(customProvider);
 
     return builder.constructRegistry(Locale.getDefault());
   }
 
   protected Registry buildMinimalRegistry(Resource l)
     throws Exception
   {
     RegistryBuilder builder = new RegistryBuilder();
 
     return builder.constructRegistry(Locale.getDefault());
   }
 
   protected MockControl newControl(Class mockClass)
   {
     MockControlFactory factory = (mockClass.isInterface()) ? _interfaceMockControlFactory : _classMockControlFactory;
 
     MockControl result = factory.newControl(mockClass);
 
     addControl(result);
 
     return result;
   }
 
   protected MockControl getControl(Object mock)
   {
     Iterator i = this._controls.iterator();
     while (i.hasNext())
     {
       MockControl control = (MockControl)i.next();
 
       if (control.getMock() == mock) {
         return control;
       }
     }
     throw new IllegalArgumentException(mock + " is not a mock object controlled by any registered MockControl instance.");
   }
 
   protected void setThrowable(Object mock, Throwable t)
   {
     getControl(mock).setThrowable(t);
   }
 
   protected void setReturnValue(Object mock, Object returnValue)
   {
     getControl(mock).setReturnValue(returnValue);
   }
 
   protected void setReturnValue(Object mock, long returnValue)
   {
     getControl(mock).setReturnValue(returnValue);
   }
 
   protected void setReturnValue(Object mock, float returnValue)
   {
     getControl(mock).setReturnValue(returnValue);
   }
 
   protected void setReturnValue(Object mock, double returnValue)
   {
     getControl(mock).setReturnValue(returnValue);
   }
 
   protected void setReturnValue(Object mock, boolean returnValue)
   {
     getControl(mock).setReturnValue(returnValue);
   }
 
   protected void addControl(MockControl control)
   {
     this._controls.add(control);
   }
 
   protected Object newMock(Class mockClass)
   {
     return newControl(mockClass).getMock();
   }
 
   protected void replayControls()
   {
     Iterator i = this._controls.iterator();
     while (i.hasNext())
     {
       MockControl c = (MockControl)i.next();
       c.replay();
     }
   }
 
   protected void verifyControls()
   {
     Iterator i = this._controls.iterator();
     while (i.hasNext())
     {
       MockControl c = (MockControl)i.next();
       c.verify();
       c.reset();
     }
   }
 
   protected void resetControls()
   {
     Iterator i = this._controls.iterator();
     while (i.hasNext())
     {
       MockControl c = (MockControl)i.next();
       c.reset();
     }
   }
 
   /** @deprecated */
   protected Location fabricateLocation(int line)
   {
     String path = "/" + super.getClass().getName().replace('.', '/');
 
     Resource r = new ClasspathResource(getClassResolver(), path);
 
     return new LocationImpl(r, line);
   }
 
   protected Location newLocation()
   {
     return fabricateLocation(this._line++);
   }
 
   protected ClassResolver getClassResolver()
   {
     if (this._classResolver == null) {
       this._classResolver = new DefaultClassResolver();
     }
     return this._classResolver;
   }
 
   protected boolean matches(String input, String pattern) throws Exception
   {
     setupMatcher();
 
     Pattern compiled = _compiler.compile(pattern);
 
     return _matcher.matches(input, compiled);
   }
 
   static
   {
     try
     {
       _classMockControlFactory = new ClassMockControlFactory(null);
     }
     catch (NoClassDefFoundError ex)
     {
       _classMockControlFactory = new PlaceholderClassMockControlFactory();
     }
   }
 
   static class PlaceholderClassMockControlFactory
     implements HiveMindTestCase.MockControlFactory
   {
     public MockControl newControl(Class mockClass)
     {
       throw new RuntimeException("Unable to instantiate EasyMock control for " + mockClass + "; ensure that easymockclassextension-1.1.jar and cglib-full-2.0.1.jar are on the classpath.");
     }
   }
 
   private static class ClassMockControlFactory
     implements HiveMindTestCase.MockControlFactory
   {
     private ClassMockControlFactory()
     {
     }
 
     public MockControl newControl(Class mockClass)
     {
       return MockClassControl.createStrictControl(mockClass);
     }
 
     ClassMockControlFactory(HiveMindTestCase.1 x0)
     {
     }
   }
 
   private static class InterfaceMockControlFactory
     implements HiveMindTestCase.MockControlFactory
   {
     private InterfaceMockControlFactory()
     {
     }
 
     public MockControl newControl(Class mockClass)
     {
       return MockControl.createStrictControl(mockClass);
     }
 
     InterfaceMockControlFactory(HiveMindTestCase.1 x0)
     {
     }
   }
 
   static abstract interface MockControlFactory
   {
     public abstract MockControl newControl(Class paramClass);
   }
 }