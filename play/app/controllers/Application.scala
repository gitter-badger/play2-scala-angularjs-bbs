package controllers

import play.api._
import play.api.mvc._
import play.api.http.MimeTypes
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.libs.{json, Jsonp}
import models.Tweet

object Application extends Controller {

  /*
  参照 :http://akiomik.hatenablog.jp/entry/2013/10/28/021650
   */

  /*
  def postNewTweet = Action { implicit req =>

    val body = req.body.asFormUrlEncoded.get("body")(0)
    val rtn:Int = Tweet.create(body)

    if(rtn == 0) {
      Ok("ng").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    } else {
      Ok("ok").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    }
  }
  */

  def newTweet(body: String) = Action {
    val rtn:Long = Tweet.create(body)

    if(rtn == 0) {
      Ok("ng").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    } else {
      Ok("ok").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    }
  }

  def updateTweet(id: Long, body: String) = Action {
    val rtn:Int = Tweet.update(id, body)

    if(rtn == 0) {
      Ok("ng").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    } else {
      Ok("ok").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    }
  }

  def deleteTweet(id: Long) = Action {
    val rtn:Int = Tweet.delete(id)

    if(rtn == 0) {
      Ok("ng").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    } else {
      Ok("ok").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    }
  }

  def getTweetList(callback:String) = Action {

    val tweet : List[Tweet] = Tweet.all()
    val list = for( obj <- tweet ) yield {
      Map(
        "id" -> obj.id.toString,
        "body" -> obj.body.toString
      )
    }
    println(list)
    Ok(Jsonp(callback, Json.toJson(list)))
    //Ok(Json.toJson(list)).withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
  }

}
