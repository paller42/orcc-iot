/**
 * Copyright (c) 2013, INSA Rennes
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
 *   * Neither the name of INSA Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
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
package net.sf.orcc.backends.util;

import com.google.common.base.Objects;
import java.util.List;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.OrccAttributes;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Class containing static methods useful to set potential alignment information of ports
 * and actions.
 * 
 * @author Alexandre Sanchez
 */
@SuppressWarnings("all")
public class Alignable {
  private static boolean isPortAlignable(final Pattern pattern, final Port port) {
    int _numTokens = pattern.getNumTokens(port);
    return (_numTokens >= BackendsConstants.MIN_REPEAT_ALIGNABLE);
  }
  
  private static Iterable<Attribute> filterAlignableAttributs(final EList<Attribute> attrs) {
    final Function1<Attribute, Boolean> _function = new Function1<Attribute, Boolean>() {
      @Override
      public Boolean apply(final Attribute it) {
        return Boolean.valueOf(it.getName().endsWith(("_" + OrccAttributes.ALIGNABLE)));
      }
    };
    return IterableExtensions.<Attribute>filter(attrs, _function);
  }
  
  private static Iterable<Attribute> filterNotAlignableAttributs(final EList<Attribute> attrs) {
    final Function1<Attribute, Boolean> _function = new Function1<Attribute, Boolean>() {
      @Override
      public Boolean apply(final Attribute it) {
        return Boolean.valueOf(it.getName().endsWith(("_NOT_" + OrccAttributes.ALIGNABLE)));
      }
    };
    return IterableExtensions.<Attribute>filter(attrs, _function);
  }
  
  private static boolean setAlignableAttributs(final Pattern pattern, final Port port, final String actionName) {
    boolean isAlignable = Alignable.isPortAlignable(pattern, port);
    if (isAlignable) {
      port.addAttribute(((actionName + "_") + OrccAttributes.ALIGNABLE));
      port.setAttribute(((actionName + "_") + OrccAttributes.ALIGNABLE), Integer.valueOf(pattern.getNumTokens(port)));
    } else {
      port.addAttribute(((actionName + "_NOT_") + OrccAttributes.ALIGNABLE));
    }
    return isAlignable;
  }
  
  private static void setAlwaysAlignedAttributs(final Pattern pattern, final Port port) {
    final int numTokens = pattern.getNumTokens(port);
    boolean isAlwaysAligned = (Alignable.isPortAlignable(pattern, port) && ValueUtil.isPowerOfTwo(numTokens));
    if (isAlwaysAligned) {
      isAlwaysAligned = ((isAlwaysAligned && (IterableExtensions.size(Alignable.filterAlignableAttributs(port.getAttributes())) == 1)) && (IterableExtensions.size(Alignable.filterNotAlignableAttributs(port.getAttributes())) == 0));
      int _size = IterableExtensions.size(Alignable.filterAlignableAttributs(port.getAttributes()));
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        Iterable<Attribute> _filterAlignableAttributs = Alignable.filterAlignableAttributs(port.getAttributes());
        for (final Attribute attr : _filterAlignableAttributs) {
          isAlwaysAligned = (isAlwaysAligned && Objects.equal(attr.getStringValue(), Integer.valueOf(numTokens).toString()));
        }
      }
    }
    if (isAlwaysAligned) {
      port.addAttribute(OrccAttributes.ALIGNED_ALWAYS);
    }
  }
  
  private static void setAlignableAttributs(final Action action) {
    boolean isVectorizable = false;
    EList<Port> _ports = action.getInputPattern().getPorts();
    for (final Port port : _ports) {
      isVectorizable = (Alignable.setAlignableAttributs(action.getInputPattern(), port, action.getName()) || isVectorizable);
    }
    EList<Port> _ports_1 = action.getOutputPattern().getPorts();
    for (final Port port_1 : _ports_1) {
      isVectorizable = (Alignable.setAlignableAttributs(action.getOutputPattern(), port_1, action.getName()) || isVectorizable);
    }
    if (isVectorizable) {
      action.addAttribute(OrccAttributes.ALIGNABLE);
    }
  }
  
  private static void setAlwaysAlignedAttributs(final Action action) {
    boolean isAlwaysAligned = true;
    EList<Port> _ports = action.getInputPattern().getPorts();
    for (final Port port : _ports) {
      {
        Alignable.setAlwaysAlignedAttributs(action.getInputPattern(), port);
        isAlwaysAligned = (isAlwaysAligned && port.hasAttribute(OrccAttributes.ALIGNED_ALWAYS));
      }
    }
    EList<Port> _ports_1 = action.getOutputPattern().getPorts();
    for (final Port port_1 : _ports_1) {
      {
        Alignable.setAlwaysAlignedAttributs(action.getOutputPattern(), port_1);
        isAlwaysAligned = (isAlwaysAligned && port_1.hasAttribute(OrccAttributes.ALIGNED_ALWAYS));
      }
    }
    if (isAlwaysAligned) {
      action.addAttribute(OrccAttributes.ALIGNED_ALWAYS);
    }
  }
  
  public static void setAlignability(final Actor actor) {
    EList<Action> _actions = actor.getActions();
    for (final Action action : _actions) {
      Alignable.setAlignableAttributs(action);
    }
    EList<Action> _actions_1 = actor.getActions();
    for (final Action action_1 : _actions_1) {
      boolean _hasAttribute = action_1.hasAttribute(OrccAttributes.ALIGNABLE);
      if (_hasAttribute) {
        Alignable.setAlwaysAlignedAttributs(action_1);
      }
    }
  }
  
  public static void setAlignability(final Network network) {
    List<Actor> _allActors = network.getAllActors();
    for (final Actor actor : _allActors) {
      Alignable.setAlignability(actor);
    }
  }
}
