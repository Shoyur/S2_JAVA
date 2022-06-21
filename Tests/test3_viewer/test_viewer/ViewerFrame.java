import java.awt.*;
import javax.swing.*;

class ViewerFrame extends JFrame {

    public ViewerFrame() {
    // a panel of aribtrary size
    this.add(new JPanel() {
    
    @Override
    public Dimension getPreferredSize() {
    return new Dimension(640, 480);
    }
    }, BorderLayout.CENTER);
    this.add(createBtnPanel(), BorderLayout.EAST);
    }
    
    private JPanel createBtnPanel() {
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
    btnPanel.add(createButton("Button 1"));
    btnPanel.add(createButton("Button 2"));
    btnPanel.add(createButton("Long Button 3"));
    btnPanel.add(createButton("Button 4"));
    btnPanel.add(createButton("Button 5"));
    return btnPanel;
    }
    
    private JButton createButton(String name) {
    final JButton b = new JButton(name) {
    
    @Override
    public Dimension getMaximumSize() {
    return new Dimension(
    Short.MAX_VALUE, getPreferredSize().height);
    }
    };
    b.setAlignmentX(0.5f);
    return b;
    }
    }