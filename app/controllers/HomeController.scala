package controllers

import javax.inject._
import play.api.mvc._

class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok("Your new application is ready.")
  }

  def someLegacy = Action {
    Ok
  }

}
