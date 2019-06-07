/**
 * Copyright (c) 2013, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.javaspring;

import java.util.Map;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.javaspring.ActorPrinter;
import net.sf.orcc.backends.javaspring.JavaSpringTemplate;
import net.sf.orcc.df.Instance;

@SuppressWarnings("all")
public class InstancePrinter extends JavaSpringTemplate {
  private Instance instance;
  
  private String entityName;
  
  private Map<String, Object> options;
  
  public Instance setInstance(final Instance instance) {
    Instance _xblockexpression = null;
    {
      this.entityName = instance.getName();
      boolean _isActor = instance.isActor();
      boolean _not = (!_isActor);
      if (_not) {
        throw new OrccRuntimeException((("Instance " + this.entityName) + " is not an Actor\'s instance"));
      }
      _xblockexpression = this.instance = instance;
    }
    return _xblockexpression;
  }
  
  public int printInstance(final String targetFolder) {
    int _xblockexpression = (int) 0;
    {
      final ActorPrinter actorPrinter = new ActorPrinter(this.instance, this.options);
      _xblockexpression = actorPrinter.print(targetFolder);
    }
    return _xblockexpression;
  }
  
  public CharSequence getFileContent() {
    final ActorPrinter actorPrinter = new ActorPrinter(this.instance, this.options);
    return actorPrinter.getActorFileContent();
  }
}
