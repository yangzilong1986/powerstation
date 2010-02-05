 package org.apache.hivemind.ant;
 
 import java.io.File;
 import org.apache.tools.ant.BuildException;
 import org.apache.tools.ant.Project;
 import org.apache.tools.ant.ProjectComponent;
 import org.apache.tools.ant.Task;
 import org.apache.tools.ant.types.Path;
 
 public class ManifestClassPath extends Task
 {
   private String _property;
   private Path _classpath;
   private File _directory;
 
   public Path createClasspath()
   {
     this._classpath = new Path(super.getProject());
 
     return this._classpath;
   }
 
   public String getProperty()
   {
     return this._property;
   }
 
   public void setProperty(String string)
   {
     this._property = string;
   }
 
   public void execute()
   {
     if (this._classpath == null) {
       throw new BuildException("You must specify a classpath to generate the manifest entry from");
     }
     if (this._property == null) {
       throw new BuildException("You must specify a property to assign the manifest classpath to");
     }
     StringBuffer buffer = new StringBuffer();
 
     String[] paths = this._classpath.list();
 
     String stripPrefix = null;
 
     if (this._directory != null) {
       stripPrefix = this._directory.getPath();
     }
 
     boolean needSep = false;
 
     for (int i = 0; i < paths.length; ++i)
     {
       String path = paths[i];
 
       if (stripPrefix != null)
       {
         if (!(path.startsWith(stripPrefix)))
         {
           continue;
         }
 
         if (path.length() == stripPrefix.length()) {
           continue;
         }
         if (needSep) {
           buffer.append(' ');
         }
 
         buffer.append(filter(path.substring(stripPrefix.length() + 1)));
 
         needSep = true;
       }
       else
       {
         if (needSep) {
           buffer.append(' ');
         }
         File f = new File(path);
 
         buffer.append(f.getName());
 
         needSep = true;
       }
     }
 
     super.getProject().setProperty(this._property, buffer.toString());
   }
 
   public File getDirectory()
   {
     return this._directory;
   }
 
   public void setDirectory(File file)
   {
     this._directory = file;
   }
 
   protected String filter(String value)
   {
     if (File.separatorChar == '/') {
       return value;
     }
     return value.replace(File.separatorChar, '/');
   }
 }