/**
 * Copyright (c) 2013, University of Rennes 1 / IRISA
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
 *   * Neither the name of the University of Rennes 1 / IRISA nor the names of its
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

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.Map;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;

/**
 * Class containing static methods useful to check the validity of an input
 * network before generating code.
 * 
 * @author Herve Yviquel
 */
@SuppressWarnings("all")
public class Validator {
  /**
   * Check if the given network does not contain any ports. In the contrary
   * case, an RuntimeException is thrown.
   * 
   * @param network
   *            the given network
   */
  public static void checkTopLevel(final Network network) {
    if (((!network.getInputs().isEmpty()) || (!network.getOutputs().isEmpty()))) {
      System.out.println(
        ("The given network contains port(s): Be sure to run the code " + "generation on the top-level network."));
      OrccLogger.warnln("The given network contains port(s): Be sure to run the code generation on the top-level network.");
    }
  }
  
  /**
   * Check if the given network does not contain repeat bigger than the size of the associated connection.
   * In the contrary case, a message is displayed.
   * 
   * @param network
   *            the given network
   * @param size
   *            default connection size
   * @param displayWarning
   *            If true, displayed message is a warning. If false, it will be a notice
   */
  public static void checkMinimalFifoSize(final Network network, final int size) {
    Iterable<Actor> _filter = Iterables.<Actor>filter(network.getChildren(), Actor.class);
    for (final Actor actor : _filter) {
      EList<Action> _actions = actor.getActions();
      EList<Action> _initializes = actor.getInitializes();
      Iterable<Action> _plus = Iterables.<Action>concat(_actions, _initializes);
      for (final Action action : _plus) {
        {
          Validator.checkInputRepeat(action.getInputPattern(), actor, size);
          Validator.checkOutputRepeat(action.getOutputPattern(), actor, size);
        }
      }
    }
    final Function1<Instance, Boolean> _function = new Function1<Instance, Boolean>() {
      @Override
      public Boolean apply(final Instance it) {
        return Boolean.valueOf(it.isActor());
      }
    };
    Iterable<Instance> _filter_1 = IterableExtensions.<Instance>filter(Iterables.<Instance>filter(network.getChildren(), Instance.class), _function);
    for (final Instance instance : _filter_1) {
      {
        final Actor actor_1 = instance.getActor();
        EList<Action> _actions_1 = actor_1.getActions();
        EList<Action> _initializes_1 = actor_1.getInitializes();
        Iterable<Action> _plus_1 = Iterables.<Action>concat(_actions_1, _initializes_1);
        for (final Action action_1 : _plus_1) {
          {
            Validator.checkInputRepeat(action_1.getInputPattern(), instance, size);
            Validator.checkOutputRepeat(action_1.getOutputPattern(), instance, size);
          }
        }
      }
    }
    List<Network> _allNetworks = network.getAllNetworks();
    for (final Network subnetwork : _allNetworks) {
      Validator.checkMinimalFifoSize(subnetwork, size);
    }
  }
  
  private static void checkInputRepeat(final Pattern pattern, final Vertex vertex, final int size) {
    final Map<Port, Connection> incomingPortMap = vertex.<Entity>getAdapter(Entity.class).getIncomingPortMap();
    EList<Port> _ports = pattern.getPorts();
    for (final Port port : _ports) {
      boolean _containsKey = incomingPortMap.containsKey(port);
      if (_containsKey) {
        Validator.checkConnectionSize(incomingPortMap.get(port), pattern.getNumTokens(port), size);
      }
    }
  }
  
  private static void checkOutputRepeat(final Pattern pattern, final Vertex vertex, final int size) {
    final Map<Port, List<Connection>> outgoingPortMap = vertex.<Entity>getAdapter(Entity.class).getOutgoingPortMap();
    EList<Port> _ports = pattern.getPorts();
    for (final Port port : _ports) {
      boolean _containsKey = outgoingPortMap.containsKey(port);
      if (_containsKey) {
        List<Connection> _get = outgoingPortMap.get(port);
        for (final Connection outgoing : _get) {
          Validator.checkConnectionSize(outgoing, pattern.getNumTokens(port), size);
        }
      }
    }
  }
  
  private static void checkConnectionSize(final Connection connection, final int numTokens, final int size) {
    if (((connection != null) && (numTokens >= (ObjectExtensions.<Integer>operator_elvis(connection.getSize(), Integer.valueOf(size))).intValue()))) {
      final String msg = (("Potential deadlock due to the size of (" + connection) + ").");
      OrccLogger.warnln(msg);
    }
  }
}
