package controllers;

import play.data.Form;
import play.mvc.*;
import views.html.*;
import models.*;

public class Application extends Controller {
  
  /**
   * Defines a form wrapping the User class.
   */ 
  final static Form<Installation> installForm = form(Installation.class);
	
  public static Result index() {
    return ok(index.render());
  }
  
  public static Result licence() {
	  return ok(licence.render());
  }
  
}