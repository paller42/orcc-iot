/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class defines a generic FIFO.
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 * 
 */
public class Fifo<T> {

	private BlockingQueue<T> content;

	private String name;
	private int size;

	private FileOutputStream fos;
	private PrintWriter writer;

	private boolean enableTraces;

	/**
	 * Creates a new FIFO with the given size and a file for tracing exchanged
	 * data.
	 * 
	 * @param size
	 *            the size of the FIFO
	 * @param folderName
	 *            output traces folder
	 * @param fifoName
	 *            name of the FIFO (and the trace file)
	 */
	public Fifo(int size, String folderName, String fifoName,
			boolean enableTraces) {
		this(size);
		this.name = fifoName;
		this.enableTraces = enableTraces;

		if (enableTraces) {
			// Create network communication tracing file
			File file = new File(folderName);
			try {
				fos = new FileOutputStream(new File(file, fifoName
						+ "_traces.txt"));
				writer = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"),
						true);
			} catch (FileNotFoundException e) {
				String msg = "folder not found: \"" + folderName + "\"";
				throw new RuntimeException(msg, e);
			} catch (UnsupportedEncodingException e) {
				String msg = "unsupported utf8 encoding for folder : \""
						+ folderName + "\"";
				throw new RuntimeException(msg, e);
			}
		}
	}

	/**
	 * Creates a new FIFO with the given type and size.
	 * 
	 * @param size
	 *            the size of the FIFO
	 */
	public Fifo(int size) {
		this.size = size;
		content = new ArrayBlockingQueue<T>(size);
	}

	public void closePrinter() {
		if (writer != null) {
			writer.close();
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the name of the FIFO (name of writing actor and its corresponding
	 * output port.
	 * 
	 * @return the FIFO name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the size of the FIFO ( >= number of tokens in the FIFO).
	 * 
	 * @return FIFO size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns <code>true</code> if there is enough room for the given number of
	 * tokens in this FIFO.
	 * 
	 * @param numTokens
	 *            a number of tokens
	 * @return <code>true</code> if there is enough room for the given number of
	 *         tokens in this FIFO
	 */
	final public boolean hasRoom(int numTokens) {
		return content.remainingCapacity() >= numTokens;
	}

	/**
	 * Returns <code>true</code> if the FIFO contains at least the given number
	 * of tokens.
	 * 
	 * @param numTokens
	 *            a number of tokens
	 * @return <code>true</code> if the FIFO contains at least the given number
	 *         of tokens
	 */
	final public boolean hasTokens(int numTokens) {
		return content.size() >= numTokens;
	}

	/**
	 * Peeks one token from the FIFO (but don't remove it).
	 * 
	 * @return the token read
	 */
	@SuppressWarnings("unchecked")
	public T peek(int offset) {
		T result;

		if (offset == 0) {
			result = content.peek();
		} else {
			Object[] tempArray = content.toArray();
			result = (T) tempArray[offset];
		}

		return result;
	}


	/**
	 * Reads one token and remove it from the FIFO.
	 * 
	 * @return the token read
	 */
	public T read() {
		return content.poll();
	}

	@Override
	public String toString() {
		return "Fifo[" + size + "] : " + content.size() + " elements";
	}

	/**
	 * Writes one token in the FIFO.
	 * 
	 * @return the token read
	 */
	public void write(T value) {
		content.offer(value);

		if (enableTraces) {
			writePrinter(value);
		}
	}

	private void writePrinter(T value) {
		writer.println(value);
	}
}
