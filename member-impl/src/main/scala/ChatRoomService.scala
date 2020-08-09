import akka.{Done, NotUsed}
import api.MemberInChatRoomApi
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import model.MemberInChatRoom

class ChatRoomService(persistentEntityRegistry: PersistentEntityRegistry) extends MemberInChatRoomApi {
  /**
    * Add a user to a specific chat room.
    *
    * @param chatRoomName The chat room name
    * @return Seq[MemberInChatRoom] Body Parameter  The username to be added.
    */
  override def addUserInChatRoom(chatRoomName: String): ServiceCall[MemberInChatRoom, scala.Seq[MemberInChatRoom]] = ServiceCall { request: MemberInChatRoom =>
    val ref = persistentEntityRegistry.refFor[ChatRoomEntity](chatRoomName)
    ref.ask(AddMemberInChatRoomCommand(MemberInChatRoom(request.username, request.link)))
  }

  /**
    * Delete a specific member of a particular chat room.
    *
    * @param chatRoomName The chat room name
    * @param username     The chat room user&#39;s identifier (i.e. the identifier of the corresponding user)
    * @return void
    */
  override def removeUserFromChatRoom(chatRoomName: String, username: String): ServiceCall[NotUsed, Done] = ServiceCall {_ =>
    val ref = persistentEntityRegistry.refFor[ChatRoomEntity](chatRoomName)
    ref.ask(RemoveMemberFromChatRoomCommand(username))
  }
}
