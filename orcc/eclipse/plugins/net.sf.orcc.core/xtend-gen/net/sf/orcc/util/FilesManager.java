/**
 * Copyright (c) 2014, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.sf.orcc.util.Result;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Utility class to manipulate files. It brings everything needed to extract files
 * from a jar plugin to the filesystem, check if 2 files are identical, read/write
 * files, etc.
 */
@SuppressWarnings("all")
public class FilesManager {
  private final static int BUFFER_SIZE = 1024;
  
  /**
   * <p>Copy the file or the folder at given <em>path</em> to the given
   * <em>target folder</em>.</p>
   * 
   * <p>It is important to understand that the resource (file or folder) at the given path
   * will be copied <b>into</b> the target folder. For example,
   * <code>extract("/path/to/file.txt", "/home/johndoe")</code> will copy <em>file.txt</em>
   * into <em>/home/johndoe</em>, and <code>extract("/path/to/MyFolder", "/home/johndoe")</code>
   * will create <em>MyFolder</em> directory in <em>/home/johndoe</em> and copy all files
   * from the source folder into it.
   * </p>
   * 
   * @param path The path of the source (folder or file) to copy
   * @param targetFolder The directory where to copy the source element
   * @return A Result object counting exactly how many files have been really
   * 		written, and how many haven't because they were already up-to-date
   * @throws FileNotFoundException If not resource have been found at the given path
   */
  public static Result extract(final String path, final String targetFolder) {
    try {
      Result _xblockexpression = null;
      {
        String _sanitize = FilesManager.sanitize(targetFolder);
        final File targetF = new File(_sanitize);
        final URL url = FilesManager.getUrl(path);
        if ((url == null)) {
          throw new FileNotFoundException(path);
        }
        Result _xifexpression = null;
        boolean _equals = url.getProtocol().equals("jar");
        if (_equals) {
          Result _xblockexpression_1 = null;
          {
            final String[] splittedURL = url.getFile().split("!");
            String _head = IterableExtensions.<String>head(((Iterable<String>)Conversions.doWrapArray(splittedURL)));
            final URI fileUri = new URI(_head);
            File _file = new File(fileUri);
            final JarFile jar = new JarFile(_file);
            _xblockexpression_1 = FilesManager.jarExtract(jar, IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(splittedURL))), targetF);
          }
          _xifexpression = _xblockexpression_1;
        } else {
          URI _uRI = FilesManager.getUrl(path).toURI();
          File _file = new File(_uRI);
          _xifexpression = FilesManager.fsExtract(_file, targetF);
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Copy the given <i>source</i> file to the given <em>targetFile</em>
   * path.
   * 
   * @param source
   * 			An existing File instance
   * @param targetFolder
   * 			The target folder to copy the file
   */
  private static Result fsExtract(final File source, final File targetFolder) {
    try {
      Result _xblockexpression = null;
      {
        boolean _exists = source.exists();
        boolean _not = (!_exists);
        if (_not) {
          String _path = source.getPath();
          throw new FileNotFoundException(_path);
        }
        String _name = source.getName();
        final File target = new File(targetFolder, _name);
        Result _xifexpression = null;
        boolean _isFile = source.isFile();
        if (_isFile) {
          Result _xifexpression_1 = null;
          boolean _isContentEqual = FilesManager.isContentEqual(source, target);
          if (_isContentEqual) {
            _xifexpression_1 = Result.newCachedInstance();
          } else {
            _xifexpression_1 = FilesManager.streamExtract(new FileInputStream(source), target);
          }
          _xifexpression = _xifexpression_1;
        } else {
          Result _xifexpression_2 = null;
          boolean _isDirectory = source.isDirectory();
          if (_isDirectory) {
            _xifexpression_2 = FilesManager.fsDirectoryExtract(source, target);
          }
          _xifexpression = _xifexpression_2;
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Copy the given <i>source</i> directory and its content into
   * the given <em>targetFolder</em> directory.
   * 
   * @param source
   * 			The source path of an existing file
   * @param targetFolder
   * 			Path to the folder where source will be copied
   */
  private static Result fsDirectoryExtract(final File source, final File targetFolder) {
    Assert.isTrue(source.isDirectory());
    boolean _exists = targetFolder.exists();
    boolean _not = (!_exists);
    if (_not) {
      Assert.isTrue(targetFolder.mkdirs());
    } else {
      Assert.isTrue(targetFolder.isDirectory());
    }
    final Result result = Result.newInstance();
    File[] _listFiles = source.listFiles();
    for (final File file : _listFiles) {
      result.merge(
        FilesManager.fsExtract(file, targetFolder));
    }
    return result;
  }
  
  /**
   * Starting point for extraction of a file/folder resource from a jar.
   */
  private static Result jarExtract(final JarFile jar, final String path, final File targetFolder) {
    Result _xblockexpression = null;
    {
      String _xifexpression = null;
      boolean _startsWith = path.startsWith("/");
      if (_startsWith) {
        _xifexpression = path.substring(1);
      } else {
        _xifexpression = path;
      }
      final String updatedPath = _xifexpression;
      final JarEntry entry = jar.getJarEntry(updatedPath);
      String _xifexpression_1 = null;
      boolean _endsWith = entry.getName().endsWith("/");
      if (_endsWith) {
        String _name = entry.getName();
        int _length = entry.getName().length();
        int _minus = (_length - 1);
        _xifexpression_1 = _name.substring(0, _minus);
      } else {
        _xifexpression_1 = entry.getName();
      }
      final String name = _xifexpression_1;
      String _xifexpression_2 = null;
      int _lastIndexOf = name.lastIndexOf("/");
      boolean _notEquals = (_lastIndexOf != (-1));
      if (_notEquals) {
        _xifexpression_2 = name.substring(name.lastIndexOf("/"));
      } else {
        _xifexpression_2 = name;
      }
      final String fileName = _xifexpression_2;
      Result _xifexpression_3 = null;
      boolean _isDirectory = entry.isDirectory();
      if (_isDirectory) {
        File _file = new File(targetFolder, fileName);
        _xifexpression_3 = FilesManager.jarDirectoryExtract(jar, entry, _file);
      } else {
        Result _xblockexpression_1 = null;
        {
          final Function1<JarEntry, Boolean> _function = new Function1<JarEntry, Boolean>() {
            @Override
            public Boolean apply(final JarEntry it) {
              return Boolean.valueOf(name.startsWith(updatedPath));
            }
          };
          final Iterable<JarEntry> entries = IterableExtensions.<JarEntry>filter(Collections.<JarEntry>list(jar.entries()), _function);
          Result _xifexpression_4 = null;
          int _size = IterableExtensions.size(entries);
          boolean _greaterThan = (_size > 1);
          if (_greaterThan) {
            File _file_1 = new File(targetFolder, fileName);
            _xifexpression_4 = FilesManager.jarDirectoryExtract(jar, entry, _file_1);
          } else {
            File _file_2 = new File(targetFolder, fileName);
            _xifexpression_4 = FilesManager.jarFileExtract(jar, entry, _file_2);
          }
          _xblockexpression_1 = _xifexpression_4;
        }
        _xifexpression_3 = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression_3;
    }
    return _xblockexpression;
  }
  
  /**
   * Extract all files in the given <em>entry</em> from the given <em>jar</em> into
   * the <em>target folder</em>.
   */
  private static Result jarDirectoryExtract(final JarFile jar, final JarEntry entry, final File targetFolder) {
    final String prefix = entry.getName();
    final Function1<JarEntry, Boolean> _function = new Function1<JarEntry, Boolean>() {
      @Override
      public Boolean apply(final JarEntry it) {
        return Boolean.valueOf(it.getName().startsWith(prefix));
      }
    };
    final Iterable<JarEntry> entries = IterableExtensions.<JarEntry>filter(Collections.<JarEntry>list(jar.entries()), _function);
    final Result result = Result.newInstance();
    for (final JarEntry e : entries) {
      String _substring = e.getName().substring(prefix.length());
      File _file = new File(targetFolder, _substring);
      result.merge(
        FilesManager.jarFileExtract(jar, e, _file));
    }
    return result;
  }
  
  /**
   * Extract the file <em>entry</em> from the given <em>jar</em> into the <em>target
   * file</em>.
   */
  private static Result jarFileExtract(final JarFile jar, final JarEntry entry, final File targetFile) {
    try {
      Result _xblockexpression = null;
      {
        targetFile.getParentFile().mkdirs();
        boolean _isDirectory = entry.isDirectory();
        if (_isDirectory) {
          targetFile.mkdir();
          return Result.newInstance();
        }
        Result _xifexpression = null;
        boolean _isContentEqual = FilesManager.isContentEqual(jar.getInputStream(entry), targetFile);
        if (_isContentEqual) {
          _xifexpression = Result.newCachedInstance();
        } else {
          _xifexpression = FilesManager.streamExtract(jar.getInputStream(entry), targetFile);
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Copy the content represented by the given <em>inputStream</em> into the
   * <em>target file</em>. No checking is performed for equality between input
   * stream and target file. Data are always written.
   * 
   * @return A Result object with information about extraction status
   */
  private static Result streamExtract(final InputStream inputStream, final File targetFile) {
    try {
      boolean _exists = targetFile.getParentFile().exists();
      boolean _not = (!_exists);
      if (_not) {
        targetFile.getParentFile().mkdirs();
      }
      final BufferedInputStream bufferedInput = new BufferedInputStream(inputStream);
      FileOutputStream _fileOutputStream = new FileOutputStream(targetFile);
      final BufferedOutputStream outputStream = new BufferedOutputStream(_fileOutputStream);
      final byte[] buffer = new byte[FilesManager.BUFFER_SIZE];
      int readLength = 0;
      while (((readLength = bufferedInput.read(buffer)) != (-1))) {
        outputStream.write(buffer, 0, readLength);
      }
      bufferedInput.close();
      outputStream.close();
      return Result.newOkInstance();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Search on the file system for a file or folder corresponding to the
   * given path. If not found, search on the current classpath. If this method
   * returns an URL, it always represents an existing file.
   * 
   * @param path
   * 			A path
   * @return
   * 			An URL for an existing file, or null
   */
  public static URL getUrl(final String path) {
    try {
      URL _xblockexpression = null;
      {
        final String sanitizedPath = FilesManager.sanitize(path);
        final File file = new File(sanitizedPath);
        boolean _exists = file.exists();
        if (_exists) {
          return file.toURI().toURL();
        }
        final Bundle bundle = FrameworkUtil.getBundle(FilesManager.class);
        URL _xifexpression = null;
        if ((bundle != null)) {
          URL _xblockexpression_1 = null;
          {
            final Bundle[] bundles = bundle.getBundleContext().getBundles();
            final Function1<Bundle, Boolean> _function = new Function1<Bundle, Boolean>() {
              @Override
              public Boolean apply(final Bundle it) {
                return Boolean.valueOf(it.getSymbolicName().contains("orcc"));
              }
            };
            final Function1<Bundle, URL> _function_1 = new Function1<Bundle, URL>() {
              @Override
              public URL apply(final Bundle it) {
                return it.getEntry(path);
              }
            };
            final Function1<URL, Boolean> _function_2 = new Function1<URL, Boolean>() {
              @Override
              public Boolean apply(final URL it) {
                return Boolean.valueOf((it != null));
              }
            };
            _xblockexpression_1 = IterableExtensions.<URL>findFirst(IterableExtensions.<Bundle, URL>map(IterableExtensions.<Bundle>filter(((Iterable<Bundle>)Conversions.doWrapArray(bundles)), _function), _function_1), _function_2);
          }
          _xifexpression = _xblockexpression_1;
        } else {
          _xifexpression = FilesManager.class.getResource(path);
        }
        final URL url = _xifexpression;
        URL _xifexpression_1 = null;
        String _protocol = null;
        if (url!=null) {
          _protocol=url.getProtocol();
        }
        String _lowerCase = null;
        if (_protocol!=null) {
          _lowerCase=_protocol.toLowerCase();
        }
        boolean _contains = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("bundle", "bundleresource", "bundleentry")).contains(_lowerCase);
        if (_contains) {
          _xifexpression_1 = FileLocator.resolve(url);
        } else {
          _xifexpression_1 = url;
        }
        _xblockexpression = _xifexpression_1;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Check if given a CharSequence have exactly the same content
   * than file b.
   */
  public static boolean isContentEqual(final CharSequence a, final File b) {
    byte[] _bytes = a.toString().getBytes();
    return FilesManager.isContentEqual(new ByteArrayInputStream(_bytes), b);
  }
  
  /**
   * Check if given File a have exactly the same content
   * than File b.
   */
  public static boolean isContentEqual(final File a, final File b) {
    try {
      return FilesManager.isContentEqual(new FileInputStream(a), b);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * <p>Compare the content of input stream <em>a</em> and file <em>b</em>.
   * Returns true if the </p>
   * 
   * <p><strong>Important</strong>: This method will close the input stream
   * <em>a</em> before returning the result.</p>
   * 
   * @param a An input stream
   * @param b A file
   * @return true if content in a is equals to content in b
   */
  public static boolean isContentEqual(final InputStream a, final File b) {
    try {
      boolean _exists = b.exists();
      boolean _not = (!_exists);
      if (_not) {
        return false;
      }
      final BufferedInputStream inputStreamA = new BufferedInputStream(a);
      FileInputStream _fileInputStream = new FileInputStream(b);
      final BufferedInputStream inputStreamB = new BufferedInputStream(_fileInputStream);
      int byteA = 0;
      int byteB = 0;
      do {
        {
          byteA = inputStreamA.read();
          byteB = inputStreamB.read();
        }
      } while(((byteA == byteB) && (byteA != (-1))));
      inputStreamA.close();
      inputStreamB.close();
      return (byteA == (-1));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Write <em>content</em> to the given <em>targetFolder</em> in a new file called
   * <em>fileName</em>. This method will write the content to the target file only if
   * it is empty or if its content is different than that given.
   * 
   * @param content The text content to write
   * @param targetFolder The folder where the file should be created
   * @param fileName The name of the new file
   */
  public static Result writeFile(final CharSequence content, final String targetFolder, final String fileName) {
    String _sanitize = FilesManager.sanitize(targetFolder);
    File _file = new File(_sanitize, fileName);
    return FilesManager.writeFile(content, _file);
  }
  
  /**
   * Write <em>content</em> to the given file <em>path</em>. This method will write the
   * content to the target file only if it is empty or if its content is different than
   * that given.
   * 
   * @param content The text content to write
   * @param path The path of the file to write
   */
  public static Result writeFile(final CharSequence content, final String path) {
    String _sanitize = FilesManager.sanitize(path);
    File _file = new File(_sanitize);
    return FilesManager.writeFile(content, _file);
  }
  
  /**
   * Write the <em>content</em> into the <em>targetFile</em> only if necessary.
   */
  public static Result writeFile(final CharSequence content, final File targetFile) {
    try {
      boolean _isContentEqual = FilesManager.isContentEqual(content, targetFile);
      if (_isContentEqual) {
        return Result.newCachedInstance();
      }
      boolean _exists = targetFile.exists();
      boolean _not = (!_exists);
      if (_not) {
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();
      }
      FileOutputStream _fileOutputStream = new FileOutputStream(targetFile);
      final PrintStream ps = new PrintStream(_fileOutputStream);
      ps.print(content);
      ps.close();
      return Result.newOkInstance();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Read the file at the given <em>path</em> and returns its content
   * as a String.
   * 
   * @param path
   * 			The path of the file to read
   * @returns
   * 			The content of the file
   * @throws FileNotFoundException
   * 			If the file doesn't exists
   */
  public static String readFile(final String path) {
    try {
      String _xblockexpression = null;
      {
        final URL url = FilesManager.getUrl(path);
        if ((url == null)) {
          throw new FileNotFoundException(path);
        }
        InputStream _xifexpression = null;
        boolean _equals = url.getProtocol().equals("jar");
        if (_equals) {
          InputStream _xblockexpression_1 = null;
          {
            final String[] splittedURL = url.getFile().split("!");
            String _substring = IterableExtensions.<String>head(((Iterable<String>)Conversions.doWrapArray(splittedURL))).substring(5);
            final JarFile jar = new JarFile(_substring);
            final String entryPath = IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(splittedURL)));
            String _xifexpression_1 = null;
            boolean _startsWith = entryPath.startsWith("/");
            if (_startsWith) {
              _xifexpression_1 = entryPath.substring(1);
            } else {
              _xifexpression_1 = path;
            }
            final String updatedPath = _xifexpression_1;
            _xblockexpression_1 = jar.getInputStream(jar.getEntry(updatedPath));
          }
          _xifexpression = _xblockexpression_1;
        } else {
          URI _uRI = url.toURI();
          File _file = new File(_uRI);
          _xifexpression = new FileInputStream(_file);
        }
        final InputStream inputStream = _xifexpression;
        int readLength = 0;
        byte[] buffer = new byte[FilesManager.BUFFER_SIZE];
        final BufferedInputStream bufferedInput = new BufferedInputStream(inputStream);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (((readLength = bufferedInput.read(buffer)) != (-1))) {
          outputStream.write(buffer, 0, readLength);
        }
        bufferedInput.close();
        _xblockexpression = outputStream.toString();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Transform the given path to a valid filesystem one.
   * 
   * <ul>
   * <li>It replaces first '~' by the home directory of the current user.</li>
   * </ul>
   */
  public static String sanitize(final String path) {
    if (((!StringExtensions.isNullOrEmpty(path)) && path.substring(0, 1).equals("~"))) {
      String _property = System.getProperty("user.home");
      final StringBuilder builder = new StringBuilder(_property);
      builder.append(File.separatorChar).append(path.substring(1));
      return builder.toString();
    }
    return path;
  }
  
  /**
   * Delete the given d directory and all its content
   */
  public static void recursiveDelete(final File d) {
    File[] _listFiles = d.listFiles();
    for (final File e : _listFiles) {
      boolean _isFile = e.isFile();
      if (_isFile) {
        e.delete();
      } else {
        boolean _isDirectory = e.isDirectory();
        if (_isDirectory) {
          FilesManager.recursiveDelete(e);
        }
      }
    }
    d.delete();
  }
}
