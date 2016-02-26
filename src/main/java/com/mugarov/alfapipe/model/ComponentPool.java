/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import com.mugarov.alfapipe.control.listeners.MainViewButtonListener;
import com.mugarov.alfapipe.control.listeners.MenuListener;
import com.mugarov.alfapipe.view.MainViewButtonPool;

/**
 *
 * @author mugarov
 */
public class ComponentPool {
    
    // listeners for the main content - the components (Button, Menu....) will add them by themselves 
    public static final MainViewButtonListener LISTENER_BUTTON = new MainViewButtonListener();
    public static final MenuListener LISTENER_MENU = new MenuListener();
    
    // actual components
    public static MainViewButtonPool MAIN_BUTTON_POOL = new MainViewButtonPool();
    
}
