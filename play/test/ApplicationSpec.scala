import org.specs2.mutable._
import org.specs2.specification._
import org.specs2.matcher.JsonMatchers

import play.api.test._
import play.api.test.Helpers._

import controllers._
import models.Tweet


class ApplicationSpec extends Specification with JsonMatchers {

  "Application Controller test" should {

    "tweetList" should {
      "No callback param" in new WithApplication() {
        val rtn = route(FakeRequest(GET,"/tweetList")).get
        status(rtn) must equalTo(BAD_REQUEST)
      }
      "Valid param" in new WithApplication() {
         val rtn = route(FakeRequest(GET, "/tweetList?callback=CALL_BACK")).get
         status(rtn) must equalTo(OK)
      }
    }

    "newTweet" should {

      "Invalid param" in new WithApplication() {
        val rtn = route(FakeRequest(GET,"/tweet")).get
        status(rtn) must equalTo(NOT_FOUND)
      }
      "Valid param" in new WithApplication() {
        val rtn = route(FakeRequest(GET,"/tweet/test")).get
        status(rtn) must equalTo(OK)
        contentAsString(rtn) must contain("ok")
      }
    }

    "deleteTweet" should {
      "Invalid param" in new WithApplication() {
        val rtn = route(FakeRequest(GET,"/tweet/test/delete")).get
        status(rtn) must equalTo(BAD_REQUEST)
      }
      "Valid param(not exist id)" in new WithApplication() {
        val rtn = route(FakeRequest(GET,"/tweet/1/delete")).get
        status(rtn) must equalTo(OK)
        contentAsString(rtn) must contain("ng")
      }
    }

    "updateTweet" should {
      "Invalid param" in new WithApplication() {
        val rtn = route(FakeRequest(GET,"/tweet/test/update/test")).get
        status(rtn) must equalTo(BAD_REQUEST)
      }
      "Valid param(not exist id)" in new WithApplication() {
        val rtn = route(FakeRequest(GET,"/tweet/1/update/test")).get
        status(rtn) must equalTo(OK)
        contentAsString(rtn) must contain("ng")
      }
    }

    "getTweetList" should {
      "json match" in new WithApplication() {
        Tweet.create("dummoy-tweet")
        println(Tweet.all())
        val json = route(FakeRequest(GET,"/tweetList?=callback=CALL_BACK")).get
        println(s"------")
        println(status(json))
        //json must /("id" -> "1", "body" -> "dummy-tweet")
      }
    }

  }
}
