package model

import play.api.libs.json._

case class ListOfMemberInChatRoom (
                  usernames: Option[Seq[MemberInChatRoom]]
)

object ListOfMemberInChatRoom {
implicit val format: Format[ListOfMemberInChatRoom] = Json.format
}

