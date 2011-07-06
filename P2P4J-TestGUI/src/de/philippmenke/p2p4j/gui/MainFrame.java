/**
 * P2P4J-TestGUI source code and binaries are distributed under the MIT license. 
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



package de.philippmenke.p2p4j.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * @author Philipp
 *
 */
public class MainFrame extends JFrame {

	private static final Logger log = Logger.getLogger(MainFrame.class.getName());
	private static final long serialVersionUID = -2799082374757112377L;
	private JPanel contentPane;
	private JList listLog;
	private JButton btnRequestAdvertisements;
	private JCheckBox chckbxSendOnRequest;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("P2P4J-TestGUI");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listLog = new JList();
		listLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listLog.setBounds(10, 327, 774, 114);
		contentPane.add(listLog);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(10, 308, 46, 14);
		contentPane.add(lblLog);
		
		btnRequestAdvertisements = new JButton("Request Advertisements");
		btnRequestAdvertisements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				requestAdvertisements();
			}
		});
		btnRequestAdvertisements.setBounds(10, 11, 151, 23);
		contentPane.add(btnRequestAdvertisements);
		
		chckbxSendOnRequest = new JCheckBox("Send Advertisements if requested");
		chckbxSendOnRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendOnRequestChange();
			}
		});
		chckbxSendOnRequest.setBounds(595, 11, 189, 23);
		contentPane.add(chckbxSendOnRequest);
		
		initWindow();
	}
	
	private void requestAdvertisements() {
		log.warning("Not implemented yet");
	}
	
	private void sendOnRequestChange() {
		log.log(Level.WARNING,"Not implemented yet. State: {0}",chckbxSendOnRequest.isSelected());
	}

	/**
	 * Initialized the Window incl. Models etc.
	 */
	private void initWindow() {
		DefaultListModel model = new DefaultListModel();
		listLog.setModel(model);
		log.addHandler(new ListLogHandler(model));
		log.info("Initialization done");
	}
	
	private class ListLogHandler extends Handler{
		
		private DefaultListModel model;

		/**
		 * Initialized the LogHandler with a {@link ListModel} to append the log to
		 */
		public ListLogHandler(DefaultListModel model) {
			this.model = model;
		}
		
		@Override
		public void publish(LogRecord record) {
			SimpleFormatter f = new SimpleFormatter();
			model.add(0, f.format(record));
		}
		
		@Override
		public void flush() {
			//Just do nothing.
		}
		
		@Override
		public void close() throws SecurityException {
			//Just do nothing
		}
	}
}
