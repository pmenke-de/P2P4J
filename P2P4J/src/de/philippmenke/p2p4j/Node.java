/**
 * P2P4J source code and binaries are distributed under the MIT license. 
 *
 * Copyright (c) 2011 Philipp Menke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */



package de.philippmenke.p2p4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Philipp
 *
 */
public class Node {
	private UUID uuid;
	private InetAddress initGroup;
	private static final Logger log = Logger.getLogger(Node.class.getName());
	
	Node(){
		uuid = UUID.randomUUID();
		try{
			initGroup = InetAddress.getByName("FF12::dead:beef");
		}catch(UnknownHostException e){
			//Should never happen
			initGroup = null;
			log.log(Level.SEVERE,e.getLocalizedMessage(),e);
		}
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public InetAddress getInitGroup() {
		return initGroup;
	}
}
