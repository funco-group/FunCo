import { getCommentsData, postNotesComment, updateComment } from '@/apis/note'
import NoData from '@/components/Common/NoData'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { NoteCommentType } from '@/interfaces/note/NoteCommentType'
import { NoteCommentsType } from '@/interfaces/note/NoteCommentsType'
import { ChangeEvent, Dispatch, SetStateAction, useState } from 'react'

interface NotesDetailCommentInputProps {
  noteId: number
  parentCommentId: number | null
  commentId: number | null
  initialValue: string
  isEdit: boolean
  setCommentList: Dispatch<SetStateAction<NoteCommentType[]>>
  setCommentCnt: Dispatch<SetStateAction<number>>
}

function NotesDetailCommentInput({
  noteId,
  parentCommentId,
  commentId,
  initialValue,
  isEdit,
  setCommentList,
  setCommentCnt,
}: NotesDetailCommentInputProps) {
  const [commentText, setCommentText] = useState(initialValue)
  const { user } = useUserState()

  const handleCommentChange = (event: ChangeEvent<HTMLInputElement>) => {
    setCommentText(event.target.value)
  }

  const RefreshCommentList = async () => {
    const commentList: NoteCommentsType = await getCommentsData(noteId)
    setCommentList(commentList.comments)
    setCommentCnt(commentList.commentCount)
  }

  const handleClickSubmitBtn = () => {
    if (isEdit && commentId !== null) {
      console.log(commentText)
      updateComment(commentId, { content: commentText }, RefreshCommentList)
    } else if (parentCommentId) {
      postNotesComment(
        noteId,
        { parentCommentId, content: commentText },
        RefreshCommentList,
      )
    } else {
      postNotesComment(noteId, { content: commentText }, RefreshCommentList)
    }
    setCommentText(initialValue)
  }

  return (
    <div className="rounded-lg border border-solid p-2">
      {user ? (
        <div>
          <div className="mb-1 text-sm">{user.nickname}</div>
          <input
            type="text"
            value={commentText}
            onChange={handleCommentChange}
            placeholder="댓글을 입력해주세요"
            className="w-full border-none outline-none"
          />
          <button
            type="button"
            className={`ml-auto mr-0 block h-7 rounded border-none bg-brandColor px-2 font-NSB text-xs text-brandWhite ${!commentText.trim() && 'bg-deactivatedGray'}`}
            disabled={!commentText.trim()}
            onClick={handleClickSubmitBtn}
          >
            등록
          </button>
        </div>
      ) : (
        <NoData content="로그인 후 댓글을 작성할 수 있습니다." height={20} />
      )}
    </div>
  )
}

export default NotesDetailCommentInput
