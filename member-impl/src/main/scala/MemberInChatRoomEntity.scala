import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import model.{ListOfMemberInChatRoom, MemberInChatRoom}
import play.api.libs.json.{Format, Json}

import scala.collection.immutable.Seq

class MemberInChatRoomEntity extends PersistentEntity {
  override type Command = MemberInChatRoomCommand[_]
  override type Event = MemberInChatRoomEvent
  override type State = ChatRoomState

  override def initialState: ChatRoomState = ChatRoomState(List.empty)

  override def behavior: Behavior = {
    case ChatRoomState(members) => Actions()
      .onCommand[AddMemberInChatRoomCommand, ListOfMemberInChatRoom] {
      case (AddMemberInChatRoomCommand(member), context, _) =>
        context.thenPersist(AddedMemberInChatRoomEvent(member)) {_ =>
          context.reply(ListOfMemberInChatRoom(Some(members)))
        }
    }.onEvent {
      case (AddedMemberInChatRoomEvent(member), state) => ChatRoomState(member :: state.members)
    }
  }

  case class ChatRoomState(members: List[MemberInChatRoom])
  object ChatRoomState {
    implicit val format: Format[ChatRoomState] = Json.format[ChatRoomState]
  }

  sealed trait MemberInChatRoomEvent extends AggregateEvent[MemberInChatRoomEvent] {
    def aggregateTag: AggregateEventTag[MemberInChatRoomEvent] = MemberInChatRoomEvent.Tag
  }
  object MemberInChatRoomEvent {
    val Tag: AggregateEventTag[MemberInChatRoomEvent] = AggregateEventTag[MemberInChatRoomEvent]
  }

  sealed trait MemberInChatRoomCommand[R] extends ReplyType[R]
  final case class AddMemberInChatRoomCommand(member: MemberInChatRoom) extends MemberInChatRoomCommand[Done]

  object MemberInChatRoomSerializerRegistry extends JsonSerializerRegistry {
    override def serializers: Seq[JsonSerializer[_]] = Seq(JsonSerializer[ChatRoomState])
  }

  case class AddedMemberInChatRoomEvent(member: MemberInChatRoom)

}
