package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * Snippet Premultiplied Alpha - Bug 493455
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
public class SnippetPremultipliedAlphaBug493455 {

	public static void main(String[] args) {
		Display display = new Display();
		Image image = new Image(display, SnippetPremultipliedAlphaBug493455.class.getResourceAsStream("image.png"));

		Shell shell = new Shell(display);
		shell.setText("Snippet Premultiplied Alpha - Bug 493455");
		shell.setImage(image);
		shell.setLayout(new RowLayout(SWT.VERTICAL));

		// Menu and submenu with image
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
		menuItem.setText("Menu");
		menuItem.setImage(image);
		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		menuItem.setMenu(submenu);
		MenuItem submenuItem = new MenuItem(submenu, SWT.PUSH);
		submenuItem.setText("Submenu");
		submenuItem.setImage(image);

		// Button with text and image
		Button button = new Button(shell, SWT.PUSH);
		button.setText("Button");
		button.setImage(image);

		// Toolbar with image
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.BORDER);
		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setText("Toolbar item");
		toolItem.setImage(image);
		toolBar.pack();

		// Table with images
		Table table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setText("Table header");
		tableColumn.setImage(image);
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(0, "Table cell");
		tableItem.setImage(0, image);
		tableColumn.pack();

		// Tree with images
		Tree tree = new Tree(shell, SWT.BORDER);
		TreeItem treeItem = new TreeItem(tree, 0);
		treeItem.setText("Tree item");
		treeItem.setImage(image);

		// Tab folder
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("Tab item");
		tabItem.setImage(image);

		// Taskbar
		TaskBar taskBar = display.getSystemTaskBar();
		TaskItem taskItem = taskBar.getItem(shell);
		taskItem.setText("Task item");
		taskItem.setOverlayImage(image);

		// Tray icon
		Tray tray = display.getSystemTray();
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		trayItem.setText("Tray item");
		trayItem.setImage(image);

		shell.open();
		shell.pack();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
