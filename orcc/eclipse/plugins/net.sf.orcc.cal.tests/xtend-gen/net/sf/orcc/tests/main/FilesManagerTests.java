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
package net.sf.orcc.tests.main;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.net.URL;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test methods of OrccFileWriter utility class
 */
@RunWith(JUnit4.class)
@SuppressWarnings("all")
public class FilesManagerTests extends Assert {
  private static String tempDir = "";
  
  private String jarFile = "/java/lang/Class.class";
  
  private String jarFolder = "/org/jgrapht/graph";
  
  private String bundleFile = "/test/extraction/subfolder/aaa.z";
  
  private String bundleFolder = "/test/extraction";
  
  private String standardFolder = "~/.ssh";
  
  @BeforeClass
  public static void initialization() {
    StringConcatenation _builder = new StringConcatenation();
    String _property = System.getProperty("java.io.tmpdir");
    _builder.append(_property);
    _builder.append(File.separatorChar);
    _builder.append("ORCC_FILE_TESTS");
    FilesManagerTests.tempDir = _builder.toString();
    final File f = new File(FilesManagerTests.tempDir);
    boolean _exists = f.exists();
    if (_exists) {
      FilesManager.recursiveDelete(f);
    }
  }
  
  /**
   * Append the given <em>fileName</em> to the current temp directory,
   * using the OS specific path separator, to build a new temp path.
   */
  private String getTempFilePath(final String fileName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(FilesManagerTests.tempDir);
    _builder.append(File.separatorChar);
    _builder.append(fileName);
    return _builder.toString();
  }
  
  @Test
  public void pathSanitization() {
    Assert.assertTrue(this.standardFolder.startsWith("~"));
    final String result = FilesManager.sanitize(this.standardFolder);
    Assert.assertFalse(result.startsWith("~"));
    Assert.assertTrue(result.startsWith(System.getProperty("user.home")));
  }
  
  @Test
  public void classpathFilesFolderLookup() {
    URL url = FilesManager.getUrl(this.jarFile);
    Assert.assertNotNull(url);
    Assert.assertEquals("jar", url.getProtocol());
    url = FilesManager.getUrl(this.jarFolder);
    Assert.assertNotNull(url);
    Assert.assertEquals("jar", url.getProtocol());
    url = FilesManager.getUrl(this.bundleFile);
    Assert.assertNotNull(url);
    url = FilesManager.getUrl(this.bundleFolder);
    Assert.assertNotNull(url);
    url = FilesManager.getUrl(this.standardFolder);
    Assert.assertNotNull(url);
    Assert.assertEquals("file", url.getProtocol());
  }
  
  @Test
  public void simpleFileRead() {
    Assert.assertEquals("azerty", FilesManager.readFile("/test/extraction/aTextFile.txt"));
  }
  
  @Test
  public void simpleFileWrite() {
    try {
      final String filePath = this.getTempFilePath("writtenFile.txt");
      FilesManager.writeFile("consectetur adipisicing elit, sed", filePath);
      final File targetFile = new File(filePath);
      Assert.assertTrue(targetFile.isFile());
      Assert.assertNotEquals(targetFile.length(), 0);
      Assert.assertEquals("consectetur adipisicing elit, sed", Files.toString(targetFile, Charsets.UTF_8));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void validWrittenContent() {
    final String theFile = this.getTempFilePath("loremIpsumContentTest.txt");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod");
    _builder.newLine();
    _builder.append("tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,");
    _builder.newLine();
    _builder.append("quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo");
    _builder.newLine();
    _builder.append("consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse");
    _builder.newLine();
    _builder.append("cillum dolore eu fugiat nulla pariatur.");
    _builder.newLine();
    final String theContent = _builder.toString();
    FilesManager.writeFile(theContent, theFile);
    Assert.assertEquals(theContent, FilesManager.readFile(theFile));
  }
  
  @Test
  public void contentEquality() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod");
    _builder.newLine();
    _builder.append("tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam");
    _builder.newLine();
    final String theContent = _builder.toString();
    final String f1 = this.getTempFilePath("contentEquality1.txt");
    final String f2 = this.getTempFilePath("contentEquality2.txt");
    FilesManager.writeFile(theContent, f1);
    FilesManager.writeFile(theContent, f2);
    File _file = new File(f1);
    File _file_1 = new File(f2);
    Assert.assertTrue(FilesManager.isContentEqual(_file, _file_1));
    Assert.assertEquals(theContent, FilesManager.readFile(f1));
    Assert.assertEquals(theContent, FilesManager.readFile(f2));
    File _file_2 = new File(f1);
    Assert.assertTrue(FilesManager.isContentEqual(theContent, _file_2));
    File _file_3 = new File(f2);
    Assert.assertTrue(FilesManager.isContentEqual(theContent, _file_3));
  }
  
  @Test
  public void fileExtraction() {
    FilesManager.extract("/test/pass/CodegenWhile.cal", FilesManagerTests.tempDir);
    final File targetFile = new File(FilesManagerTests.tempDir, "CodegenWhile.cal");
    Assert.assertTrue(targetFile.exists());
    Assert.assertNotEquals(targetFile.length(), 0);
    Assert.assertTrue(FilesManager.isContentEqual(
      FilesManager.readFile("/test/pass/CodegenWhile.cal"), targetFile));
  }
  
  @Test
  public void folderExtraction() {
    FilesManager.extract("/test/extraction", FilesManagerTests.tempDir);
    final File targetFolder = new File(FilesManagerTests.tempDir, "extraction");
    Assert.assertTrue(targetFolder.isDirectory());
    Assert.assertTrue(new File(targetFolder, "subfolder").isDirectory());
    Assert.assertTrue(new File(targetFolder, "subfolder/aaa.z").isFile());
    Assert.assertTrue(new File(targetFolder, "subfolder/subsubfolder/zzz.txt").isFile());
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(FilesManagerTests.tempDir);
    _builder.append("/extraction/subfolder/subsubfolder/xxxxx.txt");
    Assert.assertEquals("xxxx", 
      FilesManager.readFile(_builder.toString()));
  }
  
  @Test
  public void jarExtractions() {
    final String targetDirectory = this.getTempFilePath("jarExtract");
    FilesManager.extract(this.jarFile, targetDirectory);
    Assert.assertTrue(new File(targetDirectory, "Class.class").isFile());
    FilesManager.extract(this.jarFolder, targetDirectory);
    Assert.assertTrue(new File(targetDirectory, "graph").isDirectory());
    Assert.assertTrue(new File(targetDirectory, "graph/DefaultListenableGraph.class").isFile());
  }
  
  @Test
  public void cachedFiles() {
    try {
      final String path = this.getTempFilePath("testCached");
      final File file = new File(path);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod");
      _builder.newLine();
      _builder.append("tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,");
      _builder.newLine();
      _builder.append("quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo");
      _builder.newLine();
      _builder.append("consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse");
      _builder.newLine();
      _builder.append("cillum dolore eu fugiat nulla pariatur.");
      _builder.newLine();
      final String content = _builder.toString();
      Result result = FilesManager.writeFile(content, path);
      Assert.assertEquals(result.cached(), 0);
      Assert.assertEquals(result.written(), 1);
      final long timestamp = file.lastModified();
      Assert.assertNotEquals(timestamp, 0);
      Thread.sleep(1500);
      result = FilesManager.writeFile(content, path);
      Assert.assertEquals(result.cached(), 1);
      Assert.assertEquals(result.written(), 0);
      Assert.assertEquals(timestamp, file.lastModified());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
