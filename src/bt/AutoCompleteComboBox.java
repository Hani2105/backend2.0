/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 *
 * @author gabor_hanacsek
 */
public class AutoCompleteComboBox extends JComboBox {

    public int caretPos = 0;
    public JTextField tfield = null;

    public AutoCompleteComboBox(final Object itemtomb[]) {
        super(itemtomb);
        setEditor(new BasicComboBoxEditor());
        setEditable(true);
    }

    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);
        tfield.setText(getItemAt(index).toString());
        tfield.setSelectionEnd(caretPos + tfield.getText().length());
        tfield.moveCaretPosition(caretPos);
    }

    public void setEditor(ComboBoxEditor editor) {
        super.setEditor(editor);
        
            if (editor.getEditorComponent() instanceof JTextField) {
                tfield = (JTextField) editor.getEditorComponent();
                tfield.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent ke) {
                        char key = ke.getKeyChar();
                        if (!(Character.isLetterOrDigit(key) || Character.isSpaceChar(key))) {
                            return;
                        }
                        caretPos = tfield.getCaretPosition();
                        String text = "";
                        try {
                            text = tfield.getText(0, caretPos);
                        } catch (javax.swing.text.BadLocationException e) {
                            e.printStackTrace();
                            Starter.e.sendMessage(e);
                        }
                        for (int i = 0; i < getItemCount(); i++) {
                            String element = (String) getItemAt(i);
                            if (element.startsWith(text)) {
                                setSelectedIndex(i);
                                return;
                            }
                        }
                    }
                });
            }
        }
    
}
