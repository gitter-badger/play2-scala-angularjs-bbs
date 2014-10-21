package models

import anorm._
import anorm.SqlParser._
import java.util.Date
import org.joda.time._
import play.api.db._
import play.api.Play._
//import play.api.Logger

case class Tweet (
  //id: Pk[Long], //Primary Key
  id: Long,
  body: String
)

object Tweet {

  val tweet = {
    get[Long]("id") ~
      get[String]("body") map {
      // mapには列をscalaオブジェクトに変換する関数を渡す
      // パターンマッチでの結果を分解する
      case id~body => Tweet(id,body)
    }
  }

  // asメソッドが task * パーサーを用いてResultSetをパースして、List[Tweet]をreturn
  def all(): List[Tweet] = {
    DB.withConnection { implicit c =>
      SQL("select * from tweet").as(tweet *)
    }
  }

  def create(body: String):Int = {
    DB.withConnection { implicit c =>
      val line: Int  = SQL(
        //"""insert into tweet (body,created) values ({body},{created})"""
        """insert into tweet(body) values ({body})"""
      ).on(
          'body ->body
      ).executeUpdate()
      //ref http://playframework.blog.fc2.com/blog-entry-40.html

      line
    }
  }

  def update(id: Long, body: String):Int = {
    DB.withConnection { implicit c =>
      val line: Int = SQL(
        """update tweet set body = {body} where id = {id}"""
      ).on(
          'body ->body,
          'id -> id
      ).executeUpdate()
      //ref http://playframework.blog.fc2.com/blog-entry-40.html

      line
    }
  }

  def delete(id: Long):Int = {
    DB.withConnection { implicit c =>
      val line: Int = SQL("delete from tweet where id = {id}").on(
        'id -> id
      ).executeUpdate()

      line //return
    }
  }

  def findById(id: Int):Option[Tweet] = {
    DB.withConnection { implicit c =>
      SQL("select from tweet where id = {id}").on(
        'id -> id
      ).as(tweet.singleOpt)
      //singleOptでoption型から1つ取り出す
    }
  }

  def count():Long = {
    DB.withConnection { implicit c =>
      SQL("select count(*) from tweet")as(scalar[Long].single)
    }
  }
}
