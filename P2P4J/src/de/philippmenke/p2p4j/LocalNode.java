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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import javax.swing.event.EventListenerList;

/**
 * @author Philipp
 *
 */
public class LocalNode extends Node {
	private EventListenerList listeners;
    private MulticastSocket socket;
    private List<InetAddress> multicastGroups;
    private boolean running;
    private static final Logger log = Logger.getLogger(LocalNode.class.getName());

    public LocalNode(){
        super();
        listeners  = new EventListenerList();
        multicastGroups  = new LinkedList<InetAddress>();
        log.fine("Constructed");
    }

    public void advertise() throws IOException{
        DatagramPacket pack = new DatagramPacket("Hello Keks".getBytes(),"Hello Keks".length(),getInitGroup(), 2306);
        socket.send(pack);
        log.info("Advertisement sent");
    }

    public void start() throws IOException{
        socket = new MulticastSocket(2306);
        socket.joinGroup(getInitGroup());
        running = true;

        //Run the advertisementReceiveLoop
        new Thread(new Runnable() {public void run() {
                advertisementReceiveLoop();
            }}).start();
        log.info("Receiver started");
    }

    public void stop() throws IOException{
        for(InetAddress group : multicastGroups){
            try{socket.leaveGroup(group);}
            catch(Exception e){log.log(Level.WARNING,"Unable to leave group "+group,e);}
        }
        socket.leaveGroup(getInitGroup());
        socket.close();
        log.info("Socket closed");
    }

    private void advertisementReceiveLoop(){
        try {
            socket.setSoTimeout(1000);
            DatagramPacket packet;
            boolean packetRecived;
            while(running){
                //Init
                packetRecived = true;
                packet = new DatagramPacket(new byte[60000], 60000);
                
                //Receive
                try{socket.receive(packet);}
                catch(SocketTimeoutException e){packetRecived = false;}

                if(packetRecived){
                    log.info("Packet received");
                    AdvertisementEvent event = new AdvertisementEvent();
                    event.setMessage(new String(packet.getData()));
                    notifyAdvertisement(event);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger("main")
                    .log(Level.SEVERE,"Major fail in advertisementReceiveLoop",ex);
        }
    }

    /* ********************************************* */
    /* ***************EVENT LISTENERS*************** */
    /* ********************************************* */

    /**
     * Adds a new eventlistener to the Advertisement event
     * @param listener The listener to add
     */
    public void addAdvertisementListener(AdvertisementListener listener){
        listeners.add(AdvertisementListener.class, listener);
    }

    /**
     * Removes a eventlistener from the Advertisement event
     * @param listener The listener to remove
     */
    public void removeAdvertisementListener(AdvertisementListener listener){
        listeners.remove(AdvertisementListener.class, listener);
    }

    /**
     * Notifies all Advertisement eventlisteners
     * @param e The event
     */
    protected synchronized void notifyAdvertisement(AdvertisementEvent e){
        for(AdvertisementListener listener : listeners.getListeners(AdvertisementListener.class)){
            listener.advertisement(e);
        }
    }
}
