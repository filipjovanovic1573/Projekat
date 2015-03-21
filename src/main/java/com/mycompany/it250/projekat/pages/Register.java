/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.it250.projekat.pages;

import org.apache.tapestry5.alerts.Alert;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.corelib.components.Submit;

/**
 *
 * @author Workbench
 */
// @Import(stylesheet="context:/css/auxiliary.css")
public class Register {
	
	@InjectComponent
	private Form register;

	@InjectComponent
	private TextField fName, lName, email;

	@InjectComponent
	private PasswordField password, cPassword;
	
	@Property
	private String fNameValue, lNameValue, emailValue, passwordValue,
			cPasswordValue;
	
	private void onSuccessFromRegister(){
		System.out.println(fNameValue);
	}
}
