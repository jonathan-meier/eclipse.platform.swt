package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ImageTest {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setText("Image Test");

        Image image = new Image(display, ImageTest.class.getResourceAsStream("image.png"));

        Tree tree = new Tree (shell, SWT.BORDER);
        for (int i=0; i<4; i++) {
            TreeItem iItem = new TreeItem (tree, 0);
//            iItem.setBackground(new Color(new RGB(0, 255, 0)));
            iItem.setImage(image);
            iItem.setText ("TreeItem (0) -" + i);
        }

        shell.open();
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }
}