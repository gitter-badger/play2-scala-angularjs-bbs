package model

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.specs2.specification
import models.Tweet

class TweetSpec extends Specification {

  def memoryDb = FakeApplication(additionalConfiguration = inMemoryDatabase("default"))

  /*
  def before = {
    "before Insert data" in new WithApplication(memoryDb) {
      DB.withConnection { implicit c =>
        SQL(
          """insert into Tweet(body) values({body})"""
        ).on(
            'body -> "before-dummy-tweet"
        ).execute()
      }
    }
  }

  def after = {
    "after delete data" in new WithApplication(memoryDb) {
      DB.withConnection { implicit c =>
        SQL("""delete from tweet""").execute()
      }
    }
  }
  */

  "Tweet" should {

    "Insert tweet -> update Tweet -> delete Tweet all" in new WithApplication(memoryDb) {
      Tweet.create("dummy-tweet") must_!= (0)

      val tweet = Tweet.all()
      Tweet.update(tweet.head.id,"dummy-update")
      println(tweet.head.id)
      println(tweet.head.body)
      println(tweet.tail)
      println(tweet.isEmpty)

      val update_tweet = Tweet.all()
      update_tweet.head.body must equalTo("dummy-update")

      val tweets = Tweet.all()
      println(tweets)
      tweets.foreach(tweet => Tweet.delete(tweet.id))
      Tweet.count() must_==(0)
    }

    "getAll tweet" in new WithApplication(memoryDb) {
        Tweet.all() must_!=(null)
    }

  }

}
