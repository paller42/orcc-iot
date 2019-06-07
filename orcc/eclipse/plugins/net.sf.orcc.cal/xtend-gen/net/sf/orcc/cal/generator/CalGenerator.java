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
package net.sf.orcc.cal.generator;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Unit;
import net.sf.orcc.frontend.ActorTransformer;
import net.sf.orcc.frontend.Frontend;
import net.sf.orcc.frontend.UnitTransformer;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.Attributable;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 * 
 * This class is used by Xtext to build IR file. The default BuilderParticipant has been
 * extended (see net.sf.orcc.cal.ui.builder.CalBuilder) to add calls to beforeBuild() and afterBuild().
 * These functions are used to manage dependency at the beginning and the end of a build session. A build
 * session build all file in a specific project. There is no way to know which project has been built before,
 * or if other project will be build after.
 * 
 * This generator class try to manage as quick as possible references between CAL Actors / Units.
 * It must deal with references between files in the same project or in other projects.
 * 
 * For each file to build, this class check Units it import. If it is an import from the same project,
 * the import is built before the current file. If it is an import from another project, we assume
 * this project is up-to-date, and we load mappings between AST/IR objects in the frontend.
 * 
 * @author Antoine Lorence
 */
@SuppressWarnings("all")
public class CalGenerator implements IGenerator {
  private final ActorTransformer actorTransformer = new ActorTransformer();
  
  private final UnitTransformer unitTransformer = new UnitTransformer();
  
  private final HashSet<Resource> loadedResources = CollectionLiterals.<Resource>newHashSet();
  
  private IProject currentProject;
  
  private ResourceSet calResourceSet;
  
  private ResourceSet irResourceSet;
  
  @Inject
  private Provider<ResourceSet> rsProvider;
  
  /**
   * Start a build session. This method is called by net.sf.orcc.cal.ui.builder.CalBuilder
   * each time a build is triggered for a specific project.
   * 
   * Please note that when user perform a Clean on more that one project, this method will
   * be called as many times as projects to clean.
   * 
   * Default Xtext implementation of BuilderParticipant ensure projects will be built in
   * the right order.
   */
  public ResourceSet beforeBuild(final IProject project, final ResourceSet rs) {
    ResourceSet _xblockexpression = null;
    {
      this.currentProject = project;
      this.loadedResources.clear();
      this.calResourceSet = rs;
      _xblockexpression = this.irResourceSet = this.rsProvider.get();
    }
    return _xblockexpression;
  }
  
  /**
   * Generate the IR file corresponding to the given calResource. If the given
   * resource depends on other resources (Units), this method ensures:
   * <ol>
   * <li>All objects in the imported resource will be available in CacheManager</li>
   * <li>We will not try to transform/serialize the imported resource if it is not absolutely necessary</li>
   * </ol>
   * 
   * This method is called by net.sf.orcc.cal.ui.builder.CalBuilder
   */
  @Override
  public void doGenerate(final Resource calResource, final IFileSystemAccess fsa) {
    final String irSubPath = this.getIrRelativePath(calResource);
    boolean _contains = this.loadedResources.contains(calResource);
    if (_contains) {
      return;
    }
    final Function1<Resource, Boolean> _function = new Function1<Resource, Boolean>() {
      @Override
      public Boolean apply(final Resource it) {
        boolean _contains = CalGenerator.this.loadedResources.contains(it);
        return Boolean.valueOf((!_contains));
      }
    };
    final Iterable<Resource> toImport = IterableExtensions.<Resource>filter(this.getImportedResource(this.getEntity(calResource)), _function);
    for (final Resource importedResource : toImport) {
      boolean _isInSameProject = this.isInSameProject(importedResource, calResource);
      if (_isInSameProject) {
        this.doGenerate(importedResource, fsa);
      } else {
        this.loadMappings(importedResource);
      }
    }
    fsa.generateFile(irSubPath, this.serialize(calResource));
    this.loadedResources.add(calResource);
  }
  
  /**
   * Returns a EMF serialized version of the given AstEntity
   */
  private String serialize(final Resource calResource) {
    try {
      String _xblockexpression = null;
      {
        final AstEntity astEntity = this.getEntity(calResource);
        Attributable _xifexpression = null;
        AstUnit _unit = astEntity.getUnit();
        boolean _tripleNotEquals = (_unit != null);
        if (_tripleNotEquals) {
          _xifexpression = this.unitTransformer.doSwitch(astEntity.getUnit());
        } else {
          Actor _xifexpression_1 = null;
          AstActor _actor = astEntity.getActor();
          boolean _tripleNotEquals_1 = (_actor != null);
          if (_tripleNotEquals_1) {
            _xifexpression_1 = this.actorTransformer.doSwitch(astEntity.getActor());
          } else {
            _xifexpression_1 = null;
          }
          _xifexpression = _xifexpression_1;
        }
        final Attributable entity = _xifexpression;
        if ((entity == null)) {
          OrccLogger.warnln("Unable to transform the CAL content");
          return "";
        }
        final Resource irResource = this.irResourceSet.createResource(OrccUtil.getIrUri(calResource.getURI()));
        irResource.getContents().add(entity);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        irResource.save(outputStream, CollectionLiterals.<Object, Object>newHashMap());
        _xblockexpression = outputStream.toString();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Load content from the given AstEntity (unit) resource into the Frontend
   */
  private boolean loadMappings(final Resource calResource) {
    boolean _xblockexpression = false;
    {
      final Resource irResource = this.irResourceSet.getResource(OrccUtil.getIrUri(calResource.getURI()), true);
      EObject _head = IterableExtensions.<EObject>head(irResource.getContents());
      final Unit unit = ((Unit) _head);
      final AstUnit astUnit = this.getEntity(calResource).getUnit();
      EList<Variable> _variables = astUnit.getVariables();
      for (final Variable astConstant : _variables) {
        {
          final Var irConstant = unit.getConstant(astConstant.getName());
          Frontend.instance.putMapping(astConstant, irConstant);
        }
      }
      EList<Function> _functions = astUnit.getFunctions();
      for (final Function function : _functions) {
        {
          final Procedure procedure = unit.getProcedure(function.getName());
          Frontend.instance.putMapping(function, procedure);
        }
      }
      EList<AstProcedure> _procedures = astUnit.getProcedures();
      for (final AstProcedure astProcedure : _procedures) {
        {
          final Procedure procedure = unit.getProcedure(astProcedure.getName());
          Frontend.instance.putMapping(astProcedure, procedure);
        }
      }
      _xblockexpression = this.loadedResources.add(calResource);
    }
    return _xblockexpression;
  }
  
  /**
   * Check if given resources URIs point in the same project
   */
  private boolean isInSameProject(final Resource a, final Resource b) {
    return a.getURI().segment(1).equals(b.getURI().segment(1));
  }
  
  /**
   * Return the AstEntity contained in the given resource
   */
  private AstEntity getEntity(final Resource calResource) {
    EObject _head = IterableExtensions.<EObject>head(calResource.getContents());
    return ((AstEntity) _head);
  }
  
  /**
   * Returns the path where IR file corresponding to given calResource should be stored.
   * Important: the returned path is relative to the output folder (not the project)
   * 
   * @param calResource An EMF resource representing a CAL file
   * @return Path of the IR file as String
   */
  private String getIrRelativePath(final Resource calResource) {
    String _xblockexpression = null;
    {
      String _platformString = calResource.getURI().toPlatformString(true);
      final Path path = new Path(_platformString);
      final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
      _xblockexpression = file.getProjectRelativePath().removeFirstSegments(1).removeFileExtension().addFileExtension(OrccUtil.IR_SUFFIX).toString();
    }
    return _xblockexpression;
  }
  
  /**
   * Return a list of all resources imported in the given AstEntity
   */
  private HashSet<Resource> getImportedResource(final AstEntity astEntity) {
    final HashSet<Resource> dependingResource = CollectionLiterals.<Resource>newHashSet();
    EList<Import> _imports = astEntity.getImports();
    for (final Import imp : _imports) {
      {
        final IFile calFile = this.getExistingCalFile(imp);
        if ((calFile != null)) {
          dependingResource.add(
            this.calResourceSet.getResource(URI.createPlatformResourceURI(calFile.getFullPath().toString(), true), true));
        }
      }
    }
    return dependingResource;
  }
  
  /**
   * If the given import is valid, return the corresponding CAL file.
   * If not, this method returns null.
   */
  private IFile getExistingCalFile(final Import imported) {
    final int lastDotIndex = imported.getImportedNamespace().lastIndexOf(".");
    final String unitQualifiedName = imported.getImportedNamespace().substring(0, lastDotIndex);
    String _replace = unitQualifiedName.replace(".", "/");
    final IPath unitPath = new Path(_replace).addFileExtension(OrccUtil.CAL_SUFFIX);
    List<IFolder> _allSourceFolders = OrccUtil.getAllSourceFolders(this.currentProject);
    for (final IFolder folder : _allSourceFolders) {
      {
        final IFile ifile = folder.getFile(unitPath);
        boolean _exists = ifile.exists();
        if (_exists) {
          return ifile;
        }
      }
    }
    return null;
  }
  
  /**
   * Perform cleans needed by the end of a session.
   */
  public void afterBuild() {
    CacheManager.instance.unloadAllCaches();
    this.loadedResources.clear();
  }
}
