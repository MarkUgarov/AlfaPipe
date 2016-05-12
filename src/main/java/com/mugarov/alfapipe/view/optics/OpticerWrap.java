package com.mugarov.alfapipe.view.optics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * WrapperClass
 */
public class OpticerWrap extends JPanel{
	private JComponent component;
        private boolean painted;
        private int alpha;
        private boolean opaque;

	public OpticerWrap(JComponent component)
	{
		this.component = component;
		this.setLayout( new BorderLayout() );
		this.setOpaque( false );
		this.component.setOpaque( false );
		this.add( component );
                this.painted = false;
	}
 
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(this.getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            this.painted = true;
            
        }
        
        //@Override
        @Override
        public void setBackground(Color bg){
            this.painted = false;
            super.setBackground(bg);
//            System.out.println("Color is "+bg);
        }
        
        public void forceRepaint(){
            this.painted = false;
            this.repaint();
        }
        
        @Override
        public void setOpaque(boolean isOpaque){
            this.opaque = isOpaque;
            super.setOpaque(isOpaque);
        }
        
        @Override
        public boolean isOpaque(){
            return this.opaque;
        }
     
        

        

}
