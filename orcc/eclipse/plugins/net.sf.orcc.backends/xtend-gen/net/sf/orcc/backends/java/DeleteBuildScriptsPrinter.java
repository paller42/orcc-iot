/**
 * Copyright (c) 2012, Gang of four...
 * All rights reserved.
 */
package net.sf.orcc.backends.java;

import java.util.List;
import net.sf.orcc.backends.java.JavaTemplate;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class DeleteBuildScriptsPrinter extends JavaTemplate {
  private Network network;
  
  protected boolean hasNative = false;
  
  public void setNetwork(final Network network) {
    this.network = network;
    this.hasNative = false;
    int _size = network.getAllActors().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      boolean _isNative = network.getAllActors().get((i).intValue()).isNative();
      if (_isNative) {
        this.hasNative = true;
      }
    }
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
    _builder.append("<classpath>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<classpathentry kind=\"src\" path=\"runtime\"/>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<classpathentry kind=\"src\" path=\"src\"/>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER\"/>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<classpathentry kind=\"output\" path=\"bin\"/>");
    _builder.newLine();
    _builder.append("</classpath>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence getPomXMLContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<modelVersion>4.0.0</modelVersion>");
    _builder.newLine();
    _builder.append("  \t");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("<groupId>net.sf.orcc</groupId>");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("<artifactId>");
    String _simpleName = this.network.getSimpleName();
    _builder.append(_simpleName, " ");
    _builder.append("</artifactId>");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("<version>1.0-SNAPSHOT</version>");
    _builder.newLine();
    _builder.append(" ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<properties>");
    _builder.newLine();
    _builder.append("  \t");
    _builder.append("<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>");
    _builder.newLine();
    _builder.append("  \t");
    _builder.append("<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>\t\t  ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<maven.compiler.source>1.8</maven.compiler.source>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<maven.compiler.target>1.8</maven.compiler.target>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("</properties>");
    _builder.newLine();
    {
      if (this.hasNative) {
        _builder.append(" ");
        _builder.append("<repositories>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<repository>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<id>repos-local</id>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<name>repository</name>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<url>file://${project.basedir}\\repos</url>");
        _builder.newLine();
        _builder.append(" \t");
        _builder.append("</repository>");
        _builder.newLine();
        _builder.append("</repositories>");
        _builder.newLine();
      }
    }
    _builder.append("<dependencies>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<dependency>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<groupId>org.java-websocket</groupId>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<artifactId>Java-WebSocket</artifactId>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<version>1.3.9</version>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</dependency>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<dependency>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<groupId>com.fasterxml.jackson.core</groupId>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<artifactId>jackson-databind</artifactId>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<version>2.9.8</version>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</dependency>");
    _builder.newLine();
    {
      List<Actor> _allActors = this.network.getAllActors();
      for(final Actor act : _allActors) {
        _builder.append("<dependency>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<groupId>");
        String _package = act.getPackage();
        _builder.append(_package, "\t");
        _builder.append("</groupId>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<artifactId>");
        String _simpleName_1 = act.getSimpleName();
        _builder.append(_simpleName_1, "\t");
        _builder.append("</artifactId>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<version>1.0-SNAPSHOT</version>");
        _builder.newLine();
        _builder.append("</dependency>");
        _builder.newLine();
      }
    }
    _builder.append("</dependencies>");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("<build>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<directory>bin</directory>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<sourceDirectory>src</sourceDirectory>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<plugins>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<plugin>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<groupId>org.apache.maven.plugins</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<artifactId>maven-assembly-plugin</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<executions>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<execution>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<phase>package</phase>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<goals>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<goal>single</goal>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("</goals>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<configuration>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<archive>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("<manifest>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("<mainClass>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t\t");
    String _simpleName_2 = this.network.getSimpleName();
    _builder.append(_simpleName_2, "\t\t\t\t\t\t\t\t\t");
    _builder.append(".");
    String _simpleName_3 = this.network.getSimpleName();
    _builder.append(_simpleName_3, "\t\t\t\t\t\t\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("</mainClass>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("</manifest>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("</archive>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<descriptorRefs>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("<descriptorRef>jar-with-dependencies</descriptorRef>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("</descriptorRefs>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("</configuration>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</execution>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</executions>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</plugin>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</plugins>");
    _builder.newLine();
    _builder.append("</build>");
    _builder.newLine();
    _builder.append(" ");
    _builder.newLine();
    _builder.append("</project>");
    _builder.newLine();
    return _builder;
  }
}
