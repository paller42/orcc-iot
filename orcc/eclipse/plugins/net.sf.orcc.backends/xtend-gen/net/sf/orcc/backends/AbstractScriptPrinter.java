/**
 * Copyright (c) 2012,
 * Nebojša Taušan, Gabor Paller, Gabor Farkas, Endri Bezati
 * All rights reserved.
 */
package net.sf.orcc.backends;

import net.sf.orcc.df.Network;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * following script generators are implemented:
 * 
 * getProjectFileContent()    		creates the .project file which enable the import of generated sources in Eclipse IDE
 * getClasspathFileContent()  		creates the .classpath file which enable the import of generated sources in Eclipse IDE
 */
@SuppressWarnings("all")
public abstract class AbstractScriptPrinter {
  protected Network network;
  
  public Network setNetwork(final Network network) {
    return this.network = network;
  }
  
  public CharSequence getProjectFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<projectDescription>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<name>");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName, "\t");
    _builder.append("</name>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<comment></comment>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<projects>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</projects>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<buildSpec>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<buildCommand>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<name>org.eclipse.jdt.core.javabuilder</name>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<arguments>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</arguments>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</buildCommand>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</buildSpec>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<natures>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<nature>org.eclipse.jdt.core.javanature</nature>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</natures>");
    _builder.newLine();
    _builder.append("</projectDescription>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getClasspathFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<classpath>   ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<classpathentry kind=\"src\" path=\"src\"/>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER\"/>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<classpathentry kind=\"output\" path=\"target\"/>");
    _builder.newLine();
    _builder.append("</classpath>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getPomXMLContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getSpringStarterFileContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getQueueConfig() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    return _builder;
  }
}
