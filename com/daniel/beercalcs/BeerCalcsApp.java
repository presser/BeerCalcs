package com.daniel.beercalcs;

import com.daniel.beercalcs.base.*;

public class BeerCalcsApp extends ApplicationBase {
	
    public void startApp() {
    	FormMenu form = new FormMenu(this);
    	setActiveForm(form);    	
    }
}
