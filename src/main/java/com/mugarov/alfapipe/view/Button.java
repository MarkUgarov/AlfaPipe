/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 *
 * @author Mark
 */
public class Button extends JButton{
    
    public Button(String text, String command){
        super(text);
        this.setDoubleBuffered(true);
        this.setActionCommand(command);
        
        
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        this.setBorderPainted(false);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        Shape firstClip = g.getClip();
        RoundRectangle2D rect = new RoundRectangle2D.Double();
        double arc = Math.ceil(getSize().getHeight()/3);
        rect.setRoundRect(0, 0, Math.ceil(getSize().getWidth()), Math.ceil(getSize().getHeight()), arc, arc);
        Area secondClip = new Area(getBounds());
        secondClip.subtract(new Area(rect));
        Area finalClip = new Area(firstClip);
        finalClip.subtract(secondClip);
        g2.setClip(finalClip);
        super.paintComponent(g2);
        Color[] gradients;
        if(getModel().isRollover())
        {
            gradients = new Color[] { new Color(184, 207, 229), new Color(122, 138, 153), new Color(184, 207, 229) };
        }
        else
        {
            gradients = new Color[] { new Color(122, 138, 153) };
        }
        for(int i = 0; i < gradients.length; i++)
        {
            arc -= 2;
            g2.setColor(gradients[i]);
            g2.drawRoundRect(i+1, i+1, (int)Math.ceil(getSize().getWidth()-2)-(i*2), (int)Math.ceil(getSize().getHeight()-2)-(i*2), (int)arc, (int)arc);
        }
    }

}
