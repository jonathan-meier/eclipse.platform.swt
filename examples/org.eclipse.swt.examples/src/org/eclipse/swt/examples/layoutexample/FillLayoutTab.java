package org.eclipse.swt.examples.layoutexample;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved
 */

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

class FillLayoutTab extends Tab {
	/* Controls for setting layout parameters */
	Button horizontal, vertical;
	/* The example layout instance */
	FillLayout fillLayout;
	/* TableEditors and related controls*/
	TableEditor comboEditor;
	CCombo combo;
	
	/**
	 * Creates the Tab within a given instance of LayoutExample.
	 */
	FillLayoutTab(LayoutExample instance) {
		super(instance);
	}
	
	/**
	 * Creates the widgets in the "child" group.
	 */
	void createChildWidgets () {
		/* Add common controls */
		super.createChildWidgets ();
		
		/* Add TableEditors */
		comboEditor = new TableEditor (table);
		table.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				resetEditors ();
				index = table.getSelectionIndex ();
				if (index == -1) return;
				TableItem oldItem = comboEditor.getItem ();
				newItem = table.getItem (index);
				if (newItem == oldItem) return;
				table.showSelection ();
				
				combo = new CCombo (table, SWT.NONE);
				createComboEditor (combo, comboEditor);
			}
		});
		
		/* Add listener to add an element to the table */
		add.addSelectionListener(new SelectionAdapter () {
			public void widgetSelected(SelectionEvent e) {
				TableItem item = new TableItem (table, 0);
				item.setText (LayoutExample.getResourceString ("Canvas"));
				data.addElement (LayoutExample.getResourceString ("Canvas"));
				resetEditors ();
			}
		});
	}
	
	/**
	 * Creates the control widgets.
	 */
	void createControlWidgets () {
		/* Create the SelectionListener */
		SelectionListener selectionListener = new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				resetEditors ();
			};
		};
		
		/* Controls the type of FillLayout */
		Group typeGroup = new Group (controlGroup, SWT.NONE);
		typeGroup.setText (LayoutExample.getResourceString ("Type"));
		typeGroup.setLayout (new GridLayout ());
		horizontal = new Button (typeGroup, SWT.RADIO);
		horizontal.setText ("SWT.HORIZONTAL");
		horizontal.setSelection(true);
		horizontal.addSelectionListener (selectionListener);
		vertical = new Button (typeGroup, SWT.RADIO);
		vertical.setText ("SWT.VERTICAL");
		vertical.addSelectionListener (selectionListener); 
		
		/* Add common controls */
		super.createControlWidgets ();
	}
	
	/**
	 * Creates the example layout.
	 */
	void createLayout () {
		fillLayout = new FillLayout ();
		layoutComposite.setLayout (fillLayout);
	}
	
	/** 
	 * Disposes the editors without placing their contents
	 * into the table.
	 */
	void disposeEditors () {
		comboEditor.setEditor (null, null, -1);
		combo.dispose ();
	}

	
	/**
	 * Generates code for the example layout.
	 */
	StringBuffer generateLayoutCode () {
		StringBuffer code = new StringBuffer ();
		code.append ("\t\tFillLayout fillLayout = new FillLayout ();\n");
		if (fillLayout.type == SWT.VERTICAL) {
			code.append ("\t\tfillLayout.type = SWT.VERTICAL;\n");
		}
		code.append ("\t\tshell.setLayout (fillLayout);\n");
		for (int i = 0; i < children.length; i++) {
			Control control = children [i];
			code.append (getChildCode (control, i));
		}
		return code;
	}
	
	/**
	 * Returns the layout data column widths.
	 */
	int [] getLayoutDataColumnWidths () {
		return new int [] {100};
	}
	
	/**
	 * Returns the layout data field names.
	 */
	String [] getLayoutDataFieldNames() {
		return new String [] { LayoutExample.getResourceString ("Child_Widget")};
	}
	
	/**
	 * Gets the text for the tab folder item.
	 */
	String getTabText () {
		return LayoutExample.getResourceString ("FillLayout");
	}
	
	/**
	 * Takes information from TableEditors and stores it.
	 */
	void resetEditors () {
		TableItem oldItem = comboEditor.getItem ();
		comboEditor.setEditor (null, null, -1);
		if (oldItem != null) {
			int row = table.indexOf (oldItem);
			data.insertElementAt (combo.getText (), row);
			oldItem.setText (0, data.elementAt (row).toString ());
			combo.dispose ();
		}
		setLayoutState ();
		refreshLayoutComposite ();
		layoutComposite.layout (true);
		layoutGroup.layout (true);
	}
	
	/**
	 * Sets the state of the layout.
	 */
	void setLayoutState () {
		if (vertical.getSelection()) {
			fillLayout.type = SWT.VERTICAL;
		} else {
			fillLayout.type = SWT.HORIZONTAL;
		}
	}
}
