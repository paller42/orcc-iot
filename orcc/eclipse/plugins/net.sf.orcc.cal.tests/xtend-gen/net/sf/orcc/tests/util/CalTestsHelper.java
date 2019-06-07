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
package net.sf.orcc.tests.util;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import net.sf.orcc.cal.CalInjectorProvider;
import net.sf.orcc.cal.cal.AstEntity;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;

/**
 * This class provides methods to test more efficiently CAL actors and units
 * 
 * @author Antoine Lorence
 */
@InjectWith(CalInjectorProvider.class)
@SuppressWarnings("all")
public class CalTestsHelper extends AbstractXtextTests {
  @Inject
  protected Injector injector;
  
  @Inject
  protected Provider<XtextResourceSet> resourceSetProvider;
  
  @Inject
  @Extension
  protected ParseHelper<AstEntity> _parseHelper;
  
  /**
   * Open the given path as resource stream and parse it as CAL file.
   * Returns the parsed AstEntity, or null if the file contains syntax errors
   */
  public AstEntity parseFile(final String path) {
    AstEntity _xblockexpression = null;
    {
      final InputStream input = this.getClass().getResourceAsStream(path);
      final URI uri = URI.createPlatformResourceURI(path, true);
      _xblockexpression = this._parseHelper.parse(input, uri, Collections.EMPTY_MAP, this.resourceSetProvider.get());
    }
    return _xblockexpression;
  }
  
  /**
   * Open the given paths as resource streams and parse them. Returns the list of
   * parsed AstEntity. Each null elements correspond to actors with syntax errors
   */
  public List<AstEntity> parseFiles(final String... paths) {
    final XtextResourceSet rs = this.resourceSetProvider.get();
    List<AstEntity> parsedEntities = Collections.<AstEntity>unmodifiableList(CollectionLiterals.<AstEntity>newArrayList());
    for (final String path : paths) {
      {
        final InputStream input = this.getClass().getResourceAsStream(path);
        final URI uri = URI.createPlatformResourceURI(path, true);
        parsedEntities.add(this._parseHelper.parse(input, uri, Collections.EMPTY_MAP, rs));
      }
    }
    return parsedEntities;
  }
}
