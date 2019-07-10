
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import akka.{Done, NotUsed}
import api.MemberInChatRoomApi
import model.{ListOfMemberInChatRoom, MemberInChatRoom}

class MemberInChatRoomService(persistentEntityRegistry: PersistentEntityRegistry) extends MemberInChatRoomApi {
  /**
    * Add a user to a particular chat room
    * Add a user to a particular chat room.
    *
    * @param chatRoomName The chat room name
    * @return ListOfMemberInChatRoom Body Parameter  The username to be added.
    */
  override def addUserInChatRoom(chatRoomName: String): ServiceCall[MemberInChatRoom, ListOfMemberInChatRoom] = {request: MemberInChatRoom =>
    val ref = persistentEntityRegistry.refFor[MemberInChatRoomEntity]

    ref.ask(AddMemberInChatRoomCommand(request.chatroom, request.username))
  }


  /**
    * Delete a particular user of a particular chat room
    * Delete a particular member of a particular chat room.
    *
    * @param chatRoomName The chat room name
    * @param username     The chat room user&#39;s identifier (i.e. the identifier of the corresponding user)
    * @return void
    */
  override def removeUserFromChatRoom(chatRoomName: String, username: String): ServiceCall[NotUsed, Done] = ???
}
